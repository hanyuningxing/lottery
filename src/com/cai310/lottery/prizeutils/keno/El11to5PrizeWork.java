package com.cai310.lottery.prizeutils.keno;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.el11to5.El11to5CompoundContent;
import com.cai310.lottery.support.el11to5.El11to5PlayType;
import com.cai310.lottery.support.el11to5.El11to5WinUnits;
import com.cai310.utils.NumUtils;

/**
 * 11选5奖金计算
 * 
 */
public class El11to5PrizeWork {

	private Collection<El11to5CompoundContent> contents;

	private El11to5WinUnits el11to5WinUnits;

	private String result;

	private El11to5PlayType el11to5PlayType;

	private Integer multiple;

	public El11to5PrizeWork(Collection<El11to5CompoundContent> content, Integer multiple, String result,
			El11to5PlayType el11to5PlayType) throws DataException{
		this.contents = content;
		this.multiple = multiple;
		this.result = result;
		this.el11to5PlayType = el11to5PlayType;
		el11to5WinUnits = new El11to5WinUnits();
		checkCompoundWin();
	}
	
	public El11to5PrizeWork(String content, Integer multiple, String result,
			El11to5PlayType el11to5PlayType) throws DataException{
		this.multiple = multiple;
		this.result = result;
		this.el11to5PlayType = el11to5PlayType;
		el11to5WinUnits = new El11to5WinUnits();
		checkSingleWin(content);
	}

	public El11to5WinUnits getEl11to5Win() {
		return el11to5WinUnits;
	}

	private void checkCompoundWin() throws DataException{
		Integer[] results = NumUtils.toIntegerArr(result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER));
		if(results.length!=5){
			throw new RuntimeException("开奖号码有误！");
		}
		El11to5WinUnits el11to5WinUnitsTemp;
		for (El11to5CompoundContent el11to5CompoundContent : contents) {
			el11to5WinUnitsTemp = el11to5PlayType.calcWinUnit(results, el11to5CompoundContent, multiple);
			if (el11to5WinUnitsTemp.isWon()) {
				el11to5WinUnits.addEl11to5WinUnits(el11to5WinUnitsTemp);
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
		El11to5WinUnits el11to5WinUnitsTemp;
		for (String lineBet : arr) {
			El11to5CompoundContent el11to5CompoundContent=new El11to5CompoundContent();
			if (el11to5PlayType.equals(El11to5PlayType.ForeTwoDirect)) {
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				bet1 = new ArrayList<String>();
				bet1.add(temps[0]);
				bet2 = new ArrayList<String>();
				bet2.add(temps[1]);
				el11to5CompoundContent.setBet1List(bet1);
				el11to5CompoundContent.setBet2List(bet2);
				el11to5WinUnitsTemp = el11to5PlayType.calcWinUnit(results, el11to5CompoundContent, multiple);
			} else if (el11to5PlayType.equals(El11to5PlayType.ForeThreeDirect)) {
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				bet1 = new ArrayList<String>();
				bet1.add(temps[0]);
				bet2 = new ArrayList<String>();
				bet2.add(temps[1]);
				bet3 = new ArrayList<String>();
				bet3.add(temps[2]);
				el11to5CompoundContent.setBet1List(bet1);
				el11to5CompoundContent.setBet2List(bet2);
				el11to5CompoundContent.setBet3List(bet3);
				el11to5WinUnitsTemp = el11to5PlayType.calcWinUnit(results, el11to5CompoundContent, multiple);
			} else {
				bets = new ArrayList<String>();
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				for(String str:temps){
					bets.add(str);
				}
				el11to5CompoundContent.setBetList(bets);
				el11to5WinUnitsTemp = el11to5PlayType.calcWinUnit(results, el11to5CompoundContent, multiple);
			}
			if (el11to5WinUnitsTemp.isWon()) {
				el11to5WinUnits.addEl11to5WinUnits(el11to5WinUnitsTemp);
			}
			
		}
	}

}
