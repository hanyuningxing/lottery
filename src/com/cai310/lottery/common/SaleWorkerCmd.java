package com.cai310.lottery.common;

import org.apache.commons.lang.StringUtils;

/**
 * 销售相关操作类型
 * 
 */
public enum SaleWorkerCmd {
	/** 开始销售 */
	BeginSale(EventLogType.BeginSale),

	/** 暂停销售 */
	PauseSale(EventLogType.PauseSale),

	/** 恢复销售 */
	ResumeSale(EventLogType.ResumeSale),

	/** 未出票截止 */
	UnPrintedEndSale(EventLogType.UnPrintedEndSale),

	/** 结束销售 */
	EndSale(EventLogType.EndSale),

	/** 保底 */
	Baodi(EventLogType.Baodi),

	/** 完成交易 */
	CommitPayment(EventLogType.CommitPayment),

	/** 设为非当前销售 */
	HideIssue(EventLogType.HideIssue),

	/** 更新中奖 */
	UpdateResult(EventLogType.UpdateResult),

	/** 更新奖金 */
	UpdatePrize(EventLogType.UpdatePrize),

	/** 派发奖金 */
	DelivePrize(EventLogType.DelivePrize),

	/** 关闭期号 */
	CloseIssue(EventLogType.CloseIssue),

	/** 触发追号 */
	ChasePlan(EventLogType.ChasePlan),

	/** 触发下一期追号 */
	NextIssueChasePlan(EventLogType.NextIssueChasePlan),
	
	/** 销售统计 */
	SaleAnalyse(EventLogType.SaleAnalyse),
	
	/** 设为非当前销售 */
	ShowIssue(EventLogType.ShowIssue);

	private final EventLogType eventLogType;

	private SaleWorkerCmd(EventLogType eventLogType) {
		this.eventLogType = eventLogType;
	}

	public byte getStatus() {
		return eventLogType.getLogType();
	}

	public String getCmdName() {
		return eventLogType.getText();
	}

	/**
	 * 根据操作类型的状态值获取操作类型
	 * 
	 * @param status
	 * @return 操作类型
	 */
	public static SaleWorkerCmd valueOfStatus(byte status) {
		for (SaleWorkerCmd cmd : SaleWorkerCmd.values()) {
			if (cmd.eventLogType.getLogType() == status) {
				return cmd;
			}
		}
		return null;
	}

	/**
	 * 根据操作类型的名称获取操作类型（不区分大小写）
	 * 
	 * @param name
	 * @return 操作类型
	 */
	public static SaleWorkerCmd valueOfIgnoreCase(String name) {
		if (StringUtils.isNotBlank(name)) {
			name = name.trim();
			for (SaleWorkerCmd cmd : SaleWorkerCmd.values()) {
				if (cmd.name().equalsIgnoreCase(name)) {
					return cmd;
				}
			}
		}
		return null;
	}

	public EventLogType getEventLogType() {
		return eventLogType;
	}
}
