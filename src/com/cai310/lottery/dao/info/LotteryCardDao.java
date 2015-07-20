package com.cai310.lottery.dao.info;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.info.LotteryCard;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 
 * 
 */
@Repository
public class LotteryCardDao extends HibernateDao<LotteryCard, String> {
	
}
