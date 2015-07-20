package com.cai310.lottery.prizeutils.dlt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.lottery.dlt.DltWinUnit;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.dlt.DltCompoundContent;
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.utils.NumUtils;

public class PrizeWork {

	/** 单式方案内容 */
	private String singleContent;

	private String result;

	private int multiple;

	/** 复式方案内容 */
	private Collection<DltCompoundContent> compoundContent;

	/** 结果封装BEAN */
	private DltWinUnit winUnit;

	private DltPlayType playType;

	/**
	 * 构造函数中调用相关方法进行奖金计算
	 * 
	 * @param content
	 * @param result
	 * @param multiple
	 * @throws DataException
	 */
	public PrizeWork(String singleContent, String result, int multiple, DltPlayType playType) throws DataException {
		this.singleContent = singleContent;
		this.result = result;
		this.multiple = multiple;
		winUnit = new DltWinUnit();
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
	public PrizeWork(Collection<DltCompoundContent> compoundContent, String result, int multiple, DltPlayType playType)
			throws DataException {
		this.compoundContent = compoundContent;
		this.result = result;
		this.multiple = multiple;
		winUnit = new DltWinUnit();
		this.playType = playType;
		calcPassCompound();
	}

	/**
	 * 返回奖金计算结果
	 * 
	 * @return
	 */
	public DltWinUnit getDltWinUnit() {
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
		if (results.length != 7) {
			throw new RuntimeException("中奖号码格式错误！");
		}

		String[] redResultNums = new String[5];
		redResultNums[0] = results[0];
		redResultNums[1] = results[1];
		redResultNums[2] = results[2];
		redResultNums[3] = results[3];
		redResultNums[4] = results[4];
		String[] blueResultNums = new String[2];
		blueResultNums[0] = results[5];
		blueResultNums[1] = results[6];
		// 用户方案投注号码

		String[] arr = singleContent.split("(\r\n|\n)+");
		List<Integer> blueBets;
		List<Integer> blueDanBets;
		List<Integer> redDanBets;
		List<Integer> redBets;
		Integer[] redBetsTemp;
		Integer[] blueBetsTemp;
		DltWinUnit winUnitTemp;
		for (String lineBet : arr) {
			if (DltPlayType.General.equals(playType)) {
				if (lineBet.indexOf(Constant.SINGLE_SEPARATOR_FOR_NUMBER) != -1) {
					String[] redAndBule = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
					redBetsTemp = new Integer[5];
					redBetsTemp[0] = Integer.valueOf(redAndBule[0]);
					redBetsTemp[1] = Integer.valueOf(redAndBule[1]);
					redBetsTemp[2] = Integer.valueOf(redAndBule[2]);
					redBetsTemp[3] = Integer.valueOf(redAndBule[3]);
					redBetsTemp[4] = Integer.valueOf(redAndBule[4]);
					blueBetsTemp = new Integer[2];
					blueBetsTemp[0] = Integer.valueOf(redAndBule[5]);
					blueBetsTemp[1] = Integer.valueOf(redAndBule[6]);
				} else {
					throw new RuntimeException("方案格式错误！");
				}
				if (redBetsTemp.length + blueBetsTemp.length != 7)
					throw new RuntimeException("方案格式错误！");
				redDanBets = new ArrayList<Integer>();
				redBets = Arrays.asList(redBetsTemp);
				blueDanBets = new ArrayList<Integer>();
				blueBets = Arrays.asList(blueBetsTemp);
				winUnitTemp = playType.calcWinUnit(NumUtils.toIntegerList(redResultNums), NumUtils
						.toIntegerList(blueResultNums), redDanBets, redBets, blueDanBets, blueBets, multiple);
				winUnit.addWinUnit(winUnitTemp);
				winUnit.setGeneralAdditional(Boolean.FALSE);
			} else if (DltPlayType.Select12to2.equals(playType)) {
				if (lineBet.indexOf(Constant.SINGLE_SEPARATOR_FOR_NUMBER) != -1) {
					blueBetsTemp = NumUtils.toIntegerArr(lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER));
				} else {
					throw new RuntimeException("方案格式错误！");
				}
				if (blueBetsTemp.length != 2)
					throw new RuntimeException("方案格式错误！");
				blueDanBets = new ArrayList<Integer>();
				blueBets = Arrays.asList(blueBetsTemp);
				winUnitTemp = playType.calcWinUnit(NumUtils.toIntegerList(blueResultNums), blueDanBets, blueBets,
						multiple);
				winUnit.addWinUnit(winUnitTemp);
				winUnit.setGeneralAdditional(Boolean.FALSE);
			}else if (DltPlayType.GeneralAdditional.equals(playType)) {
				if (lineBet.indexOf(Constant.SINGLE_SEPARATOR_FOR_NUMBER) != -1) {
					String[] redAndBule = lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
					redBetsTemp = new Integer[5];
					redBetsTemp[0] = Integer.valueOf(redAndBule[0]);
					redBetsTemp[1] = Integer.valueOf(redAndBule[1]);
					redBetsTemp[2] = Integer.valueOf(redAndBule[2]);
					redBetsTemp[3] = Integer.valueOf(redAndBule[3]);
					redBetsTemp[4] = Integer.valueOf(redAndBule[4]);
					blueBetsTemp = new Integer[2];
					blueBetsTemp[0] = Integer.valueOf(redAndBule[5]);
					blueBetsTemp[1] = Integer.valueOf(redAndBule[6]);
				} else {
					throw new RuntimeException("方案格式错误！");
				}
				if (redBetsTemp.length + blueBetsTemp.length != 7)
					throw new RuntimeException("方案格式错误！");
				redDanBets = new ArrayList<Integer>();
				redBets = Arrays.asList(redBetsTemp);
				blueDanBets = new ArrayList<Integer>();
				blueBets = Arrays.asList(blueBetsTemp);
				winUnitTemp = playType.calcWinUnit(NumUtils.toIntegerList(redResultNums), NumUtils
						.toIntegerList(blueResultNums), redDanBets, redBets, blueDanBets, blueBets, multiple);
				winUnit.addWinUnit(winUnitTemp);
				winUnit.setGeneralAdditional(Boolean.TRUE);
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
		if (results.length != 7) {
			throw new RuntimeException("中奖号码格式错误！");
		}

		String[] redResultNums = new String[5];
		redResultNums[0] = results[0];
		redResultNums[1] = results[1];
		redResultNums[2] = results[2];
		redResultNums[3] = results[3];
		redResultNums[4] = results[4];
		String[] blueResultNums = new String[2];
		blueResultNums[0] = results[5];
		blueResultNums[1] = results[6];

		// 用户方案投注号码

		DltCompoundContent DltCompoundContent;
		Iterator<DltCompoundContent> compoundContentIt = compoundContent.iterator();
		List<Integer> blueBets;
		List<Integer> blueDanBets;
		List<Integer> redDanBets;
		List<Integer> redBets;
		DltWinUnit winUnitTemp;
		while (compoundContentIt.hasNext()) {
			DltCompoundContent = compoundContentIt.next();
			if (DltPlayType.General.equals(playType)) {
				redDanBets = NumUtils.toIntegerList(DltCompoundContent.getRedDanList());

				redBets = NumUtils.toIntegerList(DltCompoundContent.getRedList());

				blueDanBets = NumUtils.toIntegerList(DltCompoundContent.getBlueDanList());

				blueBets = NumUtils.toIntegerList(DltCompoundContent.getBlueList());

				winUnitTemp = playType.calcWinUnit(NumUtils.toIntegerList(redResultNums), NumUtils
						.toIntegerList(blueResultNums), redDanBets, redBets, blueDanBets, blueBets, multiple);

				winUnit.addWinUnit(winUnitTemp);
				winUnit.setGeneralAdditional(Boolean.FALSE);
			} else if (DltPlayType.Select12to2.equals(playType)) {
				blueDanBets = NumUtils.toIntegerList(DltCompoundContent.getBlueDanList());

				blueBets = NumUtils.toIntegerList(DltCompoundContent.getBlueList());

				winUnitTemp = playType.calcWinUnit(NumUtils.toIntegerList(blueResultNums), blueDanBets, blueBets,
						multiple);
				winUnit.addWinUnit(winUnitTemp);
				winUnit.setGeneralAdditional(Boolean.FALSE);
			}else if (DltPlayType.GeneralAdditional.equals(playType)) {
				redDanBets = NumUtils.toIntegerList(DltCompoundContent.getRedDanList());

				redBets = NumUtils.toIntegerList(DltCompoundContent.getRedList());

				blueDanBets = NumUtils.toIntegerList(DltCompoundContent.getBlueDanList());

				blueBets = NumUtils.toIntegerList(DltCompoundContent.getBlueList());

				winUnitTemp = playType.calcWinUnit(NumUtils.toIntegerList(redResultNums), NumUtils
						.toIntegerList(blueResultNums), redDanBets, redBets, blueDanBets, blueBets, multiple);

				winUnit.addWinUnit(winUnitTemp);
				winUnit.setGeneralAdditional(Boolean.TRUE);
			} else {
				throw new DataException("投注方式不正确.");
			}
		}

	}

}
