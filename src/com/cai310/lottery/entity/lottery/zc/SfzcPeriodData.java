package com.cai310.lottery.entity.lottery.zc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * 胜负足彩期数据实体类
 * 
 * 
 * 
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SFZC_PERIOD_DATA")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SfzcPeriodData extends ZcPeriodData {
	
	protected Long totalSales_14; // 总销售额
	protected Integer firstPrize_14; // 一等奖奖金
	protected int firstWinUnits_14;// 一等奖注数
	protected Integer secondPrize_14; // 二等奖奖金
	protected int secondWinUnits_14;// 二等奖注数
	protected Long prizePool_14; // 奖池金额

	@Column(name = "totalSales_14")
	public Long getTotalSales_14() {
		return totalSales_14;
	}

	public void setTotalSales_14(Long totalSales_14) {
		this.totalSales_14 = totalSales_14;
	}

	
	@Column(name = "firstPrize_14")
	public Integer getFirstPrize_14() {
		return firstPrize_14;
	}

	public void setFirstPrize_14(Integer firstPrize_14) {
		this.firstPrize_14 = firstPrize_14;
	}

	@Column(name = "firstWinUnits_14")
	public int getFirstWinUnits_14() {
		return firstWinUnits_14;
	}

	public void setFirstWinUnits_14(int firstWinUnits_14) {
		this.firstWinUnits_14 = firstWinUnits_14;
	}

	
	@Column(name = "secondPrize_14")
	public Integer getSecondPrize_14() {
		return secondPrize_14;
	}

	public void setSecondPrize_14(Integer secondPrize_14) {
		this.secondPrize_14 = secondPrize_14;
	}

	@Column(name = "secondWinUnits_14")
	public int getSecondWinUnits_14() {
		return secondWinUnits_14;
	}

	public void setSecondWinUnits_14(int secondWinUnits_14) {
		this.secondWinUnits_14 = secondWinUnits_14;
	}

	@Column(name = "prizePool_14")
	public Long getPrizePool_14() {
		return prizePool_14;
	}

	public void setPrizePool_14(Long prizePool_14) {
		this.prizePool_14 = prizePool_14;
	}

}