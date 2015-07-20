package com.cai310.lottery.common;

public class LotteryUtil {
	private static final Lottery[] WEB_LOTTERY_ARR;
	private static final Lottery[] PHONE_LOTTERY_ARR;
	static {
		WEB_LOTTERY_ARR = new Lottery[19];
		WEB_LOTTERY_ARR[0] = Lottery.DCZC;
		WEB_LOTTERY_ARR[1] = Lottery.SFZC;
		WEB_LOTTERY_ARR[2] = Lottery.SCZC;
		WEB_LOTTERY_ARR[3] = Lottery.LCZC;
		WEB_LOTTERY_ARR[4] = Lottery.SSQ;
		WEB_LOTTERY_ARR[5] = Lottery.DLT;
		WEB_LOTTERY_ARR[6] = Lottery.PL;
		WEB_LOTTERY_ARR[7] = Lottery.WELFARE3D;
		WEB_LOTTERY_ARR[8] = Lottery.SEVENSTAR;
		WEB_LOTTERY_ARR[9] = Lottery.SDEL11TO5;
		WEB_LOTTERY_ARR[10] = Lottery.JCLQ;
		WEB_LOTTERY_ARR[11] = Lottery.SSC;
		WEB_LOTTERY_ARR[12] = Lottery.TC22TO5;
		WEB_LOTTERY_ARR[13] = Lottery.SEVEN;
		WEB_LOTTERY_ARR[14] = Lottery.JCZQ;
		WEB_LOTTERY_ARR[15]=Lottery.AHKUAI3;
		WEB_LOTTERY_ARR[16]=Lottery.EL11TO5;
		WEB_LOTTERY_ARR[17]=Lottery.GDEL11TO5;
		WEB_LOTTERY_ARR[18]=Lottery.XJEL11TO5;
		PHONE_LOTTERY_ARR = WEB_LOTTERY_ARR;
	}

	/**
	 * 前台列表显示
	 * 
	 * @return 彩票类型[]
	 */
	public static Lottery[] getWebLotteryList() {
		return WEB_LOTTERY_ARR;
	}
	/**
	 * 手机
	 * 
	 * @return 彩票类型[]
	 */
	public static Lottery[] getPhoneLotteryList() {
		return PHONE_LOTTERY_ARR;
	}

}
