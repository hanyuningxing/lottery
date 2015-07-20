package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 代理统计状态.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum AgentAnalyseState {
	/** 正常 */
	NONE("未更新"),
	/** 删除*/
	UPDATED("已更新");
	
	/** 状态名称 */
	private final String stateName;

	/**
	 * @param stateName {@link #stateName}
	 */
	private AgentAnalyseState(String stateName) {
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
