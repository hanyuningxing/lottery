package com.cai310.lottery.task.keno;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhIssueData;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhScheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;

@Component("qyhSaleWorkerTimerTask")
public class QyhSaleWorkerTimerTask extends SaleWorkerTimerTask<QyhIssueData, QyhScheme> {

	@Resource(name = "qyhKenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<QyhIssueData, QyhScheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.QYH;
	}

}
