package com.cai310.lottery.service.lottery.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.AutoFollowDetailState;
import com.cai310.lottery.common.AutoFollowState;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.AutoFollowDetailDao;
import com.cai310.lottery.dao.lottery.AutoFollowOrderDao;
import com.cai310.lottery.dao.lottery.AutoFollowQueueDao;
import com.cai310.lottery.entity.lottery.AutoFollowDetail;
import com.cai310.lottery.entity.lottery.AutoFollowOrder;
import com.cai310.lottery.entity.lottery.AutoFollowQueue;
import com.cai310.lottery.entity.lottery.AutoFollowQueueId;
import com.cai310.lottery.service.lottery.AutoFollowEntityManager;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;

/**
 * 自动跟单相关实体管理类
 * 
 */
@Service("autoFollowEntityManagerImpl")
@Transactional
public class AutoFollowEntityManagerImpl implements AutoFollowEntityManager {

	@Autowired
	protected AutoFollowOrderDao orderDao;

	@Autowired
	protected AutoFollowDetailDao detailDao;

	@Autowired
	protected AutoFollowQueueDao queueDao;

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public AutoFollowDetail getAutoFollowDetail(Long id) {
		return detailDao.get(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public AutoFollowOrder getAutoFollowOrder(Long id) {
		return orderDao.get(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public AutoFollowQueue getAutoFollowQueue(AutoFollowQueueId id) {
		return queueDao.get(id);
	}

	public AutoFollowDetail saveAutoFollowDetail(AutoFollowDetail detail) {
		return detailDao.save(detail);
	}

	public AutoFollowOrder saveAutoFollowOrder(AutoFollowOrder order) {
		return orderDao.save(order);
	}

	public AutoFollowQueue saveAutoFollowQueue(AutoFollowQueue queue) {
		return queueDao.save(queue);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean hasAutoFollow(final Long sponsorUserId, final Lottery lotteryType) {
		return (Boolean) orderDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.rowCount());
				criteria.add(Restrictions.eq("sponsorUserId", sponsorUserId));
				criteria.add(Restrictions.eq("lotteryType", lotteryType));
				criteria.add(Restrictions.eq("state", AutoFollowState.RUNNING));
				Integer count = (Integer) criteria.uniqueResult();
				return count != null && count > 0;
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<AutoFollowQueueId> findAutoFollowQueueId() {
		return (List<AutoFollowQueueId>) queueDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				ProjectionList propList = Projections.projectionList();
				propList.add(Projections.property("id.schemeId"), "schemeId");
				propList.add(Projections.property("id.lotteryType"), "lotteryType");
				criteria.setProjection(propList);
				criteria.setResultTransformer(new AliasToBeanResultTransformer(AutoFollowQueueId.class));
				return criteria.list();
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Long> findAutoFollowOrderId(final Long sponsorUserId, final Lottery lotteryType,
			final Byte lotteryPlayType) {
		return (List<Long>) orderDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("sponsorUserId", sponsorUserId));
				criteria.add(Restrictions.eq("lotteryType", lotteryType));
				if (lotteryPlayType != null)
					criteria.add(Restrictions.eq("lotteryPlayType", lotteryPlayType));
				criteria.add(Restrictions.eq("state", AutoFollowState.RUNNING));
				return criteria.list();
			}
		});
	}

	public void removeAutoFollowQueue(AutoFollowQueueId id) {
		queueDao.delete(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal countFollowCost(final Long orderId, final Long periodId) {
		return (BigDecimal) detailDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.sum("followCost"));
				criteria.add(Restrictions.eq("followOrderId", orderId));
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.add(Restrictions.eq("state", AutoFollowDetailState.SUCCESS));
				return criteria.uniqueResult();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findFollowOrderId(final Long schemeId) {
		return (List<Long>) detailDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("followOrderId"));
				criteria.add(Restrictions.eq("schemeId", schemeId));
				return criteria.list();
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean isAlreadyFollow(final Long orderId, final Long schemeId) {
		return (Boolean) detailDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.rowCount());
				criteria.add(Restrictions.eq("followOrderId", orderId));
				criteria.add(Restrictions.eq("schemeId", schemeId));
				Integer count = (Integer) criteria.uniqueResult();
				return count != null && count > 0;
			}
		});
	}
}
