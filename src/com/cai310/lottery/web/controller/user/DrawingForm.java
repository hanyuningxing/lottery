package com.cai310.lottery.web.controller.user;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.common.DrawingWay;

public class DrawingForm {
	//提款金额
	private String drawingMoney;
	//提款密码
	private String userPassword;
	//可提金额
	private BigDecimal resultMoney;
	
	private String defaultAccountRemainMoney; //现金账户余额
	
	private String activityAccountRemainMoney; //彩金账户余额

	//提款银行名称
	private String bankName;
	//银行卡号
	private String bankCard;
	//开户银行省份
	private String partBankProvince;
	//开户银行城市
	private String partBankCity;
	//开户支行名称
	private String partBankName;
	//用户名称
	private String userRealName;
	
	private DrawingWay drawingWay;

	private boolean  canUpdate;


	public boolean isCanUpdate() {
		return canUpdate;
	}
	public void setCanUpdate(boolean canUpdate) {
		this.canUpdate = canUpdate;
	}
	private String threePartyName;
	private String threePartyNum;
	
	private String aliDrawingMoney;
	
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	public String getDrawingMoney() {
		return drawingMoney;
	}
	public void setDrawingMoney(String drawingMoney) {
		this.drawingMoney = drawingMoney;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public BigDecimal getResultMoney() {
		return resultMoney;
	}
	public void setResultMoney(BigDecimal resultMoney) {
		this.resultMoney = resultMoney;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	
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

	public String getThreePartyNum() {
		return threePartyNum;
	}
	public void setThreePartyNum(String threePartyNum) {
		this.threePartyNum = threePartyNum;
	}
	public String getAliDrawingMoney() {
		return aliDrawingMoney;
	}
	public void setAliDrawingMoney(String aliDrawingMoney) {
		this.aliDrawingMoney = aliDrawingMoney;
	}
	
	public DrawingWay getDrawingWay() {
		return drawingWay;
	}
	public void setDrawingWay(DrawingWay drawingWay) {
		this.drawingWay = drawingWay;
	}
	public String getThreePartyName() {
		return threePartyName;
	}
	public void setThreePartyName(String threePartyName) {
		this.threePartyName = threePartyName;
	}
	public String getUserRealNameString() {
		if(StringUtils.isNotBlank(this.userRealName)){
			if(this.userRealName.length()==1){
				return this.userRealName;
			}else if(this.userRealName.length()==2){
				return this.userRealName.substring(0, 1)+"*";
			}else if(this.userRealName.length()==3){
				return this.userRealName.substring(0, 1)+"*"+this.userRealName.substring(2);
			}else if(this.userRealName.length()>3){
				return this.userRealName.substring(0, 1)+"*"+this.userRealName.substring(2);
			}
		}
		return null;
	}
	
}
