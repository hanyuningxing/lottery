package com.cai310.lottery.dto.lottery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PeriodState;
import com.cai310.lottery.common.SubscriptionWay;
import com.cai310.lottery.support.RateItem;

/**
 * 竞彩数据传输对象
 * 
 */
public class JcMixMatchDTO {

	/** 场次编号 */
	private String matchKey;

	/** 联赛名称 */
	private String gameName;

	/** 联赛颜色 */
	private String gameColor;

	/** 比赛时间 */
	private String matchTime;
	/** 截止时间 */
	private String endSaleTime;
	
	/** 主队名称 */
	private String homeTeamName;

	/** 客队名称 */
	private String guestTeamName;

	/** 比赛是否已经结束 */
	private boolean ended;

	/** 比赛是否取消 */
	private boolean cancel;
	
	/** 比赛是否开售 */
	private Map<String, Boolean> mixOpen;
	
	/** 让球或者总分 */
	private Float handicap;
	
	private Map<String,Map<String, RateItem>> mixSp;

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

	

	public Float getHandicap() {
		return handicap;
	}

	public void setHandicap(Float handicap) {
		this.handicap = handicap;
	}


	public String getEndSaleTime() {
		return endSaleTime;
	}

	public void setEndSaleTime(String endSaleTime) {
		this.endSaleTime = endSaleTime;
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

	public Map<String, Boolean> getMixOpen() {
		return mixOpen;
	}

	public void setMixOpen(Map<String, Boolean> mixOpen) {
		this.mixOpen = mixOpen;
	}

	public Map<String, Map<String, RateItem>> getMixSp() {
		return mixSp;
	}

	public void setMixSp(Map<String, Map<String, RateItem>> mixSp) {
		this.mixSp = mixSp;
	}


	
}
