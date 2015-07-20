package com.cai310.lottery.service.lottery;

import java.util.List;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.GroupNumMiss;
import com.cai310.lottery.entity.lottery.MissDataInfo;
import com.cai310.lottery.entity.lottery.NumberAnalyse;

public interface MissDataEntityManager<T extends MissDataInfo> {

	public void saveBatchMissData(List<T> dataList);

	public T getMissDataByPeriodId(Long periodId);

	public T getMissData(Long id);

	public T getLastMissData();

	/**
	 * 获取最新的遗漏数据
	 * 
	 * @param count
	 *            期数,为0时查出全部
	 * @return
	 */
	public List<T> getLastMissDatas(int count);

	public NumberAnalyse saveNumberAnalyse(NumberAnalyse data);

	public NumberAnalyse getNumberAnalyseByLottery(Lottery lottery);

	public NumberAnalyse getNumberAnalyseById(Long id);

	public GroupNumMiss saveGroupNumMiss(GroupNumMiss data);

	public GroupNumMiss getGroupNumMiss(Lottery lottery);

	public GroupNumMiss getGroupNumMiss(Long id);
}
