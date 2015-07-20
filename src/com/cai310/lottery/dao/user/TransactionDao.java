package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.Transaction;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 交易DAO
 * 
 */
@Repository
public class TransactionDao extends HibernateDao<Transaction, Long> {

}
