package com.cai310.lottery.entity.lottery.dczc;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.entity.lottery.SchemeWonInfo;
import com.cai310.lottery.support.dczc.PassMode;
import com.cai310.lottery.support.dczc.PassType;

/**
 * 北单足彩方案过关信息抽象类.
 * 
 */
@MappedSuperclass
public abstract class BdzcSchemeWonInfo extends SchemeWonInfo {

	private static final long serialVersionUID = 1L;

	/** 期编号 */
	private Long periodId;

	/** 方案发起人的用户编号 */
	private Long sponsorId;

	/** 方案发起人的用户名 */
	private String sponsorName;

	/** 方案注数（单倍注数） */
	private Integer units;

	/** 倍数 */
	private Integer multiple;

	/** 方案金额 */
	private Integer schemeCost;

	/** 方案奖金 */
	private BigDecimal schemePrize;

	/** 方案状态 */
	private SchemeState state;

	/** 方案分享类型 */
	private ShareType shareType;

	/** 选择的场次 */
	private int betCount;

	/** 全中的注数 */
	private int wonCount;

	/** 命中场次 */
	private Integer passcount;

	/** 比赛状态 */
	private WinningUpdateStatus finsh;

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.WinningUpdateStatus"),
			@Parameter(name = EnumType.TYPE, value = PassMode.SQL_TYPE) })
	@Column(nullable = false)
	public WinningUpdateStatus getFinsh() {
		return finsh;
	}

	public void setFinsh(WinningUpdateStatus finsh) {
		this.finsh = finsh;
	}

	/**
	 * 过关模式
	 * 
	 * @see com.cai310.lottery.support.dczc.PassMode
	 * */
	private PassMode passMode;

	/**
	 * @return {@link #passMode}
	 */

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.support.dczc.PassMode"),
			@Parameter(name = EnumType.TYPE, value = PassMode.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public PassMode getPassMode() {
		return passMode;
	}

	/**
	 * @param passMode the {@link #passMode} to set
	 */
	public void setPassMode(PassMode passMode) {
		this.passMode = passMode;
	}

	/** 过关方式 */
	private int passType;

	/**
	 * @return {@link #passType}
	 */
	public int getPassType() {
		return passType;
	}

	/**
	 * @param passType the {@link #passType} to set
	 */
	public void setPassType(int passType) {
		this.passType = passType;
	}

	@Transient
	public List<PassType> getPassTypes() {
		return PassType.getPassTypes(passType);
	}

	/**
	 * 方案投注的方式
	 */
	private SalesMode mode;

	/**
	 * @return {@link #periodId}
	 */
	@Column(nullable = false, updatable = false)
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
	@Column(nullable = false, updatable = false)
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
	 * @return {@link #sponsorName}
	 */
	@Column(nullable = false, length = 20, updatable = false)
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
	@Column(nullable = false, updatable = false)
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
	@Column(name = "sales_mode", nullable = false, updatable = false)
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

	public ShareType getShareType() {
		return shareType;
	}

	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}

	public int getBetCount() {
		return betCount;
	}

	public void setBetCount(int betCount) {
		this.betCount = betCount;
	}

	public int getWonCount() {
		return wonCount;
	}

	public void setWonCount(int wonCount) {
		this.wonCount = wonCount;
	}

	/**
	 * @return the passcount
	 */
	@Column(name = "passcount")
	public Integer getPasscount() {
		return passcount;
	}

	/**
	 * @param passcount the passcount to set
	 */
	public void setPasscount(Integer passcount) {
		this.passcount = passcount;
	}

}
