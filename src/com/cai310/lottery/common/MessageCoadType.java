package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 优惠活动状态.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum MessageCoadType {
	registerValidator("注册手机短信验证"),

	Customer_SchemeCancel("方案未出票撤单用户通知"),

	Administrator_SchemeCancel("方案未出票撤单运营通知");
	
	private final String typeName;

	/**
	 * @param stateName
	 *            {@link #stateName}
	 */
	private MessageCoadType(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return {@link #stateName}
	 */
	public String getTypeName() {
		return typeName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

}
