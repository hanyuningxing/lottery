package com.cai310.lottery.dto.lottery.jczq;
/**
 * 用于捉数据
 * @author Administrator
 *
 */
public class JczqMatchDTO{

	/** 场次归属的日期 */
	private Integer matchDate;

	/** 一天里场次的序号 */
	private Integer lineId;
	/** 让球 */
	private Byte handicap;

	/** 主队半场进球数 */
	private Integer halfHomeScore;

	/** 客队半场进球数 */
	private Integer halfGuestScore;

	/** 主队全场进球数 */
	private Integer fullHomeScore;

	/** 客队全场进球数 */
	private Integer fullGuestScore;

	/** 胜平负 */
	private String rfspfResultSp;
	private String spfResultSp;
	private String jqsResultSp;
	private String bfResultSp;
	private String bqqResultSp;
	
	private String rfspfFlag;
	private String spfFlag;
	private String jqsFlag;
	private String bqqFlag;
	
	private String biduiFlag;
	
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
	public Byte getHandicap() {
		return handicap;
	}
	public void setHandicap(Byte handicap) {
		this.handicap = handicap;
	}
	public Integer getHalfHomeScore() {
		return halfHomeScore;
	}
	public void setHalfHomeScore(Integer halfHomeScore) {
		this.halfHomeScore = halfHomeScore;
	}
	public Integer getHalfGuestScore() {
		return halfGuestScore;
	}
	public void setHalfGuestScore(Integer halfGuestScore) {
		this.halfGuestScore = halfGuestScore;
	}
	public Integer getFullHomeScore() {
		return fullHomeScore;
	}
	public void setFullHomeScore(Integer fullHomeScore) {
		this.fullHomeScore = fullHomeScore;
	}
	public Integer getFullGuestScore() {
		return fullGuestScore;
	}
	public void setFullGuestScore(Integer fullGuestScore) {
		this.fullGuestScore = fullGuestScore;
	}	
	public String getRfspfResultSp() {
		return rfspfResultSp;
	}
	public void setRfspfResultSp(String rfspfResultSp) {
		this.rfspfResultSp = rfspfResultSp;
	}
	public String getSpfResultSp() {
		return spfResultSp;
	}
	public void setSpfResultSp(String spfResultSp) {
		this.spfResultSp = spfResultSp;
	}
	public String getJqsResultSp() {
		return jqsResultSp;
	}
	public void setJqsResultSp(String jqsResultSp) {
		this.jqsResultSp = jqsResultSp;
	}
	public String getBfResultSp() {
		return bfResultSp;
	}
	public void setBfResultSp(String bfResultSp) {
		this.bfResultSp = bfResultSp;
	}
	public String getBqqResultSp() {
		return bqqResultSp;
	}
	public void setBqqResultSp(String bqqResultSp) {
		this.bqqResultSp = bqqResultSp;
	}
	public String getRfspfFlag() {
		return rfspfFlag;
	}
	public void setRfspfFlag(String rfspfFlag) {
		this.rfspfFlag = rfspfFlag;
	}
	public String getSpfFlag() {
		return spfFlag;
	}
	public void setSpfFlag(String spfFlag) {
		this.spfFlag = spfFlag;
	}
	public String getJqsFlag() {
		return jqsFlag;
	}
	public void setJqsFlag(String jqsFlag) {
		this.jqsFlag = jqsFlag;
	}
	public String getBqqFlag() {
		return bqqFlag;
	}
	public void setBqqFlag(String bqqFlag) {
		this.bqqFlag = bqqFlag;
	}
	public String getBiduiFlag() {
		return biduiFlag;
	}
	public void setBiduiFlag(String biduiFlag) {
		this.biduiFlag = biduiFlag;
	}
 
	 

	
}
