package com.cai310.lottery.service.lottery.keno;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.NumberScheme;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;
import com.cai310.lottery.exception.DataException;

public interface KenoManager<I extends KenoPeriod, S extends NumberScheme> {
	/**
	 * 销售流程
	 * @throws DataException 
	 */
	public void sale() throws DataException;
	
	public void ticketSynchroned() throws DataException;

	/**
	 * 获取当前期缓存
	 * 
	 * @return
	 */
	public I getCurrentData();
	
	/**
	 * 获取当前期缓存
	 * 
	 * @return
	 */
	public I getKenoPeriod(Long id);
	/**
	 * @return 所管理彩种类型
	 * @see com.cai310.lottery.common.Lottery
	 */
	Lottery getLottery();
}
