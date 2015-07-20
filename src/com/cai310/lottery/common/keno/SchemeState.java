package com.cai310.lottery.common.keno;


public enum SchemeState {

	INITED("发起中"), SUCCESS("成功"), CANCEL("撤单");

	private String stateName;

	private SchemeState(String stateName) {
		this.stateName = stateName;
	}

	public byte getStatevalue() {
		return (byte) this.ordinal();
	}

	public String getStateName() {
		return this.stateName;
	}
}
