package com.cai310.lottery.service.lottery.keno.el11to5.impl;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class W500WanResultVisitor extends VisitorSupport{
	private String opencode;
	private String expect;
	public void visit(Attribute node){
		if("opencode".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				opencode = node.getText().trim();
			}
		}
		if("expect".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				expect = node.getText().trim();
			}
		}
	}
	public void visit(Element node){
		
	}
	/**
	 * @return the result
	 */
	public String getOpencode() {
		return opencode;
	}
	public void setOpencode(String opencode) {
		this.opencode = opencode;
	}
	public String getExpect() {
		return expect;
	}
	public void setExpect(String expect) {
		this.expect = expect;
	}

}
