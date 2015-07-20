package com.cai310.lottery.entity.lottery.jczq;

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

import com.cai310.entity.IdEntity;
import com.cai310.lottery.common.ChaseState;
import com.cai310.lottery.common.Lottery;

/**
 * 竞彩足球追投计划类
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "JCZQ_CHASE_PLAN")
@Entity
public class JczqChasePlan extends IdEntity{
	private static final long serialVersionUID = -1095287141392304283L;
	
	/** 版本号 */
	protected Integer version;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	
	/** 彩种 **/
	private Lottery lotteryType;

	/** 已追金额 */
	private Integer chasedCost;

	/** 追号用户ID **/
	private Long userId;

	/*** 追号用户名 **/
	private String userName;
	
	/*** 追号方案名 称 **/
	private String chasePlanName;
	
	/** 追号总奖金 */
	private BigDecimal totalPrize;
	
	/** 回报率 **/
	private Integer returnRateLevel;
	
	/** 起始倍数 **/
	private Integer mutiple;
	
	/**
	 * @return {@link #totalPrize}
	 */
	
	@Transient
	public BigDecimal getTotalPrize() {
		return totalPrize;
	}

	/**
	 * @param totalPrize the {@link #totalPrize} to set
	 */
	public void setTotalPrize(BigDecimal totalPrize) {
		this.totalPrize = totalPrize;
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
	 * @return {@link #lotteryType}
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
	 * @return {@link #userName}
	 */
	@Column(nullable = false, updatable = false, length = 20)
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the {@link #userName} to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @return {@link #chasedCost}
	 */
	
	@Column
	public Integer getChasedCost() {
		return chasedCost;
	}

	/**
	 * @param chasedCost the {@link #chasedCost} to set
	 */
	public void setChasedCost(Integer chasedCost) {
		this.chasedCost = chasedCost;
	}


	public Integer getMutiple() {
		return mutiple;
	}

	public void setMutiple(Integer mutiple) {
		this.mutiple = mutiple;
	}

	public Integer getReturnRateLevel() {
		return returnRateLevel;
	}

	public void setReturnRateLevel(Integer returnRateLevel) {
		this.returnRateLevel = returnRateLevel;
	}

	public String getChasePlanName() {
		return chasePlanName;
	}

	public void setChasePlanName(String chasePlanName) {
		this.chasePlanName = chasePlanName;
	}
}
