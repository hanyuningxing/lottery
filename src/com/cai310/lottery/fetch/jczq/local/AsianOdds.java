package com.cai310.lottery.fetch.jczq.local;

import java.util.Date;

import com.cai310.lottery.fetch.jczq.support.AsianCompany;
import com.cai310.lottery.fetch.jczq.support.AsianHandicap;

public class AsianOdds {
	
	private AsianCompany company;
	
	private Date updateTime;
	
	private Double homeSp;
	
	private AsianHandicap asianHandicap;
	
	private Double guestSp;

	public AsianCompany getCompany() {
		return company;
	}

	public void setCompany(AsianCompany company) {
		this.company = company;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Double getHomeSp() {
		return homeSp;
	}

	public void setHomeSp(Double homeSp) {
		this.homeSp = homeSp;
	}

	public AsianHandicap getAsianHandicap() {
		return asianHandicap;
	}

	public void setAsianHandicap(AsianHandicap asianHandicap) {
		this.asianHandicap = asianHandicap;
	}

	public Double getGuestSp() {
		return guestSp;
	}

	public void setGuestSp(Double guestSp) {
		this.guestSp = guestSp;
	}
	
}
