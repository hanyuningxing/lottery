package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.UserReport;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 用户反馈DAO
 * 
 */
@Repository
public class UserReportDao extends HibernateDao<UserReport, Long> {
    
}
