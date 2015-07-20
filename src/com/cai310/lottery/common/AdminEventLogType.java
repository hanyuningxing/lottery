package com.cai310.lottery.common;

/**
 * 后台操作记录类型
 * 
 */
public enum AdminEventLogType {
	FUND("额度"), RESOURCE("后台资源"), ROLE("后台角色"), ADMINUSER("后台用户"), DRAWING("提款"), USER("用户");
	/** 类型名称 */
	private final String typeName;

	/**
	 * @param typeName {@link #typeName}
	 */
	private AdminEventLogType(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return {@link #typeName}
	 */
	public String getTypeName() {
		return typeName;
	}
}
