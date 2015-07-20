package com.cai310.lottery.web.controller.admin.lottery;

/**
 * 方案查询排序类型
 * 
 */
public enum SchemeQueryOrder {
	ID("id", "发起时间"), PROCESS_RATE("processRate", "进度"), SCHEME_COST("schemeCost", "金额");

	private final String propName;
	private final String text;

	private SchemeQueryOrder(String propName, String text) {
		this.propName = propName;
		this.text = text;
	}

	public String getPropName() {
		return propName;
	}

	public String getText() {
		return text;
	}
}
