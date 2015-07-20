package com.cai310.lottery.entity.lottery.welfare36to7;

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
public class Welfare36to7Prize implements Serializable {
	private static final long serialVersionUID = 4761360751476732484L;

	private Integer firstPrize;
	private Integer secondPrize;
	private Integer thirdPrize;
	private Integer fourthPrize = 500;
	private Integer fifthPrize = 100;
	private Integer sixthPrize = 10;

	private Integer haocai1Prize = 46; //zhuhui motify 2011-07-04
	private Integer haocai2Prize;
	private Integer haocai3Prize;

	private Integer zodiacPrize = 15;
	private Integer seasonPrize = 5;
	private Integer azimuthPrize = 5;

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

	/**
	 * @return the haocai1Prize
	 */
	@Column(name = "haocai1_prize")
	public Integer getHaocai1Prize() {
		return haocai1Prize;
	}

	/**
	 * @param haocai2Prize the haocai2Prize to set
	 */
	public void setHaocai1Prize(Integer haocai1Prize) {
		this.haocai1Prize = haocai1Prize;
	}

	/**
	 * @return the haocai2Prize
	 */
	@Column(name = "haocai2_prize")
	public Integer getHaocai2Prize() {
		return haocai2Prize;
	}

	/**
	 * @param haocai2Prize the haocai2Prize to set
	 */
	public void setHaocai2Prize(Integer haocai2Prize) {
		this.haocai2Prize = haocai2Prize;
	}

	/**
	 * @return the haocai3Prize
	 */
	@Column(name = "haocai3_prize")
	public Integer getHaocai3Prize() {
		return haocai3Prize;
	}

	/**
	 * @param haocai3Prize the haocai3Prize to set
	 */
	public void setHaocai3Prize(Integer haocai3Prize) {
		this.haocai3Prize = haocai3Prize;
	}

	/**
	 * @return the zodiacPrize
	 */
	@Column(name = "zodiac_prize")
	public Integer getZodiacPrize() {
		return zodiacPrize;
	}

	/**
	 * @param zodiacPrize the zodiacPrize to set
	 */
	public void setZodiacPrize(Integer zodiacPrize) {
		this.zodiacPrize = zodiacPrize;
	}

	/**
	 * @return the seasonPrize
	 */
	@Column(name = "season_prize")
	public Integer getSeasonPrize() {
		return seasonPrize;
	}

	/**
	 * @param seasonPrize the seasonPrize to set
	 */
	public void setSeasonPrize(Integer seasonPrize) {
		this.seasonPrize = seasonPrize;
	}

	/**
	 * @return the azimuthPrize
	 */
	@Column(name = "azimuth_prize")
	public Integer getAzimuthPrize() {
		return azimuthPrize;
	}

	/**
	 * @param azimuthPrize the azimuthPrize to set
	 */
	public void setAzimuthPrize(Integer azimuthPrize) {
		this.azimuthPrize = azimuthPrize;
	}

	public Integer calcSixthPrize(Integer winUnit) {
		Integer prize = this.getSixthPrize();
		if (winUnit != null && winUnit > 0 && prize != null && prize > 0) {
			return winUnit * prize;
		}
		return null;
	}

	public int calcTotalPrize(Welfare36to7WinUnit winUnit) {
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
		return totalPrize;
	}

	public List<PrizeItem> getPrizeItemList(Welfare36to7WinUnit winUnit) {
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

		unit = winUnit.getHaocai1WinUnits();
		prize = this.getHaocai1Prize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("好彩1", unit), BigDecimal.valueOf(prize)));
		}

		unit = winUnit.getHaocai2WinUnits();
		prize = this.getHaocai2Prize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("好彩2", unit), BigDecimal.valueOf(prize)));
		}

		unit = winUnit.getHaocai3WinUnits();
		prize = this.getHaocai3Prize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("好彩3", unit), BigDecimal.valueOf(prize)));
		}

		unit = winUnit.getZodiacWinUnits();
		prize = this.getZodiacPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("生肖", unit), BigDecimal.valueOf(prize)));
		}

		unit = winUnit.getSeasonWinUnits();
		prize = this.getSeasonPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("季节", unit), BigDecimal.valueOf(prize)));
		}

		unit = winUnit.getAzimuthWinUnits();
		prize = this.getAzimuthPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("方位", unit), BigDecimal.valueOf(prize)));
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

		if (this.getHaocai1Prize() == null || this.getHaocai1Prize() < 0) {
			throw new DataException("好彩1奖金未设置！");
		}

		if (this.getHaocai2Prize() == null || this.getHaocai2Prize() < 0) {
			throw new DataException("好彩2奖金未设置！");
		}
		if (this.getHaocai3Prize() == null || this.getHaocai3Prize() < 0) {
			throw new DataException("好彩3奖金未设置！");
		}
		if (this.getZodiacPrize() == null || this.getZodiacPrize() < 0) {
			throw new DataException("生肖奖金未设置！");
		}
		if (this.getSeasonPrize() == null || this.getSeasonPrize() < 0) {
			throw new DataException("季节奖金未设置！");
		}
		if (this.getAzimuthPrize() == null || this.getAzimuthPrize() < 0) {
			throw new DataException("方位奖金未设置！");
		}
	}

}
