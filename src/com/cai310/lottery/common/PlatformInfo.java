package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 平台类型.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum PlatformInfo {
	/** IOS苹果客户端 */
	IOS("0","E10ADC3949BA59ABBE56E057F20F883E","IOS苹果客户端"),

	/** Android安卓客户端 */
	ANDROID("1","E10ADC3949BA59ABBE56E057F20F883E","Android安卓客户端"),
	
	/** WAP */
	WAP("2","E10ADC3949BA59ABBE56E057F20F883E","WAP"),

	/** 网站 */
	WEB("3","E10ADC3949BA59ABBE56E057F20F883E","网站"),
	/** 接票 */
	TICKET("4","E10ADC3949BA59ABBE56E057F20F883E","接票");
	
	/** 名称 */
	private final String id;
	private final String password;
	private final String name;

	/**
	 * @param stateName {@link #drawName}
	 */
	private PlatformInfo(String id,String password,String name) {
		this.id = id;
		this.password = password;
		this.name = name;
	}
	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

	public String getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	public String getName() {
		return name;
	}
	public String getTypeName(){
		return this.name;
	}

}
