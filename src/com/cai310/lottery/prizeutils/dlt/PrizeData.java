package com.cai310.lottery.prizeutils.dlt;

import com.cai310.lottery.prizeutils.template.VariableString;

public class PrizeData {

	private Integer firstWinUnits;
	private Integer secondWinUnits;
	private Integer thirdWinUnits;
	private Integer fourthWinUnits;
	private Integer fifthWinUnits;
	private Integer sixthWinUnits;
	private Integer seventhWinUnits;
	private Integer eighthWinUnits;
	private Integer select12to2WinUnits;

	private Integer firstPrize;
	private Integer secondPrize;
	private Integer thirdPrize;
	private Integer fourthPrize = 3000;
	private Integer fifthPrize = 600;
	private Integer sixthPrize = 100;
	private Integer seventhPrize = 10;
	private Integer eighthPrize = 5;
	private Integer select12to2Prize = 60;

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
		if (firstWinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "一等奖");
			varWonLineText.setVar("WINUNITS", firstWinUnits);
			varWonLineText.setVar("PRIZE", firstWinUnits * firstPrize >= 10000.0d ? firstWinUnits * firstPrize * 0.8d
					: firstWinUnits * firstPrize);
			prizeDesc += varWonLineText.toString();

		}
		if (secondWinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "二等奖");
			varWonLineText.setVar("WINUNITS", secondWinUnits);
			varWonLineText.setVar("PRIZE", secondWinUnits * secondPrize >= 10000.0d ? secondWinUnits * secondPrize
					* 0.8d : secondWinUnits * secondPrize);
			prizeDesc += varWonLineText.toString();
		}
		if (thirdWinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "三等奖");
			varWonLineText.setVar("WINUNITS", thirdWinUnits);
			varWonLineText.setVar("PRIZE", thirdWinUnits * thirdPrize >= 10000.0d ? thirdWinUnits * thirdPrize * 0.8d
					: thirdWinUnits * thirdPrize);
			prizeDesc += varWonLineText.toString();

		}
		if (fourthWinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "四等奖");
			varWonLineText.setVar("WINUNITS", fourthWinUnits);
			varWonLineText.setVar("PRIZE", fourthWinUnits * fourthPrize >= 10000.0d ? fourthWinUnits * fourthPrize
					* 0.8d : fourthWinUnits * fourthPrize);
			prizeDesc += varWonLineText.toString();
		}
		if (fifthWinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "五等奖");
			varWonLineText.setVar("WINUNITS", fifthWinUnits);
			varWonLineText.setVar("PRIZE", fifthWinUnits * fifthPrize >= 10000.0d ? fifthWinUnits * fifthPrize * 0.8d
					: fifthWinUnits * fifthPrize);
			prizeDesc += varWonLineText.toString();
		}
		if (sixthWinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "六等奖");
			varWonLineText.setVar("WINUNITS", sixthWinUnits);
			varWonLineText.setVar("PRIZE", sixthWinUnits * sixthPrize >= 10000.0d ? sixthWinUnits * sixthPrize * 0.8d
					: sixthWinUnits * sixthPrize);
			prizeDesc += varWonLineText.toString();
		}

		if (seventhWinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "七等奖");
			varWonLineText.setVar("WINUNITS", seventhWinUnits);
			varWonLineText.setVar("PRIZE", seventhWinUnits * seventhPrize >= 10000.0d ? seventhWinUnits * seventhPrize
					* 0.8d : seventhWinUnits * seventhPrize);
			prizeDesc += varWonLineText.toString();
		}
		if (eighthWinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "八等奖");
			varWonLineText.setVar("WINUNITS", eighthWinUnits);
			varWonLineText.setVar("PRIZE", eighthWinUnits * eighthPrize >= 10000.0d ? eighthWinUnits * eighthPrize
					* 0.8d : eighthWinUnits * eighthPrize);
			prizeDesc += varWonLineText.toString();
		}
		if (select12to2WinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "12选2");
			varWonLineText.setVar("WINUNITS", select12to2WinUnits);
			varWonLineText.setVar("PRIZE", select12to2WinUnits * select12to2Prize >= 10000.0d ? select12to2WinUnits
					* select12to2Prize * 0.8d : select12to2WinUnits * select12to2Prize);
			prizeDesc += varWonLineText.toString();
		}
		return prizeDesc;
	}

	public double getPrizeAfterTax() {
		double prize = 0;
		if (firstWinUnits > 0) {
			prize += firstWinUnits * firstPrize >= 10000.0d ? firstWinUnits * firstPrize * 0.8d : firstWinUnits
					* firstPrize;
		}
		if (secondWinUnits > 0) {
			prize += secondWinUnits * secondPrize >= 10000.0d ? secondWinUnits * secondPrize * 0.8d : secondWinUnits
					* secondPrize;//
		}
		if (thirdWinUnits > 0) {
			prize += thirdWinUnits * thirdPrize >= 10000.0d ? thirdWinUnits * thirdPrize * 0.8d : thirdWinUnits
					* thirdPrize;//

		}
		if (fourthWinUnits > 0) {
			prize += fourthWinUnits * fourthPrize >= 10000.0d ? fourthWinUnits * fourthPrize * 0.8d : fourthWinUnits
					* fourthPrize;//
		}
		if (fifthWinUnits > 0) {
			prize += fifthWinUnits * 10 >= 10000.0d ? fifthWinUnits * fifthPrize * 0.8d : fifthWinUnits * fifthPrize;//
		}
		if (sixthWinUnits > 0) {
			prize += sixthWinUnits * 5 >= 10000.0d ? sixthWinUnits * sixthPrize * 0.8d : sixthWinUnits * sixthPrize;//
		}
		if (seventhWinUnits > 0) {
			prize += seventhWinUnits * seventhPrize >= 10000.0d ? seventhWinUnits * seventhPrize * 0.8d
					: seventhWinUnits * seventhPrize;//
		}
		if (eighthWinUnits > 0) {
			prize += eighthWinUnits * 10 >= 10000.0d ? eighthWinUnits * eighthPrize * 0.8d : eighthWinUnits
					* eighthPrize;//
		}
		if (select12to2WinUnits > 0) {
			prize += select12to2WinUnits * 5 >= 10000.0d ? select12to2WinUnits * select12to2Prize * 0.8d
					: select12to2WinUnits * select12to2Prize;//
		}
		return prize;
	}

	public double getPrize() {
		double prize = 0;
		if (firstWinUnits > 0) {
			prize += firstWinUnits * firstPrize;
		}
		if (secondWinUnits > 0) {
			prize += secondWinUnits * secondPrize;//
		}
		if (thirdWinUnits > 0) {
			prize += thirdWinUnits * thirdPrize;//

		}
		if (fourthWinUnits > 0) {
			prize += fourthWinUnits * fourthPrize;//
		}
		if (fifthWinUnits > 0) {
			prize += fifthWinUnits * fifthPrize;//
		}
		if (sixthWinUnits > 0) {
			prize += sixthWinUnits * sixthPrize;//
		}
		if (seventhWinUnits > 0) {
			prize += seventhWinUnits * seventhPrize;//
		}
		if (eighthWinUnits > 0) {
			prize += eighthWinUnits * eighthPrize;//
		}
		if (select12to2WinUnits > 0) {
			prize += select12to2WinUnits * select12to2Prize;//
		}
		return prize;
	}

	protected void setFirstPrize(int firstPrize) {
		this.firstPrize = firstPrize;
	}

	protected void setSecondPrize(int secondPrize) {
		this.secondPrize = secondPrize;
	}

	protected void setThirdPrize(int thirdPrize) {
		this.thirdPrize = thirdPrize;
	}

	protected void setFourthPrize(int fourthPrize) {
		this.fourthPrize = fourthPrize;
	}

	protected void setFifthPrize(int fifthPrize) {
		this.fifthPrize = fifthPrize;
	}

	protected void setSixthPrize(int sixthPrize) {
		this.sixthPrize = sixthPrize;
	}

	protected void addFirstWinUnits(int firstWinUnits) {
		this.firstWinUnits += firstWinUnits;
	}

	protected void addSecondWinUnits(int secondWinUnits) {
		this.secondWinUnits += secondWinUnits;
	}

	protected void addThirdWinUnits(int thirdWinUnits) {
		this.thirdWinUnits += thirdWinUnits;
	}

	protected void addFourthWinUnits(int fourthWinUnits) {
		this.fourthWinUnits += fourthWinUnits;
	}

	protected void addFifthWinUnits(int fifthWinUnits) {
		this.fifthWinUnits += fifthWinUnits;
	}

	protected void addSixthWinUnits(int sixthWinUnits) {
		this.sixthWinUnits += sixthWinUnits;
	}

	/**
	 * @return the firstWinUnits
	 */
	public Integer getFirstWinUnits() {
		return firstWinUnits;
	}

	/**
	 * @param firstWinUnits the firstWinUnits to set
	 */
	public void setFirstWinUnits(Integer firstWinUnits) {
		this.firstWinUnits = firstWinUnits;
	}

	/**
	 * @return the secondWinUnits
	 */
	public Integer getSecondWinUnits() {
		return secondWinUnits;
	}

	/**
	 * @param secondWinUnits the secondWinUnits to set
	 */
	public void setSecondWinUnits(Integer secondWinUnits) {
		this.secondWinUnits = secondWinUnits;
	}

	/**
	 * @return the thirdWinUnits
	 */
	public Integer getThirdWinUnits() {
		return thirdWinUnits;
	}

	/**
	 * @param thirdWinUnits the thirdWinUnits to set
	 */
	public void setThirdWinUnits(Integer thirdWinUnits) {
		this.thirdWinUnits = thirdWinUnits;
	}

	/**
	 * @return the fourthWinUnits
	 */
	public Integer getFourthWinUnits() {
		return fourthWinUnits;
	}

	/**
	 * @param fourthWinUnits the fourthWinUnits to set
	 */
	public void setFourthWinUnits(Integer fourthWinUnits) {
		this.fourthWinUnits = fourthWinUnits;
	}

	/**
	 * @return the fifthWinUnits
	 */
	public Integer getFifthWinUnits() {
		return fifthWinUnits;
	}

	/**
	 * @param fifthWinUnits the fifthWinUnits to set
	 */
	public void setFifthWinUnits(Integer fifthWinUnits) {
		this.fifthWinUnits = fifthWinUnits;
	}

	/**
	 * @return the sixthWinUnits
	 */
	public Integer getSixthWinUnits() {
		return sixthWinUnits;
	}

	/**
	 * @param sixthWinUnits the sixthWinUnits to set
	 */
	public void setSixthWinUnits(Integer sixthWinUnits) {
		this.sixthWinUnits = sixthWinUnits;
	}

	/**
	 * @return the seventhWinUnits
	 */
	public Integer getSeventhWinUnits() {
		return seventhWinUnits;
	}

	/**
	 * @param seventhWinUnits the seventhWinUnits to set
	 */
	public void setSeventhWinUnits(Integer seventhWinUnits) {
		this.seventhWinUnits = seventhWinUnits;
	}

	/**
	 * @return the eighthWinUnits
	 */
	public Integer getEighthWinUnits() {
		return eighthWinUnits;
	}

	/**
	 * @param eighthWinUnits the eighthWinUnits to set
	 */
	public void setEighthWinUnits(Integer eighthWinUnits) {
		this.eighthWinUnits = eighthWinUnits;
	}

	/**
	 * @return the select12to2WinUnits
	 */
	public Integer getSelect12to2WinUnits() {
		return select12to2WinUnits;
	}

	/**
	 * @param select12to2WinUnits the select12to2WinUnits to set
	 */
	public void setSelect12to2WinUnits(Integer select12to2WinUnits) {
		this.select12to2WinUnits = select12to2WinUnits;
	}

	/**
	 * @return the firstPrize
	 */
	public Integer getFirstPrize() {
		return firstPrize;
	}

	/**
	 * @param firstPrize the firstPrize to set
	 */
	public void setFirstPrize(Integer firstPrize) {
		this.firstPrize = firstPrize;
	}

	/**
	 * @return the secondPrize
	 */
	public Integer getSecondPrize() {
		return secondPrize;
	}

	/**
	 * @param secondPrize the secondPrize to set
	 */
	public void setSecondPrize(Integer secondPrize) {
		this.secondPrize = secondPrize;
	}

	/**
	 * @return the thirdPrize
	 */
	public Integer getThirdPrize() {
		return thirdPrize;
	}

	/**
	 * @param thirdPrize the thirdPrize to set
	 */
	public void setThirdPrize(Integer thirdPrize) {
		this.thirdPrize = thirdPrize;
	}

	/**
	 * @return the fourthPrize
	 */
	public Integer getFourthPrize() {
		return fourthPrize;
	}

	/**
	 * @param fourthPrize the fourthPrize to set
	 */
	public void setFourthPrize(Integer fourthPrize) {
		this.fourthPrize = fourthPrize;
	}

	/**
	 * @return the fifthPrize
	 */
	public Integer getFifthPrize() {
		return fifthPrize;
	}

	/**
	 * @param fifthPrize the fifthPrize to set
	 */
	public void setFifthPrize(Integer fifthPrize) {
		this.fifthPrize = fifthPrize;
	}

	/**
	 * @return the sixthPrize
	 */
	public Integer getSixthPrize() {
		return sixthPrize;
	}

	/**
	 * @param sixthPrize the sixthPrize to set
	 */
	public void setSixthPrize(Integer sixthPrize) {
		this.sixthPrize = sixthPrize;
	}

	/**
	 * @return the seventhPrize
	 */
	public Integer getSeventhPrize() {
		return seventhPrize;
	}

	/**
	 * @param seventhPrize the seventhPrize to set
	 */
	public void setSeventhPrize(Integer seventhPrize) {
		this.seventhPrize = seventhPrize;
	}

	/**
	 * @return the eighthPrize
	 */
	public Integer getEighthPrize() {
		return eighthPrize;
	}

	/**
	 * @param eighthPrize the eighthPrize to set
	 */
	public void setEighthPrize(Integer eighthPrize) {
		this.eighthPrize = eighthPrize;
	}

	/**
	 * @return the select12to2Prize
	 */
	public Integer getSelect12to2Prize() {
		return select12to2Prize;
	}

	/**
	 * @param select12to2Prize the select12to2Prize to set
	 */
	public void setSelect12to2Prize(Integer select12to2Prize) {
		this.select12to2Prize = select12to2Prize;
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
