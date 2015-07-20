package com.cai310.lottery.fetch.dczc;

import java.util.Map;

import com.cai310.lottery.support.dczc.PlayType;

public class DczcFetchResult {

	/**
	 * 玩法类型
	 * 
	 * @see com.cai310.lottery.support.dczc.PlayType
	 */
	private PlayType playType;

	/** 赔率数据 */
	private Map<String, Map<String, Double>> rateData;

	public DczcFetchResult(PlayType playType, Map<String, Map<String, Double>> rateData) {
		super();
		this.playType = playType;
		this.rateData = rateData;
	}

	/**
	 * @return the {@link #rateData}
	 */
	public Map<String, Map<String, Double>> getRateData() {
		return rateData;
	}

	/**
	 * @return the {@link #playType}
	 */
	public PlayType getPlayType() {
		return playType;
	}
}
