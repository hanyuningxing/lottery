package com.cai310.lottery.dao.lottery;

import com.cai310.lottery.entity.lottery.MissDataInfo;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 遗漏数据Dao
 * 
 * @param <T>
 */
public class MissDataDao<T extends MissDataInfo> extends HibernateDao<T, Long> {

	public T getByPeriodId(Long periodId) {
		return findUniqueBy("periodId", periodId);
	}
}
