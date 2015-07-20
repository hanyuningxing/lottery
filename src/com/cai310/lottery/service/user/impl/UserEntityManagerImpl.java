package com.cai310.lottery.service.user.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.AgentDetailType;
import com.cai310.lottery.common.DrawingOrderState;
import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.FundMode;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LuckDetailType;
import com.cai310.lottery.common.MessageType;
import com.cai310.lottery.common.PlatformInfo;
import com.cai310.lottery.common.PrepaymentState;
import com.cai310.lottery.common.PrepaymentType;
import com.cai310.lottery.common.ScoreDetailType;
import com.cai310.lottery.common.TransactionState;
import com.cai310.lottery.common.TransactionType;
import com.cai310.lottery.common.UserWay;
import com.cai310.lottery.dao.lottery.AutoFollowOrderDao;
import com.cai310.lottery.dao.ticket.TicketPlatformInfoDao;
import com.cai310.lottery.dao.user.BankInfoDao;
import com.cai310.lottery.dao.user.DrawingOrderDao;
import com.cai310.lottery.dao.user.FundDetailDao;
import com.cai310.lottery.dao.user.IpsorderDao;
import com.cai310.lottery.dao.user.LuckDetailDao;
import com.cai310.lottery.dao.user.MediaDao;
import com.cai310.lottery.dao.user.PhonePopuDao;
import com.cai310.lottery.dao.user.PopuDao;
import com.cai310.lottery.dao.user.PrepaymentDao;
import com.cai310.lottery.dao.user.RandomMessageDao;
import com.cai310.lottery.dao.user.ScoreDetailDao;
import com.cai310.lottery.dao.user.TransactionDao;
import com.cai310.lottery.dao.user.UserDao;
import com.cai310.lottery.dao.user.UserGradeDao;
import com.cai310.lottery.dao.user.UserInfoDao;
import com.cai310.lottery.dao.user.UserLoginDao;
import com.cai310.lottery.dao.user.UserReportDao;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.ticket.TicketPlatformInfo;
import com.cai310.lottery.entity.user.BankInfo;
import com.cai310.lottery.entity.user.DrawingOrder;
import com.cai310.lottery.entity.user.FundDetail;
import com.cai310.lottery.entity.user.Ipsorder;
import com.cai310.lottery.entity.user.LuckDetail;
import com.cai310.lottery.entity.user.Media;
import com.cai310.lottery.entity.user.PhonePopu;
import com.cai310.lottery.entity.user.Popu;
import com.cai310.lottery.entity.user.Prepayment;
import com.cai310.lottery.entity.user.RandomMessage;
import com.cai310.lottery.entity.user.ScoreDetail;
import com.cai310.lottery.entity.user.Transaction;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserGrade;
import com.cai310.lottery.entity.user.UserInfo;
import com.cai310.lottery.entity.user.UserLogin;
import com.cai310.lottery.entity.user.UserReport;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.EventLogManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.ticket.TicketThenEntityManager;
import com.cai310.lottery.service.user.AgentEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.support.user.GradeMedal;
import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.lottery.web.controller.user.BankInfoForm;
import com.cai310.orm.Page;
import com.cai310.orm.PropertyFilter;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;
import com.cai310.utils.JsonUtil;
import com.cai310.utils.MD5;
import com.cai310.utils.MessageUtil;
import com.cai310.utils.MessageUtil.SendState;
import com.cai310.utils.RandomUtils;
import com.google.common.collect.Lists;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 用户相关实体的管理实现类, 包括用户,交易,预付款,资金明细等.
 * 
 */
