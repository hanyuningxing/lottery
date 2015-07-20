package com.cai310.lottery.common;

import java.math.BigDecimal;

public interface PrizeSendType {

	/**
	 * @return 方案奖金
	 */
	public BigDecimal getSchemePrize();

	/**
	 * @return 方案是否盈利
	 */
	public boolean getProfit();

	/**
	 * @return 方案发起人佣金率
	 */
	public BigDecimal getOrganigerDeductRate();

	/**
	 * @return 方案发起人佣金提成
	 */
	public BigDecimal getOrganigerDeduct();

	/**
	 * @return 网站提成率
	 */
	public BigDecimal getCompanyDeductRate();

	/**
	 * @return 网站提成
	 */
	public BigDecimal getCompanyDeduct();

	/**
	 * @return 所有提成总金额
	 */
	public BigDecimal getTotalDeduct();

	/**
	 * @return 扣除所有提成后的可分配金额
	 */
	public BigDecimal getTotalReturn();

	/**
	 * 获取奖金分配详情（使用默认模板）
	 * 
	 * @return 奖金分配详情
	 */
	public String getPrizeCutDesc();

	/**
	 * 获取奖金分配详情（指定模板）
	 * 
	 * @param templateKey 奖金分配详情模板key
	 * @return 奖金分配详情
	 */
	public String getPrizeCutDesc(String templateKey);

}