package com.cai310.lottery.web.controller.lottery;

import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;

public class SchemeQueryForm {

	/**
	 * 方案分享类型
	 * 
	 * @see com.cai310.lottery.common.ShareType
	 */
	private ShareType shareType;

	/** 方案发起人的用户名 */
	private String sponsorName;

	/** 方案号 */
	private String schemeNumber;

	/** 期号 */
	private Long periodId;

	private OrderType orderType;
	private StateType stateType;

	// 金额类型
	private Integer costType;
	// 方案类型
	private Integer chooseType;

	private Integer minSchemeCost;

	private Integer maxSchemeCost;

	private SchemeState schemeState;

	private String periodNumber;

	private boolean won;
	
	private SecretType secretType;
	
	private String playTypeName;
	
	private String lotteryType;
	/**
	 * @return {@link #schemeState}
	 */
	public SchemeState getSchemeState() {
		return schemeState;
	}

	/**
	 * @param schemeState the {@link #schemeState} to set
	 */
	public void setSchemeState(SchemeState schemeState) {
		this.schemeState = schemeState;
	}

	/**
	 * @return {@link #minSchemeCost}
	 */
	public Integer getMinSchemeCost() {
		return minSchemeCost;
	}

	/**
	 * @param minSchemeCost the {@link #minSchemeCost} to set
	 */
	public void setMinSchemeCost(Integer minSchemeCost) {
		this.minSchemeCost = minSchemeCost;
	}

	/**
	 * @return {@link #maxSchemeCost}
	 */
	public Integer getMaxSchemeCost() {
		return maxSchemeCost;
	}

	/**
	 * @param maxSchemeCost the {@link #maxSchemeCost} to set
	 */
	public void setMaxSchemeCost(Integer maxSchemeCost) {
		this.maxSchemeCost = maxSchemeCost;
	}

	/**
	 * @return {@link #shareType}
	 */
	public ShareType getShareType() {
		return shareType;
	}

	/**
	 * @param shareType the {@link #shareType} to set
	 */
	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}

	/**
	 * @return {@link #sponsorName}
	 */
	public String getSponsorName() {
		return sponsorName;
	}

	/**
	 * @param sponsorName the {@link #sponsorName} to set
	 */
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	/**
	 * @return {@link #schemeNumber}
	 */
	public String getSchemeNumber() {
		return schemeNumber;
	}

	/**
	 * @param schemeNumber the {@link #schemeNumber} to set
	 */
	public void setSchemeNumber(String schemeNumber) {
		this.schemeNumber = schemeNumber;
	}

	/**
	 * @return {@link #orderType}
	 */
	public OrderType getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the {@link #orderType} to set
	 */
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return {@link #stateType}
	 */
	public StateType getStateType() {
		return stateType;
	}

	/**
	 * @param stateType the {@link #stateType} to set
	 */
	public void setStateType(StateType stateType) {
		this.stateType = stateType;
	}

	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	public Integer getCostType() {
		return costType;
	}

	public void setCostType(Integer costType) {
		this.costType = costType;
	}

	public void setMinMaxCost() {
		if (null == this.costType)
			return;
		if (Integer.valueOf(0).equals(this.costType) || Integer.valueOf(3).equals(this.costType)) {
			// /0和3是不限
			return;
		} else if (Integer.valueOf(1).equals(this.costType)) {
			// 千元以下
			this.setMaxSchemeCost(1000 - 1);
		} else if (Integer.valueOf(2).equals(this.costType)) {
			// 千元或以上
			this.setMinSchemeCost(1000);
		} 

	}

	public Integer getChooseType() {
		return chooseType;
	}

	public void setChooseType(Integer chooseType) {
		this.chooseType = chooseType;
	}

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}

	public SecretType getSecretType() {
		return secretType;
	}

	public void setSecretType(SecretType secretType) {
		this.secretType = secretType;
	}

	public String getPlayTypeName() {
		return playTypeName;
	}

	public void setPlayTypeName(String playTypeName) {
		this.playTypeName = playTypeName;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	
	

}
