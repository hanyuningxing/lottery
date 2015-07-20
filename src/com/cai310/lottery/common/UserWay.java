package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 用户类型.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum UserWay {
	/** 网站 */
	WEB("网站"),
	WAP("WAP"),
	/** 手机 */
	WPHONE("微软手机"),
	WPAD("微软Pad"),
	IPHONE("苹果手机"),
	IPAD("苹果Pad"),
	APHONE("安卓手机"),
	APAD("安卓PAd"),
	JAVA("JAVA通用"),
	
	TICKET("接票"),
	AGENT("代理");
	/** 名称 */
	private final String name;

	/**
	 * @param stateName {@link #drawName}
	 */
	private UserWay(String name) {
		this.name = name;
	}
	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

	public String getName() {
		return name;
	}
	public static UserWay getUserWayByName(String name){
		for (UserWay userWay : UserWay.values()) {
			if(userWay.name().equals(name))return userWay;
		}
		return null;
	}
	public String getTypeName() {
		return name;
	}

}
