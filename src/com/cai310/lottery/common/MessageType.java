package com.cai310.lottery.common;
/**
 * 短信类别
 *
 */
public enum MessageType {
	CHECKPHONE("用户确认手机",false),
	YANZHENGMA("验证码",true);
	private final String typeName;
	private final Boolean canSendAgain;
	
    MessageType(String typeName, Boolean canSendAgain) {
		this.typeName = typeName;
		this.canSendAgain = canSendAgain;
	}
	
	public Boolean getCanSendAgain() {
		return canSendAgain;
	}

	public String getTypeName(){
		return typeName;
	}
}
