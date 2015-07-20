package com.cai310.lottery.fetch.jczq.matchinfo;

public class JczqMatchEuroOdds {
	
	private String matchKey;
	
	private String remoteId;
	
	private Double avgWonSp;
	
	private Double avgDrawSp;
	
	private Double avgLoseSp;
	
	private Double wonKelly;
	
	private Double drawKelly;
	
	private Double loseKelly;
	
	public JczqMatchEuroOdds(String matchKey, String remoteId) {
		this.matchKey = matchKey;
		this.remoteId = remoteId;
	}

	public String getMatchKey() {
		return matchKey;
	}

	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}

	public String getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(String remoteId) {
		this.remoteId = remoteId;
	}

	public Double getAvgWonSp() {
		return avgWonSp;
	}

	public void setAvgWonSp(Double avgWonSp) {
		this.avgWonSp = avgWonSp;
	}

	public Double getAvgDrawSp() {
		return avgDrawSp;
	}

	public void setAvgDrawSp(Double avgDrawSp) {
		this.avgDrawSp = avgDrawSp;
	}

	public Double getAvgLoseSp() {
		return avgLoseSp;
	}

	public void setAvgLoseSp(Double avgLoseSp) {
		this.avgLoseSp = avgLoseSp;
	}

	public Double getWonKelly() {
		return wonKelly;
	}

	public void setWonKelly(Double wonKelly) {
		this.wonKelly = wonKelly;
	}

	public Double getDrawKelly() {
		return drawKelly;
	}

	public void setDrawKelly(Double drawKelly) {
		this.drawKelly = drawKelly;
	}

	public Double getLoseKelly() {
		return loseKelly;
	}

	public void setLoseKelly(Double loseKelly) {
		this.loseKelly = loseKelly;
	}

}
