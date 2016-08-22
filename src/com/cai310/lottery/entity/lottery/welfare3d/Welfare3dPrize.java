package com.cai310.lottery.entity.lottery.welfare3d;

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
public class Welfare3dPrize implements Serializable {
	private static final long serialVersionUID = 4761360751476732484L;
	private Integer unitPrize = 1040;// 直选中奖金额
	private Integer g3UnitPrize = 346;// 组三中奖金额
	private Integer g6UnitPrize = 173;// 组六中奖金额

	
	@Column(name = "unit_prize")
	public Integer getUnitPrize() {
		return this.unitPrize;
	}

	public void setUnitPrize(Integer unitPrize) {
		this.unitPrize = unitPrize;
	}

	
	@Column(name = "g3_unit_prize")
	public Integer getG3UnitPrize() {
		return this.g3UnitPrize;
	}

	public void setG3UnitPrize(Integer g3UnitPrize) {
		this.g3UnitPrize = g3UnitPrize;
	}

	
	@Column(name = "g6_unit_prize")
	public Integer getG6UnitPrize() {
		return this.g6UnitPrize;
	}

	public void setG6UnitPrize(Integer g6UnitPrize) {
		this.g6UnitPrize = g6UnitPrize;
	}

	public List<PrizeItem> getPrizeItemList(Welfare3dWinUnit winUnit) {
		List<PrizeItem> list = new ArrayList<PrizeItem>();

		Integer unit = winUnit.getWinUnits();
		Integer prize = this.getUnitPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("直选", unit), BigDecimalUtil.valueOf(prize)));
		}
		unit = winUnit.getG3WinUnits();
		prize = this.getG3UnitPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("组三", unit), BigDecimalUtil.valueOf(prize)));
		}

		unit = winUnit.getG6WinUnits();
		prize = this.getG6UnitPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("组六", unit), BigDecimalUtil.valueOf(prize)));
		}

		return list;
	}

	public void checkPrize() throws DataException {
		if (this.getUnitPrize() == null || this.getUnitPrize() < 0) {
			throw new DataException("直选奖金未设置！");
		}
		if (this.getG3UnitPrize() == null || this.getG3UnitPrize() < 0) {
			throw new DataException("组三奖金未设置！");
		}
		if (this.getG6UnitPrize() == null || this.getG6UnitPrize() < 0) {
			throw new DataException("组六奖金未设置！");
		}
	}
}
