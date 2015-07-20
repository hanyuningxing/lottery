package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.RandomMessage;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * DAO
 * 
 */
@Repository
public class RandomMessageDao extends HibernateDao<RandomMessage, Long> {

}
