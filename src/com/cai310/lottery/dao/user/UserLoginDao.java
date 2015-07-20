package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.UserLogin;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 用户登录记录DAO
 * 
 */
@Repository
public class UserLoginDao extends HibernateDao<UserLogin, Long> {
}
