package com.cai310.lottery.fetch.jclq;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.fetch.jclq.local.JclqMatchDto;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PlayType;
import com.google.common.collect.Maps;

public class JclqDXFPassFetchParser extends JclqSimplePassFetchParser {

	@Override
	public String getSourceUrl() {
		return "http://i.sporttery.cn/odds_calculator/get_odds?i_format=json&i_callback=getData&poolcode[]=hilo";
	}

	@Override
	public PlayType getPlayType() {
		return PlayType.DXF;
	}

	@Override
	public PassMode getPassMode() {
		return PassMode.PASS;
	}

	@Override
	protected void handleReducedValue(Map<String, Double> itemMap, String reducedValueStr) {
		Double value;
		if (StringUtils.isNotBlank(reducedValueStr)) {
			reducedValueStr = reducedValueStr.trim().replaceAll("\\+", "");
			value = Double.valueOf(reducedValueStr);
		} else {
			value = 0d;
		}
		itemMap.put(REDUCED_VALUE_KEY, value);
	}
	
	@Override
	protected String singleTotalScore(String value){
		return value;
	}
	
	@Override
	public Map<String, Double> getItemMap(JclqMatchDto matchDto) {
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
		int i = 0;
		for (Item item : getPlayType().getAllItems()) {
			String rateStr = odd[i];
			Double rate = (StringUtils.isNotBlank(rateStr) && rateStr.matches(DOUBLE_REGEX)) ? Double.valueOf(rateStr)
					: 0;
			itemMap.put(item.toString(), rate);
			i++;
		}
		itemMap.put(REDUCED_VALUE_KEY, Double.valueOf(matchDto.getSingleTotalScore()));
		return itemMap;
	}
}