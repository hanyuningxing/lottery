package com.cai310.lottery.prizeutils.welfare36to7;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7WinUnit;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.welfare36to7.Welfare36to7CompoundContent;
import com.cai310.lottery.support.welfare36to7.Welfare36to7PlayType;
import com.cai310.utils.NumUtils;

public class PrizeWork {

	/** 单式方案内容 */
	private String singleContent;

	private String result;

	private int multiple;

	/** 复式方案内容 */
	private Collection<Welfare36to7CompoundContent> compoundContent;

	/** 结果封装BEAN */
	private Welfare36to7WinUnit winUnit;

	private Welfare36to7PlayType playType;

	/**
	 * 构造函数中调用相关方法进行奖金计算
	 * 
	 * @param content
	 * @param result
	 * @param multiple
	 * @throws DataException
	 */
	public PrizeWork(String singleContent, String result, int multiple, Welfare36to7PlayType playType)
			throws DataException {
		this.singleContent = singleContent;
		this.result = result;
		this.multiple = multiple;
		winUnit = new Welfare36to7WinUnit();
		this.playType = playType;
		calcPassSingle();
	}

	/**
	 * 构造函数中调用相关方法进行奖金计算
	 * 
	 * @param content
	 * @param result
	 * @param multiple
	 * @throws DataException
	 */
	public PrizeWork(Collection<Welfare36to7CompoundContent> compoundContent, String result, int multiple,
			Welfare36to7PlayType playType) throws DataException {
		this.compoundContent = compoundContent;
		this.result = result;
		this.multiple = multiple;
		winUnit = new Welfare36to7WinUnit();
		this.playType = playType;
		calcPassCompound();
	}

	/**
	 * 返回奖金计算结果
	 * 
	 * @return
	 */
	public Welfare36to7WinUnit getWelfare36to7WinUnit() {
		return winUnit;
	}

	// //////////////////////////////////////计算中奖算法///////////////////////////////////////////

	/**
	 * 开奖结果计算(单式)
	 * 
	 * @throws DataException
	 */
	public void calcPassSingle() throws DataException {

		// 开奖结果号码
		String[] results = result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
		Integer[] resultNums = NumUtils.toIntegerArr(results);
		List<Integer> commonResults = new ArrayList<Integer>();
		commonResults.add(resultNums[0]);
		commonResults.add(resultNums[1]);
		commonResults.add(resultNums[2]);
		commonResults.add(resultNums[3]);
		commonResults.add(resultNums[4]);
		commonResults.add(resultNums[5]);
		Integer specialResult = resultNums[6];
		// 用户方案投注号码

		String[] arr = singleContent.split("(\r\n|\n)+");
		List<Integer> bets;
		Welfare36to7WinUnit winUnitTemp;
		for (String lineBet : arr) {
			if (lineBet.indexOf(Constant.SINGLE_SEPARATOR_FOR_NUMBER) != -1) {
				bets = NumUtils.toIntegerList(lineBet, Constant.SINGLE_SEPARATOR_FOR_NUMBER);
			} else {
				bets = new ArrayList<Integer>();
				bets.add(Integer.valueOf(lineBet));
			}
			if (Welfare36to7PlayType.General.equals(playType)) {
				winUnitTemp = playType.calcWinUnit(commonResults, specialResult, bets, multiple);
				winUnit.addWelfare36to7WinUnit(winUnitTemp);
			} else if (Welfare36to7PlayType.Haocai1.equals(playType)) {
				winUnitTemp = playType.calcWinUnit(commonResults, specialResult, bets, multiple);
				winUnit.addWelfare36to7WinUnit(winUnitTemp);
			} else if (Welfare36to7PlayType.Haocai2.equals(playType)) {
				winUnitTemp = playType.calcWinUnit(commonResults, specialResult, bets, multiple);
				winUnit.addWelfare36to7WinUnit(winUnitTemp);
			} else if (Welfare36to7PlayType.Haocai3.equals(playType)) {
				winUnitTemp = playType.calcWinUnit(commonResults, specialResult, bets, multiple);
				winUnit.addWelfare36to7WinUnit(winUnitTemp);
			} else if (Welfare36to7PlayType.Zodiac.equals(playType)) {
				winUnitTemp = playType.calcWinUnit(commonResults, specialResult, bets, multiple);
				winUnit.addWelfare36to7WinUnit(winUnitTemp);
			} else if (Welfare36to7PlayType.Season.equals(playType)) {
				winUnitTemp = playType.calcWinUnit(commonResults, specialResult, bets, multiple);
				winUnit.addWelfare36to7WinUnit(winUnitTemp);
			} else if (Welfare36to7PlayType.Azimuth.equals(playType)) {
				winUnitTemp = playType.calcWinUnit(commonResults, specialResult, bets, multiple);
				winUnit.addWelfare36to7WinUnit(winUnitTemp);
			} else {
				throw new DataException("投注方式不正确.");
			}
		}
	}

