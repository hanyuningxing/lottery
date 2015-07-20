package com.cai310.lottery.fetch.jczq.local;

import java.util.Date;

import com.cai310.lottery.fetch.jczq.support.EuropeCompany;

public class EuropeOdds {
	
	private EuropeCompany company;
	
	private Date updateTime;
	
	private Double winSp;
	
	private Double drawSp;
	
	private Double loseSp;

	public EuropeCompany getCompany() {
		return company;
	}

	public void setCompany(EuropeCompany company) {
		this.company = company;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Double getWinSp() {
		return winSp;
	}

	public void setWinSp(Double winSp) {
		this.winSp = winSp;
	}

	public Double getDrawSp() {
		return drawSp;
	}

	public void setDrawSp(Double drawSp) {
		this.drawSp = drawSp;
	}

	public Double getLoseSp() {
		return loseSp;
	}

	public void setLoseSp(Double loseSp) {
		this.loseSp = loseSp;
	}

}
