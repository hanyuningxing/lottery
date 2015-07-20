package com.cai310.lottery.prizeutils.welfare3d;

import com.cai310.lottery.prizeutils.template.VariableString;

public class PrizeData {

	private Integer winUnits;// 直选中奖注数
	private Integer g3WinUnits;// 组3中奖注数
	private Integer g6WinUnits;// 组6中奖注数

	private Integer unitPrize;// 直选中奖金额
	private Integer g3UnitPrize;// 组三中奖金额
	private Integer g6UnitPrize;// 组六中奖金额

	private String resultTemplate;
	/**
	 * 模板：{PRIZE_ITEM}:{WINUNITS}注,{PRIZE}元;}
	 */
	private String prizeTemplate;

	public PrizeData() {

	}

	public PrizeData(String resultTemplate, String prizeTemplate) {
		this.resultTemplate = resultTemplate;
		this.prizeTemplate = prizeTemplate;
	}

	public String getWonDetail() {
		return "";
	}

	public String getPrizeDetail() {
		VariableString varWonLineText = new VariableString(prizeTemplate, null);
		String prizeDesc = "";
		if (winUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "直选");
			varWonLineText.setVar("WINUNITS", winUnits);
			varWonLineText.setVar("PRIZE", winUnits * unitPrize >= 10000.0d ? winUnits * unitPrize * 0.8d : winUnits
					* unitPrize);
			prizeDesc += varWonLineText.toString();

		}
		if (g3WinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "组三");
			varWonLineText.setVar("WINUNITS", g3WinUnits);
			varWonLineText.setVar("PRIZE", g3WinUnits * g3UnitPrize >= 10000.0d ? g3WinUnits * g3UnitPrize * 0.8d
					: g3WinUnits * g3UnitPrize);
			prizeDesc += varWonLineText.toString();

		}
		if (g6WinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "组六");
			varWonLineText.setVar("WINUNITS", g6WinUnits);
			varWonLineText.setVar("PRIZE", g6WinUnits * g6UnitPrize >= 10000.0d ? g6WinUnits * g6UnitPrize * 0.8d
					: g6WinUnits * g6UnitPrize);
			prizeDesc += varWonLineText.toString();

		}
		return prizeDesc;
	}

	public double getPrizeAfterTax() {
		double prize = 0;
		if (winUnits > 0) {
			prize += winUnits * unitPrize >= 10000.0d ? winUnits * unitPrize * 0.8d : winUnits * unitPrize;

		}
		if (g3WinUnits > 0) {
			prize += g3WinUnits * g3UnitPrize >= 10000.0d ? g3WinUnits * g3UnitPrize * 0.8d : g3WinUnits * g3UnitPrize;

		}
		if (g6WinUnits > 0) {
			prize += g6WinUnits * g6UnitPrize >= 10000.0d ? g6WinUnits * g6UnitPrize * 0.8d : g6WinUnits * g6UnitPrize;

		}
		return prize;
	}

	public double getPrize() {
		double prize = 0;
		if (winUnits > 0) {
			prize += winUnits * unitPrize;

		}
		if (g3WinUnits > 0) {
			prize += g3WinUnits * g3UnitPrize;

		}
		if (g6WinUnits > 0) {
			prize += g6WinUnits * g6UnitPrize;

		}
		return prize;
	}

	/**
	 * @return the winUnits
	 */
	public Integer getWinUnits() {
		return winUnits;
	}

	/**
	 * @param winUnits the winUnits to set
	 */
	public void setWinUnits(Integer winUnits) {
		this.winUnits = winUnits;
	}

	/**
	 * @return the g3WinUnits
	 */
	public Integer getG3WinUnits() {
		return g3WinUnits;
	}

	/**
	 * @param g3WinUnits the g3WinUnits to set
	 */
	public void setG3WinUnits(Integer g3WinUnits) {
		this.g3WinUnits = g3WinUnits;
	}

	/**
	 * @return the g6WinUnits
	 */
	public Integer getG6WinUnits() {
		return g6WinUnits;
	}

	/**
	 * @param g6WinUnits the g6WinUnits to set
	 */
	public void setG6WinUnits(Integer g6WinUnits) {
		this.g6WinUnits = g6WinUnits;
	}

	/**
	 * @return the unitPrize
	 */
	public Integer getUnitPrize() {
		return unitPrize;
	}

	/**
	 * @param unitPrize the unitPrize to set
	 */
	public void setUnitPrize(Integer unitPrize) {
		this.unitPrize = unitPrize;
	}

	/**
	 * @return the g3UnitPrize
	 */
	public Integer getG3UnitPrize() {
		return g3UnitPrize;
	}

	/**
	 * @param g3UnitPrize the g3UnitPrize to set
	 */
	public void setG3UnitPrize(Integer g3UnitPrize) {
		this.g3UnitPrize = g3UnitPrize;
	}

	/**
	 * @return the g6UnitPrize
	 */
	public Integer getG6UnitPrize() {
		return g6UnitPrize;
	}

	/**
	 * @param g6UnitPrize the g6UnitPrize to set
	 */
	public void setG6UnitPrize(Integer g6UnitPrize) {
		this.g6UnitPrize = g6UnitPrize;
	}

	/**
	 * @return the resultTemplate
	 */
	public String getResultTemplate() {
		return resultTemplate;
	}

	/**
	 * @param resultTemplate the resultTemplate to set
	 */
	public void setResultTemplate(String resultTemplate) {
		this.resultTemplate = resultTemplate;
	}

	/**
	 * @return the prizeTemplate
	 */
	public String getPrizeTemplate() {
		return prizeTemplate;
	}

	/**
	 * @param prizeTemplate the prizeTemplate to set
	 */
	public void setPrizeTemplate(String prizeTemplate) {
		this.prizeTemplate = prizeTemplate;
	}

}
