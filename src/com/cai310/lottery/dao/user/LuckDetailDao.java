package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.LuckDetail;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 赠送明细DAO
 * 
 */
@Repository
public class LuckDetailDao extends HibernateDao<LuckDetail, Long> {
}
