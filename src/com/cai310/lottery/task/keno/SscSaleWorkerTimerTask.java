package com.cai310.lottery.task.keno;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.keno.ssc.SscIssueData;
import com.cai310.lottery.entity.lottery.keno.ssc.SscScheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;

@Component("sscSaleWorkerTimerTask")
public class SscSaleWorkerTimerTask extends SaleWorkerTimerTask<SscIssueData, SscScheme> {

	@Resource(name = "sscKenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<SscIssueData, SscScheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.SSC;
	}

}
