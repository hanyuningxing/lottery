package com.cai310.lottery.web.controller;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.PopuType;
import com.cai310.lottery.entity.user.Popu;
import com.cai310.utils.Struts2Utils;
@Results({
	 @Result(name = "message", location = "/WEB-INF/content/common/message.ftl") 
	 })
public class PopuController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String downloadAndroid() {
		Popu popu = this.getPopu(PopuType.ANDROID);
		if(null!=popu){
			userManager.savePopu(popu);
		}
		String redirectUrl = "http://cdn.cai310.com/android/HongCai.apk";
		Struts2Utils.setAttribute("redirectUrl",redirectUrl );
		return redirectforward(Boolean.TRUE,  "欢迎您下载"+Constant.WEB_NAME+"手机客户端，页面将自动跳转到下载页", redirectUrl, "message");
	}
	
}