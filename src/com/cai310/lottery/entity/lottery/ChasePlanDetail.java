package com.cai310.lottery.entity.lottery;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.ChaseState;
import com.cai310.lottery.common.Lottery;

/**
 * 追号详细实体
 * <p>
 * 倍数的存放规则 正整数表示该期追号 0表示该期不追号 负整数表示该期在追号过程中被停掉的 数字用这个"[]"包着的,表示该期已经做过相应处理.
 * 例:[1],[0],-2,2 这个串表示追号共追3期;第二期在发起时选择了不追;追号过程中又把第三期给停掉了;目前第一期 第二期 已经处理过,下一期
 * 是追第三期
 * </p>
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "CHASE_PLAN_DETAIL")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChasePlanDetail extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = -4650685530026841524L;
	/**
	 * 彩种类型
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private Lottery lotteryType;

	/** 方案编号 */
	private Long chasePlanId;

	/** 方案编号 */
	private Long schemeId;

	private String schemeNum;

	private Integer allCount;

	private Integer nowCount;

	/** 追号状态 **/
	private ChaseState state;

	private Boolean ifHitStop;

	/** 用户编号 */
	private Long userId;

	/** 用户名 */
	private String userName;

	/** 认购金额 */
	private BigDecimal betCost;

	private Integer betCostPerMul;

	private String multiples;

	private Integer prePayId;

	private Boolean schemeWon;

	private String wonDesc;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;

	/**
	 * @return the lotteryType
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lotteryType the lotteryType to set
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	/**
	 * @return the chasePlanId
	 */
	@Column(nullable = false)
	public Long getChasePlanId() {
		return chasePlanId;
	}

	/**
	 * @param chasePlanId the chasePlanId to set
	 */
	public void setChasePlanId(Long chasePlanId) {
		this.chasePlanId = chasePlanId;
	}

	/**
	 * @return the schemeId
	 */
	@Column(nullable = false)
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
	 * @return the schemeNum
	 */
	@Column(nullable = false)
	public String getSchemeNum() {
		return schemeNum;
	}

	/**
	 * @param schemeNum the schemeNum to set
	 */
	public void setSchemeNum(String schemeNum) {
		this.schemeNum = schemeNum;
	}

	/**
	 * @return the allCount
	 */
	@Column(nullable = false)
	public Integer getAllCount() {
		return allCount;
	}

	/**
	 * @param allCount the allCount to set
	 */
	public void setAllCount(Integer allCount) {
		this.allCount = allCount;
	}

	/**
	 * @return the nowCount
	 */
	@Column(nullable = false)
	public Integer getNowCount() {
		return nowCount;
	}

	/**
	 * @param nowCount the nowCount to set
	 */
	public void setNowCount(Integer nowCount) {
		this.nowCount = nowCount;
	}

	/**
	 * @return the state
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.ChaseState"),
			@Parameter(name = EnumType.TYPE, value = ChaseState.SQL_TYPE) })
	@Column(nullable = false)
	public ChaseState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(ChaseState state) {
		this.state = state;
	}

	/**
	 * @return the ifHitStop
	 */
	public Boolean getIfHitStop() {
		return ifHitStop;
	}

	/**
	 * @param ifHitStop the ifHitStop to set
	 */
	public void setIfHitStop(Boolean ifHitStop) {
		this.ifHitStop = ifHitStop;
	}

	/**
	 * @return the userId
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
	 * @return the userName
	 */
	@Column(nullable = false, updatable = false)
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
	 * @return the betCost
	 */
	
	@Column(nullable = false, updatable = false)
	public BigDecimal getBetCost() {
		return betCost;
	}

	/**
	 * @param betCost the betCost to set
	 */
	public void setBetCost(BigDecimal betCost) {
		this.betCost = betCost;
	}

	/**
	 * @return the betCostPerMul
	 */
	
	@Column(nullable = false, updatable = false)
	public Integer getBetCostPerMul() {
		return betCostPerMul;
	}

	/**
	 * @param betCostPerMul the betCostPerMul to set
	 */
	public void setBetCostPerMul(Integer betCostPerMul) {
		this.betCostPerMul = betCostPerMul;
	}

	/**
	 * @return the multiples
	 */
	@Column(nullable = false, updatable = false)
	public String getMultiples() {
		return multiples;
	}

	/**
	 * @param multiples the multiples to set
	 */
	public void setMultiples(String multiples) {
		this.multiples = multiples;
	}

	/**
	 * @return the prePayId
	 */
	@Column(updatable = false)
	public Integer getPrePayId() {
		return prePayId;
	}

	/**
	 * @param prePayId the prePayId to set
	 */
	public void setPrePayId(Integer prePayId) {
		this.prePayId = prePayId;
	}

	/**
	 * @return the schemeWon
	 */
	@Column(nullable = true)
	public Boolean getSchemeWon() {
		return schemeWon;
	}

	/**
	 * @param schemeWon the schemeWon to set
	 */
	public void setSchemeWon(Boolean schemeWon) {
		this.schemeWon = schemeWon;
	}

	/**
	 * @return the wonDesc
	 */
	@Column(nullable = true)
	public String getWonDesc() {
		return wonDesc;
	}

	/**
	 * @param wonDesc the wonDesc to set
	 */
	public void setWonDesc(String wonDesc) {
		this.wonDesc = wonDesc;
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
}
