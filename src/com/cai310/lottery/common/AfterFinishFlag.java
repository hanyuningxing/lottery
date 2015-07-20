package com.cai310.lottery.common;

/**
 * 期结束后操作标志
 * 
 */
public enum AfterFinishFlag {

	/** 消费统计 */
	SaleAnalyse(1 << 0),

	/** 战绩统计 */
	UserScore(1 << 1),

	/** 已触发下一期追号 */
	NextIssueChasePlan(1 << 2),

	/** 销量统计 */
	Statute(1 << 3);

	private final int flagValue;

	private AfterFinishFlag(int flagValue) {
		this.flagValue = flagValue;
	}

	public int getFlagValue() {
		return flagValue;
	}
}
