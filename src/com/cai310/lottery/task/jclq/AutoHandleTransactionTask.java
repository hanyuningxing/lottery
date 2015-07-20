package com.cai310.lottery.task.jclq;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.TaskState;
import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.service.info.TaskInfoDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.impl.PrintEntityManagerImpl;
import com.cai310.lottery.service.lottery.jclq.JclqMatchEntityManager;
import com.cai310.lottery.service.lottery.jclq.JclqSchemeEntityManager;
import com.cai310.lottery.service.lottery.jclq.JclqSchemeService;
import com.cai310.lottery.service.lottery.ticket.impl.SynchronizedTicketStateManager;
import com.cai310.lottery.support.jclq.PassMode;

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
	private JclqMatchEntityManager matchEntityManager;

	@Autowired
	private JclqSchemeService schemeService;
	@Autowired
	private SynchronizedTicketStateManager synchronizedTicketStateManager;
	@Autowired
	private PrintEntityManagerImpl printEntityManagerImpl;

	@Autowired
	private JclqSchemeEntityManager schemeEntityManager;

	public void runTask() {
		logger.info("自动查找已截止方案处理交易任务执行...");
		try {
			List<Period> currPeriods = periodManager.findCurrentPeriods(Lottery.JCLQ);
			if (currPeriods != null && !currPeriods.isEmpty()) {
				for (Period period : currPeriods) {
					//cyy-c 2011-05-30 把完成交易设在官方截至时间
					int aheadEndMinute = -JclqConstant.MATCH_AHEAD_TIME;
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.MINUTE, aheadEndMinute);
					Date endTime = calendar.getTime();
					
					List<String> endLineIds = matchEntityManager.findEndedMatchLineIds(period.getId(), endTime);
					List<Long> schemeIds = schemeEntityManager.findCanHandleTransactionSchemeId(period.getId());
					JclqScheme jclqScheme=null;
					for (Long schemeId : schemeIds) {
						jclqScheme = schemeEntityManager.getScheme(schemeId);
						boolean isHandled = schemeService.handleTransaction(jclqScheme,endLineIds);
						if (isHandled) {
							logger.warn("已截止方案[#{}]处理完成.", schemeId);
						}
					}
				}
			}
			taskInfoDataEntityManager.updateTaskInfoData(TaskState.RUN, TaskType.JclqAutoHandleTransaction,
					TaskType.JclqAutoHandleTransaction.getTypeName() + "正常");
		} catch (Exception e) {
			e.printStackTrace();
			taskInfoDataEntityManager.updateTaskInfoData(TaskState.EXCEPTION, TaskType.JclqAutoHandleTransaction,
					TaskType.JclqAutoHandleTransaction.getTypeName() + "出现错误：错误信息" + e.getMessage());
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			logger.warn(""+Thread.currentThread()+"sleep出错！");
		}
	}

	public void runSpTask() {
		// //在这里加入如果没有SP的方案更新
		List<Long> schemeIds = schemeEntityManager.findCanFrtchSpSchemeId();
		for (Long schemeId : schemeIds) {
			try {
				JclqScheme jclqScheme = schemeEntityManager.getScheme(schemeId);
				if (null != jclqScheme) {
					boolean canFrtchSp = false;
					for (Map<String, Map<String, Double>> map : jclqScheme.getPrintAwardList()) {
						if (map == null) {
							canFrtchSp = true;
							break;
						}
					}
					if (canFrtchSp) {
						// /没有同步SP成功
						logger.info("正在自动同步sp值.......方案号：" + jclqScheme.getSchemeNumber());
						PrintInterface printInterface = printEntityManagerImpl.findPrintInterfaceBy(
								jclqScheme.getSchemeNumber(), jclqScheme.getLotteryType());
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
