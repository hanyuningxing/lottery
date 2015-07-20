package com.cai310.lottery.common;

/**
 * 资金明细类型
 * 
 */
public enum DrawingOrderState {
	/** 审核中 */
	CHECKING("资金已冻结,审核中"),

	/** 汇款中 */
	PASSCHECK("审核通过,汇款中"),

	/** 提款成功 */
	PASS("已汇款，提款成功"),

	/** 提款失败 */
	FAILCHECK("已退款，提款失败");

	/** 类型名称 */
	private final String stateName;

	/**
	 * @param typeName {@link #stateName}
	 */
	private DrawingOrderState(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return {@link #stateName}
	 */
	public String getStateName() {
		return stateName;
	}
}
