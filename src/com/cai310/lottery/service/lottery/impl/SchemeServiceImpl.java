package com.cai310.lottery.service.lottery.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.event.UserNewestLogSupport;
import com.cai310.lottery.Constant;
import com.cai310.lottery.common.AgentDetailType;
import com.cai310.lottery.common.BaodiState;
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
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.SubscriptionState;
import com.cai310.lottery.common.SubscriptionWay;
import com.cai310.lottery.common.TicketSchemeState;
import com.cai310.lottery.common.TransactionState;
import com.cai310.lottery.common.TransactionType;
import com.cai310.lottery.common.UserWay;
import com.cai310.lottery.common.WinRecordType;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.dao.user.FundDetailDao;
import com.cai310.lottery.dao.user.PrepaymentDao;
import com.cai310.lottery.dao.user.TransactionDao;
import com.cai310.lottery.dto.lottery.SchemeDTO;
import com.cai310.lottery.dto.lottery.SchemeUploadDTO;
import com.cai310.lottery.dto.lottery.SubscribeDTO;
import com.cai310.lottery.entity.lottery.Baodi;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.SchemePasscount;
import com.cai310.lottery.entity.lottery.Subscription;
import com.cai310.lottery.entity.lottery.TradeSuccessScheme;
import com.cai310.lottery.entity.ticket.TicketDatail;
import com.cai310.lottery.entity.ticket.TicketPlatformInfo;
import com.cai310.lottery.entity.ticket.TicketThen;
import com.cai310.lottery.entity.user.BankInfo;
import com.cai310.lottery.entity.user.FundDetail;
import com.cai310.lottery.entity.user.Prepayment;
import com.cai310.lottery.entity.user.Transaction;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserInfo;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.template.VariableString;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.ticket.TicketThenEntityManager;
import com.cai310.lottery.service.user.AgentEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.utils.MessageUtil;
import com.cai310.utils.ReflectionUtils;
import com.google.common.collect.Lists;

@Transactional
public abstract class SchemeServiceImpl<T extends Scheme, E extends SchemeDTO> implements SchemeService<T, E> {
	@Autowired
	protected TransactionDao transactionDao;
	@Autowired
	protected PrepaymentDao prepaymentDao;
	@Autowired
	protected FundDetailDao fundDetailDao;
	@Autowired
	protected TicketThenEntityManager ticketThenEntityManager;
	@Autowired
	protected UserEntityManager userManager;
	@Autowired
	protected PeriodEntityManager periodManager;
	@Autowired
	protected AgentEntityManager agentEntityManager;	
	@Autowired
	protected TradeSuccessSchemeEntityManagerImpl successSchemeManager;

	protected final Class<T> schemeClass;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	public SchemeServiceImpl() {
		schemeClass = ReflectionUtils.getSuperClassGenricType(getClass(), 0);
	}

	/**
	 * @return 方案相关实体管理对象
	 * @see com.cai310.lottery.service.lottery.SchemeEntityManager
	 */
	protected abstract SchemeEntityManager<T> getSchemeEntityManager();

