package com.cai310.lottery.task.keno;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5Scheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;

@Component("el11to5SaleWorkerTimerTask")
public class El11to5SaleWorkerTimerTask extends SaleWorkerTimerTask<El11to5IssueData, El11to5Scheme> {

	@Resource(name = "el11to5KenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<El11to5IssueData, El11to5Scheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.EL11to5;
	}

}
