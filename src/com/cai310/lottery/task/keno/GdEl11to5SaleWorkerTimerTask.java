package com.cai310.lottery.task.keno;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.keno.gdel11to5.GdEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.gdel11to5.GdEl11to5Scheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;

@Component("gdel11to5SaleWorkerTimerTask")
public class GdEl11to5SaleWorkerTimerTask extends SaleWorkerTimerTask<GdEl11to5IssueData, GdEl11to5Scheme> {

	@Resource(name = "gdel11to5KenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<GdEl11to5IssueData, GdEl11to5Scheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.GDEL11to5;
	}

}
