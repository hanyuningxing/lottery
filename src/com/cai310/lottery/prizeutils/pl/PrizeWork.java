package com.cai310.lottery.prizeutils.pl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.lottery.pl.PlWinUnit;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.pl.PlCompoundContent;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.utils.NumUtils;

public class PrizeWork {
	/** 单式方案内容 */
	private String singleContent;

	private String result;

	private Integer multiple;

	/** 复式方案内容 */
	private Collection<PlCompoundContent> compoundContent;

	/** 结果封装BEAN */
	private PlWinUnit plWinUnit;

	private PlPlayType playType;

	/**
	 * 构造函数中调用相关方法进行奖金计算
	 * 
	 * @param singleContent
	 * @param result
	 * @param multiple
	 * @param playType
	 * @throws DataException
	 */
	public PrizeWork(String singleContent, String result, Integer multiple, PlPlayType playType) throws DataException {
		this.singleContent = singleContent;
		this.result = result;
		this.multiple = multiple;
		this.playType = playType;
		plWinUnit = new PlWinUnit();
		initSingleData();
	}

	/**
	 * 构造函数中调用相关方法进行奖金计算
	 * 
	 * @param compoundContent
	 * @param result
	 * @param multiple
	 * @param playType
	 * @throws DataException
	 */
	public PrizeWork(Collection<PlCompoundContent> compoundContent, String result, Integer multiple, PlPlayType playType)
			throws DataException {
		this.compoundContent = compoundContent;
		this.result = result;
		this.multiple = multiple;
		this.playType = playType;
		plWinUnit = new PlWinUnit();
		initCompoundData();
	}

	/**
	 * 返回奖金计算结果
	 * 
	 * @return
	 */
	public PlWinUnit getPlWinUnit() {
		return plWinUnit;
	}

	private void initCompoundData() throws DataException {
		this.checkCompoundWin();

	}

	private void initSingleData() throws DataException {
		this.checkSingleWin();
	}

	private void checkSingleWin() throws DataException {

		String[] results = result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
		List<Integer> resultNums = NumUtils.toIntegerList(results);
		String[] arr = singleContent.split("(\r\n|\n)+");
		List<Integer> bets;
		PlWinUnit plWinUnitTemp;
		List<Integer> bet1;
		List<Integer> bet2;
		List<Integer> bet3;
		List<Integer> bet4;
		List<Integer> bet5;
		for (String lineBet : arr) {
			if (lineBet.indexOf(Constant.SINGLE_SEPARATOR_FOR_NUMBER) != -1) {
				bets = NumUtils.toIntegerList(lineBet, Constant.SINGLE_SEPARATOR_FOR_NUMBER);
			} else {
				bets = new ArrayList<Integer>();
				bets.add(Integer.valueOf(lineBet));
			}
			if (playType.equals(PlPlayType.P5Direct)) {
				// //直选
				 bet1 = new ArrayList<Integer>();
				 bet1.add(bets.get(0));
				 bet2 = new ArrayList<Integer>();
				 bet2.add(bets.get(1));
				 bet3 = new ArrayList<Integer>();
				 bet3.add(bets.get(2));
				 bet4 = new ArrayList<Integer>();
				 bet4.add(bets.get(3));
				 bet5 = new ArrayList<Integer>();
				 bet5.add(bets.get(4));
				plWinUnitTemp = playType.calcWinUnit(resultNums, bet1,bet2, bet3, bet4, bet5 , multiple);
			} else if (playType.equals(PlPlayType.P3Direct)) {
				// //直选
				 bet1 = new ArrayList<Integer>();
				 bet1.add(bets.get(0));
				 bet2 = new ArrayList<Integer>();
				 bet2.add(bets.get(1));
				 bet3 = new ArrayList<Integer>();
				 bet3.add(bets.get(2));
				 plWinUnitTemp = playType.calcWinUnit(resultNums, bet1,bet2, bet3 , multiple);
			} else if (playType.equals(PlPlayType.Group3)) {
				// //组选3
				plWinUnitTemp = playType.calcWinUnit(resultNums, bets, multiple);
			} else if (playType.equals(PlPlayType.Group6)) {
				// //组选6
				plWinUnitTemp = playType.calcWinUnit(resultNums, bets, multiple);
			} else if (playType.equals(PlPlayType.DirectSum)) {
				// //直选和值
				plWinUnitTemp = playType.calcWinUnit(resultNums, bets, multiple);
			} else if (playType.equals(PlPlayType.GroupSum)) {
				// //组选和值
				plWinUnitTemp = playType.calcWinUnit(resultNums, bets, multiple);
			} else {
				throw new DataException("方案投注类型出错！");
			}
			plWinUnit.addPlWinUnits(plWinUnitTemp);
		}
	}

	private void checkCompoundWin() throws DataException {
		String[] results = result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
		List<Integer> resultNums = NumUtils.toIntegerList(results);
		PlCompoundContent plCompoundContent;
		PlWinUnit plWinUnitTemp;
		Iterator<PlCompoundContent> compoundContentIt = compoundContent.iterator();
		while (compoundContentIt.hasNext()) {
			plCompoundContent = compoundContentIt.next();
			if (playType.equals(PlPlayType.P5Direct)) {
				// //直选
				plWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(plCompoundContent
						.getArea1List()), NumUtils.toIntegerList(plCompoundContent.getArea2List()), NumUtils
						.toIntegerList(plCompoundContent.getArea3List()), NumUtils.toIntegerList(plCompoundContent
						.getArea4List()), NumUtils.toIntegerList(plCompoundContent.getArea5List()), multiple);
			} else if (playType.equals(PlPlayType.P3Direct)) {
				// //直选
				plWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(plCompoundContent
						.getArea1List()), NumUtils.toIntegerList(plCompoundContent.getArea2List()), NumUtils
						.toIntegerList(plCompoundContent.getArea3List()), multiple);
			} else if (playType.equals(PlPlayType.Group3)) {
				// //组选3
				plWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(plCompoundContent
						.getGroup3List()), multiple);
			} else if (playType.equals(PlPlayType.Group6)) {
				// //组选6
				plWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(plCompoundContent
						.getGroup6List()), multiple);
			} else if (playType.equals(PlPlayType.DirectSum)) {
				// //直选和值
				plWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(plCompoundContent
						.getDirectSumList()), multiple);
			} else if (playType.equals(PlPlayType.GroupSum)) {
				// //组选和值
				plWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(plCompoundContent
						.getGroupSumList()), multiple);
			} else if (playType.equals(PlPlayType.BaoChuan)) {
				// //包串
				plWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(plCompoundContent
						.getBaoChuanList()), multiple);
			} else if (playType.equals(PlPlayType.P3DirectKuadu)) {
				// //直选跨度
				plWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(plCompoundContent
						.getDirectKuaduList()), multiple);
			} else if (playType.equals(PlPlayType.P3Group3Kuadu)) {
				// //组三跨度
				plWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(plCompoundContent
						.getG3KuaduList()), multiple);
			} else if (playType.equals(PlPlayType.P3Group6Kuadu)) {
				// //组选和值
				plWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(plCompoundContent
						.getG6KuaduList()), multiple);
			} else {
				throw new DataException("方案投注类型出错！");
			}
			plWinUnit.addPlWinUnits(plWinUnitTemp);
		}

	}
}
