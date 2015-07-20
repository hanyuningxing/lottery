package com.cai310.lottery.fetch.jclq;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.fetch.jclq.local.JclqMatchDto;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PlayType;
import com.google.common.collect.Maps;

public class JclqSFPassFetchParser extends JclqSimplePassFetchParser {

	@Override
	public String getSourceUrl() {
		return "http://i.sporttery.cn/odds_calculator/get_odds?i_format=json&i_callback=getData&poolcode[]=mnl";
	}

	@Override
	public PlayType getPlayType() {
		return PlayType.SF;
	}

	@Override
	public PassMode getPassMode() {
		return PassMode.PASS;
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
		return itemMap;
	}
}
