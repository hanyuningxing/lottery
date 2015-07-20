package com.cai310.lottery.service.lottery;

import java.util.List;

import com.cai310.lottery.entity.lottery.AutoFollowQueueId;

/**
 * 自动跟单服务接口
 * 
 */
public interface AutoFollowService {

	/**
	 * 执行自动跟单
	 * 
	 * @param queueId 跟单任务编号
	 * @param orderId 跟单订单编号
	 */
	void autoFollow(AutoFollowQueueId queueId, Long orderId);

	/**
	 * 查询自动跟单订单编号集合
	 * 
	 * @param queueId 跟单任务编号
	 * @return 自动跟单订单编号集合
	 */
	List<Long> findAutoFollowOrderId(AutoFollowQueueId queueId);

	/**
	 * 完成自动跟单
	 * 
	 * @param queueId 跟单任务编号
	 */
	void autoFollowFinish(AutoFollowQueueId queueId);
}
