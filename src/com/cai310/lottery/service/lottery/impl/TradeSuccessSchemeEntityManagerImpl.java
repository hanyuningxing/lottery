package com.cai310.lottery.service.lottery.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.TradeSuccessSchemeDao;
import com.cai310.lottery.entity.lottery.TradeSuccessScheme;
import com.cai310.orm.hibernate.HibernateDao;

@Service("successSchemeManager")
@Transactional
public class TradeSuccessSchemeEntityManagerImpl extends QueryManagerImpl {

	@Autowired
	protected TradeSuccessSchemeDao successSchemeDao;

	public TradeSuccessScheme saveScheme(TradeSuccessScheme scheme) {
		return successSchemeDao.save(scheme);
	}
		
	public TradeSuccessScheme getScheme(Lottery lotteryType,Long schemeId){
		DetachedCriteria criteria = DetachedCriteria.forClass(TradeSuccessScheme.class);
		criteria.add(Restrictions.eq("lotteryType", lotteryType));
		criteria.add(Restrictions.eq("schemeId", schemeId));
		List<TradeSuccessScheme> list = successSchemeDao.findByDetachedCriteria(criteria,true);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	protected HibernateDao getHibernateDao() {
		return successSchemeDao;
	}

	
}
