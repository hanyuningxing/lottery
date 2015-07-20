package com.cai310.lottery.builder;


/**
 * Description: 获奖信息---生成器配置参数<br>
 * Copyright: Copyright (c) 2011<br>
 * Company: 肇庆优盛科技
 * @author  zhuhui 2011-03-16 编写
 * @version 1.0
 */
public class DefaultInfoBuilderCreateConfig {

	/** 模版路径  Constant.ROOTPATH+templatePath **/
	private String templatePath;
	
	/** 模版名称 **/
	private String templateName;
	
	/** 目标路径  **/
	private String targetFilePath;
	
	/** 目标路径  **/
	private String targetFileName;
	
	public String getTargetFileName() {
		return targetFileName;
	}

	public void setTargetFileName(String targetFileName) {
		this.targetFileName = targetFileName;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTargetFilePath() {
		return targetFilePath;
	}

	public void setTargetFilePath(String targetFilePath) {
		this.targetFilePath = targetFilePath;
	}
}