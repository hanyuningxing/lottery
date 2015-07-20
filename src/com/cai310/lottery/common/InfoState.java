package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 文章状态.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum InfoState {
	/** 正常 */
	NORMAL("正常"),
	/** 删除*/
	CANCEL("删除"),
	/** 审核中 */
	CHECKING("审核中"),
	/**未发布*/
	HIDDEN("未发布"),
	/**已发布*/
	SHOW("已发布");
	
	/** 状态名称 */
	private final String stateName;

	/**
	 * @param stateName {@link #stateName}
	 */
	private InfoState(String stateName) {
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
