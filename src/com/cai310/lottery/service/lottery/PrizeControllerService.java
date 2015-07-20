package com.cai310.lottery.service.lottery;

import com.cai310.lottery.entity.lottery.PeriodSalesId;

public interface PrizeControllerService {
	/**
	 * 更新开奖结果
	 * 
	 * @param periodId
	 */
	public void updateResultWork(long periodId);

	/**
	 * 更新奖金
	 * 
	 * @param periodId
	 */
	public void updatePrizeWork(long periodId);

	/**
	 * 派奖
	 * 
	 * @param periodId
	 */
	public void delivePrizeWork(long periodId);

	/**
	 * 赠送积分
	 * 
	 * @param salesId
	 */
	//public void deliveWinRecord(long periodId);
}
