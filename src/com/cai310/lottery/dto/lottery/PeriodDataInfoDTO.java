package com.cai310.lottery.dto.lottery;

import java.math.BigDecimal;
import java.util.Date;

import net.sf.json.JSONArray;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PeriodState;
import com.cai310.lottery.common.SubscriptionWay;
import com.cai310.lottery.entity.lottery.dlt.DltPrize;
import com.cai310.lottery.entity.lottery.dlt.DltWinUnit;

/**
 * 期信息数据详情传输对象
 * 
 */
public class PeriodDataInfoDTO {
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
	
	private JSONArray winUnit;
	
	private JSONArray prize;
	
	private Long prizePool; // 奖池金额
	
	private Long totalSales; // 销售总额
	
	/** 截至兑奖时间 */
	protected String endCashTime;

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

	public JSONArray getWinUnit() {
		return winUnit;
	}

	public void setWinUnit(JSONArray winUnit) {
		this.winUnit = winUnit;
	}

	public JSONArray getPrize() {
		return prize;
	}

	public void setPrize(JSONArray prize) {
		this.prize = prize;
	}

	public Long getPrizePool() {
		return prizePool;
	}

	public void setPrizePool(Long prizePool) {
		this.prizePool = prizePool;
	}

	public Long getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(Long totalSales) {
		this.totalSales = totalSales;
	}

	public String getEndCashTime() {
		return endCashTime;
	}

	public void setEndCashTime(String endCashTime) {
		this.endCashTime = endCashTime;
	}
}
