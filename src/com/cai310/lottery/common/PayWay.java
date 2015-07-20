package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 提款方式.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum PayWay {
	/** 易宝 */
	YEEPAY("易宝"),

	/** 支付宝 */
	ALIPAY("支付宝"),

	/** 支付宝 */
	ALIPAY_PHONE("手机支付宝");
	
	/** 状态名称 */
	private final String payName;

	/**
	 * @param stateName {@link #drawName}
	 */
	private PayWay(String payName) {
		this.payName = payName;
	}

	/**
	 * @return {@link #drawName}
	 */
	public String getPayName() {
		return payName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
	public static PayWay getPayWayByName(String name){
		for (PayWay userWay : PayWay.values()) {
			if(userWay.name().equals(name))return userWay;
		}
		return null;
	}
}
