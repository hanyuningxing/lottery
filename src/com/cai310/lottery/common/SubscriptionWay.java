package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 认购方式.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum SubscriptionWay {
	/** 发起时认购 */
	INITIATE("发起时认购"),

	/** 普通认购 */
	NORMAL("普通认购"),

	/** 自动跟单 */
	AUTOFOLLOW("自动跟单"),

	/** 从保底转换 */
	TRANSFORM_BAODI("从保底转换"),

	/** 网站保底 */
	SITE_BAODI("网站保底");

	/** 类型名称 */
	private final String followTypeName;

	/**
	 * @param followTypeName {@link #followTypeName}
	 */
	private SubscriptionWay(String followTypeName) {
		this.followTypeName = followTypeName;
	}

	/**
	 * @return {@link #followTypeName}
	 */
	public String getFollowTypeName() {
		return followTypeName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
