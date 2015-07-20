package com.cai310.lottery.prizeutils.keno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.SscConstant;
import com.cai310.lottery.entity.lottery.pl.PlWinUnit;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.support.ssc.SscCompoundContent;
import com.cai310.lottery.support.ssc.SscPlayType;
import com.cai310.lottery.support.ssc.SscWinUnits;
import com.cai310.utils.NumUtils;
import com.google.common.collect.Lists;

/**
 * 群英会奖金计算
 * 
 */
public class SscPrizeWork {

	private Collection<SscCompoundContent> contents;

	private SscWinUnits sscWinUnits;

	private String result;

	private SscPlayType playType;

	private Integer multiple;

	public SscPrizeWork(Collection<SscCompoundContent> content, Integer multiple, String result, SscPlayType playType)
			throws DataException {
		this.contents = content;
		this.multiple = multiple;
		this.result = result;
		this.playType = playType;
		sscWinUnits = new SscWinUnits();
		checkCompoundWin();
	}

	public SscPrizeWork(String content, Integer multiple, String result, SscPlayType playType) throws DataException {
		this.multiple = multiple;
		this.result = result;
		this.playType = playType;
		sscWinUnits = new SscWinUnits();
		checkSingleWin(content);
	}

	public SscWinUnits getSscWin() {
		return sscWinUnits;
	}

	private void checkCompoundWin() throws DataException {
		Integer[] results = NumUtils.toIntegerArr(result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER));
		if (results.length != 5) {
			throw new RuntimeException("开奖号码有误！");
		}
		SscWinUnits sscWinUnitsTemp;
		for (SscCompoundContent sscCompoundContent : contents) {
			sscWinUnitsTemp = playType.calcWinUnit(results, sscCompoundContent, multiple);
			if (sscWinUnitsTemp.isWon()) {
				sscWinUnits.addSscWinUnits(sscWinUnitsTemp);
			}
		}
	}
	private void checkSingleWin(String singleContent) throws DataException {
		Integer[] results = NumUtils.toIntegerArr(result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER));
		if (results.length != 5) {
			throw new RuntimeException("开奖号码有误！");
		}
		SscWinUnits sscWinUnitsTemp;
		String[] arr = singleContent.split("(\r\n|\n)+");
		List<String> area1List = new ArrayList<String>(); // 万
		List<String> area2List = new ArrayList<String>(); // 千
		List<String> area3List = new ArrayList<String>(); // 百
		List<String> area4List = new ArrayList<String>(); // 十
		List<String> area5List = new ArrayList<String>(); // 个
		SscCompoundContent sscCompoundContent = new SscCompoundContent();
		List<String> numList = new ArrayList<String>();
		for (String str : arr) {
			area1List.clear();
			area2List.clear();
			area3List.clear();
			area4List.clear();
			area5List.clear();
			sscCompoundContent = new SscCompoundContent();
			numList = new ArrayList<String>();
			String[] numArr = str.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
			for (String num : numArr) {
				num = num.trim();
				int number = Integer.parseInt(num);
				if (number < 0 || number > 9)
					throw new DataException("方案内容不正确,号码在[00-09]之间.");
				numList.add("" + number);
			}
			if (playType.equals(SscPlayType.DirectFive)) {
				sscCompoundContent.setArea1List(Lists.newArrayList(numList.get(0)));
				sscCompoundContent.setArea2List(Lists.newArrayList(numList.get(1)));
				sscCompoundContent.setArea3List(Lists.newArrayList(numList.get(2)));
				sscCompoundContent.setArea4List(Lists.newArrayList(numList.get(3)));
				sscCompoundContent.setArea5List(Lists.newArrayList(numList.get(4)));
				if (numList.size() != 5)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(SscPlayType.DirectThree)) {
				if (numList.size() != 3)
					throw new DataException("投注数目不对.");
				sscCompoundContent.setArea3List(Lists.newArrayList(numList.get(0)));
				sscCompoundContent.setArea4List(Lists.newArrayList(numList.get(1)));
				sscCompoundContent.setArea5List(Lists.newArrayList(numList.get(2)));
			} else if (playType.equals(SscPlayType.DirectTwo)) {
				if (numList.size() != 2)
					throw new DataException("投注数目不对.");
				sscCompoundContent.setArea4List(Lists.newArrayList(numList.get(0)));
				sscCompoundContent.setArea5List(Lists.newArrayList(numList.get(1)));
			} else if (playType.equals(SscPlayType.DirectOne)) {
				if (numList.size() != 1)
					throw new DataException("投注数目不对.");
				sscCompoundContent.setArea5List(Lists.newArrayList(numList.get(0)));
			} else if (playType.equals(SscPlayType.ThreeGroup3)) {
				// //组选3
				sscCompoundContent.setGroup3List(numList);
				if (numList.size() != 3)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(SscPlayType.ThreeGroup6)) {
				// //组选6
				sscCompoundContent.setGroup6List(numList);
				if (numList.size() != 3)
					throw new DataException("投注数目不对.");
			} else if (playType.equals(SscPlayType.GroupTwo)) {
				// //二星 组选
				if (numList.size() != 2)
					throw new DataException("投注数目不对.");
				sscCompoundContent.setGroupTwoList(numList);
			} else {
				throw new DataException("投注方式错误");
			}
			sscWinUnitsTemp = playType.calcWinUnit(results, sscCompoundContent, multiple);
			if (sscWinUnitsTemp.isWon()) {
				sscWinUnits.addSscWinUnits(sscWinUnitsTemp);
			}

		}
		
	}

}
