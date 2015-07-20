package com.cai310.lottery.fetch.jclq;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Table;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.client.ClientProtocolException;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.cai310.lottery.Constant;
import com.cai310.lottery.dto.lottery.jclq.JclqMatchDTO;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.lottery.zc.LczcMatch;
import com.cai310.lottery.entity.lottery.zc.ZcMatch;
import com.cai310.lottery.exception.FetchParseException;
import com.cai310.lottery.fetch.SimpleAbstractFetchParser;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.UTF8PostMethod;
import com.google.common.collect.Lists;


public class JclqResultFetchParser extends SimpleAbstractFetchParser<List<JclqMatchDTO>, Integer> {
	private String starDateStr;
	private String nowDateStr;
	private String endDateStr;
	public static final NumberFormat numFmt = new DecimalFormat("0.0");
	protected static final Pattern MATCH_ID_PATTERN = Pattern.compile("\\s*周(一|二|三|四|五|六|日)(\\d{3})\\s*");
	@Override
	public String getSourceUrl() {
		return "http://www.310win.com/jingcailanqiu/kaijiang_jclq_all.html";
	}
	@Override
	public List<JclqMatchDTO> fetch(Integer param) {
		Date starDate = DateUtil.strToDate(param+"","yyyyMMdd");
		Date endDate = DateUtils.addDays(starDate, 2);
		Date date = DateUtils.addDays(starDate, 1);
		this.setStarDateStr(DateUtil.dateToStr(starDate,"yyyy-MM-dd"));
		this.setEndDateStr(DateUtil.dateToStr(endDate,"yyyy-MM-dd"));
		this.setNowDateStr(DateUtil.dateToStr(date,"yyyy-MM-dd"));
		String sourceUrl = getSourceUrl();
		if (StringUtils.isBlank(sourceUrl)) {
			this.logger.warn("抓取的目标网址为空.");
			return null;
		}

		final String charset = (StringUtils.isNotBlank(getCharset())) ? getCharset() : Constant.DEFAULT_ENCODE;
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("Button1", "");
		ParamMap.put("__EVENTVALIDATION", "/wEWBALCy4LXCgLg2ZN+AsKGtEYCjOeKxgZoKG3zUdkHdLCmvrauuYyjqt2bBQ==");
		ParamMap.put("__VIEWSTATE", "/wEPDwUJNjExMjc4NTA3ZGTgskB5C5PIXxUCT19ZOjwSIuCvfA==");
		ParamMap.put("txtStartDate", starDateStr);
		ParamMap.put("txtEndDate", endDateStr);
		String html = null;
		try {
			html = httpClientUtils(getSourceUrl(), ParamMap);
		} catch (ClientProtocolException e) {
			this.logger.warn("抓取回来的内容为空，目标网址：[{}].", sourceUrl);
		} catch (IOException e) {
			this.logger.warn("抓取回来的内容为空，目标网址：[{}].", sourceUrl);
		}
		if (StringUtils.isBlank(html)) {
			this.logger.warn("抓取回来的内容为空，目标网址：[{}].", sourceUrl);
			return null;
		}
		return parserHTML(html, charset);
	}
	public static String httpClientUtils(String reUrl, Map<String,String> ParamMap)throws ClientProtocolException, IOException  {
		org.apache.commons.httpclient.HttpClient httpClient;
		PostMethod postMethod = null;
		try{
			httpClient = new org.apache.commons.httpclient.HttpClient();
			HttpClientParams p = new HttpClientParams();
			p.setSoTimeout(30000);
			p.setIntParameter(HttpConnectionParams.SO_TIMEOUT,10000); //超时设置
			p.setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 10000);//连接超时 
			httpClient.setParams(p);
			postMethod = new UTF8PostMethod(reUrl);
		    
			// HTTP参数
			org.apache.commons.httpclient.NameValuePair[] postData = new org.apache.commons.httpclient.NameValuePair[ParamMap.keySet().size()];
			int i =0;
			for (String key : ParamMap.keySet()) {
				String value = ParamMap.get(key);
				postData[i] = new org.apache.commons.httpclient.NameValuePair(key,value);
				i++;
			}
			postMethod.setRequestBody(postData);  
			httpClient.executeMethod(postMethod);
			String reStr = postMethod.getResponseBodyAsString();
			return reStr;

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			postMethod.releaseConnection();
		}    
		return "";
    
	}
	@Override
	protected List<JclqMatchDTO> parserHTML(String html, String charset) {
		TableTag tag = getTableTag(html, charset);
		if (tag == null) {
			return null;
		}
		try {
			return parser(tag);
		} catch (FetchParseException e) {
			this.logger.warn("匹配抓取数据出错.", e);
		} catch (ParseException e) {
			this.logger.warn("匹配抓取数据出错.", e);
		}

		return null;
	}
	NumberFormat nf = new DecimalFormat("00");
	protected List<JclqMatchDTO> parser(TableTag tag) throws FetchParseException, ParseException {
		TableRow[] rows = tag.getRows();
		int i = 0;
		List<JclqMatchDTO> list = Lists.newLinkedList();
		for (TableRow row : rows) {
			i++;
			if(i<1)continue;
			TableColumn[] columns = row.getColumns();
			Matcher matchIdMatcher = MATCH_ID_PATTERN.matcher(columns[0].toPlainTextString());
			try{
				if (matchIdMatcher.find()) {
					String dayOfWeekStr = matchIdMatcher.group(1);
					String lineIdOfDayStr = matchIdMatcher.group(2);
					String matchTimeStr = columns[2].toPlainTextString().trim();
					matchTimeStr=matchTimeStr.replaceAll("月", "-").replaceAll("日", " ");
					String[] matchTimeStrArr = matchTimeStr.split(" ");
					matchTimeStr =nf.format(Integer.valueOf(matchTimeStrArr[0].split("-")[0]));
					matchTimeStr =matchTimeStr+"-";
					matchTimeStr = matchTimeStr+nf.format(Integer.valueOf(matchTimeStrArr[0].split("-")[1]));
					matchTimeStr = matchTimeStr;
					if(starDateStr.indexOf(matchTimeStr)!=-1){
						matchTimeStr=starDateStr.split("-")[0].substring(2)+"-"+matchTimeStr+" "+matchTimeStrArr[1];;
					}else if(nowDateStr.indexOf(matchTimeStr)!=-1){
						matchTimeStr=nowDateStr.split("-")[0].substring(2)+"-"+matchTimeStr+" "+matchTimeStrArr[1];;
					}else if(endDateStr.indexOf(matchTimeStr)!=-1){
						matchTimeStr=endDateStr.split("-")[0].substring(2)+"-"+matchTimeStr+" "+matchTimeStrArr[1];;
					}else{
						continue;
					}
					Date matchTime = DateUtils.parseDate(matchTimeStr, new String[] { "yy-MM-dd HH:mm" });
					Integer matchDate = JclqUtil.getMatchDate(dayOfWeekStr, matchTime);
					JclqMatchDTO jclqMatch = new JclqMatchDTO();
					jclqMatch.setLineId(Integer.valueOf(lineIdOfDayStr));
					jclqMatch.setMatchDate(matchDate);
					String matchResult = columns[4].toPlainTextString();
					if(matchResult.indexOf("-")!=-1){
						jclqMatch.setHomeScore(Integer.valueOf(matchResult.split("-")[0]));
						jclqMatch.setGuestScore(Integer.valueOf(matchResult.split("-")[1]));
					}
					String singleHandicap = columns[7].toPlainTextString();
					try{jclqMatch.setSingleHandicap(numFmt.format(Double.valueOf(singleHandicap)));}catch(Exception e){}
					String singleTotalScore = columns[10].toPlainTextString();
					try{jclqMatch.setSingleTotalScore(numFmt.format(Double.valueOf(singleTotalScore)));}catch(Exception e){}
					String rfsfResultSp = columns[8].getChild(2).toPlainTextString();
					try{jclqMatch.setRfsfResultSp(Constant.MONEY_FORMAT.format(Double.valueOf(rfsfResultSp)));}catch(Exception e){}
					String dxfResultSp = columns[11].getChild(2).toPlainTextString();
					try{jclqMatch.setDxfResultSp(Constant.MONEY_FORMAT.format(Double.valueOf(dxfResultSp)));}catch(Exception e){}
					String sfResultSp = columns[6].getChild(2).toPlainTextString();
					try{jclqMatch.setSfResultSp(Constant.MONEY_FORMAT.format(Double.valueOf(sfResultSp)));}catch(Exception e){}
					String sfcResultSp = columns[9].getChild(2).toPlainTextString();
					try{jclqMatch.setSfcResultSp(Constant.MONEY_FORMAT.format(Double.valueOf(sfcResultSp)));}catch(Exception e){}
					list.add(jclqMatch);
				}
			}catch(Exception e){
				this.logger.warn("匹配抓取数据出错.", e);
				continue;
			}
		}
		return list;
	}
	 public static void main(String[] args) {
		 JclqResultFetchParser JclqResultFetchParser =  new JclqResultFetchParser();
		 JclqResultFetchParser.fetch(20130117);
	}
	protected TableTag getTableTag(String html, String charset) {
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

		for (int i = 0; i <= nodeList.size(); i++) {
			if (nodeList.elementAt(i) instanceof TableTag) {
				TableTag tag = (TableTag) nodeList.elementAt(i);
			    if ("socai".equals(tag.getAttribute("class"))) {
					return tag;
				}
			}
		}
		return null;
	}

	@Override
	public String getCharset() {
		return "GBK";
	}
	public String getStarDateStr() {
		return starDateStr;
	}
	public void setStarDateStr(String starDateStr) {
		this.starDateStr = starDateStr;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public String getNowDateStr() {
		return nowDateStr;
	}
	public void setNowDateStr(String nowDateStr) {
		this.nowDateStr = nowDateStr;
	}

}
