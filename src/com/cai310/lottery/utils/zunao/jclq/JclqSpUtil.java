package com.cai310.lottery.utils.zunao.jclq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.entity.lottery.ticket.Ticket;
import com.cai310.lottery.support.jclq.ItemDXF;
import com.cai310.lottery.support.jclq.ItemRFSF;
import com.cai310.lottery.support.jclq.ItemSF;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.support.jclq.JclqMatchItem;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOdds;
import com.cai310.lottery.ticket.protocol.response.dto.JcMatchOddsList;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public  class JclqSpUtil{
	 //4-002:[胜=1.64]/5-031:[胜=1.65,负=1.65^-14.5]
	public static JcMatchOddsList parseResponseSp(String responseMessage,JclqPrintItemObj jclqPrintItemObj,Ticket ticket){
		PlayType playType = PlayType.values()[jclqPrintItemObj.getPlayTypeOrdinal()];
		JclqSpItemObj jclqSpItemObj = new JclqSpItemObj();
		jclqSpItemObj.setIndex(ticket.getTicketIndex());
		List <JclqSpItem> awardList = Lists.newArrayList();
		if(null!=jclqPrintItemObj&&!jclqPrintItemObj.getItems().isEmpty()){
			String[] spArr = responseMessage.split("/");
			List<JclqItem> options  = null;
			JclqItem jclqItem = null;
			JclqSpItem jclqSpItem  = null;
			int pos = 0;
			String matchKey = null;
			for (JclqMatchItem jclqMatchItem : jclqPrintItemObj.getItems()) {
				matchKey = JclqUtil.getMatchWeek(jclqMatchItem.getMatchKey())+"-"+JclqUtil.getLineId(jclqMatchItem.getMatchKey());
				for (String str : spArr) {
					//5-031:[胜=1.65]
				    String[] match_arr = str.split(":");
					if(matchKey.equals(match_arr[0].trim())){
						jclqSpItem = new JclqSpItem();
						jclqSpItem.setIntTime(Integer.valueOf(jclqMatchItem.getMatchKey().split("-")[0]));
						jclqSpItem.setLineId(Integer.valueOf(JclqUtil.getLineId(jclqMatchItem.getMatchKey())));
						String temp = match_arr[1].replace("[", "");
						temp = temp.replace("]", "");
						if(playType.equals(PlayType.RFSF)||playType.equals(PlayType.DXF)){
							String[] matchSpArrTemp =temp.split("\\^");
							String[] matchSpArr =matchSpArrTemp[0].split(",");
							options  = Lists.newArrayList();
							for (String matchSp : matchSpArr) {
								String[] sp_arr = matchSp.split("=");
								jclqItem = new JclqItem();
								jclqItem.setValue(sp_arr[0].trim());
								jclqItem.setAward(Double.valueOf(sp_arr[1]));
								options.add(jclqItem);
							}
							jclqSpItem.setOptions(options);
							jclqSpItem.setReferenceValue(Double.valueOf(matchSpArrTemp[1]));
							awardList.add(jclqSpItem);
						}else{
							String[] matchSpArr =temp.split(",");
							options  = Lists.newArrayList();
							for (String matchSp : matchSpArr) {
								String[] sp_arr = matchSp.split("=");
								jclqItem = new JclqItem();
								jclqItem.setValue(sp_arr[0].trim());
								jclqItem.setAward(Double.valueOf(sp_arr[1]));
								options.add(jclqItem);
							}
							jclqSpItem.setOptions(options);
							jclqSpItem.setReferenceValue(0D);
							awardList.add(jclqSpItem);
						}
					}
				}
			}
		}
		jclqSpItemObj.setAwardList(awardList);
		Map<String, Double> awardMap;
		Map<String,Map<String, Double>> spMap = Maps.newHashMap();
		for(JclqSpItem jclqSpItem:jclqSpItemObj.getAwardList()){
			String matchKey = jclqSpItem.getIntTime()+"-"+JclqUtil.formatLineId(jclqSpItem.getLineId());
			awardMap = spMap.get(matchKey);
			for (JclqItem jclqItem : jclqSpItem.getOptions()) {
				if(null==awardMap||awardMap.isEmpty()){
					awardMap = Maps.newHashMap();
					awardMap.put(getAwardValueBySpText(jclqItem.getValue(), playType),jclqItem.getAward());
				}else{
					awardMap.put(getAwardValueBySpText(jclqItem.getValue(), playType),jclqItem.getAward());
				}
			}
			if(null==awardMap||awardMap.isEmpty()){
				awardMap = Maps.newHashMap();
				awardMap.put(JclqConstant.REFERENCE_VALUE_KEY,jclqSpItem.getReferenceValue());
			}else{
				awardMap.put(JclqConstant.REFERENCE_VALUE_KEY,jclqSpItem.getReferenceValue());
			}
			spMap.put(matchKey, awardMap);
		}
		JcMatchOddsList  jcMatchOddsList =  JclqUtil.bulidPrintAwardMap(spMap);
		return jcMatchOddsList;
	}
	public static String getAwardValueBySpText(String betValue,PlayType playType){
		String value = null;
		
				switch (playType) {
				case SF:
						if(betValue.equals("主胜")){
							value = ItemSF.WIN.getValue();
						}else if(betValue.equals("客胜")){
							value = ItemSF.LOSE.getValue();
						}
					break;
				case RFSF:
						if(betValue.equals("主胜")){
							value = ItemRFSF.SF_WIN.getValue();
						}else if(betValue.equals("客胜")){
							value = ItemRFSF.SF_LOSE.getValue();
						}
					break;
				case SFC:
					for (ItemSFC type : ItemSFC.values()) {
						String valueTemp = type.name().replace("HOME","胜");
						valueTemp = valueTemp.replace("GUEST","负");
						if(type.equals(ItemSFC.GUEST26)||type.equals(ItemSFC.HOME26)){
							valueTemp = valueTemp+"分以上";
						}else{
							valueTemp = valueTemp.replace("_", "-");
							valueTemp = valueTemp+"分";
						}
						if(betValue.equals(valueTemp)){
							value = type.getValue();
						}
					}
					break;
				case DXF:
					for (ItemDXF type : ItemDXF.values()) {
						if(betValue.equals(type.getText())){
							value =type.getValue();
						}
					}
					break;
				default:
					throw new RuntimeException("玩法不正确.");
				}
				return value;
	}
}
