package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.Media;
import com.cai310.lottery.entity.user.Popu;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 推广DAO
 * 
 */
@Repository
public class PopuDao extends HibernateDao<Popu, Long> {

}
