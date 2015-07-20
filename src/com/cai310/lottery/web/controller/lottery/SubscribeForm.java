package com.cai310.lottery.web.controller.lottery;

import java.math.BigDecimal;

public class SubscribeForm {

	/** 认购的金额 */
	private BigDecimal subscriptionCost;

	/** 保底的金额 */
	private BigDecimal baodiCost;

	/** 认购的密码 */
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
}
