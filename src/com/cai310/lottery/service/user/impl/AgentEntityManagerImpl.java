package com.cai310.lottery.service.user.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.AgentDetailType;
import com.cai310.lottery.common.AgentLotteryType;
import com.cai310.lottery.common.AgentUserType;
import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.FundMode;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SubscriptionState;
import com.cai310.lottery.dao.user.AccountDao;
import com.cai310.lottery.dao.user.BankInfoDao;
import com.cai310.lottery.dao.user.FundDetailDao;
import com.cai310.lottery.dao.user.UserDao;
import com.cai310.lottery.dao.user.UserInfoDao;
import com.cai310.lottery.dao.user.agent.AgentFundDetailDao;
import com.cai310.lottery.dao.user.agent.AgentRebateDao;
import com.cai310.lottery.dao.user.agent.AgentRelationDao;
import com.cai310.lottery.dto.user.AgentInfoDTO;
import com.cai310.lottery.dto.user.AgentRelationRebate;
import com.cai310.lottery.dto.user.AgentSumDTO;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.Subscription;
import com.cai310.lottery.entity.user.Account;
import com.cai310.lottery.entity.user.BankInfo;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserInfo;
import com.cai310.lottery.entity.user.agent.AgentFundDetail;
import com.cai310.lottery.entity.user.agent.AgentRebate;
import com.cai310.lottery.entity.user.agent.AgentRelation;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.user.AgentEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.web.controller.forum.ForumController;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;
import com.cai310.utils.NumUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 代理用户实体相关服务类
 * 
 */
@Service("agentEntityManager")
@Transactional
public class AgentEntityManagerImpl implements AgentEntityManager{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private List<SchemeEntityManager> schemeEntityManagerList;
	private SchemeEntityManager getSchemeEntityManager(Lottery lotteryType) {
		for (SchemeEntityManager manager : schemeEntityManagerList) {
			if (manager.getLottery().equals(lotteryType))
				return manager;
		}
		return null;
	}
	@Autowired
	private QueryService queryService;
	@Autowired
	protected AgentRelationDao agentRelationDao;
	@Autowired
	protected AgentFundDetailDao agentFundDetailDao;
	@Autowired
	protected AccountDao accountDao;
	@Autowired
	protected AgentRebateDao agentRebateDao;
	@Autowired
	protected FundDetailDao fundDetailDao;
	@Autowired
	protected UserDao userDao;
	@Autowired
	protected UserInfoDao userInfoDao;
	@Autowired
	protected BankInfoDao bankInfoDao;
	@Autowired
	protected UserEntityManager userEntityManager;
	
	
	public void returnRebate(Long schemeId,Lottery lottery){
		Scheme scheme = getSchemeEntityManager(lottery).getScheme(schemeId);
		if(null!=scheme&&SchemePrintState.SUCCESS.equals(scheme.getSchemePrintState())){
            if(!isReturnedRebate(schemeId,lottery)){
				List<Subscription> subscriptionList = getSchemeEntityManager(lottery).findSubscriptionsOfScheme(scheme.getId(),SubscriptionState.NORMAL);
				for (Subscription subscription : subscriptionList) {
					User user = this.getUser(subscription.getUserId());
					recordAgent(user, subscription.getLotteryType(), AgentDetailType.BUY, subscription.getCreateTime(), subscription.getCost(),scheme.getId());
				}
            }
		}
	}
	
