package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 跟单详情状态.
 * 
 */
public enum AutoFollowDetailState {
	SUCCESS("成功"), FAIL("失败");

	private String stateName;

	private AutoFollowDetailState(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * 获取状态描述
	 * 
	 * @return
	 */
	public String getStateName() {
		return this.stateName;
	}

	/**
	 * 获取状态值
	 * 
	 * @return
	 */
	public byte getStateValue() {
		return (byte) this.ordinal();
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
