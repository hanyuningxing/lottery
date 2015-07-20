package com.cai310.lottery.dto.user;

import java.math.BigDecimal;

import com.cai310.lottery.common.ActivityType;
import com.cai310.lottery.utils.BigDecimalUtil;

public class UserInfoDTO {
	private String partBankProvince;
	
	private String partBankCity;
	
	private String partBankName;
	/** 银行卡开户银行名称 */
	private String bankName;

	/** 银行卡号码 */
	private String bankCard;
	
	/** 真实姓名 */
	private String realName;

	/** 电话号码 */
	private String mobile;

	/** 身份证 */
	private String idCard;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
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

	
}
