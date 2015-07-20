package com.cai310.lottery.dto.lottery;

import java.util.Date;

import com.cai310.lottery.common.Lottery;

public class SchemeTempQueryDTO {
		
	/** 彩种类型*/
	private Lottery lotteryType;
	/** 发起起始时间 */
	private Date startTime;
	
	private Date endTime;
	
	/** 方案发起人的用户编号 */
	private Long sponsorId;
	
	public Lottery getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}
	
}
