package com.cai310.lottery.dao.lottery;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 追号DAO.
 * 
 */
@Repository
public class ChasePlanDao extends HibernateDao<ChasePlan, Long> {

}
