package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 优惠活动状态.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum SecurityLevel {
	DANAGENROUS("危险"),
	COMMON("一般"),
	GOOD("好"),
	VERYGOOD("很好"),
	VERYVERYGOOD("非常好"),	
	PERFECT("完美");

	private final String stateName;

	/**
	 * @param stateName {@link #stateName}
	 */
	private SecurityLevel(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return {@link #stateName}
	 */
	public String getStateName() {
		return stateName;
	}
	public Integer getOrdinalValue() {
		return this.ordinal();
	}
	/**
	 * @param ordinal 
	 * @return SalesMode
	 */
	public static SecurityLevel valueOfOrdinal(Integer ordinal) {
		if (null!=ordinal) {
			for (SecurityLevel l : SecurityLevel.values()) {
				if (ordinal.equals(l.ordinal()))return l;
			}
		}
		return null;
	}
	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;

}
