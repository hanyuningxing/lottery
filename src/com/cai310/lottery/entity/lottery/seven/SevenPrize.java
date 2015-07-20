package com.cai310.lottery.entity.lottery.seven;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.PrizeItem;
import com.cai310.lottery.support.WinItem;

@Embeddable
public class SevenPrize implements Serializable {
	private static final long serialVersionUID = 4761360751476732484L;

	private Integer firstPrize;
	private Integer secondPrize;
	private Integer thirdPrize;
	private Integer fourthPrize = 200;
	private Integer fifthPrize = 50;
	private Integer sixthPrize = 10;
	private Integer sevenPrize = 5;

	@Column(name = "first_prize")
	public Integer getFirstPrize() {
		return this.firstPrize;
	}

	public void setFirstPrize(Integer firstPrize) {
		this.firstPrize = firstPrize;
	}

	public Integer calcFirstPrize(Integer winUnit) {
		Integer prize = this.getFirstPrize();
		if (winUnit != null && winUnit > 0 && prize != null && prize > 0) {
			return winUnit * prize;
		}
		return null;
	}

	@Column(name = "second_prize")
	public Integer getSecondPrize() {
		return this.secondPrize;
	}

	public void setSecondPrize(Integer secondPrize) {
		this.secondPrize = secondPrize;
	}

	public Integer calcSecondPrize(Integer winUnit) {
		Integer prize = this.getSecondPrize();
		if (winUnit != null && winUnit > 0 && prize != null && prize > 0) {
			return winUnit * prize;
		}
		return null;
	}

	@Column(name = "third_prize")
	public Integer getThirdPrize() {
		return this.thirdPrize;
	}

	public void setThirdPrize(Integer thirdPrize) {
		this.thirdPrize = thirdPrize;
	}

	public Integer calcThirdPrize(Integer winUnit) {
		Integer prize = this.getThirdPrize();
		if (winUnit != null && winUnit > 0 && prize != null && prize > 0) {
			return winUnit * prize;
		}
		return null;
	}

	@Column(name = "fourth_prize")
	public Integer getFourthPrize() {
		return this.fourthPrize;
	}

	public void setFourthPrize(Integer fourthPrize) {
		this.fourthPrize = fourthPrize;
	}

	public Integer calcFourthPrize(Integer winUnit) {
		Integer prize = this.getFourthPrize();
		if (winUnit != null && winUnit > 0 && prize != null && prize > 0) {
			return winUnit * prize;
		}
		return null;
	}

	@Column(name = "fifth_prize")
	public Integer getFifthPrize() {
		return this.fifthPrize;
	}

	public void setFifthPrize(Integer fifthPrize) {
		this.fifthPrize = fifthPrize;
	}

	public Integer calcFifthPrize(Integer winUnit) {
		Integer prize = this.getFifthPrize();
		if (winUnit != null && winUnit > 0 && prize != null && prize > 0) {
			return winUnit * prize;
		}
		return null;
	}

	@Column(name = "sixth_prize")
	public Integer getSixthPrize() {
		return this.sixthPrize;
	}

	public void setSixthPrize(Integer sixthPrize) {
		this.sixthPrize = sixthPrize;
	}
	public Integer calcSixthPrize(Integer winUnit) {
		Integer prize = this.getSixthPrize();
		if (winUnit != null && winUnit > 0 && prize != null && prize > 0) {
			return winUnit * prize;
		}
		return null;
	}
	
	@Column(name = "seven_prize")
	public Integer getSevenPrize() {
		return sevenPrize;
	}
	public void setSevenPrize(Integer sevenPrize) {
		this.sevenPrize = sevenPrize;
	}
	public Integer calcSevenPrize(Integer winUnit) {
		Integer prize = this.getSevenPrize();
		if (winUnit != null && winUnit > 0 && prize != null && prize > 0) {
			return winUnit * prize;
		}
		return null;
	}

	public int calcTotalPrize(SevenWinUnit winUnit) {
		int totalPrize = 0;
		Integer prize = this.calcFirstPrize(winUnit.getFirstWinUnits());
		if (prize != null) {
			totalPrize += prize;
		}
		prize = this.calcSecondPrize(winUnit.getSecondWinUnits());
		if (prize != null) {
			totalPrize += prize;
		}
		prize = this.calcThirdPrize(winUnit.getThirdWinUnits());
		if (prize != null) {
			totalPrize += prize;
		}
		prize = this.calcFourthPrize(winUnit.getFourthWinUnits());
		if (prize != null) {
			totalPrize += prize;
		}
		prize = this.calcFifthPrize(winUnit.getFifthWinUnits());
		if (prize != null) {
			totalPrize += prize;
		}
		prize = this.calcSixthPrize(winUnit.getSixthWinUnits());
		if (prize != null) {
			totalPrize += prize;
		}
		prize = this.calcSevenPrize(winUnit.getSevenWinUnits());
		if (prize != null) {
			totalPrize += prize;
		}
		return totalPrize;
	}

	public List<PrizeItem> getPrizeItemList(SevenWinUnit winUnit) {
		List<PrizeItem> list = new ArrayList<PrizeItem>();

		Integer unit = winUnit.getFirstWinUnits();
		Integer prize = this.getFirstPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("一等奖", unit), BigDecimal.valueOf(prize)));
		}

		unit = winUnit.getSecondWinUnits();
		prize = this.getSecondPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("二等奖", unit), BigDecimal.valueOf(prize)));
		}

		unit = winUnit.getThirdWinUnits();
		prize = this.getThirdPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("三等奖", unit), BigDecimal.valueOf(prize)));
		}

		unit = winUnit.getFourthWinUnits();
		prize = this.getFourthPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("四等奖", unit), BigDecimal.valueOf(prize)));
		}

		unit = winUnit.getFifthWinUnits();
		prize = this.getFifthPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("五等奖", unit), BigDecimal.valueOf(prize)));
		}

		unit = winUnit.getSixthWinUnits();
		prize = this.getSixthPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("六等奖", unit), BigDecimal.valueOf(prize)));
		}
		
		unit = winUnit.getSevenWinUnits();
		prize = this.getSevenPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("七等奖", unit), BigDecimal.valueOf(prize)));
		}
		return list;
	}

	public void checkPrize() throws DataException {
		if (this.getFirstPrize() == null || this.getFirstPrize() < 0) {
			throw new DataException("一等奖奖金未设置！");
		}
		if (this.getSecondPrize() == null || this.getSecondPrize() < 0) {
			throw new DataException("二等奖奖金未设置！");
		}
		if (this.getThirdPrize() == null || this.getThirdPrize() < 0) {
			throw new DataException("三等奖奖金未设置！");
		}
		if (this.getFourthPrize() == null || this.getFourthPrize() < 0) {
			throw new DataException("四等奖奖金未设置！");
		}
		if (this.getFifthPrize() == null || this.getFifthPrize() < 0) {
			throw new DataException("五等奖奖金未设置！");
		}
		if (this.getSixthPrize() == null || this.getSixthPrize() < 0) {
			throw new DataException("六等奖奖金未设置！");
		}
		if (this.getSevenPrize() == null || this.getSevenPrize() < 0) {
			throw new DataException("七等奖奖金未设置！");
		}
	}

	
}
