package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 预付款状态.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum PrepaymentState {
	/** 等待中 */
	AWAIT("等待中"),

	/** 取消 */
	CANCEL("取消"),

	/** 成功 */
	SUCCESS("成功"),

	NONE("无效");

	/** 状态名称 */
	private final String stateName;

	/**
	 * @param stateName {@link #stateName}
	 */
	private PrepaymentState(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return {@link #stateName}
	 */
	public String getStateName() {
		return stateName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
