package com.cai310.lottery.task.jclq;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.fetch.jclq.JclqAbstractFetchParser;
import com.cai310.lottery.fetch.jclq.JclqContextHolder;
import com.cai310.lottery.fetch.jclq.JclqDXFPassFetchParser;
import com.cai310.lottery.fetch.jclq.JclqFetchResult;
import com.cai310.lottery.fetch.jclq.JclqRFSFPassFetchParser;
import com.cai310.lottery.fetch.jclq.JclqSFCPassFetchParser;
import com.cai310.lottery.fetch.jclq.JclqSFPassFetchParser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class JclqFetchTask {
	private final transient Logger logger = LoggerFactory.getLogger(this.getClass());

	private static List<JclqAbstractFetchParser> fetchList;
	
	static{
		fetchList = Lists.newArrayList();
		fetchList.add(new JclqSFPassFetchParser());
		fetchList.add(new JclqRFSFPassFetchParser());
		fetchList.add(new JclqSFCPassFetchParser());
		fetchList.add(new JclqDXFPassFetchParser());
//		fetchList.add(new JclqSFSingleFetchParser());
//		fetchList.add(new JclqRFSFSingleFetchParser());
//		fetchList.add(new JclqSFCSingleFetchParser());
//		fetchList.add(new JclqDXFSingleFetchParser());		
	}
	
	
	public void fetch() {
		TaskType taskType = TaskType.FETCH_JCLQ;
		this.logger.info("[{}]线程开始执行...", taskType.getTypeName());
		if (fetchList == null || fetchList.isEmpty()) {
			return;
		}
		List<JclqFetchResult> resultList = Lists.newArrayList();
		Date fetchTime = new Date();
		for (JclqAbstractFetchParser fetchParser : fetchList) {
			try {
				JclqFetchResult rs = fetchParser.fetch(null);
				if (rs != null) {
					resultList.add(rs);
					JclqContextHolder.updateRateData(rs.getPlayType(), rs.getPassMode(), rs.getRateData(), fetchTime);
				}else{
					this.logger.warn("[{}]玩法-[{}]抓取结果为空.", fetchParser.getPlayType().getText(),fetchParser.getPassMode());
					continue;
				}
			} catch (Exception e) {
				this.logger.warn("[" + fetchParser.getPlayType().getText() +"-"+ fetchParser.getPassMode() + "]玩法抓取发生异常.");//, e);
			}
		}
		SortedMap<String, JclqMatch> matchMap = buildAssembleMatchDTO(resultList);
		JclqContextHolder.updateMatchList(Lists.newArrayList(matchMap.values()));
		//this.logger.info("[{}]抓取结束.", taskType.getTypeName());
	}

	private static SortedMap<String, JclqMatch> buildAssembleMatchDTO(List<JclqFetchResult> resultList) {
		SortedMap<String, JclqMatch> assembleMatchMap = Maps.newTreeMap();
		for (JclqFetchResult rs : resultList) {
			Map<String, JclqMatch> matchMap = rs.getMatchMap();
			for (Entry<String, JclqMatch> entry : matchMap.entrySet()) {
				String matchKey = entry.getKey();
				JclqMatch matchDTO = entry.getValue();

				JclqMatch assembleMatchDTO = assembleMatchMap.get(matchKey);
				if (assembleMatchDTO == null) {
					assembleMatchDTO = new JclqMatch();
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
				if (assembleMatchDTO.getSingleHandicap() == null || assembleMatchDTO.getSingleHandicap() == 0) {
					assembleMatchDTO.setSingleHandicap(matchDTO.getSingleHandicap());
				}
				if (assembleMatchDTO.getSingleTotalScore() == null || assembleMatchDTO.getSingleTotalScore() == 0) {
					assembleMatchDTO.setSingleTotalScore(matchDTO.getSingleTotalScore());
				}
				if (assembleMatchDTO.getLineId() == null || assembleMatchDTO.getLineId() == 0) {
					assembleMatchDTO.setLineId(matchDTO.getLineId());
				}
				if (assembleMatchDTO.getMatchDate() == null || assembleMatchDTO.getMatchDate() == 0) {
					assembleMatchDTO.setMatchDate(matchDTO.getMatchDate());
				}
				if (assembleMatchDTO.getMatchTime() == null) {
//					assembleMatchDTO.setMatchTime(matchDTO.getMatchTime());
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
