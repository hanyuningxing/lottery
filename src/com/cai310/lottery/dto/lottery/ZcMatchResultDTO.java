package com.cai310.lottery.dto.lottery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PeriodState;
import com.cai310.lottery.common.SubscriptionWay;
import com.cai310.lottery.support.RateItem;

/**
 * 足球数据传输对象
 * 
 */
public class ZcMatchResultDTO {
	/** 1场次编号 */
	private String matchKey;

	/** 1联赛名称 */
	private String gameName;

	/** 1联赛颜色 */
	private String gameColor;

	/** 1比赛时间 */
	private String matchTime;
	
	/** 1主队名称 */
	private String homeTeamName;

	/** 1客队名称 */
	private String guestTeamName;

	/** 1比赛是否已经结束 */
	private boolean ended;

	/** 1比赛是否取消 */
	private boolean cancel;
	
	/** 1让球或者总分 */
	private Float handicap;
	/**上半场主队比分*/
	private Integer homeHalfScore;
	/**上半场客队比分*/
	private Integer guestHalfScore;
	/**全场主队比分*/
	private Integer homeScore;
	/**全场客队比分*/
	private Integer guestScore;
	/** 比赛结果 */
	private String result;
	
	private String resultSp;
	
	private Float totalScore;

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

	public String getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
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

	public Float getHandicap() {
		return handicap;
	}

	public void setHandicap(Float handicap) {
		this.handicap = handicap;
	}

	public Integer getHomeHalfScore() {
		return homeHalfScore;
	}

	public void setHomeHalfScore(Integer homeHalfScore) {
		this.homeHalfScore = homeHalfScore;
	}

	public Integer getGuestHalfScore() {
		return guestHalfScore;
	}

	public void setGuestHalfScore(Integer guestHalfScore) {
		this.guestHalfScore = guestHalfScore;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	

	public Float getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Float totalScore) {
		this.totalScore = totalScore;
	}

	public String getResultSp() {
		return resultSp;
	}

	public void setResultSp(String resultSp) {
		this.resultSp = resultSp;
	}

	
}
