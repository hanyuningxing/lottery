package com.cai310.lottery.dto.lottery.jczq;

public class OddsDto {
	private String company;
	private String[] firstOdds;
	private String[] currentOdds;
	
	
	private String odds;
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String[] getFirstOdds() {
		return firstOdds;
	}
	public void setFirstOdds(String[] firstOdds) {
		this.firstOdds = firstOdds;
	}
	public String getOdds() {
		return odds;
	}
	public void setOdds(String odds) {
		this.odds = odds;
	}
	public String[] getCurrentOdds() {
		return currentOdds;
	}
	public void setCurrentOdds(String[] currentOdds) {
		this.currentOdds = currentOdds;
	}
	
}
