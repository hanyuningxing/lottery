package com.cai310.lottery.service.lottery.dczc.impl;

import com.cai310.lottery.common.AfterSaleFlag;
import com.cai310.lottery.service.lottery.dczc.MatchNotEndException;
import com.cai310.lottery.service.lottery.impl.PrizeControllerServiceImpl;

public class DczcPrizeControllerServiceImpl extends PrizeControllerServiceImpl {

	@Override
	public synchronized void updateResultWork(long periodId) {
		super.updateResultWork(periodId);
		getPeriodManager().updateAfterSaleFlags(periodId, AfterSaleFlag.PRIZE_UPDATED);
	}

	@Override
	public synchronized void updatePrizeWork(long periodId) {
		updateResultWork(periodId);
		getPeriodManager().updateAfterSaleFlags(periodId, AfterSaleFlag.RESULT_UPDATED);
	}

	@Override
	protected void updateResult(Long schemeId) {
		try {
			super.updateResult(schemeId);
		} catch (MatchNotEndException e) {
			workHelper.logInfo(e.getMessage());
		}
	}
}
