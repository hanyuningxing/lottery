package com.cai310.lottery.web.controller.info.passcount.visitor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.cai310.lottery.web.controller.info.passcount.form.PassCountIssue;

public class PassCountIssueVisitor extends VisitorSupport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7831617015418311983L;
	
	private PassCountIssue issue;
	
	private List<PassCountIssue> issueList;

	public PassCountIssue getIssue() {
		return issue;
	}

	public void setIssue(PassCountIssue issue) {
		this.issue = issue;
	}

	public List<PassCountIssue> getIssueList() {
		return issueList;
	}

	public void setIssueList(List<PassCountIssue> issueList) {
		this.issueList = issueList;
	}
	
	@Override
	public void visit(Element node){
		if("periodNumber".equals(node.getName())){
			if(StringUtils.isNotBlank(node.getText())){
				if(null==issueList){
					issueList = new ArrayList<PassCountIssue>();
				}
				issue = new PassCountIssue();
				issue.setPeriodNumber(node.getText().trim());
				issueList.add(issue);
			}
		}
	}
}
