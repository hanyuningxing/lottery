package com.cai310.lottery.common;

import com.cai310.lottery.Constant;


/**
 * 用户活动类型类型.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum ActivityType {
	/** 注册赠送彩金 */
	REGISTERED_RECHARGE("手机验证赠送彩金"),

	/** 充值赠送彩金 */
	RECHARGE_ACTIVITY("充值赠送彩金"),
	
	/** 充值赠送彩金 */
	LOTTERYCARD_ACTIVITY("彩金卡充值");

	private final String activityTypeName;

	/**
	 * @param secretName {@link #secretName}
	 */
	private ActivityType(String activityTypeName) {
		this.activityTypeName = activityTypeName;
	}

	/**
	 * @return {@link #secretName}
	 */
	public String getActivityTypeName() {
		return activityTypeName;
	}
	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

}
