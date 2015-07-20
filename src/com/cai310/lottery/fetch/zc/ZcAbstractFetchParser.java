package com.cai310.lottery.fetch.zc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.lottery.zc.LczcMatch;
import com.cai310.lottery.entity.lottery.zc.SfzcMatch;
import com.cai310.lottery.entity.lottery.zc.ZcMatch;
import com.cai310.lottery.fetch.SimpleAbstractFetchParser;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.utils.ZunaoQueryPVisitor;
import com.cai310.utils.HttpClientUtil;
import com.google.common.collect.Maps;

public abstract class ZcAbstractFetchParser extends SimpleAbstractFetchParser<List<ZcMatch>, String> {
    public Map<Integer,ZcMatch> zcMatchMap=Maps.newHashMap();
	public abstract String getType();
	public abstract ZcMatch getZcMatch();
	@Override
	public List<ZcMatch> fetch(String issueNum) {
		String sourceUrl = getSourceUrl();
		sourceUrl = sourceUrl+"20"+issueNum;
		if (StringUtils.isBlank(sourceUrl)) {
			this.logger.warn("抓取的目标网址为空.");
			return null;
		}
		///捉取赔率
		String oddsUrl = "http://www.310win.com/handle/handicap.aspx?issuenum="+"20"+issueNum+"&typeid="+getType()+"&companyid=1&"+System.currentTimeMillis()/1000+"000";
		String oddsReturn = HttpClientUtil.getRemoteSource(oddsUrl, Constant.DEFAULT_ENCODE);
		try {
			Document doc=DocumentHelper.parseText(oddsReturn);
			Element root = doc.getRootElement();
			Element ticketElement;
			for (Iterator i = root.elementIterator("i"); i.hasNext();) {
				Element oddsElement = (Element) i.next();
				String odds = oddsElement.getText();
				if(StringUtils.isNotBlank(odds)){
					odds = odds.trim();
					String[] oddsArr = odds.split(",");
					ZcMatch zcMatch =  getZcMatch();
					zcMatch.setLineId(Integer.valueOf(oddsArr[1]));
					zcMatch.setAsiaOdd(StringUtils.isNotBlank(oddsArr[4])?Double.valueOf(oddsArr[4]):Double.valueOf(0));
					zcMatchMap.put(zcMatch.getLineId(), zcMatch);
				}
			}
		} catch (DocumentException e) {
			this.logger.warn("解析网址失败，目标网址：[{}].", oddsUrl);
		}
		oddsUrl = "http://www.310win.com/handle/1x2.aspx?issuenum="+"20"+issueNum+"&typeid="+getType()+"&companyid=0&"+System.currentTimeMillis()/1000+"000";
		oddsReturn = HttpClientUtil.getRemoteSource(oddsUrl, Constant.DEFAULT_ENCODE);
		try {
			Document doc=DocumentHelper.parseText(oddsReturn);
			Element root = doc.getRootElement();
			Element ticketElement;
			for (Iterator i = root.elementIterator("i"); i.hasNext();) {
				Element oddsElement = (Element) i.next();
				String odds = oddsElement.getText();
				if(StringUtils.isNotBlank(odds)){
					odds = odds.trim();
					String[] oddsArr = odds.split(",");
					ZcMatch zcMatch = null;
					if(null!=zcMatchMap.get(Integer.valueOf(oddsArr[1]))){
						zcMatch =  zcMatchMap.get(Integer.valueOf(oddsArr[1]));
					}else{
						zcMatch =  getZcMatch();
						zcMatch.setLineId(Integer.valueOf(oddsArr[1]));
					}
					zcMatch.setOdds1(StringUtils.isNotBlank(oddsArr[3])?Double.valueOf(oddsArr[3]):Double.valueOf(0));
					zcMatch.setOdds2(StringUtils.isNotBlank(oddsArr[4])?Double.valueOf(oddsArr[4]):Double.valueOf(0));
					zcMatch.setOdds3(StringUtils.isNotBlank(oddsArr[5])?Double.valueOf(oddsArr[5]):Double.valueOf(0));
					zcMatchMap.put(zcMatch.getLineId(), zcMatch);
				}
			}
		} catch (DocumentException e) {
			this.logger.warn("解析网址失败，目标网址：[{}].", oddsUrl);
		}
		///捉取赔率结束
		final String charset = (StringUtils.isNotBlank(getCharset())) ? getCharset() : Constant.DEFAULT_ENCODE;
		String html = fetchHTML(sourceUrl, charset);
		if (StringUtils.isBlank(html)) {
			this.logger.warn("抓取回来的内容为空，目标网址：[{}].", sourceUrl);
			return null;
		}
		return parserHTML(html, charset);
	}
	
	public static void main(String[] args) {
		NumberFormat TWO_NF = new DecimalFormat("##########000");
		System.out.println(TWO_NF.format(System.currentTimeMillis()));
		System.out.println(System.currentTimeMillis()/1000+"000");
	}
	public Map<Integer, ZcMatch> getZcMatchMap() {
		return zcMatchMap;
	}
	public void setZcMatchMap(Map<Integer, ZcMatch> zcMatchMap) {
		this.zcMatchMap = zcMatchMap;
	}
}
