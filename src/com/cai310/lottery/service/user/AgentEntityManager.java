package com.cai310.lottery.service.user;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.AgentDetailType;
import com.cai310.lottery.common.AgentLotteryType;
import com.cai310.lottery.common.AgentUserType;
import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.FundMode;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PlatformInfo;
import com.cai310.lottery.common.PrepaymentType;
import com.cai310.lottery.common.TransactionType;
import com.cai310.lottery.entity.lottery.keno.KenoScheme;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.user.BankInfo;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserInfo;
import com.cai310.lottery.entity.user.UserReport;
import com.cai310.lottery.entity.user.agent.AgentRebate;
import com.cai310.lottery.entity.user.agent.AgentRelation;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.orm.Pagination;

/**
 * 用户代理
 * 
 */
public interface AgentEntityManager {
	void returnRebate(Long schemeId,Lottery lottery);
	AgentRebate saveAgentRebate(AgentRebate userRebate);
	AgentRelation saveAgentRelation(AgentRelation agentRelation);
	Boolean isAgent(final Long userId);
	Boolean isUserAgent(final Long userId,final Long agentId);
	Boolean isUserGroup(final Long userId,final Long groupId);
	void recordAgent(User user,Lottery lottery,AgentDetailType agentDetailType,Date time,BigDecimal money,Long transactionId);
	 /**
	  * 
	  * @param userId
	  * @param agentLotteryType
	  * @return
	  */
	 AgentRebate findAgentRebate(Long userId,AgentLotteryType agentLotteryType);
	 
	 User saveAgentUser(final Long userId,User user, UserInfo info, BankInfo bank,List<AgentRebate> userRebateList,String password);
	 
	 void createAgentIfNull(final Long userId,Double rebate);
	 
	 Pagination findAgentGroupInfo(Long userId, Long findUserId, Date dateStart, Date dateEnd,
			Pagination pagination, AgentLotteryType agentLotteryType);
	Pagination findAgentGroupInfoSum(Long userId, Long findUserId,
			Pagination pagination, AgentUserType agentUserType,Date start, Date end);
	
	void updateAgentRebate(List<AgentRebate> oprRebateMap);
	
	void createAgentWithAgent(final Long userId,Double rebate,final Long agentUserId);
}
