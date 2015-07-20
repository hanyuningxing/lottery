package com.cai310.lottery.web.controller.user;

public class CommTraceForm {
	/**payWay */
	private Integer payWay;
	/** 银行名称 */
	private String bankName;
	
	/** 银行pic */
	private String bankPic;

	/** 充值金额 */
	private String amount;
	
	private String userName;

	private String alipayUrl;
	
	private String customOrderNo;
	
	private String phoneNumber;
	
	//用户名称
	private String userRealName;
	//银行卡号
	private String bankCard;
	//开户银行省份
	private String partBankProvince;
	//开户银行城市
	private String partBankCity;
	//开户支行名称
	private String partBankName;
	
	private String idCard;
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getPartBankProvince() {
		return partBankProvince;
	}

	public void setPartBankProvince(String partBankProvince) {
		this.partBankProvince = partBankProvince;
	}

	public String getPartBankCity() {
		return partBankCity;
	}

	public void setPartBankCity(String partBankCity) {
		this.partBankCity = partBankCity;
	}

	public String getPartBankName() {
		return partBankName;
	}

	public void setPartBankName(String partBankName) {
		this.partBankName = partBankName;
	}


	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getCustomOrderNo() {
		return customOrderNo;
	}

	public void setCustomOrderNo(String customOrderNo) {
		this.customOrderNo = customOrderNo;
	}

	/**
	 * @return {@link #bankName}
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the {@link #bankName} to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the alipayUrl
	 */
	public String getAlipayUrl() {
		return alipayUrl;
	}

	/**
	 * @param alipayUrl the alipayUrl to set
	 */
	public void setAlipayUrl(String alipayUrl) {
		this.alipayUrl = alipayUrl;
	}

	/**
	 * @return the bankPic
	 */
	public String getBankPic() {
		return bankPic;
	}

	/**
	 * @param bankPic the bankPic to set
	 */
	public void setBankPic(String bankPic) {
		this.bankPic = bankPic;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

}
