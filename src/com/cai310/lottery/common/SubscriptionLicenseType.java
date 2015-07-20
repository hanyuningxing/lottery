package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 方案认购许可类型.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum SubscriptionLicenseType {
	PUBLIC_LICENSE("公开"), PASSWORD_LICENSE("需认购密码");

	/** 认购许可名称 */
	private final String licenseName;

	/**
	 * @param licenseName {@link #licenseName}
	 */
	private SubscriptionLicenseType(String licenseName) {
		this.licenseName = licenseName;
	}

	/**
	 * @return {@link #licenseName}
	 */
	public String getLicenseName() {
		return licenseName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
