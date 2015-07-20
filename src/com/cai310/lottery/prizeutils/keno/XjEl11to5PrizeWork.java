package com.cai310.lottery.prizeutils.keno;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.xjel11to5.XjEl11to5CompoundContent;
import com.cai310.lottery.support.xjel11to5.XjEl11to5PlayType;
import com.cai310.lottery.support.xjel11to5.XjEl11to5WinUnits;
import com.cai310.utils.NumUtils;

public class XjEl11to5PrizeWork {
	private Collection<XjEl11to5CompoundContent> contents;

	private XjEl11to5WinUnits xjel11to5WinUnits;

	private String result;

	private XjEl11to5PlayType xjel11to5PlayType;

	private Integer multiple;

	public XjEl11to5PrizeWork(Collection<XjEl11to5CompoundContent> content, Integer multiple, String result,
			XjEl11to5PlayType xjel11to5PlayType) throws DataException{
		this.contents = content;
		this.multiple = multiple;
		this.result = result;
		this.xjel11to5PlayType = xjel11to5PlayType;
		xjel11to5WinUnits = new XjEl11to5WinUnits();
		checkCompoundWin();
	}
	
	public XjEl11to5PrizeWork(String content, Integer multiple, String result,
			XjEl11to5PlayType xjel11to5PlayType) throws DataException{
		this.multiple = multiple;
		this.result = result;
		this.xjel11to5PlayType = xjel11to5PlayType;
		xjel11to5WinUnits = new XjEl11to5WinUnits();
		checkSingleWin(content);
	}

	public XjEl11to5WinUnits getxjEl11to5Win() {
		return xjel11to5WinUnits;
	}

	private void checkCompoundWin() throws DataException{
		Integer[] results = NumUtils.toIntegerArr(result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER));
		if(results.length!=5){
			throw new RuntimeException("开奖号码有误！");
		}
		XjEl11to5WinUnits xjel11to5WinUnitsTemp;
		for (XjEl11to5CompoundContent xjel11to5CompoundContent : contents) {
			xjel11to5WinUnitsTemp = xjel11to5PlayType.calcWinUnit(results, xjel11to5CompoundContent, multiple);
			if (xjel11to5WinUnitsTemp.isWon()) {
				xjel11to5WinUnits.addSdEl11to5WinUnits(xjel11to5WinUnitsTemp);
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
		XjEl11to5WinUnits xjel11to5WinUnitsTemp;
		for (String lineBet : arr) {
			XjEl11to5CompoundContent xjel11to5CompoundContent=new XjEl11to5CompoundContent();
			if (xjel11to5PlayType.equals(XjEl11to5PlayType.ForeTwoDirect)) {
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				bet1 = new ArrayList<String>();
				bet1.add(temps[0]);
				bet2 = new ArrayList<String>();
				bet2.add(temps[1]);
				xjel11to5CompoundContent.setBet1List(bet1);
				xjel11to5CompoundContent.setBet2List(bet2);
				xjel11to5WinUnitsTemp = xjel11to5PlayType.calcWinUnit(results, xjel11to5CompoundContent, multiple);
			} else if (xjel11to5PlayType.equals(XjEl11to5PlayType.ForeThreeDirect)) {
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				bet1 = new ArrayList<String>();
				bet1.add(temps[0]);
				bet2 = new ArrayList<String>();
				bet2.add(temps[1]);
				bet3 = new ArrayList<String>();
				bet3.add(temps[2]);
				xjel11to5CompoundContent.setBet1List(bet1);
				xjel11to5CompoundContent.setBet2List(bet2);
				xjel11to5CompoundContent.setBet3List(bet3);
				xjel11to5WinUnitsTemp = xjel11to5PlayType.calcWinUnit(results, xjel11to5CompoundContent, multiple);
			} else {
				bets = new ArrayList<String>();
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				for(String str:temps){
					bets.add(str);
				}
				xjel11to5CompoundContent.setBetList(bets);
				xjel11to5WinUnitsTemp = xjel11to5PlayType.calcWinUnit(results, xjel11to5CompoundContent, multiple);
			}
			if (xjel11to5WinUnitsTemp.isWon()) {
				xjel11to5WinUnits.addSdEl11to5WinUnits(xjel11to5WinUnitsTemp);
			}
			
		}
	}
}
