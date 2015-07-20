package com.cai310.lottery.prizeutils.keno;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.klpk.KlpkCompoundContent;
import com.cai310.lottery.support.klpk.KlpkPlayType;
import com.cai310.lottery.support.klpk.KlpkWinUnits;
import com.cai310.utils.NumUtils;

/**
 * 奖金计算
 * 
 */
public class KlpkPrizeWork {

	private Collection<KlpkCompoundContent> contents;

	private KlpkWinUnits klpkWinUnits;

	private String result;

	private KlpkPlayType klpkPlayType;

	private Integer multiple;

	public KlpkPrizeWork(Collection<KlpkCompoundContent> content, Integer multiple, String result,
			KlpkPlayType klpkPlayType) throws DataException{
		this.contents = content;
		this.multiple = multiple;
		this.result = result;
		this.klpkPlayType = klpkPlayType;
		klpkWinUnits = new KlpkWinUnits();
		checkCompoundWin();
	}
	public KlpkWinUnits getKlpkWin() {
		return klpkWinUnits;
	}

	private void checkCompoundWin() throws DataException{
		Integer[] results = NumUtils.toIntegerArr(result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER));
		if(results.length!=6){
			throw new RuntimeException("开奖号码有误！");
		}
		KlpkWinUnits klpkWinUnitsTemp;
		for (KlpkCompoundContent klpkCompoundContent : contents) {
			klpkWinUnitsTemp = klpkPlayType.calcWinUnit(results, klpkCompoundContent, multiple);
			if (klpkWinUnitsTemp.isWon()) {
				klpkWinUnits.addKlpkWinUnits(klpkWinUnitsTemp);
			}
		}
	}

}
