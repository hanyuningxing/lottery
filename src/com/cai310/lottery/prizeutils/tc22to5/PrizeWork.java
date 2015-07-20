package com.cai310.lottery.prizeutils.tc22to5;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collection;

import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5WinUnit;
import com.cai310.lottery.support.tc22to5.Tc22to5CompoundContent;
import com.cai310.utils.MathUtils;

public class PrizeWork {
	/** 单式方案内容 */
	private String singleContent;

	private String result;

	private int multiple;

	/** 复式方案内容 */
	private Collection<Tc22to5CompoundContent> compoundContent;

	/** 结果封装BEAN */
	private Tc22to5WinUnit tc22to5WinUnit;

	/**
	 * 构造函数中调用相关方法进行奖金计算
	 * 
	 * @param content
	 * @param result
	 * @param multiple
	 */
	public PrizeWork(String singleContent, String result, int multiple) {
		this.singleContent = singleContent;
		this.result = result;
		this.multiple = multiple;
		tc22to5WinUnit = new Tc22to5WinUnit();
		initSingleData();
	}

	/**
	 * 构造函数中调用相关方法进行奖金计算
	 * 
	 * @param content
	 * @param result
	 * @param multiple
	 */
	public PrizeWork(Collection<Tc22to5CompoundContent> compoundContent, String result, int multiple) {
		this.compoundContent = compoundContent;
		this.result = result;
		this.multiple = multiple;
		tc22to5WinUnit = new Tc22to5WinUnit();
		initCompoundData();
	}

	/**
	 * 返回奖金计算结果
	 * 
	 * @return
	 */
	public Tc22to5WinUnit getTc22to5WinUnit() {
		return tc22to5WinUnit;
	}

	private void initCompoundData() {
		String[] bets = new String[0];
		String[] gallBalls = new String[0];
		int ballHit = 0;
		int gallHits = 0;
		String[] results = this.result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
		for (Tc22to5CompoundContent c : compoundContent) {
			bets = c.getBetList().toArray(new String[0]);
			gallBalls = c.getDanList().toArray(new String[0]);
			gallHits = checkHit(gallBalls, results);
			ballHit = checkHit(bets, results);
			checkWin(gallBalls.length, gallHits, bets.length, ballHit);
		}
	}

	private void initSingleData() {
		String[] contents = this.singleContent.split("\r\n");
		String[] gallBalls = new String[0];
		int betHit = 0;
		int gallHits = 0;
		String[] results = this.result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
		for (String c : contents) {
			String[] codes = c.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
			betHit = checkHit(codes, results);
			checkWin(gallBalls.length, gallHits, codes.length, betHit);
		}
	}

	private void checkWin(int galls, int gallHits, int bets, int betHits) {
		this.tc22to5WinUnit.addFirstWinUnits(calFirst(galls, gallHits, bets, betHits) * multiple);
		this.tc22to5WinUnit.addSecondWinUnits(calSecond(galls, gallHits, bets, betHits) * multiple);
		this.tc22to5WinUnit.addThirdWinUnits(calThird(galls, gallHits, bets, betHits) * multiple);
	}

	/**
	 * 计算命中个数
	 * 
	 * @param codes
	 * @param results
	 * @return
	 */
	private int checkHit(String[] codes, String[] results) {
		int c = 0;
		NumberFormat numFmt = new DecimalFormat("00");
		for (String r : results) {
			r=numFmt.format(Integer.parseInt(r));
			if (Arrays.binarySearch(codes, r) >= 0) {
				c++;
			}
		}
		return c;
	}

	/**
	 * 计算一等奖注数,一等奖须命中：5
	 * 
	 * @param galls 胆码
	 * @param betGallHits 胆码命中数
	 * @param bets 拖码
	 * @param betHits 拖码命中数
	 * @return 一等奖注数
	 */
	protected int calFirst(int galls, int gallHits, int bets, int betHits) {
		return calHit(5, 5, galls, gallHits, bets, betHits);
	}

	protected int calSecond(int galls, int gallHits, int bets, int betHits) {
		return calHit(5, 4, galls, gallHits, bets, betHits);
	}

	protected int calThird(int galls, int gallHits, int bets, int betHits) {
		return calHit(5, 3, galls, gallHits, bets, betHits);
	}

	
	/**
	 * 计算命中指定数的组合数
	 * 
	 * @param maxHits 最大命中数
	 * @param hits 须命中数
	 * @param gallBalls 胆码
	 * @param gallBallHits 胆码命中数
	 * @param balls 拖码
	 * @param ballHits 拖码命中数
	 * @return 符合要求的组合数
	 */
	protected int calHit(int maxHits, int hits, int gallBalls, int gallBallHits, int balls, int ballHits) {
		if (hits - gallBallHits >= 0 && maxHits - gallBalls >= hits - gallBallHits) {
			return MathUtils.comp(hits - gallBallHits, ballHits)
					* MathUtils.comp(maxHits - gallBalls - (hits - gallBallHits), balls - ballHits);
		} else {
			return 0;
		}
	}
}
