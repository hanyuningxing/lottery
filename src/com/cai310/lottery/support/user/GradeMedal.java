package com.cai310.lottery.support.user;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户战绩奖牌对象
 * @author jack
 *
 */
public class GradeMedal implements Serializable {

	private static final long serialVersionUID = 2109049438762237528L;
	
	private static final int UPGRADE = 5;//晋级数
	
	private static final float GOLDEN1=50*10000;//一级金牌>=50万
	private static final float GOLDEN2=5*10000;//二级金牌>=5万
	private static final float GOLDEN3=2000;//三级金牌>=2000
	private static final int GOLDEN3_PROFIT=500;//三级金牌盈利
	private static final int GOLDEN3_PROFIT_RATE=5;//三级金牌回报倍数
	
	private static final float SILVERY=4000;//银牌>=2000
	private static final int SILVERY_PROFIT=1000;//银牌盈利
	private static final int SILVERY_PROFIT_RATE=10;//银牌回报倍数
	
	//金星数
	private int goldenNums;
	//银星数
	private int silveryNums;
	
	/**
	 * 统计金牌
	 * @param schemeCost
	 * @param prize
	 */
	public void statGolden(Integer schemeCost,BigDecimal prize){
		double prize2d = Double.valueOf(prize.toString());
		double profit = prize2d - schemeCost;
		if(profit>=GOLDEN1){
			goldenNums+=UPGRADE*UPGRADE;
		}else if(profit>=GOLDEN2){
			goldenNums+=UPGRADE;
		}else if(profit>=GOLDEN3 || (profit>=GOLDEN3_PROFIT && (profit/schemeCost)>=GOLDEN3_PROFIT_RATE)){
			goldenNums+=1;
		}
	}
	
	/**
	 * 统计银牌
	 * @param schemeCost
	 * @param prize
	 */
	public void statSilvery(Integer schemeCost,BigDecimal prize){
		double prize2d = Double.valueOf(prize.toString());
		double profit = prize2d - schemeCost;
		if(profit>=SILVERY || (profit>=SILVERY_PROFIT && (profit/schemeCost)>=SILVERY_PROFIT_RATE)){
			silveryNums+=1;
		}
	}
	
	//一级金牌数
	public int golden1(){
		return this.goldenNums/(UPGRADE*UPGRADE);
	}
	
	//二级金牌数
	public int golden2(){
		return (this.goldenNums%(UPGRADE*UPGRADE))/UPGRADE;
	}
	
	//三级金牌数
	public int golden3(){
		return this.goldenNums%UPGRADE;
	}
	
	//一级银牌数
	public int silvery1(){
		return this.silveryNums/(UPGRADE*UPGRADE);
	}
	
	//二级银牌数
	public int silvery2(){
		return (this.silveryNums%(UPGRADE*UPGRADE))/UPGRADE;
	}
	
	//三级银牌数
	public int silvery3(){
		return this.silveryNums%UPGRADE;
	}
	
	public int getGoldenNums() {
		return goldenNums;
	}
	public void setGoldenNums(int goldenNums) {
		this.goldenNums = goldenNums;
	}
	public int getSilveryNums() {
		return silveryNums;
	}
	public void setSilveryNums(int silveryNums) {
		this.silveryNums = silveryNums;
	}
	
}
