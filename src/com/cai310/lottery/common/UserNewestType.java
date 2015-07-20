package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 用户最新动态类型枚举
 * @author jack
 *
 */
public enum UserNewestType {

	SUBMIT("发单"),
	JOIN("认购"),
	WON("中奖");
	
	private final String typeName;
	
	private UserNewestType(String typeName){
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}
	
	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
