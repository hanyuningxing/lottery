package com.cai310.lottery.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.lottery.ticket.common.SecurityUtil;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.MD5;
import com.google.common.collect.Lists;

public class RlygUtil {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private static final String reUrl="http://116.213.75.179:8080/billservice/sltAPI";
	private static final String realname ="hongbo";
	private static final String username ="hongbo";
	private static final String mobile ="";
	private static final String email ="";
	private static final String cardtype = "1";
	private static final String cardno = "";
	private static final String agentID = "800053";
	private static final String AgentPwd ="s8dugf7r9mqp";
	private static final String prefix ="";
	/*OrderID=D2010128298
	LotID=33
	LotIssue=2010008
	LotMoney=12
	LotMulti=2
	OneMoney=2
	LotCode=1|68,2,9;6|5,1,8
	Attach=投注测试*/
	public static String getRlygLotteryId(Ticket ticket){
		if(null==ticket)return null;
		if(null==ticket.getLotteryType())return null;
		if(Lottery.SSQ.equals(ticket.getLotteryType())){
			return "001";
		}else if(Lottery.DLT.equals(ticket.getLotteryType())){
			DltPlayType dltPlayType = DltPlayType.values()[ticket.getBetType()];
			if(dltPlayType.equals(DltPlayType.Select12to2)){//12选2
				return "114";
			}else{
				return "113";
			}
		}else if(Lottery.WELFARE3D.equals(ticket.getLotteryType())){
			return "002";
		}else if(Lottery.LCZC.equals(ticket.getLotteryType())){
			return "115";
		}else if(Lottery.SCZC.equals(ticket.getLotteryType())){
			return "116";
		}else if(Lottery.PL.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.pl.PlPlayType playType = com.cai310.lottery.support.pl.PlPlayType.values()[ticket.getBetType()];
			if(playType.equals(com.cai310.lottery.support.pl.PlPlayType.P5Direct)){
				return "109";
			}else{
				return "108";
			}
		}else if(Lottery.SFZC.equals(ticket.getLotteryType())){
			com.cai310.lottery.support.zc.PlayType playType = com.cai310.lottery.support.zc.PlayType.values()[ticket.getBetType()];
			if(playType.equals(com.cai310.lottery.support.zc.PlayType.SFZC14)){
				return "117";
			}else if(playType.equals(com.cai310.lottery.support.zc.PlayType.SFZC9)){
				return "118";
			}
		}else if(Lottery.SDEL11TO5.equals(ticket.getLotteryType())){
			return "107";
		}else if(Lottery.SSC.equals(ticket.getLotteryType())){
			return "018";
		}else if(Lottery.SEVEN.equals(ticket.getLotteryType())){
			return "003";
		}
		else if(Lottery.KLPK.equals(ticket.getLotteryType())){
			return "003";
		}
		return null;
	}
	
