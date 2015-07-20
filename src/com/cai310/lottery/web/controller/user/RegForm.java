package com.cai310.lottery.web.controller.user;

import com.cai310.lottery.common.ExternalNetworkType;

public class RegForm {
	private String userName;
	private String password;
	private String confirmPassword;
	/** 真实姓名 */
	private String realName;
	/** 身份证 */
	private String idCard;

	/** 电话号码 */
	private String phoneNumber;

	/** 满18岁 */
	private String up18;

	/** 电子邮箱 */
	private String email;
	/**
	 * 验证邮箱标识
	 */
	private int validatedEmail;
	/**
	 * 验证电话标识
	 */
	private int validatedPhoneNo;	
	/**
	 * 外网注册ID
	 */
	private String outSystemUserID;
	/**
	 * 外网注册类型
	 */
	private ExternalNetworkType networkType;
	
	private String oldpassword;
	
	/**点位小数点前面的*/
	private Integer rebate1;
	
	/**点位小数点后面的*/
	private Integer rebate2;
	
	public ExternalNetworkType getNetworkType() {
		return networkType;
	}

	public void setNetworkType(ExternalNetworkType networkType) {
		this.networkType = networkType;
	}

	public String getOutSystemUserID() {
		return outSystemUserID;
	}

	public void setOutSystemUserID(String outSystemUserID) {
		this.outSystemUserID = outSystemUserID;
	}

	/**
	 * @return {@link #userName}
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the {@link #userName} to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

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
	 * @return {@link #confirmPassword}
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the {@link #confirmPassword} to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	/**
	 * @return the idCard
	 */
	public String getIdCard() {
		return idCard;
	}

	/**
	 * @param idCard the idCard to set
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the up18
	 */
	public String getUp18() {
		return up18;
	}

	/**
	 * @param up18 the up18 to set
	 */
	public void setUp18(String up18) {
		this.up18 = up18;
	}

	public int getValidatedEmail() {
		return validatedEmail;
	}

	public void setValidatedEmail(int validatedEmail) {
		this.validatedEmail = validatedEmail;
	}

	public int getValidatedPhoneNo() {
		return validatedPhoneNo;
	}

	public void setValidatedPhoneNo(int validatedPhoneNo) {
		this.validatedPhoneNo = validatedPhoneNo;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public Integer getRebate1() {
		return rebate1;
	}

	public void setRebate1(Integer rebate1) {
		this.rebate1 = rebate1;
	}

	public Integer getRebate2() {
		return rebate2;
	}

	public void setRebate2(Integer rebate2) {
		this.rebate2 = rebate2;
	}
	
}