	public void recordAgent(User user,Lottery lottery,AgentDetailType agentDetailType,Date time,BigDecimal money,Long schemeId){
		if(!user.getId().equals(Constant.SITE_BAODI_USER_ID)){
			//不是网站保底账户派发佣金
			if(AgentDetailType.BUY.equals(agentDetailType)){
				///如果是认购的成功佣金
				LinkedHashMap<User, AgentRelationRebate> agentRebateMap = this.findAgentUser(user, lottery);
				if(null!=agentRebateMap&&!agentRebateMap.isEmpty()){
					try {
						this.saveAgentFundDetail(user, money, AgentDetailType.BUY, AgentLotteryType.getAgentLotteryType(lottery), null,schemeId,lottery);
						sendAgentRebate(agentRebateMap,money, lottery);
					} catch (DataException e) {
						throw new ServiceException(e.getMessage());
					}
				}
			}else{
				this.saveAgentFundDetail(user, money, agentDetailType, null, null, null, null);
			}
		}
	}
	
	
	public void sendAgentRebate(LinkedHashMap<User, AgentRelationRebate> agentRebateMap,BigDecimal amount,Lottery lottery) throws DataException{
		Set<User> agentRebateKey = agentRebateMap.keySet();
		if(agentRebateKey.size()>0){
			//多个上级
			User buy_user = null;
			LinkedList<User> userList = Lists.newLinkedList();
			for (User user : agentRebateKey) {
				userList.add(user);
				buy_user = user;
			}
			User userUp = null;
			AgentRelationRebate agentRebateUp = null;
			User userDown = null;
			AgentRelationRebate agentRebateDown = null;
			Double rebatetemp  = null;
			BigDecimal rebateAmount = null;
			for (int i = 0; i < userList.size(); i++) {
				if(i==userList.size()-1){
					//最后一个
					userUp =  userList.get(i);///
					agentRebateUp =  agentRebateMap.get(userUp);
					rebatetemp = agentRebateUp.getRebate() ;///上级的返点
					rebateAmount = amount.multiply(BigDecimal.valueOf(rebatetemp)).divide(BigDecimal.valueOf(100));///上级的佣金
					if(null!=rebateAmount.abs()&&rebateAmount.abs().doubleValue()>0){
						this.saveAgentFundDetail(userUp, rebateAmount, AgentDetailType.REBATE, AgentLotteryType.getAgentLotteryType(lottery), rebatetemp, null, null);
						userEntityManager.oprUserMoney(userUp, rebateAmount.abs(), "用户{"+buy_user.getUserName()+"}消费金额为{"+amount+"},佣金返回用户返点为{"+NumUtils.format(rebatetemp, 4,4)+"},返回佣金{"+NumUtils.format(rebateAmount.abs(), 4,4)+"}",  FundMode.IN, FundDetailType.REBATE, null);
					}
				}else{
					userUp =  userList.get(i);///上一级用户
					agentRebateUp =  agentRebateMap.get(userUp);
					userDown =  userList.get(i+1);//后一个用户，这里不是最后一个。确保有用户在后面
					agentRebateDown =  agentRebateMap.get(userDown);
					
					rebatetemp = agentRebateUp.getRebate() - agentRebateDown.getRebate();///上级的返点
					rebateAmount = amount.multiply(BigDecimal.valueOf(rebatetemp)).divide(BigDecimal.valueOf(100));///用户的佣金
					//个人消费统计
					if(null!=rebateAmount.abs()&&rebateAmount.abs().doubleValue()>0){
						this.saveAgentFundDetail(userUp, rebateAmount, AgentDetailType.REBATE, AgentLotteryType.getAgentLotteryType(lottery), rebatetemp, null, null);
						userEntityManager.oprUserMoney(userUp, rebateAmount.abs(), "用户{"+buy_user.getUserName()+"}消费金额为{"+amount+"},佣金返回用户返点为{"+agentRebateUp.getRebate()+"-"+agentRebateDown.getRebate()+"},返回佣金{"+NumUtils.format(rebateAmount.abs(), 4,4)+"}",  FundMode.IN, FundDetailType.REBATE, null);
						
					}
				}

			}
		}
	}
	
	
	public User getUser(Long id) {
		return userDao.get(id);
	}
	public AgentFundDetail saveAgentFundDetail(User user,BigDecimal money, AgentDetailType detailType,AgentLotteryType lotteryType,Double rebate,Long schemeId,Lottery lottery){
		AgentFundDetail agentFundDetail = new AgentFundDetail();
		agentFundDetail.setUserId(user.getId());
		agentFundDetail.setUserName(user.getUserName());
		agentFundDetail.setMoney(money);
		agentFundDetail.setDetailType(detailType);
		Account account = findAccount(user.getId());
		if(null==account){
			//新建一个用户子账户
			account = new Account(user);
			account = this.accountDao.save(account);
		}
		agentFundDetail.setAccountId(account.getId());
		agentFundDetail.setLotteryType(lotteryType);
		agentFundDetail.setRebate(rebate);
		agentFundDetail.setSchemeId(schemeId);
		agentFundDetail.setLottery(lottery);
		return this.agentFundDetailDao.save(agentFundDetail);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Account findAccount(final Long userId){
		return this.accountDao.findUniqueBy("userId", userId);
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Boolean isReturnedRebate(final Long schemeId,final Lottery lottery){
		return (Boolean)this.agentFundDetailDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("schemeId", schemeId));
				criteria.add(Restrictions.eq("lottery", lottery));
				List list = criteria.list();
				if(null==list||list.isEmpty())return false;
				return true;
			}
		});
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public AgentRebate findAgentRebate(final Long userId,final AgentLotteryType agentLotteryType){
		return (AgentRebate)agentRebateDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("userId", userId));
				criteria.add(Restrictions.eq("agentLotteryType", agentLotteryType));
				List list = criteria.list();
				if(null!=list&&!list.isEmpty())return list.get(0);
				return null;
			}
		});
	}
	public void createAgentIfNull(final Long userId,Double rebate){
		List<AgentRebate> agentRebateList  = this.findAllAgentRebate(userId);
		if(null==agentRebateList||agentRebateList.isEmpty()){
			//初始化
			User user = userEntityManager.getUser(userId);
			for (AgentLotteryType agentLotteryType : AgentLotteryType.values()) {
				AgentRebate agentRebate = new AgentRebate();
				agentRebate.setUserId(user.getUserId());
				agentRebate.setAgentLotteryType(agentLotteryType);
				agentRebate.setUserName(user.getUserName());
				//设置限制
				agentRebate.setRebate(rebate);
				this.saveAgentRebate(agentRebate);

			}
			AgentRelation agentRelation = new AgentRelation();
			agentRelation.setUserId(user.getId());
			agentRelation.setAgentId(user.getId());
			agentRelation.setGroupId(user.getId());
			agentRelation.setUserName(user.getUserName());
			agentRelation.setRealName(null==user.getInfo()?"":user.getInfo().getRealName());
			agentRelation.setPos(0);
			saveAgentRelation(agentRelation);
		}
	}
	public void createAgentWithAgent(final Long userId,Double rebate,final Long agentUserId){
		List<AgentRebate> agentRebateList  = this.findAllAgentRebate(userId);
			if(null==agentRebateList||agentRebateList.isEmpty()){
			User agentUser = userDao.get(agentUserId);
			User user = userDao.get(userId);
			List<AgentRelation> userAgentList = this.findUserAgentRelation(agentUser.getId());
			User userTemp = null;
			int i=0;
			AgentRelation agentRelation = null;
			for (AgentRelation a: userAgentList) {
				userTemp =  this.getUser(a.getGroupId());
				agentRelation = new AgentRelation();
				agentRelation.setUserId(user.getId());
				agentRelation.setAgentId(agentUser.getId());
				agentRelation.setGroupId(userTemp.getId());
				agentRelation.setUserName(user.getUserName());
				agentRelation.setRealName(null==user.getInfo()?"":user.getInfo().getRealName());
				agentRelation.setPos(i);
				agentRelationDao.save(agentRelation);
				i++;
			}
			agentRelation = new AgentRelation();
			agentRelation.setUserId(user.getId());
			agentRelation.setAgentId(agentUser.getId());
			agentRelation.setGroupId(user.getId());
			agentRelation.setUserName(user.getUserName());
			agentRelation.setRealName(null==user.getInfo()?"":user.getInfo().getRealName());
			agentRelation.setPos(i);
			agentRelationDao.save(agentRelation);
			for (AgentLotteryType agentLotteryType : AgentLotteryType.values()) {
				AgentRebate agentRebate = this.findAgentRebate(agentUserId, agentLotteryType);
				if(null!=agentRebate&&null!=rebate&&rebate-agentRebate.getRebate()>0){
					if(null==user)throw new ServiceException("设置点位大于上级点位");
				}
				AgentRebate userRebate = new AgentRebate();
				userRebate.setUserId(user.getUserId());
				userRebate.setAgentLotteryType(agentLotteryType);
				userRebate.setUserName(user.getUserName());
				//设置限制
				userRebate.setRebate(rebate);
				this.saveAgentRebate(userRebate);
	
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<AgentRebate> findAllAgentRebate(final Long userId){
		return (List<AgentRebate>)agentRebateDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("userId", userId));
				List list = criteria.list();
				return list;
			}
		});
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<AgentRelation> findUserAgentRelation(final Long userId){
		return (List<AgentRelation>)agentRelationDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("userId", userId));
				criteria.addOrder(Order.asc("pos"));
				return criteria.list();
			}
		});
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public LinkedHashMap<User, AgentRelationRebate> findAgentUser(User user,Lottery lottery){
		if(null==user)throw new ServiceException("消费用户为空");
		User userTemp = null;
		LinkedHashMap<User,AgentRelationRebate> agentRelationRebateMap = Maps.newLinkedHashMap();
		List<AgentRelation> userAgentList = this.findUserAgentRelation(user.getId());
		if(null!=userAgentList){
			for (AgentRelation agentRelation : userAgentList) {
				userTemp = this.getUser(agentRelation.getGroupId());
				if(null!=userTemp){
					AgentRelationRebate agentRelationRebate = new AgentRelationRebate();
					if(null!=lottery){
						agentRelationRebate.setAgentLotteryType(AgentLotteryType.getAgentLotteryType(lottery));
						if(userTemp.getId().equals(Constant.SITE_BAODI_USER_ID)){
							 agentRelationRebate.setRebate(Constant.MAXREBATE);
						}else{
						    agentRelationRebate.setRebate(this.findAgentRebate(userTemp.getId(), AgentLotteryType.getAgentLotteryType(lottery)).getRebate());
						}
					}
					agentRelationRebate.setUserId(userTemp.getId());
					agentRelationRebate.setUserName(userTemp.getUserName());
					agentRelation.setRealName(null==userTemp.getInfo()?null:userTemp.getInfo().getRealName());
					agentRelation.setPos(agentRelation.getPos());
					agentRelationRebateMap.put(userTemp,agentRelationRebate);
				}
			}
		}
		return agentRelationRebateMap;
	}
	
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Pagination findAgentGroupInfoSum(final Long userId,final Long findUserId, final Pagination pagination,final AgentUserType agentUserType,Date start, Date end) {
		if (userId == null)
			throw new ServiceException("用户ID不能为空.");
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer buf = new StringBuffer();
		buf.append("Select ar");
		buf.append(" From AgentRelation ar");
		buf.append(" Where ");
		if(AgentUserType.AGENT.equals(agentUserType)){
			buf.append("ar.agentId=:userId");
			buf.append(" And ar.groupId=:userId");
		}else{
			buf.append("ar.groupId=:userId");
		}
		
		if(null!=findUserId){
			buf.append(" And ar.userId = :findUserId");
			map.put("findUserId", findUserId);
		}
		buf.append(" order by ar.userId asc");
		map.put("userId", userId);
		Pagination returnPagination =  queryService.findByHql(buf.toString(), map, pagination, Criteria.DISTINCT_ROOT_ENTITY);
		List<AgentSumDTO> result = Lists.newArrayList();
		AgentSumDTO agentSumDTO = null;
		if(null!=returnPagination.getResult()&&!returnPagination.getResult().isEmpty()){
			for (Object obj : returnPagination.getResult()) {
				AgentRelation agentRelation = (AgentRelation) obj;
				agentSumDTO = countFundDetail(agentRelation.getUserId(),start,end) ;
				agentSumDTO.setUserId(agentRelation.getUserId());
				agentSumDTO.setUserName(agentRelation.getUserName());
				agentSumDTO.setRealName(agentRelation.getRealName());
				User user = this.userDao.get(agentRelation.getUserId());
				agentSumDTO.setRemainMoney(user.getRemainMoney());
				result.add(agentSumDTO);
			}
		}
		returnPagination.setResult(result);
		return returnPagination;
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public AgentSumDTO countFundDetail(final Long userId,final Date start,final Date end){
		return (AgentSumDTO)this.agentFundDetailDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				ProjectionList prop = Projections.projectionList();
				prop.add(Projections.sum("money"), "money");
				prop.add(Projections.groupProperty("detailType"), "detailType");
				criteria.add(Restrictions.eq("userId", userId));
				if(null!=start&&null!=end){
					criteria.add(Restrictions.ge("createTime", start));
					criteria.add(Restrictions.le("createTime", end));
				}
				criteria.setProjection(prop);
				criteria.setResultTransformer(Transformers.aliasToBean(AgentFundDetail.class));
				AgentSumDTO agentSumDTO = new AgentSumDTO();
				List<AgentFundDetail> all =  criteria.list();
				for (AgentFundDetail agentFundDetail : all) {
					if(agentFundDetail.getDetailType().equals(AgentDetailType.BUY)){
						agentSumDTO.setBet_money(agentFundDetail.getMoney());
					}else if(agentFundDetail.getDetailType().equals(AgentDetailType.DRAWING)){
						agentSumDTO.setDrawing_money(agentFundDetail.getMoney());
					}else if(agentFundDetail.getDetailType().equals(AgentDetailType.LUCK)){
						agentSumDTO.setLuck_money(agentFundDetail.getMoney());
					}else if(agentFundDetail.getDetailType().equals(AgentDetailType.PAY)){
						agentSumDTO.setIps_money(agentFundDetail.getMoney());
					}else if(agentFundDetail.getDetailType().equals(AgentDetailType.PRIZE)){
						agentSumDTO.setPrize_money(agentFundDetail.getMoney());
					}else if(agentFundDetail.getDetailType().equals(AgentDetailType.REBATE)){
						agentSumDTO.setRebate_money(agentFundDetail.getMoney());
					}
				}
				return agentSumDTO;
			}
		});
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findGroupMember(final long userId) {
		return (List<Long>) this.agentRelationDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("userId"));
				criteria.add(Restrictions.eq("groupId", userId));
				return criteria.list();
			}
		});
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Pagination findAgentGroupInfo(final Long userId,final Long findUserId,final Date dateStart,final Date dateEnd, final Pagination pagination, final AgentLotteryType agentLotteryType) {
				XDetachedCriteria criteria =new XDetachedCriteria(AgentFundDetail.class);
				if(null!=findUserId){
					criteria.add(Restrictions.eq("userId", findUserId));
				}
				if(null!=dateStart&&null!=dateEnd){
					criteria.add(Restrictions.ge("createTime", dateStart));
					criteria.add(Restrictions.le("createTime", dateEnd));
				}
				if(null!=agentLotteryType){
					criteria.add(Restrictions.eq("lotteryType", agentLotteryType));
				}
				criteria.add(Restrictions.in("userId", findGroupMember(userId)));
				criteria.addOrder(Order.desc("id"));
				return this.queryService.findByCriteriaAndPagination(criteria, pagination);
	}
	
	
	
