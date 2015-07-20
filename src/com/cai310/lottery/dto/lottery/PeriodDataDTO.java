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
public class PeriodDataDTO {
	/** 期ID */
	protected Long periodId;

	protected String result; // 文本开奖结果
	/** 彩种类型 */
	private Lottery lotteryType;
	/** 期描述 */
	private String periodTitle;
	/** 期号 */
	protected String periodNumber;
	/** 开奖时间 */
	protected String prizeTime;

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


	public String getPeriodTitle() {
		return periodTitle;
	}

	public void setPeriodTitle(String periodTitle) {
		this.periodTitle = periodTitle;
	}

	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getPrizeTime() {
		return prizeTime;
	}

	public void setPrizeTime(String prizeTime) {
		this.prizeTime = prizeTime;
	}
}
