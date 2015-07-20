package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 资金明细类型.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum LuckDetailType {
	/** 注册送彩金 */
	RESIGER_LUCK("注册送彩金"),  //0

	/** 消费彩金 */
	USE_LUCK("消费彩金"), //1
	
	TENSENDONE("10送1"); //2

	
	
	/** 类型名称 */
	private final String typeName;
	

	/**
	 * @param typeName {@link #typeName}
	 */
	
	private LuckDetailType(String typeName) {
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