//	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
//	public AgentGroupInfo countAgentGroupInfo(final Long userId,final Long findUserId,final Long dateStart,final Long dateEnd, AgentLotteryType agentLotteryType) {
//		if (userId == null)
//			throw new ServiceException("用户ID不能为空.");
//		Map<String, Object> map = new HashMap<String, Object>();
//		StringBuffer buf = new StringBuffer();
//		buf.append("Select sum(agi.ips_money) as ips_money,sum(agi.rebate_money) as rebate_money,sum(agi.luck_money) as luck_money,sum(agi.drawing_money) as drawing_money,sum(agi.prize_money) as prize_money,sum(agi.bet_money) as bet_money,sum(agi.rebate_money) as rebate_money");
//		buf.append(" From AgentGroupInfo agi, AgentRelation ar");
//		buf.append(" Where ar.groupId=:userId And agi.userId = ar.userId");
//		if(null!=findUserId){
//			buf.append(" And ar.userId = :findUserId");
//			map.put("findUserId", findUserId);
//		}
//		if(null!=dateStart&&null!=dateEnd){
//			buf.append(" And agi.timePos >= :dateStart");
//			buf.append(" And agi.timePos <= :dateEnd");
//			map.put("dateStart", dateStart);
//			map.put("dateEnd", dateEnd);
//		}
//		if(null!=agentLotteryType){
//			buf.append(" And agi.agentLotteryType = :agentLotteryType");
//			map.put("agentLotteryType", agentLotteryType);
//		}
//		map.put("userId", userId);
//		List list = queryService.findByHql(buf.toString(), map, Criteria.ALIAS_TO_ENTITY_MAP);
//		if(null!=list){
//			HashMap resultMap = (HashMap) list.get(0);
//			AgentGroupInfo agentGroupInfo = new AgentGroupInfo();
//			agentGroupInfo.setRebate_money(null==resultMap.get("rebate_money")?null:BigDecimal.valueOf(Double.valueOf(""+resultMap.get("rebate_money"))));
//			agentGroupInfo.setLuck_money(null==resultMap.get("luck_money")?null:BigDecimal.valueOf(Double.valueOf(""+resultMap.get("luck_money"))));
//			agentGroupInfo.setIps_money(null==resultMap.get("ips_money")?null:BigDecimal.valueOf(Double.valueOf(""+resultMap.get("ips_money"))));
//			agentGroupInfo.setBet_money(null==resultMap.get("bet_money")?null:BigDecimal.valueOf(Double.valueOf(""+resultMap.get("bet_money"))));
//			agentGroupInfo.setDrawing_money(null==resultMap.get("drawing_money")?null:BigDecimal.valueOf(Double.valueOf(""+resultMap.get("drawing_money"))));
//			agentGroupInfo.setPrize_money(null==resultMap.get("prize_money")?null:BigDecimal.valueOf(Double.valueOf(""+resultMap.get("prize_money"))));
//			return agentGroupInfo;
//		}
//		return null;
//	}
	
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Pagination findAgentPersonInfoSum(final Long userId,final Long findUserId, final Pagination papination,final AgentUserType agentUserType) {
		if (userId == null)
			throw new ServiceException("用户ID不能为空.");
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer buf = new StringBuffer();
		buf.append("Select aps as ag");
		buf.append(" From AgentPersonSum aps, AgentRelation ar");
	    String couString = "groupId";
		if(AgentUserType.AGENT.equals(agentUserType)){
			couString = "agentId";
		}else if(AgentUserType.GROUP.equals(agentUserType)){
			couString = "groupId";
		}
		buf.append(" Where ar."+couString+"=:userId And aps.userId = ar.userId");
		if(null!=findUserId){
			buf.append(" And ar.userId = :findUserId");
			map.put("findUserId", findUserId);
		}
		buf.append(" Order by aps.userId asc,aps.bet_money desc");
		map.put("userId", userId);
		return queryService.findByHql(buf.toString(), map, papination, Criteria.DISTINCT_ROOT_ENTITY);
	}
	
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Pagination findAgentPersonInfo(final Long userId,final Long findUserId,final Long dateStart,final Long dateEnd, final Pagination papination, AgentLotteryType agentLotteryType) {
		if (userId == null)
			throw new ServiceException("用户ID不能为空.");
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer buf = new StringBuffer();
		buf.append("Select api.userId as userId,");
		buf.append( "api.userName as userName,");
		buf.append( "api.realName as realName,");
		buf.append( "api.ips_money as ips_money,");
		buf.append( "api.drawing_money as drawing_money,");
		buf.append( "api.prize_money as prize_money,");
		buf.append( "api.bet_money as bet_money,");
		
		buf.append( "api.rebate as rebate,");
		buf.append( "api.rebate_money as rebate_money,");
		buf.append( "api.agentLotteryType as agentLotteryType,");
		buf.append( "api.timePos as timePos,");
		buf.append( "api.luck_money as luck_money,");
		
		buf.append( "api.createTime as createTime,");
		buf.append( "api.lastModifyTime as lastModifyTime,");
		buf.append( "api.version as version,");
		buf.append( "api.id as id");
		
		buf.append(" From AgentPersonInfo api, AgentRelation ar");
		buf.append(" Where ar.groupId=:userId And api.userId = ar.userId");
		if(null!=findUserId){
			buf.append(" And ar.userId = :findUserId");
			map.put("findUserId", findUserId);
		}
		if(null!=dateStart&&null!=dateEnd){
			buf.append(" And api.timePos >= :dateStart");
			buf.append(" And api.timePos <= :dateEnd");
			map.put("dateStart", dateStart);
			map.put("dateEnd", dateEnd);
		}
		if(null!=agentLotteryType){
			buf.append(" And api.agentLotteryType = :agentLotteryType");
			map.put("agentLotteryType", agentLotteryType);
		}
		buf.append(" Order by timePos desc");
		map.put("userId", userId);
		
		Pagination pagination =  queryService.findByHql(buf.toString(), map, papination,  new AliasToBeanResultTransformer(AgentInfoDTO.class));
	    return pagination;
	}
	
	
