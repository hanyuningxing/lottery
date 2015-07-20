package com.cai310.lottery.service.lottery.jclq;

import java.util.Date;
import java.util.List;

import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.exception.DataException;

public interface JclqMatchEntityManager {

	
	JclqMatch getMatch(Long id);

	List<JclqMatch> findMatchsOfUnEnd();

	void saveMatchs(List<JclqMatch> matchs);

	Integer getFirstMatchDate();

	List<JclqMatch> findMatchs(Integer matchDate);

	List<JclqMatch> findMatchs(List<String> matchKeyList);
	/**
	 * 查找比赛时间小于指定时间的对阵序号集合
	 * 
	 * @param periodId 销售期ID
	 * @param leMatchTime 比赛时间
	 * @return 对阵序号集合
	 * @throws DataException 
	 */
	List<String> findEndedMatchLineIds(Long periodId, Date leMatchTime) throws DataException;

	List<JclqMatch> findMatchsOfTicketUnEnd();

	JclqMatch findLastMatchsOfEnd();
}
