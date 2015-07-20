package com.cai310.lottery.fetch.jczq;

import java.util.Map;
import java.util.SortedMap;

import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PlayType;

public class JczqFetchResult {
	/** 对阵数据 */
	private SortedMap<String, JczqMatch> matchMap;

	/** 赔率数据 */
	private Map<String, Map<String, Double>> rateData;

	/**
	 * 玩法类型
	 * 
	 * @see org.xhero.app.webfetch.support.jczq.PlayType
	 */
	private PlayType playType;
	
	/**
	 * 过关模式
	 * 
	 * @see org.xhero.app.webfetch.support.jczq.PassMode
	 */
	private PassMode passMode;
	
	/**
	 * @param playType the {@link #playType}
	 * @param passMode the {@link #passMode}
	 * @param matchMap the {@link #matchMap}
	 * @param rateData the {@link #rateData}
	 */
	public JczqFetchResult(PlayType playType, PassMode passMode, SortedMap<String, JczqMatch> matchMap,
			Map<String, Map<String, Double>> rateData ) {
		super();
		this.playType = playType;
		this.passMode = passMode;
		this.rateData = rateData;
		this.matchMap = matchMap;
	}

	/**
	 * @return the {@link #rateData}
	 */
	public Map<String, Map<String, Double>> getRateData() {
		return rateData;
	}

	/**
	 * @return the {@link #matchMap}
	 */
	public SortedMap<String, JczqMatch> getMatchMap() {
		return matchMap;
	}

	/**
	 * @return the {@link #playType}
	 */
	public PlayType getPlayType() {
		return playType;
	}

	/**
	 * @return the {@link #passMode}
	 */
	public PassMode getPassMode() {
		return passMode;
	}
}
