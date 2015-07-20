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
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;

/**
 * 竞彩足球追投计划详情类
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "JCZQ_CHASE_PLAN_DETAIL")
@Entity
public class JczqChasePlanDetail extends IdEntity{
	private static final long serialVersionUID = 9103487551920059133L;
	
	/** 版本号 */
	protected Integer version;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	
	/** 追号方案Id **/
	private Long jczqChasePlanId;
	
	/** 追号用户ID **/
	private Long userId;

	/** 追号用户名 **/
	private String userName;
	
	/** 是否已经投注 **/
	private boolean hasPay;
	
	/** 彩种 **/
	private Lottery lotteryType;
	
	/** 方案金额 **/
	private Integer schemeCost;
	
	/** 方案ID **/
	private Long schemeId;
	
	/** 方案号 **/
	private String schemeNumber;
	
	/** 中奖金额 **/
	private BigDecimal prizeAfterTax;
	
	/** 方案出票状态 */
	private SchemePrintState schemePrintState;
	
	/** 投注时间 */
	private Date schemeCreateTime;
	
	/** 回报率 **/
	private Integer returnRateLevel;	
	
	/** 起始倍数 **/
	private Integer mutiple;
	
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

	public Long getJczqChasePlanId() {
		return jczqChasePlanId;
	}

	public void setJczqChasePlanId(Long jczqChasePlanId) {
		this.jczqChasePlanId = jczqChasePlanId;
	}

	public Integer getSchemeCost() {
		return schemeCost;
	}

	public void setSchemeCost(Integer schemeCost) {
		this.schemeCost = schemeCost;
	}

	public Integer getMutiple() {
		return mutiple;
	}

	public void setMutiple(Integer mutiple) {
		this.mutiple = mutiple;
	}

	public boolean isHasPay() {
		return hasPay;
	}

	public void setHasPay(boolean hasPay) {
		this.hasPay = hasPay;
	}

	public Integer getReturnRateLevel() {
		return returnRateLevel;
	}

	public void setReturnRateLevel(Integer returnRateLevel) {
		this.returnRateLevel = returnRateLevel;
	}

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	public String getSchemeNumber() {
		return schemeNumber;
	}

	public void setSchemeNumber(String schemeNumber) {
		this.schemeNumber = schemeNumber;
	}
	
	@Transient
	public BigDecimal getPrizeAfterTax() {
		return prizeAfterTax;
	}

	public void setPrizeAfterTax(BigDecimal prizeAfterTax) {
		this.prizeAfterTax = prizeAfterTax;
	}
	
	@Transient
	public SchemePrintState getSchemePrintState() {
		return schemePrintState;
	}

	public void setSchemePrintState(SchemePrintState schemePrintState) {
		this.schemePrintState = schemePrintState;
	}
	
	@Transient
	public Date getSchemeCreateTime() {
		return schemeCreateTime;
	}

	public void setSchemeCreateTime(Date schemeCreateTime) {
		this.schemeCreateTime = schemeCreateTime;
	}

	
}
