package com.cai310.lottery.builder;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.AgentLotteryType;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.user.AgentEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.support.agent.UserRebateLimit;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
@Component("initAgent")
public class InitAgent{
//	@Autowired
//	AgentEntityManager agentEntityManager;
//	@Autowired
//	UserEntityManager userEntityManager;
//	@Autowired
//	QueryService queryService;
//	private Map<Long,UserRebate> userRebateMap = Maps.newHashMap();
//	@PostConstruct
//	public void initAgent(){
//		//初始化网站第一个用户
//		NumberFormat TWO_NF = new DecimalFormat("0.0");
//				List<UserRebate> baodiUserRebateList  = agentEntityManager.findAllUserRebate(Constant.SITE_BAODI_USER_ID);
//				if(null==baodiUserRebateList||baodiUserRebateList.isEmpty()){
//					//初始化网站第一个用户
//					User baodiUser = userEntityManager.getUser(Constant.SITE_BAODI_USER_ID);
//					for (AgentLotteryType agentLotteryType : AgentLotteryType.values()) {
//						UserRebate userRebate = new UserRebate();
//						userRebate.setUserId(baodiUser.getUserId());
//						userRebate.setAgentLotteryType(agentLotteryType);
//						userRebate.setUserName(baodiUser.getUserName());
//						userRebate.setRealName(null==baodiUser.getInfo()?"":baodiUser.getInfo().getRealName());
//						//设置限制
//						userRebate.setRebate(agentLotteryType.getAdminMaxRebate());
//						List<UserRebateLimit> userRebateLimitList = Lists.newArrayList();
//						for (Double i = agentLotteryType.getMinRebate()+0.1; i <=agentLotteryType.getAdminMaxRebate();) {
//							UserRebateLimit userRebateLimit = new UserRebateLimit();
//							userRebateLimit.setRebate(TWO_NF.format(i));
//							userRebateLimit.setGroupNumber(1);
//							userRebateLimit.setLimitNumber(100000000);
//							userRebateLimitList.add(userRebateLimit);
//							i = i+Double.valueOf("0.1");
//						}
//						userRebate.setlimitNumber(userRebateLimitList);
//						agentEntityManager.saveUserRebete(userRebate);
//
//					}
//					AgentRelation agentRelation = new AgentRelation();
//					agentRelation.setUserId(baodiUser.getId());
//					agentRelation.setAgentId(1L);
//					agentRelation.setGroupId(1L);
//					agentRelation.setUserName(baodiUser.getUserName());
//					agentRelation.setRealName(null==baodiUser.getInfo()?"":baodiUser.getInfo().getRealName());
//					agentRelation.setPos(0);
//					agentEntityManager.saveAgentRelation(agentRelation);
//				}
//				//把网站所有用户的下属缓存起来
//				DetachedCriteria criteria = DetachedCriteria.forClass(UserRebate.class);
//				criteria.add(Restrictions.eq("agentLotteryType", AgentLotteryType.JC));
//				List<UserRebate> userRebateList =  queryService.findByDetachedCriteria(criteria);
//				for (UserRebate userRebate : userRebateList) {
//					userRebateMap.put(userRebate.getUserId(), userRebate);
//				}
//				
//	}
//	public Long getUserGroupNum(Long userId) {
//		UserRebate userRebate = userRebateMap.get(userId);
//		if(null!=userRebate){
//			return userRebate.getGroupCount();
//		}else{
//			userRebate = agentEntityManager.findUserRebate(userId, AgentLotteryType.JC);
//			if(null!=userRebate){
//				return userRebate.getGroupCount();
//			}else{
//				return 0L;
//			}
//		}
//	}
//	public Long getUserAgentNum(Long userId) {
//		UserRebate userRebate = userRebateMap.get(userId);
//		if(null!=userRebate){
//			return userRebate.getAgentCount();
//		}else{
//			userRebate = agentEntityManager.findUserRebate(userId, AgentLotteryType.JC);
//			if(null!=userRebate){
//				return userRebate.getAgentCount();
//			}else{
//				return 0L;
//			}
//		}
//	}
}
