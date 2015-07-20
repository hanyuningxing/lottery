package com.cai310.lottery.web.controller.lottery;

import java.math.BigDecimal;

import com.cai310.lottery.common.AutoFollowState;
import com.cai310.lottery.common.AutoFollowType;
import com.cai310.lottery.common.Lottery;

public class AutoForm {
	private Long id;
	/** 发起人的用户编号 */
	private Long sponsorUserId;
	/** 发起人的用户名 */
	private String sponsorUserName;
	/** 跟单人的用户编号 */
	private Long followUserId;
	/** 跟单人的用户名 */
	private String followUserName;
	/**
	 * 跟单类型
	 * 
	 * @see com.cai310.lottery.common.AutoFollowType
	 */
	private AutoFollowType followType;
	/**
	 * 彩种类型
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private Lottery lotteryType;

	/** 玩法 */
	private Byte lotteryPlayType;

	/**
	 * 跟单订单状态
	 * 
	 * @see com.cai310.lottery.common.AutoFollowState
	 */
	private AutoFollowState state;
	/** 一期跟单金额上限 */
	private String periodMaxFollowCost;
	/** 跟单百分比 */
	private String followPercent;
	/** 跟单金额 */
	private String followCost;

	
	/** 一期跟单金额上限 */
	private BigDecimal periodMaxFollowCostBigDecimal;
	/** 跟单百分比 */
	private Integer followPercentInteger;
	/** 跟单金额 */
	private BigDecimal followCostBigDecimal;
	
	/**
	 * @return the sponsorUserId
	 */
	public Long getSponsorUserId() {
		return sponsorUserId;
	}

	/**
	 * @param sponsorUserId the sponsorUserId to set
	 */
	public void setSponsorUserId(Long sponsorUserId) {
		this.sponsorUserId = sponsorUserId;
	}

	/**
	 * @return the sponsorUserName
	 */
	public String getSponsorUserName() {
		return sponsorUserName;
	}

	/**
	 * @param sponsorUserName the sponsorUserName to set
	 */
	public void setSponsorUserName(String sponsorUserName) {
		this.sponsorUserName = sponsorUserName;
	}

	/**
	 * @return the followUserId
	 */
	public Long getFollowUserId() {
		return followUserId;
	}

	/**
	 * @param followUserId the followUserId to set
	 */
	public void setFollowUserId(Long followUserId) {
		this.followUserId = followUserId;
	}

	/**
	 * @return the followUserName
	 */
	public String getFollowUserName() {
		return followUserName;
	}

	/**
	 * @param followUserName the followUserName to set
	 */
	public void setFollowUserName(String followUserName) {
		this.followUserName = followUserName;
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

	/**
	 * @return the state
	 */
	public AutoFollowState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(AutoFollowState state) {
		this.state = state;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the followType
	 */
	public AutoFollowType getFollowType() {
		return followType;
	}

	/**
	 * @param followType the followType to set
	 */
	public void setFollowType(AutoFollowType followType) {
		this.followType = followType;
	}

	/**
	 * @return {@link #lotteryPlayType}
	 */
	public Byte getLotteryPlayType() {
		return lotteryPlayType;
	}

	/**
	 * @param lotteryPlayType the {@link #lotteryPlayType} to set
	 */
	public void setLotteryPlayType(Byte lotteryPlayType) {
		this.lotteryPlayType = lotteryPlayType;
	}

	/**
	 * @return the periodMaxFollowCost
	 */
	public String getPeriodMaxFollowCost() {
		return periodMaxFollowCost;
	}

	/**
	 * @param periodMaxFollowCost the periodMaxFollowCost to set
	 */
	public void setPeriodMaxFollowCost(String periodMaxFollowCost) {
		this.periodMaxFollowCost = periodMaxFollowCost;
	}

	/**
	 * @return the followPercent
	 */
	public String getFollowPercent() {
		return followPercent;
	}

	/**
	 * @param followPercent the followPercent to set
	 */
	public void setFollowPercent(String followPercent) {
		this.followPercent = followPercent;
	}

	/**
	 * @return the followCost
	 */
	public String getFollowCost() {
		return followCost;
	}

	/**
	 * @param followCost the followCost to set
	 */
	public void setFollowCost(String followCost) {
		this.followCost = followCost;
	}

	/**
	 * @return the periodMaxFollowCostBigDecimal
	 */
	public BigDecimal getPeriodMaxFollowCostBigDecimal() {
		return periodMaxFollowCostBigDecimal;
	}

	/**
	 * @param periodMaxFollowCostBigDecimal the periodMaxFollowCostBigDecimal to set
	 */
	public void setPeriodMaxFollowCostBigDecimal(
			BigDecimal periodMaxFollowCostBigDecimal) {
		this.periodMaxFollowCostBigDecimal = periodMaxFollowCostBigDecimal;
	}

	/**
	 * @return the followPercentInteger
	 */
	public Integer getFollowPercentInteger() {
		return followPercentInteger;
	}

	/**
	 * @param followPercentInteger the followPercentInteger to set
	 */
	public void setFollowPercentInteger(Integer followPercentInteger) {
		this.followPercentInteger = followPercentInteger;
	}

	/**
	 * @return the followCostBigDecimal
	 */
	public BigDecimal getFollowCostBigDecimal() {
		return followCostBigDecimal;
	}

	/**
	 * @param followCostBigDecimal the followCostBigDecimal to set
	 */
	public void setFollowCostBigDecimal(BigDecimal followCostBigDecimal) {
		this.followCostBigDecimal = followCostBigDecimal;
	}

}
