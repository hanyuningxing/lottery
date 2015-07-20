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
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PlatformInfo;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SubscriptionState;
import com.cai310.lottery.common.SubscriptionWay;
import com.cai310.lottery.exception.DataException;

/**
 * <b>方案认购记录基类.</b>
 * <p>
 * <strong>注：其中某些字段设置了[updatable = false], 执行更新操作时这些字段不会更新到数据库.</strong>
 * </p>
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SUBSCRIPTION")
@Entity
public class Subscription extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = -1423605093794280635L;

	/** 方案编号 */
	private Long schemeId;

	/** 用户编号 */
	private Long userId;

	/** 用户名 */
	private String userName;

	/** 认购金额 */
	private BigDecimal cost;

	/** 状态 */
	private SubscriptionState state;

	/** 认购方式 */
	private SubscriptionWay way;

	/** 分红 */
	private BigDecimal bonus;

	/** 分红是否已派送 */
	private boolean bonusSended;

	/** 版本号,用于实现乐观锁 */
	private Integer version;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;

	/** 预付款ID */
	private Long prepaymentId;

	/**
	 * 彩票类型
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private Lottery lotteryType;
	
	/** 方案来源*/
	private PlatformInfo platform;

	/**
	 * @return {@link #schemeId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getSchemeId() {
		return schemeId;
	}

	/**
	 * @param schemeId the schemeId to set
	 */
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	/**
	 * @return {@link #userId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return {@link #userName}
	 */
	@Column(nullable = false, updatable = false, length = 20)
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return {@link #cost}
	 */
	
	@Column(nullable = false, updatable = false ,scale = 4, precision = 18)
	public BigDecimal getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	/**
	 * @return {@link #state}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SubscriptionState"),
			@Parameter(name = EnumType.TYPE, value = SubscriptionState.SQL_TYPE) })
	@Column(nullable = false)
	public SubscriptionState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(SubscriptionState state) {
		this.state = state;
	}

	/**
	 * @return {@link #way}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SubscriptionWay"),
			@Parameter(name = EnumType.TYPE, value = SubscriptionWay.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public SubscriptionWay getWay() {
		return way;
	}

	/**
	 * @param way the way to set
	 */
	public void setWay(SubscriptionWay way) {
		this.way = way;
	}

	/**
	 * @return {@link #bonus}
	 */
	
	@Column(scale = 4, precision = 18)
	public BigDecimal getBonus() {
		return bonus;
	}

	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}

	/**
	 * @return {@link #bonusSended}
	 */
	@Column(nullable = false)
	public boolean isBonusSended() {
		return bonusSended;
	}

	/**
	 * @param bonusSended the bonusSended to set
	 */
	public void setBonusSended(boolean bonusSended) {
		this.bonusSended = bonusSended;
	}

	/**
	 * @return {@link #version}
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
	 * 彩票类型
	 * 
	 * @return {@link #lotteryType}
	 * @see com.cai310.lottery.common.Lottery
	 */
	@Column(nullable = false, updatable = false)
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * 设置彩票类型
	 * 
	 * @param lotteryType {@link #lotteryType}
	 * @see com.cai310.lottery.common.Lottery
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	/**
	 * @return {@link #prepaymentId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getPrepaymentId() {
		return prepaymentId;
	}

	/**
	 * @param prepaymentId the {@link #prepaymentId} to set
	 */
	public void setPrepaymentId(Long prepaymentId) {
		this.prepaymentId = prepaymentId;
	}

	/* --------------- logic mehtod --------------- */

	@Transient
	public boolean isCanCancel() {
		return getWay() != SubscriptionWay.INITIATE && getState() == SubscriptionState.NORMAL;
	}
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.PlatformInfo"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column(updatable = false)
	public PlatformInfo getPlatform() {
		return platform;
	}

	public void setPlatform(PlatformInfo platform) {
		this.platform = platform;
	}
	/**
	 * 撤销认购
	 * 
	 * @throws DataException
	 */
	public void cancel() throws DataException {
		if (getWay() == SubscriptionWay.INITIATE)
			throw new DataException("发起时认购的记录不能撤销，只能撤销方案.");
		if (getState() != SubscriptionState.NORMAL)
			throw new DataException("该认购当前不能撤销.");

		setState(SubscriptionState.CANCEL);
	}

	protected void reset() {
		this.setBonus(null);
		this.setCreateTime(new Date());
		this.setState(SubscriptionState.NORMAL);
		this.setBonusSended(false);
	}
    /////用于追号重置。追号金额=方案金额。修改2011-4-12  cyy-c
	public void reset(Scheme scheme, long prePayId) {
		this.reset();
        this.setCost(BigDecimal.valueOf(scheme.getSchemeCost()));
		this.setId(null);
		this.setSchemeId(scheme.getId());
		this.setPrepaymentId(prePayId);
	}
}
