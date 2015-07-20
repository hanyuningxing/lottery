package com.cai310.lottery.service.lottery;

import java.util.Set;

import com.cai310.lottery.common.SaleWorkerCmd;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;

public interface SalesControllerService extends ControllerService {

	/**
	 * 开售
	 * 
	 * @param periodSalesId
	 */
	void beginSaleWork(PeriodSalesId periodSalesId);

	/**
	 * 暂停销售
	 * 
	 * @param periodId
	 */
	void pauseSaleWork(Long periodId);

	/**
	 * 恢复销售
	 * 
	 * @param periodId
	 */
	void resumeSaleWork(Long periodId);

	/**
	 * 截止销售
	 * 
	 * @param periodSalesId
	 */
	void endSaleWork(PeriodSalesId periodSalesId);

	/**
	 * 设为非当前期
	 * 
	 * @param periodId
	 */
	public void hideIssueWork(long periodId);
	
	/**
	 * 设为当前期
	 * 
	 * @param periodId
	 */
	public void showIssueWork(long periodId);

	/**
	 * 执行保底
	 * 
	 * @param issueSaleId
	 */
	void baodiWork(PeriodSalesId periodSalesId);

	/**
	 * 完成交易
	 * 
	 * @param periodSalesId
	 */
	void commitPaymentWork(PeriodSalesId periodSalesId);


	/**
	 * 结束期次
	 * 
	 * @param periodId
	 */
	void closePeriodWork(Long periodId);
	
	/**
	 * 销售统计
	 * 
	 * @param periodSalesId
	 */
	void saleAnalyseWork(long periodId);
	
	

	public boolean canRun(Long periodId, SalesMode salesMode, SaleWorkerCmd cmd);

	public Set<SaleWorkerCmd> getCanRunCmds(Period period);

	public Set<SaleWorkerCmd> getCanRunCmds(PeriodSales periodSales);
}
