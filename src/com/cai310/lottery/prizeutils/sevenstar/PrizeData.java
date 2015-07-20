package com.cai310.lottery.prizeutils.sevenstar;

import com.cai310.lottery.prizeutils.template.VariableString;

public class PrizeData {

	private Integer firstPrize;
	private Integer secondPrize;
	private Integer thirdPrize = 1800;
	private Integer fourthPrize = 300;
	private Integer fifthPrize = 20;
	private Integer sixthPrize = 5;

	private Integer firstWinUnits;
	private Integer secondWinUnits;
	private Integer thirdWinUnits;
	private Integer fourthWinUnits;
	private Integer fifthWinUnits;
	private Integer sixthWinUnits;

	/**
	 * 模板：{PRIZE_ITEM}:{WINUNITS}注,{PRIZE}元;}
	 */
	private String prizeTemplate;

	public PrizeData() {

	}

	public PrizeData(String prizeTemplate) {
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
			prize += fifthWinUnits * fifthPrize >= 10000.0d ? fifthWinUnits * fifthPrize * 0.8d : fifthWinUnits
					* fifthPrize;//
		}
		if (sixthWinUnits > 0) {
			prize += sixthWinUnits * sixthPrize >= 10000.0d ? sixthWinUnits * sixthPrize * 0.8d : sixthWinUnits
					* sixthPrize;//
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

}
