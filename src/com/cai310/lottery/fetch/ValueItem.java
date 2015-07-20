package com.cai310.lottery.fetch;

public class ValueItem {
	/** 值 */
	private Double value;

	/** 变化：0表示无变化，1表示上升，-1表示下降 */
	private int chg;

	/**
	 * @param value the {@link #value}
	 */
	public ValueItem(Double value) {
		this(value, 0);
	}

	/**
	 * @param value the {@link #value}
	 * @param chg the {@link #chg}
	 */
	public ValueItem(Double value, int chg) {
		super();
		this.value = value;
		this.chg = chg;
	}

	/**
	 * @return the {@link #value}
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @return the {@link #chg}
	 */
	public int getChg() {
		return chg;
	}
}
