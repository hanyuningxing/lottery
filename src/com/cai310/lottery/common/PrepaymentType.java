package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 预付款类型.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum PrepaymentType {
	/** 认购 */
	SUBSCRIPTION("认购"),

	/** 保底 */
	BAODI("保底"),

	/** 追号 */
	CHASE("追号");

	/** 类型名称 */
	private final String typeName;

	/**
	 * @param typeName {@link #typeName}
	 */
	private PrepaymentType(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return {@link #typeName}
	 */
	public String getTypeName() {
		return typeName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
