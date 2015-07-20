package com.cai310.lottery.prizeutils.welfare3d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dWinUnit;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.welfare3d.Welfare3dCompoundContent;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;
import com.cai310.utils.NumUtils;

public class PrizeWork {
	/** 单式方案内容 */
	private String singleContent;

	private String result;

	private Integer multiple;

	/** 复式方案内容 */
	private Collection<Welfare3dCompoundContent> compoundContent;

	/** 结果封装BEAN */
	private Welfare3dWinUnit welfare3dWinUnit;

	private Welfare3dPlayType playType;

	/**
	 * 构造函数中调用相关方法进行奖金计算
	 * 
	 * @param singleContent
	 * @param result
	 * @param multiple
	 * @param playType
	 * @throws DataException
	 */
	public PrizeWork(String singleContent, String result, Integer multiple, Welfare3dPlayType playType)
			throws DataException {
		this.singleContent = singleContent;
		this.result = result;
		this.multiple = multiple;
		this.playType = playType;
		welfare3dWinUnit = new Welfare3dWinUnit();
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
	public PrizeWork(Collection<Welfare3dCompoundContent> compoundContent, String result, Integer multiple,
			Welfare3dPlayType playType) throws DataException {
		this.compoundContent = compoundContent;
		this.result = result;
		this.multiple = multiple;
		this.playType = playType;
		welfare3dWinUnit = new Welfare3dWinUnit();
		initCompoundData();
	}

	/**
	 * 返回奖金计算结果
	 * 
	 * @return
	 */
	public Welfare3dWinUnit getWelfare3dWinUnit() {
		return welfare3dWinUnit;
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
		Welfare3dWinUnit welfare3dWinUnitTemp;
		List<Integer> bet1;
		List<Integer> bet2;
		List<Integer> bet3;
		for (String lineBet : arr) {
			if (lineBet.indexOf(Constant.SINGLE_SEPARATOR_FOR_NUMBER) != -1) {
				bets = NumUtils.toIntegerList(lineBet, Constant.SINGLE_SEPARATOR_FOR_NUMBER);
			} else {
				bets = new ArrayList<Integer>();
				bets.add(Integer.valueOf(lineBet));
			}
			if (playType.equals(Welfare3dPlayType.Direct)) {
				// //直选
				bet1 = new ArrayList<Integer>();
				bet1.add(bets.get(0));
				bet2 = new ArrayList<Integer>();
				bet2.add(bets.get(1));
				bet3 = new ArrayList<Integer>();
				bet3.add(bets.get(2));
				welfare3dWinUnitTemp = playType.calcWinUnit(resultNums, bet1, bet2, bet3 , multiple);
			} else if (playType.equals(Welfare3dPlayType.Group3)) {
				// //组选3
				welfare3dWinUnitTemp = playType.calcWinUnit(resultNums, bets, multiple);
			} else if (playType.equals(Welfare3dPlayType.Group6)) {
				// //组选6
				welfare3dWinUnitTemp = playType.calcWinUnit(resultNums, bets, multiple);
			} else if (playType.equals(Welfare3dPlayType.DirectSum)) {
				// //直选和值
				welfare3dWinUnitTemp = playType.calcWinUnit(resultNums, bets, multiple);
			} else if (playType.equals(Welfare3dPlayType.GroupSum)) {
				// //组选和值
				welfare3dWinUnitTemp = playType.calcWinUnit(resultNums, bets, multiple);
			} else {
				throw new DataException("方案投注类型出错！");
			}
			welfare3dWinUnit.addWelfare3dWinUnits(welfare3dWinUnitTemp);
		}
	}

	private void checkCompoundWin() throws DataException {
		String[] results = result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
		List<Integer> resultNums = NumUtils.toIntegerList(results);
		Welfare3dCompoundContent welfare3dCompoundContent;
		Welfare3dWinUnit welfare3dWinUnitTemp;
		Iterator<Welfare3dCompoundContent> compoundContentIt = compoundContent.iterator();
		while (compoundContentIt.hasNext()) {
			welfare3dCompoundContent = compoundContentIt.next();
			if (playType.equals(Welfare3dPlayType.Direct)) {
				// //直选
				welfare3dWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(welfare3dCompoundContent
						.getArea1List()), NumUtils.toIntegerList(welfare3dCompoundContent.getArea2List()), NumUtils
						.toIntegerList(welfare3dCompoundContent.getArea3List()), multiple);
			} else if (playType.equals(Welfare3dPlayType.Group3)) {
				// //组选3
				welfare3dWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(welfare3dCompoundContent
						.getGroup3List()), multiple);
			} else if (playType.equals(Welfare3dPlayType.Group6)) {
				// //组选6
				welfare3dWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(welfare3dCompoundContent
						.getGroup6List()), multiple);
			} else if (playType.equals(Welfare3dPlayType.DirectSum)) {
				// //直选和值
				welfare3dWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(welfare3dCompoundContent
						.getDirectSumList()), multiple);
			} else if (playType.equals(Welfare3dPlayType.GroupSum)) {
				// //组选和值
				welfare3dWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(welfare3dCompoundContent
						.getGroupSumList()), multiple);
			} else if (playType.equals(Welfare3dPlayType.BaoChuan)) {
				// //包串
				welfare3dWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(welfare3dCompoundContent
						.getBaoChuanList()), multiple);
			} else if (playType.equals(Welfare3dPlayType.DirectKuadu)) {
				// //直选跨度
				welfare3dWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(welfare3dCompoundContent
						.getDirectKuaduList()), multiple);
			} else if (playType.equals(Welfare3dPlayType.Group3Kuadu)) {
				// //组三跨度
				welfare3dWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(welfare3dCompoundContent
						.getG3KuaduList()), multiple);
			} else if (playType.equals(Welfare3dPlayType.Group6Kuadu)) {
				// //组选和值
				welfare3dWinUnitTemp = playType.calcWinUnit(resultNums, NumUtils.toIntegerList(welfare3dCompoundContent
						.getG6KuaduList()), multiple);
			} else {
				throw new DataException("方案投注类型出错！");
			}
			welfare3dWinUnit.addWelfare3dWinUnits(welfare3dWinUnitTemp);
		}

	}
}
