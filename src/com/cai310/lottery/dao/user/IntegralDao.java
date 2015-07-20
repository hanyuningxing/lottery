package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.user.Integral;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 积分设置的泛型DAO类.
 * 
 *
 */
@Repository
public class IntegralDao extends HibernateDao<Integral, Long>{

}
