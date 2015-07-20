package com.cai310.lottery.task.keno;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5Scheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;

@Component("sdel11to5SaleWorkerTimerTask")
public class SdEl11to5SaleWorkerTimerTask extends SaleWorkerTimerTask<SdEl11to5IssueData, SdEl11to5Scheme> {

	@Resource(name = "sdel11to5KenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<SdEl11to5IssueData, SdEl11to5Scheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.SDEL11to5;
	}

}
