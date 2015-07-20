package com.cai310.lottery.entity.lottery.dlt;

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
public class DltPrize implements Serializable {
	private static final long serialVersionUID = 4761360751476732484L;
	private Integer firstPrize;
	private Integer secondPrize;
	private Integer thirdPrize;
	private Integer fourthPrize = 200;
	private Integer fifthPrize = 10;
	private Integer sixthPrize = 5;
	private Integer seventhPrize = 0;
	private Integer eighthPrize = 0;
	private Integer select12to2Prize = 60;

	
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

	/**
	 * @return the seventhPrize
	 */
	
	@Column(name = "seventh_prize")
	public Integer getSeventhPrize() {
		return seventhPrize;
	}

	/**
	 * @param seventhPrize the seventhPrize to set
	 */
	public void setSeventhPrize(Integer seventhPrize) {
		this.seventhPrize = seventhPrize;
	}

	public Integer calcSeventhPrize(Integer winUnit) {
		Integer prize = this.getSeventhPrize();
		if (winUnit != null && winUnit > 0 && prize != null && prize > 0) {
			return winUnit * prize;
		}
		return null;
	}

	/**
	 * @return the eighthPrize
	 */
	
	@Column(name = "eighth_prize")
	public Integer getEighthPrize() {
		return eighthPrize;
	}

	/**
	 * @param eighthPrize the eighthPrize to set
	 */
	public void setEighthPrize(Integer eighthPrize) {
		this.eighthPrize = eighthPrize;
	}

	public Integer calcEighthPrize(Integer winUnit) {
		Integer prize = this.getEighthPrize();
		if (winUnit != null && winUnit > 0 && prize != null && prize > 0) {
			return winUnit * prize;
		}
		return null;
	}

	/**
	 * @return the select12to2Prize
	 */
	
	@Column(name = "select12to2_prize")
	public Integer getSelect12to2Prize() {
		return select12to2Prize;
	}

	/**
	 * @param select12to2Prize the select12to2Prize to set
	 */
	public void setSelect12to2Prize(Integer select12to2Prize) {
		this.select12to2Prize = select12to2Prize;
	}

	public Integer calcSelect12to2Prize(Integer winUnit) {
		Integer prize = this.getSelect12to2Prize();
		if (winUnit != null && winUnit > 0 && prize != null && prize > 0) {
			return winUnit * prize;
		}
		return null;
	}

	public int calcTotalPrize(DltWinUnit winUnit) {
		int totalPrize = 0;
		Integer prize = this.calcFirstPrize(winUnit.getFirstWinUnits());
		if (prize != null) {
			if (null != winUnit.getGeneralAdditional() && winUnit.getGeneralAdditional()) {
				totalPrize += prize * Double.valueOf("1.6");
			} else {
				totalPrize += prize;
			}

		}
		prize = this.calcSecondPrize(winUnit.getSecondWinUnits());
		if (prize != null) {
			if (null != winUnit.getGeneralAdditional() && winUnit.getGeneralAdditional()) {
				totalPrize += prize * Double.valueOf("1.6");
			} else {
				totalPrize += prize;
			}
		}
		prize = this.calcThirdPrize(winUnit.getThirdWinUnits());
		if (prize != null) {
			if (null != winUnit.getGeneralAdditional() && winUnit.getGeneralAdditional()) {
				totalPrize += prize * Double.valueOf("1.6");
			} else {
				totalPrize += prize;
			}
		}
		prize = this.calcFourthPrize(winUnit.getFourthWinUnits());
		if (prize != null) {
			if (null != winUnit.getGeneralAdditional() && winUnit.getGeneralAdditional()) {
				totalPrize += prize * Double.valueOf("1.5");
			} else {
				totalPrize += prize;
			}
		}
		prize = this.calcFifthPrize(winUnit.getFifthWinUnits());
		if (prize != null) {
			if (null != winUnit.getGeneralAdditional() && winUnit.getGeneralAdditional()) {
				totalPrize += prize * Double.valueOf("1.5");
			} else {
				totalPrize += prize;
			}
		}
		prize = this.calcSixthPrize(winUnit.getSixthWinUnits());
		if (prize != null) {
			totalPrize += prize;
		}
		prize = this.calcSeventhPrize(winUnit.getSeventhWinUnits());
		if (prize != null) {
			totalPrize += prize;
		}

		prize = this.calcEighthPrize(winUnit.getEighthWinUnits());
		if (prize != null) {
			totalPrize += prize;
		}

		prize = this.calcSelect12to2Prize(winUnit.getSelect12to2WinUnits());
		if (prize != null) {
			totalPrize += prize;
		}

		return totalPrize;
	}

