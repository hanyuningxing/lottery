package com.cai310.lottery.service.lottery;

import java.util.List;

import com.cai310.lottery.common.ChaseState;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.ChasePlan;

/**
 * 追号相关实体管理接口
 * 
 */
public interface ChasePlanEntityManager {
	/**
	 * 获取追号记录
	 * 
	 * @param id 追号ID
	 * @return 追号记录
	 */
	ChasePlan getChasePlan(Long id);

	/**
	 * 保存追号
	 * 
	 * @param entity 追号对象
	 * @return 保存后的追号对象
	 */
	ChasePlan saveChasePlan(ChasePlan entity);

	/**
	 * 查询进行中的追号编号
	 * 
	 * @param curPeriodId 已追的最新ID
	 * @param lotteryType 采种类型
	 * @param wonStop 中后停追标志
	 * @return
	 */
	public List<Long> findChasePlanIdOfRunning(long curPeriodId, Lottery lotteryType, Boolean wonStop);
	/**
	 * 查询用户的追号
	 * @param userId
	 * @param start
	 * @param count
	 * @param chaseState
	 * @param lotteryType
	 * @return
	 */
	public List<ChasePlan> findChasePlan(final Long userId, final Integer start, final Integer count, final ChaseState chaseState, final Lottery lotteryType);

	/**
	 * 统计未处理的追号数目
	 * 
	 * @param curPeriodId
	 * @return
	 */
	public Integer statUnHandleChase(long curPeriodId);
}
