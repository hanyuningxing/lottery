package com.cai310.lottery.dao.lottery.dczc;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.lottery.dczc.DczcSpInfo;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.orm.hibernate.HibernateDao;

@Repository
public class DczcSpInfoDao extends HibernateDao<DczcSpInfo, Long> {
	
	public DczcSpInfo getDczcSpInfo(PlayType playType,String periodNumber,Integer lineId){
		Criteria criteria=getSession().createCriteria(DczcSpInfo.class);
		criteria.add(Restrictions.eq("playType", playType));
		criteria.add(Restrictions.eq("periodNumber", periodNumber));
		criteria.add(Restrictions.eq("lineId", lineId));
		return (DczcSpInfo)criteria.uniqueResult();
	}
}
