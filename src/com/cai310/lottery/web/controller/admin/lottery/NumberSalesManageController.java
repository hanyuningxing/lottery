package com.cai310.lottery.web.controller.admin.lottery;

import java.util.Set;

import com.cai310.lottery.common.SaleWorkerCmd;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.NumberSalesControllerService;
import com.cai310.lottery.support.WorkCallBack;

public abstract class NumberSalesManageController extends SalesManageController {

	private static final long serialVersionUID = -1981592901907045406L;

	@Override
	protected Set<SaleWorkerCmd> getPrizeProcessCmds() {
		Set<SaleWorkerCmd> cmds = super.getPrizeProcessCmds();
		cmds.add(SaleWorkerCmd.NextIssueChasePlan);
		return cmds;
	}

	@Override
	protected void checkCanRun(SalesMode salesMode, SaleWorkerCmd workCmd) throws DataException {
		super.checkCanRun(salesMode, workCmd);
		if (workCmd == SaleWorkerCmd.ChasePlan) {
			if (!this.period.isOnSale()) {
				throw new DataException("非在售状态不能执行追号。");
			} else if (this.period.isPaused()) {
				throw new DataException("暂停销售中不能执行追号。");
			} else if (!this.singleSales.isOnSale()) {
				throw new DataException("单式已截止销售不能执行追号。");
			} else if (!this.compoundSales.isOnSale()) {
				throw new DataException("复式已截止销售不能执行追号。");
			}
		}
	}

	@Override
	protected WorkCallBack getWorkCallBack(final long periodId, final SalesMode salesMode, final SaleWorkerCmd workCmd) {
		switch (workCmd) {
		case NextIssueChasePlan:
			return new WorkCallBack() {
				public void run() {
					getSalesControllerService().nextPeriodChasePlanWork(periodId);
				}
			};
		default:
			return super.getWorkCallBack(periodId, salesMode, workCmd);
		}
	}

	@Override
	protected abstract NumberSalesControllerService getSalesControllerService();
}