//	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
//	public AgentPersonInfo countAgentPersonInfo(final Long userId,final Long findUserId,final Long dateStart,final Long dateEnd, AgentLotteryType agentLotteryType) {
//		if (userId == null)
//			throw new ServiceException("用户ID不能为空.");
//		Map<String, Object> map = new HashMap<String, Object>();
//		StringBuffer buf = new StringBuffer();
//		buf.append("Select sum(api.ips_money) as ips_money,sum(api.rebate_money) as rebate_money,sum(api.luck_money) as luck_money,sum(api.drawing_money) as drawing_money,sum(api.prize_money) as prize_money,sum(api.bet_money) as bet_money,sum(api.rebate_money) as rebate_money");
//		buf.append(" From AgentPersonInfo api, AgentRelation ar");
//		buf.append(" Where ar.groupId=:userId And api.userId = ar.userId");
//		if(null!=findUserId){
//			buf.append(" And ar.userId = :findUserId");
//			map.put("findUserId", findUserId);
//		}
//		if(null!=dateStart&&null!=dateEnd){
//			buf.append(" And api.timePos >= :dateStart");
//			buf.append(" And api.timePos <= :dateEnd");
//			map.put("dateStart", dateStart);
//			map.put("dateEnd", dateEnd);
//		}
//		if(null!=agentLotteryType){
//			buf.append(" And api.agentLotteryType = :agentLotteryType");
//			map.put("agentLotteryType", agentLotteryType);
//		}
//		map.put("userId", userId);
//		List list = queryService.findByHql(buf.toString(), map, Criteria.ALIAS_TO_ENTITY_MAP);
//		if(null!=list){
//			HashMap resultMap = (HashMap) list.get(0);
//			AgentPersonInfo agentPersonInfo = new AgentPersonInfo();
//			agentPersonInfo.setRebate_money(null==resultMap.get("rebate_money")?null:BigDecimal.valueOf(Double.valueOf(""+resultMap.get("rebate_money"))));
//			agentPersonInfo.setLuck_money(null==resultMap.get("luck_money")?null:BigDecimal.valueOf(Double.valueOf(""+resultMap.get("luck_money"))));
//			agentPersonInfo.setIps_money(null==resultMap.get("ips_money")?null:BigDecimal.valueOf(Double.valueOf(""+resultMap.get("ips_money"))));
//			agentPersonInfo.setBet_money(null==resultMap.get("bet_money")?null:BigDecimal.valueOf(Double.valueOf(""+resultMap.get("bet_money"))));
//			agentPersonInfo.setDrawing_money(null==resultMap.get("drawing_money")?null:BigDecimal.valueOf(Double.valueOf(""+resultMap.get("drawing_money"))));
//			agentPersonInfo.setPrize_money(null==resultMap.get("prize_money")?null:BigDecimal.valueOf(Double.valueOf(""+resultMap.get("prize_money"))));
//			return agentPersonInfo;
//		}
//		return null;
//	}
	
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Boolean isUserGroup(final Long userId,final Long groupId){
		return (Boolean)agentRelationDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				try{
					criteria.add(Restrictions.eq("userId", userId));
					criteria.add(Restrictions.eq("groupId", groupId));
					List list = criteria.list();
					if(null!=list&&!list.isEmpty())return Boolean.TRUE;
				}catch(Exception e){
				}
				return Boolean.FALSE;
			}
		});
	}
	
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Boolean isUserAgent(final Long userId,final Long agentId){
		return (Boolean)agentRelationDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				try{
					criteria.add(Restrictions.eq("userId", userId));
					criteria.add(Restrictions.eq("agentId", agentId));
					List list = criteria.list();
					if(null!=list&&!list.isEmpty())return Boolean.TRUE;
				}catch(Exception e){
				}
				return Boolean.FALSE;
			}
		});
	}
	
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Boolean isAgent(final Long userId){
		return (Boolean)agentRelationDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				try{
					criteria.add(Restrictions.eq("userId", userId));
					List list = criteria.list();
					if(null!=list&&!list.isEmpty())return Boolean.TRUE;
				}catch(Exception e){
				}
				return Boolean.FALSE;
			}
		});
	}
	public AgentRebate saveAgentRebate(AgentRebate agentRebate) {
		return this.agentRebateDao.save(agentRebate);
	}
	public AgentRelation saveAgentRelation(AgentRelation agentRelation) {
		return this.agentRelationDao.save(agentRelation);
	}

	
	
	public User saveAgentUser(Long agentUserId,User user, UserInfo info, BankInfo bank,List<AgentRebate> agentRebateList, String password) {
		user = userDao.save(user);
		User agentUser = userDao.get(agentUserId);
		List<AgentRelation> userAgentList = this.findUserAgentRelation(agentUser.getId());
		User userTemp = null;
		int i=0;
		AgentRelation agentRelation = null;
		for (AgentRelation a: userAgentList) {
			userTemp =  this.getUser(a.getGroupId());
			agentRelation = new AgentRelation();
			agentRelation.setUserId(user.getId());
			agentRelation.setAgentId(agentUser.getId());
			agentRelation.setGroupId(userTemp.getId());
			agentRelation.setUserName(user.getUserName());
			agentRelation.setRealName(null==info?"":info.getRealName());
			agentRelation.setPos(i);
			agentRelationDao.save(agentRelation);
			i++;
		}
		agentRelation = new AgentRelation();
		agentRelation.setUserId(user.getId());
		agentRelation.setAgentId(agentUser.getId());
		agentRelation.setGroupId(user.getId());
		agentRelation.setUserName(user.getUserName());
		agentRelation.setRealName(null==info?"":info.getRealName());
		agentRelation.setPos(i);
		agentRelationDao.save(agentRelation);
		for (AgentRebate agentRebate : agentRebateList) {
			agentRebate.setUserId(user.getUserId());
			agentRebate.setUserName(user.getUserName());
			//设置限制
			agentRebateDao.save(agentRebate);
		}
		if (info != null) {
			info.setUser(user);
			info = userInfoDao.save(info);
		}
		if (bank != null) {
			bank.setUser(user);
			bank = bankInfoDao.save(bank);
		}
		String userName = user.getUserName();
		String email = user.getUserId().toString() + "@miracle310" + ".com";
		//ForumController.reg(userName, password, email);
		return user;
	}

	@Override
	public void updateAgentRebate(List<AgentRebate> oprRebateMap) {
		for (AgentRebate agentRebate : oprRebateMap) {
			this.saveAgentRebate(agentRebate);
		}
	}
	
}
