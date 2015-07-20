package com.cai310.lottery.dao.user;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.user.UserWonRank;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 用户中奖排行DAO
 * 
 */
@Repository
public class UserWonRankDao extends HibernateDao<UserWonRank, Long> {

	/**
	 * 重置用户排行榜数据
	 */
	public void resetUserWonRank() {
		StringBuilder sb = new StringBuilder();
		sb.append("update ").append(UserWonRank.TABLE_NAME).append(" set cost_7=0,profit_7=0,cost_30=0,profit_30=0,cost_90=0,profit_90=0");
		SQLQuery sq = getSession().createSQLQuery(sb.toString());
		sq.executeUpdate();
	}
}
