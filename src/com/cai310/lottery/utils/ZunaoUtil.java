package com.cai310.lottery.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.fetch.jczq.local.JczqMatchDto;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.MD5;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZunaoUtil {
	 private static final String reUrl="http://121.12.168.12:662/ticketinterface.aspx"; //电信接口
	    //private static final String reUrl="http://218.60.9.69:662/TicketInterface.aspx";  //网通接口(一)
	    //private static final String reUrl="http://210.51.44.6:662/ticketinterface.aspx";  //网通接口(二)
	 private static String transcode="1000";
	 private static String msg="msg";
	 private static String partnerid = "349039";
	 private static String key ="6161A929FB95A44F134BCBE08EB36D89";
	
//	private static final String reUrl="http://121.12.168.124:661/ticketinterface.aspx";
//	private static String transcode="1000";
//	private static String msg="msg";
//	private static String partnerid = "349022";
//	private static String key ="123456";
	
	public static ZunaoQueryPVisitor getIssue(Lottery lottery,Byte betType) throws DataException, IOException, DocumentException{
		transcode="001" ;
		Ticket ticket = new Ticket();
		ticket.setBetType(betType);
		ticket.setLotteryType(lottery);
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		wParamValueSb.append("<msg>");
		wParamValueSb.append("<head transcode=\""+transcode+"\"  partnerid=\""+partnerid+"\" version=\"1.0\" time=\"20110628175541\" />");
		wParamValueSb.append("<body>");
		wParamValueSb.append("<queryissue  lotteryId=\""+getCpdyjLotteryId(ticket)+"\"  issueNumber=\"\"  />");
		wParamValueSb.append("</body>");
		wParamValueSb.append("</msg>");
		msg = wParamValueSb.toString();
		String postData="transcode="+transcode+"&msg="+msg+"&key="+MD5.md5(transcode+msg+key).toLowerCase()+"&partnerid="+partnerid;
	    String returnString = Utf8HttpClientUtils(reUrl,postData);
	    if(StringUtils.isNotBlank(returnString)){
	    	Integer start=returnString.indexOf("msg=")+"msg=".length();
	    	Integer end=returnString.indexOf("&key=");
	    	returnString = returnString.substring(start,end);
	    	Document doc=DocumentHelper.parseText(returnString);
	    	ZunaoQueryPVisitor queryPVisitor=new ZunaoQueryPVisitor();
			doc.accept(queryPVisitor);
			return queryPVisitor;
	    }
		return null;
	}
	public static ZunaoQueryPVisitor confirmTicket(Ticket ticket) throws DataException, IOException, DocumentException{
		transcode="003" ;
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
		wParamValueSb.append("<msg>"); 
		wParamValueSb.append("<head transcode=\"003\" partnerid=\""+partnerid+"\" version=\"1.0\" time=\""+System.currentTimeMillis()+"\" />"); 
		wParamValueSb.append("<body>"); 
		wParamValueSb.append("<queryticket   palmId=\"\"  ticketId=\""+ticket.getId()+"\"/>"); 
		wParamValueSb.append("</body>"); 
	    wParamValueSb.append("</msg>"); 
	    msg = wParamValueSb.toString();
		String postData="transcode="+transcode+"&msg="+msg+"&key="+MD5.md5(transcode+msg+key).toLowerCase()+"&partnerid="+partnerid;
	    String returnString = Utf8HttpClientUtils(reUrl,postData);
	    if(StringUtils.isNotBlank(returnString)){
	    	Integer start=returnString.indexOf("msg=")+"msg=".length();
	    	Integer end=returnString.indexOf("&key=");
	    	returnString = returnString.substring(start,end);

			Document doc=DocumentHelper.parseText(returnString);
			Element root = doc.getRootElement();
			Element ticketElement;
			ZunaoQueryPVisitor queryPVisitor = null;
			Element body = root.element("body");
			for (Iterator i = body.elementIterator("ticketresult"); i.hasNext();) {
				ticketElement = (Element) i.next();
					queryPVisitor=new ZunaoQueryPVisitor();
					ticketElement.accept(queryPVisitor);
					
			}
			return queryPVisitor;
	    }
		return null;
	}
	public static ZunaoDczcSpVisitor getDczcSp(Lottery lottery,Byte betType,String issueNumer) throws DataException, IOException, DocumentException{
		transcode="008" ;
		Ticket ticket = new Ticket();
		ticket.setBetType(betType);
		ticket.setLotteryType(lottery);
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		wParamValueSb.append("<msg>");
		wParamValueSb.append("<head transcode=\""+transcode+"\"  partnerid=\""+partnerid+"\" version=\"1.0\" time=\"20110628175541\" />");
		wParamValueSb.append("<body>");
		wParamValueSb.append("<querygamesp  lotteryId =\""+getCpdyjLotteryId(ticket)+"\"  issueNumber =\""+issueNumer+"\" />");
		wParamValueSb.append("</body>");
		wParamValueSb.append("</msg>");
		msg = wParamValueSb.toString();
		String postData="transcode="+transcode+"&msg="+msg+"&key="+MD5.md5(transcode+msg+key).toLowerCase()+"&partnerid="+partnerid;
	    String returnString = Utf8HttpClientUtils(reUrl,postData);
	    if(StringUtils.isNotBlank(returnString)){
	    	Integer start=returnString.indexOf("msg=")+"msg=".length();
	    	Integer end=returnString.indexOf("&key=");
	    	returnString = returnString.substring(start,end);
	    	Document doc=DocumentHelper.parseText(returnString);
	    	ZunaoDczcSpVisitor queryPVisitor=new ZunaoDczcSpVisitor();
			doc.accept(queryPVisitor);
			return queryPVisitor;
	    }
		return null;
	}
	public static ZunaoDczcSpVisitor getJczqSp(String type) throws DataException, IOException, DocumentException{
		String match = DateUtil.dateToStr(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd");
		transcode="013" ;
		Ticket ticket = new Ticket();
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		wParamValueSb.append("<msg>");
		wParamValueSb.append("<head transcode=\""+transcode+"\"  partnerid=\""+partnerid+"\" version=\"1.0\" time=\"20110628175541\" />");
		wParamValueSb.append("<body>");
		wParamValueSb.append("<querygamesp  type=\""+type+"\"  lotteryId =\"\"  macthdate =\""+match+"\" />");
		wParamValueSb.append("</body>");
		wParamValueSb.append("</msg>");
		msg = wParamValueSb.toString();
		String postData="transcode="+transcode+"&msg="+msg+"&key="+MD5.md5(transcode+msg+key).toLowerCase()+"&partnerid="+partnerid;
	    String returnString = Utf8HttpClientUtils(reUrl,postData);
	    if(StringUtils.isNotBlank(returnString)){
	    	Integer start=returnString.indexOf("msg=")+"msg=".length();
	    	Integer end=returnString.indexOf("&key=");
	    	returnString = returnString.substring(start,end);
	    	Document doc=DocumentHelper.parseText(returnString);
	    	ZunaoDczcSpVisitor queryPVisitor=new ZunaoDczcSpVisitor();
			doc.accept(queryPVisitor);
			return queryPVisitor;
	    }
		return null;
	}
	public static Map<String, JczqMatch> getJczqMatch(List<JczqMatchDto> colorList) throws DataException, IOException, DocumentException, ParseException{
		String match = DateUtil.dateToStr(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd");
		transcode="016" ;
		Ticket ticket = new Ticket();
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		wParamValueSb.append("<msg>");
		wParamValueSb.append("<head transcode=\""+transcode+"\"  partnerid=\""+partnerid+"\" version=\"1.0\" time=\"20110628175541\" />");
		wParamValueSb.append("<body>");
		wParamValueSb.append("<querySchedule  type=\"jczq\"/>");
		wParamValueSb.append("</body>");
		wParamValueSb.append("</msg>");
		msg = wParamValueSb.toString();
		String postData="transcode="+transcode+"&msg="+msg+"&key="+MD5.md5(transcode+msg+key).toLowerCase()+"&partnerid="+partnerid;
	    String returnString = Utf8HttpClientUtils(reUrl,postData);
	    //transcode=116&msg=<?xml version="1.0" encoding="UTF-8"?><msg><head transcode="116" partnerid="349039" version="1.0" time="20131106161312" /><body><jcgames>
	    //<jcgame name="欧洲冠军联赛" matchID="周三001" hometeam="圣彼得堡泽尼特" guestteam="波尔图" matchstate="0" matchtime="2013-11-07 01:00" sellouttime="2013-11-06 23:55" polygoal="-1" goal="0" ougoal="0" PrivilegesType="" /></jcgames></body></msg>&key=35A39D3F67012319F58DF6014F26C1D6&partnerid=349039
	    Map<String, JczqMatch> map = Maps.newHashMap();
	    if(StringUtils.isNotBlank(returnString)){
	    	Integer start=returnString.indexOf("msg=")+"msg=".length();
	    	Integer end=returnString.indexOf("&key=");
	    	returnString = returnString.substring(start,end);
	    	Document doc = DocumentHelper.parseText(returnString);
			Element root = doc.getRootElement(); 
			Element body = (Element)root.element("body");
			Element jcgames = (Element)body.element("jcgames");
			for (Iterator i = jcgames.elementIterator("jcgame"); i.hasNext();) {
				Element jcgame= (Element) i.next();
				String name = jcgame.attributeValue("name");
				String matchID = jcgame.attributeValue("matchID");
				String hometeam = jcgame.attributeValue("hometeam");
				String guestteam = jcgame.attributeValue("guestteam");
				String matchstate = jcgame.attributeValue("matchstate");
				String matchtime = jcgame.attributeValue("matchtime");
				String polygoal = jcgame.attributeValue("polygoal");
				String privilegesType = jcgame.attributeValue("PrivilegesType");
				String dayOfWeekStr = matchID.substring(1,2);
				String lineIdOfDayStr = matchID.substring(2);
				Date matchTime = DateUtils.parseDate(matchtime, new String[] { "yyyy-MM-dd HH:mm" });
				Integer matchDate = JczqUtil.getMatchDate(dayOfWeekStr, matchTime);
				String key = JczqUtil.buildMatchKey(matchDate, Integer.valueOf(lineIdOfDayStr));
				JczqMatch matchDTO = new JczqMatch();
				for (JczqMatchDto jczqMatchDto : colorList) {
					if(jczqMatchDto.getGameName().equalsIgnoreCase(name)){
						matchDTO.setGameColor("#"+jczqMatchDto.getGameColor());
						break;
					}
				}
				matchDTO.setGameName(name);
				matchDTO.setHomeTeamName(hometeam);
				matchDTO.setGuestTeamName(guestteam);
				matchDTO.setMatchTime(matchTime);
				matchDTO.setLineId(Integer.valueOf(lineIdOfDayStr));
				matchDTO.setMatchDate(matchDate);
				matchDTO.setHandicap(Byte.valueOf(polygoal));
				matchDTO.setOpenFlag(getOpenFlagOffset(privilegesType));
				map.put(key, matchDTO);
			}
	    	
	    }
		return map;
	}
	/**
	 * 获取用二进制位的第几位表示该类型
	 * 
	 * @param privilegesType 不支持玩法
	 * @return 用二进制位的第几位表示该类型
	 * 
	 * 用英文输入法的:【逗号】如’,’分开。竞彩足球：1:胜平负单关 2:比分单关 3:进球数单关 4:半全场单关 5:胜平负过关 6:比分过关 7:进球数过关 8:半全场过关9：不让球胜平负单关 10：不让球胜平负过关
	 */
	public static int getOpenFlagOffset(String privilegesType) {
		Integer openFlag = 0;
		int len = com.cai310.lottery.support.jczq.PassMode.values().length-1;
		if(StringUtils.isNotBlank(privilegesType)){
			Map<Integer,Integer> posMap= Maps.newHashMap();
			com.cai310.lottery.support.jczq.PlayType[] playType = {
					com.cai310.lottery.support.jczq.PlayType.SPF,
					com.cai310.lottery.support.jczq.PlayType.BF,
					com.cai310.lottery.support.jczq.PlayType.JQS,
					com.cai310.lottery.support.jczq.PlayType.BQQ,
//					com.cai310.lottery.support.jczq.PlayType.RQSPF
				  };
			com.cai310.lottery.support.jczq.PassMode[] passMode = {
					com.cai310.lottery.support.jczq.PassMode.SINGLE,
					com.cai310.lottery.support.jczq.PassMode.PASS
				  };
			Integer pos = 1;
			for (com.cai310.lottery.support.jczq.PassMode pm : passMode) {
			    for (com.cai310.lottery.support.jczq.PlayType pt : playType) {
			    	posMap.put(pos, (pt.ordinal() * len + pm.ordinal()));
			    	pos++;
				}
			}
			posMap.put(9, (com.cai310.lottery.support.jczq.PlayType.RQSPF.ordinal() * len + com.cai310.lottery.support.jczq.PassMode.SINGLE.ordinal()));
			posMap.put(10, (com.cai310.lottery.support.jczq.PlayType.RQSPF.ordinal() * len + com.cai310.lottery.support.jczq.PassMode.PASS.ordinal()));
			
			String[] arr = privilegesType.split(",");
			List<Integer> unOpen = Lists.newArrayList();
			for (String flag : arr) {
				if(flag.equals("1")){
					flag="9";
				}
				else if(flag.equals("9")){
					flag="1";
				}else if(flag.equals("5")){
					flag="10";
				}else if(flag.equals("10")){
					flag="5";
				}
				unOpen.add(Integer.valueOf(flag));
			}
			for (Map.Entry<Integer,Integer> posm : posMap.entrySet()) {
				if(!unOpen.contains(posm.getKey()))
					openFlag |= 0x1 << posm.getValue();
			}
			return openFlag;
		}else{
		    //全部开赛
			com.cai310.lottery.support.jczq.PlayType[] playType = {
					com.cai310.lottery.support.jczq.PlayType.SPF,
					com.cai310.lottery.support.jczq.PlayType.JQS,
					com.cai310.lottery.support.jczq.PlayType.BF,
					com.cai310.lottery.support.jczq.PlayType.BQQ,
					com.cai310.lottery.support.jczq.PlayType.RQSPF
				  };
			com.cai310.lottery.support.jczq.PassMode[] passMode = {
					com.cai310.lottery.support.jczq.PassMode.SINGLE,
					com.cai310.lottery.support.jczq.PassMode.PASS
				  };
			for (com.cai310.lottery.support.jczq.PassMode pm : passMode) {
			    for (com.cai310.lottery.support.jczq.PlayType pt : playType) {
			    	openFlag |= 0x1 << (pt.ordinal() * len + pm.ordinal());
				}
			}
			return openFlag;
		}
	} 
	public static Map<com.cai310.lottery.support.jczq.PlayType,Map<String,Map<String, Double>>> getJczqGgSp() throws DataException, IOException, DocumentException{
		ZunaoDczcSpVisitor zunaoDczcSpVisitor = ZunaoUtil.getJczqSp("jczqgg");
		List<ZunaoDczcSp> sp = zunaoDczcSpVisitor.getZunaoDczcSpList();
		Map<com.cai310.lottery.support.jczq.PlayType,Map<String,Map<String, Double>>> rateData = Maps.newLinkedHashMap();
		Map<String,Map<String, Double>> rqsf_rateData = Maps.newLinkedHashMap();
		Map<String,Map<String, Double>> jqs_rateData = Maps.newLinkedHashMap();
		Map<String,Map<String, Double>> bf_rateData = Maps.newLinkedHashMap();
		Map<String,Map<String, Double>> bqq_rateData = Maps.newLinkedHashMap();
		Map<String,Map<String, Double>> spf_rateData = Maps.newLinkedHashMap();
		
		for (ZunaoDczcSp zunaoDczcSp : sp) {
				try {
					String dayOfWeekStr = zunaoDczcSp.getMatchId().substring(1,2);
					String lineIdOfDayStr = zunaoDczcSp.getMatchId().substring(2);
					Date matchTime = DateUtils.parseDate(zunaoDczcSp.getMatchtime(), new String[] { "yyyy-MM-dd HH:mm" });
					Integer matchDate = JczqUtil.getMatchDate(dayOfWeekStr, matchTime);
					String key = JczqUtil.buildMatchKey(matchDate, Integer.valueOf(lineIdOfDayStr));
					String spItem = zunaoDczcSp.getSp();
					if(StringUtils.isNotBlank(spItem)&&spItem.indexOf("|")!=-1){
						String[] spArr = spItem.split("\\|");
						com.cai310.lottery.support.jczq.PlayType playType = null;
						for (int i = 0; i < spArr.length; i++) {
							try {
								Map<String, Double> itemMap = Maps.newHashMap();
								int columnIndex = 0;
								if(i==0){
									playType = com.cai310.lottery.support.jczq.PlayType.RQSPF;
								}else if(i==1){
									playType = com.cai310.lottery.support.jczq.PlayType.JQS;
								}else if(i==2){
									playType = com.cai310.lottery.support.jczq.PlayType.BF;
								}else if(i==3){
									playType = com.cai310.lottery.support.jczq.PlayType.BQQ;
								}else if(i==4){
									playType = com.cai310.lottery.support.jczq.PlayType.SPF;
								}
								for (Item item : playType.getAllItems()) {
									String rateStr = spArr[i].split(",")[columnIndex];
									Double rate = (StringUtils.isNotBlank(rateStr) && rateStr.matches(DOUBLE_REGEX)) ? Double.valueOf(rateStr): 0;
									itemMap.put(item.toString(), rate);
									columnIndex++;
								}
								if(i==0){
									rqsf_rateData.put(key, itemMap);
								}else if(i==1){
									jqs_rateData.put(key, itemMap);
								}else if(i==2){
									bf_rateData.put(key, itemMap);
								}else if(i==3){
									bqq_rateData.put(key, itemMap);
								}else if(i==4){
									spf_rateData.put(key, itemMap);
								}
							} catch (Exception e) {
								continue;
							}
						}
					}
				} catch (Exception e) {
					continue;
				}
		}
		rateData.put(com.cai310.lottery.support.jczq.PlayType.RQSPF, rqsf_rateData);
		rateData.put(com.cai310.lottery.support.jczq.PlayType.JQS, jqs_rateData);
		rateData.put(com.cai310.lottery.support.jczq.PlayType.BF, bf_rateData);
		rateData.put(com.cai310.lottery.support.jczq.PlayType.BQQ, bqq_rateData);
		rateData.put(com.cai310.lottery.support.jczq.PlayType.SPF,spf_rateData);
		return rateData;
	}
	public static Map<com.cai310.lottery.support.jczq.PlayType,Map<String,Map<String, Double>>> getJczqDgSp() throws DataException, IOException, DocumentException{
		ZunaoDczcSpVisitor zunaoDczcSpVisitor = ZunaoUtil.getJczqSp("jczqdg");
		List<ZunaoDczcSp> sp = zunaoDczcSpVisitor.getZunaoDczcSpList();
		Map<com.cai310.lottery.support.jczq.PlayType,Map<String,Map<String, Double>>> rateData = Maps.newLinkedHashMap();
		Map<String,Map<String, Double>> rqsf_rateData = Maps.newLinkedHashMap();
		Map<String,Map<String, Double>> jqs_rateData = Maps.newLinkedHashMap();
		Map<String,Map<String, Double>> bf_rateData = Maps.newLinkedHashMap();
		Map<String,Map<String, Double>> bqq_rateData = Maps.newLinkedHashMap();
		Map<String,Map<String, Double>> spf_rateData = Maps.newLinkedHashMap();
		
		for (ZunaoDczcSp zunaoDczcSp : sp) {
				try {
					String dayOfWeekStr = zunaoDczcSp.getMatchId().substring(1,2);
					String lineIdOfDayStr = zunaoDczcSp.getMatchId().substring(2);
					Date matchTime = DateUtils.parseDate(zunaoDczcSp.getMatchtime(), new String[] { "yyyy-MM-dd HH:mm" });
					Integer matchDate = JczqUtil.getMatchDate(dayOfWeekStr, matchTime);
					String key = JczqUtil.buildMatchKey(matchDate, Integer.valueOf(lineIdOfDayStr));
					String spItem = zunaoDczcSp.getSp();
					if(StringUtils.isNotBlank(spItem)&&spItem.indexOf("|")!=-1){
						String[] spArr = spItem.split("\\|");
						com.cai310.lottery.support.jczq.PlayType playType = null;
						for (int i = 0; i < spArr.length; i++) {
							try{
								Map<String, Double> itemMap = Maps.newHashMap();
								int columnIndex = 0;
								if(i==0){
									playType = com.cai310.lottery.support.jczq.PlayType.RQSPF;
								}else if(i==1){
									playType = com.cai310.lottery.support.jczq.PlayType.JQS;
								}else if(i==2){
									playType = com.cai310.lottery.support.jczq.PlayType.BF;
								}else if(i==3){
									playType = com.cai310.lottery.support.jczq.PlayType.BQQ;
								}else if(i==4){
									playType = com.cai310.lottery.support.jczq.PlayType.SPF;
								}
								for (Item item : playType.getAllItems()) {
									String rateStr = spArr[i].split(",")[columnIndex];
									Double rate = (StringUtils.isNotBlank(rateStr) && rateStr.matches(DOUBLE_REGEX)) ? Double.valueOf(rateStr): 0;
									itemMap.put(item.toString(), rate);
									columnIndex++;
								}
								if(i==0){
									rqsf_rateData.put(key, itemMap);
								}else if(i==1){
									jqs_rateData.put(key, itemMap);
								}else if(i==2){
									bf_rateData.put(key, itemMap);
								}else if(i==3){
									bqq_rateData.put(key, itemMap);
								}else if(i==4){
									spf_rateData.put(key, itemMap);
								}
							} catch (Exception e) {
								continue;
							}
						}
					}
				} catch (Exception e) {
					continue;
				}
		}
		rateData.put(com.cai310.lottery.support.jczq.PlayType.RQSPF, rqsf_rateData);
		rateData.put(com.cai310.lottery.support.jczq.PlayType.JQS, jqs_rateData);
		rateData.put(com.cai310.lottery.support.jczq.PlayType.BF, bf_rateData);
		rateData.put(com.cai310.lottery.support.jczq.PlayType.BQQ, bqq_rateData);
		rateData.put(com.cai310.lottery.support.jczq.PlayType.SPF,spf_rateData);
		return rateData;
	}
	protected static final String DOUBLE_REGEX = "\\s*\\d+(\\.\\d+)?\\s*";
	public static void main(String[] args) throws NumberFormatException, DataException, IOException, DocumentException, ParseException {
		getJczqMatch(null);
		//getOpenFlagOffset();
	}
	 public static String Utf8HttpClientUtils(String url,String param){
	     String result="";
	     URL dataUrl=null;
	     HttpURLConnection httpConn =null;
	     PrintWriter out = null;
	     BufferedReader in = null;
	     try {
	    	 	dataUrl = new URL(url);
				httpConn = (HttpURLConnection) dataUrl.openConnection();
				httpConn.setDoOutput(true);
				httpConn.setDoInput(true);
				httpConn.setRequestProperty("content-type",  "text/xml");
				// 设置连接超时时间3秒
				httpConn.setConnectTimeout(new Integer(10000));
				// 设置接收超时时间3秒
				httpConn.setReadTimeout(new Integer(10000));

				out = new PrintWriter(new OutputStreamWriter(httpConn.getOutputStream(),"utf-8"));
				out.print(param);
				out.flush();
				out.close();
				in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"utf-8"));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
				in.close();

		     }catch (Exception ex) {
		    	  dataUrl=null;
		    	  httpConn =null;
			      out = null;
			      in = null;
		     }finally{
		    	  dataUrl=null;
		    	  httpConn =null;
			      out = null;
			      in = null;
			 }   
		     return result;
	}
	/*OrderID=D2010128298
	LotID=33
	LotIssue=2010008
	LotMoney=12
	LotMulti=2
	OneMoney=2
	LotCode=1|68,2,9;6|5,1,8
	Attach=投注测试*/
		public static String getCpdyjLotteryId(Ticket ticket){
			if(null==ticket)return null;
			if(null==ticket.getLotteryType())return null;
			if(Lottery.SSC.equals(ticket.getLotteryType())){
				return "ZQSSC";
			}
			if(Lottery.JCZQ.equals(ticket.getLotteryType())){
				return "42";
			}else if(Lottery.JCLQ.equals(ticket.getLotteryType())){
				return "43";
			}else if(Lottery.WELFARE3D.equals(ticket.getLotteryType())){
				return "3D";
			}else if(Lottery.DLT.equals(ticket.getLotteryType())){
				return "DLT";
			}else if(Lottery.SSQ.equals(ticket.getLotteryType())){
				return "SSQ";
			}else if(Lottery.SFZC.equals(ticket.getLotteryType())){
				com.cai310.lottery.support.zc.PlayType playType = com.cai310.lottery.support.zc.PlayType.values()[ticket.getBetType()];
				if(playType.equals(com.cai310.lottery.support.zc.PlayType.SFZC14)){
					return "14CSF";
				}else if(playType.equals(com.cai310.lottery.support.zc.PlayType.SFZC9)){
					return "SFR9";
				}
				
			}else if(Lottery.SCZC.equals(ticket.getLotteryType())){
				return "4CJQ";
			}else if(Lottery.LCZC.equals(ticket.getLotteryType())){
				return "6CBQ";
			}else if(Lottery.SDEL11TO5.equals(ticket.getLotteryType())){
				return "SD11X5";
			}else if(Lottery.EL11TO5.equals(ticket.getLotteryType())){
				return "23009";
			}else if(Lottery.PL.equals(ticket.getLotteryType())){
				com.cai310.lottery.support.pl.PlPlayType playType = com.cai310.lottery.support.pl.PlPlayType.values()[ticket.getBetType()];
				if(playType.equals(com.cai310.lottery.support.pl.PlPlayType.P5Direct)){
					return "PL5";
				}else{
					return "Pl3";
				}
			}else if(Lottery.SEVEN.equals(ticket.getLotteryType())){
				return "QLC";
			}else if(Lottery.DCZC.equals(ticket.getLotteryType())){
				com.cai310.lottery.support.dczc.PlayType playType = com.cai310.lottery.support.dczc.PlayType.values()[ticket.getBetType()];
				if(PlayType.SPF.equals(playType)){
					return "SPF";
				}else if(PlayType.ZJQS.equals(playType)){
					return "JQS";
				}else if(PlayType.BF.equals(playType)){
					return "BF";
				}else if(PlayType.BQQSPF.equals(playType)){
					return "BQC";
				}else if(PlayType.SXDS.equals(playType)){
					return "SXDS";
				}
			}else if(Lottery.SEVENSTAR.equals(ticket.getLotteryType())){
				return "QXC";
			}else if(Lottery.TC22TO5.equals(ticket.getLotteryType())){
				return "22X5";
			}
			return null;
		}
	
	protected Map<String, Object> map;
	public ZunaoUtil(Ticket ticket){
		if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(null!=ticket&&null!=ticket.getContent())map = JsonUtil.getMap4Json(ticket.getContent());
		}
	}
	public ZunaoUtil(){
		
	}
	public Map<String, Object> getTicketContentMap(Ticket ticket){
		if(null!=map)return map;
		if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(null!=ticket&&null!=ticket.getContent())map = JsonUtil.getMap4Json(ticket.getContent());
		}
		return map;
	}
	protected List<String> formatBetNum(List<String> numList,NumberFormat nf){
		List<String> newNumList = Lists.newArrayList();
		for (String num : numList) {
			newNumList.add(nf.format(Integer.valueOf(num)));
		}
		return newNumList;
	}
	
}
