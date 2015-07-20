package com.cai310.lottery.service.lottery.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.cai310.lottery.common.AfterFinishFlag;
import com.cai310.lottery.common.SaleWorkerCmd;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.exception.ControllerException;
import com.cai310.lottery.service.lottery.ChasePlanEntityManager;
import com.cai310.lottery.service.lottery.NumberSalesControllerService;
import com.cai310.lottery.service.lottery.NumberSchemeService;

public class NumberSaleControllerServiceImpl extends SaleControllerServiceImpl implements NumberSalesControllerService {

	@Resource(name = "chasePlanEntityManagerImpl")
	ChasePlanEntityManager chasePlanEntityManager;

	@Override
	public Set<SaleWorkerCmd> getCanRunCmds(Period period) {
		Set<SaleWorkerCmd> cmds = super.getCanRunCmds(period);
		if (period.isOnSale()) {
			cmds.add(SaleWorkerCmd.ChasePlan);
		} else if (period.isFinished()) {
			if (!period.checkAfterFinishFlags(AfterFinishFlag.NextIssueChasePlan.getFlagValue())) {
				cmds.add(SaleWorkerCmd.NextIssueChasePlan);
			}
			if (!period.checkAfterFinishFlags(AfterFinishFlag.SaleAnalyse.getFlagValue())) {
				cmds.add(SaleWorkerCmd.SaleAnalyse);
			}
		}
		return cmds;
	}

	@Override
	public void closePeriodWork(long periodId) {
		Period period = getPeriodManager().getPeriod(periodId);
		if (period.getPrevPreriodId() != null) {
			Period prevIssue = getPeriodManager().getPeriod(period.getPrevPreriodId());
			if (!prevIssue.checkAfterFinishFlags(AfterFinishFlag.NextIssueChasePlan.getFlagValue())) {
				throw new ControllerException("上一期【触发下一期追号】未执行，不能截止销售。");
			}
		}
		super.closePeriodWork(periodId);
	}

	/**
	 * 触发下一期追号
	 * 
	 * @param periodId
	 */
	public void nextPeriodChasePlanWork(long periodId) {
		SaleWorkerCmd cmd = SaleWorkerCmd.NextIssueChasePlan;

		workHelper.logCmdStart(cmd, periodId);

		Period period = getPeriodManager().getPeriod(periodId);
		if (period.checkAfterFinishFlags(AfterFinishFlag.NextIssueChasePlan.getFlagValue())) {
			throw new ControllerException("触发下一期追号已完成，不能再执行！");
		}
		Period nextPeriod = getPeriodManager().getNextPeriod(period.getLotteryType(), period.getId());
		if (nextPeriod == null) {
			throw new ControllerException("下一期不存在！");
		}
		if (!nextPeriod.isStarted()) {
			throw new ControllerException("下一期未开售！");
		} else if (nextPeriod.isPaused()) {
			throw new ControllerException("下一期暂停销售中！");
		} else if (nextPeriod.isSaleEnded()) {
			throw new ControllerException("下一期已截止！");
		} else {
			PeriodSales singleSale = getPeriodManager().getPeriodSales(
					new PeriodSalesId(nextPeriod.getId(), SalesMode.SINGLE));
			if (!singleSale.isOnSale()) {
				throw new ControllerException("单式非在售状态！");
			}
			PeriodSales compoundSale = getPeriodManager().getPeriodSales(
					new PeriodSalesId(nextPeriod.getId(), SalesMode.COMPOUND));
			if (!compoundSale.isOnSale()) {
				throw new ControllerException("复式非在售状态！");
			}
		}

		workHelper.logInfo("开始处理未设置中奖后停止的追号...");
		List<Long> list = chasePlanEntityManager.findChasePlanIdOfRunning(period.getId(), getLotteryType(), false);
		if (list != null && !list.isEmpty()) {
			for (Long chasePlanId : list) {
				workHelper.logInfo("正在处理追号[#" + chasePlanId + "]...");
				getSchemeService().handleChasePlan(chasePlanId, nextPeriod.getId());
			}
		} else {
			workHelper.logWarn("没有找到未设置中奖后停止的追号。");
		}

		// 检查是否已经全部结束
		if (period.isFinished()) {
			workHelper.logInfo("开始处理设置了中奖后停止的追号...");
			list = chasePlanEntityManager.findChasePlanIdOfRunning(period.getId(), getLotteryType(), true);
			if (list != null && !list.isEmpty()) {
				for (Long chasePlanId : list) {
					workHelper.logInfo("正在处理追号[#" + chasePlanId + "]...");
					getSchemeService().handleChasePlan(chasePlanId, nextPeriod.getId());
				}
			} else {
				workHelper.logWarn("没有找到设置了中奖后停止的追号。");
			}
		} else {
			workHelper.logWarn("未全部结束，不能处理设置了中奖后停止的追号，请记得在全部结束后重新执行。");
		}

		Integer unHandleChaseNum = chasePlanEntityManager.statUnHandleChase(period.getId());
		if (unHandleChaseNum > 0) {
			workHelper.logWarn("还有" + unHandleChaseNum + "个追号未处理。");
			return;
		}

		workHelper.logInfo("正在更新标识...");
		getPeriodManager().updateAfterFinishFlags(periodId, AfterFinishFlag.NextIssueChasePlan);

		workHelper.logCmdSuccess();
	}

	@Override
	protected NumberSchemeService getSchemeService() {
		return (NumberSchemeService) super.getSchemeService();
	}
}
