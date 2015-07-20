package com.cai310.lottery.support.jclq;

import java.io.Serializable;
import java.util.List;

/**
 * 竞彩篮球复式内容Bean
 * 
 */
public class JclqCompoundContent implements Serializable {
	private static final long serialVersionUID = -1902401847461051686L;

	/** 选择的场次内容 */
	private List<JclqMatchItem> items;

	/** 胆码最小命中数 */
	private Integer danMinHit;

	/** 胆码最大命中数 */
	private Integer danMaxHit;

	/** 最小奖金预测(单倍投)*/
	private Double bestMinPrize;
	
	/** 最大奖金预测(单倍投)*/
	private Double bestMaxPrize;
	
	/**
	 * @return {@link #items}
	 */
	public List<JclqMatchItem> getItems() {
		return items;
	}

	/**
	 * @param items the {@link #items} to set
	 */
	public void setItems(List<JclqMatchItem> items) {
		this.items = items;
	}

	/**
	 * @return {@link #danMinHit}
	 */
	public Integer getDanMinHit() {
		return danMinHit;
	}

	/**
	 * @param danMinHit the {@link #danMinHit} to set
	 */
	public void setDanMinHit(Integer danMinHit) {
		this.danMinHit = danMinHit;
	}

	/**
	 * @return {@link #danMaxHit}
	 */
	public Integer getDanMaxHit() {
		return danMaxHit;
	}

	/**
	 * @param danMaxHit the {@link #danMaxHit} to set
	 */
	public void setDanMaxHit(Integer danMaxHit) {
		this.danMaxHit = danMaxHit;
	}

	public Double getBestMinPrize() {
		return bestMinPrize;
	}

	public void setBestMinPrize(Double bestMinPrize) {
		this.bestMinPrize = bestMinPrize;
	}

	public Double getBestMaxPrize() {
		return bestMaxPrize;
	}

	public void setBestMaxPrize(Double bestMaxPrize) {
		this.bestMaxPrize = bestMaxPrize;
	}

}
