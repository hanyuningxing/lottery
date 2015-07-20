package com.cai310.lottery.service.user.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.user.UserDao;
import com.cai310.lottery.dao.user.UserWonRankDao;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserWonRank;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;

/**
 * 用户中奖排行实体管理类
 * @author jack
 *
 */
@Service("userWonRankEntityManager")
@Transactional
public class UserWonRankManagerImpl {

	@Autowired
	protected UserDao userDao;
	@Autowired
	protected UserWonRankDao userWonRankDao;
	
	public UserWonRank saveUserWonRank(UserWonRank userWonRank) { 
		return userWonRankDao.save(userWonRank);
	}
	
	public void resetUserWonRank(){
		userWonRankDao.resetUserWonRank();
	}
	
	/**
	 * 保存排行榜信息
	 * @param userWonRanks
	 * @param schemeManager
	 * @param schemes
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveUserWonRank(List<UserWonRank> userWonRanks,SchemeEntityManager schemeManager,List<Scheme> schemes,Integer currDateToInt,int statDays) {
		if(userWonRanks==null || userWonRanks.isEmpty())return;
		UserWonRank userWonRankOfPersistent = null;
		for(UserWonRank userWonRank : userWonRanks){
			DetachedCriteria criteria = DetachedCriteria.forClass(UserWonRank.class);
			criteria.add(Restrictions.eq("user", userWonRank.getUser()));
			criteria.add(Restrictions.eq("lottery", schemeManager.getLottery()));
			criteria.add(Restrictions.eq("ptOrdinal", userWonRank.getPtOrdinal()));
			criteria.add(Restrictions.eq("success", userWonRank.isSuccess()));
			criteria.add(Restrictions.eq("fadan", userWonRank.isFadan()));
			List<UserWonRank> rankList = userWonRankDao.findByDetachedCriteria(criteria,0,1);
			if (rankList != null && rankList.size() > 0) {
				userWonRankOfPersistent = rankList.get(0);
				userWonRankOfPersistent.setRankFlag(currDateToInt);
				switch(statDays){
				case 7:
					userWonRankOfPersistent.setCost_7(userWonRankOfPersistent.getCost_7().add(userWonRank.getCost()));
					userWonRankOfPersistent.setProfit_7(userWonRankOfPersistent.getProfit_7().add(userWonRank.getProfit()));
					userWonRankOfPersistent.setCost_30(userWonRankOfPersistent.getCost_30().add(userWonRank.getCost()));
					userWonRankOfPersistent.setProfit_30(userWonRankOfPersistent.getProfit_30().add(userWonRank.getProfit()));
					userWonRankOfPersistent.setCost_90(userWonRankOfPersistent.getCost_90().add(userWonRank.getCost()));
					userWonRankOfPersistent.setProfit_90(userWonRankOfPersistent.getProfit_90().add(userWonRank.getProfit()));
					break;
				case 30:
					userWonRankOfPersistent.setCost_30(userWonRankOfPersistent.getCost_30().add(userWonRank.getCost()));
					userWonRankOfPersistent.setProfit_30(userWonRankOfPersistent.getProfit_30().add(userWonRank.getProfit()));
					userWonRankOfPersistent.setCost_90(userWonRankOfPersistent.getCost_90().add(userWonRank.getCost()));
					userWonRankOfPersistent.setProfit_90(userWonRankOfPersistent.getProfit_90().add(userWonRank.getProfit()));
					break;
				case 90:
					userWonRankOfPersistent.setCost_90(userWonRankOfPersistent.getCost_90().add(userWonRank.getCost()));
					userWonRankOfPersistent.setProfit_90(userWonRankOfPersistent.getProfit_90().add(userWonRank.getProfit()));
					break;
				}			
			}else{
				User user = userWonRank.getUser();
				if(user==null){
					continue;					
				}
				userWonRank.setLottery(schemeManager.getLottery());
				BigDecimal initMoney = new BigDecimal(0);
				userWonRank.setCost_7(initMoney);
				userWonRank.setProfit_7(initMoney);
				userWonRank.setCost_30(initMoney);
				userWonRank.setProfit_30(initMoney);
				userWonRank.setCost_90(initMoney);
				userWonRank.setProfit_90(initMoney);
				switch(statDays){
				case 7:
					userWonRank.setCost_7(userWonRank.getCost());
					userWonRank.setProfit_7(userWonRank.getProfit());
					userWonRank.setCost_30(userWonRank.getCost());
					userWonRank.setProfit_30(userWonRank.getProfit());
					userWonRank.setCost_90(userWonRank.getCost());
					userWonRank.setProfit_90(userWonRank.getProfit());
					break;
				case 30:
					userWonRank.setCost_30(userWonRank.getCost());
					userWonRank.setProfit_30(userWonRank.getProfit());
					userWonRank.setCost_90(userWonRank.getCost());
					userWonRank.setProfit_90(userWonRank.getProfit());
					break;
				case 90:
					userWonRank.setCost_90(userWonRank.getCost());
					userWonRank.setProfit_90(userWonRank.getProfit());
					break;
				}
				userWonRank.setRankFlag(currDateToInt);
				userWonRankOfPersistent=userWonRank;
			}
			userWonRankDao.save(userWonRankOfPersistent);
		}
		
		//更新排行统计标识
		for(Scheme scheme : schemes){
			scheme = schemeManager.getScheme(scheme.getId());
			scheme.setRankFlag(currDateToInt);
			schemeManager.saveScheme(scheme);
		}
	}
	
	/**
	 * 查询用户排行版
	 * @param lottery 彩种
	 * @param ptOrdinal 玩法索引
	 * @param success 是否成功方案
	 * @param fadan 是否发单排行
	 * @return
	 */
	@Transactional(readOnly=true,  propagation = Propagation.SUPPORTS)
	public Pagination findUserWonRank(Lottery lottery,int ptOrdinal,Boolean success,Boolean fadan,int days,Pagination pagination){
		XDetachedCriteria criteria = new XDetachedCriteria(UserWonRank.class);
		criteria.setCacheable(true);
		if(lottery!=null){
			criteria.add(Restrictions.eq("lottery", lottery));
		}
		criteria.add(Restrictions.eq("ptOrdinal", ptOrdinal));
		if(success!=null){
			criteria.add(Restrictions.eq("success", success));
		}
		if(fadan!=null){
			criteria.add(Restrictions.eq("fadan", fadan));
		}
		criteria.add(Restrictions.gt("cost_"+days, new BigDecimal(0)));
		criteria.add(Restrictions.gt("profit_"+days, new BigDecimal(0)));
		criteria.addOrder(Order.desc("profit_"+days));
		return userWonRankDao.findByCriteriaAndPagination(criteria, pagination);
	}
}
