package com.cai310.lottery.common;

import com.cai310.lottery.Constant;


/**
 * 信息子类别 
 *
 */
public enum MobileInfoType {
	
	ZJJH("专家荐号"),
	HCSC(Constant.WEB_NAME+"说彩"),
	GFTZ("官方通知"),
	XWZX("新闻咨询");
	
	private String typeName;

	private MobileInfoType(String typeName) {
		this.typeName = typeName;
	}
	
	public String getTypeName(){
		return this.typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
