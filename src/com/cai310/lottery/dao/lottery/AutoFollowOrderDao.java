package com.cai310.lottery.dao.lottery;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.lottery.AutoFollowOrder;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 自动跟单DAO
 * 
 */
@Repository
public class AutoFollowOrderDao extends HibernateDao<AutoFollowOrder, Long> {

}
