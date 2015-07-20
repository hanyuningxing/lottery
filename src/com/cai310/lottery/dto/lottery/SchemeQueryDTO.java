package com.cai310.lottery.dto.lottery;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.ShareType;

public class SchemeQueryDTO {

	private List<Criterion> restrictions;
	
	/**
	 * 方案状态
	 * 
	 * @see com.cai310.lottery.common.SchemeState
	 */
	private SchemeState state;
	
	private Date startTime_prize;
	
	private Date endTime_prize;
	
	private ShareType shareType;
	/** 发起起始时间 */
	private Date startTime;
	
	private Date endTime;
	
	private Long orderId;

	private String periodNumber;

	private String playType;

	private boolean won;
	
	/**
	 * @return {@link #state}
	 */
	public SchemeState getState() {
		return state;
	}

	/**
	 * @param state the {@link #state} to set
	 */
	public void setState(SchemeState state) {
		this.state = state;
	}

	/**
	 * @return {@link #startTime}
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the {@link #startTime} to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}

	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}

	public List<Criterion> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<Criterion> restrictions) {
		this.restrictions = restrictions;
	}

	public Date getStartTime_prize() {
		return startTime_prize;
	}

	public void setStartTime_prize(Date startTime_prize) {
		this.startTime_prize = startTime_prize;
	}

	public Date getEndTime_prize() {
		return endTime_prize;
	}

	public void setEndTime_prize(Date endTime_prize) {
		this.endTime_prize = endTime_prize;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public ShareType getShareType() {
		return shareType;
	}

	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}

}
