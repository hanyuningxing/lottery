package com.cai310.lottery.fetch.jczq.matchinfo;

import java.math.BigDecimal;
import java.util.List;

import com.cai310.lottery.fetch.jczq.local.EuropeOdds;

public class EuroOddsUtil {
	
	public static JczqMatchEuroOdds calculateAvgEuroOdds(String matchKey, String remoteId, List<EuropeOdds> euroOddsList) {
		JczqMatchEuroOdds euroOdds = new JczqMatchEuroOdds(matchKey, remoteId);
		Double sumWonSp = new Double(0);
		Double sumDrawSp = new Double(0);
		Double sumLoseSp = new Double(0);
		Double temp = new Double(0);
		Double sumWonRate = new Double(0);
		Double sumDrawRate = new Double(0);
		Double sumLoseRate = new Double(0);
		Integer num = euroOddsList.size();
		for(EuropeOdds odd : euroOddsList) {
			sumWonSp += odd.getWinSp();
			sumDrawSp += odd.getDrawSp();
			sumLoseSp += odd.getLoseSp();
			temp = odd.getWinSp()*(odd.getDrawSp() + odd.getLoseSp()) + odd.getDrawSp()*odd.getLoseSp();
			sumWonRate += odd.getDrawSp()*odd.getLoseSp()/temp;
			sumDrawRate += odd.getWinSp()*odd.getLoseSp()/temp;
			sumLoseRate += odd.getWinSp()*odd.getDrawSp()/temp;
		}
		Double avgWonSp = sumWonSp/num;
		Double avgDrawSp = sumDrawSp/num;
		Double avgLoseSp = sumLoseSp/num;
		Double avgWonRate = sumWonRate/num;
		Double avgDrawRate = sumDrawRate/num;
		Double avgLoseRate = sumLoseRate/num;
		euroOdds.setAvgWonSp(round(avgWonSp));
		euroOdds.setAvgDrawSp(round(avgDrawSp));
		euroOdds.setAvgLoseSp(round(avgLoseSp));
		
		Double sumWonVariance = new Double(0);
		Double sumDrawVariance = new Double(0);
		Double sumLoseVariance = new Double(0);
		for(EuropeOdds odd : euroOddsList) {
			sumWonVariance += (odd.getWinSp()-avgWonSp)*(odd.getWinSp()-avgWonSp);
			sumDrawVariance += (odd.getDrawSp()-avgDrawSp)*(odd.getDrawSp()-avgDrawSp);
			sumLoseVariance += (odd.getLoseSp()-avgLoseSp)*(odd.getLoseSp()-avgLoseSp);
		}
		euroOdds.setWonKelly(round(sumWonVariance*avgWonRate*avgWonRate*1000/num));
		euroOdds.setDrawKelly(round(sumDrawVariance*avgDrawRate*avgDrawRate*1000/num));
		euroOdds.setLoseKelly(round(sumLoseVariance*avgLoseRate*avgLoseRate*1000/num));
		return euroOdds;
	}
	
	public static double round(double prize) {
		BigDecimal decimal = new BigDecimal(prize);
		decimal = decimal.setScale(3, BigDecimal.ROUND_DOWN);// 保留三位小数，后面的舍去
		decimal = decimal.setScale(2, BigDecimal.ROUND_HALF_UP);// 保留两位小数，1.235=1.23，1.236=1.24
		return decimal.doubleValue();
	}

}
