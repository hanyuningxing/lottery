package com.cai310.lottery.dto.lottery;

import java.math.BigDecimal;
import java.util.Date;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PeriodState;
import com.cai310.lottery.common.SubscriptionWay;

/**
 * 期信息数据传输对象
 * 
 */
public class PeriodDTO {
	/** 彩种类型 */
	private Lottery lotteryType;
	/** 玩法类型 -主要为了区分排列三和排列五 14和9 */
	private String playType;
	/** 期描述 */
	private String periodTitle;

	/** 期号 */
	protected String periodNumber;
	
	/**
	 * 期状态
	 * 
	 * @see com.cai310.lottery.common.PeriodState
	 * 
	 */
	protected PeriodState state;
	
	/** 官方期截止时间 */
	protected String endedTime;

	/** 开奖时间 */
	protected String prizeTime;
	
	/*** 合买发起截止时间 */
	protected String shareEndInitTime_single;

	/*** 代购发起截止时间 */
	protected String selfEndInitTime_single;

	/*** 加入截止时间 */
	protected String endJoinTime_single;
	
	/*** 合买发起截止时间 */
	protected String shareEndInitTime_compound;

	/*** 代购发起截止时间 */
	protected String selfEndInitTime_compound;

	/*** 加入截止时间 */
	protected String endJoinTime_compound;

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

	public PeriodState getState() {
		return state;
	}

	public void setState(PeriodState state) {
		this.state = state;
	}

	public String getPeriodTitle() {
		return periodTitle;
	}

	public void setPeriodTitle(String periodTitle) {
		this.periodTitle = periodTitle;
	}

	public String getEndedTime() {
		return endedTime;
	}

	public void setEndedTime(String endedTime) {
		this.endedTime = endedTime;
	}

	public String getPrizeTime() {
		return prizeTime;
	}

	public void setPrizeTime(String prizeTime) {
		this.prizeTime = prizeTime;
	}

	public String getShareEndInitTime_single() {
		return shareEndInitTime_single;
	}

	public void setShareEndInitTime_single(String shareEndInitTime_single) {
		this.shareEndInitTime_single = shareEndInitTime_single;
	}

	public String getSelfEndInitTime_single() {
		return selfEndInitTime_single;
	}

	public void setSelfEndInitTime_single(String selfEndInitTime_single) {
		this.selfEndInitTime_single = selfEndInitTime_single;
	}

	public String getEndJoinTime_single() {
		return endJoinTime_single;
	}

	public void setEndJoinTime_single(String endJoinTime_single) {
		this.endJoinTime_single = endJoinTime_single;
	}

	public String getShareEndInitTime_compound() {
		return shareEndInitTime_compound;
	}

	public void setShareEndInitTime_compound(String shareEndInitTime_compound) {
		this.shareEndInitTime_compound = shareEndInitTime_compound;
	}

	public String getSelfEndInitTime_compound() {
		return selfEndInitTime_compound;
	}

	public void setSelfEndInitTime_compound(String selfEndInitTime_compound) {
		this.selfEndInitTime_compound = selfEndInitTime_compound;
	}

	public String getEndJoinTime_compound() {
		return endJoinTime_compound;
	}

	public void setEndJoinTime_compound(String endJoinTime_compound) {
		this.endJoinTime_compound = endJoinTime_compound;
	}

	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}
}
