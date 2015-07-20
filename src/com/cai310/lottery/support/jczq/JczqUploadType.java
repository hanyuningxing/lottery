package com.cai310.lottery.support.jczq;
/**
 * 竞彩足球单式上传类型枚举
 *
 */
public enum JczqUploadType {
	SELECT_MATCH("选择场次"), 
	CONTAIN_MATCH("包含场次"), 
	AHEAD("先发起后上传");
	
	private String typeName;

	private JczqUploadType(String typeName) {
		this.typeName = typeName;
	}
	
	public String getTypeName(){
		return this.typeName;
	}
}
