package com.cai310.lottery.dto.lottery;

import java.math.BigDecimal;

import com.cai310.lottery.common.SubscriptionWay;

/**
 * 认购和保底的数据传输对象
 * 
 */
public class SubscribeDTO {
	/** 所要认购或保底的方案ID */
	private Long schemeId;

	/** 用户ID */
	private Long userId;

	/** 认购的金额 */
	private BigDecimal subscriptionCost;

	/** 保底的金额 */
	private BigDecimal baodiCost;

	/** 认购密码 */
	private String password;

	/**
	 * @return {@link #password}
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the {@link #password} to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 认购的方式
	 * 
	 * @see com.cai310.lottery.common.SubscriptionWay
	 */
	private SubscriptionWay way;

	/**
	 * @return {@link #schemeId}
	 */
	public Long getSchemeId() {
		return schemeId;
	}

	/**
	 * @param schemeId the {@link #schemeId} to set
	 */
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @return {@link #userId}
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the {@link #userId} to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return {@link #subscriptionCost}
	 */
	public BigDecimal getSubscriptionCost() {
		return subscriptionCost;
	}

	/**
	 * @param subscriptionCost the {@link #subscriptionCost} to set
	 */
	public void setSubscriptionCost(BigDecimal subscriptionCost) {
		this.subscriptionCost = subscriptionCost;
	}

	/**
	 * @return {@link #baodiCost}
	 */
	public BigDecimal getBaodiCost() {
		return baodiCost;
	}

	/**
	 * @param baodiCost the {@link #baodiCost} to set
	 */
	public void setBaodiCost(BigDecimal baodiCost) {
		this.baodiCost = baodiCost;
	}

	/**
	 * @return {@link #way}
	 */
	public SubscriptionWay getWay() {
		return way;
	}

	/**
	 * @param way the {@link #way} to set
	 */
	public void setWay(SubscriptionWay way) {
		this.way = way;
	}
}
