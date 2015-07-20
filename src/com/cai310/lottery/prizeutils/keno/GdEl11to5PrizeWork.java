package com.cai310.lottery.prizeutils.keno;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.gdel11to5.GdEl11to5CompoundContent;
import com.cai310.lottery.support.gdel11to5.GdEl11to5PlayType;
import com.cai310.lottery.support.gdel11to5.GdEl11to5WinUnits;
import com.cai310.utils.NumUtils;

/**
 * 11选5奖金计算
 * 
 */
public class GdEl11to5PrizeWork {

	private Collection<GdEl11to5CompoundContent> contents;

	private GdEl11to5WinUnits gdel11to5WinUnits;

	private String result;

	private GdEl11to5PlayType gdel11to5PlayType;

	private Integer multiple;

	public GdEl11to5PrizeWork(Collection<GdEl11to5CompoundContent> content, Integer multiple, String result,
			GdEl11to5PlayType gdel11to5PlayType) throws DataException{
		this.contents = content;
		this.multiple = multiple;
		this.result = result;
		this.gdel11to5PlayType = gdel11to5PlayType;
		gdel11to5WinUnits = new GdEl11to5WinUnits();
		checkCompoundWin();
	}
	
	public GdEl11to5PrizeWork(String content, Integer multiple, String result,
			GdEl11to5PlayType gdel11to5PlayType) throws DataException{
		this.multiple = multiple;
		this.result = result;
		this.gdel11to5PlayType = gdel11to5PlayType;
		gdel11to5WinUnits = new GdEl11to5WinUnits();
		checkSingleWin(content);
	}

	public GdEl11to5WinUnits getGdEl11to5Win() {
		return gdel11to5WinUnits;
	}

	private void checkCompoundWin() throws DataException{
		Integer[] results = NumUtils.toIntegerArr(result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER));
		if(results.length!=5){
			throw new RuntimeException("开奖号码有误！");
		}
		GdEl11to5WinUnits gdel11to5WinUnitsTemp;
		for (GdEl11to5CompoundContent gdel11to5CompoundContent : contents) {
			gdel11to5WinUnitsTemp = gdel11to5PlayType.calcWinUnit(results, gdel11to5CompoundContent, multiple);
			if (gdel11to5WinUnitsTemp.isWon()) {
				gdel11to5WinUnits.addSdEl11to5WinUnits(gdel11to5WinUnitsTemp);
			}
		}
	}
	
	private void checkSingleWin(String singleContent) throws DataException {

		Integer[] results = NumUtils.toIntegerArr(result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER));
		String[] arr = singleContent.split("(\r\n|\n)+");
		List<String> bets;
		List<String> bet1;
		List<String> bet2;
		List<String> bet3;
		if(results.length!=5){
			throw new RuntimeException("开奖号码有误！");
		}
		GdEl11to5WinUnits gdel11to5WinUnitsTemp;
		for (String lineBet : arr) {
			GdEl11to5CompoundContent gdel11to5CompoundContent=new GdEl11to5CompoundContent();
			if (gdel11to5PlayType.equals(GdEl11to5PlayType.ForeTwoDirect)) {
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				bet1 = new ArrayList<String>();
				bet1.add(temps[0]);
				bet2 = new ArrayList<String>();
				bet2.add(temps[1]);
				gdel11to5CompoundContent.setBet1List(bet1);
				gdel11to5CompoundContent.setBet2List(bet2);
				gdel11to5WinUnitsTemp = gdel11to5PlayType.calcWinUnit(results, gdel11to5CompoundContent, multiple);
			} else if (gdel11to5PlayType.equals(GdEl11to5PlayType.ForeThreeDirect)) {
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				bet1 = new ArrayList<String>();
				bet1.add(temps[0]);
				bet2 = new ArrayList<String>();
				bet2.add(temps[1]);
				bet3 = new ArrayList<String>();
				bet3.add(temps[2]);
				gdel11to5CompoundContent.setBet1List(bet1);
				gdel11to5CompoundContent.setBet2List(bet2);
				gdel11to5CompoundContent.setBet3List(bet3);
				gdel11to5WinUnitsTemp = gdel11to5PlayType.calcWinUnit(results, gdel11to5CompoundContent, multiple);
			} else {
				bets = new ArrayList<String>();
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				for(String str:temps){
					bets.add(str);
				}
				gdel11to5CompoundContent.setBetList(bets);
				gdel11to5WinUnitsTemp = gdel11to5PlayType.calcWinUnit(results, gdel11to5CompoundContent, multiple);
			}
			if (gdel11to5WinUnitsTemp.isWon()) {
				gdel11to5WinUnits.addSdEl11to5WinUnits(gdel11to5WinUnitsTemp);
			}
			
		}
	}

}
