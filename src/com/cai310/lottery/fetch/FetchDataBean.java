package com.cai310.lottery.fetch;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;

public class FetchDataBean {
	/** 版本号默认上限 */
	public static final int DEFAULT_MAX_VERSION = Integer.MAX_VALUE;

	/** 变化历史默认超时时间，单位（秒） */
	public static final int DEFAULT_CHG_HISTORY_EXPIRATION = 60;

	/** 全局数据 */
	private DataBlock dataBlock;

	/** 变化历史 */
	private final ConcurrentMap<Integer, ChangeHistory> chgHistory;

	/** 版本号上限 */
	private final int maxVersion;

	/**
	 * 使用默认的版本号上限和超时时间
	 */
	public FetchDataBean() {
		this(DEFAULT_MAX_VERSION, DEFAULT_CHG_HISTORY_EXPIRATION);
	}

	/**
	 * 指定超时时间，使用默认的版本号上限
	 * 
	 * @param expiration 变化历史超时时间，单位（秒）
	 */
	public FetchDataBean(int expiration) {
		this(DEFAULT_MAX_VERSION, expiration);
	}

	/**
	 * @param maxVersion 版本号上限
	 * @param expiration 变化历史超时时间，单位（秒）
	 */
	public FetchDataBean(int maxVersion, int expiration) {
		if (maxVersion <= 0)
			maxVersion = DEFAULT_MAX_VERSION;
		if (expiration <= 0)
			expiration = DEFAULT_CHG_HISTORY_EXPIRATION;

		this.maxVersion = maxVersion;
		this.chgHistory = new MapMaker().expiration(expiration, TimeUnit.SECONDS).makeMap();
	}

	/**
	 * 更新数据
	 * 
	 * @param fetchData 抓取的数据
	 * @param fetchTime 抓取的时间
	 */
	public synchronized void updateData(Map<String, Map<String, Double>> fetchData, Date fetchTime) {
		if (this.dataBlock == null) {
			SortedMap<String, Map<String, ValueItem>> data = Maps.newTreeMap();
			for (Entry<String, Map<String, Double>> entry : fetchData.entrySet()) {
				Map<String, ValueItem> itemMap = Maps.newHashMap();
				for (Entry<String, Double> itemEntry : entry.getValue().entrySet()) {
					itemMap.put(itemEntry.getKey(), new ValueItem(itemEntry.getValue()));
				}
				data.put(entry.getKey(), itemMap);
			}
			this.dataBlock = new DataBlock(data, fetchTime, 0);
		} else {
			SortedMap<String, Map<String, ValueItem>> oldData = this.dataBlock.getData();
			SortedMap<String, Map<String, ValueItem>> newData = Maps.newTreeMap();
			SortedMap<String, Map<String, ValueItem>> chgData = Maps.newTreeMap();
			for (Entry<String, Map<String, Double>> entry : fetchData.entrySet()) {
				String key = entry.getKey();
				Map<String, ValueItem> itemMap = oldData.get(key);
				if (itemMap == null) {
					itemMap = Maps.newHashMap();
				}
				Map<String, ValueItem> chgMap = Maps.newHashMap();
				for (Entry<String, Double> itemEntry : entry.getValue().entrySet()) {
					String itemKey = itemEntry.getKey();
					Double newValue = itemEntry.getValue();
					if (newValue == null)
						newValue = 0.0;
					ValueItem oldItem = itemMap.get(itemKey);
					Double oldValue = (oldItem != null && oldItem.getValue() != null) ? oldItem.getValue() : 0.0;

					int chg;
					if (newValue == 0 && oldValue > 0) {
						newValue = oldValue;
						chg = 0;
					} else {
						chg = newValue.compareTo(oldValue);
					}

					ValueItem item = new ValueItem(newValue, chg);
					itemMap.put(itemKey, item);
					if (chg != 0) {
						chgMap.put(itemKey, item);
					}
				}
				newData.put(key, itemMap);
				if (!chgMap.isEmpty()) {
					chgData.put(key, chgMap);
				}
			}
			if (!chgData.isEmpty()) {
				int newVersion;
				if (this.dataBlock.getVersion() < this.maxVersion) {
					newVersion = this.dataBlock.getVersion() + 1;
					chgHistory.put(newVersion, new ChangeHistory(chgData, fetchTime));
				} else {
					newVersion = 0;
					chgHistory.clear();
				}
				this.dataBlock = new DataBlock(newData, fetchTime, newVersion);
			}
		}
	}

	public DataBlock getDataBlock() {
		return dataBlock;
	}

	public ChgData getChgHistory() {
		SortedMap<Integer, ChangeHistory> map = new TreeMap<Integer, ChangeHistory>(chgHistory);
		List<Integer> versionList = Lists.newArrayList(map.keySet());
		List<ChangeHistory> chgList = Lists.newArrayList(map.values());
		Integer minVersion;
		Integer maxVersion;
		if (!versionList.isEmpty()) {
			minVersion = versionList.get(0);
			maxVersion = versionList.get(versionList.size() - 1);
		} else if (this.dataBlock != null) {
			minVersion = maxVersion = this.dataBlock.getVersion();
		} else {
			minVersion = maxVersion = 0;
		}
		return new ChgData(minVersion, maxVersion, chgList);
	}

	public int getMaxVersion() {
		return maxVersion;
	}

	public static class ChgData {
		private Integer minVersion;
		private Integer maxVersion;
		private List<ChangeHistory> chgList;

		public ChgData(Integer minVersion, Integer maxVersion, List<ChangeHistory> chgList) {
			super();
			this.minVersion = minVersion;
			this.maxVersion = maxVersion;
			this.chgList = chgList;
		}

		public Integer getMinVersion() {
			return minVersion;
		}

		public Integer getMaxVersion() {
			return maxVersion;
		}

		public List<ChangeHistory> getChgList() {
			return chgList;
		}
	}
}
