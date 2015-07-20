package com.cai310.lottery.support;

/**
 * 奖金计算结果包装类
 * @author jack
 *
 */
public class PrizeForecastData {
	
	private int wonUnit;// 中奖注数
	private Double minPrize;// 最小奖金
	private Double maxPrize;// 最大奖金
	private String minPercent;//最小盈利百分比
	private String maxPercent;//最大盈利百分比


	public int getWonUnit() {
		return wonUnit;
	}

	public void setWonUnit(int wonUnit) {
		this.wonUnit = wonUnit;
	}

	public Double getMinPrize() {
		return minPrize;
	}

	public void setMinPrize(Double minPrize) {
		if(this.minPrize==null || this.minPrize>minPrize){
			this.minPrize = minPrize;
		}
	}

	public Double getMaxPrize() {
		return maxPrize;
	}

	public void setMaxPrize(Double maxPrize) {
		if(this.maxPrize==null || this.maxPrize<maxPrize){
			this.maxPrize = maxPrize;
		}
	}

	public String getMinPercent() {
		return minPercent;
	}

	public void setMinPercent(String minPercent) {
		this.minPercent = minPercent;
	}

	public String getMaxPercent() {
		return maxPercent;
	}

	public void setMaxPercent(String maxPercent) {
		this.maxPercent = maxPercent;
	}
	
	
}
