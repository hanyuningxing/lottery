package com.cai310.lottery.common;
/**
 * 信息类别
 *
 */
public enum ExternalNetworkType {
	RENREN("人人网"), 
	QQ("QQ"), 
	SINA("新浪微博"),
	ALIPAY("支付宝");
	private String typeName;

	private ExternalNetworkType(String typeName) {
		this.typeName = typeName;
	}
	
	public String getTypeName(){
		return this.typeName;
	}
}
