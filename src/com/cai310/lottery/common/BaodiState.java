package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 方案保底状态. <br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum BaodiState {
	/** 正常状态 */
	NORMAL("正常状态"),

	/** 已使用 */
	USED("已使用"),

	/** 取消 */
	CANCEL("取消");

	/** 状态名称 */
	private final String stateName;

	/**
	 * @param stateName {@link #stateName}
	 */
	private BaodiState(String stateName) {
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
