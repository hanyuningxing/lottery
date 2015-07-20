package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.UserNewestLog;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 用户最新动态日志DAO
 * @author jack
 */
@Repository
public class UserNewestLogDao extends HibernateDao<UserNewestLog, Long> {
	
}
