package com.cai310.lottery.dto.lottery.jclq;
public class JclqMatchDTO {
	/** 场次归属的日期 */
	private Integer matchDate;

	/** 一天里场次的序号 */
	private Integer lineId;

	/** 主队分数 */
	private Integer homeScore;

	/** 客队分数 */
	private Integer guestScore;

	private String sfResultSp;
	private String rfsfResultSp;
	private String sfcResultSp;
	private String dxfResultSp;
	/** 单关让分 */
	private String singleHandicap;

	/** 单关总分 */
	private String singleTotalScore;
	public Integer getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(Integer matchDate) {
		this.matchDate = matchDate;
	}
	public Integer getLineId() {
		return lineId;
	}
	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}
	public Integer getHomeScore() {
		return homeScore;
	}
	public void setHomeScore(Integer homeScore) {
		this.homeScore = homeScore;
	}
	public Integer getGuestScore() {
		return guestScore;
	}
	public void setGuestScore(Integer guestScore) {
		this.guestScore = guestScore;
	}
	public String getSfResultSp() {
		return sfResultSp;
	}
	public void setSfResultSp(String sfResultSp) {
		this.sfResultSp = sfResultSp;
	}
	public String getRfsfResultSp() {
		return rfsfResultSp;
	}
	public void setRfsfResultSp(String rfsfResultSp) {
		this.rfsfResultSp = rfsfResultSp;
	}
	public String getSfcResultSp() {
		return sfcResultSp;
	}
	public void setSfcResultSp(String sfcResultSp) {
		this.sfcResultSp = sfcResultSp;
	}
	public String getDxfResultSp() {
		return dxfResultSp;
	}
	public void setDxfResultSp(String dxfResultSp) {
		this.dxfResultSp = dxfResultSp;
	}
	public String getSingleHandicap() {
		return singleHandicap;
	}
	public void setSingleHandicap(String singleHandicap) {
		this.singleHandicap = singleHandicap;
	}
	public String getSingleTotalScore() {
		return singleTotalScore;
	}
	public void setSingleTotalScore(String singleTotalScore) {
		this.singleTotalScore = singleTotalScore;
	}

    
}
