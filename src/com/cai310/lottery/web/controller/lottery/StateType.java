package com.cai310.lottery.web.controller.lottery;

public enum StateType {
	FULL("已满员方案"),

	UNFULL("未满员方案"),

	BAODI("保底方案");

	private StateType(String text) {
		this.text = text;
	}

	private final String text;

	/**
	 * @return {@link #text}
	 */
	public String getText() {
		return text;
	}
}
