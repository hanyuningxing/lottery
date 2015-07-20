package com.cai310.lottery.builder;

import java.util.Map;

import com.cai310.lottery.Constant;
import com.cai310.lottery.exception.DataException;
import com.cai310.utils.TemplateGenerator;

/**
 * Description: 建造者抽象类<br>
 * 抽取创建HTML/FTL基本方法 Copyright: Copyright (c) 2011<br>
 * Company: 肇庆优盛科技
 * @author zhuhui 2011-03-16 编写
 * @version 1.0
 */
public abstract class AbstractInfoBuilder {
	/**
	 * 
	 * 生成静态文件工具类
	 * 
	 * @param contents
	 *            Map<String,Object> 数据
	 * @param templatePath
	 *            String 模版路径：Constant.ROOTPATH+templatePath
	 * @param templateName
	 *            String 模版名称
	 * @param targetFilePath
	 *            String 目标生成文件路径：Constant.ROOTPATH+targetFilePath
	 * @param targetFileName
	 *            String 目标生成文件名称
	 * @author zhuhui 2011-03-17 motify
	 * @throws DataException
	 */
	public void createNewsFile(Map<String, Object> contents, String templatePath, String templateName,
			String targetFilePath, String targetFileName) throws DataException {
		try {
			TemplateGenerator tg = new TemplateGenerator(Constant.ROOTPATH + templatePath);
			tg.create(templateName, contents, targetFileName, Constant.ROOTPATH + targetFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("生成静态文件失败");
		}
		
	}
	
}
