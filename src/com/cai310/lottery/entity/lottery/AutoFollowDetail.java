package com.cai310.lottery.entity.lottery;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.lottery.common.AutoFollowDetailState;
import com.cai310.lottery.common.Lottery;

/**
 * 自动跟单明细
 * 
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "AUTO_FOLLOW_DETAIL")
public class AutoFollowDetail extends IdEntity implements CreateMarkable {
	private static final long serialVersionUID = 2897452111436700063L;

	/** 跟单订单编号 */
	private Long followOrderId;

	/** 跟单人用户编号 */
	private Long followUserId;

	/** 跟单人用户名 */
	private String followUserName;

	/** 发起人的用户编号 */
	private Long sponsorUserId;

	/** 发起人的用户名 */
	private String sponsorUserName;
	/**
	 * 彩种类型
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private Lottery lotteryType;

	/** 玩法 */
	private Byte lotteryPlayType;

	/**
	 * 跟单明细状态
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private AutoFollowDetailState state;

	/** 跟单方案编号 */
	private Long schemeId;

	/** 期ID */
	private Long periodId;

	/** 期号 */
	private String periodNumber;

	/** 跟单金额 */
	private BigDecimal followCost;

	/** 备注 */
	private String remark;

	/** 创建时间 */
	private Date createTime;

	/**
	 * @return the createTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the {@link #followOrderId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getFollowOrderId() {
		return followOrderId;
	}

	/**
	 * @param followOrderId the {@link #followOrderId} to set
	 */
	public void setFollowOrderId(Long followOrderId) {
		this.followOrderId = followOrderId;
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
	 * @return the {@link #lotteryType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
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
	 * @return the {@link #state}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.AutoFollowDetailState"),
			@Parameter(name = EnumType.TYPE, value = AutoFollowDetailState.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public AutoFollowDetailState getState() {
		return state;
	}

	/**
	 * @param state the {@link #state} to set
	 */
	public void setState(AutoFollowDetailState state) {
		this.state = state;
	}

	/**
	 * @return the {@link #schemeId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getSchemeId() {
		return schemeId;
	}

	/**
	 * @param schemeId the {@link #schemeId} to set
	 */
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @return the {@link #periodId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getPeriodId() {
		return periodId;
	}

	/**
	 * @param periodId the {@link #periodId} to set
	 */
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	/**
	 * @return the {@link #periodNumber}
	 */
	@Column(nullable = false, updatable = false, length = 20)
	public String getPeriodNumber() {
		return periodNumber;
	}

	/**
	 * @param periodNumber the {@link #periodNumber} to set
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	/**
	 * @return the {@link #followCost}
	 */
	
	@Column(updatable = false)
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
	 * @return the {@link #remark}
	 */
	@Column(updatable = false)
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the {@link #remark} to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
				if (this.getLotteryPlayType() == 0) {
					return "排列3";
				} else {
					return "排列5";
				}
			}
		}
		return this.getLotteryType().getLotteryName();
	}
}