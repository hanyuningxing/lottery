package com.cai310.lottery.dao.user;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.UserGrade;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 用户个人信息DAO
 * 
 */
@Repository
public class UserGradeDao extends HibernateDao<UserGrade, Long> {
}
