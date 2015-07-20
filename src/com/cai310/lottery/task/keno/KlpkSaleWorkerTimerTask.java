package com.cai310.lottery.task.keno;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkIssueData;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkScheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;

@Component("klpkSaleWorkerTimerTask")
public class KlpkSaleWorkerTimerTask extends SaleWorkerTimerTask<KlpkIssueData, KlpkScheme> {

	@Resource(name = "klpkKenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<KlpkIssueData, KlpkScheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.KLPK;
	}

}
