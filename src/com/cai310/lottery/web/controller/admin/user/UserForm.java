package com.cai310.lottery.web.controller.admin.user;

import java.math.BigDecimal;

public class UserForm{
	private Long id;

	/** 用户名 */
	private String userName;

	/** 账户密码 */
	private String password;

	/** 账户是否被锁定 */
	private String status;

	/** 账户余额 */
	private BigDecimal remainMoney;

	/** 消费余额 */
	private BigDecimal consumptionMoney;
	
	/** 中奖总额 */
	private BigDecimal sumUserBonus;
	
	public BigDecimal getSumUserBonus() {
		return sumUserBonus;
	}

	public void setSumUserBonus(BigDecimal sumUserBonus) {
		this.sumUserBonus = sumUserBonus;
	}

	private String realName;


	private String defaultAccountRemainMoney; //现金账户余额
	private String activityAccountRemainMoney; //现金账户余额

	public String getDefaultAccountRemainMoney() {
		return defaultAccountRemainMoney;
	}

	public void setDefaultAccountRemainMoney(String defaultAccountRemainMoney) {
		this.defaultAccountRemainMoney = defaultAccountRemainMoney;
	}

	public String getActivityAccountRemainMoney() {
		return activityAccountRemainMoney;
	}

	public void setActivityAccountRemainMoney(String activityAccountRemainMoney) {
		this.activityAccountRemainMoney = activityAccountRemainMoney;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the remainMoney
	 */
	public BigDecimal getRemainMoney() {
		return remainMoney;
	}

	/**
	 * @param remainMoney the remainMoney to set
	 */
	public void setRemainMoney(BigDecimal remainMoney) {
		this.remainMoney = remainMoney;
	}

	/**
	 * @return the consumptionMoney
	 */
	public BigDecimal getConsumptionMoney() {
		return consumptionMoney;
	}

	/**
	 * @param consumptionMoney the consumptionMoney to set
	 */
	public void setConsumptionMoney(BigDecimal consumptionMoney) {
		this.consumptionMoney = consumptionMoney;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
