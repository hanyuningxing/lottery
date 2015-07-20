package com.cai310.lottery.entity.lottery.pl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.PrizeItem;
import com.cai310.lottery.support.WinItem;
import com.cai310.lottery.utils.BigDecimalUtil;

@Embeddable
public class PlPrize implements Serializable {

	private static final long serialVersionUID = 4761360751476732484L;
	private Integer p5UnitPrize = 100000;// 排五直选中奖金额
	private Integer p3UnitPrize = 1000;// 直选中奖金额
	private Integer p3G3UnitPrize = 320;// 组三中奖金额
	private Integer p3G6UnitPrize = 160;// 组六中奖金额

	/**
	 * @return the p5UnitPrize
	 */
	
	@Column(name = "p5_win_prize")
	public Integer getP5UnitPrize() {
		return p5UnitPrize;
	}

	/**
	 * @param p5UnitPrize the p5UnitPrize to set
	 */
	public void setP5UnitPrize(Integer p5UnitPrize) {
		this.p5UnitPrize = p5UnitPrize;
	}

	/**
	 * @return the p3UnitPrize
	 */
	
	@Column(name = "p3_win_prize")
	public Integer getP3UnitPrize() {
		return p3UnitPrize;
	}

	/**
	 * @param p3UnitPrize the p3UnitPrize to set
	 */
	public void setP3UnitPrize(Integer p3UnitPrize) {
		this.p3UnitPrize = p3UnitPrize;
	}

	/**
	 * @return the p3G3UnitPrize
	 */
	
	@Column(name = "p3_g3_win_prize")
	public Integer getP3G3UnitPrize() {
		return p3G3UnitPrize;
	}

	/**
	 * @param p3g3UnitPrize the p3G3UnitPrize to set
	 */
	public void setP3G3UnitPrize(Integer p3g3UnitPrize) {
		p3G3UnitPrize = p3g3UnitPrize;
	}

	/**
	 * @return the p3G6UnitPrize
	 */
	
	@Column(name = "p3_g6_win_prize")
	public Integer getP3G6UnitPrize() {
		return p3G6UnitPrize;
	}

	/**
	 * @param p3g6UnitPrize the p3G6UnitPrize to set
	 */
	public void setP3G6UnitPrize(Integer p3g6UnitPrize) {
		p3G6UnitPrize = p3g6UnitPrize;
	}

	public List<PrizeItem> getPrizeItemList(PlWinUnit winUnit) {
		List<PrizeItem> list = new ArrayList<PrizeItem>();

		Integer unit = winUnit.getP5WinUnits();
		Integer prize = this.getP5UnitPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("排列5直选", unit), BigDecimalUtil.valueOf(prize)));
		}

		unit = winUnit.getP3WinUnits();
		prize = this.getP3UnitPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("排列3直选", unit), BigDecimalUtil.valueOf(prize)));
		}
		unit = winUnit.getP3G3WinUnits();
		prize = this.getP3G3UnitPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("排列3组三", unit), BigDecimalUtil.valueOf(prize)));
		}

		unit = winUnit.getP3G6WinUnits();
		prize = this.getP3G6UnitPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("排列3组六", unit), BigDecimalUtil.valueOf(prize)));
		}
		return list;
	}

	public void checkPrize() throws DataException {
		if (this.getP5UnitPrize() == null || this.getP5UnitPrize() < 0) {
			throw new DataException("排列5直选奖金未设置！");
		}
		if (this.getP3UnitPrize() == null || this.getP3UnitPrize() < 0) {
			throw new DataException("排列3直选奖金未设置！");
		}
		if (this.getP3G3UnitPrize() == null || this.getP3G3UnitPrize() < 0) {
			throw new DataException("排列3组三奖金未设置！");
		}
		if (this.getP3G6UnitPrize() == null || this.getP3G6UnitPrize() < 0) {
			throw new DataException("排列3组六奖金未设置！");
		}
	}
}
