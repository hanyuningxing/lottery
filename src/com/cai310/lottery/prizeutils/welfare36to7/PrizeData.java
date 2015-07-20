package com.cai310.lottery.prizeutils.welfare36to7;

import com.cai310.lottery.prizeutils.template.VariableString;

public class PrizeData {

	private Integer firstPrize;
	private Integer secondPrize;
	private Integer thirdPrize;
	private Integer fourthPrize = 500;
	private Integer fifthPrize = 100;
	private Integer sixthPrize = 10;
	private Integer haocai2Prize;
	private Integer haocai3Prize;
	private Integer zodiacPrize = 50;
	private Integer seasonPrize = 15;
	private Integer azimuthPrize = 15;

	private Integer firstWinUnits;
	private Integer secondWinUnits;
	private Integer thirdWinUnits;
	private Integer fourthWinUnits;
	private Integer fifthWinUnits;
	private Integer sixthWinUnits;
	private Integer haocai2WinUnits;
	private Integer haocai3WinUnits;
	private Integer zodiacWinUnits;
	private Integer seasonWinUnits;
	private Integer azimuthWinUnits;

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
		if (haocai2WinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "好彩2");
			varWonLineText.setVar("WINUNITS", haocai2WinUnits);
			varWonLineText.setVar("PRIZE", haocai2WinUnits * haocai2Prize >= 10000.0d ? haocai2WinUnits * haocai2Prize
					* 0.8d : haocai2WinUnits * haocai2Prize);
			prizeDesc += varWonLineText.toString();
		}
		if (haocai3WinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "好彩3");
			varWonLineText.setVar("WINUNITS", haocai3WinUnits);
			varWonLineText.setVar("PRIZE", haocai3WinUnits * haocai3Prize >= 10000.0d ? haocai3WinUnits * haocai3Prize
					* 0.8d : haocai3WinUnits * haocai3Prize);
			prizeDesc += varWonLineText.toString();
		}
		if (zodiacWinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "生肖");
			varWonLineText.setVar("WINUNITS", zodiacWinUnits);
			varWonLineText.setVar("PRIZE", zodiacWinUnits * zodiacPrize >= 10000.0d ? zodiacWinUnits * zodiacPrize
					* 0.8d : zodiacWinUnits * zodiacPrize);
			prizeDesc += varWonLineText.toString();
		}
		if (seasonWinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "季节");
			varWonLineText.setVar("WINUNITS", seasonWinUnits);
			varWonLineText.setVar("PRIZE", seasonWinUnits * seasonPrize >= 10000.0d ? seasonWinUnits * seasonPrize
					* 0.8d : seasonWinUnits * seasonPrize);
			prizeDesc += varWonLineText.toString();
		}
		if (azimuthWinUnits > 0) {
			varWonLineText.setVar("PRIZEITEM", "方位");
			varWonLineText.setVar("WINUNITS", azimuthWinUnits);
			varWonLineText.setVar("PRIZE", azimuthWinUnits * azimuthPrize >= 10000.0d ? azimuthWinUnits * azimuthPrize
					* 0.8d : azimuthWinUnits * azimuthPrize);
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
		if (haocai3WinUnits > 0) {
			prize += haocai3WinUnits * haocai3Prize >= 10000.0d ? haocai3WinUnits * haocai3Prize * 0.8d
					: haocai3WinUnits * haocai3Prize;//
		}
		if (haocai2WinUnits > 0) {
			prize += haocai2WinUnits * haocai2Prize >= 10000.0d ? haocai2WinUnits * haocai2Prize * 0.8d
					: haocai2WinUnits * haocai2Prize;//
		}
		if (zodiacWinUnits > 0) {
			prize += zodiacWinUnits * zodiacPrize >= 10000.0d ? zodiacWinUnits * zodiacPrize * 0.8d : zodiacWinUnits
					* zodiacPrize;//
		}
		if (seasonWinUnits > 0) {
			prize += seasonWinUnits * seasonPrize >= 10000.0d ? seasonWinUnits * seasonPrize * 0.8d : seasonWinUnits
					* seasonPrize;//
		}
		if (azimuthWinUnits > 0) {
			prize += azimuthWinUnits * azimuthPrize >= 10000.0d ? azimuthWinUnits * azimuthPrize * 0.8d
					: azimuthWinUnits * azimuthPrize;//
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
		if (haocai3WinUnits > 0) {
			prize += haocai3WinUnits * haocai3Prize;//
		}
		if (haocai2WinUnits > 0) {
			prize += haocai2WinUnits * haocai2Prize;//
		}
		if (zodiacWinUnits > 0) {
			prize += zodiacWinUnits * zodiacPrize;//
		}
		if (seasonWinUnits > 0) {
			prize += seasonWinUnits * seasonPrize;//
		}
		if (azimuthWinUnits > 0) {
			prize += azimuthWinUnits * azimuthPrize;//
		}
		return prize;
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
	 * @return the haocai2Prize
	 */
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
	public Integer getAzimuthPrize() {
		return azimuthPrize;
	}

	/**
	 * @param azimuthPrize the azimuthPrize to set
	 */
	public void setAzimuthPrize(Integer azimuthPrize) {
		this.azimuthPrize = azimuthPrize;
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
	 * @return the haocai2WinUnits
	 */
	public Integer getHaocai2WinUnits() {
		return haocai2WinUnits;
	}

	/**
	 * @param haocai2WinUnits the haocai2WinUnits to set
	 */
	public void setHaocai2WinUnits(Integer haocai2WinUnits) {
		this.haocai2WinUnits = haocai2WinUnits;
	}

	/**
	 * @return the haocai3WinUnits
	 */
	public Integer getHaocai3WinUnits() {
		return haocai3WinUnits;
	}

	/**
	 * @param haocai3WinUnits the haocai3WinUnits to set
	 */
	public void setHaocai3WinUnits(Integer haocai3WinUnits) {
		this.haocai3WinUnits = haocai3WinUnits;
	}

	/**
	 * @return the zodiacWinUnits
	 */
	public Integer getZodiacWinUnits() {
		return zodiacWinUnits;
	}

	/**
	 * @param zodiacWinUnits the zodiacWinUnits to set
	 */
	public void setZodiacWinUnits(Integer zodiacWinUnits) {
		this.zodiacWinUnits = zodiacWinUnits;
	}

	/**
	 * @return the seasonWinUnits
	 */
	public Integer getSeasonWinUnits() {
		return seasonWinUnits;
	}

	/**
	 * @param seasonWinUnits the seasonWinUnits to set
	 */
	public void setSeasonWinUnits(Integer seasonWinUnits) {
		this.seasonWinUnits = seasonWinUnits;
	}

	/**
	 * @return the azimuthWinUnits
	 */
	public Integer getAzimuthWinUnits() {
		return azimuthWinUnits;
	}

	/**
	 * @param azimuthWinUnits the azimuthWinUnits to set
	 */
	public void setAzimuthWinUnits(Integer azimuthWinUnits) {
		this.azimuthWinUnits = azimuthWinUnits;
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
