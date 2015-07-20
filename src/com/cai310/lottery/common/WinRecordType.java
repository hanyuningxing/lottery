package com.cai310.lottery.common;

/**
 *
 * Description: 战绩级别<br>

 * Copyright: Copyright (c) 2011 <br>
 * Company: 肇庆优盛科技
 * 
 * @author zhuhui 2011-8-23 编写
 * @version 1.0
 */
public enum  WinRecordType {
//	1个司令＝3个军长 
//	1个军长＝3个师长
//	1个师长＝3个旅长
//	1个旅长＝3个团长
//	1个团长＝3个营长
//	1个营长＝3个连长
//	1个连长＝3个排长
	SILING("司令",2187),
	JUNZHANG("军长",729),
	SHIZHANG("师长",243),
	LVZHANG("旅长",81),
	TUANZHANG("团长",27),
	YINGZHANG("营长",9),
	LIANZHANG("连长",3),
	PAIZHANG("排长",1);	
	/** 类型名称 */
	private final String typeName;
	
	/** 类目类型名称 */
	private final int winRecord;

	public String getTypeName() {
		return typeName;
	}

	public int getWinRecord() {
		return winRecord;
	}

	private WinRecordType(String typeName,  int winRecord) {
		this.typeName = typeName;
		this.winRecord = winRecord;
	}

}