	protected Map<String, Object> map;
	public RlygUtil(Ticket ticket){
		if(SalesMode.COMPOUND.equals(ticket.getMode())){
			if(null!=ticket&&null!=ticket.getContent())map = JsonUtil.getMap4Json(ticket.getContent());
		}
	}
	public RlygUtil(){
		
	}
	public Map<String, Object> getTicketContentMap(Ticket ticket){
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
	public static void main(String[] args) throws NumberFormatException, ClientProtocolException, IOException, DocumentException {
		RlygQueryPVisitor rlygQueryPVisitor1 = RlygUtil.getIssue(Lottery.SSC, Byte.valueOf("0"), "120710044");
	}
	public static RlygQueryPVisitor getIssue(Lottery lottery,Byte betType) throws ClientProtocolException, IOException, DocumentException {
		Ticket ticket = new Ticket();
		ticket.setLotteryType(lottery);
		ticket.setBetType(betType);
		StringBuffer bodyValueSb = new StringBuffer();
		String cmd = "2000";
		bodyValueSb.append("<body>");
		bodyValueSb.append("<loto lotoid=\""+getRlygLotteryId(ticket)+"\" issue=\"\"/>");
		bodyValueSb.append("</body>"); 
		
		String md5 = SecurityUtil.md5((agentID+AgentPwd+bodyValueSb.toString()).getBytes("UTF-8")).toLowerCase();
		
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
		wParamValueSb.append("<msg v=\"1.0\" id=\""+System.currentTimeMillis()+"\"><ctrl><agentID>"+agentID+"</agentID><cmd>"+cmd+"</cmd><timestamp>"+System.currentTimeMillis()+"</timestamp><md>"+md5+"</md></ctrl>");
		wParamValueSb.append(bodyValueSb.toString());
		wParamValueSb.append("</msg>");
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("cmd", cmd);
		ParamMap.put("msg", wParamValueSb.toString());
		String returnString = HttpclientUtil.Utf8HttpClientUtils(reUrl, ParamMap);
	    if(StringUtils.isNotBlank(returnString)){
			Document doc=DocumentHelper.parseText(returnString);
			Element root = doc.getRootElement();
			Element body = root.element("body");
		    Element response = body.element("response");
		    Element issuequery = response.element("issuequery");
		    RlygQueryPVisitor queryPVisitor=new RlygQueryPVisitor();
		    issuequery.element("issue").accept(queryPVisitor);
			return queryPVisitor;
	    }
		
		return null;
	}
	public static RlygQueryPVisitor getIssue(Lottery lottery,Byte betType,String issue) throws ClientProtocolException, IOException, DocumentException {
		Ticket ticket = new Ticket();
		ticket.setLotteryType(lottery);
		ticket.setBetType(betType);
		StringBuffer bodyValueSb = new StringBuffer();
		String cmd = "2000";
		bodyValueSb.append("<body>");
		bodyValueSb.append("<loto lotoid=\""+getRlygLotteryId(ticket)+"\" issue=\""+issue+"\"/>");
		bodyValueSb.append("</body>"); 
		
		String md5 = SecurityUtil.md5((agentID+AgentPwd+bodyValueSb.toString()).getBytes("UTF-8")).toLowerCase();
		
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
		wParamValueSb.append("<msg v=\"1.0\" id=\""+System.currentTimeMillis()+"\"><ctrl><agentID>"+agentID+"</agentID><cmd>"+cmd+"</cmd><timestamp>"+System.currentTimeMillis()+"</timestamp><md>"+md5+"</md></ctrl>");
		wParamValueSb.append(bodyValueSb.toString());
		wParamValueSb.append("</msg>");
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("cmd", cmd);
		ParamMap.put("msg", wParamValueSb.toString());
		String returnString = HttpclientUtil.Utf8HttpClientUtils(reUrl, ParamMap);
	    if(StringUtils.isNotBlank(returnString)){
			Document doc=DocumentHelper.parseText(returnString);
			Element root = doc.getRootElement();
			Element body = root.element("body");
		    Element response = body.element("response");
		    Element issuequery = response.element("issuequery");
		    RlygQueryPVisitor queryPVisitor=new RlygQueryPVisitor();
		    issuequery.element("issue").accept(queryPVisitor);
			return queryPVisitor;
	    }
		
		return null;
	}
	public static RlygQueryPVisitor confirmTicket(Ticket ticket) throws ClientProtocolException, IOException, DocumentException {
		StringBuffer bodyValueSb = new StringBuffer();
		String cmd = "2015";
		bodyValueSb.append("<body>");
		bodyValueSb.append("<order merchantid=\""+agentID+"\" orderno=\""+prefix+ticket.getLotteryType().getKey()+ticket.getId()+"\"/>");
		bodyValueSb.append("</body>"); 
		
		String md5 = SecurityUtil.md5((agentID+AgentPwd+bodyValueSb.toString()).getBytes("UTF-8")).toLowerCase();
		
		StringBuffer wParamValueSb = new StringBuffer();
		wParamValueSb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); 
		wParamValueSb.append("<msg v=\"1.0\" id=\""+System.currentTimeMillis()+"\"><ctrl><agentID>"+agentID+"</agentID><cmd>"+cmd+"</cmd><timestamp>"+System.currentTimeMillis()+"</timestamp><md>"+md5+"</md></ctrl>");
		wParamValueSb.append(bodyValueSb.toString());
		wParamValueSb.append("</msg>");
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("cmd", cmd);
		ParamMap.put("msg", wParamValueSb.toString());
		String returnString = HttpclientUtil.Utf8HttpClientUtils(reUrl, ParamMap);
	    if(StringUtils.isNotBlank(returnString)){
			Document doc=DocumentHelper.parseText(returnString);
			Element root = doc.getRootElement();
			Element body = root.element("body");
		    Element response = body.element("response");
		    Element order = response.element("order");
		    RlygQueryPVisitor queryPVisitor=new RlygQueryPVisitor();
		    order.element("ticket").accept(queryPVisitor);
			return queryPVisitor;
	    }
		
		return null;
	}
}
