package com.cai310.lottery.task.keno;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3IssueData;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3Scheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;

@Component("ahkuai3SaleWorkerTimerTask")
public class AhKuai3SaleWorkerTimerTask extends SaleWorkerTimerTask<AhKuai3IssueData, AhKuai3Scheme> {

	@Resource(name = "ahkuai3KenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<AhKuai3IssueData, AhKuai3Scheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.AHKUAI3;
	}

}
