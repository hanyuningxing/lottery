package com.cai310.lottery.fetch.jczq;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.client.ClientProtocolException;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.cai310.lottery.Constant;
import com.cai310.lottery.dto.lottery.jczq.JczqMatchDTO;
import com.cai310.lottery.exception.FetchParseException;
import com.cai310.lottery.fetch.SimpleAbstractFetchParser;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.UTF8PostMethod;
import com.google.common.collect.Lists;

public class JczqResultFetchParser extends SimpleAbstractFetchParser<List<JczqMatchDTO>, Integer> {
	private String starDateStr;
	private String nowDateStr;
	private String endDateStr;
	protected static final Pattern MATCH_ID_PATTERN = Pattern.compile("\\s*周(一|二|三|四|五|六|日)(\\d{3})\\s*");
	@Override
	public String getSourceUrl() {
		return "http://www.310win.com/jingcaizuqiu/kaijiang_jc_all.html";
	}
	@Override
	public List<JczqMatchDTO> fetch(Integer param) {
		Date date = DateUtil.strToDate(param+"","yyyyMMdd");
		Date endDate = DateUtils.addDays(date, 1);
		Date starDate = DateUtils.addDays(date, -1);
		this.setStarDateStr(DateUtil.dateToStr(starDate,"yyyy-MM-dd"));
		this.setNowDateStr(DateUtil.dateToStr(date,"yyyy-MM-dd"));
		this.setEndDateStr(DateUtil.dateToStr(endDate,"yyyy-MM-dd"));
		String sourceUrl = getSourceUrl();
		if (StringUtils.isBlank(sourceUrl)) {
			this.logger.warn("抓取的目标网址为空.");
			return null;
		}

		final String charset = (StringUtils.isNotBlank(getCharset())) ? getCharset() : Constant.DEFAULT_ENCODE;
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("Button1", "");
		ParamMap.put("__EVENTVALIDATION", "/wEWBAKz/MymDQLg2ZN+AsKGtEYCjOeKxgZTuQ3CfTwdLjkkRCt+jN6hlGXAzA==");
		ParamMap.put("__VIEWSTATE", "/wEPDwUJMzYzMzQyODQ2ZGQTU54JfdsFlPL78umJ2GdLvnQLNg==");
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
//			postMethod.setRequestBody(postData);  
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
	protected List<JczqMatchDTO> parserHTML(String html, String charset) {
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
	protected List<JczqMatchDTO> parser(TableTag tag) throws FetchParseException, ParseException {
		TableRow[] rows = tag.getRows();
		int i = 0;
		List<JczqMatchDTO> list = Lists.newLinkedList();
		for (TableRow row : rows) {
			i++;
			if(i<2)continue;
			TableColumn[] columns = row.getColumns();
			Matcher matchIdMatcher = MATCH_ID_PATTERN.matcher(columns[0].toPlainTextString());
			try{
				if (matchIdMatcher.find()) {
					String dayOfWeekStr = matchIdMatcher.group(1);
					String lineIdOfDayStr = matchIdMatcher.group(2);
					String matchTimeStr = columns[0].getChild(2).toPlainTextString();
					String[] matchTimeStrArr = matchTimeStr.split(" ");
					if(starDateStr.indexOf(matchTimeStrArr[0])!=-1){
						matchTimeStr=starDateStr.split("-")[0].substring(2)+"-"+matchTimeStr;
					}else if(nowDateStr.indexOf(matchTimeStrArr[0])!=-1){
						matchTimeStr=nowDateStr.split("-")[0].substring(2)+"-"+matchTimeStr;
					}else if(endDateStr.indexOf(matchTimeStrArr[0])!=-1){
						matchTimeStr=endDateStr.split("-")[0].substring(2)+"-"+matchTimeStr;
					}else{
						continue;
					}
					Date matchTime = DateUtils.parseDate(matchTimeStr, new String[] { "yy-MM-dd HH:mm" });
					Integer matchDate = JczqUtil.getMatchDate(dayOfWeekStr, matchTime);
					JczqMatchDTO jczqMatch = new JczqMatchDTO();
					jczqMatch.setLineId(Integer.valueOf(lineIdOfDayStr));
					jczqMatch.setMatchDate(matchDate);
					String matchHalfResult = columns[5].toPlainTextString();
					if(matchHalfResult.indexOf("-")!=-1){
						jczqMatch.setHalfHomeScore(Integer.valueOf(matchHalfResult.split("-")[0]));
						jczqMatch.setHalfGuestScore(Integer.valueOf(matchHalfResult.split("-")[1]));
					}
					String matchResult = columns[3].toPlainTextString();
					if(matchResult.indexOf("-")!=-1){
						jczqMatch.setFullHomeScore(Integer.valueOf(matchResult.split("-")[0]));
						jczqMatch.setFullGuestScore(Integer.valueOf(matchResult.split("-")[1]));
					}
					
					String rfspfResultSp = columns[6].getChild(2).toPlainTextString();
					try{jczqMatch.setRfspfResultSp(Constant.MONEY_FORMAT.format(Double.valueOf(rfspfResultSp)));}catch(Exception e){}
					String spfResultSp = columns[7].getChild(2).toPlainTextString();
					try{jczqMatch.setSpfResultSp(Constant.MONEY_FORMAT.format(Double.valueOf(spfResultSp)));}catch(Exception e){}
					String bfResultSp = columns[8].getChild(2).toPlainTextString();
					try{jczqMatch.setBfResultSp(Constant.MONEY_FORMAT.format(Double.valueOf(bfResultSp)));}catch(Exception e){}
					String jqsResultSp = columns[9].getChild(2).toPlainTextString();
					try{jczqMatch.setJqsResultSp(Constant.MONEY_FORMAT.format(Double.valueOf(jqsResultSp)));}catch(Exception e){}
					String bqqResultSp = columns[10].getChild(2).toPlainTextString();
					try{jczqMatch.setBqqResultSp(Constant.MONEY_FORMAT.format(Double.valueOf(bqqResultSp)));}catch(Exception e){}
					list.add(jczqMatch);
				}
			}catch(Exception e){
				this.logger.warn("匹配抓取数据出错.", e);
				continue;
			}
		}
		return list;
	}
	 public static void main(String[] args) {
		 try {
			Date matchTime = DateUtils.parseDate("16-03-24 02:11", new String[] { "yy-MM-dd HH:mm" });
			int i = 0;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 JczqResultFetchParser JczqResultFetchParser =  new JczqResultFetchParser();
		 JczqResultFetchParser.fetch(20160324);
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
