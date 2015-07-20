package com.cai310.lottery.dto.lottery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.common.PlatformInfo;

/**
 * 方案发起的数据传输对象基类
 */
public class SchemeDTO implements Serializable {
	private static final long serialVersionUID = -1025920991877401793L;
    private Date officialEndTime;
	/** 期编号 */
	private Long periodId;

	/** 方案发起人的用户编号 */
	private Long sponsorId;

	/** 方案描述 */
	private String description;

	/** 方案注数（单倍注数） */
	private Integer units;

	/** 方案倍数 */
	private Integer multiple;

	/** 方案金额 */
	private Integer schemeCost;

	/**
	 * 方案投注的方式
	 * 
	 * @see com.cai310.lottery.common.SalesMode
	 */
	private SalesMode mode;

	/**
	 * 方案分享类型
	 * 
	 * @see com.cai310.lottery.common.ShareType
	 */
	private ShareType shareType;

	/**
	 * 方案保密类型
	 * 
	 * @see com.cai310.lottery.common.SecretType
	 */
	private SecretType secretType;

	/**
	 * 方案认购许可类型
	 * 
	 * @see com.cai310.lottery.common.SubscriptionLicenseType
	 */
	private SubscriptionLicenseType subscriptionLicenseType;

	/** 方案认购密码 */
	private String subscriptionPassword;

	/** 方案最低认购金额 */
	private BigDecimal minSubscriptionCost = BigDecimal.valueOf(1.00);

	/** 方案发起人的佣金率，值应小于1 */
	private Float commissionRate;

	/** 发起人认购金额 */
	private BigDecimal sponsorSubscriptionCost;

	/** 发起人保底金额 */
	private BigDecimal sponsorBaodiCost;

	/** 方案内容 */
	private String content;

	/** 接票ID(订单号)*/
	private String orderId;
	
	/** 是否接票*/
	private Boolean isTicket = Boolean.FALSE;
	
	private PlatformInfo platform = PlatformInfo.WEB;
	

	/** 委托方方案号 一个方案号对应一个或者多个订单号 */
	private String outOrderNumber;
	/**
	 * @return {@link #periodId}
	 */
	public Long getPeriodId() {
		return periodId;
	}

	/**
	 * @param periodId the periodId to set
	 */
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	/**
	 * @return {@link #sponsorId}
	 */
	public Long getSponsorId() {
		return sponsorId;
	}

	/**
	 * @param sponsorId the sponsorId to set
	 */
	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	/**
	 * @return {@link #description}
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return {@link #units}
	 */
	public Integer getUnits() {
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(Integer units) {
		this.units = units;
	}

	/**
	 * @return {@link #multiple}
	 */
	public Integer getMultiple() {
		return multiple;
	}

	/**
	 * @param multiple the multiple to set
	 */
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	/**
	 * @return {@link #schemeCost}
	 */
	public Integer getSchemeCost() {
		return schemeCost;
	}

	/**
	 * @param schemeCost the schemeCost to set
	 */
	public void setSchemeCost(Integer schemeCost) {
		this.schemeCost = schemeCost;
	}

	/**
	 * @return {@link #mode}
	 */
	public SalesMode getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(SalesMode mode) {
		this.mode = mode;
	}

	/**
	 * @return {@link #shareType}
	 */
	public ShareType getShareType() {
		return shareType;
	}

	/**
	 * @param shareType the shareType to set
	 */
	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}

	/**
	 * @return {@link #secretType}
	 */
	public SecretType getSecretType() {
		return secretType;
	}

	/**
	 * @param secretType the secretType to set
	 */
	public void setSecretType(SecretType secretType) {
		this.secretType = secretType;
	}

	/**
	 * @return {@link #subscriptionLicenseType}
	 */
	public SubscriptionLicenseType getSubscriptionLicenseType() {
		return subscriptionLicenseType;
	}

	/**
	 * @param subscriptionLicenseType the subscriptionLicenseType to set
	 */
	public void setSubscriptionLicenseType(SubscriptionLicenseType subscriptionLicenseType) {
		this.subscriptionLicenseType = subscriptionLicenseType;
	}

	/**
	 * @return {@link #subscriptionPassword}
	 */
	public String getSubscriptionPassword() {
		return subscriptionPassword;
	}

	/**
	 * @param subscriptionPassword the subscriptionPassword to set
	 */
	public void setSubscriptionPassword(String subscriptionPassword) {
		this.subscriptionPassword = subscriptionPassword;
	}

	/**
	 * @return {@link #minSubscriptionCost}
	 */
	public BigDecimal getMinSubscriptionCost() {
		return minSubscriptionCost;
	}

	/**
	 * @param minSubscriptionCost the minSubscriptionCost to set
	 */
	public void setMinSubscriptionCost(BigDecimal minSubscriptionCost) {
		this.minSubscriptionCost = minSubscriptionCost;
	}

	/**
	 * @return {@link #commissionRate}
	 */
	public Float getCommissionRate() {
		return commissionRate;
	}

	/**
	 * @param commissionRate the {@link #commissionRate} to set
	 */
	public void setCommissionRate(Float commissionRate) {
		this.commissionRate = commissionRate;
	}

	/**
	 * @return {@link #sponsorSubscriptionCost}
	 */
	public BigDecimal getSponsorSubscriptionCost() {
		return sponsorSubscriptionCost;
	}

	/**
	 * @param sponsorSubscriptionCost the {@link #sponsorSubscriptionCost} to
	 *            set
	 */
	public void setSponsorSubscriptionCost(BigDecimal sponsorSubscriptionCost) {
		this.sponsorSubscriptionCost = sponsorSubscriptionCost;
	}

	/**
	 * @return {@link #sponsorBaodiCost}
	 */
	public BigDecimal getSponsorBaodiCost() {
		return sponsorBaodiCost;
	}

	/**
	 * @param sponsorBaodiCost the {@link #sponsorBaodiCost} to set
	 */
	public void setSponsorBaodiCost(BigDecimal sponsorBaodiCost) {
		this.sponsorBaodiCost = sponsorBaodiCost;
	}

	/**
	 * @return {@link #content}
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the {@link #content} to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOfficialEndTime() {
		return officialEndTime;
	}

	public void setOfficialEndTime(Date officialEndTime) {
		this.officialEndTime = officialEndTime;
	}

	public Boolean getIsTicket() {
		return isTicket;
	}

	public void setIsTicket(Boolean isTicket) {
		this.isTicket = isTicket;
	}

	public PlatformInfo getPlatform() {
		return platform;
	}

	public void setPlatform(PlatformInfo platform) {
		this.platform = platform;
	}

	public String getOutOrderNumber() {
		return outOrderNumber;
	}

	public void setOutOrderNumber(String outOrderNumber) {
		this.outOrderNumber = outOrderNumber;
	}
}
