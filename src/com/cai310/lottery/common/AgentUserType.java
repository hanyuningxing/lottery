package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum AgentUserType {
	AGENT("直属下属"),
	GROUP("所有下属");
	/** 类型名称 */
	private final String typeName;
	/**
	 * @param typeName {@link #typeName}
	 */
	private AgentUserType(String typeName){
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
