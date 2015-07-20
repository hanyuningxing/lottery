package com.cai310.lottery.prizeutils.ssq;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.ArrayUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.lottery.ssq.SsqWinUnit;
import com.cai310.lottery.support.ssq.SsqCompoundContent;
import com.cai310.utils.MathUtils;

public class PrizeWork {
	/** 单式方案内容 */
	private String singleContent;

	private String result;

	private int multiple;

	/** 复式方案内容 */
	private Collection<SsqCompoundContent> compoundContent;

	/** 结果封装BEAN */
	private SsqWinUnit ssqWinUnit;

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
		ssqWinUnit = new SsqWinUnit();
		initSingleData();
	}

	/**
	 * 构造函数中调用相关方法进行奖金计算
	 * 
	 * @param content
	 * @param result
	 * @param multiple
	 */
	public PrizeWork(Collection<SsqCompoundContent> compoundContent, String result, int multiple) {
		this.compoundContent = compoundContent;
		this.result = result;
		this.multiple = multiple;
		ssqWinUnit = new SsqWinUnit();
		initCompoundData();
	}

	/**
	 * 返回奖金计算结果
	 * 
	 * @return
	 */
	public SsqWinUnit getSsqWinUnit() {
		return ssqWinUnit;
	}

	private void initCompoundData() {
		String[] redBalls = new String[0];
		String[] gallRedBalls = new String[0];
		String[] blueBalls = new String[0];
		int redBallHit = 0;
		int redGallHits = 0;
		int blueBallHit = 0;
		String[] results = this.result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
		String[] redResults = (String[]) ArrayUtils.subarray(results, 0, 6);
		String[] blueResults = (String[]) ArrayUtils.subarray(results, 6, 7);
		for (SsqCompoundContent c : compoundContent) {
			blueBalls = c.getBlueList().toArray(new String[0]);
			redBalls = c.getRedList().toArray(new String[0]);
			gallRedBalls = c.getRedDanList().toArray(new String[0]);
			redGallHits = checkHit(gallRedBalls, redResults);
			redBallHit = checkHit(redBalls, redResults);
			blueBallHit = checkHit(blueBalls, blueResults);
			checkWin(gallRedBalls.length, redGallHits, redBalls.length, redBallHit, blueBalls.length, blueBallHit);
		}
	}

	private void initSingleData() {
		String[] contents = this.singleContent.split("\r\n");
		String[] redBalls = new String[0];
		String[] gallRedBalls = new String[0];
		String[] blueBalls = new String[0];
		int redBallHit = 0;
		int redGallHits = 0;
		int blueBallHit = 0;
		String[] results = this.result.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
		String[] redResults = (String[]) ArrayUtils.subarray(results, 0, 6);
		String[] blueResults = (String[]) ArrayUtils.subarray(results, 6, 7);
		for (String c : contents) {
			String[] codes = c.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER);
			redBalls = (String[]) ArrayUtils.subarray(codes, 0, 6);
			blueBalls = (String[]) ArrayUtils.subarray(codes, 6, 7);
			redGallHits = checkHit(gallRedBalls, redResults);
			redBallHit = checkHit(redBalls, redResults);
			blueBallHit = checkHit(blueBalls, blueResults);
			checkWin(gallRedBalls.length, redGallHits, redBalls.length, redBallHit, blueBalls.length, blueBallHit);
		}
	}

	private void checkWin(int redGalls, int redGallHits, int reds, int redHits, int blues, int blueHits) {
		this.ssqWinUnit.addFirstWinUnits(calFirst(redGalls, redGallHits, reds, redHits, blues, blueHits) * multiple);
		this.ssqWinUnit.addSecondWinUnits(calSecond(redGalls, redGallHits, reds, redHits, blues, blueHits) * multiple);
		this.ssqWinUnit.addThirdWinUnits(calThird(redGalls, redGallHits, reds, redHits, blues, blueHits) * multiple);
		this.ssqWinUnit.addFourthWinUnits(calFourth(redGalls, redGallHits, reds, redHits, blues, blueHits) * multiple);
		this.ssqWinUnit.addFifthWinUnits(calFiveth(redGalls, redGallHits, reds, redHits, blues, blueHits) * multiple);
		this.ssqWinUnit.addSixthWinUnits(calSixth(redGalls, redGallHits, reds, redHits, blues, blueHits) * multiple);
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
	 * 计算一等奖注数,一等奖须命中：6+1
	 * 
	 * @param redGalls 前区胆码
	 * @param redGallHits 前区胆码命中数
	 * @param reds 前区拖码
	 * @param redHits 前区拖码命中数
	 * @param blues 后区拖码
	 * @param blueHits 后区拖码命中数
	 * @return 一等奖注数
	 */
	protected int calFirst(int redGalls, int redGallHits, int reds, int redHits, int blues, int blueHits) {
		return calHit(6, 6, redGalls, redGallHits, reds, redHits) * calHit(1, 1, 0, 0, blues, blueHits);
	}

	protected int calSecond(int redGalls, int redGallHits, int reds, int redHits, int blues, int blueHits) {
		return calHit(6, 6, redGalls, redGallHits, reds, redHits) * calHit(1, 0, 0, 0, blues, blueHits);
	}

	protected int calThird(int redGalls, int redGallHits, int reds, int redHits, int blues, int blueHits) {
		return calHit(6, 5, redGalls, redGallHits, reds, redHits) * calHit(1, 1, 0, 0, blues, blueHits);
	}

	protected int calFourth(int redGalls, int redGallHits, int reds, int redHits, int blues, int blueHits) {
		return calHit(6, 5, redGalls, redGallHits, reds, redHits) * calHit(1, 0, 0, 0, blues, blueHits)
				+ calHit(6, 4, redGalls, redGallHits, reds, redHits) * calHit(1, 1, 0, 0, blues, blueHits);
	}

	protected int calFiveth(int redGalls, int redGallHits, int reds, int redHits, int blues, int blueHits) {
		return calHit(6, 4, redGalls, redGallHits, reds, redHits) * calHit(1, 0, 0, 0, blues, blueHits)
				+ calHit(6, 3, redGalls, redGallHits, reds, redHits) * calHit(1, 1, 0, 0, blues, blueHits);
	}

	protected int calSixth(int redGalls, int redGallHits, int reds, int redHits, int blues, int blueHits) {
		return calHit(6, 2, redGalls, redGallHits, reds, redHits) * calHit(1, 1, 0, 0, blues, blueHits)
				+ calHit(6, 1, redGalls, redGallHits, reds, redHits) * calHit(1, 1, 0, 0, blues, blueHits)
				+ calHit(6, 0, redGalls, redGallHits, reds, redHits) * calHit(1, 1, 0, 0, blues, blueHits);
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
