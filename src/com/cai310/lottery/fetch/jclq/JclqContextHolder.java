package com.cai310.lottery.fetch.jclq;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.fetch.FetchDataBean;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PlayType;
import com.google.common.collect.Maps;

public class JclqContextHolder {

	private static List<JclqMatch> matchList = null;

	private static Map<String, FetchDataBean> rateMap = Maps.newHashMap();

	public static List<JclqMatch> getMatchList() {
		return matchList;
	}

	public static void updateMatchList(List<JclqMatch> matchList) {
		JclqContextHolder.matchList = matchList;
	}

	protected static String genKey(PlayType playType, PassMode passMode) {
		return String.format("%s_%s", playType, passMode);
	}

	public static FetchDataBean getRateData(PlayType playType, PassMode passMode) {
		return rateMap.get(genKey(playType, passMode));
	}

	public static void updateRateData(PlayType playType, PassMode passMode, Map<String, Map<String, Double>> rateData, Date updateTime) {
		String key = genKey(playType, passMode);
		FetchDataBean bean = JclqContextHolder.rateMap.get(key);
		if (bean == null) {
			bean = new FetchDataBean();
			JclqContextHolder.rateMap.put(key, bean);
		}
		bean.updateData(rateData, updateTime);
	}

}
