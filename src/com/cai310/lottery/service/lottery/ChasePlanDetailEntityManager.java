package com.cai310.lottery.service.lottery;

import com.cai310.lottery.entity.lottery.ChasePlanDetail;

/**
 * 追号详情详细实体管理接口
 * 
 */
public interface ChasePlanDetailEntityManager {
	/**
	 * 获取追号详情记录
	 * 
	 * @param id 追号详情ID
	 * @return 追号详情记录
	 */
	ChasePlanDetail getChasePlanDetail(Long id);

	/**
	 * 保存追号详情
	 * 
	 * @param entity 追号详情对象
	 * @return 保存后的追号详情对象
	 */
	ChasePlanDetail saveChasePlanDetailDetail(ChasePlanDetail entity);
}
