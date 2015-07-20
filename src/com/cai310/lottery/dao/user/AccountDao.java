package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.Account;
import com.cai310.lottery.entity.user.agent.AgentFundDetail;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * DAO
 * 
 */
@Repository
public class AccountDao extends HibernateDao<Account, Long> {
	
}
