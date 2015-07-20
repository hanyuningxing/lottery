package com.cai310.lottery.service.lottery.keno.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.event.UserNewestLogSupport;
import com.cai310.lottery.Constant;
import com.cai310.lottery.common.AgentAnalyseState;
import com.cai310.lottery.common.AgentDetailType;
import com.cai310.lottery.common.AgentLotteryType;
import com.cai310.lottery.common.BaodiState;
import com.cai310.lottery.common.ChaseState;
import com.cai310.lottery.common.DefaultPrizeSendType;
import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.FundMode;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PlatformInfo;
import com.cai310.lottery.common.PrepaymentState;
import com.cai310.lottery.common.PrepaymentType;
import com.cai310.lottery.common.PrizeSendType;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.ScoreDetailType;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.common.SubscriptionState;
import com.cai310.lottery.common.SubscriptionWay;
import com.cai310.lottery.common.TicketSchemeState;
import com.cai310.lottery.common.TransactionState;
import com.cai310.lottery.common.TransactionType;
import com.cai310.lottery.common.UserWay;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.common.keno.IssueState;
import com.cai310.lottery.dao.lottery.BaodiDao;
import com.cai310.lottery.dao.lottery.PrintInterfaceDao;
import com.cai310.lottery.dao.lottery.SubscriptionDao;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.KenoSysConfigDao;
import com.cai310.lottery.dao.lottery.keno.SchemeDao;
import com.cai310.lottery.dao.user.AccountDao;
import com.cai310.lottery.dao.user.FundDetailDao;
import com.cai310.lottery.dao.user.IntegralDao;
import com.cai310.lottery.dao.user.PrepaymentDao;
import com.cai310.lottery.dao.user.TransactionDao;
import com.cai310.lottery.dao.user.UserDao;
import com.cai310.lottery.dao.user.agent.AgentFundDetailDao;
import com.cai310.lottery.dao.user.agent.AgentRebateDao;
import com.cai310.lottery.dao.user.agent.AgentRelationDao;
import com.cai310.lottery.dto.lottery.SubscribeDTO;
import com.cai310.lottery.dto.user.AgentRelationRebate;
import com.cai310.lottery.entity.lottery.Baodi;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.lottery.SaleAnalyse;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.Subscription;
import com.cai310.lottery.entity.lottery.TradeSuccessScheme;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;
import com.cai310.lottery.entity.lottery.keno.KenoScheme;
import com.cai310.lottery.entity.lottery.keno.KenoSysConfig;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3Scheme;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5Scheme;
import com.cai310.lottery.entity.lottery.keno.gdel11to5.GdEl11to5Scheme;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5Scheme;
import com.cai310.lottery.entity.lottery.keno.ssc.SscScheme;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5Scheme;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.ticket.TicketDatail;
import com.cai310.lottery.entity.ticket.TicketPlatformInfo;
import com.cai310.lottery.entity.ticket.TicketThen;
import com.cai310.lottery.entity.user.Account;
import com.cai310.lottery.entity.user.BankInfo;
import com.cai310.lottery.entity.user.FundDetail;
import com.cai310.lottery.entity.user.Integral;
import com.cai310.lottery.entity.user.Prepayment;
import com.cai310.lottery.entity.user.Transaction;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserInfo;
import com.cai310.lottery.entity.user.agent.AgentFundDetail;
import com.cai310.lottery.entity.user.agent.AgentRebate;
import com.cai310.lottery.entity.user.agent.AgentRelation;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.template.VariableString;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.ChasePlanEntityManager;
import com.cai310.lottery.service.lottery.SaleAnalyseEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.TradeSuccessSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.service.ticket.TicketThenEntityManager;
import com.cai310.lottery.service.user.AgentEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.lottery.web.controller.lottery.SubscribeForm;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;
import com.cai310.utils.DateUtil;
import com.cai310.utils.NumUtils;
import com.cai310.utils.ReflectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Transactional
public abstract class KenoServiceImpl<I extends KenoPeriod, S extends KenoScheme> implements KenoService<I, S> {
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
	protected AgentFundDetailDao agentFundDetailDao;
	@Autowired
	protected AgentRebateDao agentRebateDao;
	@Autowired
	protected AgentRelationDao agentRelationDao;
	@Autowired
	protected UserDao userDao;
	@Autowired
	protected AccountDao accountDao;
	@Autowired
	protected TransactionDao transactionDao;
	@Autowired
	protected PrepaymentDao prepaymentDao;
	@Autowired
	protected FundDetailDao fundDetailDao;
	@Autowired
	protected BaodiDao baodiDao;
	@Autowired
	protected IntegralDao integralDao;
	@Resource
	private QueryService queryService;
	protected KenoManager<I, S> kenoManager;
	protected KenoPlayer<I, S> kenoPlayer;
	@Autowired
	protected UserEntityManager userEntityManager;
	public void setKenoPlayer(KenoPlayer<I, S> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}
	@Autowired
	protected TradeSuccessSchemeEntityManagerImpl successSchemeManager;
	protected SubscribeForm subscribeForm;
//	protected NumberSchemeEntityManager<KenoScheme> getSchemeEntityManager() {
//		return schemeManager;
//	}
	
	SdEl11to5Scheme sdEl11to5Scheme =null;
	SscScheme sscScheme =null;
	GdEl11to5Scheme gdEl11to5Scheme = null;
	El11to5Scheme el11to5Scheme = null;
	AhKuai3Scheme  kuaiScheme = null;
	XjEl11to5Scheme xjEl11to5Scheme=null;
	String playName=null;
	String simpleName=null;
	@Autowired
	protected TicketThenEntityManager ticketThenEntityManager;
	long time = System.currentTimeMillis();
	public void xiaolu(String word){
		System.out.println("----------"+word+"------------用时："+(System.currentTimeMillis()-time));
		time=System.currentTimeMillis();
	}
	public void updatePeriodPrize(I issueData,KenoPlayer kenoPlayer) throws DataException{
		issueData = this.findIssueDataById(issueData.getId());
		String results = issueData.getResults();
		// 读取本期中的投注方案
		List<S> schemeList = null;
		schemeList = this.getSchemeByIssueNumber(issueData.getPeriodNumber().trim());
		if (schemeList != null && !schemeList.isEmpty()) {
			for (S scheme : schemeList) {
				 xiaolu("更新中奖-处理完方案"+scheme.getId());
				if (!scheme.isWon()) {
					kenoPlayer.calculatePrice(scheme, results);
//					ZoomType zoomType = ((KenoScheme)scheme).getZoomType();
//					WonType wonType = ((KenoScheme)scheme).getWonType();
					if (scheme.isWon()) {
						BigDecimal prizeAfterTax= scheme.getPrizeAfterTax();
						scheme.setPrize(scheme.getPrize());
						scheme.setPrizeAfterTax(scheme.getPrizeAfterTax());
						scheme.setPrizeDetail(scheme.getPrizeDetail()+"税后总奖金："+scheme.getPrizeAfterTax());
						scheme.setWinningUpdateStatus(WinningUpdateStatus.WINNING_UPDATED);
					}else{
						scheme.setWinningUpdateStatus(WinningUpdateStatus.WINNING_UPDATED);
					}
					this.saveScheme(scheme);
					this.saveTradeSuccessScheme(scheme);
				}
				 xiaolu("更新中奖-处理完方案"+scheme.getId());
			}
		}

		logger.info("==========：处理开号后停追的追号==========");
		// 找出期间所有设置了 开号后停追的追号
		// 判断投注是否中奖
		// 停追
		List<ChasePlan> chasePlanList = getOutNumStopChasePlan(true, issueData);
		if (chasePlanList != null && !chasePlanList.isEmpty()) {
			for (ChasePlan chasePlan : chasePlanList) {
				// 开号后停止 且 未进行追号记录
				if (chasePlan.isOutNumStop() && chasePlan.getChasedSize() < 0) {
					boolean isWon = kenoPlayer.calculatePrice(chasePlan.getMode(), chasePlan.getContent(),
							chasePlan.getNextMultiple(), chasePlan.getPlayType(), results);
					if (isWon) {
						logger.info("==========开号后停追的追号ID======已停====" + chasePlan.getId());
						synchronized (Constant.STOPCHASE) {
							this.stopChasePlan(chasePlan.getId(), "开号停追");
						}
					}
				}
			}
		}

		logger.info("==========：处理中后停追的追号==========");
		this.doChasePlan(issueData.getId(), true);

		// 更新为已更新中奖方案
		this.updateIssueState(issueData.getId(), IssueState.ISSUE_SATE_ACCOUNT_PRIZE);
		logger.info("==========：[" + issueData.getPeriodNumber() + "]期上升为更新中奖状态==========");
	}
	
