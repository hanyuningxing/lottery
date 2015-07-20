package com.cai310.lottery.support.jclq;

import java.io.Serializable;

import com.cai310.lottery.support.Item;

public class JclqExtraItem implements Serializable, Comparable<JclqExtraItem> {
	private static final long serialVersionUID = -769274396953729506L;

	private Item item;

	private Double sp;

	/**
	 * @return {@link #item}
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item the {@link #item} to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return {@link #sp}
	 */
	public Double getSp() {
		return sp;
	}

	/**
	 * @param sp the {@link #sp} to set
	 */
	public void setSp(Double sp) {
		this.sp = sp;
	}

	public int compareTo(JclqExtraItem o) {
		Double sp1 = (this.getSp() != null) ? this.getSp() : Double.valueOf(0);
		Double sp2 = (o.getSp() != null) ? o.getSp() : Double.valueOf(0);
		return sp1.compareTo(sp2);
	}
}
