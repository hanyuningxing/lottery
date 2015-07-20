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
public class ZcMatchDTO {
	
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
	private boolean open;
	
	/** 让球或者总分 */
	private Float handicap;
	
	private Map<String, RateItem> sp;

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

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public Float getHandicap() {
		return handicap;
	}

	public void setHandicap(Float handicap) {
		this.handicap = handicap;
	}

	public Map<String, RateItem> getSp() {
		return sp;
	}

	public void setSp(Map<String, RateItem> sp) {
		this.sp = sp;
	}

	public String getEndSaleTime() {
		return endSaleTime;
	}

	public void setEndSaleTime(String endSaleTime) {
		this.endSaleTime = endSaleTime;
	}

	
}
