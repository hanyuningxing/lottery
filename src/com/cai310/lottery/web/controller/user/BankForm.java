package com.cai310.lottery.web.controller.user;

public class BankForm {

	/** 银行卡开户银行名称 */
	private String bankName;

	/** 银行卡号码 */
	private String bankCard;

	/** 投注密码,非银行卡密码 */
	private String password;

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
	 * @return {@link #bankCard}
	 */
	public String getBankCard() {
		return bankCard;
	}

	/**
	 * @param bankCard the {@link #bankCard} to set
	 */
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
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

}
