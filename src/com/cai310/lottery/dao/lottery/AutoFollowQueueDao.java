package com.cai310.lottery.dao.lottery;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.lottery.AutoFollowQueue;
import com.cai310.lottery.entity.lottery.AutoFollowQueueId;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 自动跟单任务队列DAO
 * 
 */
@Repository
public class AutoFollowQueueDao extends HibernateDao<AutoFollowQueue, AutoFollowQueueId> {

}
