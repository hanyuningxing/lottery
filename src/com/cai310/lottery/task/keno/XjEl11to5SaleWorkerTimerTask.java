package com.cai310.lottery.task.keno;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5Scheme;
import com.cai310.lottery.service.lottery.keno.KenoManager;
@Component("xjel11to5SaleWorkerTimerTask")
public class XjEl11to5SaleWorkerTimerTask extends SaleWorkerTimerTask<XjEl11to5IssueData, XjEl11to5Scheme>{
	@Override
	@Resource(name = "xjel11to5KenoManagerImpl")
	public void setKenoManager(KenoManager<XjEl11to5IssueData, XjEl11to5Scheme> kenoManager) {
		this.kenoManager = kenoManager;
	}

	@Override
	public TaskType getTaskType() {
		return TaskType.XJEL11TO5;
	}
}
