package com.cai310.lottery.fetch.jczq.matchinfo;

public class JczqMatchInfoDTO {
	
	private String matchKey;
	
	private String remoteId;
	
	private String remoteHomeId;
	
	private String remoteGuestId;
	
	private String leagueId;
	
	private TeamResults homeResults;
	
	private TeamResults guestResults;

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

	public String getRemoteHomeId() {
		return remoteHomeId;
	}

	public void setRemoteHomeId(String remoteHomeId) {
		this.remoteHomeId = remoteHomeId;
	}

	public String getRemoteGuestId() {
		return remoteGuestId;
	}

	public void setRemoteGuestId(String remoteGuestId) {
		this.remoteGuestId = remoteGuestId;
	}

	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	public TeamResults getHomeResults() {
		return homeResults;
	}

	public void setHomeResults(TeamResults homeResults) {
		this.homeResults = homeResults;
	}

	public TeamResults getGuestResults() {
		return guestResults;
	}

	public void setGuestResults(TeamResults guestResults) {
		this.guestResults = guestResults;
	}

}
