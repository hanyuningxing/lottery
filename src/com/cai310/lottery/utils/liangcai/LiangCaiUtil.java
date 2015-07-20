package com.cai310.lottery.utils.liangcai;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.lottery.ticket.common.SecurityUtil;
import com.cai310.lottery.ticket.protocol.response.dto.IssueInfo;
import com.cai310.utils.MD5;
import com.cai310.utils.DateUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;

public class LiangCaiUtil {
	protected static Logger logger = LoggerFactory.getLogger(LiangCaiUtil.class);
//	private static final String reUrl="http://t.zc310.net:8089/bin/LotSaleHttp.dll";//ceshi
//	private static final String wAgentValue="3821";
//	private static final String key="a8b8c8d8e8f8g8h8";
//	private static final String id_p="qiu111";
	private static final String reUrl="http://i.zc310.net:8089/bin/LotSaleHttp.dll";
	private static final String wAgentValue="832795";
	private static final String key="cndhfhrn";///key这里留空。根据实制填
	private static final String id_p="qiu112";
	public static LiangResultVisitor getIssue(Lottery lottery,Byte betType) throws DataException, IOException, DocumentException{
		Ticket ticket = new Ticket();
		ticket.setBetType(betType);
		ticket.setLotteryType(lottery);
		String LotID = getLiangCaiLotteryId(ticket);
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("LotID="+LotID.trim());
		String wParamValue=wParamValueSb.toString();
		String wMsgIDValue = System.currentTimeMillis()+"";
		StringBuffer sb = new StringBuffer();
		String wActionValue = "104";
		sb.append(reUrl);
		
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("wAgent", wAgentValue);
		ParamMap.put("wAction", wActionValue);
		ParamMap.put("wMsgID", wMsgIDValue);
		ParamMap.put("wSign",SecurityUtil.md5((wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).getBytes("GBK")).toLowerCase());
		ParamMap.put("wParam", wParamValue);
		String returnString = HttpclientUtil.GBKHttpClientUtils(sb.toString(), ParamMap);
		Document doc=DocumentHelper.parseText(returnString);
		LiangResultVisitor cpResultVisitor=new LiangResultVisitor();
		doc.accept(cpResultVisitor);
		return cpResultVisitor;
	}
	public static LiangNumResultVisitor getResult(Lottery lottery,Byte betType,String LotIssue) throws DataException, IOException, DocumentException{
		Ticket ticket = new Ticket();
		ticket.setBetType(betType);
		ticket.setLotteryType(lottery);
		String LotID = getLiangCaiLotteryId(ticket);
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("LotID="+LotID.trim());
		wParamValueSb.append("_LotIssue="+LotIssue.trim());
		String wParamValue=wParamValueSb.toString();
		String wMsgIDValue = System.currentTimeMillis()+"";
		StringBuffer sb = new StringBuffer();
		String wActionValue = "110";
		sb.append(reUrl);
		
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("wAgent", wAgentValue);
		ParamMap.put("wAction", wActionValue);
		ParamMap.put("wMsgID", wMsgIDValue);
		ParamMap.put("wSign", SecurityUtil.md5((wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).getBytes("GBK")).toLowerCase());
		ParamMap.put("wParam", wParamValue);
		String returnString = HttpclientUtil.GBKHttpClientUtils(sb.toString(), ParamMap);
		Document doc=DocumentHelper.parseText(returnString);
		LiangNumResultVisitor liangNumResultVisitor=new LiangNumResultVisitor();
		doc.accept(liangNumResultVisitor);
		return liangNumResultVisitor;
	}
	
