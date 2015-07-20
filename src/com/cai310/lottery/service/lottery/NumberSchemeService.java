package com.cai310.lottery.service.lottery;

import com.cai310.lottery.dto.lottery.NumberSchemeDTO;
import com.cai310.lottery.entity.lottery.NumberScheme;

public interface NumberSchemeService<T extends NumberScheme, E extends NumberSchemeDTO> extends SchemeService<T, E> {

	/**
	 * 追号处理
	 * 
	 * @param chasePlanId
	 * @param periodId
	 */
	public void handleChasePlan(long chasePlanId, long periodId);
}
