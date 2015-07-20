package com.cai310.lottery.common;
/**
 * 信息类别
 *
 */
public enum InfoType {
	FORECAST("预测"), 
	SKILLS("技巧"), 
	NOTICE("公告"),
	INFO("资讯"),
	TOPICS("专题"),
	PHONE("手机");
	private String typeName;

	private InfoType(String typeName) {
		this.typeName = typeName;
	}
	
	public String getTypeName(){
		return this.typeName;
	}
}
