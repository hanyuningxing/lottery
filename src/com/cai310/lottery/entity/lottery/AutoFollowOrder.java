package com.cai310.lottery.entity.lottery;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.AutoFollowState;
import com.cai310.lottery.common.AutoFollowType;
import com.cai310.lottery.common.Lottery;

/**
 * 自动跟单订单
 * 
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "AUTO_FOLLOW_ORDER")
public class AutoFollowOrder extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = 2897452111436700063L;

	/** 发起人的用户编号 */
	private Long sponsorUserId;

	/** 发起人的用户名 */
	private String sponsorUserName;

	/** 跟单人的用户编号 */
	private Long followUserId;

	/** 跟单人的用户名 */
	private String followUserName;

	/**
	 * 彩种类型
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private Lottery lotteryType;

	/** 玩法 */
	private Byte lotteryPlayType;

	/**
	 * 跟单类型
	 * 
	 * @see com.cai310.lottery.common.AutoFollowType
	 */
	private AutoFollowType followType;

	/**
	 * 跟单订单状态
	 * 
	 * @see com.cai310.lottery.common.AutoFollowState
	 */
	private AutoFollowState state;

	/** 一期跟单金额上限 */
	private BigDecimal periodMaxFollowCost;

	/** 跟单百分比 */
	private Integer followPercent;

	/** 跟单金额 */
	private BigDecimal followCost;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;

	/** 版本号 */
	private Integer version;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	/**
	 * @return the version
	 */
	@Version
	@Column(nullable = false)
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return the {@link #sponsorUserId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getSponsorUserId() {
		return sponsorUserId;
	}

	/**
	 * @param sponsorUserId the {@link #sponsorUserId} to set
	 */
	public void setSponsorUserId(Long sponsorUserId) {
		this.sponsorUserId = sponsorUserId;
	}

	/**
	 * @return the {@link #sponsorUserName}
	 */
	@Column(nullable = false, updatable = false, length = 20)
	public String getSponsorUserName() {
		return sponsorUserName;
	}

	/**
	 * @param sponsorUserName the {@link #sponsorUserName} to set
	 */
	public void setSponsorUserName(String sponsorUserName) {
		this.sponsorUserName = sponsorUserName;
	}

	/**
	 * @return the {@link #followUserId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getFollowUserId() {
		return followUserId;
	}

	/**
	 * @param followUserId the {@link #followUserId} to set
	 */
	public void setFollowUserId(Long followUserId) {
		this.followUserId = followUserId;
	}

	/**
	 * @return the {@link #followUserName}
	 */
	@Column(nullable = false, updatable = false, length = 20)
	public String getFollowUserName() {
		return followUserName;
	}

	/**
	 * @param followUserName the {@link #followUserName} to set
	 */
	public void setFollowUserName(String followUserName) {
		this.followUserName = followUserName;
	}

	/**
	 * @return the {@link #lotteryType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = false)
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lotteryType the {@link #lotteryType} to set
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	/**
	 * @return the {@link #followType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.AutoFollowType"),
			@Parameter(name = EnumType.TYPE, value = AutoFollowType.SQL_TYPE) })
	@Column(nullable = false)
	public AutoFollowType getFollowType() {
		return followType;
	}

	/**
	 * @param followType the {@link #followType} to set
	 */
	public void setFollowType(AutoFollowType followType) {
		this.followType = followType;
	}

	/**
	 * @return the {@link #state}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.AutoFollowState"),
			@Parameter(name = EnumType.TYPE, value = AutoFollowState.SQL_TYPE) })
	@Column(nullable = false)
	public AutoFollowState getState() {
		return state;
	}

	/**
	 * @param state the {@link #state} to set
	 */
	public void setState(AutoFollowState state) {
		this.state = state;
	}

	/**
	 * @return the {@link #periodMaxFollowCost}
	 */
	
	@Column
	public BigDecimal getPeriodMaxFollowCost() {
		return periodMaxFollowCost;
	}

	/**
	 * @param periodMaxFollowCost the {@link #periodMaxFollowCost} to set
	 */
	public void setPeriodMaxFollowCost(BigDecimal periodMaxFollowCost) {
		this.periodMaxFollowCost = periodMaxFollowCost;
	}

	/**
	 * @return the {@link #followPercent}
	 */
	@Column
	public Integer getFollowPercent() {
		return followPercent;
	}

	/**
	 * @param followPercent the {@link #followPercent} to set
	 */
	public void setFollowPercent(Integer followPercent) {
		this.followPercent = followPercent;
	}

	/**
	 * @return the {@link #followCost}
	 */
	
	@Column
	public BigDecimal getFollowCost() {
		return followCost;
	}

	/**
	 * @param followCost the {@link #followCost} to set
	 */
	public void setFollowCost(BigDecimal followCost) {
		this.followCost = followCost;
	}

	/**
	 * @return {@link #lotteryPlayType}
	 */
	@Column
	public Byte getLotteryPlayType() {
		return lotteryPlayType;
	}

	/**
	 * @param lotteryPlayType the {@link #lotteryPlayType} to set
	 */
	public void setLotteryPlayType(Byte lotteryPlayType) {
		this.lotteryPlayType = lotteryPlayType;
	}

	@Transient
	public String getLotteryName() {
		if (this.getLotteryPlayType() != null) {
			switch (this.getLotteryType()) {
			case SFZC:
				return com.cai310.lottery.support.zc.PlayType.values()[this.getLotteryPlayType()].getText();
			case DCZC:
				return com.cai310.lottery.support.dczc.PlayType.values()[this.getLotteryPlayType()].getText();
			case PL:
				return com.cai310.lottery.support.pl.LotteryPlayType.values()[this.getLotteryPlayType()].getText();
			}
		}
		return this.getLotteryType().getLotteryName();
	}
}