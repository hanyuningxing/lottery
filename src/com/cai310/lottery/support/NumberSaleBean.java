package com.cai310.lottery.support;

import java.util.Date;

import com.cai310.lottery.common.Lottery;

public class NumberSaleBean {
	
	private Long id;
	/** 彩种类型 */
	private Lottery lotteryType;
	/** 期号 */
	protected String periodNumber;
	
	/*** 代购发起截止时间 */
	protected String selfEndInitTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lottery getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	public String getSelfEndInitTime() {
		return selfEndInitTime;
	}

	public void setSelfEndInitTime(String selfEndInitTime) {
		this.selfEndInitTime = selfEndInitTime;
	}

	

}
