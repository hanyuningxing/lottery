package com.cai310.lottery.dao.security;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.security.Authority;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 授权对象的泛型DAO.
 */
@Repository
public class AuthorityDao extends HibernateDao<Authority, Long> {

}
