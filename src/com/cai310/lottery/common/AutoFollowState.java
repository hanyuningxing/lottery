package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 自动跟单状态.
 * 
 */
public enum AutoFollowState {
	/** 正常 */
	RUNNING("正常"),

	/** 停止 */
	STOPED("停止");

	private String stateName;

	private AutoFollowState(String stateName) {
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
