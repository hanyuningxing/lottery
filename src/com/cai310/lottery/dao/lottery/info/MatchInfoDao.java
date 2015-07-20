package com.cai310.lottery.dao.lottery.info;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.info.MatchInfo;
import com.cai310.orm.hibernate.HibernateDao;

@Repository
public class MatchInfoDao extends HibernateDao<MatchInfo, Long> {

	/**
	 * 
	 * @param Lottery
	 * @param periodId
	 * @param lineId
	 * @return
	 */
	public MatchInfo findMatchInfo(Lottery Lottery,Long periodId,Integer lineId){
		Criteria criteria=getSession().createCriteria(MatchInfo.class);
		criteria.add(Restrictions.eq("lottery", Lottery));
		criteria.add(Restrictions.eq("periodId", periodId));
		criteria.add(Restrictions.eq("lineId", lineId));
		criteria.setMaxResults(1);
		return (MatchInfo)criteria.uniqueResult();
	}
}
