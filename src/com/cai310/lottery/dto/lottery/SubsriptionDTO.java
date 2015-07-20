package com.cai310.lottery.dto.lottery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.common.PlatformInfo;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.support.zc.PlayType;

/**
 * 方案的数据传输对象基类
 */
public class SubsriptionDTO implements Serializable {

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 6966952595119831593L;
	/** 最小认购金额 */
	private BigDecimal minSubscriptionCost;
	/** 方案注数（单倍注数） */
	private Integer units;

	/** 倍数 */
	private Integer multiple;

	/** 方案金额 */
	private Integer schemeCost;

	/** 彩种类型 */
	private Lottery lotteryType;
	/** 方案ID */
	private Long schemeId;
	/** 方案ID */
	private String periodNumber;

	private Long sponsorId;

	private String sponsorName;


	private SchemeState schemeState;
	
	/** 方案出票状态 */
	private SchemePrintState schemePrintState;
	
	/** 方案进度 */
	private Float progressRate;

	/** 已认购金额 */
	private BigDecimal subscribedCost;

	/** 保底金额 */
	private BigDecimal baodiCost;
	/**
	 * 方案投注的方式
	 * 
	 * @see com.cai310.lottery.common.SalesMode
	 */
	private SalesMode mode;
	/**
	 * 方案保密类型
	 * 
	 * @see com.cai310.lottery.common.SecretType
	 */
	private SecretType secretType;
	/** 方案发起人的佣金率，值应小于1 */
	private Float commissionRate;
	
	public Byte playTypeOrdinal;
	
	public Long getSponsorId() {
		return sponsorId;
	}
	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}
	public Integer getUnits() {
		return units;
	}
	public void setUnits(Integer units) {
		this.units = units;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public Integer getSchemeCost() {
		return schemeCost;
	}
	public void setSchemeCost(Integer schemeCost) {
		this.schemeCost = schemeCost;
	}
	public Lottery getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}
	public Long getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}
	public String getPeriodNumber() {
		return periodNumber;
	}
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}
	public String getSponsorName() {
		return sponsorName;
	}
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	public SchemeState getSchemeState() {
		return schemeState;
	}
	public void setSchemeState(SchemeState schemeState) {
		this.schemeState = schemeState;
	}
	public SchemePrintState getSchemePrintState() {
		return schemePrintState;
	}
	public void setSchemePrintState(SchemePrintState schemePrintState) {
		this.schemePrintState = schemePrintState;
	}
	public Float getProgressRate() {
		return progressRate;
	}
	public void setProgressRate(Float progressRate) {
		this.progressRate = progressRate;
	}
	public BigDecimal getSubscribedCost() {
		return subscribedCost;
	}
	public void setSubscribedCost(BigDecimal subscribedCost) {
		this.subscribedCost = subscribedCost;
	}
	public BigDecimal getBaodiCost() {
		return baodiCost;
	}
	public void setBaodiCost(BigDecimal baodiCost) {
		this.baodiCost = baodiCost;
	}
	public SalesMode getMode() {
		return mode;
	}
	public void setMode(SalesMode mode) {
		this.mode = mode;
	}
	public SecretType getSecretType() {
		return secretType;
	}
	public void setSecretType(SecretType secretType) {
		this.secretType = secretType;
	}
	public Float getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(Float commissionRate) {
		this.commissionRate = commissionRate;
	}
	public Byte getPlayTypeOrdinal() {
		return playTypeOrdinal;
	}
	public void setPlayTypeOrdinal(Byte playTypeOrdinal) {
		this.playTypeOrdinal = playTypeOrdinal;
	}
	public BigDecimal getMinSubscriptionCost() {
		return minSubscriptionCost;
	}
	public void setMinSubscriptionCost(BigDecimal minSubscriptionCost) {
		this.minSubscriptionCost = minSubscriptionCost;
	}

	
}
