package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.DrawingOrder;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 提款申请DAO
 * 
 */
@Repository
public class DrawingOrderDao extends HibernateDao<DrawingOrder, Long> {

}
