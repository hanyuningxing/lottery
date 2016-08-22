package com.cai310.lottery.fetch.dczc;

 
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.cai310.lottery.Constant;
import com.cai310.utils.HttpClientUtil;
import com.google.common.collect.Maps;
 

public class DczcKaijiangOkoooFetchParser  {

	private final static Logger logger = Logger.getLogger( DczcKaijiangOkoooFetchParser.class);
 
	protected  Map parserHTML(String html, String charset,String period) {
		// TODO Auto-generated method stub
	 
		 Map<String,DczcKaijiang> map = Maps.newHashMap();
 
		Double rqspf,zjq,bqc,bifen,sxds;
 
		DczcKaijiang k  =null;
		TableTag table = getTable(html,charset);
		TableRow [] trs = table.getRows();
		
		for(int i=2;i<trs.length-1;i++){
			k =new DczcKaijiang();
			TableColumn[] tcs = trs[i].getColumns();
			String matchKey = tcs[0].getChildrenHTML();
			 
			k.setMatchKey(matchKey);
			k.setPeriod(period);
			try{
				rqspf = Double.valueOf(tcs[9].getFirstChild().getText()); 
			k.setRqspf(rqspf);
			}catch(Exception e){
				k.setRqspf(0);
			}
		 
			try{
				zjq = Double.valueOf(tcs[13].getFirstChild().getText()); 
				k.setZjq(zjq);
			}catch(Exception e){
				k.setZjq(0);
			}
			try{
				bifen = Double.valueOf(tcs[11].getFirstChild().getText()); 
				k.setBifen(bifen);
			}catch(Exception e){
				k.setBifen(0);
			}
			try{
				sxds = Double.valueOf(tcs[17].getFirstChild().getText()); 
				k.setSxds(sxds);
			}catch(Exception e){
				k.setBifen(0);
			}
			try{
				bqc = Double.valueOf(tcs[15].getFirstChild().getText()); 
				k.setBqc(bqc);
			}catch(Exception e){
				k.setBqc(0);
			} 
 
			map.put(period+matchKey, k);
		}
  
		return  map;
	}
	
	
	
	
	protected TableTag getTable(String html, String charset) {		
		Parser parser = Parser.createParser(html, charset);		
		NodeFilter tableFilter = new NodeClassFilter(TableTag.class);		
		NodeFilter[] filters = new NodeFilter[] { tableFilter };
		OrFilter filter = new OrFilter(filters);
		NodeList nodeList;
		try {
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			this.logger.warn("解析HTML出错.", e);
			return null;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof TableTag) {
				TableTag tag = (TableTag) nodeList.elementAt(i);		 
				if ("tableborder tableborder02".equals(tag.getAttribute("class"))) {
					return tag;
				}
			}
		}
		return null;
	}
	protected InputTag getInputTag(String html,String charset){
		Parser parser = Parser.createParser(html, charset);
		NodeFilter inputFilter = new NodeClassFilter(InputTag.class);
		NodeFilter[] filters = new NodeFilter[]{inputFilter};
		OrFilter filter  = new OrFilter(filters);
		NodeList nodeList;
		try{
			nodeList = parser.parse(filter);
		} catch (ParserException e) {
			this.logger.warn("解析HTML出错.", e);
			return null;
		}
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof InputTag) {
				InputTag tag = (InputTag) nodeList.elementAt(i);		 
				if ("date".equals(tag.getAttribute("id"))) {
					return tag;
				}
			}
		}
		return null;
	}
	public String getCharset() {
		// TODO Auto-generated method stub
		return "gbk";
	}
	/**
	 * 
	 * @param period
	 * 期号：例子 131007
	 * @return
	 */
	public Map fetch(String period) {
		String sourceUrl = getSourceUrl(period);
		if (StringUtils.isBlank(sourceUrl)) {
			this.logger.warn("抓取的目标网址为空.");
			return null;
		}

		final String charset = (StringUtils.isNotBlank(getCharset())) ? getCharset() : Constant.DEFAULT_ENCODE;
		String html = fetchHTML(sourceUrl, charset);
		if (StringUtils.isBlank(html)) {
			this.logger.warn("抓取回来的内容为空，目标网址：[{}]."+sourceUrl);
			return null;
		}
	 
		return parserHTML(html, charset,period);
	}

	protected String fetchHTML(String url, String charset) {
		return HttpClientUtil.getRemoteSource(url, charset);
	}

 
	public String getSourceUrl(String period) {
		// TODO Auto-generated method stub
		return "http://www.okooo.com/danchang/kaijiang/?LotteryNo="+period;
	}


	public static void main(String[] args) {//20131012001
		DczcKaijiangOkoooFetchParser parser = new DczcKaijiangOkoooFetchParser();
		Map rateData = parser.fetch("151004");
		Iterator it = rateData.entrySet().iterator();
		while(it.hasNext()){
			try{
			Entry<String, DczcKaijiang> entry = (Entry<String,DczcKaijiang>) it.next();
			DczcKaijiang k= entry.getValue();
		 
			System.out.println("key"+entry.getKey()+"|"+"【"+k.getMatchKey()+" "+k.getPeriod()+"】" +k.getRqspf()+"  "+k.getSxds()+"  "+k.getZjq()
								+"  "+k.getBqc());
			
			}catch(Exception e){
				System.out.println(e);
			}
		}
	}
	


	
}
