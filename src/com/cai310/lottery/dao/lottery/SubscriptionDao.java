package com.cai310.lottery.dao.lottery;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.lottery.Subscription;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 认购DAO
 * 
 */
@Repository
public class SubscriptionDao extends HibernateDao<Subscription, Long> {

}
