package com.cai310.lottery.dao.lottery;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.lottery.GameColor;
import com.cai310.orm.hibernate.HibernateDao;

@Repository
public class GameColorDao extends HibernateDao<GameColor, String> {

}
