package com.cai310.lottery.prizeutils.sevenstar;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.cai310.lottery.Constant;
import com.cai310.lottery.entity.lottery.sevenstar.SevenstarWinUnit;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.sevenstar.SevenstarCompoundContent;
import com.cai310.utils.NumUtils;
import com.google.common.collect.Lists;

public class PrizeWork {

	/** 单式方案内容 */
	private String singleContent;

	private String result;

	private int multiple;

	/** 复式方案内容 */
	private Collection<SevenstarCompoundContent> compoundContent;

	/** 结果封装BEAN */
	private SevenstarWinUnit sevenstarWinUnit;

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
		sevenstarWinUnit = new SevenstarWinUnit();
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
	public PrizeWork(Collection<SevenstarCompoundContent> compoundContent, String result, int multiple)
			throws DataException {
		this.compoundContent = compoundContent;
		this.result = result;
		this.multiple = multiple;
		sevenstarWinUnit = new SevenstarWinUnit();
		calcPassCompound();
	}

	/**
	 * 返回奖金计算结果
	 * 
	 * @return
	 */
	public SevenstarWinUnit getSevenstarWinUnit() {
		return sevenstarWinUnit;
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
		// 用户方案投注号码

		String[] arr = singleContent.split("(\r\n|\n)+");
		Integer[] bets;
		Boolean[] hit;
		Integer hitCount;
		List<Integer> hitCountList;
		Iterator<Integer> hitContIt;
		for (String lineBet : arr) {
			bets = NumUtils.toIntegerArr(lineBet.split(Constant.SINGLE_SEPARATOR_FOR_NUMBER));
			hit = new Boolean[len];
			hit[0] = bets[0] == resultNums[0];
			hit[1] = bets[1] == resultNums[1];
			hit[2] = bets[2] == resultNums[2];
			hit[3] = bets[3] == resultNums[3];
			hit[4] = bets[4] == resultNums[4];
			hit[5] = bets[5] == resultNums[5];
			hit[6] = bets[6] == resultNums[6];
			// 特等奖：1234567
			// 一等奖：123456X、X234567
			// 二等奖：12345XX、X23456X、XX34567
			// 三等奖：1234XXX、X2345XX、XX3456X、XXX4567
			// 四等奖：123XXXX、X234XXX、XX345XX、XXX456X、XXXX567
			// 五等奖：12XXXXX、X23XXXX、XX34XXX、XXX45XX、XXXX56X、XXXXX67
			hitCount = 0;
			hitCountList = new ArrayList<Integer>();
			for (int i = 0; i < hit.length; i++) {
				if (hit[i]) {
					// 该位命中
					hitCount++;
					if (i == hit.length - 1) {
						hitCountList.add(hitCount);
						hitCount = 0;
					}
				} else {
					// 该位没命中
					hitCountList.add(hitCount);
					hitCount = 0;
				}

			}
			Collections.sort(hitCountList,new Comparator<Integer>(){
	            public int compare(Integer arg0, Integer arg1) {
	                return arg1.compareTo(arg0);
	            }
			});
			hitContIt = hitCountList.iterator();
			if (hitContIt.hasNext()) {
				hitCount = hitContIt.next();
				if (hitCount >= 2) {
					// 大于2中奖
					if (hitCount == 7) {
						// 一等
						sevenstarWinUnit.addFirstWinUnits(multiple);
					} else if (hitCount == 6) {
						// 二等
						sevenstarWinUnit.addSecondWinUnits(multiple);
					} else if (hitCount == 5) {
						// 三等
						sevenstarWinUnit.addThirdWinUnits(multiple);
					} else if (hitCount == 4) {
						// 四等
						sevenstarWinUnit.addFourthWinUnits(multiple);
					} else if (hitCount == 3) {
						// 五等
						sevenstarWinUnit.addFifthWinUnits(multiple);
					} else if (hitCount == 2) {
						// 六等
						sevenstarWinUnit.addSixthWinUnits(multiple);
					}

				}
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
		// 用户方案投注号码

		SevenstarCompoundContent sevenstarCompoundContent;
		Iterator<SevenstarCompoundContent> compoundContentIt = compoundContent.iterator();
		List<Integer> bets;
		Boolean[] hit;
		Integer hitCount;
		List<Integer> hitCountList;
		Iterator<Integer> hitContIt;
		while (compoundContentIt.hasNext()) {
			hit = new Boolean[len];
			sevenstarCompoundContent = compoundContentIt.next();
			int unWon = 1;
			// 第一区间
			bets = NumUtils.toIntegerList(sevenstarCompoundContent.getArea1List());
			
			hit[0] = bets.contains(resultNums[0]);
			if(!hit[0]&&bets.size()>1){
				//不中并且选项大于1
				unWon = unWon*bets.size();
			}
			// 第二区间
			bets = NumUtils.toIntegerList(sevenstarCompoundContent.getArea2List());
			hit[1] = bets.contains(resultNums[1]);
			if(!hit[1]&&bets.size()>1){
				//不中并且选项大于1
				unWon = unWon*bets.size();
			}
			// 第三区间
			bets = NumUtils.toIntegerList(sevenstarCompoundContent.getArea3List());
			hit[2] = bets.contains(resultNums[2]);
			if(!hit[2]&&bets.size()>1){
				//不中并且选项大于1
				unWon = unWon*bets.size();
			}
			// 第四区间
			bets = NumUtils.toIntegerList(sevenstarCompoundContent.getArea4List());
			hit[3] = bets.contains(resultNums[3]);
			if(!hit[3]&&bets.size()>1){
				//不中并且选项大于1
				unWon = unWon*bets.size();
			}
			// 第五区间
			bets = NumUtils.toIntegerList(sevenstarCompoundContent.getArea5List());
			hit[4] = bets.contains(resultNums[4]);
			if(!hit[4]&&bets.size()>1){
				//不中并且选项大于1
				unWon = unWon*bets.size();
			}
			// 第六区间
			bets = NumUtils.toIntegerList(sevenstarCompoundContent.getArea6List());
			hit[5] = bets.contains(resultNums[5]);
			if(!hit[5]&&bets.size()>1){
				//不中并且选项大于1
				unWon = unWon*bets.size();
			}
			// 第七区间
			bets = NumUtils.toIntegerList(sevenstarCompoundContent.getArea7List());
			hit[6] = bets.contains(resultNums[6]);
			if(!hit[6]&&bets.size()>1){
				//不中并且选项大于1
				unWon = unWon*bets.size();
			}
			// 特等奖：1234567
			// 一等奖：123456X、X234567
			// 二等奖：12345XX、X23456X、XX34567
			// 三等奖：1234XXX、X2345XX、XX3456X、XXX4567
			// 四等奖：123XXXX、X234XXX、XX345XX、XXX456X、XXXX567
			// 五等奖：12XXXXX、X23XXXX、XX34XXX、XXX45XX、XXXX56X、XXXXX67
			hitCount = 0;
			hitCountList = new ArrayList<Integer>();
			for (int i = 0; i < hit.length; i++) {
				if (hit[i]) {
					// 该位命中
					hitCount++;
					if (i == hit.length - 1) {
						hitCountList.add(hitCount);
						hitCount = 0;
					}
				} else {
					// 该位没命中
					hitCountList.add(hitCount);
					hitCount = 0;
				}

			}
			hitContIt = hitCountList.iterator();
			while (hitContIt.hasNext()) {
				hitCount = hitContIt.next();
				if (hitCount >= 2) {
					// 大于2中奖
					if (hitCount == 7) {
						// 一等
						sevenstarWinUnit.addFirstWinUnits(multiple*unWon);
					} else if (hitCount == 6) {
						// 二等
						sevenstarWinUnit.addSecondWinUnits(multiple*unWon);
					} else if (hitCount == 5) {
						// 三等
						sevenstarWinUnit.addThirdWinUnits(multiple*unWon);
					} else if (hitCount == 4) {
						// 四等
						sevenstarWinUnit.addFourthWinUnits(multiple*unWon);
					} else if (hitCount == 3) {
						// 五等
						sevenstarWinUnit.addFifthWinUnits(multiple*unWon);
					} else if (hitCount == 2) {
						// 六等
						sevenstarWinUnit.addSixthWinUnits(multiple*unWon);
					}

				}
			}
		}

	}
	public static void main(String[] args) {
		Integer len = 7;
		// 开奖结果号码
				String[] results = "1,2,3,4,5,6,7".split(Constant.RESULT_SEPARATOR_FOR_NUMBER);

				Integer[] resultNums = NumUtils.toIntegerArr(results);
				// 用户方案投注号码
				/** 复式方案内容 */
				List<SevenstarCompoundContent> compoundContent = Lists.newArrayList();
				SevenstarCompoundContent sevenstarCompoundContent =new SevenstarCompoundContent();
				sevenstarCompoundContent.setUnits(3);
				sevenstarCompoundContent.setArea1List(Lists.newArrayList("1"));
				sevenstarCompoundContent.setArea2List(Lists.newArrayList("2","4"));
				sevenstarCompoundContent.setArea3List(Lists.newArrayList("1"));
				sevenstarCompoundContent.setArea4List(Lists.newArrayList("2"));
				sevenstarCompoundContent.setArea5List(Lists.newArrayList("1"));
				sevenstarCompoundContent.setArea6List(Lists.newArrayList("2","3"));
				sevenstarCompoundContent.setArea7List(Lists.newArrayList("1","3","2"));
				compoundContent.add(sevenstarCompoundContent);
				Iterator<SevenstarCompoundContent> compoundContentIt = compoundContent.iterator();
				List<Integer> bets;
				Boolean[] hit;
				Integer hitCount;
				List<Integer> hitCountList;
				Iterator<Integer> hitContIt;
				while (compoundContentIt.hasNext()) {
					hit = new Boolean[len];
					sevenstarCompoundContent = compoundContentIt.next();
					int unWon = 1;
					// 第一区间
					bets = NumUtils.toIntegerList(sevenstarCompoundContent.getArea1List());
					
					hit[0] = bets.contains(resultNums[0]);
					if(!hit[0]&&bets.size()>1){
						//不中并且选项大于1
						unWon = unWon*bets.size();
					}
					// 第二区间
					bets = NumUtils.toIntegerList(sevenstarCompoundContent.getArea2List());
					hit[1] = bets.contains(resultNums[1]);
					if(!hit[1]&&bets.size()>1){
						//不中并且选项大于1
						unWon = unWon*bets.size();
					}
					// 第三区间
					bets = NumUtils.toIntegerList(sevenstarCompoundContent.getArea3List());
					hit[2] = bets.contains(resultNums[2]);
					if(!hit[2]&&bets.size()>1){
						//不中并且选项大于1
						unWon = unWon*bets.size();
					}
					// 第四区间
					bets = NumUtils.toIntegerList(sevenstarCompoundContent.getArea4List());
					hit[3] = bets.contains(resultNums[3]);
					if(!hit[3]&&bets.size()>1){
						//不中并且选项大于1
						unWon = unWon*bets.size();
					}
					// 第五区间
					bets = NumUtils.toIntegerList(sevenstarCompoundContent.getArea5List());
					hit[4] = bets.contains(resultNums[4]);
					if(!hit[4]&&bets.size()>1){
						//不中并且选项大于1
						unWon = unWon*bets.size();
					}
					// 第六区间
					bets = NumUtils.toIntegerList(sevenstarCompoundContent.getArea6List());
					hit[5] = bets.contains(resultNums[5]);
					if(!hit[5]&&bets.size()>1){
						//不中并且选项大于1
						unWon = unWon*bets.size();
					}
					// 第七区间
					bets = NumUtils.toIntegerList(sevenstarCompoundContent.getArea7List());
					hit[6] = bets.contains(resultNums[6]);
					if(!hit[6]&&bets.size()>1){
						//不中并且选项大于1
						unWon = unWon*bets.size();
					}
					// 特等奖：1234567
					// 一等奖：123456X、X234567
					// 二等奖：12345XX、X23456X、XX34567
					// 三等奖：1234XXX、X2345XX、XX3456X、XXX4567
					// 四等奖：123XXXX、X234XXX、XX345XX、XXX456X、XXXX567
					// 五等奖：12XXXXX、X23XXXX、XX34XXX、XXX45XX、XXXX56X、XXXXX67
					hitCount = 0;
					hitCountList = new ArrayList<Integer>();
					for (int i = 0; i < hit.length; i++) {
						if (hit[i]) {
							// 该位命中
							hitCount++;
							if (i == hit.length - 1) {
								hitCountList.add(hitCount);
								hitCount = 0;
							}
						} else {
							// 该位没命中
							hitCountList.add(hitCount);
							hitCount = 0;
						}

					}
					hitContIt = hitCountList.iterator();
					while (hitContIt.hasNext()) {
						hitCount = hitContIt.next();
						if (hitCount >= 2) {
							// 大于2中奖
							if (hitCount == 7) {
								// 一等
								System.out.println("1-"+3*unWon);
							} else if (hitCount == 6) {
								// 二等
								System.out.println("2-"+3*unWon);
							} else if (hitCount == 5) {
								// 三等
								System.out.println("3"+3*unWon);
							} else if (hitCount == 4) {
								// 四等
								System.out.println("4"+3*unWon);
							} else if (hitCount == 3) {
								// 五等
								System.out.println("5"+3*unWon);
							} else if (hitCount == 2) {
								// 六等
								System.out.println("6-"+3*unWon);
							}

						}
					}
				}

	}
}
