package com.cai310.lottery.utils.liangcai;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

public class LiangNumResultVisitor extends VisitorSupport{
	private String issue;
	private String result;
	private Boolean isSuccess=Boolean.FALSE;
	public void visit(Attribute node){
		
	}
	public void visit(Element node){
		if("xCode".equals(node.getName())){
			///是否成功
			if(StringUtils.isNotBlank(node.getText())){
				if(String.valueOf("0").equals(node.getText().trim())){
					///成功
					isSuccess=Boolean.TRUE;
				}
			}
		}
		if("xValue".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				String issueData = node.getText().trim();
				String[] issueDataArr = issueData.split("_");
				issue = issueDataArr[0];
				result = issueDataArr[1];
			}
		}
	}
	/**
	 * @return the isSuccess
	 */
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	/**
	 * @param isSuccess the isSuccess to set
	 */
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
