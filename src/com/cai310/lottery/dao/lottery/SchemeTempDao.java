package com.cai310.lottery.dao.lottery;

import com.cai310.lottery.entity.lottery.SchemeTemp;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 免费保存方案DAO.
 * 
 */
public abstract class SchemeTempDao<TT extends SchemeTemp> extends HibernateDao<TT, Long> {

}
