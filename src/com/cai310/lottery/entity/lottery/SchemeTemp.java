package com.cai310.lottery.entity.lottery;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;

/**
 * 
 * 免费保存方案实体类.
 * 
 */
@MappedSuperclass
public abstract class SchemeTemp extends IdEntity implements CreateMarkable {
	
	private static final long serialVersionUID = -8707345951227333149L;
		
	/** 期编号 */
	private Long periodId;

	/** 期号 */
	private String periodNumber;

	/** 方案发起人的用户编号 */
	private Long sponsorId;

	/** 方案发起人的用户名 */
	private String sponsorName;
	
	/** 方案内容是否已上传 */
	private boolean uploaded;

	/** 方案内容上传时间 */
	private Date uploadTime;

	/** 方案注数（单倍注数） */
	private Integer units;

	/** 倍数 */
	private Integer multiple;

	/** 方案金额 */
	protected Integer schemeCost;

	/** 方案进度 */
	private Float progressRate;
	
	/** 方案内容 */
	private String content;
	
	/** 彩种类型*/
	private Lottery lotteryType;

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
	
	/** 最小认购金额 */
	private BigDecimal minSubscriptionCost;
	
	/** 创建时间 */
	private Date createTime;
	
	
	@Column(nullable = false, updatable = false)
	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	@Column(nullable = false, length = 20, updatable = false)
	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	@Column(nullable = false, updatable = false)
	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	@Column(nullable = false, length = 20, updatable = false)
	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	
	@Column(nullable = false)
	public boolean isUploaded() {
		return uploaded;
	}

	public void setUploaded(boolean uploaded) {
		this.uploaded = uploaded;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getUploadTime() {
		return uploadTime;
	}
	
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	@Column
	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}
	
	@Column
	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	
	@Column(nullable = false, updatable = false)
	public Integer getSchemeCost() {
		return schemeCost;
	}

	public void setSchemeCost(Integer schemeCost) {
		this.schemeCost = schemeCost;
	}

	@Column
	public Float getProgressRate() {
		return progressRate;
	}

	public void setProgressRate(Float progressRate) {
		this.progressRate = progressRate;
	}

	@Lob
	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SalesMode"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column(name = "sales_mode", nullable = false, updatable = false)
	public SalesMode getMode() {
		return mode;
	}

	public void setMode(SalesMode mode) {
		this.mode = mode;
	}

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.ShareType"),
			@Parameter(name = EnumType.TYPE, value = ShareType.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public ShareType getShareType() {
		return shareType;
	}

	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SecretType"),
			@Parameter(name = EnumType.TYPE, value = SecretType.SQL_TYPE) })
	@Column(nullable = false)
	public SecretType getSecretType() {
		return secretType;
	}

	public void setSecretType(SecretType secretType) {
		this.secretType = secretType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column
	public BigDecimal getMinSubscriptionCost() {
		return minSubscriptionCost;
	}

	public void setMinSubscriptionCost(BigDecimal minSubscriptionCost) {
		this.minSubscriptionCost = minSubscriptionCost;
	}
	
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(name = "lottery", nullable = false, updatable = false)
	public Lottery getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}
	
	@Transient
	public SchemeState getState() {
		return SchemeState.CANCEL;
	}
}
