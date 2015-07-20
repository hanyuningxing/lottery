package com.cai310.lottery.web.controller;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
@Results({
	 @Result(name = "index", location = "/WEB-INF/content/phone/index.ftl") 
	 })
public class PhoneController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String index() {
		return "index";
	}
	
}