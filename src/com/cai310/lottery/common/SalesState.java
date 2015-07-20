package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 销售状态.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum SalesState {

	/** 未开售 */
	NOT_BEGUN("未开售"),

	/** 销售中 */
	ON_SALE("销售中"),

	/** 未出票截止 */
	UN_PRINTED_ENDED("未出票截止"),

	/** 已截止 */
	SALE_ENDED("已截止"),

	/** 已保底 */
	BAODI_EXECUTED("已保底"),

	/** 已完成交易 */
	PAYMENT_COMMITTED("已完成交易"),

	/** 已赠送积分 */
	SENDPRICE_ENDED("已赠送积分");

	/**
	 * 状态描述
	 */
	private String stateName;

	private SalesState(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * 获取状态值
	 */
	public byte getState() {
		return (byte) this.ordinal();
	}

	/**
	 * 获取状态描述
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * 根据状态值获取相应的销售状态
	 */
	public static SalesState getStateByValue(byte state) {
		return SalesState.values()[state];
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