	/**
	 * 保存交易成功的方案
	 * @param scheme
	 */
	private void saveTradeSuccessScheme(S scheme){
		Long successSchemeId = null;
		TradeSuccessScheme successScheme = successSchemeManager.getScheme(getLottery(), scheme.getId());
		if(successScheme == null){
			successScheme = new TradeSuccessScheme();
		}else{
			successSchemeId = successScheme.getId();
		}
		successScheme.setLotteryType(getLottery());
		successScheme.setSchemeId(scheme.getId());
		try {
			BeanUtils.copyProperties(successScheme, scheme);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		successScheme.setId(successSchemeId);
		if(scheme.getPrize()!=null){
			successScheme.setSchemePrize(scheme.getPrize());
		}
		if(scheme.getPrizeAfterTax()!=null){
			successScheme.setSchemePrizeAfterTax(scheme.getPrizeAfterTax());
		}	
		successSchemeManager.saveScheme(successScheme);
	}
	
	public void sendPrize(I issueData){
		// 读取本期中的投注方案
		issueData = this.findIssueDataById(issueData.getId());
		List<S>  schemeList = this.getSchemeByIssueNumber(issueData.getPeriodNumber().trim());
		if (schemeList != null && !schemeList.isEmpty()) {
			for (S scheme : schemeList) {
				if (scheme.isWon() && !scheme.isPrizeSended()) {
					// 对已经计算方案但尚未派奖的方案进行派奖
					this.sendUserPrice(scheme);
				}
			}
		}
		// 更新为已派奖方案
		this.updateIssueState(issueData.getId(), IssueState.ISSUE_SATE_SEND_PRIZE);
		logger.info("==========：[" + issueData.getPeriodNumber() + "]期上升为派奖状态==========");

	}
	@Override
	public List<ChasePlan> getOutNumStopChasePlan(boolean outNumStop, I issueData) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChasePlan.class);
		if (outNumStop) {
			criteria.add(Restrictions.eq("outNumStop", outNumStop));
		}
		criteria.add(Restrictions.eq("lotteryType", this.getLottery()));
		// criteria.add(Restrictions.ge("curPeriodId",
		// Long.valueOf(issueData.getPeriodNumber())));
		criteria.add(Restrictions.eq("state", ChaseState.RUNNING));
		List<ChasePlan> list = queryService.findByDetachedCriteria(criteria);
		return list;
	}
	/**
	 * 自动追号
	 */
	@Override
	public void doChasePlan(long curPeriodId, Boolean iswonStop) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChasePlan.class);
		criteria.setProjection(Projections.property("id"));
		if (iswonStop != null)
			criteria.add(Restrictions.eq("wonStop", iswonStop));
		criteria.add(Restrictions.eq("lotteryType", getLottery()));
		criteria.add(Restrictions.eq("curPeriodId", curPeriodId));
		criteria.add(Restrictions.eq("state", ChaseState.RUNNING));
		List<Long> list = queryService.findByDetachedCriteria(criteria);
		if (list != null && !list.isEmpty()) {
			for (Long chasePlanId : list) {
				this.handChasePlan(chasePlanId);
			}
		}
	}
	// protected ChasePlanDao<C> chasePlanDao;
	protected IssueDataDao<I> issueDataDao;
	protected SchemeDao<S> schemeDao;
	protected KenoSysConfigDao kenoSysConfigDao;
	@Autowired
	protected ChasePlanEntityManager chasePlanEntityManager;
	
	@Autowired
	protected AgentEntityManager agentEntityManager;

	@Autowired
	protected UserEntityManager userManager;
	@Autowired
	protected SaleAnalyseEntityManager saleAnalyseEntityManager;
	@Autowired
	protected PrintInterfaceDao printInterfaceDao;

	@Autowired
	protected SubscriptionDao subscriptionDao;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public abstract Lottery getLottery();

	/**
	 * 把方案写到打印接口
	 * 
	 * @param scheme
	 */
	protected abstract void pushToPrintInterface(S scheme, I issueData);

	protected abstract ChasePlan setChasePlanPlayType(ChasePlan chasePlan, KenoSchemeDTO schemeDTO);

	/**
	 * 据键值读取配置信息项
	 * 
	 * @param id
	 *            键值
	 * @return 一条配置信息
	 */
	@Transactional(readOnly = true)
	public KenoSysConfig getSysConfigByKey(String id) {
		return kenoSysConfigDao.get(id);
	}

	/**
	 * 保存或更新配置信息
	 * 
	 * @param entity
	 *            一条配置信息
	 */
	@Transactional(readOnly = true)
	public void getSysConfigByKey(KenoSysConfig entity) {
		kenoSysConfigDao.save(entity);
	}

	/**
	 * 据期号读取期号数据
	 * 
	 * @param issueNumber
	 *            期号数据
	 */
	@Transactional(readOnly = true)
	public I findByIssueNumber(String issueNumber) {
		return issueDataDao.findDataByNumber(issueNumber);
	}

	/**
	 * 读取当前期数据
	 * 
	 * @param dateNow
	 *            当前系统时间
	 * @param beforeTime
	 *            提前开售和截止时间（单位：分钟）
	 * @return 前期数据
	 */
	@Transactional(readOnly = true)
	public I getCurrIssueData(Date dateNow, int beforeTime) {
		return issueDataDao.findCurrentData(dateNow, beforeTime);
	}

	/**
	 * 读取最后一期的期号数据
	 * 
	 * @return 最后一期的期号数据
	 */
	@Transactional(readOnly = true)
	public I getLastIssue() {
		return issueDataDao.findLastIssue();
	}

	/**
	 * 读取最后一期开奖期号数据
	 * 
	 * @return 最后一期开奖期号数据
	 */
	@Transactional(readOnly = true)
	public I getLastResultIssueData() {
		return issueDataDao.findLastResultIssueData();
	}

	/**
	 * 读取需要派奖的期号列表
	 * 
	 * @param dateNow
	 *            当前系统时间
	 * @return 需要派奖的期号列表
	 */
	@Transactional(readOnly = true)
	public List<I> getCanSendPrize(Date dateNow) {
		return issueDataDao.findIssueDataList(dateNow, IssueState.ISSUE_SATE_ACCOUNT_PRIZE);
	}

	/**
	 * 开售当前期
	 * 
	 * @param dateNow
	 *            当前系统时间
	 * @param beforeTime
	 *            提前开售和截止时间（单位：分钟）
	 */
	public void openCurrentIssue(Date dateNow, int beforeTime) {
		I issueData = issueDataDao.findCurrentData(dateNow, beforeTime);
		if (issueData != null) {
			if (issueData.getState().getStateValue() < IssueState.ISSUE_SATE_CURRENT.getStateValue()) {
				issueData.setState(IssueState.ISSUE_SATE_CURRENT);
				issueDataDao.save(issueData);
			}
		}
	}

	@Transactional(readOnly = true)
	public I findCurrentData(Date dateNow, int beforeTime) {
		return issueDataDao.findCurrentData(dateNow, beforeTime);
	}

	/**
	 * 保存期号数据
	 * 
	 * @param issueData
	 *            要保存的期号数据
	 */
	@Transactional
	public I saveIssueData(I issueData) {
		I newIssueData = issueDataDao.save(issueData);
		return newIssueData;
	}

	/**
	 * 读取可以更新中奖的期号列表
	 * 
	 * @param dateNow
	 *            当前系统时间
	 * @return 可以更新中奖的期号列表
	 */
	@Transactional(readOnly = true)
	public List<I> getCanOpenPrize(Date dateNow) {
		return issueDataDao.findIssueDataList(dateNow, IssueState.ISSUE_SATE_RESULT);
	}

	@Transactional(readOnly = true)
	public List<I> getCanOpenResults() {
		DetachedCriteria criteria = DetachedCriteria.forClass(issueDataDao.getEntityClass());
		criteria.add(Restrictions.lt("endedTime", new Date()));
		criteria.add(Restrictions.gt("endedTime", DateUtils.addDays(new Date(), -1)));
		criteria.add(Restrictions.eq("state", IssueState.ISSUE_SATE_END));
		criteria.addOrder(Order.desc("id"));
		return issueDataDao.findByDetachedCriteria(criteria, true);
	}
	public void endAll(I issueData){
		// 读取本期中的投注方案
		issueData = this.findIssueDataById(issueData.getId());
		this.updateIssueState(issueData.getId(), IssueState.ISSUE_SATE_FINISH);
	}
	/**
	 * 更新期号状态
	 * 
	 * @param issueId
	 *            期号ID
	 * @param state
	 *            期号状态
	 */
	public void updateIssueState(Long issueId, IssueState state) {
		issueDataDao.updateIssueState(issueId, state);
	}

	public List<I> findKenoPeriodByCriteria(DetachedCriteria criteria, Integer num) {
		if (null == num) {
			return issueDataDao.findByDetachedCriteria(criteria);
		}
		return issueDataDao.findByDetachedCriteria(criteria, 0, num);
	}

	/**
	 * 据期号读取方案列表
	 * 
	 * @param issueNumber
	 *            期号
	 * @return 方案列表
	 */
	@Transactional(readOnly = true)
	public List<S> getSchemeByIssueNumber(String issueNumber) {
		return schemeDao.findByIssueNumber(issueNumber);
	}
	/**
	 * 据读未统计方案列表
	 * 
	 * @param periodId
	 *            期号
	 * @return 方案列表
	 */
	@Transactional(readOnly = true)
	public List<S> getUnAgentAnalyseScheme(Long periodId) {
		return schemeDao.findUnAnalyes(periodId);
	}
	/**
	 * 据读未派奖方案列表
	 * 
	 * @param periodId
	 *            期号
	 * @return 方案列表
	 */
	@Transactional(readOnly = true)
	public List<S> findUnSendPrize(Long periodId) {
		return schemeDao.findUnSendPrize(periodId);
	}
	/**
	 * 据期号读取出票失败方案列表
	 * 
	 * @param issueNumber
	 *            期号
	 * @return 出票失败方案列表
	 */
	@Transactional(readOnly = true)
	public List<S> getPrintFailSchemeByIssueNumber(String issueNumber) {
		return schemeDao.findPrintFailByIssueNumber(issueNumber);
	}

	/**
	 * 保存方案
	 * 
	 * @param scheme
	 *            需保存的方案
	 * @return 
	 */
	public void saveScheme(S scheme) {
		schemeDao.save(scheme);
	}

	public S saveSchemeReturn(S scheme) {
		return schemeDao.save(scheme);
	}
	/**
	 * 据方案号向用户派奖
	 * 
	 * @param scheme
	 *            需派奖的方案
	 */
	public void sendUserPrice(S scheme) {
		
//		KenoScheme kenoScheme = null;
		
		Float bouns=0.0f;
		Float scoreRate=0.0f;
		scheme.setPrizeSended(true);
		// zhuhui motify by 2011-05-03 增加 派送奖金时间
		scheme.setPrizeSendTime(new Date());
		// xxxc motify by 2012-09-03 增加 派送奖金状态
		scheme.setWinningUpdateStatus(WinningUpdateStatus.PRICE_UPDATED);
		this.schemeDao.save(scheme);
		
		if(Lottery.isKeno(scheme.getLotteryType())){//判断是否高频彩
			System.out.println(scheme.getLotteryType()+"采种");//采种类型
			if(Lottery.SSC.equals(scheme.getLotteryType())){
				sscScheme=(SscScheme)scheme;
				playName=sscScheme.getBetType().getTypeName();
				simpleName = Lottery.SSC.getSchemeNumberPrefix();
			}else if(Lottery.SDEL11TO5.equals(scheme.getLotteryType())){
				sdEl11to5Scheme=(SdEl11to5Scheme)scheme;
				playName=sdEl11to5Scheme.getBetType().getTypeName();
				simpleName = Lottery.SDEL11TO5.getSchemeNumberPrefix();
			}else if(Lottery.GDEL11TO5.equals(scheme.getLotteryType())){
				gdEl11to5Scheme=(GdEl11to5Scheme)scheme;
				playName=gdEl11to5Scheme.getBetType().getTypeName();
				simpleName = Lottery.GDEL11TO5.getSchemeNumberPrefix();
			}else if (Lottery.EL11TO5.equals(scheme.getLotteryType())){
				el11to5Scheme=(El11to5Scheme)scheme;
				playName=el11to5Scheme.getBetType().getTypeName();
				simpleName = Lottery.EL11TO5.getSchemeNumberPrefix();
			}else if(Lottery.AHKUAI3.equals(scheme.getLotteryType())){
				kuaiScheme=(AhKuai3Scheme)scheme;
				playName=kuaiScheme.getBetType().getTypeName();
				simpleName = Lottery.AHKUAI3.getSchemeNumberPrefix();
			}else if(Lottery.XJEL11TO5.equals(scheme.getLotteryType())){
				xjEl11to5Scheme=(XjEl11to5Scheme) scheme;
				playName=xjEl11to5Scheme.getBetType().getTypeName();
				simpleName=Lottery.XJEL11TO5.getSchemeNumberPrefix();
			}
			DetachedCriteria criteria = DetachedCriteria.forClass(Integral.class,"m");
			criteria.add(Restrictions.eq("m.parent",simpleName));
			criteria.add(Restrictions.eq("m.playName",playName));
		    List<Integral> list =integralDao.findByDetachedCriteria(criteria);
		    bouns=list.get(0).getBouns();
		    scoreRate=list.get(0).getScoreRate();
		}
		
		PrizeSendType prizeSendType = new DefaultPrizeSendType(scheme);// 默认奖金分配方案
		StringBuffer memosb = new StringBuffer("您");
		if (scheme.getShareType() == ShareType.TOGETHER) {
			memosb.append("参与合买的方案");
		} else if (scheme.isChasePlanScheme()) {
			memosb.append("追号的方案");
		} else {
			memosb.append("自购的方案");
		}
		memosb.append("[" + scheme.getSchemeNumber() + "]");
		memosb.append("中奖，您获得分配奖金{prize}元");
		VariableString varReturnMemo = new VariableString(memosb.toString(), null);// 生成合买分配的奖金模板
		
		// 加载正常的加入对象列表
		List<Subscription> subscriptionList = findSubscriptionsOfScheme(scheme.getId(), SubscriptionState.NORMAL);// /读出跟单列表
		BigDecimal actualTotalReturn = BigDecimal.ZERO;// 计算实际分配的总金额
		BigDecimal joinPrize;
		//方案金额
		BigDecimal schemeCost = BigDecimalUtil.valueOf(scheme.getSchemeCost());
		for (Subscription subscription : subscriptionList) {
			joinPrize = BigDecimalUtil.divide(
					BigDecimalUtil.multiply(subscription.getCost(), prizeSendType.getTotalReturn()), schemeCost);
			subscription.setBonusSended(true);
			subscription.setBonus(joinPrize);// 更新加入对象的奖金信息
			subscription = subscriptionDao.save(subscription);// 更新加入对象

			varReturnMemo.setVar("prize", Constant.numFmt.format(joinPrize));// 合买分配的奖金信息

			// 执行操作：为用户添中资金
			userManager.oprUserMoney(userManager.getUser(subscription.getUserId()), joinPrize,
					varReturnMemo.toString(), FundMode.IN, FundDetailType.SCHEME_BONUS, null);
			///增加派奖统计
			agentEntityManager.recordAgent(userManager.getUser(subscription.getUserId()), scheme.getLotteryType(), AgentDetailType.PRIZE,new Date(), joinPrize,null);
			if(joinPrize.compareTo(BigDecimal.valueOf(bouns))>0){
				
			}else{
				//增加积分
//				String remark = varReturnMemo.toString()+"根据"+scoreRate+"比例赠送"+joinPrize.multiply(BigDecimal.valueOf(scoreRate))+"分";
				String remark = varReturnMemo.toString().concat("，根据").concat(scoreRate.toString()).concat("比例赠送").concat(joinPrize.multiply(BigDecimal.valueOf(scoreRate)).toString()).concat("分");
				userManager.oprUserScore(userManager.getUser(subscription.getUserId()), joinPrize.multiply(BigDecimal.valueOf(scoreRate)), remark, FundMode.IN, ScoreDetailType.WINNING, null);
			}
			actualTotalReturn = actualTotalReturn.add(joinPrize);// 计算总的实际分配金额
		}
		// 剩余未分配奖金
		BigDecimal remainPrize = prizeSendType.getTotalReturn().subtract(actualTotalReturn);
		// 允许每个参与者有1分钱的误差，当超同这个误差抛出异常
		if (remainPrize.doubleValue() < 0) {
			throw new ServiceException("实际分派的总金额超过可分派的金额。");
		} else if (remainPrize.doubleValue() > 0) {
			// 剩余未分配奖金派发给方案发起人
			if(remainPrize.compareTo(BigDecimal.valueOf(0.01))>0){
				varReturnMemo.setVar("prize", Constant.numFmt.format(remainPrize));
				userManager.oprUserMoney(userManager.getUser(scheme.getSponsorId()), remainPrize,
						varReturnMemo.toString(), FundMode.IN, FundDetailType.SCHEME_BONUS, null);
				///增加派奖统计
				agentEntityManager.recordAgent(userManager.getUser(scheme.getSponsorId()), scheme.getLotteryType(), AgentDetailType.PRIZE,new Date(), remainPrize,null);
				if(remainPrize.compareTo(BigDecimal.valueOf(bouns))>0){
					
				}else{
					//增加积分
					String s = null;
					String remark = varReturnMemo.toString().concat("根据").concat(scoreRate.toString()).concat("比例赠送").concat(remainPrize.multiply(BigDecimal.valueOf(scoreRate)).toString()).concat("分");
					userManager.oprUserScore(userManager.getUser(scheme.getSponsorId()), remainPrize.multiply(BigDecimal.valueOf(scoreRate)), remark, FundMode.IN, ScoreDetailType.WINNING, null);
				}
			}
		}

		if (prizeSendType.getOrganigerDeduct().doubleValue() > 0) {// 方案发起人提成
			userManager.oprUserMoney(userManager.getUser(scheme.getSponsorId()),
					prizeSendType.getOrganigerDeduct(), "方案号[" + scheme.getSchemeNumber() + "]中奖奖金提成", FundMode.IN,
					FundDetailType.SCHEME_COMMISSION, null);
		}
		UserNewestLogSupport.won(scheme);	
		ticketThenEntityManager.synchronizationWonTicket(scheme);
//		User user = userManager.getUser(scheme.getSponsorId());
//		//方案金额
//		scheme.getSchemeCost();
//		// cyy-c 2011-04-13 加入高频彩派奖写入加入表
//		// 加载正常的加入对象列表
//		List<Subscription> subscriptionList = findSubscriptionsOfScheme(scheme.getId(), SubscriptionState.NORMAL);// /读出跟单列表
//		if (null != subscriptionList && !subscriptionList.isEmpty()) {// 正常情况高频彩只有一个加入
//			Subscription subscription = subscriptionList.get(0);// 读取第一个加入
//			subscription.setBonus(scheme.getPrizeAfterTax());// 把方案税后奖金设上
//			subscriptionDao.save(subscription);// 保存
//			userManager.oprUserMoney(user, scheme.getPrizeAfterTax(), this.getLottery()
//					.getLotteryName() + "中奖:方案号:[" + scheme.getSchemeNumber() + "]", FundMode.IN,
//					FundDetailType.SCHEME_BONUS, null);// 派发奖金
//			///增加派奖统计
//			agentEntityManager.recordAgent(user, this.getLottery(), AgentDetailType.PRIZE,new Date(), scheme.getPrizeAfterTax(),null);
//		}
	}

	/**
	 * 读取需要截止的期号列表
	 * 
	 * @param dateNow
	 *            当前系统时间
	 * @return 需要截止的期号列表
	 */
	@Transactional(readOnly = true)
	public List<I> getCanCloseIssue(Date dateNow) {
		return issueDataDao.findIssueDataList(dateNow, IssueState.ISSUE_SATE_SEND_PRIZE);
	}

	/**
	 * 用户投注
	 * 
	 * @param scheme
	 *            投注的方案号
	 * @param user
	 *            投注的用户
	 * @throws DataException
	 *             投注异常信息
	 */
	public void userBet(S scheme, User user) throws DataException {
		BigDecimal cost = new BigDecimal(scheme.getSchemeCost());
		userManager.oprUserMoney(user, cost, scheme.getLotteryType().getLotteryName() + "投注",
				FundMode.OUT, FundDetailType.SUBSCRIPTION, null);
		// 增加用户消费金额
		schemeDao.save(scheme);
	}

	/**
	 * 保存追号内容
	 * 
	 * @param chasePlan
	 *            追号内容
	 */
	public void userChasePlan(ChasePlan chasePlan) {
		chasePlanEntityManager.saveChasePlan(chasePlan);
	}

	/**
	 * 分页读取期数
	 * 
	 * @param criteria
	 * @param pagination
	 * @return
	 */
	@Transactional(readOnly = true)
	public Pagination findByCriteriaAndPagination(XDetachedCriteria criteria, Pagination pagination) {
		return issueDataDao.findByCriteriaAndPagination(criteria, pagination);
	}

	@Transactional(readOnly = true)
	public I findIssueDataById(Long id) {
		return issueDataDao.get(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public I loadPeriod(String periodNumber) {
		List<I> list = issueDataDao.find(
				Restrictions.eq("periodNumber", periodNumber));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	public Class<I> getIssueDataClass() {
		return issueDataDao.getEntityClass();
	}

	@Transactional(readOnly = true)
	public Class<S> getSchemeClass() {
		return schemeDao.getEntityClass();
	}

	/**
	 * 据ID读取追号内容
	 */
	@Transactional(readOnly = true)
	public ChasePlan findChasePlanById(Long id) {
		return chasePlanEntityManager.getChasePlan(id);
	}

	/**
	 * new一个新方案对象
	 * 
	 * @return 方案对象
	 */
	@Transactional(readOnly = true)
	public S newInstance(KenoSchemeDTO schemeDTO) {
		S scheme;
		try {
			scheme = schemeDao.getEntityClass().newInstance();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		scheme.setContent(schemeDTO.getContent());
		scheme.setSchemeCost(schemeDTO.getSchemeCost());
		scheme.setUnits(schemeDTO.getUnits());
		scheme.setMultiple(schemeDTO.getMultiple());
		scheme.setPeriodId(schemeDTO.getPeriodId());
		scheme.setCreateTime(new Date());
		scheme.setPrizeSended(false);
		scheme.setWon(false);
		scheme.setSponsorId(schemeDTO.getSponsorId());
		scheme.setMode(schemeDTO.getMode());
		scheme.setSchemePrintState(SchemePrintState.UNPRINT);
		scheme.setShareType(schemeDTO.getShareType());
		scheme.setSecretType(schemeDTO.getSecretType());
		scheme.setSubscriptionLicenseType(schemeDTO.getSubscriptionLicenseType());
		scheme.setSubscriptionPassword(schemeDTO.getSubscriptionPassword());
		scheme.setMinSubscriptionCost(schemeDTO.getMinSubscriptionCost());
		scheme.setDescription(schemeDTO.getDescription());
		scheme.setCommissionRate(schemeDTO.getCommissionRate());
		if (StringUtils.isNotBlank(schemeDTO.getContent())) {
			try {
				scheme.uploadContent(schemeDTO.getContent());
			} catch (DataException e) {
				throw new ServiceException(e.getMessage());
			}
		}

		scheme.setState(SchemeState.UNFULL);
		scheme.setWinningUpdateStatus(WinningUpdateStatus.NONE);

		return scheme;
	}

	@Transactional(readOnly = true)
	public S newInstance(ChasePlan chasePlan) {
		S scheme;
		try {
			scheme = schemeDao.getEntityClass().newInstance();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		scheme.setContent(chasePlan.getContent());
		scheme.setUnits(chasePlan.getUnits());

		scheme.setSponsorId(chasePlan.getUserId());
		scheme.setSponsorName(chasePlan.getUserName());

		scheme.setChaseId(chasePlan.getId());

		scheme.setMode(chasePlan.getMode());
		scheme.setSchemePrintState(SchemePrintState.UNPRINT);
		scheme.setShareType(ShareType.SELF);
		scheme.setSecretType(SecretType.FULL_SECRET);
		scheme.setSubscriptionLicenseType(SubscriptionLicenseType.PUBLIC_LICENSE);
		if (StringUtils.isNotBlank(chasePlan.getContent())) {
			try {
				scheme.uploadContent(chasePlan.getContent());
			} catch (DataException e) {
				throw new ServiceException(e.getMessage());
			}
		}
		scheme.setId(null);
		return scheme;
	}
	/**
	 * 验证用户是否符合发起方案的相关条件
	 * 
	 * @param user
	 *            用户对象
	 * @param schemeDTO
	 *            发起方案的数据传输对象
	 */
	protected void checkTicketPlatformInfo(TicketPlatformInfo ticketPlatformInfo, KenoSchemeDTO schemeDTO) {
		if (ticketPlatformInfo.isLocked())
			throw new ServiceException("您的帐户已经被锁定,不能发起方案.如有什么疑问,请联系我们的客服人员.");

		BigDecimal totalCost = BigDecimal.ZERO;
		if (schemeDTO.getSponsorSubscriptionCost() != null
				&& schemeDTO.getSponsorSubscriptionCost().doubleValue() > 0)
			totalCost = totalCost.add(schemeDTO.getSponsorSubscriptionCost());
		if (schemeDTO.getSponsorBaodiCost() != null
				&& schemeDTO.getSponsorBaodiCost().doubleValue() > 0)
			totalCost = totalCost.add(schemeDTO.getSponsorBaodiCost());
		if (ticketPlatformInfo == null) {
			throw new ServiceException("跟单用户[#" + ticketPlatformInfo.getId() + "]不存在.");
		}
		BigDecimal allAccountBalance = ticketPlatformInfo.getRemainMoney();

		BigDecimal remainMoney = (allAccountBalance != null) ? allAccountBalance
				: BigDecimal.ZERO;
		if (remainMoney.doubleValue() < totalCost.doubleValue())
			throw new ServiceException("对不起,完成此次交易共需要["
					+ Constant.MONEY_FORMAT.format(totalCost) + "]元,您的账户余额只有["
					+ Constant.MONEY_FORMAT.format(remainMoney)
					+ "]元,不够支付此次交易.");
	}
	public S createTicketScheme(KenoSchemeDTO schemeDTO) {
		I issueData = issueDataDao.get(schemeDTO.getPeriodId());
		checkConformPeriodInitConfig(issueData, schemeDTO);

		TicketPlatformInfo ticketPlatformInfo = ticketThenEntityManager.getTicketPlatformInfo(schemeDTO.getSponsorId());
		checkTicketPlatformInfo(ticketPlatformInfo, schemeDTO);// 检查用户,余额是否能发起方案

		S scheme = null;
		try {
			scheme = schemeDao.getEntityClass().newInstance();
		} catch (InstantiationException e) {
			throw new ServiceException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new ServiceException(e.getMessage());
		}
		scheme = newInstance(schemeDTO);
		
		scheme.setPeriodNumber(issueData.getPeriodNumber());
		StringBuilder sb = new StringBuilder(50);
		sb.append("【").append(scheme.getLotteryType().getLotteryName()).append("】").append(scheme.getPeriodNumber())
				.append("期").append(scheme.getMode().getModeName()).append(scheme.getShareType().getShareName())
				.append("方案.");
		Transaction tran = userManager.createTransaction(TransactionType.SCHEME, sb.toString());
		scheme.setTransactionId(tran.getId());
		scheme.setSchemePrintState(SchemePrintState.UNPRINT);
		// /接票
		scheme.setOrderId(schemeDTO.getOrderId());
		if (null != schemeDTO.getOfficialEndTime()) {
			scheme.setCommitTime(schemeDTO.getOfficialEndTime());
		}
		// 用户来源
		if (null != schemeDTO.getPlatform()) {
			scheme.setPlatform(schemeDTO.getPlatform());
		}
		scheme = doTicket(scheme, schemeDTO, ticketPlatformInfo,issueData);
		
		return scheme;
	}
	/**
	 * 处理发起自购方案
	 * 
	 * @param scheme
	 *            方案对象
	 * @param schemeDTO
	 *            方案发起的数据传输对象
	 * @param user
	 *            方案发起人
	 */
	protected S doTicket(S scheme, KenoSchemeDTO schemeDTO, TicketPlatformInfo ticketPlatformInfo,I issueData) {
		BigDecimal subscriptionCost = BigDecimalUtil.valueOf(scheme
				.getSchemeCost());
		try {
			scheme.subscription(subscriptionCost);
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		
		
		//Prepayment prepayment = userManager.createPrepayment(scheme
		//		.getTransactionId(), user.getId(), cost,
		//		PrepaymentType.SUBSCRIPTION, FundDetailType.SUBSCRIPTION, sb
		//				.toString(), PlatformInfo.TICKET);

		// 扣除金额
		ticketPlatformInfo = ticketThenEntityManager.getTicketPlatformInfo(ticketPlatformInfo.getId());
		User user = null;
		if(null==ticketPlatformInfo.getUserId()){
			user = new User();
			user.setUserName(ticketPlatformInfo.getPlatformName());
			user.setPassword(ticketPlatformInfo.getPassword());
			user.setUserWay(UserWay.WEB);
			user.setMid(0L);
			user.setLocked(User.NO_LOCK_STATUS);
			user.setRemainMoney(BigDecimal.ZERO);
			user.setAgentId(Constant.SITE_BAODI_USER_ID);
			UserInfo info = new UserInfo();
			BankInfo bank = new BankInfo();
			user.setBank(bank);
			user.setInfo(info);
			user = userManager.saveUser(user, info, bank);
			ticketPlatformInfo.setUserId(user.getId());
		}else{
			user = userManager.getUser(ticketPlatformInfo.getUserId());
		}

		scheme.setSponsorId(user.getUserId());
		scheme.setSponsorName(user.getUserName());
		scheme = schemeDao.save(scheme);
		
		StringBuilder sb = new StringBuilder(50);
		sb.append("发起彩票[").append(scheme.getSchemeNumber()).append("].");
		try {
			ticketPlatformInfo.subtractMoney(subscriptionCost);
			///加奖金
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
		ticketPlatformInfo = ticketThenEntityManager.saveTicketPlatformInfo(ticketPlatformInfo);
		
		Transaction tran = transactionDao.get(scheme.getTransactionId());
		if (tran.getState() != TransactionState.UNDER_WAY)
					throw new ServiceException("交易记录当前状态为["
							+ tran.getState().getStateName() + "],不允许添加预付款.");

				// 创建资金明细
		FundDetail fundDetail = new FundDetail();
		fundDetail.setMode(FundMode.OUT);
		fundDetail.setType(FundDetailType.SUBSCRIPTION);
		fundDetail.setMoney(subscriptionCost);
		fundDetail.setRemark(sb.toString());
		fundDetail.setResultMoney(ticketPlatformInfo.getRemainMoney());
		fundDetail.setUserId(user.getId());
		fundDetail.setUserName(user.getUserName());
		fundDetail.setPlatform(PlatformInfo.TICKET);
		fundDetail = fundDetailDao.save(fundDetail);

				// 创建预付款记录
		Prepayment prepayment = new Prepayment();
		prepayment.setType(PrepaymentType.SUBSCRIPTION);
		prepayment.setState(PrepaymentState.AWAIT);
		prepayment.setTransactionId(tran.getId());
		prepayment.setUserId(user.getId());
		prepayment.setAmount(subscriptionCost);
		prepayment.setCreateFundDetailId(fundDetail.getId());
		prepayment.setLotteryType(scheme.getLotteryType());
		prepayment = prepaymentDao.save(prepayment);
		//创建加入
		Subscription subscription = new Subscription();
		subscription.setUserId(user.getId());
		subscription.setUserName(user.getUserName());
		subscription.setCost(subscriptionCost);
		subscription.setState(SubscriptionState.NORMAL);
		subscription.setWay(SubscriptionWay.INITIATE);
		subscription.setLotteryType(scheme.getLotteryType());
		subscription.setSchemeId(scheme.getId());
		subscription.setPrepaymentId(prepayment.getId());
		subscription.setPlatform(PlatformInfo.TICKET);
		subscription = subscriptionDao.save(subscription);
		  ///写入接票表
		TicketThen ticketThen = new TicketThen();
		ticketThen.setOutOrderNumber(schemeDTO.getOutOrderNumber());
				ticketThen.setOrderId(scheme.getOrderId());
				ticketThen.setOfficialEndTime(issueData.getEndedTime());
				ticketThen.setPlatformInfoId(ticketPlatformInfo.getId());
				ticketThen.setUserId(user.getId());
				ticketThen.setLotteryType(this.getLottery());
				ticketThen.setPeriodNumber(scheme.getPeriodNumber());
				ticketThen.setSchemeNumber(scheme.getSchemeNumber());
				ticketThen.setUnits(scheme.getUnits());
				ticketThen.setMultiple(scheme.getMultiple());
				ticketThen.setSchemeCost(scheme.getSchemeCost());
				ticketThen.setState(TicketSchemeState.FULL);
				ticketThen.setMode(scheme.getMode());
				ticketThen.setPlayType(getPlayType(schemeDTO));
		ticketThen=this.ticketThenEntityManager.saveTicketThen(ticketThen);
		TicketDatail ticketDatail = new TicketDatail();
		ticketDatail.setTicketId(ticketThen.getId());
		ticketDatail.setContent(scheme.getContentText());
		this.ticketThenEntityManager.saveTicketDatail(ticketDatail);
		///写入接票详情
		ticketOther(scheme);
		return scheme;
	}
	///玩法类型 继承类重写
    public Byte getPlayType(KenoSchemeDTO schemeDTO){
			return Byte.valueOf("0");
	}
    //继承重写 
    public void ticketOther(Scheme scheme){
			
	}
	/**
	 * 发起方案
	 * 
	 * @param dto
	 * @return
	 */
	public S createScheme(KenoSchemeDTO schemeDTO) {
		I issueData = issueDataDao.get(schemeDTO.getPeriodId());
		checkConformPeriodInitConfig(issueData, schemeDTO);

		User user = userManager.getUser(schemeDTO.getSponsorId());
		checkUser(user, schemeDTO);

		S scheme = null;
		try {
			scheme = schemeDao.getEntityClass().newInstance();
		} catch (InstantiationException e) {
			throw new ServiceException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new ServiceException(e.getMessage());
		}
		scheme = newInstance(schemeDTO);
		 //用户来源
        if(null!=schemeDTO.getPlatform()){
        	scheme.setPlatform(schemeDTO.getPlatform());
        }
        Date commitTime = DateUtils.addMinutes(issueData.getEndedTime(), -kenoPlayer.getBeforeTime());
        commitTime = DateUtils.addSeconds(commitTime, -kenoPlayer.getBeforeSecondsTime());
        scheme.setCommitTime(commitTime);
        
		scheme.setPeriodNumber(issueData.getPeriodNumber());
		scheme.setSponsorName(user.getUserName());
		StringBuilder sb = new StringBuilder(50);
		sb.append("【").append(scheme.getLotteryType().getLotteryName()).append("】").append(scheme.getPeriodNumber())
				.append("期").append(scheme.getMode().getModeName()).append(scheme.getShareType().getShareName())
				.append("方案.");
		Transaction tran = userManager.createTransaction(TransactionType.SCHEME, sb.toString());
		scheme.setTransactionId(tran.getId());
		// 追号处理
		if (schemeDTO.isChase()) {
			Lottery lotteryType = scheme.getLotteryType();
			ChasePlan chasePlan = new ChasePlan();
			chasePlan.setPlatform(schemeDTO.getPlatform());
			chasePlan.setLotteryType(lotteryType);
			chasePlan.setState(ChaseState.RUNNING);
			chasePlan.setUserId(user.getId());
			chasePlan.setUserName(user.getUserName());
			chasePlan.setTotalCost(schemeDTO.getTotalCostOfChase());
			chasePlan.setRandom(schemeDTO.isRandomOfChase());
			chasePlan.setRandomUnits(schemeDTO.getRandomUnitsOfChase());
			chasePlan.setHasDan(false);
			chasePlan.setWonStop(schemeDTO.isWonStopOfChase());
			chasePlan.setPrizeForWonStop(schemeDTO.getPrizeForWonStopOfChase());
			chasePlan.setOutNumStop(schemeDTO.isOutNumStop());
			chasePlan = this.setChasePlanPlayType(chasePlan, schemeDTO);
			chasePlan.setUnits(schemeDTO.getUnits());
			chasePlan.setMode(schemeDTO.getMode());
			chasePlan.setContent(schemeDTO.getContent());
			chasePlan.setSchemeCost(schemeDTO.getSchemeCost());
			chasePlan.setCapacityProfit(schemeDTO.getCapacityProfit());

			try {
				chasePlan.setMultiples(schemeDTO.getMultiplesOfChase());
			} catch (DataException e) {
				throw new ServiceException(e.getMessage());
			}
			StringBuilder sb1 = new StringBuilder(20);
			sb1.append("发起【").append(lotteryType.getLotteryName()).append("】追号.").append("已冻结金额：")
			.append(BigDecimalUtil.valueOf(chasePlan.getTotalCost()) + " 元  ").append(" 其中：");

			tran = userManager.createTransaction(TransactionType.CHASE, sb1.toString());
			chasePlan.setTransactionId(tran.getId());
			Prepayment chasePrepayment = userManager.createKenoPrepayment(chasePlan.getTransactionId(),
					user.getId(), BigDecimal.valueOf(chasePlan.getTotalCost()), PrepaymentType.CHASE,
					FundDetailType.CHASE, sb1.toString(),schemeDTO.getPlatform(),this.getLottery());
					
			// 关联 chasePrepayment.getId()
			chasePlan.setPrepaymentId(chasePrepayment.getId());

			if (schemeDTO.getStartChasePeriodId() != null) {
				if (scheme.getPeriodId().longValue() != schemeDTO.getStartChasePeriodId().longValue()) {
					I period = this.findIssueDataById(schemeDTO.getStartChasePeriodId());
					chasePlan.setCurPeriodId(period.getPrevPreriodId());
					chasePlan.setChasedCost(0);
					chasePlan = chasePlanEntityManager.saveChasePlan(chasePlan);
					scheme = null;
					return null;
				}
			}

			try {
				Integer nextMultiple = chasePlan.getNextMultiple();// 新的方案倍数
				if (nextMultiple == null || nextMultiple.intValue() <= 0) {
					throw new DataException("追号方案倍数异常！");
				}
				scheme.setMultiple(nextMultiple);
				scheme.setSchemeCost(scheme.getUnits() * nextMultiple * 2);

			} catch (DataException e) {
				throw new ServiceException(e.getMessage(), e);
			}

			try {
				chasePlan.chase(scheme.getPeriodId(), scheme.getSchemeCost());
			} catch (DataException e) {
				throw new ServiceException(e.getMessage());
			}

			chasePlan = chasePlanEntityManager.saveChasePlan(chasePlan);

			// 认购第一期方案

			scheme.setChaseId(chasePlan.getId());
			BigDecimal subscriptionCost = BigDecimal.valueOf(scheme.getSchemeCost());
			try {
				scheme.subscription(subscriptionCost);
			} catch (DataException e) {
				throw new ServiceException(e.getMessage(), e);
			}
			scheme = schemeDao.save(scheme);
			sb.setLength(0);
			sb.append("认购【").append(scheme.getLotteryType().getLotteryName()).append("】")
					.append(scheme.getPeriodNumber()).append("期").append("方案[").append(scheme.getSchemeNumber())
					.append("],认购金额从追号预付款(追号冻结资金)中扣除. 其中：");
			Prepayment subscriptionPrepayment = userManager.transferKenoPrepayment(scheme.getTransactionId(),
					chasePlan.getPrepaymentId(), subscriptionCost, PrepaymentType.SUBSCRIPTION,
					FundDetailType.SUBSCRIPTION, sb.toString(),schemeDTO.getPlatform(),this.getLottery());
			saveSubscription(scheme, subscriptionPrepayment.getId(), user, subscriptionCost, SubscriptionWay.INITIATE,schemeDTO.getPlatform());
			return scheme;
		} else {
			
			switch (scheme.getShareType()) {
			case TOGETHER:
				scheme=doTogether(scheme, schemeDTO, user);
				break;
			case SELF:
				scheme = doSelf(scheme, schemeDTO, user);
				break;
			default:
				throw new ServiceException("没有该方案分享类型: " + scheme.getShareType());
			}
			
//			BigDecimal subscriptionCost = BigDecimal.valueOf(scheme.getSchemeCost());
//			try {
//				scheme.subscription(subscriptionCost);
//			} catch (DataException e) {
//				throw new ServiceException(e.getMessage(), e);
//			}
//			scheme = schemeDao.save(scheme);
            
//			createSubscription(scheme, user, subscriptionCost, SubscriptionWay.INITIATE,schemeDTO.getPlatform(),schemeDTO.getZoomType());

			return scheme;
		}
	}
	
	
	private S doSelf(S scheme, KenoSchemeDTO schemeDTO, User user) {
		BigDecimal subscriptionCost = BigDecimalUtil.valueOf(scheme.getSchemeCost());
		try {
			scheme.subscription(subscriptionCost);
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		scheme = schemeDao.save(scheme);

		createSubscription(scheme, user, subscriptionCost, SubscriptionWay.INITIATE,schemeDTO.getPlatform());

		return scheme;
	}
	/**
	 * 处理发起合买方案
	 * 
	 * @param scheme
	 *            方案对象
	 * @param schemeDTO
	 *            方案发起的数据传输对象
	 * @param user
	 *            方案发起人
	 * @return 创建的方案
	 */
	protected S doTogether(S scheme, KenoSchemeDTO schemeDTO, User user) {
		if (schemeDTO.getSponsorSubscriptionCost() == null || schemeDTO.getSponsorSubscriptionCost().doubleValue() <= 0)
			throw new ServiceException("认购金额不为空、小于或等于0.");

		double sponsorMinSubscriptionCost = scheme.getSchemeCost() * Constant.SPONSOR_MIN_SUBSCRIPTION_PERCENT;
		if (schemeDTO.getSponsorSubscriptionCost().doubleValue() < sponsorMinSubscriptionCost)
			throw new ServiceException("发起人至少必须认购方案金额的" + (Constant.SPONSOR_MIN_SUBSCRIPTION_PERCENT * 100) + "%,即"
					+ Constant.MONEY_FORMAT.format(sponsorMinSubscriptionCost) + "元.");

		boolean hasBaodi = schemeDTO.getSponsorBaodiCost() != null && schemeDTO.getSponsorBaodiCost().doubleValue() > 0;
		try {
			scheme.subscription(schemeDTO.getSponsorSubscriptionCost());
			if (hasBaodi) {
				scheme.baodi(schemeDTO.getSponsorBaodiCost());
			}
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		scheme = schemeDao.save(scheme);// 保存方案
		createSubscription(scheme, user, schemeDTO.getSponsorSubscriptionCost(), SubscriptionWay.INITIATE,schemeDTO.getPlatform());
		if (hasBaodi) {
			createBaodi(scheme, user, schemeDTO.getSponsorBaodiCost(),schemeDTO.getPlatform());
		}
		return scheme;
	}
	private Baodi createBaodi(S scheme, User user, BigDecimal sponsorBaodiCost,
			PlatformInfo platform) {
		StringBuilder sb = new StringBuilder(50);
		sb.append("保底方案[").append(scheme.getSchemeNumber()).append("].");
		Prepayment prepayment = userManager.createPrepayment(scheme.getTransactionId(), user.getId(), sponsorBaodiCost,
				PrepaymentType.BAODI, FundDetailType.BAODI, sb.toString(),platform,scheme.getLotteryType());

		Baodi baodi = new Baodi();
		baodi.setSchemeId(scheme.getId());
		baodi.setPrepaymentId(prepayment.getId());
		baodi.setUserId(user.getId());
		baodi.setUserName(user.getUserName());
		baodi.setCost(sponsorBaodiCost);
		baodi.setState(BaodiState.NORMAL);
		baodi.setLotteryType(scheme.getLotteryType());
		baodi.setPlatform(platform);
//		return getSchemeEntityManager().saveBaodi(baodi);
		return baodiDao.save(baodi);
		
	}
	/**
	 * 创建认购记录
	 * 
	 * @param scheme
	 *            认购 的方案
	 * @param user
	 *            认购的用户
	 * @param cost
	 *            认购的金额
	 * @param way
	 *            认购的方式
	 * @return 认购记录
	 */
	protected Subscription createSubscription(S scheme, User user, BigDecimal cost, SubscriptionWay way,PlatformInfo platform) {
		StringBuilder sb = new StringBuilder(50);
		sb.append("认购方案[").append(scheme.getSchemeNumber()).append("].");
		Prepayment prepayment = userManager.createKenoPrepayment(scheme.getTransactionId(), user.getId(), cost,
				PrepaymentType.SUBSCRIPTION, FundDetailType.SUBSCRIPTION, sb.toString(),platform,this.getLottery());


		return saveSubscription(scheme, prepayment.getId(), user, cost, way,platform);
	}

	/**
	 * 创建认购记录
	 * 
	 * @param scheme
	 *            认购的方案
	 * @param prepaymentId
	 *            预付款ID
	 * @param user
	 *            认购的用户
	 * @param cost
	 *            认购 的金额
	 * @param way
	 *            认购的方式
	 * @return 认购记录
	 */
	protected Subscription saveSubscription(S scheme, Long prepaymentId, User user, BigDecimal cost, SubscriptionWay way,PlatformInfo platform) {
		Subscription subscription = new Subscription();
		subscription.setUserId(user.getId());
		subscription.setUserName(user.getUserName());
		subscription.setCost(cost);
		subscription.setState(SubscriptionState.NORMAL);
		subscription.setWay(way);
		subscription.setLotteryType(scheme.getLotteryType());
		subscription.setSchemeId(scheme.getId());
		subscription.setPrepaymentId(prepaymentId);
		subscription.setPlatform(platform);
		return subscriptionDao.save(subscription);
	}

	/**
	 * 验证用户是否符合发起方案的相关条件
	 * 
	 * @param user
	 *            用户对象
	 * @param schemeDTO
	 *            发起方案的数据传输对象
	 */
	@Transactional(readOnly = true)
	protected void checkUser(User user, KenoSchemeDTO schemeDTO) {
		if (user.isLocked())
			throw new ServiceException("您的帐户已经被锁定,不能发起方案.如有什么疑问,请联系我们的客服人员.");
		BigDecimal totalCost = BigDecimal.ZERO;
		if (schemeDTO.isChase()) {
			totalCost = BigDecimal.valueOf(schemeDTO.getTotalCostOfChase());
			schemeDTO.getSponsorSubscriptionCost();
		}else if(schemeDTO.getSponsorSubscriptionCost() != null){
			if (schemeDTO.getSponsorSubscriptionCost() != null
					&& schemeDTO.getSponsorSubscriptionCost().doubleValue() > 0)
				totalCost = totalCost.add(schemeDTO.getSponsorSubscriptionCost());
			if (schemeDTO.getSponsorBaodiCost() != null
					&& schemeDTO.getSponsorBaodiCost().doubleValue() > 0)
				totalCost = totalCost.add(schemeDTO.getSponsorBaodiCost());
		}else {
			totalCost = BigDecimal.valueOf(schemeDTO.getSchemeCost());
		}
		BigDecimal allAccountBalance = userManager.getUser(user.getId()).getRemainMoney();

		BigDecimal remainMoney = (allAccountBalance != null) ? allAccountBalance : BigDecimal.ZERO;
		if (remainMoney.doubleValue() < totalCost.doubleValue())
			throw new ServiceException("对不起,完成此次交易共需要[" + Constant.MONEY_FORMAT.format(totalCost) + "]元,您的账户余额只有["
					+ Constant.MONEY_FORMAT.format(remainMoney) + "]元,不够支付此次交易.");
	}

	@Transactional(readOnly = true)
	protected void checkConformPeriodInitConfig(I issueData, KenoSchemeDTO schemeDTO) {
		if (null == issueData) {
			throw new ServiceException("期号为空");
		}
		if (isPause()) {
			throw new ServiceException("对不起,系统暂停销售！");
		}
		if (issueData.isSaleEnded()) {
			throw new ServiceException("对不起," + issueData.getPeriodNumber() + "已经截止！");
		}
		Long i = (new Date()).getTime() - Long.valueOf(schemeDTO.getBeforeTime() * 1000 * 60);
		if (issueData.getEndedTime().getTime() < (new Date()).getTime()
				- Long.valueOf(schemeDTO.getBeforeTime() * 1000 * 60)) {
			throw new ServiceException("对不起," + issueData.getPeriodNumber() + "已经截止！");
		}
	}

	@Transactional(readOnly = true)
	public void setUserManager(UserEntityManager userManager) {
		this.userManager = userManager;
	}

	@Transactional(readOnly = true)
	public I getNextPeriod(long periodId) {
		return this.issueDataDao.findNextIssueData(periodId);
	}

	/**
	 * 查询列表
	 * 
	 * @param criteria
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<S> findByCriteria(XDetachedCriteria criteria) {
		return schemeDao.findByDetachedCriteria(criteria);
	}

	/**
	 * 更新开奖号码
	 * 
	 * @param issueData
	 */
	public void updateResults(I issueData) {
		I oldIssueData = issueDataDao.get(issueData.getId());
		if (oldIssueData != null) {
			if (!StringUtils.isBlank(issueData.getResults())) {
				// if(!oldIssueData.isDrawed()){
				// oldIssueData.setDrawed(true);
				// }
				if (oldIssueData.getState().getStateValue() < IssueState.ISSUE_SATE_RESULT.getStateValue()) {
					oldIssueData.setState(IssueState.ISSUE_SATE_RESULT);
				}
				// oldIssueData.addAfterSaleFlags(AfterSaleFlag.RESULT_UPDATED.getFlagValue());
				oldIssueData.setResults(issueData.getResults());
			}
			issueDataDao.save(oldIssueData);
		}
	}

	/**
	 * 暂停/启动销售
	 */
	public void pauseOrStartLottery() {
		KenoSysConfig config = kenoSysConfigDao.get(this.getLottery().getKey() + KenoSysConfig.PAUSE);
		if (config != null) {
			String value = config.getKeyValue();
			if (!StringUtils.isBlank(value) && "true".equals(value.trim())) {
				config.setKeyValue("false");
			} else {
				config.setKeyValue("true");
			}
			kenoSysConfigDao.save(config);
		}
	}

	/**
	 * 暂停/启动销售状态值
	 * 
	 * @return
	 */
	public String findPauseOrStart() {
		return isPause() ? "true" : "false";
	}

	@Transactional(readOnly = true)
	protected boolean isPause() {
		KenoSysConfig config = kenoSysConfigDao.get(this.getLottery().getKey() + KenoSysConfig.PAUSE);
		if (config != null) {
			String value = config.getKeyValue();
			if (!StringUtils.isBlank(value) && "true".equals(value.trim())) {
				return true;
			}
		}
		return false;
	}

	@Resource(name = "kenoSysConfigDao")
	public void setKenoSysConfigDao(KenoSysConfigDao kenoSysConfigDao) {
		this.kenoSysConfigDao = kenoSysConfigDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public S cloneNewScheme(S oldScheme) {
		this.schemeDao.getSession().evict(oldScheme);
		oldScheme.setSubscriptions(null);
		S newScheme = null;
		try {
			newScheme = schemeDao.getEntityClass().newInstance();
			try {
				try {
					PropertyUtils.copyProperties(newScheme, oldScheme);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			} catch (InvocationTargetException  e) {
				throw new ServiceException(e.getMessage());
			}
		} catch (InstantiationException e) {
			throw new ServiceException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new ServiceException(e.getMessage());
		}
	
		newScheme.setId(null);
		oldScheme = schemeDao.get(oldScheme.getId());
		return newScheme;
	}

	@Transactional(readOnly = true)
	public S getLastChaseScheme(long chaseId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.schemeDao.getEntityClass());
		detachedCriteria.add(Restrictions.eq("chaseId", chaseId));
		detachedCriteria.addOrder(Order.desc("id"));
		List<S> schemeList = this.schemeDao.findByDetachedCriteria(detachedCriteria, 0, 1, true);
		if (schemeList != null && schemeList.size() > 0) {
			return schemeList.get(0);
		}
		return null;
	}

	public void handChasePlan(long chasePlanId) {
		ChasePlan chasePlan = this.chasePlanEntityManager.getChasePlan(chasePlanId);
		S lastScheme = this.getLastChaseScheme(chasePlan.getId());
		if (null == lastScheme) {
			lastScheme = this.newInstance(chasePlan);
		}
		boolean canChase = true;
		if (chasePlan.isWonStop() && lastScheme.isWon() && lastScheme.getState() == SchemeState.SUCCESS) {
			if (chasePlan.getPrizeForWonStop() != null && chasePlan.getPrizeForWonStop() >= 0) {
				if (lastScheme.getPrize().intValue() >= chasePlan.getPrizeForWonStop()) {
					canChase = false;
				}
			}
		}
		if (!canChase) {
			this.stopChasePlan(chasePlan.getId(), "方案中奖");// 中奖后停止追号
		} else {
			// 获取将要追号的销售期，并判断是否允许发起追号方案
			I nexPeriod = this.getNextPeriod(chasePlan.getCurPeriodId());
			if (nexPeriod.isSaleEnded()) {
				this.stopChasePlan(chasePlan.getId(), "由于网络故障");
			} else {
				Integer multiple = chasePlan.getNextMultiple();
				if (multiple == null || multiple <= 0) {
					try {
						chasePlan.skip(nexPeriod.getId());
					} catch (DataException e) {
						throw new ServiceException(e.getMessage(), e);
					}
					this.userChasePlan(chasePlan);
					return;
				}
				// 创建新方案交易记录
				Transaction tran = userManager.createTransaction(TransactionType.SCHEME, "追号生成方案");
				// 从旧方案复制生成新方案
				S newScheme = null;
				if (null != lastScheme.getId()) {
					newScheme = this.cloneNewScheme(lastScheme);
				} else {
					newScheme = lastScheme;
				}
				try {
					newScheme.reset(nexPeriod, chasePlan, tran.getId());// 重置方案
				} catch (DataException e) {
					throw new ServiceException(e.getMessage(), e);
				}
				newScheme.setId(null);// 确认
				newScheme = schemeDao.save(newScheme);

				// 从追号预付款中扣除新追号方案的方案金额生成新的预付款
				StringBuffer memo = new StringBuffer(50);
				memo.append("自购追号，新追号方案[").append(newScheme.getSchemeNumber())
						.append("]，所需费用从追号预扣款(追号冻结资金)中转移。其中转移消费明细： ");
				Prepayment prepayment = userManager.transferKenoPrepayment(tran.getId(),
						chasePlan.getPrepaymentId(), BigDecimalUtil.valueOf(newScheme.getSchemeCost()),
						PrepaymentType.CHASE, FundDetailType.CHASE, memo.toString(),chasePlan.getPlatform(),this.getLottery());
				// 修改重置认购对象，保存新的认购对象
				List<Subscription> joinList = findSubscriptionsOfScheme(lastScheme.getId(), null);

				if (null == joinList || joinList.isEmpty() || joinList.size() == 0) {
					Subscription subscription = new Subscription();
					subscription.setUserId(chasePlan.getUserId());
					subscription.setUserName(chasePlan.getUserName());
					subscription.setCost(BigDecimalUtil.valueOf(newScheme.getSchemeCost()));
					subscription.setState(SubscriptionState.NORMAL);
					subscription.setWay(SubscriptionWay.NORMAL);
					subscription.setLotteryType(newScheme.getLotteryType());
					subscription.setSchemeId(newScheme.getId());
					subscription.setPrepaymentId(prepayment.getId());
					subscriptionDao.save(subscription);
				} else {
					Subscription oldSubscrt = joinList.get(0);
					Subscription newSubscrt = cloneNewSubscription(oldSubscrt);
					newSubscrt.reset(newScheme, prepayment.getId());
					newSubscrt = subscriptionDao.save(newSubscrt);
				}

				// 更新追号数据
				try {
					chasePlan.chase(newScheme.getPeriodId(), newScheme.getSchemeCost().intValue());
				} catch (DataException e) {
					throw new ServiceException(e.getMessage(), e);
				}
				this.userChasePlan(chasePlan);
			}
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Subscription> findSubscriptionsOfScheme(final long schemeId, final SubscriptionState state) {
		final S scheme = getScheme(schemeId);
		DetachedCriteria criteria = DetachedCriteria.forClass(Subscription.class);
		criteria.add(Restrictions.eq("schemeId", schemeId));
		criteria.add(Restrictions.eq("lotteryType", scheme.getLotteryType()));
		if (state != null) {
			criteria.add(Restrictions.eq("state", state));
		}
		return subscriptionDao.findByDetachedCriteria(criteria);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Subscription cloneNewSubscription(Subscription oldSubscription) {
		subscriptionDao.getSession().evict(oldSubscription);
		Subscription newSubscrt =  new Subscription();
		try {
			 try {
				PropertyUtils.copyProperties(newSubscrt, oldSubscription);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		} catch (IllegalAccessException  e) {
			 throw new ServiceException(e.getMessage());
		}
		newSubscrt.setId(null);
		return newSubscrt;
	}

	public void stopChasePlan(long chasePlanId, String memo) {
		ChasePlan chasePlan = this.chasePlanEntityManager.getChasePlan(chasePlanId);
		if (chasePlan == null)
			throw new ServiceException("追号记录不存在.");
		try {
			chasePlan.stop();
		} catch (DataException e) {
		}
		String multiples[]=chasePlan.getMultiples().split(",");
		StringBuffer sb_=new StringBuffer();
		for(String multiple:multiples){
			if("1".equals(multiple)){
				multiple="0";
			}
			sb_.append(multiple).append(",");;
		}
		sb_.delete(sb_.length()-1, sb_.length());
		chasePlan.setMultiples(sb_.toString());
		
		chasePlan = chasePlanEntityManager.saveChasePlan(chasePlan);

		int remainMoney = chasePlan.getTotalCost() - chasePlan.getChasedCost();

		StringBuilder sb = new StringBuilder(50);
		if (StringUtils.isNotBlank(memo)) {
			sb.append(memo + ",");
		}
		sb.append("停止编号为[").append(chasePlan.getId()).append("]的追号.");
		User user = userManager.getUser(chasePlan.getUserId());
		if (remainMoney > 0) {
			userManager.cancelPrepayment(chasePlan.getPrepaymentId(), FundDetailType.STOP_CHASE,
					sb.toString());
		}
	}

	@Transactional(readOnly = true)
	public List<I> findCanCloseIssue(Date time) {
		return this.issueDataDao.findCanCloseIssue(time);
	}

	/**
	 * 统计总方案数
	 * 
	 * @param periodId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Integer calAllSchemeCount(Long periodId) {
		Class<S> clazz = ReflectionUtils.getSuperClassGenricType(getClass(), 1);
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Restrictions.eq("periodId", periodId));
		criteria.setProjection(Projections.rowCount());
		List<Integer> resultList = schemeDao.findByDetachedCriteria(criteria);
		if (resultList != null && resultList.size() > 0) {
			return resultList.get(0);
		} else {
			return 0;
		}
	}

	/**
	 * 统计总销量
	 * 
	 * @param periodId
	 * @return
	 */
	@Transactional(readOnly = true)
	public Integer calAllSaleCount(Long periodId) {
		Class<S> clazz = ReflectionUtils.getSuperClassGenricType(getClass(), 1);
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Restrictions.eq("periodId", periodId));
		criteria.add(Restrictions.eq("state", SchemeState.SUCCESS));
		criteria.setProjection(Projections.sum("schemeCost"));
		List<Integer> resultList = schemeDao.findByDetachedCriteria(criteria);
		if (null != resultList && !resultList.isEmpty()) {
			Integer count = resultList.get(0);
			if (null != count) {
				return count.intValue();
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/**
	 * 总中奖数
	 * 
	 * @param periodId
	 * @return
	 */
	@Transactional(readOnly = true)
	public Integer calAllPrizeCount(Long periodId) {
		Class<S> clazz = ReflectionUtils.getSuperClassGenricType(getClass(), 1);
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Restrictions.eq("periodId", periodId));
		criteria.add(Restrictions.eq("won", true));
		criteria.setProjection(Projections.rowCount());
		List<Integer> resultList = schemeDao.findByDetachedCriteria(criteria);
		if (resultList != null && resultList.size() > 0) {
			return resultList.get(0);
		} else {
			return 0;
		}
	}

	/**
	 * 统计成功案数
	 * 
	 * @param periodId
	 * @return
	 */
	@Transactional(readOnly = true)
	public Integer calSuccessSchemeCount(Long periodId) {
		Class<S> clazz = ReflectionUtils.getSuperClassGenricType(getClass(), 1);
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Restrictions.eq("periodId", periodId));
		criteria.add(Restrictions.eq("state", SchemeState.SUCCESS));
		criteria.setProjection(Projections.rowCount());
		List<Integer> resultList = schemeDao.findByDetachedCriteria(criteria);
		if (resultList != null && resultList.size() > 0) {
			return resultList.get(0);
		} else {
			return 0;
		}
	}

	/**
	 * 得到可以追号的期数
	 * 
	 * @param dateNow
	 *            系统时间
	 * @param beforeTime
	 *            提前截至时间
	 * @param size
	 *            取的数
	 * @return 一条配置信息
	 */
	@Transactional(readOnly = true)
	public List<I> getCanChaseIssue(Date dateNow, Integer beforeTime, Integer size) {
		if (null == dateNow)
			return null;
		if (null == beforeTime)
			return null;
		if (null == size)
			return null;
		Class<S> clazz = ReflectionUtils.getSuperClassGenricType(getClass(), 0);
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Restrictions.not(Restrictions.eq("state", IssueState.ISSUE_SATE_FINISH)));
		criteria.add(Restrictions.gt("endedTime", DateUtils.addMinutes(dateNow, beforeTime)));
		criteria.addOrder(Order.asc("endedTime"));
		return issueDataDao.findByDetachedCriteria(criteria, 0, size, true);
	}

	/**
	 * 得到可以追号的期数
	 * 
	 * @param dateNow
	 *            系统时间
	 * @param beforeTime
	 *            提前截至时间
	 * @return 一条配置信息
	 */
	@Transactional(readOnly = true)
	public Integer getCanChaseIssueNum(Date dateNow, Integer beforeTime) {
		if (null == dateNow)
			return null;
		if (null == beforeTime)
			return null;
		Class<S> clazz = ReflectionUtils.getSuperClassGenricType(getClass(), 0);
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Restrictions.not(Restrictions.eq("state", IssueState.ISSUE_SATE_FINISH)));
		criteria.add(Restrictions.gt("endedTime", DateUtils.addMinutes(dateNow, beforeTime)));
		criteria.addOrder(Order.asc("endedTime"));
		criteria.setProjection(Projections.rowCount());
		List<Integer> resultList = schemeDao.findByDetachedCriteria(criteria, true);
		if (resultList != null && resultList.size() > 0) {
			return resultList.get(0);
		} else {
			return 0;
		}
	}

	/**
	 * 设置期号时间
	 * 
	 * @param dateStar
	 *            开始时间
	 * @param dateEnd
	 *            结束时间
	 * @param dateMin
	 *            操作时间（可以是负数，action判断）
	 * @return 一条配置信息
	 */
	public void oprIssueTime(Date dateStar, Date dateEnd, Integer dateMin) {
		if (null == dateStar)
			throw new ServiceException("起始日期为空");
		if (null == dateEnd)
			throw new ServiceException("结束日期为空");
		if (null == dateMin)
			throw new ServiceException("操作时间为空.");
		Class<S> clazz = ReflectionUtils.getSuperClassGenricType(getClass(), 0);
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Restrictions.ge("endedTime", dateStar));
		criteria.add(Restrictions.le("endedTime", dateEnd));
		criteria.addOrder(Order.asc("endedTime"));
		List<I> resultList = schemeDao.findByDetachedCriteria(criteria, true);
		if (resultList != null && resultList.size() > 0) {
			for (I issue : resultList) {
				issue.setStartTime(DateUtils.addMinutes(issue.getStartTime(), dateMin));
				issue.setEndedTime(DateUtils.addMinutes(issue.getEndedTime(), dateMin));
				issue.setPrizeTime(DateUtils.addMinutes(issue.getPrizeTime(), dateMin));
			}
		} else {
			if (null == dateMin)
				throw new ServiceException("找不到相应记录.");
		}
	}

	/**
	 * 方案撤单
	 */
	public S cancelScheme(Long id, AdminUser adminUser) {
		if (null == id)
			throw new ServiceException("方案ID为空.");
		S s = schemeDao.get(id);
		if (null == s)
			throw new ServiceException("找不到相应方案.");
		if (null == s.getSponsorId())
			throw new ServiceException("找不到相应方案的发起人.");
		if (null == s.getSchemeCost())
			throw new ServiceException("找不到相应方案的金额.");
		if (null == s.getPeriodId())
			throw new ServiceException("找不到相应方案的方案号.");
		Long issueId = s.getPeriodId();
		I issue = issueDataDao.get(issueId);
		if (null == issue)
			throw new ServiceException("找不到相应期号.");
		if (null == issue.getState())
			throw new ServiceException("相应期号没有销售状态.");
		if (SchemeState.CANCEL.equals(s.getState()))
			throw new ServiceException("不能重复撤单.");
		if (issue.isDrawed()) {
			// 期状态已经开奖不能撤单
			throw new ServiceException("期状态已经开奖不能撤单.");
		}
		s.setState(SchemeState.CANCEL);
		schemeDao.save(s);
		// cyy-c 2011-04-13 加入高频彩派奖写入加入表
		// 加载正常的加入对象列表
		List<Subscription> subscriptionList = findSubscriptionsOfScheme(s.getId(), SubscriptionState.NORMAL);// /读出跟单列表
		if (null != subscriptionList && !subscriptionList.isEmpty()) {
			 // 正常情况高频彩只有一个加入
			//xxxx-c 2014-09-23 修改高频已经加入了合买
			for (Subscription subscription : subscriptionList) {
				subscription.setState(SubscriptionState.CANCEL);
				subscriptionDao.save(subscription);// 保存
			}
		}
		StringBuilder cause = new StringBuilder(50);
		cause.append(adminUser == null ? "系统" : "管理员").append("撤销方案[").append(s.getSchemeNumber()).append("].");
		userManager.cancelTransaction(s.getTransactionId(), FundDetailType.CANCEL_SCHEME, cause.toString());
		ticketThenEntityManager.synchronizationFailTicket(s);
		return s;
	}
	/**
	 * 方案设为成功
	 */
	public S commitTransactionScheme(Long id, AdminUser adminUser) {
		if (null == id)
			throw new ServiceException("方案ID为空.");
		S s = schemeDao.get(id);
		userManager.commitTransaction(s.getTransactionId());
		return s;
	}
	// 将保底与完成交易合在了一起
	public void commitOrCancelTransaction(Long schemeId) {
		S scheme = schemeDao.get(schemeId);
		if (null == scheme)
			throw new ServiceException("方案为空.");
		
		System.out.println(scheme.isSendToPrint()+"~~~~"+scheme.getSchemePrintState());
		if (scheme.isSendToPrint() &&
				(scheme.getState().equals(com.cai310.lottery.common.SchemeState.UNFULL)||scheme.getState().equals(com.cai310.lottery.common.SchemeState.FULL))&&
				SchemePrintState.SUCCESS.equals(scheme.getSchemePrintState())) {// 已出票，使用保底
			//出票成功&状态是满员或者未满（保底）
			if (scheme.isHasBaodi()) {// 用户保底...
				List<Baodi> baodiList = this.findNormalBaodi(scheme.getId());
				if (baodiList != null && !baodiList.isEmpty()) {
					for (Baodi baodi : baodiList) {
						if (scheme.canUseBaodi()) {
							this.baodiTransferSubscription(baodi, scheme);// 使用保底
							System.out.println("使用了保底");
						} else {
							this.cancelBaodi(baodi, scheme, false);// 撤销保底
						}
					}
				} else {
					throw new ServiceException("找不到可用保底！");
				}
			}
			if (scheme.isCanSubscribe()) {// 网站保底...
				System.out.println("网站保底有没有进来？");
				User userInfo = userManager.getUser(Constant.SITE_BAODI_USER_ID);// 加载保底用户
				BigDecimal cost = scheme.getRemainingCost();
				try {
					scheme.subscription(cost);
				} catch (DataException e) {
					throw new ServiceException(e.getMessage(), e);
				}
				//增加平台来源。默认是web
				PlatformInfo platformInfo = PlatformInfo.WEB;
				this.createSubscription(scheme, userInfo, cost, SubscriptionWay.SITE_BAODI,platformInfo);
			}
			// 执行完成交易
			if (scheme.canCommitTransaction()) {
				scheme.commitTransaction();// 完成交易
				scheme = schemeDao.save(scheme);// 更新方案对象
//				// 执行操作：完成交易
//				userManager.commitTransaction(scheme.getTransactionId());
				this.commitTransactionScheme(schemeId,null);
			} else {
				throw new ServiceException("数据异常！");
			}
		} else {// 未出票，撤销方案
			if(scheme.getState().equals(com.cai310.lottery.common.SchemeState.CANCEL)||scheme.getState().equals(com.cai310.lottery.common.SchemeState.REFUNDMENT)){
	    	}else{
	    		this.cancelScheme(scheme.getId(), null);
	    	}
		}
	}

	
	public S forcePrint(Long schemeId) {
		S scheme= schemeDao.get(schemeId);
		if (scheme == null)
			throw new ServiceException("方案不存在.");
		scheme.setSendToPrint(true);// 送票
		scheme.setSchemePrintState(SchemePrintState.SUCCESS);// 出票
		scheme = schemeDao.save(scheme);
		return scheme;
	}
	public S agentAnalyse(Long schemeId) {
		S scheme= schemeDao.get(schemeId);
		if (scheme == null)
			throw new ServiceException("方案不存在.");
		scheme.setAgentAnalyseState(AgentAnalyseState.UPDATED);
		scheme = schemeDao.save(scheme);
		return scheme;
	}
	/**
	 * 取方案
	 * 
	 * @param scheme
	 */
	@Transactional(readOnly = true)
	public S getScheme(Long id) {
		return schemeDao.get(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal countSubscribedCost(Long schemeId, final Long userId) {
		final S scheme = getScheme(schemeId);
		return (BigDecimal) subscriptionDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.sum("cost"));
				criteria.add(Restrictions.eq("schemeId", scheme.getId()));
				criteria.add(Restrictions.eq("userId", userId));
				criteria.add(Restrictions.eq("state", SubscriptionState.NORMAL));
				criteria.add(Restrictions.eq("lotteryType", scheme.getLotteryType()));
				return criteria.uniqueResult();
			}
		});
	}
	
	/**
	 * 更新销售统计
	 * 
	 * @param issueData
	 * @throws DataException
	 */
	public void saleAnalye(I issueData) throws DataException {
		Class<S> clazz = ReflectionUtils.getSuperClassGenricType(getClass(), 1);
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.setProjection(Projections.property("id"));
		criteria.add(Restrictions.eq("periodId", issueData.getId()));
		List<Long> schemeIdList = schemeDao.findByDetachedCriteria(criteria);
		SaleAnalyse saleAnalyse = null;
		Integer playType = null;
		Map<String, SaleAnalyse> saleAnalyeMap = new HashMap<String, SaleAnalyse>();
		S s;
		SdEl11to5Scheme sdEl11to5Scheme =null;
		SscScheme sscScheme =null;
		AhKuai3Scheme ahKuai3Scheme=null;
		XjEl11to5Scheme xjEl11to5Scheme=null; 
		Set<Long> updateSchemeSet = Sets.newHashSet();
		for (Long id : schemeIdList) {
			// ///key规则。如果没有playType key=userId_ 如果有key=userId_0或者userId_1或者。。。。
			s = getScheme(id);
			saleAnalyse = saleAnalyeMap.get(s.getSponsorId() + "_" + (null == playType ? "" : playType));
			if (null == saleAnalyse) {
				saleAnalyse = new SaleAnalyse();
			}
			updateSchemeSet.add(s.getId());
			if(Lottery.SDEL11TO5.equals(s.getLotteryType())){
				sdEl11to5Scheme=(SdEl11to5Scheme)s;
				playType=sdEl11to5Scheme.getBetType().ordinal();
			}else if(Lottery.SSC.equals(s.getLotteryType())){
				sscScheme=(SscScheme)s;
				playType=sscScheme.getBetType().ordinal();
			}else if(Lottery.AHKUAI3.equals(s.getLotteryPlayType())){
				ahKuai3Scheme=(AhKuai3Scheme)s;
				playType=ahKuai3Scheme.getBetType().ordinal();
			}else if(Lottery.XJEL11TO5.equals(s.getLotteryPlayType())){
				xjEl11to5Scheme=(XjEl11to5Scheme) s;
				playType=xjEl11to5Scheme.getBetType().ordinal();
			}
			// //公共属性
			saleAnalyse.setPeriodId(issueData.getId());
			saleAnalyse.setLottery(s.getLotteryType());
			saleAnalyse.setPeriodNumber(issueData.getPeriodNumber());
			saleAnalyse.setPlayType(playType);
			saleAnalyse.setUserId(s.getSponsorId());
			saleAnalyse.setUserName(s.getSponsorName());
			saleAnalyse.setEndedTime(issueData.getEndedTime());
			saleAnalyse.setYearNum(DateUtil.getYear(issueData.getEndedTime()));
			saleAnalyse.setQuarterNum(DateUtil.getQuarter(issueData.getEndedTime()));
			saleAnalyse.setMonthNum(DateUtil.getMonth(issueData.getEndedTime()));
			saleAnalyse.setWeekNum(DateUtil.getWeek(issueData.getEndedTime()));
			saleAnalyse.setSendCurrency(Boolean.FALSE);

			saleAnalyse.addSchemeCount(1);
			saleAnalyse.addSchemeCost(s.getSchemeCost().intValue());
			saleAnalyse.addSchemeWonPrize(s.getPrize());
			saleAnalyse.addSubscriptionCount(1);
			saleAnalyse.addSubscriptionCost(new BigDecimal(s.getSchemeCost()));
			saleAnalyse.addSubscriptionWonPrize(s.getPrize());
			if (SchemeState.SUCCESS.equals(s.getState())) {
				// 成功的方案
				saleAnalyse.addSchemeSuccessCount(1);
				saleAnalyse.addSchemeSuccessCost(s.getSchemeCost().intValue());
				saleAnalyse.addSchemeSuccessWonPrize(s.getPrize());

				saleAnalyse.addSubscriptionSuccessCount(1);
				saleAnalyse.addSubscriptionSuccessCost(new BigDecimal(s.getSchemeCost()));
				saleAnalyse.addSubscriptionSuccessWonPrize(s.getPrize());

			} else if (SchemeState.CANCEL.equals(s.getState())) {
				// 失败的方案
				saleAnalyse.addSchemeCancelCount(1);
				saleAnalyse.addSchemeCancelCost(s.getSchemeCost().intValue());
				saleAnalyse.addSchemeCancelWonPrize(s.getPrize());

				saleAnalyse.addSubscriptionCancelCount(1);
				saleAnalyse.addSubscriptionCancelCost(new BigDecimal(s.getSchemeCost()));
				saleAnalyse.addSubscriptionCancelWonPrize(s.getPrize());
			} else {
				// 数据错误
				throw new DataException("数据错误");
			}
			saleAnalyeMap.put(s.getSponsorId() + "_" + (null == playType ? "" : playType), saleAnalyse);
		}
		Set<String> keySet = saleAnalyeMap.keySet();
		List<SaleAnalyse> updateSaleAnalyse = Lists.newArrayList();
		for (String key : keySet) {
			updateSaleAnalyse.add(saleAnalyeMap.get(key));
		}
		saleAnalyseEntityManager.updateSaleAnalyse(updateSaleAnalyse, updateSchemeSet, issueData.getLotteryType());
	}

	// TODO
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List moneyAnalye(String startDate, String endDate) {
		String tableName = "Lotu_Sd_El11to5_Scheme";
		if (Lottery.EL11TO5.equals(this.getLottery())) {
			tableName = "lotu_el11to5_scheme";
		} else if (Lottery.QYH.equals(this.getLottery())) {
			tableName = "lotu_qyh_scheme";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(t.prize) from " + tableName
				+ " t where 1=1 and t.won = 1 and t.state = 2 and t.sendtoprint = '1' and t.prizesended = '1' ");
		StringBuffer ticketSql = new StringBuffer();
		ticketSql.append("select sum(g.schemecost) from lotu_ticketgz g where 1=1 ");
		if (StringUtils.isNotBlank(startDate)) {
			sql.append("and t.prizesendtime >= to_date('" + startDate + "','yyyy-mm-dd hh24:mi:ss') ");
			ticketSql.append("and g.createtime >= to_date('" + startDate + "','yyyy-mm-dd hh24:mi:ss') ");
		}
		if (StringUtils.isNotBlank(endDate)) {
			sql.append("and t.prizesendtime <= to_date('" + endDate + "','yyyy-mm-dd hh24:mi:ss') ");
			ticketSql.append("and g.createtime <= to_date('" + endDate + "','yyyy-mm-dd hh24:mi:ss') ");
		}
		sql.append(" and t.won = 1 ");
		ticketSql.append("and g.ticketfinsh = 0 and g.lotterytype=" + this.getLottery().ordinal());
		sql.append("union " + ticketSql.toString());
		System.out.println("=============" + sql.toString());
		return issueDataDao.getSession().createSQLQuery(sql.toString()).list();
	}
	
	public void cancelSchemeBySponsor(Long schemeId) {
		S scheme= schemeDao.get(schemeId);
//		T scheme = getSchemeEntityManager().getScheme(schemeId);
		if (scheme == null)
			throw new ServiceException("方案不存在.");

		if (isSaleEnded(scheme))
			throw new ServiceException("方案已截止.");

		try {
			scheme.cancel(false);
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}

		scheme = schemeDao.save(scheme);

		StringBuilder cause = new StringBuilder(50);
		cause.append("方案发起人撤销方案[").append(scheme.getSchemeNumber()).append("].");
		userManager.cancelTransaction(scheme.getTransactionId(), FundDetailType.CANCEL_SCHEME,
				cause.toString());
	}
	
	protected boolean isSaleEnded(S scheme) {
		I period = issueDataDao.findDataByNumber(scheme.getPeriodNumber());
//		if (period.isSaleEnded()) {
//			PeriodSalesId id = new PeriodSalesId(period.getId(), scheme.getMode());
//			PeriodSales periodSales = periodManager.getPeriodSales(id);
//			try {
//				periodSales.checkIsCanCancelScheme();
//			} catch (DataException e) {
//				return true;
//			}
//			return false;
//		} else {
//			return true;
//		}
		return period.isSaleEnded();
	}
	@Transactional(propagation = Propagation.NESTED)
	public S subscribe(SubscribeDTO dto) {
//		S scheme = getSchemeEntityManager().getScheme(dto.getSchemeId());
		S scheme = schemeDao.get(dto.getSchemeId());
		if((new Date()).compareTo(scheme.getCommitTime())>0){
			throw new ServiceException("当前期已截止");
		}
		if (scheme == null)
			throw new ServiceException("方案不存在.");

		if (dto.getWay() != SubscriptionWay.AUTOFOLLOW && !scheme.isAutoFollowCompleted())
			throw new ServiceException("正在处理自动跟单,请稍候再试.");

		checkConformPeriodSubscriptionConfig(scheme);
		if (!dto.getUserId().equals(scheme.getSponsorId())) {
			switch (scheme.getSubscriptionLicenseType()) {
			case PUBLIC_LICENSE:
				break;
			case PASSWORD_LICENSE:
				if (StringUtils.isBlank(dto.getPassword()))
					throw new ServiceException("方案设置了认购密码,需要输入认购密码才能认购.");
				else if (!scheme.getSubscriptionPassword().equals(dto.getPassword().trim()))
					throw new ServiceException("认购密码不正确.");
				break;
			}
		}

		boolean isSubscription = dto.getSubscriptionCost() != null && dto.getSubscriptionCost().doubleValue() > 0;
		boolean isBaodi = dto.getBaodiCost() != null && dto.getBaodiCost().doubleValue() > 0;
		if (!isSubscription && !isBaodi)
			throw new ServiceException("认购数据不正确.");

		BigDecimal totalCost = BigDecimal.ZERO;
		if (isSubscription)
			totalCost = totalCost.add(dto.getSubscriptionCost());
		if (isBaodi)
			totalCost = totalCost.add(dto.getBaodiCost());

		User user = userManager.getUser(dto.getUserId());

		if (user == null) {
			throw new ServiceException("跟单用户[#" + dto.getUserId() + "]不存在.");
		} 
		BigDecimal allAccountBalance = user.getRemainMoney();

		if (allAccountBalance != null) {
			BigDecimal remainMoney = (allAccountBalance != null) ? allAccountBalance : BigDecimal.ZERO;
			if (remainMoney.doubleValue() < totalCost.doubleValue()) {
				throw new ServiceException("对不起,完成此次交易共需要[" + Constant.MONEY_FORMAT.format(totalCost) + "]元,您的账户余额只有["
						+ Constant.MONEY_FORMAT.format(remainMoney) + "]元,不够支付此次交易.");
			}
		}

		try {
			if (isSubscription) {
				scheme.subscription(dto.getSubscriptionCost());
				System.out.println("当前保底金额:"+scheme.getSubscribedCost());
				System.out.println("3:::"+scheme.getState());
			}
			if (isBaodi) {
				scheme.baodi(dto.getBaodiCost());
				System.out.println("当前保底金额:"+scheme.getBaodiCost());
				System.out.println("4:::"+scheme.getState());
			}
		} catch (DataException e) {
			throw new ServiceException("认购不成功："+e.getMessage(), e);
		}
//		scheme = getSchemeEntityManager().saveScheme(scheme);
		System.out.println("scheme前:::"+scheme.getState());
		scheme = schemeDao.save(scheme);
		System.out.println("scheme后:::"+scheme.getState());
		//增加平台来源。默认是web
		PlatformInfo platformInfo = PlatformInfo.WEB;
		if (isSubscription) {
			Subscription subscription=createSubscription(scheme, user, dto.getSubscriptionCost(), dto.getWay(),platformInfo);
			if(scheme.getSchemePrintState().equals(SchemePrintState.SUCCESS)){
				//写入代理
//				List<Subscription> subscriptionList = getSchemeEntityManager(scheme.getLotteryType()).findSubscriptionsOfBaoDiScheme(scheme.getId(),dto.getUserId(),SubscriptionState.NORMAL);
//				for (Subscription st : subscriptionList) {
					User user_ = this.getUser(subscription.getUserId());
					recordAgent(user_, subscription.getLotteryType(), AgentDetailType.BUY, subscription.getCreateTime(), subscription.getCost(),scheme.getId());
//				}
			}
			System.out.println("createSubscription:::"+scheme.getState());
		}
		if (isBaodi) {
			createBaodi(scheme, user, dto.getBaodiCost(),platformInfo);
		}

		if (scheme.getState() == SchemeState.FULL && scheme.isHasBaodi()) {
			
			List<Baodi> baodiList = findNormalBaodi(scheme.getId());
			if (baodiList != null && !baodiList.isEmpty()) {
				for (Baodi baodi : baodiList) {
					this.cancelBaodi(baodi, scheme, false);// 撤销保底
				}
			}
		}

		return scheme;
	}
	
	/**
	 * 检查是否符合销售期的认购配置
	 * 
	 * @param period
	 *            销售期
	 * @param dto
	 *            认购或保底的数据传输对象
	 */
	protected void checkConformPeriodSubscriptionConfig(S scheme) {
		
//		I period = kenoManager.getKenoPeriod(scheme.getPeriodId());
		I period =issueDataDao.get(scheme.getPeriodId());
		try {
			
			period.checkIsCanSubscriptionOrBaodi();
			PeriodSalesId id = new PeriodSalesId(period.getId(), scheme.getMode());
//			PeriodSales periodSales = periodManager.getPeriodSales(id);
//			periodSales.checkIsCanSubscriptionOrBaodi();
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Baodi> findNormalBaodi(final Long schemeId) {
		final S scheme = getScheme(schemeId);
		return (List<Baodi>) baodiDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("schemeId", schemeId));
				criteria.add(Restrictions.eq("lotteryType", scheme.getLotteryType()));
				criteria.add(Restrictions.eq("state", BaodiState.NORMAL));
				return criteria.list();
			}
		});
	}
	
	protected void cancelBaodi(Baodi baodi, S scheme, boolean checkSpare) {
		try {
			baodi.cancel();
			scheme.cancelBaodi(baodi.getCost(), checkSpare);
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
		baodi = baodiDao.save(baodi);
		scheme = this.saveSchemeReturn(scheme);

		StringBuilder cause = new StringBuilder(50);
		cause.append("撤销对方案[").append(scheme.getSchemeNumber()).append("]编号为[").append(baodi.getId()).append("]的保底记录.");
		userManager.cancelPrepayment(baodi.getPrepaymentId(), FundDetailType.CANCEL_BAODI, cause.toString());
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Baodi getBaodi(Long id) {
		return baodiDao.get(id);
	}
	
	public Subscription baodiTransferSubscription(Long baodiId) {
//		Baodi baodi = getSchemeEntityManager().getBaodi(baodiId);
		Baodi baodi = baodiDao.get(baodiId);
		S scheme = schemeDao.get(baodi.getSchemeId());

		checkConformPeriodSubscriptionConfig(scheme);

		Subscription subscription = baodiTransferSubscription(baodi, scheme);
		scheme = schemeDao.save(scheme);
		return subscription;
	}
	
	protected Subscription baodiTransferSubscription(Baodi baodi, S scheme) {
		BigDecimal subscriptionCost;
		BigDecimal remainBaodiCost;
		try {
			subscriptionCost = scheme.baodiTransferSubscription(baodi.getCost());
			remainBaodiCost = baodi.useBaodi(subscriptionCost);
			if (remainBaodiCost.doubleValue() > 0) {
				scheme.cancelBaodi(remainBaodiCost, false);
			}
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
//		baodi = getSchemeEntityManager().saveBaodi(baodi);
		baodi = baodiDao.save(baodi);
		User user = userManager.getUser(baodi.getUserId());

		StringBuilder sb = new StringBuilder(50);
		sb.append("编号为").append(baodi.getId()).append("的保底转为认购,成功转换的金额为")
				.append(Constant.MONEY_FORMAT.format(subscriptionCost));
		Prepayment prepayment = userManager.transferPrepayment(scheme.getTransactionId(),
				baodi.getPrepaymentId(), subscriptionCost, PrepaymentType.SUBSCRIPTION, FundDetailType.SUBSCRIPTION,
				sb.toString(),baodi.getPlatform(),this.getLottery());

		Subscription subscription = saveSubscription(scheme, prepayment.getId(), user, subscriptionCost,
				SubscriptionWay.TRANSFORM_BAODI,baodi.getPlatform());
		
		//写入代理
			List<Subscription> subscriptionList = getSchemeEntityManager(baodi.getLotteryType()).findSubscriptionsOfBaoDiScheme(scheme.getId(),baodi.getUserId(),SubscriptionState.NORMAL);
			for (Subscription st : subscriptionList) {
				User user_ = this.getUser(st.getUserId());
				recordAgent(user_, st.getLotteryType(), AgentDetailType.BUY, st.getCreateTime(), st.getCost(),scheme.getId());
			}
        	
		
		if (remainBaodiCost.doubleValue() > 0) {
			userManager.cancelPrepayment(baodi.getPrepaymentId(), FundDetailType.CANCEL_BAODI,
					"保底转认购,撤销剩余未使用的保底.");
		}
		
		return subscription;
	}
	public User getUser(Long id) {
		return userDao.get(id);
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
						sendAgentRebate(agentRebateMap,money,schemeId, lottery);
					} catch (DataException e) {
						throw new ServiceException(e.getMessage());
					}
				}
			}else{
				this.saveAgentFundDetail(user, money, agentDetailType, null, null, null, null);
			}
		}
	}
	
	public void sendAgentRebate(LinkedHashMap<User, AgentRelationRebate> agentRebateMap,BigDecimal amount,Long schemeId,Lottery lottery) throws DataException{
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
						this.saveAgentFundDetail(userUp, rebateAmount, AgentDetailType.REBATE, AgentLotteryType.getAgentLotteryType(lottery), rebatetemp, schemeId, lottery);
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
						this.saveAgentFundDetail(userUp, rebateAmount, AgentDetailType.REBATE, AgentLotteryType.getAgentLotteryType(lottery), rebatetemp, schemeId, lottery);
						userEntityManager.oprUserMoney(userUp, rebateAmount.abs(), "用户{"+buy_user.getUserName()+"}消费金额为{"+amount+"},佣金返回用户返点为{"+agentRebateUp.getRebate()+"-"+agentRebateDown.getRebate()+"},返回佣金{"+NumUtils.format(rebateAmount.abs(), 4,4)+"}",  FundMode.IN, FundDetailType.REBATE, null);
						
					}
				}

			}
		}
	}
	
	public AgentFundDetail saveAgentFundDetail(User user,BigDecimal money, AgentDetailType detailType,AgentLotteryType lotteryType,Double rebate,Long schemeId,Lottery lottery){
		if(detailType.equals(AgentDetailType.BUY)){
			XDetachedCriteria criteria=new XDetachedCriteria(AgentFundDetail.class,"a");
			criteria.add(Restrictions.eq("a.schemeId", schemeId));
			criteria.add(Restrictions.eq("a.lottery", lottery));
			criteria.add(Restrictions.eq("a.detailType", detailType));
			criteria.add(Restrictions.eq("a.userId", user.getId()));
			List<AgentFundDetail> agentFundDetailList=this.agentFundDetailDao.findByDetachedCriteria(criteria);
			if(agentFundDetailList!=null&&!agentFundDetailList.isEmpty()){
				AgentFundDetail agentFundDetail=agentFundDetailList.get(0);
				BigDecimal money_=agentFundDetail.getMoney();
				money_=money_.add(money);
				agentFundDetail.setMoney(money_);
				return this.agentFundDetailDao.save(agentFundDetail);
			}else{
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
		}else{
			XDetachedCriteria criteria=new XDetachedCriteria(AgentFundDetail.class,"a");
			criteria.add(Restrictions.eq("a.schemeId", schemeId));
			criteria.add(Restrictions.eq("a.lottery", lottery));
			criteria.add(Restrictions.eq("a.lotteryType", lotteryType));
			criteria.add(Restrictions.eq("a.detailType", detailType));
			criteria.add(Restrictions.eq("a.userId", user.getId()));
			List<AgentFundDetail> agentFundDetailList=this.agentFundDetailDao.findByDetachedCriteria(criteria);
			if(agentFundDetailList!=null||!agentFundDetailList.isEmpty()){
				AgentFundDetail agentFundDetail=agentFundDetailList.get(0);
				BigDecimal money_=agentFundDetail.getMoney();
				money_=money_.add(money);
				agentFundDetail.setMoney(money_);
				return this.agentFundDetailDao.save(agentFundDetail);
			}else{
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
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Account findAccount(final Long userId){
		return this.accountDao.findUniqueBy("userId", userId);
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
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public S getSchemeById(Long id) {
		return this.getScheme(id);
	}
}
