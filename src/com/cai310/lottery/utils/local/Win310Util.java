package com.cai310.lottery.utils.local;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONArray;

import org.dom4j.DocumentException;

import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.lottery.ticket.common.SecurityUtil;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

public class Win310Util {
	/**
	 * 奖金重置
	 * 
	 * @param ticket
	 * @return
	 */
	private static final String key="qwerty";
	private static final String reUrl="http://192.168.15.250/Api";
	
	public static CpResult ResetPrize(List<Ticket> ticketList)throws DataException, IOException, DocumentException {
		List<Map<String,String>> list = Lists.newArrayList();
		for (Ticket ticket : ticketList) {
			Map<String,String> paramMap=new LinkedHashMap<String,String>();
			String TicketId=""+ticket.getId();
//			String TicketPrize=ticket.getTotalPrizeAfterTax().toString();
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
			String OperateTime = sdFormat.format(ticket.getSendTime());
			paramMap.put("TicketId", TicketId);
			paramMap.put("Prize", "");
			paramMap.put("PrizeDescription", "");
			paramMap.put("OperateTime", OperateTime);
			list.add(paramMap);
		}
		String parame =JSONArray.fromObject(list).toString();
		String version ="1";
		String transCode = "904";
		
		Map<String,String> ParamMap=new LinkedHashMap<String,String>();
		ParamMap.put("TransCode", transCode);
		ParamMap.put("Version", version);
		ParamMap.put("Parame", parame);
		ParamMap.put("Key", SecurityUtil.md5((transCode+version+parame+key).getBytes("UTF-8")).toLowerCase());
		String returnString = HttpclientUtil.Utf8HttpClientUtils(reUrl, ParamMap);
		Gson gson = new Gson();
		CpResult cpResult = gson.fromJson(returnString, CpResult.class);
		return cpResult;
	}
}
