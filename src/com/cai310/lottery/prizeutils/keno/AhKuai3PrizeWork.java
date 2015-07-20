package com.cai310.lottery.prizeutils.keno;

import java.util.Collection;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.ahkuai3.AhKuai3CompoundContent;
import com.cai310.lottery.support.ahkuai3.AhKuai3PlayType;
import com.cai310.lottery.support.ahkuai3.AhKuai3WinUnits;
import com.cai310.lottery.support.sdel11to5.SdEl11to5PlayType;
import com.cai310.lottery.support.sdel11to5.SdEl11to5WinUnits;
import com.cai310.utils.NumUtils;

/**
 * 安徽快3奖金计算
 * <p>Title: AhKuai3PrizeWork.java </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: miracle</p>
 * @author leo
 * @date 2014-1-6 上午11:29:06 
 * @version 1.0
 */
public class AhKuai3PrizeWork {
	private Collection<AhKuai3CompoundContent> contents;

	private AhKuai3WinUnits ahkuai3WinUnits;

	private String result;

	private AhKuai3PlayType ahkuai3PlayType;

	private Integer multiple;
	
	public AhKuai3PrizeWork(Collection<AhKuai3CompoundContent> content, Integer multiple, String result,
			AhKuai3PlayType ahkuai3PlayType) throws DataException{
		this.contents = content;
		this.multiple = multiple;
		this.result = result;
		this.ahkuai3PlayType = ahkuai3PlayType;
		ahkuai3WinUnits = new AhKuai3WinUnits();
		checkCompoundWin();
	}
//	public AhKuai3PrizeWork(String content, Integer multiple, String result,
//			AhKuai3PlayType ahkuai3PlayType) throws DataException{
//		this.multiple = multiple;
//		this.result = result;
//		this.ahkuai3PlayType = ahkuai3PlayType;
//		ahkuai3WinUnits = new AhKuai3WinUnits();
//		checkSingleWin(content);
//	}
	private void checkCompoundWin() throws DataException{
		Integer[] results = NumUtils.toIntegerArr(result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER));
		if(results.length!=3){
			throw new RuntimeException("开奖号码有误！");
		}
		AhKuai3WinUnits ahkuai3WinUnitsTemp;
		for (AhKuai3CompoundContent ahkuai3CompoundContent : contents) {
			//返回各玩法中奖注数
			ahkuai3WinUnitsTemp = ahkuai3PlayType.calcWinUnit(results, ahkuai3CompoundContent, multiple);
			if (ahkuai3WinUnitsTemp.isWon()) {
				if(ahkuai3PlayType.HeZhi.equals(this.ahkuai3PlayType)){
					Integer num1 = results[0];
					Integer num2 = results[1];
					Integer num3 = results[2];
					Integer sum = num1 + num2 + num3;
					//根据所选和值，返回相应的单注奖金
					ahkuai3WinUnits.setHeZhiPrize(AhKuai3HezhiPrize.getPrizeBySum(sum));
				}
				ahkuai3WinUnits.addAhKuai3WinUnits(ahkuai3WinUnitsTemp);
			}
		}
	}
	
	public AhKuai3WinUnits getAhKuai3Win() {
		return ahkuai3WinUnits;
	}
}
