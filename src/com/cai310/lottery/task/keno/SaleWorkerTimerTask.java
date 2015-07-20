package com.cai310.lottery.task.keno;

import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.TaskState;
import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.info.TaskInfoDataEntityManager;
import com.cai310.lottery.service.lottery.keno.KenoManager;

public abstract class SaleWorkerTimerTask<I extends KenoPeriod, S extends NumberScheme> {

	protected KenoManager<I, S> kenoManager;
	@Autowired
	protected TaskInfoDataEntityManager taskInfoDataEntityManager;
	public abstract void setKenoManager(KenoManager<I, S> kenoManager);
	
	public abstract TaskType getTaskType();

	/**
	 * 销售流程线程
	 */
	public void work() {
		try {
			kenoManager.sale();
			taskInfoDataEntityManager.updateTaskInfoData(TaskState.RUN, getTaskType(), getTaskType().getTypeName()+"正常");
		} catch (DataException e) {
			e.printStackTrace();
			taskInfoDataEntityManager.updateTaskInfoData(TaskState.EXCEPTION, getTaskType(), getTaskType().getTypeName()+"出现错误：错误信息"+e.getMessage());
		}
	}
	public void ticketSynchroned(){
		try {
			kenoManager.ticketSynchroned();
		} catch (DataException e) {
			e.printStackTrace();
			taskInfoDataEntityManager.updateTaskInfoData(TaskState.EXCEPTION, getTaskType(), getTaskType().getTypeName()+"出现错误：错误信息"+e.getMessage());
		}
	}
}