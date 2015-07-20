package com.cai310.lottery.dao.ticket;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.ticket.TicketPlatformInfo;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 平台信息DAO
 * 
 */
@Repository
public class TicketPlatformInfoDao extends HibernateDao<TicketPlatformInfo, Long> {
	
}
