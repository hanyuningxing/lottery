package com.cai310.lottery.prizeutils.seven;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.SevenConstant;
import com.cai310.lottery.entity.lottery.seven.SevenWinUnit;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.seven.SevenCompoundContent;
import com.cai310.utils.MathUtils;
import com.cai310.utils.NumUtils;

public class PrizeWork {

	/** 单式方案内容 */
	private String singleContent;

	private String result;

	private int multiple;

	/** 复式方案内容 */
	private Collection<SevenCompoundContent> compoundContent;

	/** 结果封装BEAN */
	private SevenWinUnit sevenWinUnit;

	private Integer len = 7;

	/**
	 * 构造函数中调用相关方法进行奖金计算
	 * 
	 * @param content
	 * @param result
	 * @param multiple
	 * @throws DataException
	 */
	public PrizeWork(String singleContent, String result, int multiple) throws DataException {
		this.singleContent = singleContent;
		this.result = result;
		this.multiple = multiple;
		sevenWinUnit = new SevenWinUnit();
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
	public PrizeWork(Collection<SevenCompoundContent> compoundContent, String result, int multiple)
			throws DataException {
		this.compoundContent = compoundContent;
		this.result = result;
		this.multiple = multiple;
		sevenWinUnit = new SevenWinUnit();
		calcPassCompound();
	}

	/**
	 * 返回奖金计算结果
	 * 
	 * @return
	 */
	public SevenWinUnit getSevenWinUnit() {
		return sevenWinUnit;
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

		Integer[] basicResultNums = new Integer[7];
		basicResultNums[0] = Integer.valueOf(results[0]);
		basicResultNums[1] = Integer.valueOf(results[1]);
		basicResultNums[2] = Integer.valueOf(results[2]);
		basicResultNums[3] = Integer.valueOf(results[3]);
		basicResultNums[4] = Integer.valueOf(results[4]);
		basicResultNums[5] = Integer.valueOf(results[5]);
		basicResultNums[6] = Integer.valueOf(results[6]);
		Integer[] specialResultNums = new Integer[1];
		specialResultNums[0] = Integer.valueOf(results[7]);
		
		// 用户方案投注号码
		String[] arr = singleContent.split("(\r\n|\n)+");
		List<Integer> balls;
		for (String lineBet : arr) {
			balls = NumUtils.toIntegerList(lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER));
			calcPassMethod(new ArrayList<Integer>(),balls,basicResultNums,specialResultNums);
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
		Integer[] basicResultNums = new Integer[7];
		basicResultNums[0] = Integer.valueOf(results[0]);
		basicResultNums[1] = Integer.valueOf(results[1]);
		basicResultNums[2] = Integer.valueOf(results[2]);
		basicResultNums[3] = Integer.valueOf(results[3]);
		basicResultNums[4] = Integer.valueOf(results[4]);
		basicResultNums[5] = Integer.valueOf(results[5]);
		basicResultNums[6] = Integer.valueOf(results[6]);
		Integer[] specialResultNums = new Integer[1];
		specialResultNums[0] = Integer.valueOf(results[7]);
		// 用户方案投注号码

		SevenCompoundContent sevenCompoundContent;
		Iterator<SevenCompoundContent> compoundContentIt = compoundContent.iterator();
		while (compoundContentIt.hasNext()) {
			sevenCompoundContent = compoundContentIt.next();
			List<Integer> dans = NumUtils.toIntegerList(sevenCompoundContent.getDanList());
			List<Integer> balls = NumUtils.toIntegerList(sevenCompoundContent.getBallList());
			calcPassMethod(dans,balls,basicResultNums,specialResultNums);
		}

	}
	public void calcPassMethod(List<Integer> dans,List<Integer> balls,Integer[] basicResultNums,Integer[] specialResultNums){
		Integer danBasicHitted = 0;
		Integer danSpecialHitted = 0;
		Integer ballBasicHitted = 0;
		Integer ballSpecialHitted = 0;
		for (int i = 0; i < basicResultNums.length; i++) {
			Integer result = basicResultNums[i];
			if (dans.contains(result))danBasicHitted++;
			if (balls.contains(result))ballBasicHitted++;
		}
		for (int i = 0; i < specialResultNums.length; i++) {
			Integer result = specialResultNums[i];
			if (dans.contains(result)){
				danSpecialHitted++;
			}
			if (balls.contains(result)){
				ballSpecialHitted++;
			}
		}
		int first = 0;
		int second  = 0;
		int third  = 0;
		int fourth  = 0;
		int fifth  = 0;
		int sixth  = 0;
		int seven  = 0;
		int hitted7BasicNum =calHit(SevenConstant.MAX_HITS, 7, dans.size(), danBasicHitted, balls.size(), ballBasicHitted);///一等奖7个普通号码
		int hitted6BasicNum =calHit(SevenConstant.MAX_HITS, 6, dans.size(), danBasicHitted, balls.size(), ballBasicHitted);///中6个普通号码
		int hitted5BasicNum =calHit(SevenConstant.MAX_HITS, 5, dans.size(), danBasicHitted, balls.size(), ballBasicHitted);///中5个普通号码
		int hitted4BasicNum =calHit(SevenConstant.MAX_HITS, 4, dans.size(), danBasicHitted, balls.size(), ballBasicHitted);///中4个普通号码
		
		if(danSpecialHitted!=0||ballSpecialHitted!=0){
				 //有中特号的情况
			     if(danSpecialHitted!=0){
			    	 second=calHit(6, 6, dans.size()-1, danBasicHitted, balls.size(), ballBasicHitted);///二等奖6个普通号码+1个特别号码
			    	 fourth=calHit(6, 5, dans.size()-1, danBasicHitted, balls.size(), ballBasicHitted);///四等奖5个普通号码+1个特别号码
			    	 sixth =calHit(6, 4, dans.size()-1, danBasicHitted, balls.size(), ballBasicHitted);///六等奖4个普通号码+1个特别号码
			     }else if(ballSpecialHitted!=0){
			    	 second=calHit(6, 6, dans.size(), danBasicHitted, balls.size()-1, ballBasicHitted);///二等奖6个普通号码+1个特别号码
			    	 fourth=calHit(6, 5, dans.size(), danBasicHitted, balls.size()-1, ballBasicHitted);///四等奖5个普通号码+1个特别号码
			    	 sixth =calHit(6, 4, dans.size(), danBasicHitted, balls.size()-1, ballBasicHitted);///六等奖4个普通号码+1个特别号码
			     }
		}
		first = hitted7BasicNum;
		if(hitted6BasicNum-second>0){
			third=hitted6BasicNum-second;
		}
		if(hitted5BasicNum-fourth>0){
			fifth=hitted5BasicNum-fourth;
		}
		if(hitted4BasicNum-sixth>0){
			seven=hitted4BasicNum-sixth;
		}
		if (first != 0) 
			// 一等
			sevenWinUnit.addFirstWinUnits(first*multiple);
			
		if (second != 0) 
			// 二等
			sevenWinUnit.addSecondWinUnits(second*multiple);
		if (third != 0) 
			// 三等
			sevenWinUnit.addThirdWinUnits(third*multiple);
		if (fourth != 0) 
			// 四等
			sevenWinUnit.addFourthWinUnits(fourth*multiple);
		if (fifth != 0) 
			// 五等
			sevenWinUnit.addFifthWinUnits(fifth*multiple);
		if (sixth != 0) 
			// 六等
			sevenWinUnit.addSixthWinUnits(sixth*multiple);
		if (seven != 0) 
			// 七等
			sevenWinUnit.addSevenWinUnits(seven*multiple);
	}
//	public static void main(String[] args) throws DataException {
//		SevenCompoundContent sevenCompoundContent = new SevenCompoundContent();
//		List<String> ball = new ArrayList<String>();
//		for (int i = 1; i <= 10; i++) {
//			ball.add(""+i);
//		}
//		List<String> dan = new ArrayList<String>();
////		for (int i = 1; i <= 10; i++) {
////			dan.add(""+i);
////		}
//		sevenCompoundContent.setBallList(ball);
//		sevenCompoundContent.setDanList(dan);
//		List<SevenCompoundContent> bet =  new ArrayList<SevenCompoundContent>();
//		bet.add(sevenCompoundContent);
//		PrizeWork PrizeWork = new PrizeWork(bet,"1,2,3,4,5,6,7,8",1);
//		int i = 0;
//	}
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
	 
	public static int calHit(int maxHits, int hits, int gallBalls, int gallBallHits, int balls, int ballHits) {
		if (hits - gallBallHits >= 0 && maxHits - gallBalls >= hits - gallBallHits) {
			return MathUtils.comp(hits - gallBallHits, ballHits)
					* MathUtils.comp(maxHits - gallBalls - (hits - gallBallHits), balls - ballHits);
		} else {
			return 0;
		}
	}*/
	/**
	 * 计算命中指定数的组合数
	 * 
	 * @param maxNums 最大命中数
	 * @param needHits 须命中数
	 * @param danSelected 胆码
	 * @param danHits 胆码命中数
	 * @param selected 拖码
	 * @param hits 拖码命中数
	 * @return 符合要求的组合数
	 */
	public static Integer calHit(int maxNums, int needHits, int danSelected, int danHits, int selected, int hits) {
		if (danHits > needHits) {
			return 0;
		}

		int needUnHits = maxNums - needHits;  // 需要不中的数目
		int danUnHits = danSelected - danHits; // 胆码不中的数目
		if(danUnHits > needUnHits)
			return 0;

		int tuomaNeedNums = maxNums - danSelected;// 需要的拖码数
		int unHits = selected - hits;// 不中的拖码数
		int tuomaNeedHits = needHits - danHits;// 拖码需要命中的数目
		int tuomaNeedUnHits = tuomaNeedNums - tuomaNeedHits;// 拖码需要不中的数目
		return MathUtils.comp(tuomaNeedHits, hits) * MathUtils.comp(tuomaNeedUnHits, unHits);
		
	}  

}
