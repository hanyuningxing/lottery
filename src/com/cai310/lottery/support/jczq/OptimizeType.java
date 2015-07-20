package com.cai310.lottery.support.jczq;


/**
 * 竞彩足球优化方式
 * 
 */
public enum OptimizeType {
	
	PINGJUN("平均优化"),

	BAOSHOU("保守优化"),
	
	BOLENG("搏冷优化"),
	
	RANG_LING_OR_ONE("亚盘让0或-1"),
	
	RANG_ER_WU("亚盘让-0.25"),
	
	RANG_QI_WU("亚盘让-0.75");

	private final String text;

	private OptimizeType(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
