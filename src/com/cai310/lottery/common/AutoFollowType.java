package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 自动跟单类型
 * 
 */
public enum AutoFollowType {
	/** 按金额 */
	FUND("按金额"),

	/** 按股份 */
	PERCEND("按股份");

	/** 类型名称 */
	private final String typeName;

	/**
	 * @param typeName {@link #typeName}
	 */
	private AutoFollowType(String typeName) {
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
