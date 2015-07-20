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
import com.cai310.lottery.common.TransactionState;
import com.cai310.lottery.common.TransactionType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

/**
 * 交易
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "TRANSACTION")
@Entity
public class Transaction extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = 2724525706644889929L;

	/** 版本号,用于实现乐观锁 */
	private Integer version;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;

	/** 父交易ID */
	private Long parentId;

	/**
	 * 交易状态
	 * 
	 * @see com.cai310.lottery.common.TransactionState
	 */
	private TransactionState state;

	/**
	 * 交易类型
	 * 
	 * @see com.cai310.lottery.common.TransactionType
	 */
	private TransactionType type;

	/** 交易已成交额 */
	private BigDecimal turnover;

	/** 备注 */
	private String remark;

	/** 取消原因 */
	private String cancelCause;

	/* --------------------- logic method --------------------- */

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
	 * @return {@link #parentId}
	 */
	@Column(updatable = false)
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the {@link #parentId} to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return {@link #state}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.TransactionState"),
			@Parameter(name = EnumType.TYPE, value = TransactionState.SQL_TYPE) })
	@Column(nullable = false)
	public TransactionState getState() {
		return state;
	}

	/**
	 * @param state the {@link #state} to set
	 */
	public void setState(TransactionState state) {
		this.state = state;
	}

	/**
	 * @return {@link #type}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.TransactionType"),
			@Parameter(name = EnumType.TYPE, value = TransactionType.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public TransactionType getType() {
		return type;
	}

	/**
	 * @param type the {@link #type} to set
	 */
	public void setType(TransactionType type) {
		this.type = type;
	}

	/**
	 * @return {@link #turnover}
	 */
	@Column(insertable = false,scale=4,precision=18)
	public BigDecimal getTurnover() {
		return turnover;
	}

	/**
	 * @param turnover the {@link #turnover} to set
	 */
	public void setTurnover(BigDecimal turnover) {
		this.turnover = turnover;
	}

	/**
	 * @return {@link #remark}
	 */
	@Column(length = 500, updatable = false)
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
	 * @return {@link #cancelCause}
	 */
	@Column(length = 500, insertable = false)
	public String getCancelCause() {
		return cancelCause;
	}

	/**
	 * @param cancelCause the {@link #cancelCause} to set
	 */
	public void setCancelCause(String cancelCause) {
		this.cancelCause = cancelCause;
	}
}
