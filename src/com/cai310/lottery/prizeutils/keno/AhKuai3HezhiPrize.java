package com.cai310.lottery.prizeutils.keno;

public enum AhKuai3HezhiPrize {
	THREE(3,240),
	FOUR(4,80),
	FIVE(5,40),
	SIX(6,25),
	SEVEN(7,16),
	EIGHT(8,12),
	NINE(9,10),
	TEN(10,9),
	ELEVEN(11,9),
	TWELVE(12,10),
	THIRTEEN(13,12),
	FOURTHEN(14,16),
	FIFTEEN(15,25),
	SIXTEEN(16,40),
	SEVENTEEN(17,80),
	EIGHTTEEN(18,240);
	
	private Integer sum;
	
	private Integer prize;
	private AhKuai3HezhiPrize(Integer sum,Integer prize) {
		this.sum = sum;
		this.prize = prize;
	}
	
	
	public static Integer getPrizeBySum(Integer sum){
		for (AhKuai3HezhiPrize hezhiPrize : AhKuai3HezhiPrize.values()) {
			if(sum.equals(hezhiPrize.getSum()))return hezhiPrize.getPrize();
		}
		return null;
	}


	public Integer getSum() {
		return sum;
	}


	public void setSum(Integer sum) {
		this.sum = sum;
	}


	public Integer getPrize() {
		return prize;
	}


	public void setPrize(Integer prize) {
		this.prize = prize;
	}
	
}

