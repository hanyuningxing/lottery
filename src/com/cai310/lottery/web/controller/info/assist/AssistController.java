package com.cai310.lottery.web.controller.info.assist;

import com.cai310.lottery.web.controller.BaseController;
public class AssistController extends BaseController {
	/**
	 * 
	 */
	private String type;
	private static final long serialVersionUID = -4561478180419508172L;
	public String index() {
		return type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	


	
}
