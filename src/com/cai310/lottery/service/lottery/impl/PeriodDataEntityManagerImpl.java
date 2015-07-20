package com.cai310.lottery.service.lottery.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.PeriodDataDao;
import com.cai310.lottery.entity.lottery.PeriodData;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;
import com.cai310.orm.hibernate.HibernateDao;
import com.cai310.utils.ReflectionUtils;

@Transactional
public abstract class PeriodDataEntityManagerImpl<T extends PeriodData> extends QueryManagerImpl implements
		PeriodDataEntityManager<T> {

	protected abstract PeriodDataDao<T> getPeriodDataDao();

	protected T periodDataT;

	@Transactional(readOnly = true)
	public T getPeriodData(Long id) {
		return getPeriodDataDao().get(id);
	}

	public T save(T obj) {
		return getPeriodDataDao().save(obj);
	}

	@Override
	@Transactional(readOnly = true)
	protected HibernateDao getHibernateDao() {
		return getPeriodDataDao();
	}
	@Transactional(readOnly = true)
	public T getNewestResultPeriodData() {
		return (T) getPeriodDataDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.isNotNull("result"));
				criteria.addOrder(Order.desc("periodId"));
				criteria.setCacheable(true);
				List<T> l = criteria.list();
				if(null!=l&&!l.isEmpty()){
					return l.get(0);
				}
				return null;
			}
		});
	}
	@Transactional(readOnly = true)
	public List<T> getResultPeriodData(final int firstResult, final int maxResults) {
		return (List<T>) getPeriodDataDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.isNotNull("result"));
				criteria.addOrder(Order.desc("periodId"));
				criteria.setCacheable(true);
				if (firstResult >= 0) {
					criteria.setFirstResult(firstResult);
				}
				if (maxResults > 0) {
					criteria.setMaxResults(maxResults);
				}
				return criteria.list();
			}
		});
	}
	public Class<T> PeriodDataClass(){
		return ReflectionUtils.getSuperClassGenricType(getClass(), 0);
	}
	/**
	 * @return 所管理彩种类型
	 * @see com.cai310.lottery.common.Lottery
	 */
}
