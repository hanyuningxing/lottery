package com.cai310.lottery.cache;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.fetch.DataBlock;
import com.cai310.lottery.fetch.FetchDataBean;
import com.cai310.lottery.fetch.ValueItem;
import com.cai310.lottery.fetch.jczq.JczqContextHolder;
import com.cai310.lottery.service.lottery.jczq.JczqMatchEntityManager;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PlayType;
import com.google.common.base.Function;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;

@Component("JczqLocalCache")
public class JczqLocalCache {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String MATCH_KEY = "MATCH_KEY";
 
	@Autowired
	private JczqMatchEntityManager matchEntityManager;

	private ConcurrentMap<String, List<JczqMatch>> matchLocalCache;
	private ConcurrentMap<String, Map<Long,JczqMatch>> matchCache;
	private ConcurrentMap<RateKey, Map<String, Map<String, RateItem>>> rateLocalCache;

	@PostConstruct
	public void init() {
		matchLocalCache = new MapMaker().expiration(10, TimeUnit.SECONDS).makeComputingMap(
				new Function<String, List<JczqMatch>>() {

					@Override
					public List<JczqMatch> apply(String from) {
						return matchEntityManager.findMatchsOfUnEnd();
					}
				});
		matchCache = new MapMaker().expiration(10, TimeUnit.SECONDS).makeComputingMap(
				new Function<String,Map<Long,JczqMatch>>() {
					@Override
					public Map<Long,JczqMatch> apply(String from) {
						Map<Long,JczqMatch> map = JczqContextHolder.getJczqMatchMap();
						if(null==map||map.isEmpty()){
							map = Maps.newHashMap();
							List<JczqMatch> matchList = matchEntityManager.findMatchsOfUnEnd();
							for (JczqMatch jczqMatch : matchList) {
								map.put(jczqMatch.getMatchKeyInteger(), jczqMatch);
							}
						}
						return map;
					}
				});
		rateLocalCache = new MapMaker().expiration(60, TimeUnit.SECONDS).makeComputingMap(
				new Function<RateKey, Map<String, Map<String, RateItem>>>() {

					@Override
					public Map<String, Map<String, RateItem>> apply(RateKey from) {
						Map<String, Map<String, RateItem>> map = Maps.newLinkedHashMap();
						try {
							FetchDataBean fetchDataBean = JczqContextHolder.getRateData(from.getPlayType(),
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

	public List<JczqMatch> getMatchList() {
		return matchLocalCache.get(MATCH_KEY);
	}
	public Map<Long,JczqMatch> getMatchMap() {
		return matchCache.get(MATCH_KEY);
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