	/**
	 * 开奖结果计算(复式)
	 * 
	 * @throws DataException
	 */
	public void calcPassCompound() throws DataException {

		// 开奖结果号码
		String[] results = result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
		Integer[] resultNums = NumUtils.toIntegerArr(results);
		List<Integer> commonResults = new ArrayList<Integer>();
		commonResults.add(resultNums[0]);
		commonResults.add(resultNums[1]);
		commonResults.add(resultNums[2]);
		commonResults.add(resultNums[3]);
		commonResults.add(resultNums[4]);
		commonResults.add(resultNums[5]);
		Integer specialResult = resultNums[6];
		// 用户方案投注号码

		Welfare36to7CompoundContent Welfare36to7CompoundContent;
		Iterator<Welfare36to7CompoundContent> compoundContentIt = compoundContent.iterator();
		List<Integer> bets;
		Welfare36to7WinUnit winUnitTemp;
		while (compoundContentIt.hasNext()) {
			Welfare36to7CompoundContent = compoundContentIt.next();
			if (Welfare36to7PlayType.General.equals(playType)) {
				bets = NumUtils.toIntegerList(Welfare36to7CompoundContent.getWelfare36to7List());
				winUnitTemp = playType.calcWinUnit(commonResults, specialResult, bets, multiple);
				winUnit.addWelfare36to7WinUnit(winUnitTemp);
			} else if (Welfare36to7PlayType.Haocai1.equals(playType)) {
				bets = NumUtils.toIntegerList(Welfare36to7CompoundContent.getHaocai1List());
				winUnitTemp = playType.calcWinUnit(commonResults, specialResult, bets, multiple);
				winUnit.addWelfare36to7WinUnit(winUnitTemp);
			} else if (Welfare36to7PlayType.Haocai2.equals(playType)) {
				bets = NumUtils.toIntegerList(Welfare36to7CompoundContent.getHaocai2List());
				winUnitTemp = playType.calcWinUnit(commonResults, specialResult, bets, multiple);
				winUnit.addWelfare36to7WinUnit(winUnitTemp);
			} else if (Welfare36to7PlayType.Haocai3.equals(playType)) {
				bets = NumUtils.toIntegerList(Welfare36to7CompoundContent.getHaocai3List());
				winUnitTemp = playType.calcWinUnit(commonResults, specialResult, bets, multiple);
				winUnit.addWelfare36to7WinUnit(winUnitTemp);
			} else if (Welfare36to7PlayType.Zodiac.equals(playType)) {
				bets = NumUtils.toIntegerList(Welfare36to7CompoundContent.getZodiacList());
				winUnitTemp = playType.calcWinUnit(commonResults, specialResult, bets, multiple);
				winUnit.addWelfare36to7WinUnit(winUnitTemp);
			} else if (Welfare36to7PlayType.Season.equals(playType)) {
				bets = NumUtils.toIntegerList(Welfare36to7CompoundContent.getSeasonList());
				winUnitTemp = playType.calcWinUnit(commonResults, specialResult, bets, multiple);
				winUnit.addWelfare36to7WinUnit(winUnitTemp);
			} else if (Welfare36to7PlayType.Azimuth.equals(playType)) {
				bets = NumUtils.toIntegerList(Welfare36to7CompoundContent.getAzimuthList());
				winUnitTemp = playType.calcWinUnit(commonResults, specialResult, bets, multiple);
				winUnit.addWelfare36to7WinUnit(winUnitTemp);
			} else {
				throw new DataException("投注方式不正确.");
			}
		}

	}

}
