package com.cai310.lottery.fetch.dczc;

import java.util.Date;
import java.util.Map;

import com.cai310.lottery.fetch.FetchDataBean;
import com.cai310.lottery.support.dczc.PlayType;
import com.google.common.collect.Maps;

public class DczcContextHolder {
	private static String CURR_PERIOD_NUMBER = null;
	private static Map<PlayType, FetchDataBean> RATE_MAP = Maps.newHashMap();

	public static FetchDataBean getRateData(String issueNumber, PlayType playType) {
		if (!issueNumber.equals(CURR_PERIOD_NUMBER))
			return null;
		return RATE_MAP.get(playType);
	}

	public static void updateRateData(String periodNumber, PlayType playType, Map<String, Map<String, Double>> rateData,
			Date updateTime) {
		FetchDataBean bean = RATE_MAP.get(playType);
		if (bean == null || !periodNumber.equals(CURR_PERIOD_NUMBER)) {
			bean = new FetchDataBean();
			RATE_MAP.put(playType, bean);
			CURR_PERIOD_NUMBER = periodNumber;
		}
		bean.updateData(rateData, updateTime);
	}
}
