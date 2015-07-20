package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum AgentDetailType {
	/** 认购 */
	BUY("认购"),

	/** 用户提款 */
	DRAWING("用户提款"),
	
	/** 奖金 */
	PRIZE("奖金"),

	/** 在线充值 */
	PAY("在线充值"),
	
	/** 佣金 */
	REBATE("佣金"),
	
	/** 赠送 */
	LUCK("赠送");
	
	/** 类型名称 */
	private final String typeName;

	/**
	 * @param typeName {@link #typeName}
	 */
	
	private AgentDetailType(String typeName) {
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
