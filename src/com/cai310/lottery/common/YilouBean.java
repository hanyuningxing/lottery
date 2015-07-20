package com.cai310.lottery.common;

public class YilouBean {
	private String key;
	private int show;// 出现次数
	private double willShow;// 理论出现次数
	private double showPercent;// 出现频率
	private double avgYilou;// 平均遗漏
	private int maxYilou;// 最大遗漏
	private int lastYilou;// 上次遗漏
	private int curYilou;// 本次遗漏
	private double willHappen;// 欲出几率
	private double huibu;// 回补几率
	private String periodAbout;// 最大遗漏期间\

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the show
	 */
	public int getShow() {
		return show;
	}

	/**
	 * @param show
	 *            the show to set
	 */
	public void setShow(int show) {
		this.show = show;
	}

	/**
	 * @return the willShow
	 */
	public double getWillShow() {
		return willShow;
	}

	/**
	 * @param d
	 *            the willShow to set
	 */
	public void setWillShow(double d) {
		this.willShow = d;
	}

	/**
	 * @return the showPercent
	 */
	public double getShowPercent() {
		return showPercent;
	}

	/**
	 * @param showPercent
	 *            the showPercent to set
	 */
	public void setShowPercent(double showPercent) {
		this.showPercent = showPercent;
	}

	/**
	 * @return the avgYilou
	 */
	public double getAvgYilou() {
		return avgYilou;
	}

	/**
	 * @param avgYilou
	 *            the avgYilou to set
	 */
	public void setAvgYilou(double avgYilou) {
		this.avgYilou = avgYilou;
	}

	/**
	 * @return the maxYilou
	 */
	public int getMaxYilou() {
		return maxYilou;
	}

	/**
	 * @param maxYilou
	 *            the maxYilou to set
	 */
	public void setMaxYilou(int maxYilou) {
		this.maxYilou = maxYilou;
	}

	/**
	 * @return the lastYilou
	 */
	public int getLastYilou() {
		return lastYilou;
	}

	/**
	 * @param lastYilou
	 *            the lastYilou to set
	 */
	public void setLastYilou(int lastYilou) {
		this.lastYilou = lastYilou;
	}

	/**
	 * @return the curYilou
	 */
	public int getCurYilou() {
		return curYilou;
	}

	/**
	 * @param curYilou
	 *            the curYilou to set
	 */
	public void setCurYilou(int curYilou) {
		this.curYilou = curYilou;
	}

	/**
	 * @return the willHappen
	 */
	public double getWillHappen() {
		return willHappen;
	}

	/**
	 * @param willHappen
	 *            the willHappen to set
	 */
	public void setWillHappen(double willHappen) {
		this.willHappen = willHappen;
	}

	/**
	 * @return the huibu
	 */
	public double getHuibu() {
		return huibu;
	}

	/**
	 * @param huibu
	 *            the huibu to set
	 */
	public void setHuibu(double huibu) {
		this.huibu = huibu;
	}

	/**
	 * @return the periodAbout
	 */
	public String getPeriodAbout() {
		return periodAbout;
	}

	/**
	 * @param periodAbout
	 *            the periodAbout to set
	 */
	public void setPeriodAbout(String periodAbout) {
		this.periodAbout = periodAbout;
	}

}