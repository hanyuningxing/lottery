package com.cai310.lottery.task.dczc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.TaskState;
import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.service.info.TaskInfoDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcSchemeEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcSchemeService;

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
	private DczcSchemeService schemeService;

	@Autowired
	private DczcSchemeEntityManager schemeEntityManager;
	public void runTask() {
		logger.info("自动查找已截止方案处理交易任务执行...");
		try{
			List<Period> currPeriods = periodManager.findCurrentPeriods(Lottery.DCZC);
			if (currPeriods != null && !currPeriods.isEmpty()) {
				for (Period period : currPeriods) {
					List<Long> schemeIds = schemeEntityManager.findCanHandleTransactionSchemeId(period.getId());
					for (Long schemeId : schemeIds) {
						boolean isHandled = schemeService.handleTransaction(schemeId);
						if (isHandled) {
							logger.debug("已截止方案[#{}]处理完成.", schemeId);
						}
					}
				}
			}
			taskInfoDataEntityManager.updateTaskInfoData(TaskState.RUN, TaskType.AutoHandleTransaction, TaskType.AutoHandleTransaction.getTypeName()+"正常");
        }catch(Exception e){
        	e.printStackTrace();
        	taskInfoDataEntityManager.updateTaskInfoData(TaskState.EXCEPTION, TaskType.AutoHandleTransaction, TaskType.AutoHandleTransaction.getTypeName()+"出现错误：错误信息"+e.getMessage());
        }
	}
}
