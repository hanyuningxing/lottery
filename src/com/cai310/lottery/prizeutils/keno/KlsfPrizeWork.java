package com.cai310.lottery.prizeutils.keno;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.klsf.KlsfCompoundContent;
import com.cai310.lottery.support.klsf.KlsfPlayType;
import com.cai310.lottery.support.klsf.KlsfWinUnits;
import com.cai310.lottery.support.qyh.QyhCompoundContent;
import com.cai310.utils.NumUtils;

/**
 * 快乐十分奖金计算
 * 
 */
public class KlsfPrizeWork {

	private Collection<KlsfCompoundContent> contents;

	private KlsfWinUnits klsfWinUnits;

	private String result;

	private KlsfPlayType klsfPlayType;

	private Integer multiple;

	public KlsfPrizeWork(Collection<KlsfCompoundContent> content, Integer multiple, String result,
			KlsfPlayType klsfPlayType) {
		this.contents = content;
		this.multiple = multiple;
		this.result = result;
		this.klsfPlayType = klsfPlayType;
		klsfWinUnits = new KlsfWinUnits();
		checkCompoundWin();
	}
	public KlsfPrizeWork(String content, Integer multiple, String result,
			KlsfPlayType klsfPlayType) throws DataException{
		this.multiple = multiple;
		this.result = result;
		this.klsfPlayType = klsfPlayType;
		klsfWinUnits = new KlsfWinUnits();
		checkSingleWin(content);
	}
	public KlsfWinUnits getKlsfWin() {
		return klsfWinUnits;
	}

	private void checkCompoundWin() {
		Integer[] results = NumUtils.toIntegerArr(result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER));
		if(results.length!=8){
			throw new RuntimeException("开奖号码有误！");
		}
		 
		KlsfWinUnits klsfWinUnitsTemp;
		for (KlsfCompoundContent klsfCompoundContent : contents) {
			 klsfWinUnitsTemp = klsfPlayType.calcWinUnit(results, klsfCompoundContent, multiple);
			 if (klsfWinUnitsTemp.isWon()) {
				klsfWinUnits.addKlsfWinUnits(klsfWinUnitsTemp);
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
		if(results.length!=8){
			throw new RuntimeException("开奖号码有误！");
		}
		KlsfWinUnits klsfWinUnitsTemp;
		KlsfCompoundContent klsfCompoundContent = new KlsfCompoundContent();

		for (String lineBet : arr) {
			if (klsfPlayType.equals(KlsfPlayType.ConnectTwoDirect)) {
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				bet1 = new ArrayList<String>();
				bet1.add(temps[0]);
				bet2 = new ArrayList<String>();
				bet2.add(temps[1]);
				klsfCompoundContent.setBet1List(bet1);
				klsfCompoundContent.setBet2List(bet2);
				klsfWinUnitsTemp = klsfPlayType.calcWinUnit(results,klsfCompoundContent, multiple);
			} else if (klsfPlayType.equals(KlsfPlayType.ForeThreeDirect)) {
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				bet1 = new ArrayList<String>();
				bet1.add(temps[0]);
				bet2 = new ArrayList<String>();
				bet2.add(temps[1]);
				bet3 = new ArrayList<String>();
				bet3.add(temps[2]);
				klsfCompoundContent.setBet1List(bet1);
				klsfCompoundContent.setBet2List(bet2);
				klsfCompoundContent.setBet3List(bet3);
				klsfWinUnitsTemp = klsfPlayType.calcWinUnit(results,klsfCompoundContent, multiple);
			} else {
				bets = new ArrayList<String>();
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				for(String str:temps){
					bets.add(str);
				}
				klsfCompoundContent.setBetList(bets);
				klsfWinUnitsTemp = klsfPlayType.calcWinUnit(results, klsfCompoundContent, multiple);
			}
			if (klsfWinUnitsTemp.isWon()) {
				klsfWinUnits.addKlsfWinUnits(klsfWinUnitsTemp);
			}
			
		}
	}

}
