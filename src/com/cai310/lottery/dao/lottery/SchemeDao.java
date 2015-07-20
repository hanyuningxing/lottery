package com.cai310.lottery.dao.lottery;

import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 方案泛型DAO.
 * 
 * @param <T> 所管理的方案类型
 */
public abstract class SchemeDao<T extends Scheme> extends HibernateDao<T, Long> {

}
