package com.cai310.lottery.service.lottery.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.AfterSaleFlag;
import com.cai310.lottery.common.SaleWorkerCmd;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5Passcount;
import com.cai310.lottery.entity.ticket.TicketPlatformInfo;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.exception.ControllerException;
import com.cai310.lottery.service.lottery.EventLogManager;
import com.cai310.lottery.service.lottery.PrizeControllerService;
import com.cai310.lottery.service.ticket.TicketThenEntityManager;

public class PrizeControllerServiceImpl extends AbstractControllerService implements PrizeControllerService {
	@Resource
	protected EventLogManager eventLogManager;
	@Autowired
	protected TicketThenEntityManager ticketThenEntityManager;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cai310.lottery.service.lottery.impl.PrizeControllerService#updateResultWork
	 * (long)
	 */
	@SuppressWarnings("unchecked")
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

				msg.setLength(0);
				msg.append("正在处理方案[#").append(period.getLotteryType().getSchemeNumberPrefix()).append(betSchemeId).append("]...");
				workHelper.logInfo(msg.toString());

				updateResult(betSchemeId);

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
	}

	protected void updateResult(Long schemeId) {
		getSchemeService().updateResult(schemeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cai310.lottery.service.lottery.impl.PrizeControllerService#updatePrizeWork
	 * (long)
	 */
	@SuppressWarnings("unchecked")
	public synchronized void updatePrizeWork(long periodId) {
		SaleWorkerCmd cmd = SaleWorkerCmd.UpdatePrize;

		workHelper.logCmdStart(cmd, periodId);

		Period period = getPeriodManager().getPeriod(periodId);
		if (!period.isDrawed()) {
			throw new ControllerException("还未开奖，不能执行【" + cmd.getCmdName() + "】");
		}

		List<Long> idList = getSchemeManager().findSchemeIdOfCanUpdatePrize(periodId);
		if (idList != null && !idList.isEmpty()) {
			StringBuffer msg = new StringBuffer(50);
			int totalCount = idList.size();
			int count = 0;
			Date date;
			long runTime;

			for (Long betSchemeId : idList) {
				count++;
				date = new Date();

				msg.setLength(0);
				msg.append("正在处理方案[#").append(period.getLotteryType().getSchemeNumberPrefix()).append(betSchemeId).append("]...");
				workHelper.logInfo(msg.toString());

				getSchemeService().updatePrize(betSchemeId);// 更新方案奖金

				runTime = (new Date()).getTime() - date.getTime();// 操作耗时，单位毫秒
				msg.setLength(0);
				msg.append("方案[#").append(period.getLotteryType().getSchemeNumberPrefix()).append(betSchemeId).append("]处理完成，耗时").append(runTime).append("毫秒，还剩").append(
						totalCount - count).append("个方案未处理。");
				workHelper.logInfo(msg.toString());
			}
		} else {
			workHelper.logWarn("找不到可以执行[" + cmd.getCmdName() + "]操作的方案。");
		}

		// 检查是否还有方案未执行
		Integer count = getSchemeManager().statUnUpdatePrize(periodId);
		if (count != null && count > 0) {
			workHelper.logWarn("还剩" + count + "个方案未执行。");
		}

		workHelper.logInfo("正在更新售后标识...");
		getPeriodManager().updateAfterSaleFlags(periodId, AfterSaleFlag.PRIZE_UPDATED);

		workHelper.logCmdSuccess();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cai310.lottery.service.lottery.impl.PrizeControllerService#delivePrizeWork
	 * (long)
	 */
	@SuppressWarnings("unchecked")
	public synchronized void delivePrizeWork(long periodId) {
		SaleWorkerCmd cmd = SaleWorkerCmd.DelivePrize;

		workHelper.logCmdStart(cmd, periodId);
		Period period = getPeriodManager().getPeriod(periodId);
		if (!period.isDrawed()) {
			throw new ControllerException("还未开奖，不能执行【" + cmd.getCmdName() + "】");
		}

		List<Long> idList = getSchemeManager().findSchemeIdOfCanDelivePrize(periodId);
		if (idList != null && !idList.isEmpty()) {
			StringBuffer msg = new StringBuffer(50);
			int totalCount = idList.size();
			int count = 0;
			Date date;
			long runTime;
			for (Long betSchemeId : idList) {
				count++;
				date = new Date();

				msg.setLength(0);
				msg.append("正在处理方案[#").append(period.getLotteryType().getSchemeNumberPrefix()).append(betSchemeId).append("]...");
				workHelper.logInfo(msg.toString());

				getSchemeService().delivePrize(betSchemeId);

				runTime = (new Date()).getTime() - date.getTime();// 操作耗时，单位毫秒
				msg.setLength(0);
				msg.append("方案[#").append(period.getLotteryType().getSchemeNumberPrefix()).append(betSchemeId).append("]处理完成，耗时").append(runTime).append("毫秒，还剩").append(
						totalCount - count).append("个方案未处理。");
				workHelper.logInfo(msg.toString());
			}
		} else {
			workHelper.logWarn("找不到可以执行[" + cmd.getCmdName() + "]操作的方案。");
		}

		// 检查是否还有方案未执行
		Integer count = getSchemeManager().statUnDelivePrize(periodId);
		if (count != null && count > 0) {
			workHelper.logWarn("还剩" + count + "个方案未执行。");
		}

		workHelper.logInfo("正在更新售后标识...");
		getPeriodManager().updateAfterSaleFlags(periodId, AfterSaleFlag.PRIZE_DELIVED);
		workHelper.logInfo("同步出票金额开始...");
		////同步出票
		ticketThenEntityManager.updateTicketPlatformInfoPrize();
		workHelper.logInfo("同步出票金额完成...");
		workHelper.logCmdSuccess();
	}
//	public synchronized void deliveWinRecord(long periodId) {
//		workHelper.logCmdStart(cmd, periodId);
//		Period period = getPeriodManager().getPeriod(periodId);
//		if (!period.isDrawed()) {
//			throw new ControllerException("还未开奖，不能执行【" + cmd.getCmdName() + "】");
//		}
//
//		//TODO 获取 流失、成功 中奖 方案
//		List<Long> idList = getSchemeManager().findSchemeIdOfCanDeliveWinRecord(periodId);
//		if (idList != null && !idList.isEmpty()) {
//			StringBuffer msg = new StringBuffer(50);
//			int totalCount = idList.size();
//			int count = 0;
//			Date date;
//			long runTime;
//			for (Long betSchemeId : idList) {
//				count++;
//				date = new Date();
//
//				msg.setLength(0);
//				msg.append("正在处理方案[#").append(period.getLotteryType().getSchemeNumberPrefix()).append(betSchemeId).append("]...");
//				workHelper.logInfo(msg.toString());
//
//				getSchemeService().deliveWinRecord(betSchemeId);
//				
//				runTime = (new Date()).getTime() - date.getTime();// 操作耗时，单位毫秒
//				msg.setLength(0);
//				msg.append("方案[#").append(period.getLotteryType().getSchemeNumberPrefix()).append(betSchemeId).append("]处理完成，耗时").append(runTime).append("毫秒，还剩").append(
//						totalCount - count).append("个方案未处理。");
//				workHelper.logInfo(msg.toString());
//			}
//		} else {
//			workHelper.logWarn("找不到可以执行[" + cmd.getCmdName() + "]操作的方案。");
//		}
//
//		// 检查是否还有方案未执行
//		Integer count = getSchemeManager().statUnDelivePrize(periodId);
//		if (count != null && count > 0) {
//			workHelper.logWarn("还剩" + count + "个方案未执行。");
//		}
//		workHelper.logCmdSuccess();
//	}
	

}
