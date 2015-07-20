package com.cai310.lottery.support.jclq;
/**
 * 竞彩篮球单式上传类型枚举
 *
 */
public enum JclqUploadType {
	SELECT_MATCH("选择场次"), 
	CONTAIN_MATCH("包含场次"), 
	AHEAD("先发起后上传");
	
	private String typeName;

	private JclqUploadType(String typeName) {
		this.typeName = typeName;
	}
	
	public String getTypeName(){
		return this.typeName;
	}
}
