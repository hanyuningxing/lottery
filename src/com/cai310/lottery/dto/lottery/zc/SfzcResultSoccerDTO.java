package com.cai310.lottery.dto.lottery.zc;

import java.util.List;

public class SfzcResultSoccerDTO {
	private String IssueNum;
	private String AwardTime;
	private String CashInStopTime;
	private String Result;
	private String Bottom;
	private List<SfzcTable> Table;
	private List<SfzcBonus> Bonus;
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
	public List<SfzcTable> getTable() {
		return Table;
	}
	public void setTable(List<SfzcTable> table) {
		Table = table;
	}
	public List<SfzcBonus> getBonus() {
		return Bonus;
	}
	public void setBonus(List<SfzcBonus> bonus) {
		Bonus = bonus;
	}

	
}
