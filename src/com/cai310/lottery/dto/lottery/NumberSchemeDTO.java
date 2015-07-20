package com.cai310.lottery.dto.lottery;

import java.util.ArrayList;
import java.util.List;

/**
 * 数字彩方案发起的数据传输对象基类
 * 
 */
public class NumberSchemeDTO extends SchemeDTO {
	private static final long serialVersionUID = 554228590865957733L;

	/** 是否追号 */
	private boolean chase;

	/** 追号的总金额 */
	private Integer totalCostOfChase;

	/** 各期追号的倍数,空值表示该期停追号 */
	private List<Integer> multiplesOfChase = new ArrayList<Integer>();

	/** 是否机选追号 */
	private boolean randomOfChase;

	/** 每期机选注数 */
	private Integer randomUnitsOfChase;

	/** 是否有设置胆码 */
	private boolean hasDanOfChase;

	/** 机选胆码,使用json格式,如双色球:{red:['02','15'],blue:'06'} */
	private String danOfChase;

	/** 是否中奖后停止追号 */
	private boolean wonStopOfChase;

	/** 中奖金额大于该金额才停止追号 */
	private Integer prizeForWonStopOfChase;

	private String capacityProfit;
	
	public String getCapacityProfit() {
		return capacityProfit;
	}

	public void setCapacityProfit(String capacityProfit) {
		this.capacityProfit = capacityProfit;
	}

	/**
	 * @return {@link #chase}
	 */
	public boolean isChase() {
		return chase;
	}

	/**
	 * @param chase the {@link #chase} to set
	 */
	public void setChase(boolean chase) {
		this.chase = chase;
	}

	/**
	 * @return {@link #totalCostOfChase}
	 */
	public Integer getTotalCostOfChase() {
		return totalCostOfChase;
	}

	/**
	 * @param totalCostOfChase the {@link #totalCostOfChase} to set
	 */
	public void setTotalCostOfChase(Integer totalCostOfChase) {
		this.totalCostOfChase = totalCostOfChase;
	}

	/**
	 * @return {@link #multiplesOfChase}
	 */
	public List<Integer> getMultiplesOfChase() {
		return multiplesOfChase;
	}

	/**
	 * @param multiplesOfChase the {@link #multiplesOfChase} to set
	 */
	public void setMultiplesOfChase(List<Integer> multiplesOfChase) {
		this.multiplesOfChase = multiplesOfChase;
	}

	/**
	 * @return {@link #randomOfChase}
	 */
	public boolean isRandomOfChase() {
		return randomOfChase;
	}

	/**
	 * @param randomOfChase the {@link #randomOfChase} to set
	 */
	public void setRandomOfChase(boolean randomOfChase) {
		this.randomOfChase = randomOfChase;
	}

	/**
	 * @return {@link #randomUnitsOfChase}
	 */
	public Integer getRandomUnitsOfChase() {
		return randomUnitsOfChase;
	}

	/**
	 * @param randomUnitsOfChase the {@link #randomUnitsOfChase} to set
	 */
	public void setRandomUnitsOfChase(Integer randomUnitsOfChase) {
		this.randomUnitsOfChase = randomUnitsOfChase;
	}

	/**
	 * @return {@link #hasDanOfChase}
	 */
	public boolean isHasDanOfChase() {
		return hasDanOfChase;
	}

	/**
	 * @param hasDanOfChase the {@link #hasDanOfChase} to set
	 */
	public void setHasDanOfChase(boolean hasDanOfChase) {
		this.hasDanOfChase = hasDanOfChase;
	}

	/**
	 * @return {@link #danOfChase}
	 */
	public String getDanOfChase() {
		return danOfChase;
	}

	/**
	 * @param danOfChase the {@link #danOfChase} to set
	 */
	public void setDanOfChase(String danOfChase) {
		this.danOfChase = danOfChase;
	}

	/**
	 * @return {@link #wonStopOfChase}
	 */
	public boolean isWonStopOfChase() {
		return wonStopOfChase;
	}

	/**
	 * @param wonStopOfChase the {@link #wonStopOfChase} to set
	 */
	public void setWonStopOfChase(boolean wonStopOfChase) {
		this.wonStopOfChase = wonStopOfChase;
	}

	/**
	 * @return {@link #prizeForWonStopOfChase}
	 */
	public Integer getPrizeForWonStopOfChase() {
		return prizeForWonStopOfChase;
	}

	/**
	 * @param prizeForWonStopOfChase the {@link #prizeForWonStopOfChase} to set
	 */
	public void setPrizeForWonStopOfChase(Integer prizeForWonStopOfChase) {
		this.prizeForWonStopOfChase = prizeForWonStopOfChase;
	}

}
