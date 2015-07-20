package com.cai310.lottery.dao.user.agent;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.agent.AgentRelation;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * DAO
 * 
 */
@Repository
public class AgentRelationDao extends HibernateDao<AgentRelation, Long> {
}
