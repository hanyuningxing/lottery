package com.cai310.lottery.service.lottery.dczc.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cai310.lottery.common.SaleWorkerCmd;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.service.lottery.impl.SaleControllerServiceImpl;

public class DczcSaleControllerServiceImpl extends SaleControllerServiceImpl {

	public Set<SaleWorkerCmd> getCanRunCmds(Period period) {
		Set<SaleWorkerCmd> cmds = new HashSet<SaleWorkerCmd>(0);
		if (period.isOnSale())
			cmds.add(SaleWorkerCmd.PauseSale);
		else if (period.isPaused())
			cmds.add(SaleWorkerCmd.ResumeSale);
		if (period.isStarted())
			cmds.addAll(getAfterSaleCanRunCmds(period));

		if (period.isFinished())
			cmds.addAll(getAfterFinishCanRunCmds(period));

		return cmds;
	}

	public Set<SaleWorkerCmd> getAfterSaleCanRunCmds(Period period) {
		Set<SaleWorkerCmd> cmds = new HashSet<SaleWorkerCmd>(0);

		PeriodSales singleSale = getPeriodManager().getPeriodSales(new PeriodSalesId(period.getId(), SalesMode.SINGLE));
		PeriodSales polySale = getPeriodManager().getPeriodSales(new PeriodSalesId(period.getId(), SalesMode.COMPOUND));
		boolean allSaleFinished = singleSale.isSaleFinished() && polySale.isSaleFinished();// 单复式销售流程都完成了

		if (period.isCurrent() && allSaleFinished) {
			List<Period> currentIssues = getPeriodManager().findCurrentPeriods(period.getLotteryType());
			if (currentIssues != null && currentIssues.size() > 1) {
				cmds.add(SaleWorkerCmd.HideIssue);
			}
		}else{
			cmds.add(SaleWorkerCmd.ShowIssue);
		}

		if (!period.isResultUpdate()) {
			cmds.add(SaleWorkerCmd.UpdateResult);
		} else if (!period.isPrizeUpdate()) {
			cmds.add(SaleWorkerCmd.UpdateResult);
		} else if (!period.isPrizeDelived()) {
			cmds.add(SaleWorkerCmd.UpdateResult);
			cmds.add(SaleWorkerCmd.DelivePrize);
		} else if (!period.isFinished()) {
			cmds.add(SaleWorkerCmd.UpdateResult);
			cmds.add(SaleWorkerCmd.DelivePrize);
			if (period.isStarted() && period.isSaleEnded() && allSaleFinished) {
				cmds.add(SaleWorkerCmd.CloseIssue);
			}
		}
		return cmds;
	}
}
