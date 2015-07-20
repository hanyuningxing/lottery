package com.cai310.lottery.dao.lottery.zc;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.lottery.zc.ZcMatch;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 足彩对阵DAO.
 * 
 */
@Repository
public abstract class ZcMatchDao<T extends ZcMatch> extends HibernateDao<T, Long> {

}
