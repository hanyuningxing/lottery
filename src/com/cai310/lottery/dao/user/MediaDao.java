package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.Media;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 媒体DAO
 * 
 */
@Repository
public class MediaDao extends HibernateDao<Media, Long> {

}
