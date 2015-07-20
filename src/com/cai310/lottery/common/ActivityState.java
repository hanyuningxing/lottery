package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 优惠活动状态.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum ActivityState {
	/** 进行中 */
	UNDER_WAY("进行中"),

	/** 取消 */
	CANCEL("活动取消"),

	/** 成功 */
	 SUCCESS("参与成功"),

	/** 参与失败 */
	 FAILD("参与失败"),
	
	 END("活动结束");

	private final String stateName;

	/**
	 * @param stateName {@link #stateName}
	 */
	private ActivityState(String stateName) {
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
