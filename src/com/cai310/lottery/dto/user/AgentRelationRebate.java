package com.cai310.lottery.dto.user;

import java.io.Serializable;

import com.cai310.lottery.common.AgentLotteryType;
public class AgentRelationRebate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	// 充值，提现，投注，返点，撤单，追号
	private Long userId;
	private String userName;
	private String realName;
	/** 用户的返点*/
	private Double rebate;
	private Integer pos;///位置关系0开始 会员级别顺序
	/**
	 * 彩票类型
	 * 
	 * @see com.cai310.lottery.common.AgentLotteryType
	 */
	private AgentLotteryType agentLotteryType;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Double getRebate() {
		return rebate;
	}
	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}
	public Integer getPos() {
		return pos;
	}
	public void setPos(Integer pos) {
		this.pos = pos;
	}
	public AgentLotteryType getAgentLotteryType() {
		return agentLotteryType;
	}
	public void setAgentLotteryType(AgentLotteryType agentLotteryType) {
		this.agentLotteryType = agentLotteryType;
	}

	


}