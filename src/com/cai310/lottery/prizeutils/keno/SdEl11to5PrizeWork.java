package com.cai310.lottery.prizeutils.keno;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.sdel11to5.SdEl11to5CompoundContent;
import com.cai310.lottery.support.sdel11to5.SdEl11to5PlayType;
import com.cai310.lottery.support.sdel11to5.SdEl11to5WinUnits;
import com.cai310.utils.NumUtils;

/**
 * 11选5奖金计算
 * 
 */
public class SdEl11to5PrizeWork {

	private Collection<SdEl11to5CompoundContent> contents;

	private SdEl11to5WinUnits sdel11to5WinUnits;

	private String result;

	private SdEl11to5PlayType sdel11to5PlayType;

	private Integer multiple;

	public SdEl11to5PrizeWork(Collection<SdEl11to5CompoundContent> content, Integer multiple, String result,
			SdEl11to5PlayType sdel11to5PlayType) throws DataException{
		this.contents = content;
		this.multiple = multiple;
		this.result = result;
		this.sdel11to5PlayType = sdel11to5PlayType;
		sdel11to5WinUnits = new SdEl11to5WinUnits();
		checkCompoundWin();
	}
	
	public SdEl11to5PrizeWork(String content, Integer multiple, String result,
			SdEl11to5PlayType sdel11to5PlayType) throws DataException{
		this.multiple = multiple;
		this.result = result;
		this.sdel11to5PlayType = sdel11to5PlayType;
		sdel11to5WinUnits = new SdEl11to5WinUnits();
		checkSingleWin(content);
	}

	public SdEl11to5WinUnits getSdEl11to5Win() {
		return sdel11to5WinUnits;
	}

	private void checkCompoundWin() throws DataException{
		Integer[] results = NumUtils.toIntegerArr(result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER));
		if(results.length!=5){
			throw new RuntimeException("开奖号码有误！");
		}
		SdEl11to5WinUnits sdel11to5WinUnitsTemp;
		for (SdEl11to5CompoundContent sdel11to5CompoundContent : contents) {
			sdel11to5WinUnitsTemp = sdel11to5PlayType.calcWinUnit(results, sdel11to5CompoundContent, multiple);
			if (sdel11to5WinUnitsTemp.isWon()) {
				sdel11to5WinUnits.addSdEl11to5WinUnits(sdel11to5WinUnitsTemp);
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
		SdEl11to5WinUnits sdel11to5WinUnitsTemp;
		for (String lineBet : arr) {
			SdEl11to5CompoundContent sdel11to5CompoundContent=new SdEl11to5CompoundContent();
			if (sdel11to5PlayType.equals(SdEl11to5PlayType.ForeTwoDirect)) {
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				bet1 = new ArrayList<String>();
				bet1.add(temps[0]);
				bet2 = new ArrayList<String>();
				bet2.add(temps[1]);
				sdel11to5CompoundContent.setBet1List(bet1);
				sdel11to5CompoundContent.setBet2List(bet2);
				sdel11to5WinUnitsTemp = sdel11to5PlayType.calcWinUnit(results, sdel11to5CompoundContent, multiple);
			} else if (sdel11to5PlayType.equals(SdEl11to5PlayType.ForeThreeDirect)) {
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				bet1 = new ArrayList<String>();
				bet1.add(temps[0]);
				bet2 = new ArrayList<String>();
				bet2.add(temps[1]);
				bet3 = new ArrayList<String>();
				bet3.add(temps[2]);
				sdel11to5CompoundContent.setBet1List(bet1);
				sdel11to5CompoundContent.setBet2List(bet2);
				sdel11to5CompoundContent.setBet3List(bet3);
				sdel11to5WinUnitsTemp = sdel11to5PlayType.calcWinUnit(results, sdel11to5CompoundContent, multiple);
			} else {
				bets = new ArrayList<String>();
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				for(String str:temps){
					bets.add(str);
				}
				sdel11to5CompoundContent.setBetList(bets);
				sdel11to5WinUnitsTemp = sdel11to5PlayType.calcWinUnit(results, sdel11to5CompoundContent, multiple);
			}
			if (sdel11to5WinUnitsTemp.isWon()) {
				sdel11to5WinUnits.addSdEl11to5WinUnits(sdel11to5WinUnitsTemp);
			}
			
		}
	}

}
