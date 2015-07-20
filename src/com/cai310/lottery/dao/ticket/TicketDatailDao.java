package com.cai310.lottery.dao.ticket;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.ticket.TicketDatail;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 标准DAO
 * 
 */
@Repository
public class TicketDatailDao extends HibernateDao<TicketDatail, Long> {
	
}
