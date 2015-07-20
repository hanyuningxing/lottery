package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 接票状态类型.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum TicketSchemeState {
	/** 满员 */
	FULL("委托"),

	/** 成功 */
	SUCCESS("成功"),

	/** 撤销 */
	FAILD("失败");

	/** 状态名称 */
	private final String stateName;

	/**
	 * @param state {@link #stateName}
	 */
	private TicketSchemeState(String state) {
		this.stateName = state;
	}
	/**
	 * @return {@link #stateName}
	 */
	public String getStateName() {
		return stateName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
