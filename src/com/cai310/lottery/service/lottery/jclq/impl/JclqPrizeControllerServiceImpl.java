package com.cai310.lottery.service.lottery.jclq.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.AfterSaleFlag;
import com.cai310.lottery.common.SaleWorkerCmd;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.SubscriptionState;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.SaleAnalyse;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.Subscription;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.exception.ControllerException;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.SaleAnalyseEntityManager;
import com.cai310.lottery.service.lottery.SalesControllerService;
import com.cai310.lottery.service.lottery.dczc.MatchNotEndException;
import com.cai310.lottery.service.lottery.impl.PrizeControllerServiceImpl;
import com.cai310.utils.DateUtil;
import com.google.common.collect.Maps;

public class JclqPrizeControllerServiceImpl extends PrizeControllerServiceImpl {
	@Autowired
	private JclqSchemeEntityManagerImpl jclqSchemeEntityManagerImpl;
	@Autowired
	protected SaleAnalyseEntityManager saleAnalyseEntityManager;
	@Autowired
	protected JclqSaleControllerServiceImpl saleControllerService;
	@Override
	public synchronized void updateResultWork(long periodId) {
		SaleWorkerCmd cmd = SaleWorkerCmd.UpdateResult;

		workHelper.logCmdStart(cmd, periodId);

		Period period = getPeriodManager().getPeriod(periodId);
		if (!period.isDrawed()) {
			throw new ControllerException("还未开奖，不能执行【" + cmd.getCmdName() + "】");
		}

		List<Long> idList = getSchemeManager().findSchemeIdOfCanUpdateWon(periodId);
		if (idList != null && !idList.isEmpty()) {
			StringBuffer msg = new StringBuffer(50);
			int totalCount = idList.size();
			int count = 0;
			Date date;
			long runTime;

			for (Long betSchemeId : idList) {
				count++;
				date = new Date();
				updateResult(betSchemeId);
				msg.setLength(0);
				msg.append("正在处理方案[#").append(period.getLotteryType().getSchemeNumberPrefix()).append(betSchemeId).append("]...");
				workHelper.logInfo(msg.toString());

				runTime = (new Date()).getTime() - date.getTime();// 操作耗时，单位毫秒
				msg.setLength(0);
				msg.append("方案[#").append(period.getLotteryType().getSchemeNumberPrefix()).append(betSchemeId).append("]处理结束，耗时").append(runTime).append("毫秒，还剩").append(
						totalCount - count).append("个方案未处理。");
				workHelper.logInfo(msg.toString());
			}
		} else {
			workHelper.logWarn("找不到可以执行[" + cmd.getCmdName() + "]操作的方案。");
		}

		// 检查是否还有方案未执行
		Integer count = getSchemeManager().statUnUpdateWon(periodId);
		if (count != null && count > 0) {
			workHelper.logWarn("还剩" + count + "个方案未执行。");
		}
       
		workHelper.logInfo("正在更新售后标识...");
		getPeriodManager().updateAfterSaleFlags(periodId, AfterSaleFlag.RESULT_UPDATED);

		workHelper.logCmdSuccess();
		
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
			workHelper.logWarn(e.getMessage());
		}
	}
}
