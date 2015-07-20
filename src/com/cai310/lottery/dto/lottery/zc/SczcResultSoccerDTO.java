package com.cai310.lottery.dto.lottery.zc;

import java.util.List;

public class SczcResultSoccerDTO {
	private String IssueNum;
	private String AwardTime;
	private String CashInStopTime;
	private String Result;
	private String Bottom;
	private List<SczcTable> Table;
	private List<SczcBonus> Bonus;
	public String getIssueNum() {
		return IssueNum;
	}
	public void setIssueNum(String issueNum) {
		IssueNum = issueNum;
	}
	public String getAwardTime() {
		return AwardTime;
	}
	public void setAwardTime(String awardTime) {
		AwardTime = awardTime;
	}
	public String getCashInStopTime() {
		return CashInStopTime;
	}
	public void setCashInStopTime(String cashInStopTime) {
		CashInStopTime = cashInStopTime;
	}
	public String getResult() {
		return Result;
	}
	public void setResult(String result) {
		Result = result;
	}
	public String getBottom() {
		return Bottom;
	}
	public void setBottom(String bottom) {
		Bottom = bottom;
	}
	public List<SczcTable> getTable() {
		return Table;
	}
	public void setTable(List<SczcTable> table) {
		Table = table;
	}
	public List<SczcBonus> getBonus() {
		return Bonus;
	}
	public void setBonus(List<SczcBonus> bonus) {
		Bonus = bonus;
	}
}
