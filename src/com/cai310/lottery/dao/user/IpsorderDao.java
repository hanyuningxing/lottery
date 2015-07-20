package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.Ipsorder;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 用户银行信息DAO
 * 
 */
@Repository
public class IpsorderDao extends HibernateDao<Ipsorder, Long> {
}
