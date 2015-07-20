package com.cai310.lottery.web.controller.admin.fund;

import java.math.BigDecimal;
import java.util.Date;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SubscriptionState;
import com.cai310.lottery.common.SubscriptionWay;

public class SubscriptionForm {
	/** 方案编号 */
	private Long schemeId;

	/** 用户编号 */
	private Long userId;

	/** 用户名 */
	private String userName;

	/** 认购金额 */
	private BigDecimal cost;

	/** 状态 */
	private SubscriptionState state;

	/** 认购方式 */
	private SubscriptionWay way;

	/** 分红 */
	private BigDecimal bonus;

	/** 分红是否已派送 */
	private boolean bonusSended;

	/** 版本号,用于实现乐观锁 */
	private Integer version;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;

	/** 预付款ID */
	private Long prepaymentId;

	/**
	 * 彩票类型
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private Lottery lotteryType;

	/**
	 * @return the schemeId
	 */
	public Long getSchemeId() {
		return schemeId;
	}

	/**
	 * @param schemeId the schemeId to set
	 */
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the cost
	 */
	public BigDecimal getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	/**
	 * @return the state
	 */
	public SubscriptionState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(SubscriptionState state) {
		this.state = state;
	}

	/**
	 * @return the way
	 */
	public SubscriptionWay getWay() {
		return way;
	}

	/**
	 * @param way the way to set
	 */
	public void setWay(SubscriptionWay way) {
		this.way = way;
	}

	/**
	 * @return the bonus
	 */
	public BigDecimal getBonus() {
		return bonus;
	}

	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}

	/**
	 * @return the bonusSended
	 */
	public boolean isBonusSended() {
		return bonusSended;
	}

	/**
	 * @param bonusSended the bonusSended to set
	 */
	public void setBonusSended(boolean bonusSended) {
		this.bonusSended = bonusSended;
	}

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the lastModifyTime
	 */
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	/**
	 * @param lastModifyTime the lastModifyTime to set
	 */
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	/**
	 * @return the prepaymentId
	 */
	public Long getPrepaymentId() {
		return prepaymentId;
	}

	/**
	 * @param prepaymentId the prepaymentId to set
	 */
	public void setPrepaymentId(Long prepaymentId) {
		this.prepaymentId = prepaymentId;
	}

	/**
	 * @return the lotteryType
	 */
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lotteryType the lotteryType to set
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}
	
}
