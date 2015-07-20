package com.cai310.lottery.entity.lottery.zc;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.utils.DateUtil;

/**
 * <b>足彩对阵基类</b>
 * 
 */
@MappedSuperclass
public abstract class ZcMatch extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = -5518073963338881030L;

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

	/** 场次编号 */
	private Integer lineId;

	/** 联赛名称 */
	private String gameName;

	/** 联赛颜色 */
	private String gameColor;

	/** 比赛时间 */
	private Date matchTime;
	
	/** 比赛时间Str */
	private String matchTimeStr;

	/** 比赛是否取消 */
	private boolean cancel;

	/** 主队名称 */
	private String homeTeamName;

	/** 客队名称 */
	private String guestTeamName;

	/** 主队进球数 */
	private Integer homeScore;

	/** 客队进球数 */
	private Integer guestScore;
	
	/** 参考亚赔 */
	private Double asiaOdd;

	/** 参考赔率1 */
	private Double odds1;

	/** 参考赔率2 */
	private Double odds2;

	/** 参考赔率3 */
	private Double odds3;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	/**
	 * @return {@link #version}
	 */
	@Version
	@Column(nullable = false)
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return {@link #periodId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getPeriodId() {
		return periodId;
	}

	/**
	 * @param periodId the {@link #periodId} to set
	 */
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	/**
	 * @return {@link #periodNumber}
	 */
	@Column(nullable = false, updatable = false, length = 20)
	public String getPeriodNumber() {
		return periodNumber;
	}

	/**
	 * @param periodNumber the {@link #periodNumber} to set
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	/**
	 * @return {@link #lineId}
	 */
	@Column(nullable = false)
	public Integer getLineId() {
		return lineId;
	}

	/**
	 * @param lineId the {@link #lineId} to set
	 */
	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	/**
	 * @return {@link #gameName}
	 */
	@Column(length = 20)
	public String getGameName() {
		return gameName;
	}

	/**
	 * @param gameName the {@link #gameName} to set
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	/**
	 * @return {@link #gameColor}
	 */
	@Column(length = 7)
	public String getGameColor() {
		return gameColor;
	}

	/**
	 * @param gameColor the {@link #gameColor} to set
	 */
	public void setGameColor(String gameColor) {
		this.gameColor = gameColor;
	}

	/**
	 * @return {@link #matchTime}
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getMatchTime() {
		return matchTime;
	}

	/**
	 * @param matchTime the {@link #matchTime} to set
	 */
	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	/**
	 * @return {@link #cancel}
	 */
	@Column(nullable = false)
	public boolean isCancel() {
		return cancel;
	}

	/**
	 * @param cancel the {@link #cancel} to set
	 */
	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	/**
	 * @return {@link #homeTeamName}
	 */
	@Column(nullable = false, length = 20)
	public String getHomeTeamName() {
		return homeTeamName;
	}

	/**
	 * @param homeTeamName the {@link #homeTeamName} to set
	 */
	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	/**
	 * @return {@link #guestTeamName}
	 */
	@Column(nullable = false, length = 20)
	public String getGuestTeamName() {
		return guestTeamName;
	}

	/**
	 * @param guestTeamName the {@link #guestTeamName} to set
	 */
	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}

	/**
	 * @return {@link #homeScore}
	 */
	@Column
	public Integer getHomeScore() {
		return homeScore;
	}

	/**
	 * @param homeScore the {@link #homeScore} to set
	 */
	public void setHomeScore(Integer homeScore) {
		this.homeScore = homeScore;
	}

	/**
	 * @return {@link #guestScore}
	 */
	@Column
	public Integer getGuestScore() {
		return guestScore;
	}

	/**
	 * @param guestScore the {@link #guestScore} to set
	 */
	public void setGuestScore(Integer guestScore) {
		this.guestScore = guestScore;
	}

	/**
	 * @return {@link #odds1}
	 */
	@Column
	public Double getOdds1() {
		return odds1;
	}

	/**
	 * @param odds1 the {@link #odds1} to set
	 */
	public void setOdds1(Double odds1) {
		this.odds1 = odds1;
	}

	/**
	 * @return {@link #odds2}
	 */
	@Column
	public Double getOdds2() {
		return odds2;
	}

	/**
	 * @param odds2 the {@link #odds2} to set
	 */
	public void setOdds2(Double odds2) {
		this.odds2 = odds2;
	}

	/**
	 * @return {@link #odds3}
	 */
	@Column
	public Double getOdds3() {
		return odds3;
	}

	/**
	 * @param odds3 the {@link #odds3} to set
	 */
	public void setOdds3(Double odds3) {
		this.odds3 = odds3;
	}

	/**
	 * @return {@link #asiaOdd}
	 */
	@Column
	public Double getAsiaOdd() {
		return asiaOdd;
	}

	/**
	 * @param asiaOdd the {@link #asiaOdd} to set
	 */
	public void setAsiaOdd(Double asiaOdd) {
		this.asiaOdd = asiaOdd;
	}	
	/** 获取比赛结果 */
	@Transient
	public String getMatchTimeStr() {
		return this.matchTimeStr;
	}

	public void setMatchTimeStr(String matchTimeStr) {
		this.matchTimeStr = matchTimeStr;
	}
	

}
