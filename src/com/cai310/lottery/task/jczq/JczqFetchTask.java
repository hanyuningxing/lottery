package com.cai310.lottery.task.jczq;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.SortedMap;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.fetch.jczq.JczqAbstractFetchParser;
import com.cai310.lottery.fetch.jczq.JczqBFPassFetchParser;
import com.cai310.lottery.fetch.jczq.JczqBFSingleFetchParser;
import com.cai310.lottery.fetch.jczq.JczqBQQPassFetchParser;
import com.cai310.lottery.fetch.jczq.JczqBQQSingleFetchParser;
import com.cai310.lottery.fetch.jczq.JczqContextHolder;
import com.cai310.lottery.fetch.jczq.JczqFetchResult;
import com.cai310.lottery.fetch.jczq.JczqJQSPassFetchParser;
import com.cai310.lottery.fetch.jczq.JczqJQSSingleFetchParser;
import com.cai310.lottery.fetch.jczq.JczqRFSPFPassFetchParser;
import com.cai310.lottery.fetch.jczq.JczqRFSPFSingleFetchParser;
import com.cai310.lottery.fetch.jczq.JczqSPFPassFetchParser;
import com.cai310.lottery.fetch.jczq.JczqSPFSingleFetchParser;
import com.cai310.lottery.fetch.jczq.local.JczqMatchDto;
import com.cai310.lottery.fetch.jczq.local.JczqMatchFetchParser;
import com.cai310.lottery.support.jczq.JczqUtil;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.utils.QiuKeUtils;
import com.cai310.lottery.utils.ZunaoDczcSp;
import com.cai310.lottery.utils.ZunaoDczcSpVisitor;
import com.cai310.lottery.utils.ZunaoUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class JczqFetchTask {
	private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

	private static List<JczqAbstractFetchParser> fetchList;
	
	static{
		fetchList = Lists.newArrayList();
		
		fetchList.add(new JczqSPFPassFetchParser());
		fetchList.add(new JczqRFSPFPassFetchParser());
		fetchList.add(new JczqJQSPassFetchParser());
		fetchList.add(new JczqBQQPassFetchParser());
		fetchList.add(new JczqBFPassFetchParser());
//		fetchList.add(new JczqRFSPFSingleFetchParser());
//		fetchList.add(new JczqSPFSingleFetchParser());
//		fetchList.add(new JczqJQSSingleFetchParser());
//		fetchList.add(new JczqBQQSingleFetchParser());
//		fetchList.add(new JczqBFSingleFetchParser());			
	}

	public void fetch() {
		TaskType taskType = TaskType.FETCH_SP;
		this.logger.info("[{}]线程开始执行...", taskType.getTypeName());
		if (fetchList == null || fetchList.isEmpty()) {
			return;
		}
		List<JczqFetchResult> resultList = Lists.newArrayList();
		Date fetchTime = new Date();
		for (JczqAbstractFetchParser fetchParser : fetchList) {
			try {
				JczqFetchResult rs = fetchParser.fetch(null);
				if (rs != null) {
					resultList.add(rs);
					JczqContextHolder.updateRateData(rs.getPlayType(), PassMode.PASS, rs.getRateData(), fetchTime);
					JczqContextHolder.updateRateData(rs.getPlayType(), PassMode.SINGLE, rs.getRateData(), fetchTime);
				}else{
					this.logger.warn("[{}]玩法-[{}]抓取结果为空.", fetchParser.getPlayType().getText(),fetchParser.getPassMode());
					continue;
				}
			} catch (Exception e) {
				this.logger.warn("[" + fetchParser.getPlayType().getText() +"-"+ fetchParser.getPassMode() + "]玩法抓取发生异常.");//, e);
			}
		}
		SortedMap<String, JczqMatch> matchMap = buildAssembleMatchDTO(resultList);
		JczqContextHolder.updateMatchList(Lists.newArrayList(matchMap.values()));
	}
	private static SortedMap<String, JczqMatch> buildAssembleMatchDTO(List<JczqFetchResult> resultList) {
		SortedMap<String, JczqMatch> assembleMatchMap = Maps.newTreeMap();
		for (JczqFetchResult rs : resultList) {
			Map<String, JczqMatch> matchMap = rs.getMatchMap();
			for (Entry<String, JczqMatch> entry : matchMap.entrySet()) {
				String matchKey = entry.getKey();
				JczqMatch matchDTO = entry.getValue();

				JczqMatch assembleMatchDTO = assembleMatchMap.get(matchKey);
				if (assembleMatchDTO == null) {
					assembleMatchDTO = new JczqMatch();
				}
				if (StringUtils.isBlank(assembleMatchDTO.getGameColor())) {
					assembleMatchDTO.setGameColor("#"+matchDTO.getGameColor());
				}
				if (StringUtils.isBlank(assembleMatchDTO.getGameName())) {
					assembleMatchDTO.setGameName(matchDTO.getGameName());
				}
				if (StringUtils.isBlank(assembleMatchDTO.getGuestTeamName())) {
					assembleMatchDTO.setGuestTeamName(matchDTO.getGuestTeamName());
				}
				if (StringUtils.isBlank(assembleMatchDTO.getHomeTeamName())) {
					assembleMatchDTO.setHomeTeamName(matchDTO.getHomeTeamName());
				}
				if (assembleMatchDTO.getHandicap() == null || assembleMatchDTO.getHandicap() == 0) {
					assembleMatchDTO.setHandicap(matchDTO.getHandicap());
				}
				if (assembleMatchDTO.getLineId() == null || assembleMatchDTO.getLineId() == 0) {
					assembleMatchDTO.setLineId(matchDTO.getLineId());
				}
				if (assembleMatchDTO.getMatchDate() == null || assembleMatchDTO.getMatchDate() == 0) {
					assembleMatchDTO.setMatchDate(matchDTO.getMatchDate());
				}
				if (assembleMatchDTO.getMatchTime() == null) {
			        Calendar rightNow = Calendar.getInstance();
			        rightNow.setTime(matchDTO.getMatchTime());
			        rightNow.add(Calendar.MINUTE,1);//加一分钟
			        Date dt1=rightNow.getTime();
					assembleMatchDTO.setMatchTime(dt1);
				}

				if (assembleMatchDTO.getMatchKey() == null) {
					assembleMatchDTO.setMatchKey(matchKey);
				}

				Integer openFlag = assembleMatchDTO.getOpenFlag();
				Integer flag = matchDTO.getOpenFlag();
				if (openFlag == null)
					openFlag = 0;
				openFlag |= flag;
				assembleMatchDTO.setOpenFlag(openFlag);
				
				 ///修改时间大于现在的比赛不捉取
				if(matchDTO.getMatchTime().after(new Date())){
					assembleMatchMap.put(matchKey, assembleMatchDTO);
				}
			}
		}
		return assembleMatchMap;
	}
}