	/**
	 * new一个新方案对象
	 * 
	 * @return 方案对象
	 */
	protected T newSchemeInstance(E schemeDTO) {
		T scheme;
		try {
			scheme = schemeClass.newInstance();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		scheme.setPeriodId(schemeDTO.getPeriodId());
		scheme.setSponsorId(schemeDTO.getSponsorId());
		scheme.setDescription(schemeDTO.getDescription());
		scheme.setUnits(schemeDTO.getUnits());
		scheme.setMultiple(schemeDTO.getMultiple());
		scheme.setSchemeCost(schemeDTO.getSchemeCost());
		scheme.setMode(schemeDTO.getMode());
		scheme.setShareType(schemeDTO.getShareType());
		scheme.setSecretType(schemeDTO.getSecretType());
		scheme.setSubscriptionLicenseType(schemeDTO.getSubscriptionLicenseType());
		scheme.setSubscriptionPassword(schemeDTO.getSubscriptionPassword());
		scheme.setMinSubscriptionCost(schemeDTO.getMinSubscriptionCost());
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
	
	/**
	 * new一个新方案对象
	 * 
	 * @return 方案对象
	 */
	protected SchemePasscount setSchemePasscountInstance(T scheme,SchemePasscount schemePasscount) {
		schemePasscount.setSchemeId(scheme.getId());
		schemePasscount.setMode(scheme.getMode());
		schemePasscount.setMultiple(scheme.getMultiple());
		schemePasscount.setPeriodId(scheme.getPeriodId());
		schemePasscount.setPeriodNumber(scheme.getPeriodNumber());
		schemePasscount.setSchemeCost(scheme.getSchemeCost());
		schemePasscount.setSponsorId(scheme.getSponsorId());
		schemePasscount.setSponsorName(scheme.getSponsorName());
		schemePasscount.setState(scheme.getState());
		schemePasscount.setUnits(scheme.getUnits());
		schemePasscount.setShareType(scheme.getShareType());
		schemePasscount.setSchemePrize(scheme.getPrizeAfterTax());
		return schemePasscount;
	}
	

	public T createScheme(E schemeDTO) {
		Period period = periodManager.getPeriod(schemeDTO.getPeriodId());
		checkConformPeriodInitConfig(period, schemeDTO);///检查方案是否能发起

		User user = userManager.getUser(schemeDTO.getSponsorId());
		checkUser(user, schemeDTO);//检查用户,余额是否能发起方案

		T scheme = newSchemeInstance(schemeDTO);
		scheme.setPeriodNumber(period.getPeriodNumber());
		scheme.setSponsorName(user.getUserName());
		StringBuilder sb = new StringBuilder(50);
		sb.append("【").append(scheme.getLotteryType().getLotteryName()).append("】").append(scheme.getPeriodNumber())
				.append("期").append(scheme.getMode().getModeName()).append(scheme.getShareType().getShareName())
				.append("方案.");
		Transaction tran = userManager.createTransaction(TransactionType.SCHEME, sb.toString());
		scheme.setTransactionId(tran.getId());
		scheme.setSchemePrintState(SchemePrintState.UNPRINT);
		///接票
        if(null!=schemeDTO.getOrderId()){
        	scheme.setOrderId(schemeDTO.getOrderId());
        }
        if(null!=schemeDTO.getOfficialEndTime()){
        	scheme.setCommitTime(schemeDTO.getOfficialEndTime());
        }
        //用户来源
        if(null!=schemeDTO.getPlatform()){
        	scheme.setPlatform(schemeDTO.getPlatform());
        }
		switch (scheme.getShareType()) {
		case TOGETHER:
			scheme = doTogether(scheme, schemeDTO, user);
			break;
		case SELF:
			scheme = doSelf(scheme, schemeDTO, user);
			break;
		default:
			throw new ServiceException("没有该方案分享类型: " + scheme.getShareType());
		}

		return scheme;
	}

	/**
	 * 检查是否符合销售期的发起配置
	 * 
	 * @param period
	 *            销售期
	 * @param schemeDTO
	 *            发起方案的数据传输对象
	 */
	protected void checkConformPeriodInitConfig(Period period, E schemeDTO) {
		try {
			period.checkIsCanInit();
			PeriodSalesId id = new PeriodSalesId(period.getId(), schemeDTO.getMode());
			PeriodSales periodSales = periodManager.getPeriodSales(id);
			periodSales.checkIsCanInit(schemeDTO.getShareType());
			schemeDTO.setOfficialEndTime(periodSales.getSelfEndInitTime());///截止时间取代购截止时间
		} catch (DataException e) {
			if(schemeDTO.getIsTicket()){
				throw new ServiceException("7-"+e.getMessage());
			}else{
				throw new ServiceException(e.getMessage());
			}
		}
	}

	/**
	 * 验证用户是否符合发起方案的相关条件
	 * 
	 * @param user
	 *            用户对象
	 * @param schemeDTO
	 *            发起方案的数据传输对象
	 */
	protected void checkUser(User user, E schemeDTO) {
		if (user.isLocked())
			throw new ServiceException("您的帐户已经被锁定,不能发起方案.如有什么疑问,请联系我们的客服人员.");

		BigDecimal totalCost = BigDecimal.ZERO;
		if (schemeDTO.getSponsorSubscriptionCost() != null && schemeDTO.getSponsorSubscriptionCost().doubleValue() > 0)
			totalCost = totalCost.add(schemeDTO.getSponsorSubscriptionCost());
		if (schemeDTO.getSponsorBaodiCost() != null && schemeDTO.getSponsorBaodiCost().doubleValue() > 0)
			totalCost = totalCost.add(schemeDTO.getSponsorBaodiCost());
		if (user == null) {
			throw new ServiceException("跟单用户[#" + user.getId() + "]不存在.");
		} 
		BigDecimal allAccountBalance = user.getRemainMoney();

		BigDecimal remainMoney = (allAccountBalance != null) ? allAccountBalance : BigDecimal.ZERO;
		if (remainMoney.doubleValue() < totalCost.doubleValue())
			throw new ServiceException("对不起,完成此次交易共需要[" + Constant.MONEY_FORMAT.format(totalCost) + "]元,您的账户余额只有["
					+ Constant.MONEY_FORMAT.format(remainMoney) + "]元,不够支付此次交易.");
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
	protected T doTogether(T scheme, E schemeDTO, User user) {
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
		scheme = getSchemeEntityManager().saveScheme(scheme);// 保存方案
		createSubscription(scheme, user, schemeDTO.getSponsorSubscriptionCost(), SubscriptionWay.INITIATE,schemeDTO.getPlatform());
		if (hasBaodi) {
			createBaodi(scheme, user, schemeDTO.getSponsorBaodiCost(),schemeDTO.getPlatform());
		}
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
	protected T doSelf(T scheme, E schemeDTO, User user) {
		BigDecimal subscriptionCost = BigDecimalUtil.valueOf(scheme.getSchemeCost());
		try {
			scheme.subscription(subscriptionCost);
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		scheme = getSchemeEntityManager().saveScheme(scheme);

		createSubscription(scheme, user, subscriptionCost, SubscriptionWay.INITIATE,schemeDTO.getPlatform());

		return scheme;
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
	protected Subscription createSubscription(T scheme, User user, BigDecimal cost, SubscriptionWay way,PlatformInfo platformInfo) {
		if(null==platformInfo){
			platformInfo = PlatformInfo.WEB;
		}
		StringBuilder sb = new StringBuilder(50);
		sb.append("认购方案[").append(scheme.getSchemeNumber()).append("].");
		Prepayment prepayment = userManager.createPrepayment(scheme.getTransactionId(), user.getId(), cost,
				PrepaymentType.SUBSCRIPTION, FundDetailType.SUBSCRIPTION, sb.toString(),platformInfo,this.getLotteryType());
		return saveSubscription(scheme, prepayment.getId(), user, cost, way,platformInfo);
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
	protected Subscription saveSubscription(T scheme, Long prepaymentId, User user, BigDecimal cost, SubscriptionWay way,PlatformInfo platformInfo) {
		Subscription subscription = new Subscription();
		subscription.setUserId(user.getId());
		subscription.setUserName(user.getUserName());
		subscription.setCost(cost);
		subscription.setState(SubscriptionState.NORMAL);
		subscription.setWay(way);
		subscription.setLotteryType(scheme.getLotteryType());
		subscription.setSchemeId(scheme.getId());
		subscription.setPrepaymentId(prepaymentId);
		subscription.setPlatform(platformInfo);
		return getSchemeEntityManager().saveSubscription(subscription);
	}

	/**
	 * 创建保底记录
	 * 
	 * @param scheme
	 *            保底的方案
	 * @param user
	 *            保底的用户
	 * @param cost
	 *            保底 的金额
	 * @return 保底记录
	 */
	protected Baodi createBaodi(T scheme, User user, BigDecimal cost,PlatformInfo platform) {
		StringBuilder sb = new StringBuilder(50);
		sb.append("保底方案[").append(scheme.getSchemeNumber()).append("].");
		Prepayment prepayment = userManager.createPrepayment(scheme.getTransactionId(), user.getId(), cost,
				PrepaymentType.BAODI, FundDetailType.BAODI, sb.toString(),platform,this.getLotteryType());

		Baodi baodi = new Baodi();
		baodi.setSchemeId(scheme.getId());
		baodi.setPrepaymentId(prepayment.getId());
		baodi.setUserId(user.getId());
		baodi.setUserName(user.getUserName());
		baodi.setCost(cost);
		baodi.setState(BaodiState.NORMAL);
		baodi.setLotteryType(scheme.getLotteryType());
		baodi.setPlatform(platform);
		return getSchemeEntityManager().saveBaodi(baodi);
	}
	
	@Transactional(propagation = Propagation.NESTED)
	public T subscribe(SubscribeDTO dto) {
		T scheme = getSchemeEntityManager().getScheme(dto.getSchemeId());
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
			}
			if (isBaodi) {
				scheme.baodi(dto.getBaodiCost());
			}
		} catch (DataException e) {
			throw new ServiceException("认购不成功："+e.getMessage(), e);
		}
		scheme = getSchemeEntityManager().saveScheme(scheme);

		//增加平台来源。默认是web
		PlatformInfo platformInfo = PlatformInfo.WEB;
		if (isSubscription) {
			createSubscription(scheme, user, dto.getSubscriptionCost(), dto.getWay(),platformInfo);
		}
		if (isBaodi) {
			createBaodi(scheme, user, dto.getBaodiCost(),platformInfo);
		}

		if (scheme.getState() == SchemeState.FULL && scheme.isHasBaodi()) {
			List<Baodi> baodiList = getSchemeEntityManager().findNormalBaodi(scheme.getId());
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
	protected void checkConformPeriodSubscriptionConfig(T scheme) {
		Period period = periodManager.getPeriod(scheme.getPeriodId());
		try {
			period.checkIsCanSubscriptionOrBaodi();
			PeriodSalesId id = new PeriodSalesId(period.getId(), scheme.getMode());
			PeriodSales periodSales = periodManager.getPeriodSales(id);
			periodSales.checkIsCanSubscriptionOrBaodi();
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public void forcePrint(Long schemeId, Long adminUserId) {
		T scheme = getSchemeEntityManager().getScheme(schemeId);
		if (scheme == null)
			throw new ServiceException("方案不存在.");
		scheme.setSendToPrint(true);// 送票
		scheme.setSchemePrintState(SchemePrintState.SUCCESS);// 出票
		scheme = getSchemeEntityManager().saveScheme(scheme);
	}

	public T uploadScheme(SchemeUploadDTO dto) {
		T scheme = getSchemeEntityManager().getScheme(dto.getSchemeId());
		if (scheme == null)
			throw new ServiceException("方案不存在.");
		checkConformPeriodUploadConfig(scheme, dto);

		try {
			scheme.uploadContent(dto.getUploadContent());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}

		return getSchemeEntityManager().saveScheme(scheme);
	}

	/**
	 * 检查是否符合销售期的上传配置
	 * 
	 * @param period
	 *            销售期
	 * @param dto
	 *            上传方案内容的数据传输对象
	 */
	protected void checkConformPeriodUploadConfig(T scheme, SchemeUploadDTO dto) {
		Period period = periodManager.getPeriod(scheme.getPeriodId());
		if (period == null)
			throw new ServiceException("销售期不存在.");
		try {
			period.checkIsCanInit();
			PeriodSalesId id = new PeriodSalesId(period.getId(), scheme.getMode());
			PeriodSales periodSales = periodManager.getPeriodSales(id);
			periodSales.checkIsCanInit(scheme.getShareType());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public Subscription baodiTransferSubscription(Long baodiId) {
		Baodi baodi = getSchemeEntityManager().getBaodi(baodiId);
		T scheme = getSchemeEntityManager().getScheme(baodi.getSchemeId());

		checkConformPeriodSubscriptionConfig(scheme);

		Subscription subscription = baodiTransferSubscription(baodi, scheme);
		scheme = getSchemeEntityManager().saveScheme(scheme);
		return subscription;
	}

	protected Subscription baodiTransferSubscription(Baodi baodi, T scheme) {
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
		baodi = getSchemeEntityManager().saveBaodi(baodi);

		User user = userManager.getUser(baodi.getUserId());

		StringBuilder sb = new StringBuilder(50);
		sb.append("编号为").append(baodi.getId()).append("的保底转为认购,成功转换的金额为")
				.append(Constant.MONEY_FORMAT.format(subscriptionCost));
		Prepayment prepayment = userManager.transferPrepayment(scheme.getTransactionId(),
				baodi.getPrepaymentId(), subscriptionCost, PrepaymentType.SUBSCRIPTION, FundDetailType.SUBSCRIPTION,
				sb.toString(),baodi.getPlatform(),this.getLotteryType());

		Subscription subscription = saveSubscription(scheme, prepayment.getId(), user, subscriptionCost,
				SubscriptionWay.TRANSFORM_BAODI,baodi.getPlatform());

		if (remainBaodiCost.doubleValue() > 0) {
			userManager.cancelPrepayment(baodi.getPrepaymentId(), FundDetailType.CANCEL_BAODI,
					"保底转认购,撤销剩余未使用的保底.");
		}

		return subscription;
	}

	public void cancelSubscription(Long subscriptionId) {
		Subscription subscription = getSchemeEntityManager().getSubscription(subscriptionId);
		if (subscription == null)
			throw new ServiceException("认购记录不存在.");
		T scheme = getSchemeEntityManager().getScheme(subscription.getSchemeId());
		if (scheme == null)
			throw new ServiceException("方案不存在.");

		if (isSaleEnded(scheme))
			throw new ServiceException("方案已截止.");

		try {
			subscription.cancel();
			scheme.cancelSubscription(subscription.getCost());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
		subscription = getSchemeEntityManager().saveSubscription(subscription);
		scheme = getSchemeEntityManager().saveScheme(scheme);

		StringBuilder cause = new StringBuilder(50);
		cause.append("撤销对方案[").append(scheme.getSchemeNumber()).append("]编号为[").append(subscription.getId())
				.append("]的认购记录.");
		userManager.cancelPrepayment(subscription.getPrepaymentId(), FundDetailType.CANCEL_SUBSCRIPTION,
				cause.toString());
	}

	public void cancelBaodi(Long baodiId) {
		Baodi baodi = getSchemeEntityManager().getBaodi(baodiId);
		if (baodi == null)
			throw new ServiceException("保底记录不存在.");
		T scheme = getSchemeEntityManager().getScheme(baodi.getSchemeId());
		if (scheme == null)
			throw new ServiceException("方案不存在.");

		if (isSaleEnded(scheme))
			throw new ServiceException("方案已截止.");

		this.cancelBaodi(baodi, scheme, true);
	}

	protected void cancelBaodi(Baodi baodi, T scheme, boolean checkSpare) {
		try {
			baodi.cancel();
			scheme.cancelBaodi(baodi.getCost(), checkSpare);
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
		baodi = getSchemeEntityManager().saveBaodi(baodi);
		scheme = getSchemeEntityManager().saveScheme(scheme);

		StringBuilder cause = new StringBuilder(50);
		cause.append("撤销对方案[").append(scheme.getSchemeNumber()).append("]编号为[").append(baodi.getId()).append("]的保底记录.");
		userManager.cancelPrepayment(baodi.getPrepaymentId(), FundDetailType.CANCEL_BAODI, cause.toString());
	}

	public boolean isSaleEnded(Long schemeId) {
		T scheme = getSchemeEntityManager().getScheme(schemeId);
		return isSaleEnded(scheme);
	}

	protected boolean isSaleEnded(T scheme) {
		Period period = periodManager.getPeriod(scheme.getPeriodId());
		if (period.isOnSale()) {
			PeriodSalesId id = new PeriodSalesId(period.getId(), scheme.getMode());
			PeriodSales periodSales = periodManager.getPeriodSales(id);
			try {
				periodSales.checkIsCanCancelScheme();
			} catch (DataException e) {
				return true;
			}
			return false;
		} else {
			return true;
		}
	}

	public void cancelSchemeBySponsor(Long schemeId) {
		T scheme = getSchemeEntityManager().getScheme(schemeId);
		if (scheme == null)
			throw new ServiceException("方案不存在.");

		if (isSaleEnded(scheme))
			throw new ServiceException("方案已截止.");

		try {
			scheme.cancel(false);
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}

		scheme = getSchemeEntityManager().saveScheme(scheme);

		StringBuilder cause = new StringBuilder(50);
		cause.append("方案发起人撤销方案[").append(scheme.getSchemeNumber()).append("].");
		userManager.cancelTransaction(scheme.getTransactionId(), FundDetailType.CANCEL_SCHEME,
				cause.toString());
	}

	public void cancelSchemeByAdmin(Long schemeId, Long adminUserId) {
		T scheme = getSchemeEntityManager().getScheme(schemeId);
		if (scheme == null)
			throw new ServiceException("方案不存在.");

		try {
			scheme.cancel(true);
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
		scheme = getSchemeEntityManager().saveScheme(scheme);

		StringBuilder cause = new StringBuilder(50);
		cause.append(adminUserId == null ? "系统" : "管理员").append("撤销方案[").append(scheme.getSchemeNumber()).append("].");
		userManager.cancelTransaction(scheme.getTransactionId(), FundDetailType.CANCEL_SCHEME,
				cause.toString());
		//发送短信通知用户撤销方案
		ticketThenEntityManager.synchronizationFailTicket(scheme);
		sendMsgForCancelScheme(scheme);
	}

	public void forceCancelScheme(Long schemeId, Long adminUserId) {
		T scheme = getSchemeEntityManager().getScheme(schemeId);
		if (scheme == null)
			throw new ServiceException("方案不存在.");

		try {
			scheme.forceCancel();
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
		scheme = getSchemeEntityManager().saveScheme(scheme);

		StringBuilder cause = new StringBuilder(50);
		cause.append(adminUserId == null ? "系统" : "管理员").append("撤销方案[").append(scheme.getSchemeNumber()).append("].");
		userManager.cancelTransaction(scheme.getTransactionId(), FundDetailType.CANCEL_SCHEME,
				cause.toString());
		ticketThenEntityManager.synchronizationFailTicket(scheme);
		if (scheme.isSendToPrint()) {
			 //发送短信通知用户撤销方案
			sendMsgForCancelScheme(scheme);
		}
	}
	
	private void sendMsgForCancelScheme(T scheme){
		try {
			
			User user = userManager.getUserBy(scheme.getSponsorName());
			String mobile = user.getInfo().getMobile();			
			if(StringUtils.isNotBlank(mobile)){
				List<String> num = Lists.newArrayList();
				num.add(user.getInfo().getMobile());
				String content = "尊敬的" + user.getUserName() + ",你的方案号为[" + scheme.getSchemeNumber() + "]的方案已经撤单！，请您留意，祝您中大奖.";			
				MessageUtil.sendMessage(num, content);
			}
		}catch(Exception e){
			//
			
		}
	}
	
	public void refundment(Long schemeId) {
		T scheme = getSchemeEntityManager().getScheme(schemeId);
		if (scheme == null)
			throw new ServiceException("方案不存在.");

		try {
			scheme.refundment();
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
		scheme = getSchemeEntityManager().saveScheme(scheme);

		StringBuilder cause = new StringBuilder(50);
		cause.append("方案[").append(scheme.getSchemeNumber()).append("]退款.");
		userManager.refundmentTransaction(scheme.getTransactionId(), FundDetailType.REFUNDMENT_SCHEME,
				cause.toString());
		ticketThenEntityManager.synchronizationFailTicket(scheme);
		try {
			Long sponsorId = scheme.getSponsorId();
			User user = userManager.getUser(sponsorId);
			String mobile = user.getInfo().getMobile();			
			if(StringUtils.isNotBlank(mobile)){
				List<String> num = Lists.newArrayList();
				num.add(user.getInfo().getMobile());
				String content = "尊敬的" + user.getUserName() + ",你的方案号为[" + scheme.getSchemeNumber() + "]的方案已经退款！，请您留意，祝您中大奖.";			
				MessageUtil.sendMessage(num, content);
			}
		}catch(Exception e){
			//			
		}
	}

	// 将保底与完成交易合在了一起
	public void commitOrCancelTransaction(Long schemeId) {
		T scheme = getSchemeEntityManager().getScheme(schemeId);
		if (scheme == null)
			throw new ServiceException("方案不存在.");

		// cyy-c 2011-04-20 在完成交易的时候判断出票是否成功。不成功的都要做撤单处理
		//if (scheme.isSendToPrint() && (SchemePrintState.PRINT.equals(scheme.getSchemePrintState())||SchemePrintState.SUCCESS.equals(scheme.getSchemePrintState()))) {// 已出票，使用保底
		if (scheme.isSendToPrint() && SchemePrintState.SUCCESS.equals(scheme.getSchemePrintState())) {// 已出票，使用保底
			if (scheme.isHasBaodi()) {// 用户保底...
				List<Baodi> baodiList = getSchemeEntityManager().findNormalBaodi(scheme.getId());
				if (baodiList != null && !baodiList.isEmpty()) {
					for (Baodi baodi : baodiList) {
						if (scheme.canUseBaodi()) {
							this.baodiTransferSubscription(baodi, scheme);// 使用保底
						} else {
							this.cancelBaodi(baodi, scheme, false);// 撤销保底
						}
					}
				} else {
					throw new ServiceException("找不到可用保底！");
				}
			}
			if (scheme.isCanSubscribe()) {// 网站保底...
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
				scheme = getSchemeEntityManager().saveScheme(scheme);// 更新方案对象
				// 执行操作：完成交易
				userManager.commitTransaction(scheme.getTransactionId());
			} else {
				throw new ServiceException("数据异常！");
			}
		} else {
			if(SchemePrintState.FAILED.equals(scheme.getSchemePrintState())){
				// 出票失败
				this.forceCancelScheme(schemeId, null);
			}
		}

	}

	public void delivePrize(long schemeId) {
		T scheme = getSchemeEntityManager().getScheme(schemeId);
		if (scheme == null)
			throw new ServiceException("方案不存在.");
		if (scheme.getState() != SchemeState.SUCCESS)
			throw new ServiceException("该方案[#" + schemeId + "]不是成功方案，不能派放奖金！");
		if (!scheme.isWon())
			throw new ServiceException("该方案[#" + schemeId + "]没有中奖，不能派放奖金！");
		if (scheme.isPrizeSended())
			throw new ServiceException("该方案[#" + schemeId + "]的奖金已经派放，不能再派放！");
		if (scheme.getPrize() == null || scheme.getPrizeAfterTax() == null) {
			throw new ServiceException("该方案[#" + schemeId + "]的税后总奖金或去除发起人提成后的奖金为空值！");
		} else if (scheme.getPrize().doubleValue() <= 0.0 || scheme.getPrizeAfterTax().doubleValue() <= 0.0) {
			throw new ServiceException("该方案[#" + schemeId + "]的税后总奖金或去除发起人提成后的奖金少于或等于0.0！");
		}

		scheme.setPrizeSended(true);// 标识方案已派奖
		scheme.setPrizeSendTime(new Date());// 设置方案派奖时间
		scheme = getSchemeEntityManager().saveScheme(scheme);// 更新方案对象

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
		List<Subscription> subscriptionList = getSchemeEntityManager().findSubscriptionsOfScheme(scheme.getId(),
				SubscriptionState.NORMAL);
		BigDecimal actualTotalReturn = BigDecimal.ZERO;// 计算实际分配的总金额
		BigDecimal joinPrize;
		BigDecimal schemeCost = BigDecimalUtil.valueOf(scheme.getSchemeCost());
		for (Subscription subscription : subscriptionList) {
			joinPrize = BigDecimalUtil.divide(
					BigDecimalUtil.multiply(subscription.getCost(), prizeSendType.getTotalReturn()), schemeCost);
			subscription.setBonusSended(true);
			subscription.setBonus(joinPrize);// 更新加入对象的奖金信息
			subscription = getSchemeEntityManager().saveSubscription(subscription);// 更新加入对象

			varReturnMemo.setVar("prize", Constant.numFmt.format(joinPrize));// 合买分配的奖金信息

			// 执行操作：为用户添中资金
			userManager.oprUserMoney(userManager.getUser(subscription.getUserId()), joinPrize,
					varReturnMemo.toString(), FundMode.IN, FundDetailType.SCHEME_BONUS, null);
			///增加派奖统计
			agentEntityManager.recordAgent(userManager.getUser(subscription.getUserId()), scheme.getLotteryType(), AgentDetailType.PRIZE,new Date(), joinPrize,null);
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
			}
		}

		if (prizeSendType.getOrganigerDeduct().doubleValue() > 0) {// 方案发起人提成
			userManager.oprUserMoney(userManager.getUser(scheme.getSponsorId()),
					prizeSendType.getOrganigerDeduct(), "方案号[" + scheme.getSchemeNumber() + "]中奖奖金提成", FundMode.IN,
					FundDetailType.SCHEME_COMMISSION, null);
		}
		//出票同步
		ticketThenEntityManager.synchronizationWonTicket(scheme);
		UserNewestLogSupport.won(scheme);		
	}
	public void deliveWinRecord(long schemeId) {
		T scheme = getSchemeEntityManager().getScheme(schemeId);
		if (scheme == null)
			throw new ServiceException("方案不存在.");
		if (scheme.getState() != SchemeState.SUCCESS&&scheme.getState() != SchemeState.CANCEL)
			throw new ServiceException("该方案[#" + schemeId + "]没有终止，不能派放战绩！");
		if (!scheme.isWon())
			throw new ServiceException("该方案[#" + schemeId + "]没有中奖，不能派放战绩！");
		if (!scheme.isPrizeSended())
			throw new ServiceException("该方案[#" + schemeId + "]没有派奖，不能派放战绩！");
	
		if (scheme.getPrize() == null || scheme.getPrizeAfterTax() == null) {
			throw new ServiceException("该方案[#" + schemeId + "]的税后总奖金或去除发起人提成后的奖金为空值！");
		} else if (scheme.getPrize().doubleValue() <= 0.0 || scheme.getPrizeAfterTax().doubleValue() <= 0.0) {
			throw new ServiceException("该方案[#" + schemeId + "]的税后总奖金或去除发起人提成后的奖金少于或等于0.0！");
		}
		if (scheme.isWon()) {
			// 增加战绩
			
		} else {
			
		}
	}

	public void runBaodi(Long schemeId) {

	}

	public WinRecordType getWinRecord(T  Scheme){
		BigDecimal prizeAfterTax = Scheme.getPrizeAfterTax();
		Integer schemeCost = Scheme.getSchemeCost();
		if (Scheme.getState() == SchemeState.SUCCESS) {
			if (Scheme.getPrizeAfterTax().doubleValue() >= Constant.FiftyMillion.doubleValue()) {
				return WinRecordType.TUANZHANG;
			}
			if (prizeAfterTax.doubleValue() >= Constant.FiveMillion.doubleValue()) {
				return WinRecordType.YINGZHANG;
			}
			if (prizeAfterTax.doubleValue() >= Constant.OneMillion.doubleValue()) {
				return WinRecordType.LIANZHANG;
			}
			if ((prizeAfterTax.doubleValue() >= Constant.OneThousands.doubleValue())
					|| (prizeAfterTax.doubleValue() >= Constant.FiveHundreds.doubleValue() && (prizeAfterTax
							.doubleValue() / schemeCost) >= 10)) {
				return WinRecordType.PAIZHANG;
			}
		} else {
			// 流产
			if ((prizeAfterTax.doubleValue() >= Constant.TwoThousands.doubleValue())
					|| (prizeAfterTax.doubleValue() >= Constant.OneThousands.doubleValue() && (prizeAfterTax
							.doubleValue() / schemeCost) >= 20)) {
				return WinRecordType.PAIZHANG;
			}
		}
		return null;
	}
	
	public abstract Lottery getLotteryType();
	
	
	public void saveTradeSuccessScheme(T scheme){
		Long successSchemeId = null;
		TradeSuccessScheme successScheme = successSchemeManager.getScheme(getLotteryType(), scheme.getId());
		if(successScheme == null){
			successScheme = new TradeSuccessScheme();
		}else{
			successSchemeId = successScheme.getId();
		}
		successScheme.setLotteryType(getLotteryType());
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
	
	public synchronized boolean isRepeatOrder(String orderId,Long userId){
		return getSchemeEntityManager().isRepeatOrder(orderId,userId);
	}
	/**
	 * 验证用户是否符合发起方案的相关条件
	 * 
	 * @param user
	 *            用户对象
	 * @param schemeDTO
	 *            发起方案的数据传输对象
	 */
	protected void checkTicketPlatformInfo(TicketPlatformInfo ticketPlatformInfo, E schemeDTO) {
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
	public T createTicketScheme(E schemeDTO) {
		Period period = periodManager.getPeriod(schemeDTO.getPeriodId());
		checkConformPeriodInitConfig(period, schemeDTO);// /检查方案是否能发起

		TicketPlatformInfo ticketPlatformInfo = ticketThenEntityManager.getTicketPlatformInfo(schemeDTO.getSponsorId());
		checkTicketPlatformInfo(ticketPlatformInfo, schemeDTO);// 检查用户,余额是否能发起方案

		T scheme = newSchemeInstance(schemeDTO);
		scheme.setPeriodNumber(period.getPeriodNumber());
		StringBuilder sb = new StringBuilder(50);
		sb.append("【").append(scheme.getLotteryType().getLotteryName()).append(
				"】").append(scheme.getPeriodNumber()).append("期").append(
				scheme.getMode().getModeName()).append(
				scheme.getShareType().getShareName()).append("方案.");
		Transaction tran = userManager.createTransaction(
				TransactionType.SCHEME, sb.toString());
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
		switch (scheme.getShareType()) {
		case TOGETHER:
			throw new ServiceException("5-没有该分享类型: " + scheme.getShareType());
		case SELF:
			scheme = doTicket(scheme, schemeDTO, ticketPlatformInfo);
			break;
		default:
			throw new ServiceException("5-没有该方案分享类型: " + scheme.getShareType());
		}
      
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
	protected T doTicket(T scheme, E schemeDTO, TicketPlatformInfo ticketPlatformInfo) {
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
		scheme = getSchemeEntityManager().saveScheme(scheme);
		
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
		fundDetail.setResultMoney(ticketPlatformInfo.getRemainMoney().add(user.getRemainMoney()));
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
		subscription = getSchemeEntityManager().saveSubscription(
				subscription);
		  ///写入接票表
		TicketThen ticketThen = new TicketThen();
		ticketThen.setOutOrderNumber(schemeDTO.getOutOrderNumber());
				ticketThen.setOrderId(scheme.getOrderId());
				ticketThen.setOfficialEndTime(scheme.getCommitTime());
				ticketThen.setPlatformInfoId(ticketPlatformInfo.getId());
				ticketThen.setUserId(user.getId());
				ticketThen.setLotteryType(this.getLotteryType());
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
    public Byte getPlayType(E schemeDTO){
			return Byte.valueOf("0");
	}
    //继承重写 
    public void ticketOther(Scheme scheme){
			
	}
	/**
	 * 接票
	 * 
	 * @param orderId 订单编号
	 * @return 方案
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public T findSchemeByOrderId(String orderId){
		return getSchemeEntityManager().findSchemeByOrderId(orderId);
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<T> findSchemeByOrderId(List<String> orderId){
		return getSchemeEntityManager().findSchemeByOrderId(orderId);
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<T> findWonSchemeBySponsorId(Long sponsorId,Date startDate,Date endDate){
		return getSchemeEntityManager().findWonSchemeBySponsorId( sponsorId, startDate, endDate);
	}
 
}
