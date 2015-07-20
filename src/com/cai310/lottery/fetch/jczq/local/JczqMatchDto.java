package com.cai310.lottery.fetch.jczq.local;
 
import java.util.Date;

public class JczqMatchDto {
 

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;

	/** 版本号,用于实现乐观锁 */
	private Integer version;

	/** 期编号 */
	private Long periodId;

	/** 期号 */
	private String periodNumber;

	/** 场次标识 */
	private String matchKey;

	private String num;

	/** 联赛名称 */
	private String gameName;

	/** 联赛颜色 */
	private String gameColor;

	/** 比赛时间 */
	private String matchTime;
	private String matchDate;
	
	/** 开售为足彩对阵*/
	private boolean zcMatch;

	/** 比赛是否已经结束 */
	private boolean ended;

	/** 比赛是否取消 */
	private boolean cancel;

	/** 主队名称 */
	private String homeTeamName;

	/** 客队名称 */
	private String guestTeamName;

	/** 主队粤语名称 */
	private String homeTeamGuangdongName;

	/** 客队粤语名称 */
	private String guestTeamGuangdongName;

	/** 让球 */
	private String handicap;

	/** 主队半场进球数 */
	private Integer halfHomeScore;

	/** 客队半场进球数 */
	private Integer halfGuestScore;

	/** 主队全场进球数 */
	private Integer fullHomeScore;

	/** 客队全场进球数 */
	private Integer fullGuestScore;
	
	private String odds;
	/** 胜平负 */
	private Double rfspfResultSp;
	private Double spfResultSp;
	private Double jqsResultSp;
	private Double bfResultSp;
	private Double bqqResultSp;

	/**
	 * 表示场次对哪些玩法和哪些过关类型才开发
	 * <p>
	 * 以二进制位表示，一种玩法占用两位，其中一位表示单关，一位表示过关
	 * </p>
	 */
	private Integer openFlag;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	public String getMatchKey() {
		return matchKey;
	}

	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}

 
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameColor() {
		return gameColor;
	}

	public void setGameColor(String gameColor) {
		this.gameColor = gameColor;
	}
 
	
	
	public boolean isZcMatch() {
		return zcMatch;
	}

	public void setZcMatch(boolean zcMatch) {
		this.zcMatch = zcMatch;
	}

	public boolean isEnded() {
		return ended;
	}

	public void setEnded(boolean ended) {
		this.ended = ended;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public String getGuestTeamName() {
		return guestTeamName;
	}

	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}

	public String getHomeTeamGuangdongName() {
		return homeTeamGuangdongName;
	}

	public void setHomeTeamGuangdongName(String homeTeamGuangdongName) {
		this.homeTeamGuangdongName = homeTeamGuangdongName;
	}

	public String getGuestTeamGuangdongName() {
		return guestTeamGuangdongName;
	}

	public void setGuestTeamGuangdongName(String guestTeamGuangdongName) {
		this.guestTeamGuangdongName = guestTeamGuangdongName;
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

	public Double getRfspfResultSp() {
		return rfspfResultSp;
	}

	public void setRfspfResultSp(Double rfspfResultSp) {
		this.rfspfResultSp = rfspfResultSp;
	}

	public Double getSpfResultSp() {
		return spfResultSp;
	}

	public void setSpfResultSp(Double spfResultSp) {
		this.spfResultSp = spfResultSp;
	}

	public Double getJqsResultSp() {
		return jqsResultSp;
	}

	public void setJqsResultSp(Double jqsResultSp) {
		this.jqsResultSp = jqsResultSp;
	}

	public Double getBfResultSp() {
		return bfResultSp;
	}

	public void setBfResultSp(Double bfResultSp) {
		this.bfResultSp = bfResultSp;
	}

	public Double getBqqResultSp() {
		return bqqResultSp;
	}

	public void setBqqResultSp(Double bqqResultSp) {
		this.bqqResultSp = bqqResultSp;
	}

	public Integer getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(Integer openFlag) {
		this.openFlag = openFlag;
	}

 

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}

	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}
 

	public String getOdds() {
		return odds;
	}

	public void setOdds(String odds) {
		this.odds = odds;
	}

	public String getHandicap() {
		return handicap;
	}

	public void setHandicap(String handicap) {
		this.handicap = handicap;
	}

	 
	
}
