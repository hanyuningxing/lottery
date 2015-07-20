package com.cai310.lottery.entity.user.agent;

import java.io.Serializable;
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
import com.cai310.lottery.common.AgentDetailType;
import com.cai310.lottery.common.AgentLotteryType;
import com.cai310.lottery.common.DrawingOrderState;
import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.exception.DataException;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "AGENT_FUND_DETAIL")
public class AgentFundDetail extends IdEntity implements CreateMarkable, Serializable {
	private static final long serialVersionUID = 6420283302235215768L;
	// Fields
	// 充值，提现，投注，返点，撤单，追号
	private Long accountId;
	private Long userId;
	private String userName;
	private BigDecimal money;
	/** 用户的返点*/
	private Double rebate;
	private AgentDetailType detailType;
	private Long schemeId;
	/**
	 * 彩票类型
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private Lottery lottery; 
	/**
	 * 彩票类型
	 * 
	 * @see com.cai310.lottery.common.AgentLotteryType
	 */
	private AgentLotteryType lotteryType;
	/** 创建时间 */
	private Date createTime;
	
	/** 版本号,用于实现乐观锁 */
	private Integer version;

	
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
	@Column
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column
	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}
	@Column
	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.AgentDetailType"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = true, updatable = false)
	public AgentDetailType getDetailType() {
		return detailType;
	}

	public void setDetailType(AgentDetailType detailType) {
		this.detailType = detailType;
	}
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.AgentLotteryType"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = true, updatable = false)
	public AgentLotteryType getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(AgentLotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}
	@Column
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	@Column
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column
	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}
	@Column
	public Lottery getLottery() {
		return lottery;
	}

	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}
	
}