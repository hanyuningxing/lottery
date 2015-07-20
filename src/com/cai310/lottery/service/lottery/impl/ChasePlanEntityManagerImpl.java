package com.cai310.lottery.service.lottery.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.ChaseState;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.ChasePlanDao;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.ChasePlanEntityManager;
import com.cai310.orm.hibernate.ExecuteCallBack;

@Service("chasePlanEntityManagerImpl")
@Transactional
public class ChasePlanEntityManagerImpl implements ChasePlanEntityManager {

	@Autowired
	protected ChasePlanDao chasePlanDao;

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ChasePlan getChasePlan(Long id) {
		return chasePlanDao.get(id);
	}

	public ChasePlan saveChasePlan(ChasePlan entity) {
		return chasePlanDao.save(entity);
	}

	public List<Long> findChasePlanIdOfRunning(long curPeriodId, Lottery lotteryType, Boolean wonStop) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChasePlan.class);
		criteria.setProjection(Projections.property("id"));
		criteria.add(Restrictions.eq("curPeriodId", curPeriodId));
		criteria.add(Restrictions.eq("lotteryType", lotteryType));
		criteria.add(Restrictions.eq("state", ChaseState.RUNNING));
		if (wonStop != null) {
			criteria.add(Restrictions.eq("wonStop", wonStop.booleanValue()));
		}
		return chasePlanDao.findByDetachedCriteria(criteria);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<ChasePlan> findChasePlan(final Long userId, final Integer start, final Integer count, final ChaseState chaseState, final Lottery lotteryType) {
		if (userId == null)
			throw new ServiceException("用户ID不能为空.");
		return (List<ChasePlan>) chasePlanDao.execute(new ExecuteCallBack() {
			public Object execute(Session session) {
				Criteria criteria = session.createCriteria(ChasePlan.class, "m");
				criteria.setFirstResult(start);
				criteria.setMaxResults(count);
				criteria.add(Restrictions.eq("userId", userId));
				if(null!=chaseState){
					criteria.add(Restrictions.eq("state", chaseState));
				}
				if(null!=lotteryType){
					criteria.add(Restrictions.eq("lotteryType", lotteryType));
				}
				criteria.addOrder(Order.desc("id"));
				return criteria.list();
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Integer statUnHandleChase(long curPeriodId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChasePlan.class);
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("curPeriodId", curPeriodId));
		criteria.add(Restrictions.eq("state", ChaseState.RUNNING));
		List<Integer> list = chasePlanDao.findByDetachedCriteria(criteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	
}
