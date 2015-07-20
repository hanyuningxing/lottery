package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 期状态.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum PeriodState {
	/** 初始状态 */
	Inited("初始"),

	/** 开始 */
	Started("开售"),

	/** 暂停 */
	Paused("暂停"),

	/** 截止 */
	SaleEnded("截止"),

	/** 结束 */
	Finished("结束");

	/**
	 * 状态描述
	 */
	private String stateName;

	private PeriodState(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * 获取状态描述
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * 获取状态值
	 */
	public byte getState() {
		return (byte) this.ordinal();
	}

	/**
	 * 根据状态值获取相应的销售状态
	 */
	public static PeriodState getStateByValue(byte state) {
		return PeriodState.values()[state];
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
