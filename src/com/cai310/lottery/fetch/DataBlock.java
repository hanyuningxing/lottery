package com.cai310.lottery.fetch;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class DataBlock {
	/** 数据内容 */
	private SortedMap<String, Map<String, ValueItem>> data;

	/** 当前的数据版本 */
	private int version;

	/** 数据更新时间 */
	private Date updateTime;

	/**
	 * @param data the {@link #data}
	 * @param updateTime the {@link #updateTime}
	 * @param version the {@link #version}
	 */
	public DataBlock(SortedMap<String, Map<String, ValueItem>> data, Date updateTime, int version) {
		super();
		this.data = new TreeMap<String, Map<String, ValueItem>>(data);
		this.version = version;
		this.updateTime = updateTime;
	}

	/**
	 * @return the {@link #data}
	 */
	public SortedMap<String, Map<String, ValueItem>> getData() {
		return data;
	}

	/**
	 * @return the {@link #version}
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @return the {@link #updateTime}
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

}
