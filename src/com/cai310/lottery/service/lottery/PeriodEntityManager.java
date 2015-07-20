package com.cai310.lottery.service.lottery;

import java.util.List;
import java.util.Map;

import com.cai310.lottery.common.AfterFinishFlag;
import com.cai310.lottery.common.AfterSaleFlag;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesState;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.orm.Page;
import com.cai310.orm.PropertyFilter;

/**
 * 期管理类
 * 
 */
public interface PeriodEntityManager {
	/**
	 * 开始销售，每个彩种的单复式分开操作
	 * 
	 * @param salesId 单复式销售分表编号
	 */
	void doBeginSales(PeriodSalesId salesId);

	/**
	 * 结束销售，每个彩种的单复式分开操作
	 * 
	 * @param salesId 单复式销售分表编号
	 */
	void doEndSales(PeriodSalesId periodSaleId);

	/**
	 * 删除期对象
	 */
	void removePeriod(Long periodId);

	/**
	 * 获取单复式销售分表
	 * 
	 * @param id 单复式销售分表编号
	 * @return 单复式销售分表
	 */
	PeriodSales getPeriodSales(PeriodSalesId id);

	/**
	 * 更新销售期
	 * 
	 * @param updateMap 更新的字段与值Map
	 * @param periodId 销售期编号
	 * @return 更新后的销售期对象
	 */
	Period updatePeriod(Map<String, Object> updateMap, long periodId);

	/**
	 * 更新售后标识，售后标识使用二进制表示，进行【与】运算
	 * 
	 * @param periodId 销售期编号
	 * @param afterSaleFlag 售后标识类型
	 * @return 更新后的销售期对象
	 */
	Period updateAfterSaleFlags(Long periodId, AfterSaleFlag afterSaleFlag);

	/**
	 * 一次更新多个售后标识，售后标识使用二进制表示，进行【与】运算
	 * 
	 * @param periodId 销售期编号
	 * @param afterSaleFlags 售后标识类型数组
	 * @return 更新后的销售期对象
	 */
	Period updateAfterSaleFlags(Long periodId, AfterSaleFlag[] afterSaleFlags);

	/**
	 * 更新结束后标识，结束后标识使用二进制表示，进行【与】运算
	 * 
	 * @param periodId 销售期编号
	 * @param afterSaleFlag 结束后标识类型
	 * @return 更新后的销售期对象
	 */
	Period updateAfterFinishFlags(Long periodId, AfterFinishFlag afterFinishFlag);

	/**
	 * 更新销售标记
	 * 
	 * @param periodSalesId 销售分表编号
	 * @param salesState 销售标识类型
	 * @return
	 */
	PeriodSales updatePeriodSalesState(PeriodSalesId periodSalesId, SalesState salesState);

	/**
	 * 更新单复式销售分表
	 * 
	 * @param updateMap 更新的字段与值Map
	 * @param PeriodSalesId 单复式销售分表编号
	 * @return
	 */
	PeriodSales updatePeriodSales(Map<String, Object> updateMap, PeriodSalesId PeriodSalesId);

	/**
	 * 获取销售期
	 * 
	 * @param id 销售期编号
	 * @return 销售期
	 */
	Period getPeriod(Long id);

	/**
	 * 根据期号查询期数据
	 * 
	 * @param periodNumber
	 * @return
	 */
	Period loadPeriod(Lottery lotteryType, String periodNumber);

	/**
	 * 保存销售期对象
	 * 
	 * @param Period 销售期对象
	 * @return 销售期对象
	 */
	Period savePeriod(Period period);

	/**
	 * 保存销售分表对象
	 * 
	 * @param PeriodSales 销售分表对象
	 * @return 销售分表对象
	 */
	PeriodSales savePeriodSales(PeriodSales periodSales);

	/**
	 * 保存销售分表对象
	 * 
	 * @param singlePeriodSales销售分表对象
	 * @param polyPeriodSales 销售分表对象
	 */
	void savePeriodSales(PeriodSales singlePeriodSales, PeriodSales polyPeriodSales);

	List<Period> findOldPeriods(Lottery lotteryType, int size, boolean cacheable);

	/**
	 * 查找所有当前销售期,默认启用查询缓存
	 * 
	 * @return 所有当前销售期
	 */
	List<Period> findCurrentPeriods(Lottery lotteryType);

	/**
	 * 查找所有当前销售期
	 * 
	 * @param cacheable 是否启用查询缓存
	 * @return 所有当前销售期
	 */
	List<Period> findCurrentPeriods(Lottery lotteryType, final boolean cacheable);
	
	public List<Period> findCurrentPeriods(final boolean cacheable);

	/**
	 * 查找所有在售期号
	 * 
	 * @return 所有在售期号
	 */
	List<String> findOnSalePeriodNumber(Lottery lotteryType);

	/**
	 * 获取上一销售期编号
	 * 
	 * @return 上一销售期编号
	 */
	Integer getPrevPeriodId(Lottery lotteryType);

	/**
	 * 查询数据库是否存在相同的期号
	 * 
	 * @param periodNumber 期号
	 * @return 是否存在相同的期号
	 */
	boolean exists(Lottery lotteryType, String periodNumber);

	/**
	 * 设为非当前销售期
	 * 
	 * @param periodId 期编号
	 */
	void hideCurrentPeriod(Long periodId);
	
	/**
	 * 设为当前销售期
	 * 
	 * @param periodId 期编号
	 */
	void showPeriodCurrent(Long periodId);

	/**
	 * 结束完成交易
	 * 
	 * @param PeriodSalesId 销售分表编号
	 */
	void endComminPayment(PeriodSalesId PeriodSalesId);

	/**
	 * 读取最新count条开奖期数据
	 * 
	 * @param count 读取的记录数
	 * @return
	 */
	List<Period> getDrawPeriodList(Lottery lotteryType, int count);

	/**
	 * 创建期次
	 * 
	 * @param period
	 * @param sales
	 * @return
	 */
	Period createNewPeriod(Period period, PeriodSales... sales);

	/**
	 * 
	 * @param page
	 * @param propertyFilterList
	 * @return
	 */
	Page<Period> list(Page<Period> page, List<PropertyFilter> propertyFilterList);
	
	/**
	 * 查找期对应的销售数据
	 * @param periodId
	 * @return
	 */
	List<PeriodSales> findPeriodSales(long periodId);
	
	/**
	 * 查找下一期的期号
	 * @param lotteryType
	 * @param periodId
	 * @return
	 */
	Period getNextPeriod(Lottery lotteryType,long periodId);
}
