package com.cai310.lottery.entity.user.agent;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.AgentLotteryType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.agent.RebateLimitUpdate;
import com.cai310.lottery.support.agent.UserRebateLimit;
import com.cai310.lottery.support.agent.UserRebateLimitList;
import com.cai310.utils.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "AGENT_REBATE")
public class AgentRebate extends IdEntity implements CreateMarkable, UpdateMarkable, Serializable {
	private static final long serialVersionUID = 6420283302235215768L;
	// Fields
	// 充值，提现，投注，返点，撤单，追号
	private Long userId;
	private String userName;
	/** 用户的返点*/
	private Double rebate;
	/**
	 * 彩票类型
	 * 
	 * @see com.cai310.lottery.common.AgentLotteryType
	 */
	private AgentLotteryType agentLotteryType;
	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	
	/** 版本号,用于实现乐观锁 */
	private Integer version;
	
	// Constructors

	/** default constructor */
	public AgentRebate() {
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
	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}
	/**
	 * 彩票类型
	 * 
	 * @return {@link #lotteryType}
	 * @see com.cai310.lottery.common.Lottery
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.AgentLotteryType"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public AgentLotteryType getAgentLotteryType() {
		return agentLotteryType;
	}

	/**
	 * 设置彩票类型
	 * 
	 * @param lotteryType {@link #lotteryType}
	 * @see com.cai310.lottery.common.Lottery
	 */
	public void setAgentLotteryType(AgentLotteryType agentLotteryType) {
		this.agentLotteryType = agentLotteryType;
	}


}