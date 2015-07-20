package com.cai310.lottery.dao.lottery;

import com.cai310.lottery.entity.lottery.PeriodData;
import com.cai310.orm.hibernate.HibernateDao;

public abstract class PeriodDataDao<T extends PeriodData> extends HibernateDao<T, Long> {

}
