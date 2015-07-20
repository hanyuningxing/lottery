package com.cai310.freemarker;

import javax.servlet.ServletContext;

import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.cai310.freemarker.directive.DirectiveUtils;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class ExtendedFreemarkerManager extends FreemarkerManager {

	@Override
	protected Configuration createConfiguration(ServletContext servletContext) throws TemplateException {
		Configuration configuration = super.createConfiguration(servletContext);
		configuration.setSharedVariable("datetime", new DatetimeMethod()); // 加入时间日期处理
		configuration.setSharedVariable("statics", BeansWrapper.getDefaultInstance().getStaticModels()); // 访问静态变量或静态方法
		DirectiveUtils.exposeMacros(configuration);// 实现模板继承,新增加四个指令：
													// @extends,@block,@override,@super
		return configuration;
	}
}