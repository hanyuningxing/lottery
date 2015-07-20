package com.cai310.lottery.service.lottery;

import com.cai310.lottery.entity.lottery.NumberScheme;

/**
 * 数字彩方案相关实现管理接口.
 * 
 * @param <T> 方案类型
 */
public interface NumberSchemeEntityManager<T extends NumberScheme> extends SchemeEntityManager<T> {
	/**
	 * 
	 * @param chaseId
	 * @return
	 */
	public T getLastChaseScheme(long chaseId);
}
