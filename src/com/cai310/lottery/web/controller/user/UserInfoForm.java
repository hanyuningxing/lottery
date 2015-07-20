package com.cai310.lottery.web.controller.user;

public class UserInfoForm {
	//////////////首页用其他不用////////////////
	private String userName;
	
	//////////////首页用其他不用////////////////
	private String defaultAccountRemainMoney; //现金账户余额

	/** 真实姓名 */
	private String realName;

	/** 电话号码 */
	private String phoneNumber;

	/** 电子邮箱 */
	private String email;

	/** QQ号码 */
	private String qq;

	/** 联系地址 */
	private String address;

	/** 邮政编码 */
	private String postcode;

	/** 身份证 */
	private String idCard;
	/**标志是从用户充值过来*/
	private String from;

	/**
	 * @return {@link #realName}
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the {@link #realName} to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return {@link #phoneNumber}
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the {@link #phoneNumber} to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return {@link #email}
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the {@link #email} to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return {@link #qq}
	 */
	public String getQq() {
		return qq;
	}

	/**
	 * @param qq the {@link #qq} to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * @return {@link #address}
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the {@link #address} to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return {@link #postcode}
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @param postcode the {@link #postcode} to set
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * @return {@link #idCard}
	 */
	public String getIdCard() {
		return idCard;
	}

	/**
	 * @param idCard the {@link #idCard} to set
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
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

	public String getDefaultAccountRemainMoney() {
		return defaultAccountRemainMoney;
	}

	public void setDefaultAccountRemainMoney(String defaultAccountRemainMoney) {
		this.defaultAccountRemainMoney = defaultAccountRemainMoney;
	}
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}



}
