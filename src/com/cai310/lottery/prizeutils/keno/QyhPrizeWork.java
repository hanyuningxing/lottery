package com.cai310.lottery.prizeutils.keno;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.qyh.QyhCompoundContent;
import com.cai310.lottery.support.qyh.QyhPlayType;
import com.cai310.lottery.support.qyh.QyhWinUnits;
import com.cai310.utils.NumUtils;

/**
 * 群英会奖金计算
 * 
 */
public class QyhPrizeWork {

	private Collection<QyhCompoundContent> contents;

	private QyhWinUnits qyhWinUnits;

	private String result;

	private QyhPlayType qyhPlayType;

	private Integer multiple;

	public QyhPrizeWork(Collection<QyhCompoundContent> content, Integer multiple, String result, QyhPlayType qyhPlayType)
			throws DataException {
		this.contents = content;
		this.multiple = multiple;
		this.result = result;
		this.qyhPlayType = qyhPlayType;
		qyhWinUnits = new QyhWinUnits();
		checkCompoundWin();
	}

	public QyhPrizeWork(String content, Integer multiple, String result, QyhPlayType qyhPlayType) throws DataException {
		this.multiple = multiple;
		this.result = result;
		this.qyhPlayType = qyhPlayType;
		qyhWinUnits = new QyhWinUnits();
		checkSingleWin(content);
	}

	public QyhWinUnits getQyhWin() {
		return qyhWinUnits;
	}

	private void checkCompoundWin() throws DataException {
		Integer[] results = NumUtils.toIntegerArr(result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER));
		if (results.length != 5) {
			throw new RuntimeException("开奖号码有误！");
		}
		QyhWinUnits qyhWinUnitsTemp;
		for (QyhCompoundContent qyhCompoundContent : contents) {
			qyhWinUnitsTemp = qyhPlayType.calcWinUnit(results, qyhCompoundContent, multiple);
			if (qyhWinUnitsTemp.isWon()) {
				qyhWinUnits.addQyhWinUnits(qyhWinUnitsTemp);
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
		if (results.length != 5) {
			throw new RuntimeException("开奖号码有误！");
		}
		QyhWinUnits qyhWinUnitsTemp;
		for (String lineBet : arr) {
			QyhCompoundContent qyhCompoundContent = new QyhCompoundContent();
			if (qyhPlayType.equals(QyhPlayType.DirectTwo)) {
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				bet1 = new ArrayList<String>();
				bet1.add(temps[0]);
				bet2 = new ArrayList<String>();
				bet2.add(temps[1]);
				qyhCompoundContent.setBet1List(bet1);
				qyhCompoundContent.setBet2List(bet2);
				qyhWinUnitsTemp = qyhPlayType.calcWinUnit(results, qyhCompoundContent, multiple);
			} else if (qyhPlayType.equals(QyhPlayType.DirectThree)) {
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				bet1 = new ArrayList<String>();
				bet1.add(temps[0]);
				bet2 = new ArrayList<String>();
				bet2.add(temps[1]);
				bet3 = new ArrayList<String>();
				bet3.add(temps[2]);
				qyhCompoundContent.setBet1List(bet1);
				qyhCompoundContent.setBet2List(bet2);
				qyhCompoundContent.setBet3List(bet3);
				qyhWinUnitsTemp = qyhPlayType.calcWinUnit(results, qyhCompoundContent, multiple);
			} else {
				bets = new ArrayList<String>();
				String[] temps = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
				for(String str:temps){
					bets.add(str);
				}
				qyhCompoundContent.setBetList(bets);
				qyhWinUnitsTemp = qyhPlayType.calcWinUnit(results, qyhCompoundContent, multiple);
			}
			if (qyhWinUnitsTemp.isWon()) {
				qyhWinUnits.addQyhWinUnits(qyhWinUnitsTemp);
			}

		}
	}

}
