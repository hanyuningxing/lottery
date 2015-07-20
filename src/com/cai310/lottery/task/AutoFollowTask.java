package com.cai310.lottery.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.TaskState;
import com.cai310.lottery.common.TaskType;
import com.cai310.lottery.entity.lottery.AutoFollowQueueId;
import com.cai310.lottery.service.info.TaskInfoDataEntityManager;
import com.cai310.lottery.service.lottery.AutoFollowEntityManager;
import com.cai310.lottery.service.lottery.AutoFollowService;

public class AutoFollowTask {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected AutoFollowEntityManager autoFollowEntityManager;
	
	@Autowired
	protected TaskInfoDataEntityManager taskInfoDataEntityManager;

	@Autowired
	protected AutoFollowService autoFollowService;
	//TODO 自动跟单 子钱包扩展
	public void autoFollow() {
		logger.info("处理自动跟单任务执行...");
        try{
			List<AutoFollowQueueId> idList = autoFollowEntityManager.findAutoFollowQueueId();
			if (idList != null && !idList.isEmpty()) {
				for (AutoFollowQueueId queueId : idList) {
					List<Long> orderIdList = autoFollowService.findAutoFollowOrderId(queueId);
					if (orderIdList != null && !orderIdList.isEmpty()) {
						for (Long orderId : orderIdList) {
							autoFollowService.autoFollow(queueId, orderId);
						}
					}
					autoFollowService.autoFollowFinish(queueId);
				}
			}
			taskInfoDataEntityManager.updateTaskInfoData(TaskState.RUN, TaskType.AutoFollow, TaskType.AutoFollow.getTypeName()+"正常");
        }catch(Exception e){
        	e.printStackTrace();
        	taskInfoDataEntityManager.updateTaskInfoData(TaskState.EXCEPTION, TaskType.AutoFollow, TaskType.AutoFollow.getTypeName()+"出现错误：错误信息"+e.getMessage());
        }
	}
}