@Service("userEntityManager")
@Transactional
public class UserEntityManagerImpl implements UserEntityManager {
	@Autowired
	protected LuckDetailDao luckDetailDao;
	@Autowired
	protected RandomMessageDao randomMessageDao;
	//@Autowired
	//protected LuckDetailDao luckDetailDao;
	@Autowired
	protected PhonePopuDao phonePopuDao;
	@Autowired
	protected PopuDao popuDao;
	@Autowired
	protected UserDao userDao;
	@Autowired
	protected TicketThenEntityManager  ticketThenEntityManager;
	@Autowired
	protected TicketPlatformInfoDao  ticketPlatformInfoDao;
	@Autowired
	protected UserInfoDao userInfoDao;
	@Autowired
	protected UserGradeDao userGradeDao;
	@Autowired
	protected BankInfoDao bankInfoDao;
	@Autowired
	protected UserLoginDao userLoginDao;
	@Autowired
	protected TransactionDao transactionDao;
	@Autowired
	protected ScoreDetailDao scoreDetailDao;
	@Autowired
	protected PrepaymentDao prepaymentDao;
	@Autowired
	protected FundDetailDao fundDetailDao;
	@Autowired
	protected DrawingOrderDao drawingOrderDao;
	@Autowired
	protected AutoFollowOrderDao autorderDao;
	@Autowired
	protected IpsorderDao ipsorderDao;	
	@Autowired
	protected MediaDao mediaDao;	
	@Autowired
	protected UserReportDao userReportDao;	
	@Resource
	protected EventLogManager eventLogManager;	
	@Resource
	private JavaMailSender mailSender;
	@Resource
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Autowired
	AgentEntityManager agentEntityManager;
	
	
	public UserReport saveUserReport(UserReport userReport) { 
		return userReportDao.save(userReport);
	}
	
	
	public Transaction createTransaction(TransactionType type, String remark) {
		Transaction tran = new Transaction();
		tran.setState(TransactionState.UNDER_WAY);
		tran.setType(type);
		tran.setRemark(remark);
		return transactionDao.save(tran);
	}

	
	public Prepayment createPrepayment(Long transactionId, Long userId, BigDecimal amount,
			PrepaymentType prepaymentType, FundDetailType fundDetailType, String remark,PlatformInfo platformInfo,Lottery lottery) {
		// 扣除金额
		User user = userDao.get(userId);
		try {
			user.subtractMoney(amount);
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
		user = userDao.save(user);

		return createPrepayment(transactionId, user, amount, prepaymentType, fundDetailType, FundMode.OUT, remark, platformInfo,lottery);
	}
	
	
	public Prepayment createKenoPrepayment(Long transactionId, Long userId, BigDecimal amount,
			PrepaymentType prepaymentType, FundDetailType fundDetailType, String remark,PlatformInfo platformInfo,Lottery lottery ) {
		// 扣除金额
		User user = userDao.get(userId);
		try {
			user.subtractMoney(amount);///缩少金额
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
		user = userDao.save(user);

		Transaction tran = transactionDao.get(transactionId);
		if (tran.getState() != TransactionState.UNDER_WAY)
			throw new ServiceException("交易记录当前状态为[" + tran.getState().getStateName() + "],不允许添加预付款.");

		// 创建资金明细
		FundDetail fundDetail = new FundDetail();
		fundDetail.setMode(FundMode.OUT);
		fundDetail.setType(fundDetailType);
		fundDetail.setMoney(amount);
		fundDetail.setRemark(remark);
		fundDetail.setResultMoney(user.getRemainMoney());
		fundDetail.setUserId(user.getId());
		fundDetail.setUserName(user.getUserName());
		fundDetail.setPlatform(platformInfo);
		fundDetail = fundDetailDao.save(fundDetail);

		// 创建预付款记录
		Prepayment prepayment = new Prepayment();
		prepayment.setType(prepaymentType);
		prepayment.setState(PrepaymentState.AWAIT);
		prepayment.setTransactionId(tran.getId());
		prepayment.setUserId(user.getId());
		prepayment.setAmount(amount);
		prepayment.setCreateFundDetailId(fundDetail.getId());
		prepayment.setLotteryType(lottery);
		prepayment = prepaymentDao.save(prepayment);

		return prepayment;
	}
	
	
	/**
	 * 创建预付款
	 * 
	 * @param transactionId 交易ID
	 * @param user 用户
	 * @param amount 预付款金额
	 * @param prepaymentType 预付款类型
	 * @param fundDetailType 资金明细类型
	 * @param fundMode 资金的进出类型
	 * @param remark 备注
	 * @return 创建的预付款
	 */
	protected Prepayment createKenoPrepayment(Long transactionId, User user, BigDecimal amount,
			PrepaymentType prepaymentType, FundDetailType fundDetailType, FundMode fundMode, String remark,PlatformInfo platformInfo,Lottery lottery) {
		Transaction tran = transactionDao.get(transactionId);
		if (tran.getState() != TransactionState.UNDER_WAY)
			throw new ServiceException("交易记录当前状态为[" + tran.getState().getStateName() + "],不允许添加预付款.");

		// 创建资金明细
				FundDetail fundDetail = new FundDetail();
				fundDetail.setMode(FundMode.OUT);
				fundDetail.setType(fundDetailType);
				fundDetail.setMoney(amount);
				fundDetail.setRemark(remark);
				fundDetail.setResultMoney(user.getRemainMoney());
				fundDetail.setUserId(user.getId());
				fundDetail.setUserName(user.getUserName());
				fundDetail.setPlatform(platformInfo);
				fundDetail = fundDetailDao.save(fundDetail);

				// 创建预付款记录
				Prepayment prepayment = new Prepayment();
				prepayment.setType(prepaymentType);
				prepayment.setState(PrepaymentState.AWAIT);
				prepayment.setTransactionId(tran.getId());
				prepayment.setUserId(user.getId());
				prepayment.setAmount(amount);
				prepayment.setCreateFundDetailId(fundDetail.getId());
				prepayment.setLotteryType(lottery);
				prepayment = prepaymentDao.save(prepayment);

		return prepayment;
	}
	
	
	/**
	 * 创建预付款
	 * 
	 * @param transactionId 交易ID
	 * @param user 用户
	 * @param amount 预付款金额
	 * @param prepaymentType 预付款类型
	 * @param fundDetailType 资金明细类型
	 * @param fundMode 资金的进出类型
	 * @param remark 备注
	 * @return 创建的预付款
	 */
	protected Prepayment createPrepayment(Long transactionId, User user, BigDecimal amount,
			PrepaymentType prepaymentType, FundDetailType fundDetailType, FundMode fundMode, String remark,PlatformInfo platformInfo,Lottery lottery) {
		Transaction tran = transactionDao.get(transactionId);
		if (tran.getState() != TransactionState.UNDER_WAY)
			throw new ServiceException("交易记录当前状态为[" + tran.getState().getStateName() + "],不允许添加预付款.");

		// 创建资金明细
		FundDetail fundDetail = new FundDetail();
		fundDetail.setMode(fundMode);
		fundDetail.setType(fundDetailType);
		fundDetail.setMoney(amount);
		fundDetail.setRemark(remark);
		fundDetail.setResultMoney(user.getRemainMoney());
		fundDetail.setUserId(user.getId());
		fundDetail.setUserName(user.getUserName());
		fundDetail.setPlatform(platformInfo);
		fundDetail = fundDetailDao.save(fundDetail);

		// 创建预付款记录
		Prepayment prepayment = new Prepayment();
		prepayment.setType(prepaymentType);
		prepayment.setState(PrepaymentState.AWAIT);
		prepayment.setTransactionId(tran.getId());
		prepayment.setUserId(user.getId());
		prepayment.setAmount(amount);
		prepayment.setCreateFundDetailId(fundDetail.getId());
		prepayment.setLotteryType(lottery);
		prepayment = prepaymentDao.save(prepayment);

		return prepayment;
	}

	
	@SuppressWarnings("unchecked")
	public void cancelTransaction(Long transactionId, FundDetailType fundDetailType, String cause) {
		Transaction tran = transactionDao.get(transactionId);
		if (tran.getState() == TransactionState.CANCEL)
			throw new ServiceException("交易已经取消,不能再执行取消操作.");
		else if (tran.getState() != TransactionState.UNDER_WAY)
			throw new ServiceException("交易记录当前状态为[" + tran.getState().getStateName() + "],不允许执行取消操作.");

		// 以后改进：把交易状态改为“取消中”,预付款通过异步的方式进行撤销,全部撤销完再把交易状态改为“取消”

		tran.setState(TransactionState.CANCEL);
		tran.setCancelCause(cause);
		tran = transactionDao.save(tran);

		// 撤销所有预付款
		DetachedCriteria criteria = DetachedCriteria.forClass(Prepayment.class);
		criteria.setProjection(Projections.property("id"));
		criteria.add(Restrictions.eq("transactionId", tran.getId()));
		criteria.add(Restrictions.eq("state", PrepaymentState.AWAIT));
		List<Long> list = prepaymentDao.findByDetachedCriteria(criteria);
		for (Long prepaymentId : list) {
			cancelPrepayment(prepaymentId, fundDetailType, cause);
		}
	}

	
	public void cancelPrepayment(Long prepaymentId, FundDetailType fundDetailType, String cause) {
		Prepayment prepayment = prepaymentDao.get(prepaymentId);
		if (prepayment.getState() == PrepaymentState.CANCEL)
			throw new ServiceException("预付款已经取消,不能再执行取消操作.");
		else if (prepayment.getState() != PrepaymentState.AWAIT)
			throw new ServiceException("预付款当前状态为[" + prepayment.getState().getStateName() + "],不允许执行取消操作.");

		User user = userDao.get(prepayment.getUserId());

		StringBuilder remark = new StringBuilder(80);
		remark.append(cause).append("返还编号为").append(prepayment.getId()).append("的").append(
				prepayment.getType().getTypeName()).append("预付款.");
		FundDetail fundDetail = this.oprUserMoney(user, prepayment.getAmount(), remark.toString(), FundMode.IN, fundDetailType, null);
		prepayment.setState(PrepaymentState.CANCEL);
		prepayment.setCancelFundDetailId(fundDetail.getId());
		prepayment = prepaymentDao.save(prepayment);
	}
	
	
	@SuppressWarnings("unchecked")
	public void refundmentTransaction(Long transactionId, FundDetailType fundDetailType, String cause) {
		Transaction tran = transactionDao.get(transactionId);
		if (tran.getState() != TransactionState.SUCCESS)
			throw new ServiceException("交易未成功,不能执行退款操作.");

		tran.setState(TransactionState.REFUNDMENT);
		tran.setCancelCause(cause);
		tran = transactionDao.save(tran);

		// 对所有成功预付款执行退款操作
		DetachedCriteria criteria = DetachedCriteria.forClass(Prepayment.class);
		criteria.setProjection(Projections.property("id"));
		criteria.add(Restrictions.eq("transactionId", tran.getId()));
		criteria.add(Restrictions.eq("state", PrepaymentState.SUCCESS));
		List<Long> list = prepaymentDao.findByDetachedCriteria(criteria);
		for (Long prepaymentId : list) {
			refundmentPrepayment(prepaymentId, fundDetailType, cause);
		}
	}

	
	public void refundmentPrepayment(Long prepaymentId, FundDetailType fundDetailType, String cause) {
		Prepayment prepayment = prepaymentDao.get(prepaymentId);
		if (prepayment.getState() != PrepaymentState.SUCCESS)
			throw new ServiceException("预付款未成功,不能执行退款操作.");

		User user = userDao.get(prepayment.getUserId());
		try {
			user.addMoney(prepayment.getAmount());
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
		user = userDao.save(user);

		StringBuilder remark = new StringBuilder(80);
		remark.append(cause).append("返还编号为").append(prepayment.getId()).append("的").append(
				prepayment.getType().getTypeName()).append("预付款.");
		FundDetail fundDetail = new FundDetail();
		fundDetail.setMode(FundMode.IN);
		fundDetail.setType(fundDetailType);
		fundDetail.setMoney(prepayment.getAmount());
		fundDetail.setRemark(remark.toString());
		fundDetail.setResultMoney(user.getRemainMoney());
		fundDetail.setUserId(user.getId());
		fundDetail.setUserName(user.getUserName());
		fundDetail = fundDetailDao.save(fundDetail);

		prepayment.setState(PrepaymentState.CANCEL);
		prepayment.setCancelFundDetailId(fundDetail.getId());
		prepayment = prepaymentDao.save(prepayment);
	}

	
	public Prepayment transferPrepayment(Long transactionId, Long fromId, BigDecimal transferCost,
			PrepaymentType prepaymentType, FundDetailType fundDetailType, String remark,PlatformInfo platformInfo,Lottery lottery) {
		Prepayment fromPrepayment = prepaymentDao.get(fromId);
		try {
			fromPrepayment.transfer(transferCost);
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
		fromPrepayment = prepaymentDao.save(fromPrepayment);

		User user = userDao.get(fromPrepayment.getUserId());
		return createPrepayment(transactionId, user, transferCost, prepaymentType, fundDetailType, FundMode.NONE,
				remark,platformInfo,lottery);
	}
	
	
	public Prepayment transferKenoPrepayment(Long transactionId, Long fromId, BigDecimal transferCost,
			PrepaymentType prepaymentType, FundDetailType fundDetailType, String remark,PlatformInfo platformInfo,Lottery lottery) {
		Prepayment fromPrepayment = prepaymentDao.get(fromId);
		try {
			fromPrepayment.transfer(transferCost);
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
		fromPrepayment = prepaymentDao.save(fromPrepayment);

		User user = userDao.get(fromPrepayment.getUserId());
		return createKenoPrepayment(transactionId, user, transferCost, prepaymentType, fundDetailType, FundMode.NONE,
				remark,platformInfo,lottery);
	}
	
	
	@SuppressWarnings("unchecked")
	public void commitTransaction(Long transactionId) {
		Transaction tran = transactionDao.get(transactionId);
		if (tran.getState() != TransactionState.UNDER_WAY)
			throw new ServiceException("不能完成该交易.方案号码：：：：："+transactionId);

		DetachedCriteria criteria = DetachedCriteria.forClass(Prepayment.class);
		criteria.add(Restrictions.eq("transactionId", tran.getId()));
		criteria.add(Restrictions.eq("state", PrepaymentState.AWAIT));
		List<Prepayment> list = prepaymentDao.findByDetachedCriteria(criteria);
		for (Prepayment prepayment : list) {
			prepayment.setState(PrepaymentState.SUCCESS);
			prepayment = prepaymentDao.save(prepayment);
			//生成一条彩金购彩的记录
		    writeLuckDetail(prepayment);
		}

		tran.setState(TransactionState.SUCCESS);
		tran = transactionDao.save(tran);
	}
	
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public User getUser(Long id) {
		return userDao.get(id);
	}
	@Transactional(readOnly = true)
	public TicketPlatformInfo getTicketPlatformInfo(Long id){
		return ticketPlatformInfoDao.findUniqueBy("userId", id);
	}
	
	@Transactional(readOnly = true)
	public Page<User> searchUser(final Page<User> page, final List<PropertyFilter> filters) {
		return userDao.findPage(page, filters);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public User getUserBy(String userName) {
		if (StringUtils.isBlank(userName))
			return null;
		List<User> l = userDao.findBy("userName", userName.trim());
		if (null != l && !l.isEmpty()) {
			return l.get(0);
		}

		return null;
	}
	
	public User saveUser(User user) {
		return userDao.save(user);
	}
	
	public TicketPlatformInfo saveTicketPlatformInfo(TicketPlatformInfo ticketPlatformInfo){
		return ticketPlatformInfoDao.save(ticketPlatformInfo);
		
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Media getMedia(Long id) {
		return mediaDao.get(id);
	}

	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Media getMediaBy(String mediaName) {
		if (StringUtils.isBlank(mediaName))
			return null;
		List<Media> l = mediaDao.findBy("name", mediaName.trim());
		if (null != l && !l.isEmpty()) {
			return l.get(0);
		}

		return null;
	}

	
	public Media saveMedia(Media media) {
		return mediaDao.save(media);	
	}
	
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Popu getPopu(Long id) {
		return popuDao.get(id);
	}
	
	
	public Popu savePopu(Popu popu) {
		return popuDao.save(popu);
	
	}
	
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public PhonePopu getPhonePopu(Long id) {
		return phonePopuDao.get(id);
	}
	
	
	public PhonePopu savePhonePopu(PhonePopu phonePopu) {
		return phonePopuDao.save(phonePopu);
	
	}
	
	
	public void clearFailUserLogin(User user) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("active", Boolean.FALSE);
		filter.put("clear", Boolean.FALSE);
		List<UserLogin> l = userLoginDao.findByMap(filter);
		if (null != l) {
			Iterator<UserLogin> it = l.iterator();
			UserLogin userLogin = null;
			while (it.hasNext()) {

				userLogin = it.next();
				userLogin.setClear(Boolean.TRUE);
				userLoginDao.save(userLogin);
			}
		}

	}

	
	public Integer getUserLoginNum(User user) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("userId", user.getId());
		filter.put("active", Boolean.FALSE);
		filter.put("clear", Boolean.FALSE);
		List<UserLogin> l = userLoginDao.findByMap(filter);
		if (null != l) {
			return Integer.valueOf(l.size());
		}
		return Integer.valueOf(0);
	}

	
	public UserLogin saveUserLogin(UserLogin userLogin) {
		return userLoginDao.save(userLogin);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public UserLogin getUserLoginBy(Long userId) {
		Map<String, Object> filter = new HashMap<String, Object>();
		UserLogin userLogin = new UserLogin();
		filter.put("userId", userId);
		List<UserLogin> list = userLoginDao.findByMap(filter);
		if(list!=null && list.size()>0) {
			userLogin = list.get(0);
		}
		return userLogin;
	}
	
//	public User saveUserWithLuckSend(User user, UserInfo info, BankInfo bank) {
//		user = userDao.save(user);
//		if (info != null) {
//			saveUserInfoWithLuckSend(info,user);
//			info.setUser(user);
//			info = userInfoDao.save(info);
//		}
//		if (bank != null) {
//			bank.setUser(user);
//			bank = bankInfoDao.save(bank);
//		}
//		return user;
//	}
//	public synchronized UserInfo saveUserInfoWithLuckSend(UserInfo info,User user) {
//		//完成所有用户信息
//		synchronized (Constant.Luck) {
//			Date startDate = DateUtil.strToDate("2012-10-01 00:00", null);
//			Date endDate = DateUtil.strToDate("2013-01-01 00:00", null);
//			Date now = new Date();
//			if(now.after(startDate)&&now.before(endDate)){
//				//完善资料送彩金
//				oprUserMoney(user,BigDecimal.valueOf(3),"注册送3元彩金活动",FundMode.IN,FundDetailType.RECHARGEACTIVITY,null);
//				//生成一条送彩金的记录
//				LuckDetail luckDetail = new LuckDetail(user,BigDecimal.valueOf(3),"注册送3元彩金活动",FundMode.IN,LuckDetailType.RESIGER_LUCK);
//				luckDetailDao.save(luckDetail);
//			}
//			user = userDao.get(user.getId());
//			UserInfo userInfo = user.getInfo();
//			userInfo.setMobile(info.getMobile());
//			userInfo.setIdCard(info.getIdCard());
//			userInfo.setEmail(info.getEmail());
//			userInfo.setRealName(info.getRealName());
//			return userInfoDao.save(userInfo);
//		}
//	}
	private void writeLuckDetail(Prepayment prepayment){
		synchronized (Constant.Luck) {
			User user = userDao.get(prepayment.getUserId());
			LuckDetail luckDetail = new LuckDetail(user,prepayment.getAmount(),"消费彩金{"+prepayment.getAmount()+"},预付款ID{"+prepayment.getId()+"}",FundMode.OUT,LuckDetailType.USE_LUCK);
			luckDetailDao.save(luckDetail);
		}
	}
	
	
	/**
	 * 
	 * @param userInfo 用户对象
	 * @param money 操作金额
	 * @param remark 详情
	 * @param mode 详情
	 * @param type 资金类型
	 * @param adminUser 操作员
	 * @return
	 */
	public FundDetail oprUserMoney(User user, BigDecimal money, String remark, FundMode mode, FundDetailType type, AdminUser adminUser) {
		try {
			if (null == user || null == user.getId())
				throw new ServiceException("用户实体错误");
			if (null == money || BigDecimal.valueOf(0).equals(money)) {
				throw new ServiceException("操作资金金额错误");
			}
			TicketPlatformInfo ticketPlatformInfo =ticketThenEntityManager.getTicketPlatformInfoByUserId(user.getId());
			if(null!=ticketPlatformInfo){
				if(type.equals(FundDetailType.SUBSCRIPTION)){
					if(mode.equals(FundMode.OUT)){
						user.addConsumptionMoney(money);
					}else if(mode.equals(FundMode.IN)){
						user.subtractConsumptionMoney(money);
					}
				}
				user = userDao.get(user.getId());
				BigDecimal allRemainMoney = user.getRemainMoney().add(ticketPlatformInfo.getRemainMoney());
				if (null == allRemainMoney) {
					allRemainMoney = BigDecimal.valueOf(0);
				}
				BigDecimal remainMoney = user.getRemainMoney();
				if (null == remainMoney) {
					remainMoney = BigDecimal.valueOf(0);
				}
				BigDecimal afterRemainMoney;
				BigDecimal allAfterRemainMoney;
				
				if (FundMode.IN.equals(mode)) {
					afterRemainMoney = remainMoney.add(money);
					allAfterRemainMoney = allRemainMoney.add(money);
				} else if (FundMode.OUT.equals(mode)) {
					afterRemainMoney = remainMoney.subtract(money);
					allAfterRemainMoney = allRemainMoney.subtract(money);
				} else {
					throw new ServiceException("操作资金金额错误");
				}
				user.setRemainMoney(afterRemainMoney);
				userDao.save(user);
				FundDetail fundDetail = new FundDetail(user, money, allAfterRemainMoney, remark, mode, type);
				fundDetail = fundDetailDao.save(fundDetail);
				if (adminUser != null) {
					eventLogManager.saveSimpleEventLog(null,null, adminUser, EventLogType.Fund_Admin, "资金ID为【"+ fundDetail.getId() + "】");
				}
				
				return fundDetail;
				
			}else{
				if(type.equals(FundDetailType.SUBSCRIPTION)){
					if(mode.equals(FundMode.OUT)){
						user.addConsumptionMoney(money);
					}else if(mode.equals(FundMode.IN)){
						user.subtractConsumptionMoney(money);
					}
				}
				user = userDao.get(user.getId());
				BigDecimal remainMoney = user.getRemainMoney();
				if (null == remainMoney) {
					remainMoney = BigDecimal.valueOf(0);
				}
				BigDecimal afterRemainMoney;
				
				if (FundMode.IN.equals(mode)) {
					afterRemainMoney = remainMoney.add(money);
				} else if (FundMode.OUT.equals(mode)) {
					afterRemainMoney = remainMoney.subtract(money);
				} else {
					throw new ServiceException("操作资金金额错误");
				}
				user.setRemainMoney(afterRemainMoney);
				userDao.save(user);
				FundDetail fundDetail = new FundDetail(user, money, afterRemainMoney, remark, mode, type);
				fundDetail = fundDetailDao.save(fundDetail);
				if (adminUser != null) {
					eventLogManager.saveSimpleEventLog(null,null, adminUser, EventLogType.Fund_Admin, "资金ID为【"+ fundDetail.getId() + "】");
				}
				
				return fundDetail;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("操作资金错误");
		}
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<DrawingOrder> countDrawingOrder(final List<Criterion> restrictions,final ProjectionList prop,final ResultTransformer resultTransformer){
		return (List<DrawingOrder>)drawingOrderDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				if(null!=prop){
					criteria.setProjection(prop);
				}
				for (Criterion criterion : restrictions) {
					criteria.add(criterion);
				}
				if(null!=resultTransformer){
					criteria.setResultTransformer(resultTransformer);
				}
				return criteria.list();
			}
		});
	}
	
	
	public DrawingOrder oprDrawingOrder(User user, BigDecimal money, BankInfo bankInfo, String tel) {
		try {
			if (null == user || null == user.getId())
				throw new ServiceException("用户实体错误");
			if (null == money || BigDecimal.valueOf(0).equals(money)) {
				throw new ServiceException("操作资金金额错误");
			}
			FundDetail fundDetail = this.oprUserMoney(user, money, "用户在线提款,扣除帐号金额", FundMode.OUT,
					FundDetailType.DRAWING, null);
			DrawingOrder drawingOrder = new DrawingOrder(user, money, bankInfo, tel, fundDetail.getId());
			drawingOrder = drawingOrderDao.save(drawingOrder);
			return drawingOrder;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("操作提款错误");
		}

	}

	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DrawingOrder getDrawingOrder(Long id) {
		return drawingOrderDao.get(id);
	}

	
	public DrawingOrder saveDrawingOrder(DrawingOrder drawingOrder) {
		return drawingOrderDao.save(drawingOrder);
	}

	
	public DrawingOrder saveAdminDrawingOrder(AdminUser adminUser, DrawingOrder drawingOrder) {
		eventLogManager.saveSimpleEventLog(null,null, adminUser, EventLogType.Drawing_Admin, "退款ID为【"
				+ drawingOrder.getId() + "】,设置状态为" + drawingOrder.getState().getStateName());
		if(DrawingOrderState.PASS.equals(drawingOrder.getState())){
			//统计
			User user = this.getUser(drawingOrder.getUserId());
			agentEntityManager.recordAgent(user, null, AgentDetailType.DRAWING, drawingOrder.getCreateTime(), drawingOrder.getDrawingMoney(),null);
		}
		return drawingOrderDao.save(drawingOrder);
	}

	
	public DrawingOrder deleteDrawingOrder(AdminUser adminUser, DrawingOrder drawingOrder) {
		try {
			User user = userDao.get(drawingOrder.getUserId());
			if (null == user || null == user.getId())
				throw new ServiceException("用户实体错误");
			if (null == adminUser || null == adminUser.getId())
				throw new ServiceException("操作员实体错误");
			FundDetail fundDetail = this.oprUserMoney(user, drawingOrder.getMoney(), "用户在线提款,扣除帐号金额", FundMode.IN, FundDetailType.DRAWINGFAIL, adminUser);
			if (null == fundDetail) {
				throw new ServiceException("操作资金失败");
			}
			drawingOrder.setState(DrawingOrderState.FAILCHECK);
			drawingOrder = drawingOrderDao.save(drawingOrder);
			eventLogManager.saveSimpleEventLog(null,null, adminUser, EventLogType.Drawing_Admin, "删除退款退款ID为【" + drawingOrder.getId() + "】");
			return drawingOrder;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("操作提款错误");
		}
	}
	
	public BankInfo saveBankInfo(BankInfo bank) {
		return bankInfoDao.save(bank);
	}

	
	public User saveUser(User user, UserInfo info, BankInfo bank) {
		user = userDao.save(user);
		if (info != null) {
			info.setUser(user);
			info = userInfoDao.save(info);
		}
		if (bank != null) {
			bank.setUser(user);
			bank = bankInfoDao.save(bank);
		}
		
		return user;
	}
	
	
	//用于实现论坛同步注册
	public User saveUser(User user, UserInfo info, BankInfo bank, String password) {
		user = userDao.save(user);
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
	
	//用于实现论坛同步修改密码
	public User saveUser(User user,String oldPassword,String newPassword) {
		user = userDao.save(user);
		String email = user.getUserId().toString() + "@miracle310" + ".com";
		try{
			//ForumController.updatePwd(user.getUserName(), oldPassword, newPassword, email);
		} catch(Exception e) {
			System.out.println("论坛修改密码失败.");
		}
		return user;
	}
	
	public Ipsorder saveIpsorder(Ipsorder ipsorder) {
		return ipsorderDao.save(ipsorder);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Ipsorder getIpsorder(Long id) {
		return ipsorderDao.get(id);
	}

	
	public User saveUser(User user, AdminUser adminUser,Double rebate) {
		eventLogManager.saveSimpleEventLog(null,null, adminUser, EventLogType.User_Admin, "用户ID为【" + user.getId()
				+ "】,设置可用状态为" + user.isLocked());
		user = userDao.save(user);
		//增加返点
		if(UserWay.AGENT.equals(user.getUserWay())){
			if(null!=user.getAgentId()){
				this.agentEntityManager.createAgentWithAgent(user.getId(), rebate,user.getAgentId());
			}else{
				this.agentEntityManager.createAgentIfNull(user.getId(), rebate);
			}
		}
		return user;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Ipsorder> countIpsorder(final List<Criterion> restrictions,final ProjectionList prop,final ResultTransformer resultTransformer){
		return (List<Ipsorder>)ipsorderDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				if(null!=prop){
					criteria.setProjection(prop);
				}
				for (Criterion criterion : restrictions) {
					criteria.add(criterion);
				}
				if(null!=resultTransformer){
					criteria.setResultTransformer(resultTransformer);
				}
				return criteria.list();
			}
		});
	}
	
	
	public synchronized Ipsorder confirmIps(Ipsorder ipsorder,User user,AdminUser adminUser) {
		synchronized (Constant.IPSPASS) {
			ipsorder = getIpsorder(ipsorder.getId());
			if (null == ipsorder) {
				throw new ServiceException("支付不成功，错误：找不到订单号，请联系客服");
			}
			if (null == ipsorder.getAmount()) {
				throw new ServiceException("支付不成功，错误：找不到订单金额，请联系客服");
			}
			Double orderamount = ipsorder.getAmount().doubleValue();
			if (null == orderamount) {
				throw new ServiceException("支付不成功，错误：订单金额错误，请联系客服");
			}
			try {
				user = getUser(user.getId());
				if (null == user || null == user.getId()) {
					throw new ServiceException("网上支付成功，但在本网数据库该条订单信息的用户名对应不上用户列表，请及时联系我们！否则你的资金不能及时加入您的账户！");
				}
				synchronized (Constant.Epay) {
					if (ipsorder.getIfcheck()) {
						throw new ServiceException("该次支付的资金已注入，可登陆您的帐号查看资金详细情况");
					} else {
						ipsorder.setIfcheck(true);
						ipsorder = saveIpsorder(ipsorder);
						FundDetail fundDetail = oprUserMoney(user, BigDecimalUtil.valueOf(orderamount),
								"用户在线充值,增加帐号金额" + orderamount + "元，订单号:" + ipsorder.getId(), FundMode.IN, FundDetailType.PAY, adminUser);
						if (null == fundDetail || null == fundDetail.getId()) {
							throw new ServiceException("网上支付成功，但更新资金出错，请及时联系我们！否则你的资金不能及时加入您的账户！");
						}
						agentEntityManager.recordAgent(user, null, AgentDetailType.PAY, ipsorder.getCreateTime(), ipsorder.getAmount(),null);
						return ipsorder;
					}
				}
			}catch (ServiceException e) {
				throw e;
			}catch (Exception e) {
				throw new ServiceException("存储汇款结果时发生错误，请及时联系我们！否则你的资金可能没加入您的账户！" + e.toString());

			}
		}
	}

	
	@Override
	public UserInfo saveUserInfo(UserInfo info) {
		return userInfoDao.save(info);
	}

	@Override
	public UserGrade saveUserGrade(UserGrade info) {
		return userGradeDao.save(info);
	}
	
	@Override
	public UserGrade saveUserGrade(User user,UserGrade info) {
		info.setUser(user);
		info = userGradeDao.save(info);
		user.setGradeInfo(info);
		this.userDao.save(user);
		return info;
	}
	
	@Override
	public void updateUserGrade(Long userId,SchemeEntityManager schemeManager,List<Scheme> schemes,UserGrade userGrade){
		User user = this.userDao.get(userId);
		if(user==null){
			return;
		}
		UserGrade userGradeOld = user.getGradeInfo();
		if(userGradeOld==null){
			this.saveUserGrade(user,userGrade);
		}else{
			userGradeOld.setFadanNums(userGradeOld.getFadanNums()+userGrade.getFadanNums());
			userGradeOld.setFadanSuccessNums(userGradeOld.getFadanSuccessNums()+userGrade.getFadanSuccessNums());
			userGradeOld.setWonTimes(userGradeOld.getWonTimes()+userGrade.getWonTimes());
			userGradeOld.setWonTimes_qian(userGradeOld.getWonTimes_qian()+userGrade.getWonTimes_qian());
			userGradeOld.setWonTimes_wan(userGradeOld.getWonTimes_wan()+userGrade.getWonTimes_wan());
			
			//更新总奖金
			BigDecimal totalPrizeOld = userGradeOld.getTotalPrize();
			if(totalPrizeOld==null)totalPrizeOld=new BigDecimal(0);
			BigDecimal totalPrize = userGrade.getTotalPrize();
			if(totalPrize==null)totalPrize = new BigDecimal(0);
			userGradeOld.setTotalPrize(totalPrizeOld.add(totalPrize));
			
			//玩法奖金更新
			Map<String,BigDecimal> prizesMap = userGrade.getPrizeMap();
			Map<String,BigDecimal> prizesMapOld = userGradeOld.getPrizeMap();
			String key = null;
			BigDecimal prizeOld = null;
			BigDecimal prize = null;
			for(Entry<String,BigDecimal> entry : prizesMap.entrySet()){
				key = entry.getKey();
				prizeOld = prizesMapOld.get(key);
				if(prizeOld==null)prizeOld=new BigDecimal(0);
				prize=entry.getValue();
				if(prize!=null){
					prizesMapOld.put(key, prizeOld.add(prize));
				}				
			}
			String prizesPtJson = JsonUtil.getJsonString4JavaPOJO(prizesMapOld);
			userGradeOld.setPrizes_play(prizesPtJson);
			
			//更新奖牌(未完成)
			Map<String,GradeMedal> gmMapOld = userGradeOld.getMedalMap();
			Map<String,GradeMedal> gmMap = userGrade.getMedalMap();
			GradeMedal gradeMedalOld = null;
			GradeMedal gradeMedal = null;
			for(Entry<String,GradeMedal> entry : gmMap.entrySet()){
				key = entry.getKey();
				gradeMedalOld = gmMapOld.get(key);
				if(gradeMedalOld==null)gradeMedalOld=new GradeMedal();
				gradeMedal = entry.getValue();
				if(gradeMedal!=null){
					gradeMedalOld.setGoldenNums(gradeMedalOld.getGoldenNums()+gradeMedal.getGoldenNums());
					gradeMedalOld.setSilveryNums(gradeMedalOld.getSilveryNums()+gradeMedal.getSilveryNums());
					gmMapOld.put(key, gradeMedalOld);
				}				
			}
			String medalPtJson = JsonUtil.getJsonString4JavaPOJO(gmMapOld);
			userGradeOld.setMedals_play(medalPtJson);
			
			this.saveUserGrade(userGradeOld);
		}
		for(Scheme scheme : schemes){
			scheme = schemeManager.getScheme(scheme.getId());
			if(scheme==null)continue;
			scheme.setGradeFlag(true);
			schemeManager.saveScheme(scheme);
		}
	}
	
	@Override
	public void agentOprUserMoney(User agentUser, User user,BigDecimal money) {
		if(money.doubleValue()>=0){
			this.oprUserMoney(user, money.abs(), "上级代理商操作金额",  FundMode.IN, FundDetailType.AGENTOPR, null);
			this.oprUserMoney(agentUser, money.abs(), "上级代理商操作金额",  FundMode.OUT, FundDetailType.AGENTOPR, null);
		}else{
			this.oprUserMoney(agentUser, money.abs(), "上级代理商操作金额",  FundMode.IN, FundDetailType.AGENTOPR, null);
			this.oprUserMoney(user, money.abs(), "上级代理商操作金额",  FundMode.OUT, FundDetailType.AGENTOPR, null);
		}
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor=Exception.class)  
	public boolean resetPasswordAndSendEmail(String userName, String email) throws Exception {
		User user = this.userDao.findUniqueBy("userName", userName);
		if (null != user) {
			if (StringUtils.isNotBlank(user.getInfo().getEmail()) && user.getInfo().getEmail().equalsIgnoreCase((email))) {
				// 生成十位数字随机密码
					String newpassword = RandomUtils.generateNumString(10);
					user.setPassword(MD5.md5(newpassword.trim()).toUpperCase());
					userDao.save(user);
					Map<String, String> content = new HashMap<String, String>();
					content.put("userName", userName);
					content.put("newpassword", newpassword);
					
					String emailContent = generateEmailContent("user-passwd-mail.ftl", content);
					;
					Properties props=new Properties();
		        	props.put("mail.smtp.host",((JavaMailSenderImpl)mailSender).getHost());
		        	props.put("mail.smtp.auth","true");
		        	
		        	javax.mail.Session s=javax.mail.Session.getInstance(props);
					
		        	MimeMessage message=new MimeMessage(s);
		        	InternetAddress from=new InternetAddress(((JavaMailSenderImpl)mailSender).getUsername());
		        	message.setFrom(from);
		        	InternetAddress to=new InternetAddress(email);
		        	message.addRecipient(Message.RecipientType.TO,to);
		        	message.setSubject("——重置密码");
		        	message.setText(emailContent);
		        	Transport transport=s.getTransport("smtp");
		        	transport.connect(((JavaMailSenderImpl)mailSender).getHost(),((JavaMailSenderImpl)mailSender).getUsername(),((JavaMailSenderImpl)mailSender).getPassword());
		        	transport.sendMessage(message,message.getAllRecipients());
		        	//这句是必须的，我曾尝试用transport.send(message);，但没能成功。
		        	transport.close();
					return true;
			}
		}
		return false;
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor=Exception.class)  
	public boolean resetPasswordAndSendMessage(String userName, String mobile) throws Exception {
		User user = this.userDao.findUniqueBy("userName", userName);
		if (null != user) {
			    if(!user.getValidatedPhoneNo())return false;
			    if(null==user.getInfo()||StringUtils.isBlank(user.getInfo().getMobile()))return false;
			    if(!mobile.trim().equals(user.getInfo().getMobile().trim()))return false;
				// 生成十位数字随机密码
				String newpassword = RandomUtils.generateNumString(10);
				user.setPassword(MD5.md5(newpassword.trim()).toUpperCase());
				//发送短信
				List<String> mobileList = Lists.newArrayList();
				mobileList.add(mobile.trim());
				String content = "亲爱的"+user.getUserName()+",欢迎您使用重置密码功能,重置密码成功,您的新密码是:"+newpassword+",请您及时使用新密码登录并修改密码";
				Map<String,SendState> sendMap = MessageUtil.sendMessage(mobileList, content);
			    if(!sendMap.isEmpty()){
			    	SendState sendState = sendMap.get(mobile.trim());
			    	if(SendState.SUCCESS.equals(sendState)){
			    		return true;
			    	}
			    }
		}
		return false;
	}
	
	
	/**
	 * 产生邮件正文模板
	 * 
	 * @param templateName
	 * @param map
	 * @return
	 */
	public String generateEmailContent(String templateName, Map map) {
		Configuration cfg = freeMarkerConfigurer.getConfiguration();
		try {
			Template t = cfg.getTemplate(templateName);
			return FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 随机验证码
	 * 
	 * @param user
	 *            用户实体
	 * @param mobile
	 *            电话号码
	 * @param messageType
	 *            活动消息类型，参考com.cai310.lottery.common.MessageType
	 * @return RandomMessage实体
	 */
	@Transactional
	public synchronized RandomMessage SendRandomMessage(User user,
			String mobile, MessageType messageType) {
		if (null == user)
			throw new ServiceException("发送短信用户为空.");
		if (null == messageType)
			throw new ServiceException("发送短信类型为空.");
		if (StringUtils.isBlank(mobile))
			throw new ServiceException("发送手机号码为空.");
		synchronized (Constant.MESSAGE) {

			Integer randomWord = Integer.valueOf("8"+RandomUtils.newGenerateNumString(5));// 生成6位的验证码
			String content = "尊敬的" + user.getUserName() + ",欢迎您使用"
					+ messageType.getTypeName() + "，您的验证码是：" + randomWord
					+ ",请妥善保管，祝您中大奖";

			DetachedCriteria criteria = DetachedCriteria
					.forClass(RandomMessage.class);
			criteria.add(Restrictions.eq("mobile", mobile.trim()));
			criteria.add(Restrictions.eq("messageType", messageType));
			criteria.add(Restrictions.eq("enable", false));
			List<RandomMessage> list = randomMessageDao.findByDetachedCriteria(criteria);
			RandomMessage randomMessage = null;
			if (null == list || list.isEmpty()) {
				randomMessage = new RandomMessage(user, mobile, randomWord,
						messageType);
				SendMessage(content, mobile);
				return randomMessageDao.save(randomMessage);
			} else {
				randomMessage = list.get(0);
				if (randomMessage.getEnable() && !randomMessage.getMessageType().getCanSendAgain()) {
					throw new ServiceException("发送手机号码已经参加过活动.");
				} else {
					randomMessage.setUserId(user.getId());
					randomMessage.setUserName(user.getUserName());
					randomMessage.setRandomWord(randomWord);
					SendMessage(content, mobile);
					return randomMessageDao.save(randomMessage);
				}
			}
		}
	}
	
	
	private void SendMessage(String content,
			String mobile) {
		if (StringUtils.isBlank(content))
			throw new ServiceException("发送短信内容为空.");
		if (StringUtils.isBlank(mobile))
			throw new ServiceException("发送手机号码为空.");
		///发送手机短信
		List<String> num = Lists.newArrayList();
		num.add(mobile);
		MessageUtil.sendMessage(num, content);
	}
	
	
	/**
	 * 使用验证码
	 * 
	 * @param mobile
	 *            电话号码
	 * @param messageType
	 *            活动消息类型，参考com.cai310.lottery.common.MessageType
	 * @param randomWord
	 *            随机号
	 */
	@Transactional
	public synchronized void userRandomMessage(String mobile,
			MessageType messageType, Integer randomWord) {
		synchronized (Constant.MESSAGE) {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(RandomMessage.class);
			criteria.add(Restrictions.eq("mobile", mobile.trim()));
			criteria.add(Restrictions.eq("randomWord", randomWord));
			criteria.add(Restrictions.eq("messageType", messageType));
			criteria.add(Restrictions.eq("enable", false));
			criteria.addOrder(Order.desc("id"));
			List<RandomMessage> list = randomMessageDao.findByDetachedCriteria(criteria);
			RandomMessage randomMessage = null;
			if (null == list || list.isEmpty()) {
				throw new ServiceException("验证码不正确.");
			} else {
				randomMessage = list.get(0);
				if (randomMessage.getEnable()&&!randomMessage.getMessageType().getCanSendAgain())
					throw new ServiceException("本次活动该手机已经参加了.");
				randomMessage.setEnable(Boolean.TRUE);
				randomMessage = randomMessageDao.save(randomMessage);
				if (null == randomMessage.getUserId())
					throw new ServiceException("数据错误，用户id为空.");
				User user = userDao.get(randomMessage.getUserId());
				if (null == user)
					throw new ServiceException("数据错误，用户为空.");
				if (MessageType.CHECKPHONE.equals(messageType)) {
					user.setValidatedPhoneNo(Boolean.TRUE);
					user = userDao.save(user);
					UserInfo userInfo = user.getInfo();
					if (null == userInfo)
						throw new ServiceException("数据错误,用户资料为空.");
					userInfo.setMobile(randomMessage.getMobile());
					userInfoDao.save(userInfo);
				}
			}
		}
	}

	
	public PhonePopuDao getPhonePopuDao() {
		return phonePopuDao;
	}
	
	
	public void setPhonePopuDao(PhonePopuDao phonePopuDao) {
		this.phonePopuDao = phonePopuDao;
	}
	
	
	@Transactional
	@Override
	public void saveBankInfoWith(BankInfoForm bankInfoForm,BankInfo bankInfo) {
		
		bankInfo.setBankName(bankInfoForm.getBankName());
		bankInfo.setPartBankProvince(bankInfoForm.getPartBankProvince());
		bankInfo.setBankCard(bankInfoForm.getBankCard());
		if(bankInfoForm.getPartBankCity() != null) {
			bankInfo.setPartBankCity(bankInfoForm.getPartBankCity());
		}
		if(bankInfoForm.getPartBankName() != null) {
			bankInfo.setPartBankName(bankInfoForm.getPartBankName());
		}
		
		this.saveBankInfo(bankInfo);
		
	}
	
	
	@Transactional
	@Override
	public void deleteBankInfo(BankInfo bankInfo) {
		bankInfo.setBankName(null);
		bankInfo.setBankCard(null);
		bankInfo.setCreateTime(null);
		bankInfo.setLastModifyTime(null);
		bankInfo.setPartBankCity(null);
		bankInfo.setPartBankName(null);
		bankInfo.setPartBankProvince(null);
		this.saveBankInfo(bankInfo);
	}
	@Override
	public LuckDetail saveLuckDetail(LuckDetail luckDetail) {
		return luckDetailDao.save(luckDetail);
	}


	@Override
	public User getQqUserById(String id) {
		return userDao.findUniqueBy("qqId", id);
	}
	
	@Override
	public String getRandomPassword() {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 12; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	@Override
	public ScoreDetail oprUserScore(User user, BigDecimal score, String remark,
			FundMode mode, ScoreDetailType type, AdminUser adminUser) {
		// TODO Auto-generated method stub
		try {
			if (null == user || null == user.getId())
				throw new ServiceException("用户实体错误");
			if (null == score || BigDecimal.valueOf(0).equals(score)) {
				throw new ServiceException("操作资金金额错误");
			}
			// if(type.equals(FundDetailType.SUBSCRIPTION)){
			// if(mode.equals(FundMode.OUT)){
			// user.addConsumptionMoney(money);
			// }else if(mode.equals(FundMode.IN)){
			// user.subtractConsumptionMoney(money);
			// }
			// }
			user = userDao.get(user.getId());
			BigDecimal remainScore = user.getRemainScore();
			if (null == remainScore) {
				remainScore = BigDecimal.valueOf(0);
			}
			BigDecimal afterRemainScore;
			if (FundMode.IN.equals(mode)) {
				afterRemainScore = remainScore.add(score);
			} else if (FundMode.OUT.equals(mode)) {
				afterRemainScore = remainScore.subtract(score);
			} else {
				throw new ServiceException("操作资金金额错误");
			}
			user.setRemainScore(afterRemainScore);
			userDao.save(user);
			ScoreDetail scoreDetail = new ScoreDetail(user, score,
					afterRemainScore, remark, mode, type);
			scoreDetail = scoreDetailDao.save(scoreDetail);

			if (adminUser != null) {
				eventLogManager.saveSimpleEventLog(null, null, adminUser,
						EventLogType.Score_Admin,
						"积分ID为【" + scoreDetail.getId() + "】");
			}

			return scoreDetail;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("操作积分错误");
		}
	}
}
