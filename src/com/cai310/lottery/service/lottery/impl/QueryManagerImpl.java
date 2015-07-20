package com.cai310.lottery.service.lottery.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.service.lottery.QueryManager;
import com.cai310.orm.hibernate.HibernateDao;

public abstract class QueryManagerImpl implements QueryManager {

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	protected abstract HibernateDao getHibernateDao();

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List findByDetachedCriteria(DetachedCriteria criteria) {
		return getHibernateDao().findByDetachedCriteria(criteria);
	}

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List findByDetachedCriteria(DetachedCriteria criteria, int firstResult, int maxResults) {
		return getHibernateDao().findByDetachedCriteria(criteria, firstResult, maxResults);
	}
}