	public List<PrizeItem> getPrizeItemList(DltWinUnit winUnit) {
		List<PrizeItem> list = new ArrayList<PrizeItem>();

		Integer unit = winUnit.getFirstWinUnits();
		Integer prize = this.getFirstPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("一等奖", unit), BigDecimalUtil.valueOf(prize)));
			if (null != winUnit.getGeneralAdditional() && winUnit.getGeneralAdditional()) {
				list.add(new PrizeItem(new WinItem("一等奖追加奖金", unit), BigDecimalUtil.multiply(
						BigDecimalUtil.valueOf(prize), BigDecimalUtil.valueOf("0.6"))));
			}
		}

		unit = winUnit.getSecondWinUnits();
		prize = this.getSecondPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("二等奖", unit), BigDecimalUtil.valueOf(prize)));
			if (null != winUnit.getGeneralAdditional() && winUnit.getGeneralAdditional()) {
				list.add(new PrizeItem(new WinItem("二等奖追加奖金", unit), BigDecimalUtil.multiply(
						BigDecimalUtil.valueOf(prize), BigDecimalUtil.valueOf("0.6"))));
			}
		}

		unit = winUnit.getThirdWinUnits();
		prize = this.getThirdPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("三等奖", unit), BigDecimalUtil.valueOf(prize)));
			if (null != winUnit.getGeneralAdditional() && winUnit.getGeneralAdditional()) {
				list.add(new PrizeItem(new WinItem("三等奖追加奖金", unit), BigDecimalUtil.multiply(
						BigDecimalUtil.valueOf(prize), BigDecimalUtil.valueOf("0.6"))));
			}
		}

		unit = winUnit.getFourthWinUnits();
		prize = this.getFourthPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("四等奖", unit), BigDecimalUtil.valueOf(prize)));
			if (null != winUnit.getGeneralAdditional() && winUnit.getGeneralAdditional()) {
				list.add(new PrizeItem(new WinItem("四等奖追加奖金", unit), BigDecimalUtil.multiply(
						BigDecimalUtil.valueOf(prize), BigDecimalUtil.valueOf("0.5"))));
			}
		}

		unit = winUnit.getFifthWinUnits();
		prize = this.getFifthPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("五等奖", unit), BigDecimalUtil.valueOf(prize)));
			if (null != winUnit.getGeneralAdditional() && winUnit.getGeneralAdditional()) {
				list.add(new PrizeItem(new WinItem("五等奖追加奖金", unit), BigDecimalUtil.multiply(
						BigDecimalUtil.valueOf(prize), BigDecimalUtil.valueOf("0.5"))));
			}
		}

		unit = winUnit.getSixthWinUnits();
		prize = this.getSixthPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("六等奖", unit), BigDecimalUtil.valueOf(prize)));
		}

		unit = winUnit.getSeventhWinUnits();
		prize = this.getSeventhPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("七等奖", unit), BigDecimalUtil.valueOf(prize)));
		}

		unit = winUnit.getEighthWinUnits();
		prize = this.getEighthPrize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("八等奖", unit), BigDecimalUtil.valueOf(prize)));
		}

		unit = winUnit.getSelect12to2WinUnits();
		prize = this.getSelect12to2Prize();
		if (unit != null && unit > 0) {
			list.add(new PrizeItem(new WinItem("12选2", unit), BigDecimalUtil.valueOf(prize)));
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
		if (this.getSeventhPrize() == null || this.getSeventhPrize() < 0) {
			throw new DataException("七等奖奖金未设置！");
		}
		if (this.getEighthPrize() == null || this.getEighthPrize() < 0) {
			throw new DataException("八等奖奖金未设置！");
		}
		if (this.getSelect12to2Prize() == null || this.getSelect12to2Prize() < 0) {
			throw new DataException("12选2奖金未设置！");
		}
	}
}
