package com.cai310.lottery.fetch;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

public class ChangeHistory {
	/** 变化数据 */
	private SortedMap<String, Map<String, ValueItem>> chgData;

	/** 变化时间 */
	private Date chgTime;

	/**
	 * @param chgData the {@link #chgData}
	 * @param chgTime the {@link #chgTime}
	 */
	public ChangeHistory(SortedMap<String, Map<String, ValueItem>> chgData, Date chgTime) {
		super();
		this.chgData = chgData;
		this.chgTime = chgTime;
	}

	/**
	 * @return the {@link #chgData}
	 */
	public SortedMap<String, Map<String, ValueItem>> getChgData() {
		return chgData;
	}

	/**
	 * @return the {@link #chgTime}
	 */
	public Date getChgTime() {
		return chgTime;
	}
}
