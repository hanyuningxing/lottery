package com.cai310.lottery.service.lottery.zc.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.GameColorHolder;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.dao.lottery.zc.ZcMatchDao;
import com.cai310.lottery.entity.lottery.zc.ZcMatch;
import com.cai310.lottery.service.lottery.zc.ZcMatchEntityManager;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;

@Transactional
public abstract class ZcMatchEntityManagerImpl<T extends ZcMatch> implements ZcMatchEntityManager<T> {

	protected abstract ZcMatchDao<T> getZcMatchDao();

	protected abstract T[] getZcMatchInitArray();

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public T[] findMatchs(final Long periodId) {
		return (T[]) this.getZcMatchDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_ZC_MATCH_QUERY_CACHE_REGION);
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.addOrder(Order.asc("lineId"));
				return criteria.list().toArray(getZcMatchInitArray());
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public T[] findMatchs(final String periodNumber) {
		return (T[]) this.getZcMatchDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_ZC_MATCH_QUERY_CACHE_REGION);
				criteria.add(Restrictions.eq("periodNumber", periodNumber));
				criteria.addOrder(Order.asc("lineId"));
				return criteria.list().toArray(getZcMatchInitArray());
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public T getMatch(Long id) {
		return this.getZcMatchDao().get(id);
	}

	public T saveMatch(T match) {
		if (StringUtils.isBlank(match.getGameColor())){
			match.setGameColor(GameColorHolder.getColor(match.getGameName()));
		}
		return this.getZcMatchDao().save(match);
	}

	public void saveMatchs(T[] matchs) {
		int lineId = 0;
		for (T match : matchs) {
			match.setLineId(lineId);
			match = saveMatch(match);
			lineId++;
		}
	}

}
