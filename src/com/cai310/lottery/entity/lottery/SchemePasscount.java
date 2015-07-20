package com.cai310.lottery.entity.lottery;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.ShareType;

@MappedSuperclass
public abstract class SchemePasscount implements Serializable {
	private static final long serialVersionUID = 6217335316629493571L;

	protected Long schemeId;
	/** 期编号 */
	protected Long periodId;
	/** 期号 */
	protected String periodNumber;
	/** 方案发起人的用户编号 */
	protected Long sponsorId;
	/** 方案发起人的用户名 */
	protected String sponsorName;
	/** 方案注数（单倍注数） */
	protected Integer units;
	/** 倍数 */
	protected Integer multiple;
	/** 方案金额 */
	protected Integer schemeCost;
	/** 方案状态 */
	protected SchemeState state;
	/**方案投注的方式 */
	protected SalesMode mode;
	
	/** 方案奖金 */
	protected BigDecimal schemePrize;
	
	/**方案分享类型*/
	protected ShareType shareType;
	
	protected Integer version;
	
	@Transient
	abstract public String getSchemeNumber();

	
	@Id
	@Column(name = "schemeId", nullable = false)
	public Long getSchemeId() {
		return this.schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @return {@link #shareType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.ShareType"),
			@Parameter(name = EnumType.TYPE, value = ShareType.SQL_TYPE) })
	@Column(nullable = false)
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
	 * @return {@link #periodId}
	 */
	@Column(nullable = false)
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
	@Column(nullable = false)
	public Long getSponsorId() {
		return sponsorId;
	}

	/**
	 * @return {@link #periodNumber}
	 */
	@Column(nullable = false, length = 20, updatable = false)
	public String getPeriodNumber() {
		return periodNumber;
	}

	/**
	 * @param periodNumber the periodNumber to set
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}
	
	/**
	 * @param sponsorId the sponsorId to set
	 */
	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	/**
	 * @return {@link #sponsorName}
	 */
	@Column(nullable = false, length = 20)
	public String getSponsorName() {
		return sponsorName;
	}

	/**
	 * @param sponsorName the sponsorName to set
	 */
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	/**
	 * @return {@link #units}
	 */
	@Column
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
	@Column
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
	
	@Column(nullable = false)
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
	 * @return {@link #state}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SchemeState"),
			@Parameter(name = EnumType.TYPE, value = SchemeState.SQL_TYPE) })
	@Column(nullable = false)
	public SchemeState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(SchemeState state) {
		this.state = state;
	}

	/**
	 * @return {@link #mode}
	 */

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SalesMode"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column(name = "SALES_MODE", nullable = false)
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
	 * @return the schemePrize
	 */
	
	@Column
	public BigDecimal getSchemePrize() {
		return schemePrize;
	}

	/**
	 * @param schemePrize the schemePrize to set
	 */
	public void setSchemePrize(BigDecimal schemePrize) {
		this.schemePrize = schemePrize;
	}


	
	
	@Version
	@Column(name = "version", nullable = false)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	

	
}
