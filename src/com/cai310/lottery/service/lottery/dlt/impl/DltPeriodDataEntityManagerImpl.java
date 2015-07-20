package com.cai310.lottery.service.lottery.dlt.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.PeriodDataDao;
import com.cai310.lottery.dao.lottery.dlt.DltPeriodDataDao;
import com.cai310.lottery.entity.lottery.dlt.DltPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;

@Service("dltPeriodDataEntityManager")
@Transactional
public class DltPeriodDataEntityManagerImpl extends PeriodDataEntityManagerImpl<DltPeriodData> {

	@Autowired
	private DltPeriodDataDao periodDataDao;

	@Override
	@Transactional(readOnly = true)
	protected PeriodDataDao<DltPeriodData> getPeriodDataDao() {
		return periodDataDao;
	}

	public DltPeriodData getEntityInstance() {
		return new DltPeriodData();
	}
	@Transactional(readOnly = true)
	public DltPeriodData getNewestPoolPeriodData() {
		return (DltPeriodData) getHibernateDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.isNotNull("prizePool"));
				criteria.addOrder(Order.desc("periodId"));
				List<DltPeriodData> l = criteria.list();
				if(null!=l&&!l.isEmpty()){
					return l.get(0);
				}
				return null;
			}
		});
	}

	@Override
	public Lottery getLottery() {
		return Lottery.DLT;
	}
}
