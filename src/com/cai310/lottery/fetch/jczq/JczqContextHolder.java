package com.cai310.lottery.fetch.jczq;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.fetch.FetchDataBean;
import com.cai310.lottery.fetch.jczq.matchinfo.JczqBetfair;
import com.cai310.lottery.fetch.jczq.matchinfo.JczqMatchEuroOdds;
import com.cai310.lottery.fetch.jczq.matchinfo.JczqMatchInfoDTO;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PlayType;
import com.google.common.collect.Maps;

public class JczqContextHolder {

	private static List<JczqMatch> matchList = null;
	
	private static Map<Long,JczqMatch> matchMap = Maps.newHashMap();

	private static Map<String, FetchDataBean> rateMap = Maps.newHashMap();
	
	private static Map<String, JczqMatchInfoDTO> matchinfoMap = Maps.newHashMap();
	
	private static Map<String, JczqMatchEuroOdds> matchEuroOddsMap = Maps.newHashMap();
	
	private static Map<String, JczqBetfair> matchBetfairMap = Maps.newHashMap();
	
	public static List<JczqMatch> getMatchList() {
		return matchList;
	}

	public static void updateMatchList(List<JczqMatch> matchList) {
		JczqContextHolder.matchList = matchList;
		for (JczqMatch jczqMatch : matchList) {
			matchMap.put(jczqMatch.getMatchKeyInteger(), jczqMatch);
		}
	}
	
	protected static String genKey(PlayType playType, PassMode passMode){
		return String.format("%s_%s", playType, passMode);
	}

	public static FetchDataBean getRateData(PlayType playType, PassMode passMode) {
		return rateMap.get(genKey(playType, passMode));
	}
	
	public static JczqMatch getJczqMatch(String matchKey) {
		return matchMap.get(matchKey);
	}
	public static Map<Long,JczqMatch> getJczqMatchMap() {
		return matchMap;
	}
	public static void updateRateData(PlayType playType, PassMode passMode, Map<String, Map<String, Double>> rateData, Date updateTime) {
		String key = genKey(playType, passMode);
		FetchDataBean bean = JczqContextHolder.rateMap.get(key);
		if (bean == null) {
			bean = new FetchDataBean();
			JczqContextHolder.rateMap.put(key, bean);
		}
		bean.updateData(rateData, updateTime);
	}

	
	
	public static Map<String, JczqMatchInfoDTO> getJczqMatchinfoMap() {
		return matchinfoMap;
	}
	
	public static void updateJczqMatchinfo(List<JczqMatchInfoDTO> matchinfoList) {
		matchinfoMap.clear();
		for(JczqMatchInfoDTO matchinfo : matchinfoList) {
			matchinfoMap.put(matchinfo.getMatchKey(), matchinfo);
		}
	}
	
	public static Map<String, JczqMatchEuroOdds> getJczqMatchEuroOddsMap() {
		return matchEuroOddsMap;
	}
	
	public static void updateJczqMatchEuroOdds(List<JczqMatchEuroOdds> matchEuroOddsList) {
		matchEuroOddsMap.clear();
		for(JczqMatchEuroOdds euroOdds : matchEuroOddsList) {
			matchEuroOddsMap.put(euroOdds.getMatchKey(), euroOdds);
		}
	}
	
	public static Map<String, JczqBetfair> getJczqBetfairMap() {
		return matchBetfairMap;
	}
	
	public static void updateJczqBetfair(List<JczqBetfair> betfairList) {
		matchBetfairMap.clear();
		for(JczqBetfair betfair : betfairList) {
			matchBetfairMap.put(betfair.getMatchKey(), betfair);
		}
	}
	
}
