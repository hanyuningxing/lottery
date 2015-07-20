package com.cai310.lottery.fetch.jczq;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.regex.Matcher;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.params.CookiePolicy;
import org.dom4j.DocumentException;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.fetch.jczq.local.JczqMatchDto;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.utils.ZunaoDczcSp;
import com.cai310.lottery.utils.ZunaoDczcSpVisitor;
import com.cai310.lottery.utils.ZunaoUtil;
import com.cai310.utils.UTF8PostMethod;
import com.google.common.collect.Maps;

public class JczqSPFPassFetchParser extends JczqSimplePassFetchParser {

	@Override
//	public String getSourceUrl() {
////		return "http://info.sporttery.cn/lotterygame/hhad_list.php";
//		return "http://info.sporttery.cn/football/hhad_list.php";
//	}
	public String getSourceUrl() {
		return "http://i.sporttery.cn/odds_calculator/get_odds?i_format=json&poolcode[]=had"; 
	}
	public  String Utf8HttpClientUtils(String reUrl, Map<String,String> ParamMap)throws ClientProtocolException, IOException  {
		org.apache.commons.httpclient.HttpClient httpClient;
		PostMethod postMethod = null;
		try{
			httpClient = new org.apache.commons.httpclient.HttpClient();
			HttpClientParams p = new HttpClientParams();
			p.setSoTimeout(200000);
			p.setIntParameter(HttpConnectionParams.SO_TIMEOUT,200000); //超时设置
			p.setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 200000);//连接超时 
			 //设置 HttpClient 接收 Cookie,用与浏览器一样的策略
			p.setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			 //让服务器知道访问源为浏览器

			p.setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; rv:8.0.1) Gecko/20100101 Firefox/8.0.1");
			
//			原始头信息
//			Accept	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
//			Accept-Encoding	gzip, deflate
//			Accept-Language	zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3
//			Connection	keep-alive
//			Cookie	__utma=212992214.1853643110.1370238211.1370326853.1370331631.4; __utmz=212992214.1370238211.1.1.utmcsr=baidu|utmccn=(organic)|utmcmd=organic|utmctr=%D6%D0%B9%FA%BE%BA%B2%CA%CD%F8; Hm_lvt_860f3361e3ed9c994816101d37900758=1370238211,1370310912
//			Host	i.sporttery.cn
//			User-Agent	Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:22.0) Gecko/20100101 Firefox/22.0


			//通过head对象来设置请求头参数

			List<Header> headers = new ArrayList<Header>();

			headers.add(new Header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")); 
			headers.add(new Header("Accept-Encoding", "gzip, deflate"));  
			headers.add(new Header("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3"));  
			headers.add(new Header("Connection", "keep-alive"));  
			p.setParameter("http.default-headers", headers); 
			httpClient.setParams(p);
			postMethod = new UTF8PostMethod(reUrl);
			// HTTP参数
			if(null!=ParamMap){
				org.apache.commons.httpclient.NameValuePair[] postData = new org.apache.commons.httpclient.NameValuePair[ParamMap.keySet().size()];
				int i =0;
				for (String key : ParamMap.keySet()) {
					String value = ParamMap.get(key);
					postData[i] = new org.apache.commons.httpclient.NameValuePair(key,value);
					i++;
				}
				postMethod.setRequestBody(postData); 
			}
			httpClient.executeMethod(postMethod);
			String reStr = postMethod.getResponseBodyAsString();
			logger.debug("回传数据:"+reStr);
			return reStr;

		}catch(Exception e){
			e.printStackTrace();
			logger.warn("请求出错:"+e.getMessage());
		}finally{
			postMethod.releaseConnection();
		}    
		return null;
    
	}
	@Override
	public PlayType getPlayType() {
		return PlayType.SPF;
	}
	@Override
	public PassMode getPassMode() {
		return PassMode.PASS;
	}
