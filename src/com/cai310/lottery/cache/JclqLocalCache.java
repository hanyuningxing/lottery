package com.cai310.lottery.cache;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.fetch.DataBlock;
import com.cai310.lottery.fetch.FetchDataBean;
import com.cai310.lottery.fetch.ValueItem;
import com.cai310.lottery.fetch.jclq.JclqContextHolder;
import com.cai310.lottery.service.lottery.jclq.JclqMatchEntityManager;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PlayType;
import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;

@Component
public class JclqLocalCache {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String MATCH_KEY = "MATCH_KEY";

	@Autowired
	private JclqMatchEntityManager matchEntityManager;

	private ConcurrentMap<String, List<JclqMatch>> matchLocalCache;
	private ConcurrentMap<RateKey, Map<String, Map<String, RateItem>>> rateLocalCache;

	@PostConstruct
	public void init() {
		matchLocalCache = new MapMaker().expiration(10, TimeUnit.SECONDS).makeComputingMap(
				new Function<String, List<JclqMatch>>() {

					@Override
					public List<JclqMatch> apply(String from) {
						return matchEntityManager.findMatchsOfUnEnd();
					}
				});
		rateLocalCache = new MapMaker().expiration(10, TimeUnit.SECONDS).makeComputingMap(
				new Function<RateKey, Map<String, Map<String, RateItem>>>() {

					@Override
					public Map<String, Map<String, RateItem>> apply(RateKey from) {
						Map<String, Map<String, RateItem>> map = Maps.newLinkedHashMap();
						try {
							FetchDataBean fetchDataBean = JclqContextHolder.getRateData(from.getPlayType(),
									from.getPassMode());
							if (fetchDataBean != null && fetchDataBean.getDataBlock() != null) {
								DataBlock dataBlock = fetchDataBean.getDataBlock();
								if (dataBlock.getData() != null) {
									for (Entry<String, Map<String, ValueItem>> entry : dataBlock.getData().entrySet()) {
										try {
											Map<String, RateItem> itemMap = Maps.newHashMap();
											for (Entry<String, ValueItem> itemEntry : entry.getValue().entrySet()) {
												ValueItem valueItem = itemEntry.getValue();
												RateItem rateItem = new RateItem();
												rateItem.setKey(itemEntry.getKey());
												rateItem.setValue(valueItem.getValue());
												rateItem.setChg(valueItem.getChg());
												itemMap.put(itemEntry.getKey(), rateItem);
											}
											map.put(entry.getKey(), itemMap);
										}catch (Exception e) {
											continue;
										}
									}
									if (!map.isEmpty())
										return map;
								}
							}
						}catch (Exception e) {
							return map;
						}
						return map;
					}
				});
	}

	public List<JclqMatch> getMatchList() {
		return matchLocalCache.get(MATCH_KEY);
	}

	public Map<String, Map<String, RateItem>> getRateData(PlayType playType, PassMode passMode) {
		return rateLocalCache.get(new RateKey(playType, passMode));
	}

	class RateKey {
		private PlayType playType;
		private PassMode passMode;

		private RateKey(PlayType playType, PassMode passMode) {
			super();
			this.playType = playType;
			this.passMode = passMode;
		}

		public PlayType getPlayType() {
			return playType;
		}

		public PassMode getPassMode() {
			return passMode;
		}
	}
}
