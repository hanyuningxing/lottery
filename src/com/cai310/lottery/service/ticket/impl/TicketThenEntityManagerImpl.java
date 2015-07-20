package com.cai310.lottery.service.ticket.impl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.FundMode;
import com.cai310.lottery.common.TicketSchemeState;
import com.cai310.lottery.dao.ticket.TicketDatailDao;
import com.cai310.lottery.dao.ticket.TicketPlatformInfoDao;
import com.cai310.lottery.dao.ticket.TicketThenDao;
import com.cai310.lottery.dao.user.UserDao;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.ticket.TicketDatail;
import com.cai310.lottery.entity.ticket.TicketPlatformInfo;
import com.cai310.lottery.entity.ticket.TicketThen;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.ticket.TicketThenEntityManager;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;

/**
 * 接票
 * 
 */
@Service("ticketThenEntityManager")
@Transactional
public class TicketThenEntityManagerImpl implements TicketThenEntityManager {

	@Autowired
	protected TicketPlatformInfoDao ticketPlatformInfoDao;
	@Autowired
	UserEntityManager userManager;
	@Autowired
	protected TicketThenDao ticketThenDao;
	@Autowired
	protected TicketDatailDao ticketDatailDao;
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public TicketPlatformInfo getTicketPlatformInfo(Long id) {
			return ticketPlatformInfoDao.get(id);
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public TicketPlatformInfo getTicketPlatformInfoByUserId(Long userId) {
		return ticketPlatformInfoDao.findUniqueBy("userId", userId);
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean isRepeatOrder(final String orderId, final Long platformInfoId) {
		return (Boolean) ticketThenDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("orderId", orderId));
				criteria.add(Restrictions.eq("platformInfoId", platformInfoId));
				if(null!=criteria.uniqueResult()){
					return true;
				}else{
					return false;
				}
			}
		});
	}

	@Override
	public TicketPlatformInfo saveTicketPlatformInfo(
			TicketPlatformInfo ticketPlatformInfo) {
		return ticketPlatformInfoDao.save(ticketPlatformInfo);
	}

	@Override
	public TicketThen saveTicketThen(TicketThen ticketThen) {
		return ticketThenDao.save(ticketThen);
	}

	@Override
	public TicketDatail saveTicketDatail(TicketDatail ticketDatail) {
		return this.ticketDatailDao.save(ticketDatail);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public TicketThen getTicketThenByScheme(Scheme scheme) {
		// TODO 改为ID
		
		return ticketThenDao.findUniqueBy("schemeNumber", scheme.getSchemeNumber());
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public TicketDatail getTicketDatailByTicketThen(TicketThen ticketThen) {
		return ticketDatailDao.findUniqueBy("ticketId", ticketThen.getId());
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> getAllTicketPlatformInfo() {
		return (List<Long>) ticketPlatformInfoDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				return criteria.list();
			}
		});
	}
	@Override
	public void updateTicketPlatformInfoPrize(){
	    List<Long> list = this.getAllTicketPlatformInfo();
	    TicketPlatformInfo ticketPlatformInfo;
	    for (Long ticketPlatformInfoId : list) {
	    	  ticketPlatformInfo = this.ticketPlatformInfoDao.get(ticketPlatformInfoId);
	    	  tranUserToTicketPlatformInfo(ticketPlatformInfo.getUserId());
		}
	}
	
	@Override
	public void tranUserToTicketPlatformInfo(Long userId){
	    User user = this.userManager.getUser(userId);
	    if(null!=user){
	    	if(user.getRemainMoney().compareTo(BigDecimal.ZERO)>0){
	    		this.UserTranMoneyToTicketPlatformInfo(this.getTicketPlatformInfoByUserId(userId), user);
	    	}
	    }
	}
	@Override
	public void UserTranMoneyToTicketPlatformInfo(
			TicketPlatformInfo ticketPlatformInfo, User user) {
		try {
			ticketPlatformInfo.addMoney(user.getRemainMoney());
		} catch (DataException e) {
			throw new ServiceException("增加出票账户金额错误");
		}
		try {
			user.subtractMoney(user.getRemainMoney());
		} catch (DataException e) {
			throw new ServiceException("扣除彩票账户金额错误");
		}
		ticketPlatformInfoDao.save(ticketPlatformInfo);
		this.userManager.saveUser(user);
	}
	public void tranTicketPlatformInfoToUser(TicketPlatformInfo ticketPlatformInfo, User user,BigDecimal money){
		try {
			ticketPlatformInfo.subtractMoney(money);
		} catch (DataException e) {
			throw new ServiceException("扣除出票账户金额错误");
		}
		try {
			user.addMoney(money);
		} catch (DataException e) {
			throw new ServiceException("增加彩票账户金额错误");
		}
		ticketPlatformInfoDao.save(ticketPlatformInfo);
		this.userManager.saveUser(user);
	}
	@Override
	public void adminOperUserMoney(Long userId,FundMode mode,BigDecimal money,String remark,AdminUser adminUser) {
		User user = this.userManager.getUser(userId);
	    if(null==user)throw new ServiceException("找不到被操作的人");
	    TicketPlatformInfo ticketPlatformInfo = this.getTicketPlatformInfoByUserId(user.getId());
	    if(null==ticketPlatformInfo){
	    	this.userManager.oprUserMoney(user, money, remark, mode, FundDetailType.ADMIN_OPR, adminUser);
	    }else{
		    if(FundMode.IN.equals(mode)){
		    	this.tranUserToTicketPlatformInfo(user.getId());
		    	this.userManager.oprUserMoney(user, money, remark, mode, FundDetailType.ADMIN_OPR, adminUser);
		    	this.tranUserToTicketPlatformInfo(user.getId());
		    }if(FundMode.OUT.equals(mode)){
		    	//扣钱
		    	if(user.getRemainMoney().compareTo(BigDecimal.ZERO)>0){
		    		if(user.getRemainMoney().compareTo(money)>0){
		    			//用户余额大于扣款金额
		    			this.userManager.oprUserMoney(user, money, remark, mode, FundDetailType.ADMIN_OPR, adminUser);
		    			this.tranUserToTicketPlatformInfo(user.getId());
		    		}else{
		    			//用户余额小于扣款金额
		    			BigDecimal balance = money.subtract(user.getRemainMoney());
		    			if(ticketPlatformInfo.getRemainMoney().compareTo(balance)>=0){
		    				this.tranTicketPlatformInfoToUser(ticketPlatformInfo, user, balance);
		    				this.userManager.oprUserMoney(user, money, remark, mode, FundDetailType.ADMIN_OPR, adminUser);
		    				this.tranUserToTicketPlatformInfo(user.getId());
		    			}else{
		    				throw new ServiceException("出票商余额不足");
		    			}
		    		}
		    	}else{
		    		BigDecimal balance = money.subtract(user.getRemainMoney());
	    			if(ticketPlatformInfo.getRemainMoney().compareTo(balance)>=0){
	    				this.tranTicketPlatformInfoToUser(ticketPlatformInfo, user, balance);
	    				this.userManager.oprUserMoney(user, money, remark, mode, FundDetailType.ADMIN_OPR, adminUser);
	    				this.tranUserToTicketPlatformInfo(user.getId());
	    			}else{
	    				throw new ServiceException("出票商余额不足");
	    			}
		    	}
		    }
	    }
	    
	}
	@Override
	public void synchronizationFailTicket(Scheme scheme){
		if (scheme == null)throw new ServiceException("方案不存在.");
		if(null!=scheme.getOrderId()){
			TicketThen ticketThen = this.getTicketThenByScheme(scheme);
			if(null==ticketThen)throw new ServiceException("出票队列不存在.");
			//接票方案
			ticketThen.setCancelTime(new Date());
			ticketThen.setState(TicketSchemeState.FAILD);
			ticketThen.setTicketFinsh(false);
			this.saveTicketThen(ticketThen);
			this.tranUserToTicketPlatformInfo(scheme.getSponsorId());
		}
	}
	@Override
	public void synchronizationWonTicket(Scheme scheme){
		if (scheme == null)throw new ServiceException("方案不存在.");
		if(null!=scheme.getOrderId()){
			TicketThen ticketThen = this.getTicketThenByScheme(scheme);
			if(null==ticketThen)throw new ServiceException("出票队列不存在.");
			//接票方案
			if(scheme.isWon()){
				ticketThen.setWon(true);
				ticketThen.setWonDetail(scheme.getPrizeDetail());
				ticketThen.setPrizeTime(new Date());
				ticketThen.setTotalPrize(scheme.getPrize().doubleValue());
				ticketThen.setTotalPrizeAfterTax(scheme.getPrizeAfterTax());
			}
			this.saveTicketThen(ticketThen);
		}
	}
}
