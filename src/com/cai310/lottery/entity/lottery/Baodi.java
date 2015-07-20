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
import com.cai310.lottery.common.BaodiState;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PlatformInfo;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;

/**
 * <b>方案保底基类.</b>
 * <p>
 * <strong>注：其中某些字段设置了[updatable = false], 执行更新操作时这些字段不会更新到数据库.</strong>
 * </p>
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "BAODI")
@Entity
public class Baodi extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = 873816390034580466L;

	/** 方案ID */
	private Long schemeId;

	/** 用户ID */
	private Long userId;

	/** 用户名 */
	private String userName;

	/** 保底金额 */
	private BigDecimal cost;

	/** 版本号,用于实现乐观锁 */
	private Integer version;

	/** 预付款ID */
	private Long prepaymentId;

	/**
	 * 彩票类型
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private Lottery lotteryType;

	/**
	 * 保底状态
	 * 
	 * @see com.cai310.lottery.common.BaodiState
	 */
	private BaodiState state;

	/** 已使用,转为保底的金额 */
	private BigDecimal usedCost;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
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

	/**
	 * @return {@link #cost}
	 */
	
	@Column(nullable = false, updatable = false, scale = 4, precision = 18)
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
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.BaodiState"),
			@Parameter(name = EnumType.TYPE, value = BaodiState.SQL_TYPE) })
	@Column(nullable = false)
	public BaodiState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(BaodiState state) {
		this.state = state;
	}

	/**
	 * @return {@link #usedCost}
	 */
	
	@Column(precision = 18, scale = 4)
	public BigDecimal getUsedCost() {
		return usedCost;
	}

	/**
	 * @param usedCost the usedCost to set
	 */
	public void setUsedCost(BigDecimal usedCost) {
		this.usedCost = usedCost;
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
	 * 彩票类型
	 * 
	 * @return {@link #lotteryType}
	 * @see com.cai310.lottery.common.Lottery
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
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

	/* ----------------- logic method ----------------- */

	@Transient
	public boolean isCancel() {
		return getState() == BaodiState.NORMAL;
	}

	/**
	 * 撤销保底
	 * 
	 * @throws DataException
	 */
	public void cancel() throws DataException {
		if (!isCancel())
			throw new ServiceException("该保底当前不能撤销.");

		setState(BaodiState.CANCEL);
	}

	/**
	 * 使用保底金额
	 * 
	 * @param useCost 使用的保底金额
	 * @return 剩余的保底金额
	 * @throws DataException
	 */
	public BigDecimal useBaodi(BigDecimal useCost) throws DataException {
		if (getState() != BaodiState.NORMAL)
			throw new ServiceException("该保底当前不能使用.");
		if (useCost == null || useCost.doubleValue() <= 0)
			throw new DataException("使用的保底金额不能为空、小于或等于0.");
		else if (useCost.compareTo(this.cost) > 0)
			throw new DataException("使用的保底金额不能大于该保底的实际金额.");

		this.usedCost = useCost;
		this.state = BaodiState.USED;

		BigDecimal remainBaodiCost = this.cost.subtract(this.usedCost);
		return remainBaodiCost;
	}
}
