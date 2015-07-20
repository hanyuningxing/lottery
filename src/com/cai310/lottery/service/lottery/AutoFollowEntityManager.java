package com.cai310.lottery.service.lottery;

import java.math.BigDecimal;
import java.util.List;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.AutoFollowDetail;
import com.cai310.lottery.entity.lottery.AutoFollowOrder;
import com.cai310.lottery.entity.lottery.AutoFollowQueue;
import com.cai310.lottery.entity.lottery.AutoFollowQueueId;

public interface AutoFollowEntityManager {

	/**
	 * 获取跟单订单
	 * 
	 * @param id 跟单订单编号
	 * @return 跟单订单对象
	 */
	AutoFollowOrder getAutoFollowOrder(Long id);

	/**
	 * 保存跟单订单
	 * 
	 * @param order 跟单订单对象
	 * @return 保存后的跟单订单对象
	 */
	AutoFollowOrder saveAutoFollowOrder(AutoFollowOrder order);

	/**
	 * 获取跟单明细
	 * 
	 * @param id 跟单明细编号
	 * @return 跟单明细对象
	 */
	AutoFollowDetail getAutoFollowDetail(Long id);

	/**
	 * 保存跟单明细
	 * 
	 * @param detail 跟单明细对象
	 * @return 保存后的跟单明细对象
	 */
	AutoFollowDetail saveAutoFollowDetail(AutoFollowDetail detail);

	/**
	 * 获取跟单任务
	 * 
	 * @param id 编号
	 * @return 跟单任务对象
	 */
	AutoFollowQueue getAutoFollowQueue(AutoFollowQueueId id);

	/**
	 * 保存跟单任务
	 * 
	 * @param queue 跟单任务对象
	 * @return 保存后的跟单任务对象
	 */
	AutoFollowQueue saveAutoFollowQueue(AutoFollowQueue queue);

	/**
	 * 移除跟单任务
	 * 
	 * @param id 编号
	 */
	void removeAutoFollowQueue(AutoFollowQueueId id);

	/**
	 * 查询是否有人设置了自动跟单
	 * 
	 * @param sponsorUserId 发起人编号
	 * @param lotteryType 彩种
	 * @return 有或无
	 */
	boolean hasAutoFollow(Long sponsorUserId, Lottery lotteryType);

	/**
	 * 查询跟单任务队列ID集合
	 * 
	 * @return 跟单任务队列ID集合
	 */
	List<AutoFollowQueueId> findAutoFollowQueueId();

	/**
	 * 查询自动跟单订单编号集合
	 * 
	 * @param sponsorUserId 发起人编号
	 * @param lotteryType 彩种
	 * @param lotteryPlayType 彩种玩法
	 * @return 跟单订单编号集合
	 */
	List<Long> findAutoFollowOrderId(Long sponsorUserId, Lottery lotteryType, Byte lotteryPlayType);

	/**
	 * 统计该期该订单跟单总金额
	 * 
	 * @param orderId 订单编号
	 * @param periodId 期编号
	 * @return 总金额
	 */
	BigDecimal countFollowCost(Long orderId, Long periodId);

	/**
	 * 是否已经跟单
	 * 
	 * @param orderId 跟单订单
	 * @param schemeId 方案编号
	 * @return 是否已经跟单
	 */
	boolean isAlreadyFollow(final Long orderId, final Long schemeId);

	/**
	 * 查询已经跟单的订单编号集合
	 * 
	 * @param schemeId 跟单方案编号
	 * @return 订单编号集合
	 */
	List<Long> findFollowOrderId(final Long schemeId);
}
