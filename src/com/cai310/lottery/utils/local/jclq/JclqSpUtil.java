package com.cai310.lottery.utils.local.jclq;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.jclq.ItemDXF;
import com.cai310.lottery.support.jclq.ItemRFSF;
import com.cai310.lottery.support.jclq.ItemSF;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.JclqCompoundContent;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.lottery.ticket.common.TicketConstant;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOdds;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.utils.dto.JclqItem;
import com.cai310.lottery.utils.dto.JclqPrintItemObj;
import com.cai310.lottery.utils.dto.JclqSpItemObj;
import com.cai310.lottery.utils.dto.JclqSpItem;
import com.cai310.lottery.utils.local.SpConfig;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public  class JclqSpUtil{
	public static JcMatchOddsList parseResponseSp_local(JclqScheme jclqScheme) throws ClientProtocolException, IOException{
		PlayType playType = jclqScheme.getPlayType();

		JclqSpItemObj jclqSpItemObj = new JclqSpItemObj();
		List <JclqSpItem> awardList = Lists.newArrayList();
		
		
		Map<String, Double> awardMap;
		Map<String,Map<String, Double>> spMap = Maps.newHashMap();
		for(JclqSpItem jclqSpItem:jclqSpItemObj.getAwardList()){
			if(PlayType.MIX.equals(playType)){
				Map<String,String> ParamMap=new LinkedHashMap<String,String>();
				StringBuffer sb = new StringBuffer();
				for(JclqMatchItem jclqMatchItem:jclqScheme.getCompoundContent().getItems()){
					sb.append(jclqMatchItem.getMatchKey()+",");
					ParamMap.put("playType", jclqMatchItem.getPlayType().name());
					ParamMap.put("matchKey",sb.delete(sb.length()-1,sb.length()).toString());
					String returnString = HttpclientUtil.Utf8HttpClientUtils(SpConfig.SP_HOST+"/jclq/scheme!matchOdds.action",ParamMap);
					returnString = returnString.substring(returnString.indexOf("<odds>")+"<odds>".length(),returnString.indexOf("</odds>"));
					Map<String, Object> matchRates = JsonUtil.getMap4Json(returnString);
					awardList.add(getAwardValueByRateItem(matchRates,jclqMatchItem,jclqMatchItem.getPlayType()));
				}
				jclqSpItemObj.setAwardList(awardList);
			}else{
				Map<String,String> ParamMap=new LinkedHashMap<String,String>();
				ParamMap.put("playType", playType.name());
				StringBuffer sb = new StringBuffer();
				for(JclqMatchItem jclqMatchItem:jclqScheme.getCompoundContent().getItems()){
					sb.append(jclqMatchItem.getMatchKey()+",");
				}
				ParamMap.put("matchKey",sb.delete(sb.length()-1,sb.length()).toString());
				String returnString = HttpclientUtil.Utf8HttpClientUtils(SpConfig.SP_HOST+"/jclq/scheme!matchOdds.action",ParamMap);
				returnString = returnString.substring(returnString.indexOf("<odds>")+"<odds>".length(),returnString.indexOf("</odds>"));
				Map<String, Object> matchRates = JsonUtil.getMap4Json(returnString);
				for(JclqMatchItem jclqMatchItem:jclqScheme.getCompoundContent().getItems()){
					awardList.add(getAwardValueByRateItem(matchRates,jclqMatchItem,playType));
				}
				jclqSpItemObj.setAwardList(awardList);
			}
			
			String matchKey = "20"+jclqSpItem.getIntTime()+"-"+JclqUtil.formatLineId(jclqSpItem.getLineId());
			awardMap = spMap.get(matchKey);
			for (JclqItem jclqItem : jclqSpItem.getOptions()) {
				if(null==awardMap||awardMap.isEmpty()){
					awardMap = Maps.newHashMap();
					awardMap.put(getAwardValueBySpText(jclqItem.getValue(), playType),jclqItem.getAward());
					awardMap.put(JclqConstant.REFERENCE_VALUE_KEY,jclqSpItem.getReferenceValue());
				}else{
					awardMap.put(getAwardValueBySpText(jclqItem.getValue(), playType),jclqItem.getAward());
					awardMap.put(JclqConstant.REFERENCE_VALUE_KEY,jclqSpItem.getReferenceValue());
				}
			}
			spMap.put(matchKey, awardMap);
		}
		JcMatchOddsList  jcMatchOddsList =  JclqUtil.bulidPrintAwardMap(spMap);
		return jcMatchOddsList;
	}
	public static JclqSpItem getAwardValueByRateItem(Map<String,Object> matchRates,JclqMatchItem jclqMatchItem,PlayType playType){
		JclqItem jclqItem = null;
		JclqSpItem jclqSpItem  =  new JclqSpItem();
		jclqSpItem.setIntTime(Integer.valueOf((jclqMatchItem.getMatchKey().split("-")[0]).substring(2)));
		jclqSpItem.setLineId(Integer.valueOf(jclqMatchItem.getMatchKey().split("-")[1]));
		List<JclqItem> options  = Lists.newArrayList(); 
		RateItem rateItem = null;
		Map<String, Object> map = JsonUtil.getMap4Json(String.valueOf(matchRates.get(jclqMatchItem.getMatchKey())));
		Map<String, RateItem> matchRate = Maps.newHashMap();
		for (String key : map.keySet()) {
			Map<String, Object> obj = JsonUtil.getMap4Json(String.valueOf(map.get(key)));
			rateItem = new RateItem();
			rateItem.setKey(key);
			rateItem.setValue(Double.valueOf(""+obj.get("value")));
			matchRate.put(key, rateItem);
		}
		if(playType.equals(PlayType.RFSF)||playType.equals(PlayType.DXF)){
			rateItem = matchRate.get("REDUCED_VALUE");
			if(null!=rateItem)jclqSpItem.setReferenceValue(rateItem.getValue());
		}else{
			jclqSpItem.setReferenceValue(0d);
		}
		switch (playType) {
		case SF:
			for (ItemSF type : ItemSF.values()) {
				int v = 1 << type.ordinal();
				if ((jclqMatchItem.getValue() & v) > 0) {
					jclqItem = new JclqItem();
					jclqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jclqItem.setAward(rateItem.getValue());
					options.add(jclqItem);
				}
			}
			break;
		case RFSF:
			for (ItemRFSF type : ItemRFSF.values()) {
				int v = 1 << type.ordinal();
				if ((jclqMatchItem.getValue() & v) > 0) {
					jclqItem = new JclqItem();
					jclqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jclqItem.setAward(rateItem.getValue());
					options.add(jclqItem);
				}
			}
			
			break;
		case SFC:
			for (ItemSFC type : ItemSFC.values()) {
				int v = 1 << type.ordinal();
				if ((jclqMatchItem.getValue() & v) > 0) {
					jclqItem = new JclqItem();
					jclqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jclqItem.setAward(rateItem.getValue());
					options.add(jclqItem);
				}
			}
			break;
		case DXF:
			for (ItemDXF type : ItemDXF.values()) {
				int v = 1 << type.ordinal();
				if ((jclqMatchItem.getValue() & v) > 0) {
					jclqItem = new JclqItem();
					jclqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jclqItem.setAward(rateItem.getValue());
					options.add(jclqItem);
				}
			}
			break;
		default:
			throw new RuntimeException("玩法不正确.");
		}
		jclqSpItem.setOptions(options);
		return jclqSpItem;
	}
	public static String getAwardValueBySpText(String betValue,PlayType playType){
		String value = null;
		switch (playType) {
		case SF:
			for (ItemSF type : ItemSF.values()) {
				if(betValue.equals(type.getValue())){
					value = type.getValue();
				}
			}
			break;
		case RFSF:
			for (ItemRFSF type : ItemRFSF.values()) {
				if(betValue.equals(type.getValue())){
					value = type.getValue();
				}
			}
			break;
		case SFC:
			for (ItemSFC type : ItemSFC.values()) {
				if(betValue.equals(type.getValue())){
					value = type.getValue();
				}
			}
			break;
		case DXF:
			for (ItemDXF type : ItemDXF.values()) {
				if(betValue.equals(type.getValue())){
					value = type.getValue();
				}
			}
			break;
		default:
			throw new RuntimeException("玩法不正确.");
		}
		return value;
	}
	
}
