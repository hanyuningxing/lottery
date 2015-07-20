package com.cai310.lottery.entity.lottery.jclq;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.joda.time.DateTimeConstants;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.MatchNameUtil;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.jclq.ItemDXF;
import com.cai310.lottery.support.jclq.ItemRFSF;
import com.cai310.lottery.support.jclq.ItemSF;
import com.cai310.lottery.support.jclq.ItemSFC;
import com.cai310.lottery.support.jclq.JclqUtil;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.utils.DateUtil;


@Table(name = JclqMatch.TABLE_NAME)
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_JCLQ_MATCH_CACHE_REGION)
public class JclqMatch extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = -7664713483528947069L;
	public static final String TABLE_NAME = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "JCLQ_MATCH";


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

	/** 场次归属的日期 */
	private Integer matchDate;

	/** 一天里场次的序号 */
	private Integer lineId;

	/** 联赛名称 */
	private String gameName;

	/** 联赛颜色 */
	private String gameColor;

	/** 比赛时间 */
	private Date matchTime;

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

	/** 单关让分 */
	private Float singleHandicap;

	/** 单关总分 */
	private Float singleTotalScore;

	/** 主队分数 */
	private Integer homeScore;

	/** 客队分数 */
	private Integer guestScore;

	private Double sfResultSp;
	private Double rfsfResultSp;
	private Double sfcResultSp;
	private Double dxfResultSp;

	/**
	 * 表示场次对哪些玩法和哪些过关类型才开发
	 * <p>
	 * 以二进制位表示，一种玩法占用两位，其中一位表示单关，一位表示过关
	 * </p>
	 */
	private Integer openFlag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the {@link #version}
	 */
	@Version
	@Column(nullable = false)
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the {@link #version} to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * @return the {@link #matchKey}
	 */
	@Column(nullable = false, length = 12)
	public String getMatchKey() {
		return matchKey;
	}

	/**
	 * @param matchKey
	 *            the {@link #matchKey} to set
	 */
	public void setMatchKey(String matchKey) {
		this.matchKey = matchKey;
	}

	/**
	 * @return the {@link #matchDate}
	 */
	@Column(nullable = false)
	public Integer getMatchDate() {
		return matchDate;
	}

	/**
	 * @param matchDate
	 *            the {@link #matchDate} to set
	 */
	public void setMatchDate(Integer matchDate) {
		this.matchDate = matchDate;
	}

	/**
	 * @return the {@link #lineId}
	 */
	@Column(nullable = false)
	public Integer getLineId() {
		return lineId;
	}

	/**
	 * @param lineId
	 *            the {@link #lineId} to set
	 */
	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	/**
	 * @return {@link #periodId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getPeriodId() {
		return periodId;
	}

	/**
	 * @param periodId
	 *            the {@link #periodId} to set
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
	 * @param periodNumber
	 *            the {@link #periodNumber} to set
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	/**
	 * @return {@link #gameName}
	 */
	@Column(length = 20)
	public String getGameName() {
		return gameName;
	}

	/**
	 * @param gameName
	 *            the {@link #gameName} to set
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
	 * @param gameColor
	 *            the {@link #gameColor} to set
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
	 * @param matchTime
	 *            the {@link #matchTime} to set
	 */
	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	/**
	 * @return {@link #ended}
	 */
	@Column(nullable = false)
	public boolean isEnded() {
		return ended;
	}

	/**
	 * @param ended
	 *            the {@link #ended} to set
	 */
	public void setEnded(boolean ended) {
		this.ended = ended;
	}

	/**
	 * @return {@link #cancel}
	 */
	@Column(nullable = false)
	public boolean isCancel() {
		return cancel;
	}

	/**
	 * @param cancel
	 *            the {@link #cancel} to set
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
	 * @param homeTeamName
	 *            the {@link #homeTeamName} to set
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
	 * @param guestTeamName
	 *            the {@link #guestTeamName} to set
	 */
	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}

	/**
	 * @return {@link #homeTeamGuangdongName}
	 */
	@Column(length = 20)
	public String getHomeTeamGuangdongName() {
		return homeTeamGuangdongName;
	}

	/**
	 * @param homeTeamGuangdongName
	 *            the {@link #homeTeamGuangdongName} to set
	 */
	public void setHomeTeamGuangdongName(String homeTeamGuangdongName) {
		this.homeTeamGuangdongName = homeTeamGuangdongName;
	}

	/**
	 * @return {@link #guestTeamGuangdongName}
	 */
	@Column(length = 20)
	public String getGuestTeamGuangdongName() {
		return guestTeamGuangdongName;
	}

	/**
	 * @param guestTeamGuangdongName
	 *            the {@link #guestTeamGuangdongName} to set
	 */
	public void setGuestTeamGuangdongName(String guestTeamGuangdongName) {
		this.guestTeamGuangdongName = guestTeamGuangdongName;
	}

	/**
	 * @return {@link #homeScore}
	 */
	@Column
	public Integer getHomeScore() {
		return homeScore;
	}

	/**
	 * @param homeScore
	 *            the {@link #homeScore} to set
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
	 * @param guestScore
	 *            the {@link #guestScore} to set
	 */
	public void setGuestScore(Integer guestScore) {
		this.guestScore = guestScore;
	}

	@Column
	public Float getSingleHandicap() {
		return singleHandicap;
	}

	public void setSingleHandicap(Float singleHandicap) {
		this.singleHandicap = singleHandicap;
	}

	@Column
	public Float getSingleTotalScore() {
		return singleTotalScore;
	}

	public void setSingleTotalScore(Float singleTotalScore) {
		this.singleTotalScore = singleTotalScore;
	}

	/**
	 * @return the {@link #openFlag}
	 */
	@Column(nullable = false)
	public Integer getOpenFlag() {
		return openFlag;
	}

	/**
	 * @param openFlag
	 *            the {@link #openFlag} to set
	 */
	public void setOpenFlag(Integer openFlag) {
		this.openFlag = openFlag;
	}

	@Column
	public Double getSfResultSp() {
		return sfResultSp;
	}

	public void setSfResultSp(Double sfResultSp) {
		this.sfResultSp = sfResultSp;
	}

	@Column
	public Double getRfsfResultSp() {
		return rfsfResultSp;
	}

	public void setRfsfResultSp(Double rfsfResultSp) {
		this.rfsfResultSp = rfsfResultSp;
	}

	@Column
	public Double getSfcResultSp() {
		return sfcResultSp;
	}

	public void setSfcResultSp(Double sfcResultSp) {
		this.sfcResultSp = sfcResultSp;
	}

	@Column
	public Double getDxfResultSp() {
		return dxfResultSp;
	}

	public void setDxfResultSp(Double dxfResultSp) {
		this.dxfResultSp = dxfResultSp;
	}

	@Transient
	public String getMatchKeyText() {
		if (this.matchDate != null && this.lineId != null)
			return JclqUtil.getMatchKeyText(matchDate, lineId);
		else
			return null;
	}

	@Transient
	public boolean isOpen(PlayType playType, PassMode passMode) {
		if (this.getOpenFlag() == null)
			return false;
		int v = JclqUtil.getOpenFlag(playType, passMode);
		return (this.getOpenFlag() & v) > 0;
	}
	@Transient
	public boolean isNotDisplay(PlayType playType, PassMode passMode) throws DataException {
		if (!isOpen(playType, passMode)||isStop()||this.cancel) {
			return true;
		}
		return false;
	}
	
//	周一、周二、周五   9:00----22:40
//	周三、周四   7:30----22:40
//	周六~周日   9:00----00:40
	@Transient
	public boolean isStop() throws DataException {
		if(null==this.getMatchDate())return true;
		boolean isSaleEnded = this.isEnded() || this.isCancel();
		if(isSaleEnded){
			return true;
		}
		Date now = new Date();
		if(now.after(this.getWebOfficialEndTime()))return true;
		return false;
		
	}
	@Transient
	public int getMatchDayOfWeek(Date matchDate) throws DataException{
		Calendar c = Calendar.getInstance();
		c.setTime(matchDate);
		int week = c.get(Calendar.DAY_OF_WEEK);
		if (week == Calendar.SUNDAY) {
			return DateTimeConstants.SUNDAY;
		}else if(week == Calendar.MONDAY) {
			return DateTimeConstants.MONDAY;
		}else if(week == Calendar.TUESDAY) {
			return DateTimeConstants.TUESDAY;
		}else if(week == Calendar.WEDNESDAY) {
			return DateTimeConstants.WEDNESDAY;
		}else if(week == Calendar.THURSDAY) {
			return DateTimeConstants.THURSDAY;
		}else if(week == Calendar.FRIDAY) {
			return DateTimeConstants.FRIDAY;
		}else if(week == Calendar.SATURDAY) {
			return DateTimeConstants.SATURDAY;
		}else{
			throw new DataException("获取{"+this.getMatchKey()+"}比赛截止时间错误matchDate="+matchDate);
		}
	}
	@Transient
	public Date getMatchEndDate() throws DataException{
		if(null==this.getMatchDate())return null;
		if(null==this.getMatchTime())return null;
		Date endStarDate = null;///截至时间 
		Date endEndDate = null;///开售时间（截至后）
		Date weekDate = DateUtil.strToDate(""+this.getMatchDate(),"yyyyMMdd");
		if(null==weekDate){
			throw new DataException("获取{"+this.getMatchKey()+"}比赛截止时间错误weekDate=null");
		}
		Integer week = getMatchDayOfWeek(weekDate);
		if(null==week){
			throw new DataException("获取{"+this.getMatchKey()+"}比赛截止时间错误week=null");
		}
	    ////取得星期
		if(week==6||week==7){
			//周末
			weekDate = DateUtils.addDays(weekDate, 1);///把截至时间调整到次日
			endStarDate = DateUtil.strToDate(DateUtil.dateToStr(weekDate,"yyyy-MM-dd")+" "+JclqConstant.WEEKEND_EDN_TIME);
			endEndDate = DateUtil.strToDate(DateUtil.dateToStr(weekDate,"yyyy-MM-dd")+" 9:00");
		}else if(week==3||week==2){
			endStarDate = DateUtil.strToDate(DateUtil.dateToStr(weekDate,"yyyy-MM-dd")+" "+JclqConstant.UNWEEKEND_EDN_TIME);
			weekDate = DateUtils.addDays(weekDate, 1);///把截至开始时间调整到次日
			endEndDate = DateUtil.strToDate(DateUtil.dateToStr(weekDate,"yyyy-MM-dd")+" 7:30");
		}else if(week==1||week==4||week==5){
			endStarDate = DateUtil.strToDate(DateUtil.dateToStr(weekDate,"yyyy-MM-dd")+" "+JclqConstant.UNWEEKEND_EDN_TIME);
			weekDate = DateUtils.addDays(weekDate, 1);///把截至开始时间调整到次日
			endEndDate = DateUtil.strToDate(DateUtil.dateToStr(weekDate,"yyyy-MM-dd")+" 9:00");
		}else{
			throw new DataException("获取{"+this.getMatchKey()+"}比赛截止时间错误week="+week+"weekDate="+weekDate);
		}
		if(null==endStarDate){
			throw new DataException("获取{"+this.getMatchKey()+"}比赛截止时间错误endStarDate=null");
		}
		if(null==endEndDate){
			throw new DataException("获取{"+this.getMatchKey()+"}比赛截止时间错误endEndDate=null");
		}
		if(this.getMatchTime().before(endStarDate)){
			////如果比赛在停售时间之前
			return this.getMatchTime();
		}else if(this.getMatchTime().after(endStarDate)&&this.getMatchTime().before(endEndDate)){
			////如果比赛在停售时间内
			return endStarDate;
		}else if(this.getMatchTime().after(endEndDate)){
			////如果比赛在停售时间之后
			return this.getMatchTime();
		}else if(this.getMatchTime().getTime()==endStarDate.getTime()||this.getMatchTime().getTime()==endEndDate.getTime()){
			////如果比赛==停售时间
			return endStarDate;
		}else{
			//报错
			throw new DataException("获取{"+this.getMatchKey()+"}比赛截止时间错误");
		}
	}
	@Transient
	public Date getTicketOfficialEndTime() throws DataException {
		Calendar c = Calendar.getInstance();
		c.setTime(this.getMatchEndDate());
		c.add(Calendar.MINUTE, -JclqConstant.TICKET_AHEAD_TIME);
		return c.getTime();
		
	}
	@Transient
	public Date getWebOfficialEndTime() throws DataException {
		Calendar c = Calendar.getInstance();
		c.setTime(this.getMatchEndDate());
		c.add(Calendar.MINUTE, -JclqConstant.MATCH_AHEAD_TIME);
		return c.getTime();
		
	}
	@Transient
	public boolean isSaleEnded(int aheadEnd) {
		boolean isSaleEnded = this.isEnded() || this.isCancel();
		if (!isSaleEnded && this.getMatchTime() != null) {
			return System.currentTimeMillis() + aheadEnd * 60 * 1000 > this.getMatchTime().getTime();
		} else {
			return true;
		}
	}
	@Transient
	public Item getResult(PlayType playType) {
		try{
			switch (playType) {
			case SF:
				return getSfResult();
			case RFSF:
				return getRfsfResult(this.getSingleHandicap());
			case SFC:
				return getSfcResult();
			case DXF:
				return getDxfResult(this.getSingleTotalScore());
			default:
				throw new RuntimeException("玩法不正确.");
			}
		}catch(Exception e){
			return null;
		}
	}
	@Transient
	public Double getResultSp(PlayType playType) {
		if (this.isCancel())
			return 1.0;
		if (!this.isEnded())
			return null;

		switch (playType) {
		case SF:
			return getSfResultSp();
		case RFSF:
			return getRfsfResultSp();
		case SFC:
			if(getSfcResultSp()!=null){
				return getSfcResultSp()*2;
			}
			return null;
		case DXF:
			return getDxfResultSp();
		default:
			throw new RuntimeException("玩法不正确.");
		}
	}

	@Transient
	public Item getSingleResult(PlayType playType) {
		switch (playType) {
		case SF:
			return getSfResult();
		case RFSF:
			return getRfsfResult(this.getSingleHandicap());
		case SFC:
			return getSfcResult();
		case DXF:
			return getDxfResult(this.getSingleTotalScore());
		default:
			throw new RuntimeException("玩法不正确.");
		}
	}

	@Transient
	public Item getPassResult(PlayType playType, Double referenceValue) {
		switch (playType) {
		case SF:
			return getSfResult();
		case RFSF:
			return getRfsfResult(referenceValue);
		case SFC:
			return getSfcResult();
		case DXF:
			return getDxfResult(referenceValue);
		default:
			throw new RuntimeException("玩法不正确.");
		}
	}

	@Transient
	public ItemSF getSfResult() {
		if (null == this.homeScore || null == this.guestScore) {
			return null;
		}
		if (this.ended && !this.cancel) {
			int home = this.homeScore.intValue();
			int guest = this.guestScore.intValue();

			if (home > guest) {
				return ItemSF.WIN;
			} else if (home < guest) {
				return ItemSF.LOSE;
			} else {
				throw new RuntimeException("胜负玩法开奖结果异常，主队分数不能等于客队分数。");
			}
		}
		return null;
	}

	@Transient
	public ItemRFSF getRfsfResult(double handicap) {
		if (null == this.homeScore || null == this.guestScore) {
			return null;
		}
		if (this.ended && !this.cancel) {
			double home = this.homeScore.intValue() + handicap;
			double guest = this.guestScore.intValue();

			if (home > guest) {
				return ItemRFSF.SF_WIN;
			} else if (home < guest) {
				return ItemRFSF.SF_LOSE;
			} else {
				throw new RuntimeException("让分胜负玩法开奖结果异常，主队(" + handicap + ")分数不能等于客队分数。");
			}
		}
		return null;
	}

	@Transient
	public ItemSFC getSfcResult() {
		if (null == this.homeScore || null == this.guestScore) {
			return null;
		}
		if (this.ended && !this.cancel) {
			return ItemSFC.valueOf(this.guestScore.intValue() - this.homeScore.intValue());
		}
		return null;
	}

	@Transient
	public ItemDXF getDxfResult(double totalScore) {
		if (null == this.homeScore || null == this.guestScore) {
			return null;
		}
		if (this.ended && !this.cancel) {
			double sum = this.homeScore + this.guestScore;
			if (sum > totalScore) {
				return ItemDXF.LARGE;
			} else if (sum < totalScore) {
				return ItemDXF.LITTLE;
			} else {
				throw new RuntimeException("胜分差玩法开奖结果异常，总分不能等于参考总分。");
			}
		}
		return null;
	}
	@Transient
	public String getShortName(){
		String shortName=MatchNameUtil.getShotName(this.gameName);
		if(StringUtils.isNotBlank(shortName)){
			return shortName;
		}
		return this.gameName;
	}
}
