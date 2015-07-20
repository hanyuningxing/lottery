package com.cai310.lottery.utils.local.jczq;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.jczq.JczqCompoundContent;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.ItemBF;
import com.cai310.lottery.support.jczq.ItemBQQ;
import com.cai310.lottery.support.jczq.ItemJQS;
import com.cai310.lottery.support.jczq.ItemSPF;
import com.cai310.lottery.support.jczq.JczqMatchItem;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.lottery.ticket.common.TicketConstant;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOdds;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.lottery.utils.dto.JczqItem;
import com.cai310.lottery.utils.dto.JczqPrintItemObj;
import com.cai310.lottery.utils.dto.JczqSpItemObj;
import com.cai310.lottery.utils.dto.JczqSpItem;
import com.cai310.lottery.utils.local.SpConfig;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public  class JczqSpUtil{
	public static JcMatchOddsList parseResponseSp_local(List<JczqMatchItem> items,PlayType playType) throws ClientProtocolException, IOException{
		JczqSpItemObj jczqSpItemObj = new JczqSpItemObj();
		List <JczqSpItem> awardList = Lists.newArrayList();
		if(PlayType.MIX.equals(playType)){
			Map<String,String> ParamMap=new LinkedHashMap<String,String>();
			for(JczqMatchItem jczqMatchItem:items){
				StringBuffer sb = new StringBuffer();
				sb.append(jczqMatchItem.getMatchKey()+",");
				ParamMap.put("playType", jczqMatchItem.getPlayType().name());
				ParamMap.put("matchKey",sb.delete(sb.length()-1,sb.length()).toString());
				String returnString = HttpclientUtil.Utf8HttpClientUtils(SpConfig.SP_HOST+"/jczq/scheme!matchOdds.action",ParamMap);
				returnString = returnString.substring(returnString.indexOf("<odds>")+"<odds>".length(),returnString.indexOf("</odds>"));
				Map<String, Object> matchRates = JsonUtil.getMap4Json(returnString);
				awardList.add(getAwardValueByRateItem(matchRates,jczqMatchItem,jczqMatchItem.getPlayType()));
			}
			jczqSpItemObj.setAwardList(awardList);
		}else{
			Map<String,String> ParamMap=new LinkedHashMap<String,String>();
			ParamMap.put("playType", playType.name());
			StringBuffer sb = new StringBuffer();
			for(JczqMatchItem jczqMatchItem:items){
				sb.append(jczqMatchItem.getMatchKey()+",");
			}
			ParamMap.put("matchKey",sb.delete(sb.length()-1,sb.length()).toString());
			String returnString = HttpclientUtil.Utf8HttpClientUtils(SpConfig.SP_HOST+"/jczq/scheme!matchOdds.action",ParamMap);
			returnString = returnString.substring(returnString.indexOf("<odds>")+"<odds>".length(),returnString.indexOf("</odds>"));
			Map<String, Object> matchRates = JsonUtil.getMap4Json(returnString);
			for(JczqMatchItem jczqMatchItem:items){
				awardList.add(getAwardValueByRateItem(matchRates,jczqMatchItem,playType));
			}
			jczqSpItemObj.setAwardList(awardList);
		}
		
		Map<String, Double> awardMap;
		Map<String,Map<String, Double>> spMap = Maps.newHashMap();
		for(JczqSpItem jczqSpItem:jczqSpItemObj.getAwardList()){
			String matchKey = "20"+jczqSpItem.getIntTime()+"-"+JczqUtil.formatLineId(jczqSpItem.getLineId());
			awardMap = spMap.get(matchKey);
			for (JczqItem jczqItem : jczqSpItem.getOptions()) {
				if(PlayType.MIX.equals(playType)){
					if(null==awardMap||awardMap.isEmpty()){
						awardMap = Maps.newHashMap();
						awardMap.put(getAwardValueBySpText(jczqItem.getValue(), jczqSpItem.getPlayType()),jczqItem.getAward());
					}else{
						awardMap.put(getAwardValueBySpText(jczqItem.getValue(), jczqSpItem.getPlayType()),jczqItem.getAward());
					}
				}else{
					if(null==awardMap||awardMap.isEmpty()){
						awardMap = Maps.newHashMap();
						awardMap.put(getAwardValueBySpText(jczqItem.getValue(), playType),jczqItem.getAward());
					}else{
						awardMap.put(getAwardValueBySpText(jczqItem.getValue(), playType),jczqItem.getAward());
					}
				}
			}
			spMap.put(matchKey, awardMap);
		}
		JcMatchOddsList  jcMatchOddsList =  JczqUtil.bulidPrintAwardMap(spMap);
		return jcMatchOddsList;
	}
	public static JczqSpItem getAwardValueByRateItem(Map<String,Object> matchRates,JczqMatchItem jczqMatchItem,PlayType playType){
		JczqItem jczqItem = null;
		JczqSpItem jczqSpItem  =  new JczqSpItem();
		jczqSpItem.setIntTime(Integer.valueOf((jczqMatchItem.getMatchKey().split("-")[0]).substring(2)));
		jczqSpItem.setLineId(Integer.valueOf(jczqMatchItem.getMatchKey().split("-")[1]));
		jczqSpItem.setPlayType(playType);
		List<JczqItem> options  = Lists.newArrayList(); 
		RateItem rateItem = null;
		Map<String, Object> map = JsonUtil.getMap4Json(String.valueOf(matchRates.get(jczqMatchItem.getMatchKey())));
		Map<String, RateItem> matchRate = Maps.newHashMap();
		for (String key : map.keySet()) {
			Map<String, Object> obj = JsonUtil.getMap4Json(String.valueOf(map.get(key)));
			rateItem = new RateItem();
			rateItem.setKey(key);
			rateItem.setValue(Double.valueOf(""+obj.get("value")));
			matchRate.put(key, rateItem);
		}
		switch (playType) {
		case RQSPF:
			for (ItemSPF type : ItemSPF.values()) {
				int v = 1 << type.ordinal();
				if ((jczqMatchItem.getValue() & v) > 0) {
					jczqItem = new JczqItem();
					jczqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jczqItem.setAward(rateItem.getValue());
					options.add(jczqItem);
				}
			}
			break;
		case SPF:
			for (ItemSPF type : ItemSPF.values()) {
				int v = 1 << type.ordinal();
				if ((jczqMatchItem.getValue() & v) > 0) {
					jczqItem = new JczqItem();
					jczqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jczqItem.setAward(rateItem.getValue());
					options.add(jczqItem);
				}
			}
			break;
		case JQS:
			for (ItemJQS type : ItemJQS.values()) {
				int v = 1 << type.ordinal();
				if ((jczqMatchItem.getValue() & v) > 0) {
					jczqItem = new JczqItem();
					jczqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jczqItem.setAward(rateItem.getValue());
					options.add(jczqItem);
				}
			}
			break;
		case BF:
			for (ItemBF type : ItemBF.values()) {
				int v = 1 << type.ordinal();
				if ((jczqMatchItem.getValue() & v) > 0) {
					jczqItem = new JczqItem();
					jczqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jczqItem.setAward(rateItem.getValue());
					options.add(jczqItem);
				}
			}
			break;
		case BQQ:
			for (ItemBQQ type : ItemBQQ.values()) {
				int v = 1 << type.ordinal();
				if ((jczqMatchItem.getValue() & v) > 0) {
					jczqItem = new JczqItem();
					jczqItem.setValue(type.getValue());
					rateItem = matchRate.get(type.name());
					jczqItem.setAward(rateItem.getValue());
					options.add(jczqItem);
				}
			}
			break;
		default:
			throw new RuntimeException("玩法不正确.");
		}
		jczqSpItem.setOptions(options);
		return jczqSpItem;
	}
	public static String getAwardValueBySpText(String betValue,PlayType playType){
		String value = null;
				switch (playType) {
				case RQSPF:
					for (ItemSPF type : ItemSPF.values()) {
						if(betValue.equals(type.getValue())){
							value = type.getValue();
						}
					}
					break;
				case SPF:
					for (ItemSPF type : ItemSPF.values()) {
						if(betValue.equals(type.getValue())){
							value = type.getValue();
						}
					}
					break;
				case JQS:
					for (ItemJQS type : ItemJQS.values()) {
						if(betValue.equals(type.getValue())){
							value = type.getValue();
						}
					}
					break;
				case BF:
					for (ItemBF type : ItemBF.values()) {
						if("90".equals(betValue)){
							value = ItemBF.WIN_OTHER.getValue();
						}else if("99".equals(betValue)){
							value = ItemBF.DRAW_OTHER.getValue();
						}else if("09".equals(betValue)){
							value = ItemBF.LOSE_OTHER.getValue();
						}else if(betValue.equals(type.getValue())){
							value = type.getValue();
						}
						
					}
					break;
				case BQQ:
					for (ItemBQQ type : ItemBQQ.values()) {
						if(type.getValue().equals(betValue)){
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
