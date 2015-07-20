package com.cai310.lottery.common.keno;

public enum StatisticsType {

	ISSUE("期统计"),

	MONTH("月统计"),

	YEAR("年统计");

	private String typeName;

	private StatisticsType(String typeName) {
		this.typeName = typeName;
	}

	public byte getType() {
		return (byte) this.ordinal();
	}

	public String getTypeName() {
		return this.typeName;
	}

	public static StatisticsType valueOf(byte type) {
		return StatisticsType.values()[type];
	}
}
