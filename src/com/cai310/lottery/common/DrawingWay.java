package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 提款方式.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum DrawingWay {
	/** 银行 */
	BANK("银行"),

	/** 支付宝 */
	ALIPAY("支付宝");
	
	
	/** 状态名称 */
	private final String drawName;

	/**
	 * @param stateName {@link #drawName}
	 */
	private DrawingWay(String drawName) {
		this.drawName = drawName;
	}

	/**
	 * @return {@link #drawName}
	 */
	public String getDrawName() {
		return drawName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

}
