package com.cai310.lottery.service.lottery.jczq;

import java.util.Date;
import java.util.List;

import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.exception.DataException;

public interface JczqMatchEntityManager {

	
	JczqMatch getMatch(Long id);

	List<JczqMatch> findMatchsOfUnEnd();

	void saveMatchs(List<JczqMatch> matchs);

	Integer getFirstMatchDate();

	List<JczqMatch> findMatchs(Integer matchDate);

	List<JczqMatch> findMatchs(List<String> matchKeyList);
	
	/**
	 * 查找比赛时间小于指定时间的对阵序号集合
	 * 
	 * @param periodId 销售期ID
	 * @param leMatchTime 比赛时间
	 * @return 对阵序号集合
	 * @throws DataException 
	 */
	List<String> findEndedMatchLineIds(Long periodId, Date leMatchTime) throws DataException;

	List<JczqMatch> findMatchsOfTicketUnEnd();

	JczqMatch findLastMatchsOfEnd();
	
	/**
	 * 查找后台设置的足彩对阵，zcMatch true
	 * @return
	 */
	List<JczqMatch> findMatchsOfZc();
}
