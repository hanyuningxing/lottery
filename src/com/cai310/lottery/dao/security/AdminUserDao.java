package com.cai310.lottery.dao.security;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 用户对象的泛型DAO类.
 */
@Repository
public class AdminUserDao extends HibernateDao<AdminUser, Long> {
}
