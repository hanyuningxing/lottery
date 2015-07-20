package com.cai310.lottery.fetch.keno;

import java.util.Date;

public class kenoBaseResult {
	
	private String IssueNum;
	private Date AwardTime;
	private String Result;
	public String getIssueNum() {
		return IssueNum;
	}
	public void setIssueNum(String issueNum) {
		IssueNum = issueNum;
	}
	public Date getAwardTime() {
		return AwardTime;
	}
	public void setAwardTime(Date awardTime) {
		AwardTime = awardTime;
	}
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}
}
