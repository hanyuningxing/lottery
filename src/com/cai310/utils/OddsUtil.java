package com.cai310.utils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.joda.time.DateTime;

import com.cai310.lottery.cache.JczqLocalCache;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqMatchEntityManager;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.spring.SpringContextHolder;

public class OddsUtil {

	public static String getJczqOdds() throws ClientProtocolException, IOException {
		// JczqMatchEntityManager mgr =
		// SpringContextHolder.getBean("jczqMatchEntityManagerImpl");
		// JczqLocalCache cache = SpringContextHolder.getBean("JczqLocalCache");
		// List<JczqMatch> matchs = null;
		// matchs = cache.getMatchList();
		// if (matchs.isEmpty()) {
		// matchs = mgr.findMatchsOfUnEnd();
		// }
		// Set<String> set = new HashSet<String>();
		// for (JczqMatch m : matchs) {
		// DateTime dateTime = JczqUtil.getDateTime(m.getMatchDate());
		// String key = dateTime.toString("yyyyMMdd",
		// Locale.SIMPLIFIED_CHINESE);
		// set.add(key);
		//
		// }
		StringBuffer sb = new StringBuffer();
		sb.append("odds_data_arr = [];");
		int i = 0;
		String url = "http://data.fox008.com/bet007/jczqOdds";
		sb.append("odds_data_arr[" + i + "] = " + HttpClientUtil.getRemoteSource(url, "utf-8") + ";");
		String value = sb.toString();
		String dir = "/js/odds/" + Lottery.JCZQ.getKey() + "/";
		String fileName = "jczq_odds.js";
		com.cai310.utils.WriteHTMLUtil.writeHtm(dir, fileName, value, "UTF-8");
		return value;
	}

	public static String getJczqAsiaOdds() throws ClientProtocolException, IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("asia_odds_data_arr = [];");
		int i = 0;
		String url = "http://data.fox008.com/bet007/jczqAsiaOdds";
		sb.append("asia_odds_data_arr[" + i + "] = " + HttpClientUtil.getRemoteSource(url, "utf-8") + ";");
		i++;
		String value = sb.toString();
		String dir = "/js/odds/" + Lottery.JCZQ.getKey() + "/";
		String fileName = "jczq_asia_odds.js";
		com.cai310.utils.WriteHTMLUtil.writeHtm(dir, fileName, value, "UTF-8");
		return value;
	}

	public static String getDczcOdds() throws ClientProtocolException, IOException {
		PeriodEntityManager periodEntityManager = SpringContextHolder.getBean("periodEntityManager");
		List<String> periodList = periodEntityManager.findOnSalePeriodNumber(Lottery.DCZC);
		String periodNumber = periodList.get(0);
		String url = "http://61.147.127.238:8090/odds/dczcOdds2/?&period=" + periodNumber;
		String value = "odds_data_arr = " + HttpClientUtil.getRemoteSource(url, "utf-8");
		String dir = "/js/odds/" + Lottery.DCZC.getKey() + "/";
		String fileName = "dczc_odds.js";
		com.cai310.utils.WriteHTMLUtil.writeHtm(dir, fileName, value, "UTF-8");
		return value;
	}
}