//	@Override
//	protected JczqFetchResult parserHTML(String html, String charset) {
//		TableTag tag = getTableTag(html, charset);
//		if (tag == null) {
//			return null;
//		}
//
//		try {
//			return parser(tag);
//		} catch (ParseException e) {
//			this.logger.warn("匹配抓取数据出错.", e);
//		}
//
//		return null;
//	}

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
				if ("tabContent".equals(tag.getAttribute("class"))) {
					return tag;
				}
			}
		}

		return null;
	}

	protected JczqFetchResult parser(TableTag tag) throws ParseException {
		SortedMap<String, Map<String, Double>> rateData = Maps.newTreeMap();
		SortedMap<String, JczqMatch> matchMap = Maps.newTreeMap();

		TableRow[] rows = tag.getRows();
		Map<String,Map<String, Double>> rateDataSp = Maps.newHashMap();
		try {
			rateDataSp= getSPFItemMap();
		} catch (Exception e) {
			this.logger.warn("抓取zunao数据出错.", e);
		} 
		for (TableRow row : rows) {
			JczqMatch matchDTO = getMatchDTO(row);
			
			String key = JczqUtil.buildMatchKey(matchDTO.getMatchDate(), matchDTO.getLineId());
			Map<String, Double> itemMap = rateDataSp.get(key);
			matchMap.put(key, matchDTO);
			rateData.put(key, itemMap);
		}
		return new JczqFetchResult(getPlayType(), getPassMode(), matchMap, rateData);
	}
    protected Map<String,Map<String, Double>> getSPFItemMap() throws DataException, IOException, DocumentException, ParseException{
    	ZunaoDczcSpVisitor zunaoDczcSpVisitor = ZunaoUtil.getJczqSp("jczqgg");
    	List<ZunaoDczcSp> zunaoDczcSpList = zunaoDczcSpVisitor.getZunaoDczcSpList();
    	final Calendar now = Calendar.getInstance();
		final int curYear = Calendar.getInstance().get(Calendar.YEAR);
		Calendar c = Calendar.getInstance();
		SortedMap<String, Map<String, Double>> matchMap = Maps.newTreeMap();
    	for (ZunaoDczcSp zunaoDczcSp : zunaoDczcSpList) {
	    		String dayOfWeekStr =zunaoDczcSp.getMatchId().replace("周", "").substring(0,1);
	    		Integer lindId =Integer.valueOf(zunaoDczcSp.getMatchId().replace("周", "").substring(1));
	    		String matchTimeStr = zunaoDczcSp.getMatchtime();
	    		Date time = DateUtils.parseDate(matchTimeStr,
	    				new String[] { "yyyy-MM-dd HH:mm" });
	    		c.setTime(time);
	    		if (now.getTimeInMillis() - c.getTimeInMillis() > CHECK_DAY) {
	    			c.add(Calendar.YEAR, 1);
	    		}
	    		Date matchTime = c.getTime();
	
	    		Integer matchDate = JczqUtil.getMatchDate(dayOfWeekStr, matchTime);
	    		String key = JczqUtil.buildMatchKey(matchDate, lindId);
	    		///2.5,3.25,2.45|9,3.7,3.2,4.1,6,12,25,35|5,6,7,10,12,28,22,28,70,70,80,150,50,9,6,18,80,400,12,28,18,100,70,67,400,250,250,750,500,500,250|2.1,20,60,3.9,5,13,26,20,11|1.45,3.6,6.5
	    		String sp = zunaoDczcSp.getSp();
	    		sp = sp.substring(sp.lastIndexOf("|")+1);
	    		String[] spArr = sp.split(",");

	    	try{
	    		Map<String, Double> itemMap = Maps.newHashMap();
	    		itemMap.put(ItemSPF.WIN.toString(), Double.valueOf(spArr[0]));
	    		itemMap.put(ItemSPF.DRAW.toString(), Double.valueOf(spArr[1]));
	    		itemMap.put(ItemSPF.LOSE.toString(), Double.valueOf(spArr[2]));
	    		matchMap.put(key, itemMap);
    		}catch(Exception e){
    			Map<String, Double> itemMap = Maps.newHashMap();
	    		itemMap.put(ItemSPF.WIN.toString(), Double.valueOf(0));
	    		itemMap.put(ItemSPF.DRAW.toString(), Double.valueOf(0));
	    		itemMap.put(ItemSPF.LOSE.toString(), Double.valueOf(0));
    			matchMap.put(key, itemMap);
    			continue;
    		}
		}
    	
		return matchMap;
    }
    public static void main(String[] args) throws DataException, IOException, DocumentException, ParseException {
    	JczqSPFPassFetchParser ss = new JczqSPFPassFetchParser();
    	ss.fetch(null);
	}
	protected Map<String, Double> getItemMap(TableRow row) {
		TableColumn[] columns = row.getColumns();

		int columnIndex = getRateColumnStartIndex();
		Map<String, Double> itemMap = Maps.newHashMap();
		for (Item item : getPlayType().getAllItems()) {
			String rateStr = columns[columnIndex].toPlainTextString();
			Double rate = (StringUtils.isNotBlank(rateStr) && rateStr.matches(DOUBLE_REGEX)) ? Double.valueOf(rateStr)
					: 0;
			itemMap.put(item.toString(), rate);
			columnIndex++;
		}
		return itemMap;
	}

	protected int getRateColumnStartIndex() {
		return 4;
	}

	protected JczqMatch getMatchDTO(TableRow row) throws ParseException {
		final Calendar now = Calendar.getInstance();
		final int curYear = Calendar.getInstance().get(Calendar.YEAR);

		Calendar c = Calendar.getInstance();
		TableColumn[] columns = row.getColumns();
		Matcher matchIdMatcher = MATCH_ID_PATTERN.matcher(columns[0].toPlainTextString());
		if (matchIdMatcher.find()) {
			String dayOfWeekStr = matchIdMatcher.group(1);
			String lineIdOfDayStr = matchIdMatcher.group(2);

			String bgcolor = columns[1].getAttribute("bgcolor");
			if (StringUtils.isBlank(bgcolor)) {
				bgcolor = columns[1].getAttribute("bgColor");
			}
			String gameName = columns[1].toPlainTextString().trim();

			Matcher teamMatcher = TEAM_PATTERN.matcher(columns[2].toPlainTextString());
			if (teamMatcher.find()) {
				String home = teamMatcher.group(1);
				String handicapStr = teamMatcher.group(2);
				Byte handicap = (StringUtils.isNotBlank(handicapStr)) ? Byte.valueOf(handicapStr.replaceAll("\\+", ""))
						: 0;
				String guest = teamMatcher.group(3);

				String matchTimeStr = columns[3].toPlainTextString();
				Date time = DateUtils.parseDate(String.format("%s-%s", curYear, matchTimeStr),
						new String[] { "yyyy-MM-dd HH:mm" });
				c.setTime(time);
				if (now.getTimeInMillis() - c.getTimeInMillis() > CHECK_DAY) {
					c.add(Calendar.YEAR, 1);
				}
				Date matchTime = c.getTime();

				Integer matchDate = JczqUtil.getMatchDate(dayOfWeekStr, matchTime);

				JczqMatch matchDTO = new JczqMatch();
				matchDTO.setGameColor(bgcolor);
				matchDTO.setGameName(gameName);
				matchDTO.setHomeTeamName(home);
				matchDTO.setGuestTeamName(guest);
				matchDTO.setMatchTime(matchTime);
				matchDTO.setLineId(Integer.valueOf(lineIdOfDayStr));
				matchDTO.setMatchDate(matchDate);
				matchDTO.setHandicap(handicap);
				return matchDTO;
			}
		}
		return null;
	}
	
	/**
	 * 抓取的json数据rate解析
	 * Add By Suixinhang
	 */
	@Override
	public Map<String, Double> getItemMap(JczqMatchDto matchDto) {
		Map<String, Double> itemMap = Maps.newHashMap();
		String odds = matchDto.getOdds();
		if(StringUtils.isBlank(odds)){
			for (Item item : getPlayType().getAllItems()) {
				itemMap.put(item.toString(), 0.0);
			}
			return itemMap;
		}
		String[] odd = odds.split(",");
		if(odd.length == 0){
			for (Item item : getPlayType().getAllItems()) {
				itemMap.put(item.toString(), 0.0);
			}
			return itemMap;
		}
		int i = 2;
		for (Item item : getPlayType().getAllItems()) {
			String rateStr = odd[i];
			Double rate = (StringUtils.isNotBlank(rateStr) && rateStr.matches(DOUBLE_REGEX)) ? Double.valueOf(rateStr)
					: 0;
			itemMap.put(item.toString(), rate);
			i--;
		}
		return itemMap;
	}
	
}
