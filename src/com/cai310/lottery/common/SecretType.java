package com.cai310.lottery.common;

import com.cai310.lottery.Constant;

/**
 * 方案保密类型.<br/>
 * 注：添加类型只能在后面添加，不能插入中间
 * 
 */
public enum SecretType {
	/** 完全公共 */
	FULL_PUBLIC("完全公开"),

	/** 开奖后公告 */
	DRAWN_PUBLIC("开奖后公开"),

	/** 完全保密 */
	FULL_SECRET("完全保密");

	/** 保密名称 */
	private final String secretName;

	/**
	 * @param secretName {@link #secretName}
	 */
	private SecretType(String secretName) {
		this.secretName = secretName;
	}

	/**
	 * @return {@link #secretName}
	 */
	public String getSecretName() {
		return secretName;
	}

	public static final String SQL_TYPE = Constant.ENUM_DEFAULT_SQL_TYPE;
}
