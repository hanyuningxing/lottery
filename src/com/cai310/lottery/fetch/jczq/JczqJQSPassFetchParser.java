package com.cai310.lottery.fetch.jczq;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.fetch.jczq.local.JczqMatchDto;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PlayType;
import com.google.common.collect.Maps;

public class JczqJQSPassFetchParser extends JczqSimplePassFetchParser {

	@Override
	public String getSourceUrl() {
//		return "http://info.sporttery.cn/lotterygame/ttg_list.php";
//		return "http://info.sporttery.cn/football/cal_ttg.htm";
		return "http://i.sporttery.cn/odds_calculator/get_odds?i_format=json&poolcode[]=ttg";
	}

	@Override
	public PlayType getPlayType() {
		return PlayType.JQS;
	}

	@Override
	public PassMode getPassMode() {
		return PassMode.PASS;
	}

	/**
	 * 抓取的json数据rate解析
	 * Add By Suixinhang
	 */
	@Override
	public Map<String, Double> getItemMap(JczqMatchDto matchDto)  {
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