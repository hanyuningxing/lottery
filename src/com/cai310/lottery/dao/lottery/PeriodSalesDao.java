package com.cai310.lottery.dao.lottery;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 销售配置DAO
 * 
 */
@Repository
public class PeriodSalesDao extends HibernateDao<PeriodSales, PeriodSalesId> {

}
