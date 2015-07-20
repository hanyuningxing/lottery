package com.cai310.lottery.task.keno;

import javax.annotation.Resource;

import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfIssueData;
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfScheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;
import org.springframework.stereotype.Component;

@Component("klsfSaleWorkerTimerTask")
public class KlsfSaleWorkerTimerTask extends SaleWorkerTimerTask<KlsfIssueData, KlsfScheme> {

	@Resource(name = "klsfKenoManagerImpl")
	@Override
	public void setKenoManager(KenoManager<KlsfIssueData, KlsfScheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.KLSF;
	}

}