	public static IssueInfo LiangFecthPeriodNumber(Lottery lottery) throws ClientProtocolException, IOException, DocumentException{
		Ticket ticket = new Ticket();
		ticket.setLotteryType(lottery);
		String LotID ="";
		if(lottery.equals(Lottery.PL)){
			LotID="33";
		}else if(lottery.equals(Lottery.SFZC)){
			LotID="11";
		}else{
			LotID=getLiangCaiLotteryId(ticket);
		}
		if(LotID==null){
			return null;
		}
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("LotID="+LotID.trim());
		String wParamValue=wParamValueSb.toString();
		String wMsgIDValue = System.currentTimeMillis()+"";
		StringBuffer sb = new StringBuffer();
		String wActionValue = "104";
		sb.append(reUrl);
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("wAgent", wAgentValue);
		ParamMap.put("wAction", wActionValue);
		ParamMap.put("wMsgID", wMsgIDValue);
		ParamMap.put("wSign", MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
		ParamMap.put("wParam", wParamValue);
		String returnString = HttpclientUtil.Utf8HttpClientUtils(sb.toString(), ParamMap);
		if(returnString==null){
			return null;
		}
		Document doc=DocumentHelper.parseText(returnString);
		LiangResultVisitor liangResultVisitor=new LiangResultVisitor();
		doc.accept(liangResultVisitor);
		if(liangResultVisitor.getIsSuccess()){
			IssueInfo issueInfo=new IssueInfo();
			Date endTime=DateUtil.strToDate(liangResultVisitor.getEndTime(),"yyyy-MM-dd hh:mm:ss");
			Date singleEndTime=DateUtil.strToDate(liangResultVisitor.getSingleEndTime(),"yyyy-MM-dd hh:mm:ss");
			Date compoundEndTime=DateUtil.strToDate(liangResultVisitor.getCompoundEndTime(),"yyyy-MM-dd hh:mm:ss");
			issueInfo.setCompoundEndTime(getBeforeMinute(compoundEndTime));
			issueInfo.setEndTime(getBeforeMinute(endTime));
			issueInfo.setSingleEndTime(getBeforeMinute(singleEndTime));
			String periodNumber=null;
			if(lottery.equals(Lottery.SSQ)||lottery.equals(Lottery.WELFARE3D)||lottery.equals(Lottery.SEVEN)||lottery.equals(Lottery.DCZC)){
				periodNumber=liangResultVisitor.getIssue();
			}else{
				periodNumber=liangResultVisitor.getIssue().substring(2);
			}
			issueInfo.setGameIssue(periodNumber);
			return issueInfo;
		}else{
			return null;
		}
	}
	
	private static Date getBeforeMinute(Date time) {
		Calendar c = Calendar.getInstance();  
		c.setTime(time);
		c.add(Calendar.MINUTE, -5);
		return c.getTime();
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws DataException, IOException, DocumentException {
		LiangCaiUtil cpdyjUtil = new LiangCaiUtil();
		LiangNumResultVisitor liangNumResultVisitor=new LiangNumResultVisitor();
		liangNumResultVisitor=cpdyjUtil.getResult(Lottery.KLPK, Byte.valueOf("0"), "");
		System.out.println(liangNumResultVisitor.getResult());
//		LiangResultVisitor cpResultVisitor=new LiangResultVisitor();
//		cpResultVisitor=cpdyjUtil.getIssue(Lottery.KLPK, Byte.valueOf("0"));
//		System.out.println(cpResultVisitor);
	}
	public static LiangQueryPVisitor confirmTicket(Ticket ticket) throws DataException, IOException, DocumentException{
		if(null==ticket)return null;
		String OrderID = ticket.getId()+id_p;
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("OrderID="+OrderID.trim());
		String wParamValue=wParamValueSb.toString();
		String wMsgIDValue = System.currentTimeMillis()+"";
		StringBuffer sb = new StringBuffer();
		String wActionValue = "102";
		sb.append(reUrl);
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("wAgent", wAgentValue);
		ParamMap.put("wAction", wActionValue);
		ParamMap.put("wMsgID", wMsgIDValue);
		ParamMap.put("wSign", MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
		ParamMap.put("wParam", wParamValue);
		
		
//		sb.append("?");
//		sb.append(wAgent+"="+wAgentValue);
//		sb.append("&");
//		sb.append(wAction+"="+wActionValue);
//		sb.append("&");
//		sb.append(wMsgID+"="+wMsgIDValue);
//		sb.append("&");
//		sb.append(wSign+"="+MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
//		sb.append("&");
//		sb.append(wParam+"="+wParamValue);
		String returnString = HttpclientUtil.Utf8HttpClientUtils(sb.toString(), ParamMap);
		Document doc=DocumentHelper.parseText(returnString);
		LiangQueryPVisitor queryPVisitor=new LiangQueryPVisitor();
		doc.accept(queryPVisitor);
		return queryPVisitor;
	}
	
	public static LiangQueryPVisitor confirmTicket_jc(Ticket ticket) throws DataException, IOException, DocumentException{
		if(null==ticket)return null;
		String OrderID = ticket.getId()+id_p;
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("OrderID="+OrderID.trim());
		String wParamValue=wParamValueSb.toString();
		String wMsgIDValue = System.currentTimeMillis()+"";
		StringBuffer sb = new StringBuffer();
		String wActionValue = "120";
		sb.append(reUrl);
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("wAgent", wAgentValue);
		ParamMap.put("wAction", wActionValue);
		ParamMap.put("wMsgID", wMsgIDValue);
		ParamMap.put("wSign", MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
		ParamMap.put("wParam", wParamValue);
		
		
//		sb.append("?");
//		sb.append(wAgent+"="+wAgentValue);
//		sb.append("&");
//		sb.append(wAction+"="+wActionValue);
//		sb.append("&");
//		sb.append(wMsgID+"="+wMsgIDValue);
//		sb.append("&");
//		sb.append(wSign+"="+MD5.md5(wAgentValue+wActionValue+wMsgIDValue+wParamValue+key).toLowerCase());
//		sb.append("&");
//		sb.append(wParam+"="+wParamValue);
		String returnString = HttpclientUtil.Utf8HttpClientUtils(sb.toString(), ParamMap);
		Document doc=DocumentHelper.parseText(returnString);
		JcQueryPVisitor queryPVisitor=new JcQueryPVisitor();
		doc.accept(queryPVisitor);
		return queryPVisitor;
	}
	/*OrderID=D2010128298
	LotID=33
	LotIssue=2010008
	LotMoney=12
	LotMulti=2
	OneMoney=2
	LotCode=1|68,2,9;6|5,1,8
	Attach=投注测试*/
	public static String getLiangCaiLotteryId(Ticket ticket){
		if(null==ticket)return null;
		if(null==ticket.getLotteryType())return null;
		if(Lottery.SSC.equals(ticket.getLotteryType())){
			return "10401";
		}
		if(Lottery.JCZQ.equals(ticket.getLotteryType())){
			return "42";
		}else if(Lottery.JCLQ.equals(ticket.getLotteryType())){
			return "43";
		}else if(Lottery.WELFARE3D.equals(ticket.getLotteryType())){
			return "52";
		}else if(Lottery.DLT.equals(ticket.getLotteryType())){
			return "23529";
		}else if(Lottery.SSQ.equals(ticket.getLotteryType())){
			return "51";
		}else if(Lottery.SFZC.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.zc.PlayType playType = com.cai310.lottery.support.zc.PlayType.values()[ticket.getBetType()];
			if(playType.equals(com.cai310.lottery.support.zc.PlayType.SFZC14)){
				return "11";
			}else if(playType.equals(com.cai310.lottery.support.zc.PlayType.SFZC9)){
				return "19";
			}
		}else if(Lottery.SCZC.equals(ticket.getLotteryType())){
			return "18";
		}else if(Lottery.LCZC.equals(ticket.getLotteryType())){
			return "16";
		}else if(Lottery.EL11TO5.equals(ticket.getLotteryType())){
			return "23009";
		}else if(Lottery.SDEL11TO5.equals(ticket.getLotteryType())){
			return "21406";
		}else if(Lottery.PL.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.pl.PlPlayType playType = com.cai310.lottery.support.pl.PlPlayType.values()[ticket.getBetType()];
			if(playType.equals(com.cai310.lottery.support.pl.PlPlayType.P5Direct)){
				return "35";
			}else{
				return "33";
			}
		}else if(Lottery.SEVEN.equals(ticket.getLotteryType())){
			return "23528";
		}else if(Lottery.DCZC.equals(ticket.getLotteryType())){
			return "41";
		}else if(Lottery.SEVENSTAR.equals(ticket.getLotteryType())){
			return "10022";
		}else if(Lottery.TC22TO5.equals(ticket.getLotteryType())){
			return "23525";
		}else if(Lottery.KLPK.equals(ticket.getLotteryType())){
			return "20410";
		}
		return null;
	}
	
	protected Map<String, Object> map;
	public LiangCaiUtil(Ticket ticket){
		if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(null!=ticket&&null!=ticket.getContent())map = JsonUtil.getMap4Json(ticket.getContent());
		}
	}
	public LiangCaiUtil(){
		
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
