package com.cai310.lottery.service.lottery;

import java.util.List;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.PeriodData;

public interface PeriodDataEntityManager<T extends PeriodData> extends QueryManager {

	/**
	 * 获取销售期
	 * 
	 * @param id 销售期编号
	 * @return 销售期
	 */
	T getPeriodData(Long id);

	/**
	 * 持久化
	 * 
	 * @param obj
	 * @return
	 */
	T save(T obj);

	/**
	 * 创建一个实体实例
	 * 
	 * @return
	 */
	T getEntityInstance();
	
	public T getNewestResultPeriodData();
	
	public List<T> getResultPeriodData(final int firstResult, final int maxResults);
	
	/**
	 * @return 所管理彩种类型
	 * @see com.cai310.lottery.common.Lottery
	 */
	Lottery getLottery();
	
	
	Class<T> PeriodDataClass();
	
	
}
