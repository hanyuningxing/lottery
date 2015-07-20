package com.cai310.lottery.common;
/**
 * 推广类别
 *
 */
public enum PopuType {
	WEB("网站访问"), 
	ANDROID("下载安卓客户端");
	private String typeName;

	private PopuType(String typeName) {
		this.typeName = typeName;
	}
	
	public String getTypeName(){
		return this.typeName;
	}
}
