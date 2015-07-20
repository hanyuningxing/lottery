package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.Prepayment;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 预付款DAO
 * 
 */
@Repository
public class PrepaymentDao extends HibernateDao<Prepayment, Long> {

}
