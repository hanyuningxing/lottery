package com.cai310.lottery.entity.user;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PrepaymentState;
import com.cai310.lottery.common.PrepaymentType;
import com.cai310.lottery.exception.DataException;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

/**
 * 预付款项.
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "PREPAYMENT")
@Entity
public class Prepayment extends IdEntity implements CreateMarkable, UpdateMarkable {
	
	
	private static final long serialVersionUID = 4940285342821976442L;

	/** 交易ID */
	private Long transactionId;

	/** 用户ID */
	private Long userId;

	/** 预付款金额 */
	private BigDecimal amount;

	/**
	 * 预付款类型
	 * 
	 * @see com.cai310.lottery.common.PrepaymentType
	 */
	private PrepaymentType type;

	/**
	 * 预付款状态
	 * 
	 * @see com.cai310.lottery.common.PrepaymentState
	 */
	private PrepaymentState state;

	/** 版本号,用于实现乐观锁 */
	private Integer version;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;

	/** 预付款创建的资金明细ID */
	private Long createFundDetailId;

	/** 预付款取消的资金明细ID */
	private Long cancelFundDetailId;

	/** 已转化的金额 */
	private BigDecimal transferredCost;
	

	/** 彩种类型 */
	private Lottery lotteryType;

	/* --------------------- logic method --------------------- */

	/**
	 * 转化预付款
	 * 
	 * @param cost 转化的金额
	 * @throws DataException
	 */
	public void transfer(BigDecimal cost) throws DataException {
		if (cost == null || cost.doubleValue() <= 0)
			throw new DataException("转换金额不能为空、小于或等于0.");
		else if (cost.compareTo(this.amount) > 0)
			throw new DataException("转换金额不能大于预付款金额.");

		this.amount = this.amount.subtract(cost);
		this.transferredCost = (this.transferredCost == null) ? cost : this.transferredCost.add(cost);
		if (this.amount.compareTo(BigDecimal.ZERO) == 0)
			this.state = PrepaymentState.NONE;
	}

	/* --------------- getter and setter method --------------- */

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
	 * @return {@link #transactionId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the {@link #transactionId} to set
	 */
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return {@link #userId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the {@link #userId} to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return {@link #amount}
	 */
	@Column(nullable = false, scale = 4, precision = 18)
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the {@link #amount} to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return {@link #type}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.PrepaymentType"),
			@Parameter(name = EnumType.TYPE, value = PrepaymentType.SQL_TYPE) })
	@Column(nullable = true, updatable = false)
	public PrepaymentType getType() {
		return type;
	}

	/**
	 * @param type the {@link #type} to set
	 */
	public void setType(PrepaymentType type) {
		this.type = type;
	}

	/**
	 * @return {@link #state}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.PrepaymentState"),
			@Parameter(name = EnumType.TYPE, value = PrepaymentState.SQL_TYPE) })
	@Column(nullable = false)
	public PrepaymentState getState() {
		return state;
	}

	/**
	 * @param state the {@link #state} to set
	 */
	public void setState(PrepaymentState state) {
		this.state = state;
	}

	/**
	 * @return {@link #createFundDetailId}
	 */
	@Column(nullable = true, updatable = false)
	public Long getCreateFundDetailId() {
		return createFundDetailId;
	}

	/**
	 * @param createFundDetailId the {@link #createFundDetailId} to set
	 */
	public void setCreateFundDetailId(Long createFundDetailId) {
		this.createFundDetailId = createFundDetailId;
	}

	/**
	 * @return {@link #cancelFundDetailId}
	 */
	@Column(insertable = false)
	public Long getCancelFundDetailId() {
		return cancelFundDetailId;
	}

	/**
	 * @param cancelFundDetailId the {@link #cancelFundDetailId} to set
	 */
	public void setCancelFundDetailId(Long cancelFundDetailId) {
		this.cancelFundDetailId = cancelFundDetailId;
	}

	/**
	 * @return {@link #transferredCost}
	 */
	@Column(insertable = false, scale = 4, precision = 18)
	protected BigDecimal getTransferredCost() {
		return transferredCost;
	}

	/**
	 * @param transferredCost the {@link #transferredCost} to set
	 */
	protected void setTransferredCost(BigDecimal transferredCost) {
		this.transferredCost = transferredCost;
	}
	/**
	 * 彩种类型
	 * 
	 * @return 彩种类型
	 * @see com.cai310.lottery.common.Lottery
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(updatable = false)
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * 设置彩种类型
	 * 
	 * @param lotteryType 彩种类型
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}
}
