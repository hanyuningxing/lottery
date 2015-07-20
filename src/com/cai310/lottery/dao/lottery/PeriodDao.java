package com.cai310.lottery.dao.lottery;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.lottery.Period;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 销售期DAO
 * 
 */
@Repository
public class PeriodDao extends HibernateDao<Period, Long> {

}
