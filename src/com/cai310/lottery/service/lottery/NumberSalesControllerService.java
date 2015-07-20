package com.cai310.lottery.service.lottery;

public interface NumberSalesControllerService extends SalesControllerService {

	/**
	 * 触发下期追号
	 * 
	 * @param periodId
	 */
	void nextPeriodChasePlanWork(long periodId);
}
