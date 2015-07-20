package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.User;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 用户DAO
 * 
 */
@Repository
public class UserDao extends HibernateDao<User, Long> {
	
}
