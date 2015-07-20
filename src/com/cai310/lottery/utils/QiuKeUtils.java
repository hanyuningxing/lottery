package com.cai310.lottery.utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dto.lottery.JcMixMatchDTO;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.support.RateItem;
import com.cai310.lottery.ticket.common.HttpclientUtil;
import com.cai310.lottery.ticket.common.SecurityUtil;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class QiuKeUtils {

	private static final String reUrl = "http://www.qiu310.com:8070/ticket";
	private static final String Uid = "1059";
	private static final String key = "5565933D200F2D0D2A22B9F6092F3B43";

	public static Map<String, JczqMatch> getJczqMatch()
			throws ClientProtocolException, IOException {
		String returnString = getResult();
		Map<String, JczqMatch> map = Maps.newHashMap();
		if (StringUtils.isNotBlank(returnString)) {
			Map<String, Object> _map = JsonUtil.getMap4Json(returnString);
			String str = ((JSONArray) _map.get("match")).toString();
			Gson gson = new Gson();
			List<JczqMatch> list = gson.fromJson(str,
					new TypeToken<List<JczqMatch>>() {
					}.getType());
			for (JczqMatch jczqMatch : list) {
				JczqMatch matchDTO = new JczqMatch();
				matchDTO.setGameColor(jczqMatch.getGameColor());
				matchDTO.setGameName(jczqMatch.getGameName());
				matchDTO.setHomeTeamName(jczqMatch.getHomeTeamName());
				matchDTO.setGuestTeamName(jczqMatch.getGuestTeamName());
				matchDTO.setMatchTime(jczqMatch.getMatchTime());
				String lineId = jczqMatch.getMatchKey();
				matchDTO.setLineId(Integer.valueOf(lineId.split("-")[1]));
				matchDTO.setMatchDate(Integer.valueOf(lineId.split("-")[0]));
				matchDTO.setHandicap(jczqMatch.getHandicap());
				matchDTO.setOpenFlag(jczqMatch.getOpenFlag());
				map.put(jczqMatch.getMatchKey(), matchDTO);
			}
		}
		return map;
	}

	public static Map<com.cai310.lottery.support.jczq.PlayType, Map<String, Map<String, Double>>> getJczqGgSp() {
		String returnString = getResult();

		Map<com.cai310.lottery.support.jczq.PlayType, Map<String, Map<String, Double>>> rateData = Maps.newLinkedHashMap();
		Map<String, Map<String, Double>> rqsf_rateData = Maps.newLinkedHashMap();
		Map<String, Map<String, Double>> jqs_rateData = Maps.newLinkedHashMap();
		Map<String, Map<String, Double>> bf_rateData = Maps.newLinkedHashMap();
		Map<String, Map<String, Double>> bqq_rateData = Maps.newLinkedHashMap();
		Map<String, Map<String, Double>> spf_rateData = Maps.newLinkedHashMap();

		if (StringUtils.isNotBlank(returnString)) {
			Map<String, Object> _map = JsonUtil.getMap4Json(returnString);
			String str = ((JSONArray) _map.get("match")).toString();
			Gson gson = new Gson();
			List<JcMixMatchDTO> list = gson.fromJson(str,new TypeToken<List<JcMixMatchDTO>>() {}.getType());
			for (JcMixMatchDTO jcMixMatch : list) {
				Map<String, Map<String, RateItem>> map=jcMixMatch.getMixSp();
				Set<String> key=map.keySet();
				for(Iterator it = key.iterator(); it.hasNext();){
					String s = (String) it.next();
					Map<String, RateItem> map_cp=map.get(s);
					Set<String> key_=map_cp.keySet();
					Map<String, Double> itemMap = Maps.newHashMap();
					for(Iterator it_ = key_.iterator(); it_.hasNext();){
						String s_ = (String) it_.next();
						RateItem rateItem =map_cp.get(s_);
						itemMap.put(s_,rateItem.getValue() );
					}
					if(s.equals("BF")){
						bf_rateData.put(jcMixMatch.getMatchKey(), itemMap);
					}else if(s.equals("BQQ")){
						bqq_rateData.put(jcMixMatch.getMatchKey(), itemMap);
					}else if(s.equals("JQS")){
						jqs_rateData.put(jcMixMatch.getMatchKey(), itemMap);
					}else if(s.equals("RQSPF")){
						rqsf_rateData.put(jcMixMatch.getMatchKey(), itemMap);
					}else if(s.equals("SPF")){
						spf_rateData.put(jcMixMatch.getMatchKey(), itemMap);
					}
				}
			}
			rateData.put(com.cai310.lottery.support.jczq.PlayType.RQSPF, rqsf_rateData);
			rateData.put(com.cai310.lottery.support.jczq.PlayType.JQS, jqs_rateData);
			rateData.put(com.cai310.lottery.support.jczq.PlayType.BF, bf_rateData);
			rateData.put(com.cai310.lottery.support.jczq.PlayType.BQQ, bqq_rateData);
			rateData.put(com.cai310.lottery.support.jczq.PlayType.SPF,spf_rateData);
		}
		return rateData;
	}

	private static String getResult() {
		try {
			Map<String, Object> ParamMap = new LinkedHashMap<String, Object>();
			ParamMap.put("wLotteryId", Lottery.JCZQ.ordinal());
			ParamMap.put("periodNumber", "");

			ParamMap.put("playType", "4");
			ParamMap.put("type", "1");

			String wAction = "104";
			String wAgent = Uid;
			String betValue = JsonUtil.getJsonString4JavaPOJO(ParamMap);

			Map<String, String> ParamMap1 = new LinkedHashMap<String, String>();
			ParamMap1.put("wAction", wAction);
			ParamMap1.put("wParam", betValue);
			ParamMap1.put("wAgent", wAgent);
			String param = wAction + betValue + wAgent + key;
			String pwd = SecurityUtil.md5(param.getBytes("UTF-8"))
					.toUpperCase().trim();
			ParamMap1.put("wSign", pwd);
			return HttpclientUtil.Utf8HttpClientUtils(reUrl, ParamMap1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


}
