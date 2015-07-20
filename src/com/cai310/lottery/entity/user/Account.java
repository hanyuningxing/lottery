package com.cai310.lottery.entity.user;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.SecurityLevel;
import com.cai310.lottery.common.UserWay;
import com.cai310.lottery.exception.DataException;

/**
 * 用户子账户
 * @author mac
 *
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "ACCOUNT")
public class Account extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = 5919843711612214128L;
	
    public static final boolean NO_LOCK_STATUS = false;
	/** 版本号,用于实现乐观锁 */
	private Integer version;
	/** 关联用户ID 一个用户对应一个账户 */
	private Long userId;
	/** 用户名 */
	private String userName;

	/** 可提金额 */
	private BigDecimal canDrawMoney;
	
	/** 不可提可提金额 */
	private BigDecimal canNotDrawMoney;
	
	/** 冻结可提金额 */
	private BigDecimal frozenCanMoney;
	
	/** 冻结不可提金额 */
	private BigDecimal frozenCanNotDrawMoneyMoney;
	
	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	private Long groupCount;
	private Long agentCount;
	/**
	 * @return {@link #version}
	 */
	public Account(){
		
	}
	public Account(User user){
		this.userId = user.getId();
		this.userName = user.getUserName();
		this.canDrawMoney = BigDecimal.ZERO;
		this.canNotDrawMoney = BigDecimal.ZERO;
		this.frozenCanMoney = BigDecimal.ZERO;
		this.frozenCanNotDrawMoneyMoney = BigDecimal.ZERO;
		this.groupCount = 0L;
		this.agentCount = 0L;
	}
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	@Column
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column
	public BigDecimal getCanDrawMoney() {
		return canDrawMoney;
	}

	public void setCanDrawMoney(BigDecimal canDrawMoney) {
		this.canDrawMoney = canDrawMoney;
	}

	public BigDecimal getCanNotDrawMoney() {
		return canNotDrawMoney;
	}
	@Column
	public void setCanNotDrawMoney(BigDecimal canNotDrawMoney) {
		this.canNotDrawMoney = canNotDrawMoney;
	}

	public BigDecimal getFrozenCanMoney() {
		return frozenCanMoney;
	}
	@Column
	public void setFrozenCanMoney(BigDecimal frozenCanMoney) {
		this.frozenCanMoney = frozenCanMoney;
	}

	public BigDecimal getFrozenCanNotDrawMoneyMoney() {
		return frozenCanNotDrawMoneyMoney;
	}
	@Column
	public void setFrozenCanNotDrawMoneyMoney(BigDecimal frozenCanNotDrawMoneyMoney) {
		this.frozenCanNotDrawMoneyMoney = frozenCanNotDrawMoneyMoney;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Column
	public Long getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(Long groupCount) {
		this.groupCount = groupCount;
	}
	@Column
	public Long getAgentCount() {
		return agentCount;
	}

	public void setAgentCount(Long agentCount) {
		this.agentCount = agentCount;
	}
}