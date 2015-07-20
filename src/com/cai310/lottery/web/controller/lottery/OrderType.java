package com.cai310.lottery.web.controller.lottery;

public enum OrderType {
	PROCESS_RATE_DESC("方案进度↓"),

	SCHEME_COST_ASC("方案金额↑"),

	SCHEME_COST_DESC("方案金额↓"),

	CREATE_TIME_DESC("发起时间↓"),
	
	PROCESS_RATE_ASC("方案进度↑"),
	
	CREATE_TIME_ASC("发起时间↑");

	private final String text;

	private OrderType(String text) {
		this.text = text;
	}

	/**
	 * @return {@link #text}
	 */
	public String getText() {
		return text;
	}
}
