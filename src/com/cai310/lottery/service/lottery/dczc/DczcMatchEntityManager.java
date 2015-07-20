package com.cai310.lottery.service.lottery.dczc;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.entity.lottery.dczc.DczcSpInfo;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.support.dczc.PlayType;

public interface DczcMatchEntityManager {

	DczcMatch getMatch(Long id);

	List<DczcMatch> findMatchs(Long periodId);

	Map<Integer, DczcMatch> findMatchMap(Long periodId);

	List<DczcMatch> findMatchs(String periodNumber);

	DczcMatch saveMatch(DczcMatch match);

	void saveMatchs(List<DczcMatch> matchs);

	/**
	 * 查找比赛时间小于指定时间的对阵序号集合
	 * 
	 * @param periodId 销售期ID
	 * @param leMatchTime 比赛时间
	 * @return 对阵序号集合
	 */
	List<Integer> findEndedMatchLineIds(Long periodId, Date leMatchTime);

	/**
	 * 更新单场sp值
	 * 
	 * @param infoList
	 */
	void updateSpInfo(DczcSpInfo dczcSpInfo);

	List<DczcSpInfo> getDczcSpInfo(PlayType playType, String periodNumer);

	List<DczcMatch> findMatchs(Long periodId, List<Integer> lineIdList);

	Map<Integer, DczcMatch> findMatchMap(Long periodId, List<Integer> lineIdList);

	List<DczcMatch> findMatchs(Integer matchDate);

	List<DczcMatch> findMatchsOfUnEnd();

}
