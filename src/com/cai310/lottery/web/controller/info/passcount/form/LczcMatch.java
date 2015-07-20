package com.cai310.lottery.web.controller.info.passcount.form;
public class LczcMatch{
	/** 场次编号 */
	private Integer lineId;
	/** 主队名称 */
	private String homeTeamName;

	/** 客队名称 */
	private String guestTeamName;
	
	/** 比赛结果 */
	private String halfResult;
	
	/** 比赛结果 */
	private String fullResult;

	/**
	 * @return the lineId
	 */
	public Integer getLineId() {
		return lineId;
	}

	/**
	 * @param lineId the lineId to set
	 */
	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	/**
	 * @return the homeTeamName
	 */
	public String getHomeTeamName() {
		char ch;
		char[] input = homeTeamName.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer();
	    for (int i = 0; i < len; i++) {
			ch = input[i];
	    	out.append(ch);
	    	out.append("<br />");
    	}
	    return out.toString();
	}

	/**
	 * @param homeTeamName the homeTeamName to set
	 */
	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	/**
	 * @return the guestTeamName
	 */
	public String getGuestTeamName() {
		char ch;
		char[] input = guestTeamName.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer();
	    for (int i = 0; i < len; i++) {
			ch = input[i];
	    	out.append(ch);
	    	out.append("<br />");
    	}
	    return out.toString();
	}

	/**
	 * @param guestTeamName the guestTeamName to set
	 */
	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}
	/**
	 * @return the halfResult
	 */
	public String getHalfResult() {
		return halfResult;
	}

	/**
	 * @param halfResult the halfResult to set
	 */
	public void setHalfResult(String halfResult) {
		this.halfResult = halfResult;
	}

	/**
	 * @return the fullResult
	 */
	public String getFullResult() {
		return fullResult;
	}

	/**
	 * @param fullResult the fullResult to set
	 */
	public void setFullResult(String fullResult) {
		this.fullResult = fullResult;
	}
	
}
