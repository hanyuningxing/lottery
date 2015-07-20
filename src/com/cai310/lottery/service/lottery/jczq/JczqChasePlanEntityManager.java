package com.cai310.lottery.service.lottery.jczq;

import com.cai310.lottery.entity.lottery.jczq.JczqChasePlan;

public interface JczqChasePlanEntityManager {
	/**
	 * 获取追号记录
	 * 
	 * @param id 追号ID
	 * @return 追号记录
	 */
	JczqChasePlan getChasePlan(Long id);

	/**
	 * 保存追号
	 * 
	 * @param entity 追号对象
	 * @return 保存后的追号对象
	 */
	JczqChasePlan saveChasePlan(JczqChasePlan entity);

	JczqChasePlan save(JczqChasePlan plan);
}
