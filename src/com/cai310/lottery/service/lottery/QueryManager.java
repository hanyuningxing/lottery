package com.cai310.lottery.service.lottery;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface QueryManager {

	@SuppressWarnings("unchecked")
	List findByDetachedCriteria(DetachedCriteria criteria);

	@SuppressWarnings("unchecked")
	List findByDetachedCriteria(DetachedCriteria criteria, int firstResult, int maxResults);
}
