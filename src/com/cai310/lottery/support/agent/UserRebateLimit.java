package com.cai310.lottery.support.agent;

public class UserRebateLimit {
	private String rebate;
	private Integer limitNumber;
	private Integer groupNumber;
	public Integer getLimitNumber() {
		return limitNumber;
	}
	public void setLimitNumber(Integer limitNumber) {
		this.limitNumber = limitNumber;
	}
	public Integer getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(Integer groupNumber) {
		this.groupNumber = groupNumber;
	}
	public String getRebate() {
		return rebate;
	}
	public void setRebate(String rebate) {
		this.rebate = rebate;
	}
	public Integer findLeftNumber(){
		return limitNumber-groupNumber;
	}
}
