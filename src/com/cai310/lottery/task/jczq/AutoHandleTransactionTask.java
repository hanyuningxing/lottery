package com.cai310.lottery.task.jczq;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.TaskState;
import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.service.info.TaskInfoDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.dczc.MatchNotEndException;
import com.cai310.lottery.service.lottery.impl.PrintEntityManagerImpl;
import com.cai310.lottery.service.lottery.jczq.JczqMatchEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqSchemeEntityManager;
import com.cai310.lottery.service.lottery.jczq.JczqSchemeService;
import com.cai310.lottery.service.lottery.ticket.impl.SynchronizedTicketStateManager;
import com.cai310.lottery.support.jczq.PassMode;

/**
 * 自动查找已截止方案处理交易的任务
 * 
 */
public class AutoHandleTransactionTask {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected TaskInfoDataEntityManager taskInfoDataEntityManager;

	@Autowired
	private PeriodEntityManager periodManager;

	@Autowired
	private JczqSchemeService schemeService;
	@Autowired
	private SynchronizedTicketStateManager synchronizedTicketStateManager;
	@Autowired
	private PrintEntityManagerImpl printEntityManagerImpl;
	@Autowired
	private JczqMatchEntityManager matchEntityManager;
	@Autowired
	private JczqSchemeEntityManager schemeEntityManager;

	public void runTask() {
		logger.info("自动查找已截止方案处理交易任务执行...");
		try {
			List<Period> currPeriods = periodManager.findCurrentPeriods(Lottery.JCZQ);
			if (currPeriods != null && !currPeriods.isEmpty()) {
				for (Period period : currPeriods) {
					List<Long> schemeIds = schemeEntityManager.findCanHandleTransactionSchemeId(period.getId());
					//cyy-c 2011-05-30 把完成交易设在官方截至时间
					int aheadEndMinute = -(JczqConstant.MATCH_AHEAD_TIME+1);
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.MINUTE, aheadEndMinute);
					Date endTime = calendar.getTime();
					List<String> endLineIds = matchEntityManager.findEndedMatchLineIds(period.getId(), endTime);
					JczqScheme scheme = null;
					for (Long schemeId : schemeIds) {
						scheme = schemeEntityManager.getScheme(schemeId);
						boolean isHandled = schemeService.handleTransaction(scheme,endLineIds);
						if (isHandled) {
							logger.warn("已截止方案[#{}]处理完成.", schemeId);
						}
					}
				}
			}
			taskInfoDataEntityManager.updateTaskInfoData(TaskState.RUN, TaskType.JczqAutoHandleTransaction,
					TaskType.JczqAutoHandleTransaction.getTypeName() + "正常");
		} catch (Exception e) {
			e.printStackTrace();
			taskInfoDataEntityManager.updateTaskInfoData(TaskState.EXCEPTION, TaskType.JczqAutoHandleTransaction,
					TaskType.JczqAutoHandleTransaction.getTypeName() + "出现错误：错误信息" + e.getMessage());
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			logger.warn(""+Thread.currentThread()+"sleep出错！");
		}
	}

	public void runSpTask() {
		List<Long> schemeIds = schemeEntityManager.findCanFrtchSpSchemeId();
		logger.info("自动检查同步sp值，查询到方案数：" + schemeIds.size());
		for (Long schemeId : schemeIds) {
			try {
				JczqScheme jczqScheme = schemeEntityManager.getScheme(schemeId);
				if (null != jczqScheme) {
					   boolean canFrtchSp = false;
						for (Map<String, Map<String, Double>> map : jczqScheme.getPrintAwardList()) {
							if (map == null) {
								canFrtchSp = true;
								break;
							}
						}
						if (canFrtchSp) {
							// /没有同步SP成功
							logger.info("正在自动同步sp值.......方案号：" + jczqScheme.getSchemeNumber());
							PrintInterface printInterface = printEntityManagerImpl.findPrintInterfaceBy(
									jczqScheme.getSchemeNumber(), jczqScheme.getLotteryType());
							//synchronizedTicketStateManager.synchronization_Sp_Jc(printInterface);
						}
				}
			} catch (Exception e) {
				logger.warn(e.getMessage());
				logger.warn("已截止方案[#{}]SP抓取出错.", schemeId);
				continue;
				// /因为是附加功能。有出错就下一个。不影响程序正常运行
			}
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			logger.warn(""+Thread.currentThread()+"sleep出错！");
		}
	}
}
