package com.cai310.lottery.service.lottery.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.service.lottery.NumberSchemeEntityManager;

/**
 * 数字彩方案相关实现管理实现类.
 * 
 * @param <T> 方案类型
 */
@Transactional
public abstract class NumberSchemeEntityManagerImpl<T extends NumberScheme> extends SchemeEntityManagerImpl<T>
		implements NumberSchemeEntityManager<T> {

	public T getLastChaseScheme(long chaseId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.schemeClass);
		detachedCriteria.add(Restrictions.eq("chaseId", chaseId));
		detachedCriteria.addOrder(Order.desc("id"));
		List<T> schemeList = this.getSchemeDao().findByDetachedCriteria(detachedCriteria, 0, 1);
		if (schemeList != null && schemeList.size() > 0) {
			return schemeList.get(0);
		}
		return null;
	}
	
	
}
