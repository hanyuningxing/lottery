package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.FundDetail;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 资金明细DAO
 * 
 */
@Repository
public class FundDetailDao extends HibernateDao<FundDetail, Long> {

}
