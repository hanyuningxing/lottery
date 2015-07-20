package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 *
 * Description: 正常战绩、灰色战绩<br>

 * Copyright: Copyright (c) 2011 <br>
 * Company: 肇庆优盛科技
 * 
 * @author zhuhui 2011-9-6 编写
 * @version 1.0
 */
public enum WinRecordState {
	/** 进行中 */
	SUCCESS("成功"),

	/** 取消 */
	CANCEL("撤销");

	private final String stateName;

	/**
	 * @param stateName {@link #stateName}
	 */
	private WinRecordState(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return {@link #stateName}
	 */
	public String getStateName() {
		return stateName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

}
