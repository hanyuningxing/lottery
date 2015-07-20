package com.cai310.lottery.prizeutils.ssq;

import com.cai310.lottery.prizeutils.template.VariableString;

public class PrizeData {

	private int firstPrize = 0;
	private int secondPrize = 0;
	private int thirdPrize = 3000;
	private int fourthPrize = 200;
	private int fifthPrize = 10;
	private int sixthPrize = 0;

	private int firstWinUntis = 0;
	private int secondWinUntis = 0;
	private int thirdWinUntis = 0;
	private int fourthWinUntis = 0;
	private int fifthWinUntis = 0;
	private int sixthWinUntis = 0;

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
		if (firstWinUntis > 0) {
			varWonLineText.setVar("PRIZEITEM", "一等奖");
			varWonLineText.setVar("WINUNITS", firstWinUntis);
			varWonLineText.setVar("PRIZE", firstWinUntis * firstPrize >= 10000.0d ? firstWinUntis * firstPrize * 0.8d
					: firstWinUntis * firstPrize);
			prizeDesc += varWonLineText.toString();

		}
		if (secondWinUntis > 0) {
			varWonLineText.setVar("PRIZEITEM", "二等奖");
			varWonLineText.setVar("WINUNITS", secondWinUntis);
			varWonLineText.setVar("PRIZE", secondWinUntis * secondPrize >= 10000.0d ? secondWinUntis * secondPrize
					* 0.8d : secondWinUntis * secondPrize);
			prizeDesc += varWonLineText.toString();
		}
		if (thirdWinUntis > 0) {
			varWonLineText.setVar("PRIZEITEM", "三等奖");
			varWonLineText.setVar("WINUNITS", thirdWinUntis);
			varWonLineText.setVar("PRIZE", thirdWinUntis * thirdPrize >= 10000.0d ? thirdWinUntis * thirdPrize * 0.8d
					: thirdWinUntis * thirdPrize);
			prizeDesc += varWonLineText.toString();

		}
		if (fourthWinUntis > 0) {
			varWonLineText.setVar("PRIZEITEM", "四等奖");
			varWonLineText.setVar("WINUNITS", fourthWinUntis);
			varWonLineText.setVar("PRIZE", fourthWinUntis * fourthPrize >= 10000.0d ? fourthWinUntis * fourthPrize
					* 0.8d : fourthWinUntis * fourthPrize);
			prizeDesc += varWonLineText.toString();
		}
		if (fifthWinUntis > 0) {
			varWonLineText.setVar("PRIZEITEM", "五等奖");
			varWonLineText.setVar("WINUNITS", fifthWinUntis);
			varWonLineText.setVar("PRIZE", fifthWinUntis * fifthPrize >= 10000.0d ? fifthWinUntis * fifthPrize * 0.8d
					: fifthWinUntis * fifthPrize);
			prizeDesc += varWonLineText.toString();
		}
		if (sixthWinUntis > 0) {
			varWonLineText.setVar("PRIZEITEM", "六等奖");
			varWonLineText.setVar("WINUNITS", sixthWinUntis);
			varWonLineText.setVar("PRIZE", sixthWinUntis * sixthPrize >= 10000.0d ? sixthWinUntis * sixthPrize * 0.8d
					: sixthWinUntis * sixthPrize);
			prizeDesc += varWonLineText.toString();
		}
		return prizeDesc;
	}

	public double getPrizeAfterTax() {
		double prize = 0;
		if (firstWinUntis > 0) {
			prize += firstWinUntis * firstPrize >= 10000.0d ? firstWinUntis * firstPrize * 0.8d : firstWinUntis
					* firstPrize;
		}
		if (secondWinUntis > 0) {
			prize += secondWinUntis * secondPrize >= 10000.0d ? secondWinUntis * secondPrize * 0.8d : secondWinUntis
					* secondPrize;//
		}
		if (thirdWinUntis > 0) {
			prize += thirdWinUntis * thirdPrize >= 10000.0d ? thirdWinUntis * thirdPrize * 0.8d : thirdWinUntis
					* thirdPrize;//

		}
		if (fourthWinUntis > 0) {
			prize += fourthWinUntis * fourthPrize >= 10000.0d ? fourthWinUntis * fourthPrize * 0.8d : fourthWinUntis
					* fourthPrize;//
		}
		if (fifthWinUntis > 0) {
			prize += fifthWinUntis * 10 >= 10000.0d ? fifthWinUntis * fifthPrize * 0.8d : fifthWinUntis * fifthPrize;//
		}
		if (sixthWinUntis > 0) {
			prize += sixthWinUntis * 5 >= 10000.0d ? sixthWinUntis * sixthPrize * 0.8d : sixthWinUntis * sixthPrize;//
		}

		return prize;
	}

	public double getPrize() {
		double prize = 0;
		if (firstWinUntis > 0) {
			prize += firstWinUntis * firstPrize;
		}
		if (secondWinUntis > 0) {
			prize += secondWinUntis * secondPrize;//
		}
		if (thirdWinUntis > 0) {
			prize += thirdWinUntis * thirdPrize;//

		}
		if (fourthWinUntis > 0) {
			prize += fourthWinUntis * fourthPrize;//
		}
		if (fifthWinUntis > 0) {
			prize += fifthWinUntis * fifthPrize;//
		}
		if (sixthWinUntis > 0) {
			prize += sixthWinUntis * sixthPrize;//
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

	protected void addFirstWinUntis(int firstWinUntis) {
		this.firstWinUntis += firstWinUntis;
	}

	protected void addSecondWinUntis(int secondWinUntis) {
		this.secondWinUntis += secondWinUntis;
	}

	protected void addThirdWinUntis(int thirdWinUntis) {
		this.thirdWinUntis += thirdWinUntis;
	}

	protected void addFourthWinUntis(int fourthWinUntis) {
		this.fourthWinUntis += fourthWinUntis;
	}

	protected void addFifthWinUntis(int fifthWinUntis) {
		this.fifthWinUntis += fifthWinUntis;
	}

	protected void addSixthWinUntis(int sixthWinUntis) {
		this.sixthWinUntis += sixthWinUntis;
	}

}
