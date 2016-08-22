package com.cai310.lottery.entity.lottery.dczc;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.DczcConstant;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.entity.info.OddsData;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.dczc.ItemBF;
import com.cai310.lottery.support.dczc.ItemBQQSPF;
import com.cai310.lottery.support.dczc.ItemSPF;
import com.cai310.lottery.support.dczc.ItemSXDS;
import com.cai310.lottery.support.dczc.ItemZJQS;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.utils.DateUtil;

/**
 * 单场比赛对阵
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "DCZC_MATCH")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_DCZC_MATCH_CACHE_REGION)
public class DczcMatch extends IdEntity implements CreateMarkable, UpdateMarkable {
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
	private Byte handicap;

	/** 主队半场进球数 */
	private Integer halfHomeScore;

	/** 客队半场进球数 */
	private Integer halfGuestScore;

	/** 主队全场进球数 */
	private Integer fullHomeScore;

	/** 客队全场进球数 */
	private Integer fullGuestScore;

	/** 胜平负最终SP值 */
	private Double spfResultSp;

	/** 总进球数最终SP值 */
	private Double zjqsResultSp;

	/** 上下单双最终SP值 */
	private Double sxdsResultSp;

	/** 比分最终SP值 */
	private Double bfResultSp;

	/** 半全场最终SP值 */
	private Double bqqspfResultSp;

	/** 赔率信息 **/
	private OddsData oddsData;

	/**
	 * 不保存在本表
	 * 
	 * @return
	 */
	@Transient
	public OddsData getOddsData() {
		return oddsData;
	}

	public void setOddsData(OddsData oddsData) {
		this.oddsData = oddsData;
	}

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
	 * @return {@link #ended}
	 */
	@Column(nullable = false)
	public boolean isEnded() {
		return ended;
	}

	/**
	 * @param ended the {@link #ended} to set
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
	 * @return {@link #homeTeamGuangdongName}
	 */
	@Column(length = 20)
	public String getHomeTeamGuangdongName() {
		return homeTeamGuangdongName;
	}

	/**
	 * @param homeTeamGuangdongName the {@link #homeTeamGuangdongName} to set
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
	 * @param guestTeamGuangdongName the {@link #guestTeamGuangdongName} to set
	 */
	public void setGuestTeamGuangdongName(String guestTeamGuangdongName) {
		this.guestTeamGuangdongName = guestTeamGuangdongName;
	}

	/**
	 * @return {@link #halfHomeScore}
	 */
	@Column
	public Integer getHalfHomeScore() {
		return halfHomeScore;
	}

	/**
	 * @param halfHomeScore the {@link #halfHomeScore} to set
	 */
	public void setHalfHomeScore(Integer halfHomeScore) {
		this.halfHomeScore = halfHomeScore;
	}

	/**
	 * @return {@link #halfGuestScore}
	 */
	@Column
	public Integer getHalfGuestScore() {
		return halfGuestScore;
	}

	/**
	 * @param halfGuestScore the {@link #halfGuestScore} to set
	 */
	public void setHalfGuestScore(Integer halfGuestScore) {
		this.halfGuestScore = halfGuestScore;
	}

	/**
	 * @return {@link #fullHomeScore}
	 */
	@Column
	public Integer getFullHomeScore() {
		return fullHomeScore;
	}

	/**
	 * @param fullHomeScore the {@link #fullHomeScore} to set
	 */
	public void setFullHomeScore(Integer fullHomeScore) {
		this.fullHomeScore = fullHomeScore;
	}

	/**
	 * @return {@link #fullGuestScore}
	 */
	@Column
	public Integer getFullGuestScore() {
		return fullGuestScore;
	}

	/**
	 * @param fullGuestScore the {@link #fullGuestScore} to set
	 */
	public void setFullGuestScore(Integer fullGuestScore) {
		this.fullGuestScore = fullGuestScore;
	}

	/**
	 * @return {@link #spfResultSp}
	 */
	@Column
	public Double getSpfResultSp() {
		return spfResultSp;
	}

	/**
	 * @param spfResultSp the {@link #spfResultSp} to set
	 */
	public void setSpfResultSp(Double spfResultSp) {
		this.spfResultSp = spfResultSp;
	}

	/**
	 * @return {@link #zjqsResultSp}
	 */
	@Column
	public Double getZjqsResultSp() {
		return zjqsResultSp;
	}

	/**
	 * @param zjqsResultSp the {@link #zjqsResultSp} to set
	 */
	public void setZjqsResultSp(Double zjqsResultSp) {
		this.zjqsResultSp = zjqsResultSp;
	}

	/**
	 * @return {@link #sxdsResultSp}
	 */
	@Column
	public Double getSxdsResultSp() {
		return sxdsResultSp;
	}

	/**
	 * @param sxdsResultSp the {@link #sxdsResultSp} to set
	 */
	public void setSxdsResultSp(Double sxdsResultSp) {
		this.sxdsResultSp = sxdsResultSp;
	}

	/**
	 * @return {@link #bfResultSp}
	 */
	@Column
	public Double getBfResultSp() {
		return bfResultSp;
	}

	/**
	 * @param bfResultSp the {@link #bfResultSp} to set
	 */
	public void setBfResultSp(Double bfResultSp) {
		this.bfResultSp = bfResultSp;
	}

	/**
	 * @return {@link #bqqspfResultSp}
	 */
	@Column
	public Double getBqqspfResultSp() {
		return bqqspfResultSp;
	}

	/**
	 * @param bqqspfResultSp the {@link #bqqspfResultSp} to set
	 */
	public void setBqqspfResultSp(Double bqqspfResultSp) {
		this.bqqspfResultSp = bqqspfResultSp;
	}

	/**
	 * @return {@link #handicap}
	 */
	@Column
	public Byte getHandicap() {
		return handicap;
	}

	/**
	 * @param handicap the {@link #handicap} to set
	 */
	public void setHandicap(Byte handicap) {
		this.handicap = handicap;
	}

	/**
	 * 检查是否已经截止
	 * 
	 * @param aheadMinuteEnd 提前多少分钟截止
	 * @return 是否已经截止
	 */
	public boolean checkSaleEnd(int aheadMinuteEnd) {
		if (isEnded() || isCancel())
			return true;
		long nowMillis = System.currentTimeMillis();
		long matchMillis = this.getEndSaleTime().getTime();
		return nowMillis > matchMillis - aheadMinuteEnd * 60 * 1000;
	}

	@Transient
	public Double getResultSp(PlayType playType) {
		if (this.isCancel())
			return DczcConstant.CANCEL_MATCH_RESULT_SP;
		if (!this.isEnded())
			return null;

		switch (playType) {
		case SPF:
			return getSpfResultSp();
		case ZJQS:
			return getZjqsResultSp();
		case SXDS:
			return getSxdsResultSp();
		case BF:
			return getBfResultSp();
		case BQQSPF:
			return getBqqspfResultSp();
		default:
			throw new RuntimeException("玩法不正确.");
		}
	}

	@Transient
	public Item getResult(PlayType playType) {
		switch (playType) {
		case SPF:
			return getSPFResult();
		case ZJQS:
			return getZJQSResult();
		case SXDS:
			return getSXDSResult();
		case BF:
			return getBFResult();
		case BQQSPF:
			return getBQQSPFResult();
		default:
			throw new RuntimeException("玩法不正确.");
		}
	}

	@Transient
	public ItemSPF getSPFResult() {
		if (this.isCancel())
			return null;
		if (!this.isEnded())
			return null;
		if (this.getFullHomeScore() == null || this.getFullGuestScore() == null)
			return null;

		int handicap = (null == getHandicap()) ? 0 : getHandicap();
		int fullGuestScoreOfHandicap = getFullGuestScore() - handicap;
		if (getFullHomeScore() > fullGuestScoreOfHandicap)
			return ItemSPF.WIN;
		else if (getFullHomeScore().equals(fullGuestScoreOfHandicap))
			return ItemSPF.DRAW;
		else
			return ItemSPF.LOSE;
	}

	@Transient
	public ItemZJQS getZJQSResult() {
		if (this.isCancel())
			return null;
		if (!this.isEnded())
			return null;
		if (this.getFullHomeScore() == null || this.getFullGuestScore() == null)
			return null;

		int totalScore = this.getFullHomeScore() + this.getFullGuestScore();
		if (totalScore < 0)
			throw new RuntimeException("数据异常:总进球数小于零");

		switch (totalScore) {
		case 0:
			return ItemZJQS.S0;
		case 1:
			return ItemZJQS.S1;
		case 2:
			return ItemZJQS.S2;
		case 3:
			return ItemZJQS.S3;
		case 4:
			return ItemZJQS.S4;
		case 5:
			return ItemZJQS.S5;
		case 6:
			return ItemZJQS.S6;
		case 7:
			return ItemZJQS.S7;
		default:
			return ItemZJQS.S7;
		}
	}

	@Transient
	public ItemSXDS getSXDSResult() {
		if (this.isCancel())
			return null;
		if (!this.isEnded())
			return null;
		if (this.getFullHomeScore() == null || this.getFullGuestScore() == null)
			return null;

		int totalScore = getFullHomeScore() + getFullGuestScore();
		if (totalScore >= 3) {
			if (totalScore % 2 != 0)
				return ItemSXDS.UP_ODD; // 上单
			else
				return ItemSXDS.UP_EVEN; // 上双
		} else {
			if (totalScore % 2 != 0)
				return ItemSXDS.DOWN_ODD; // 下单
			else
				return ItemSXDS.DOWN_EVEN; // 下双
		}
	}

	@Transient
	public ItemBF getBFResult() {
		if (this.isCancel())
			return null;
		if (!this.isEnded())
			return null;
		if (this.getFullHomeScore() == null || this.getFullGuestScore() == null)
			return null;

		if (getFullHomeScore() == 1 && getFullGuestScore() == 0) {
			return ItemBF.WIN10;
		} else if (getFullHomeScore() == 2 && getFullGuestScore() == 0) {
			return ItemBF.WIN20;
		} else if (getFullHomeScore() == 2 && getFullGuestScore() == 1) {
			return ItemBF.WIN21;
		} else if (getFullHomeScore() == 3 && getFullGuestScore() == 0) {
			return ItemBF.WIN30;
		} else if (getFullHomeScore() == 3 && getFullGuestScore() == 1) {
			return ItemBF.WIN31;
		} else if (getFullHomeScore() == 3 && getFullGuestScore() == 2) {
			return ItemBF.WIN32;
		} else if (getFullHomeScore() == 4 && getFullGuestScore() == 0) {
			return ItemBF.WIN40;
		} else if (getFullHomeScore() == 4 && getFullGuestScore() == 1) {
			return ItemBF.WIN41;
		} else if (getFullHomeScore() == 4 && getFullGuestScore() == 2) {
			return ItemBF.WIN42;
		} else if (getFullHomeScore() > getFullGuestScore()) {
			return ItemBF.WIN_OTHER;
		} else if (getFullHomeScore() == 0 && getFullGuestScore() == 0) {
			return ItemBF.DRAW00;
		} else if (getFullHomeScore() == 1 && getFullGuestScore() == 1) {
			return ItemBF.DRAW11;
		} else if (getFullHomeScore() == 2 && getFullGuestScore() == 2) {
			return ItemBF.DRAW22;
		} else if (getFullHomeScore() == 3 && getFullGuestScore() == 3) {
			return ItemBF.DRAW33;
		} else if (getFullHomeScore().equals(getFullGuestScore())) {
			return ItemBF.DRAW_OTHER;
		} else if (getFullHomeScore() == 0 && getFullGuestScore() == 1) {
			return ItemBF.LOSE01;
		} else if (getFullHomeScore() == 0 && getFullGuestScore() == 2) {
			return ItemBF.LOSE02;
		} else if (getFullHomeScore() == 0 && getFullGuestScore() == 3) {
			return ItemBF.LOSE03;
		} else if (getFullHomeScore() == 1 && getFullGuestScore() == 2) {
			return ItemBF.LOSE12;
		} else if (getFullHomeScore() == 1 && getFullGuestScore() == 3) {
			return ItemBF.LOSE13;
		} else if (getFullHomeScore() == 2 && getFullGuestScore() == 3) {
			return ItemBF.LOSE23;
		} else if (getFullHomeScore() == 0 && getFullGuestScore() == 4) {
			return ItemBF.LOSE04;
		} else if (getFullHomeScore() == 1 && getFullGuestScore() == 4) {
			return ItemBF.LOSE14;
		} else if (getFullHomeScore() == 2 && getFullGuestScore() == 4) {
			return ItemBF.LOSE24;
		} else if (getFullHomeScore() < getFullGuestScore()) {
			return ItemBF.LOSE_OTHER;
		} else {
			throw new RuntimeException("数据异常.");
		}
	}

	@Transient
	public ItemBQQSPF getBQQSPFResult() {
		if (this.isCancel())
			return null;
		if (!this.isEnded())
			return null;
		if (this.getFullHomeScore() == null || this.getFullGuestScore() == null)
			return null;
		if (this.getHalfHomeScore() == null || this.getHalfGuestScore() == null)
			return null;

		if (getHalfHomeScore() > getHalfGuestScore()) {
			if (getFullHomeScore() > getFullGuestScore())
				return ItemBQQSPF.WIN_WIN; // 胜胜
			if (getFullHomeScore().equals(getFullGuestScore()))
				return ItemBQQSPF.WIN_DRAW; // 胜平
			else
				return ItemBQQSPF.WIN_LOSE; // 胜负
		} else if (getHalfHomeScore().equals(getHalfGuestScore())) {
			if (getFullHomeScore() > getFullGuestScore())
				return ItemBQQSPF.DRAW_WIN; // 平胜
			if (getFullHomeScore().equals(getFullGuestScore()))
				return ItemBQQSPF.DRAW_DRAW; // 平平
			else
				return ItemBQQSPF.DRAW_LOSE; // 平负
		} else {
			if (getFullHomeScore() > getFullGuestScore())
				return ItemBQQSPF.LOSE_WIN; // 负胜
			if (getFullHomeScore().equals(getFullGuestScore()))
				return ItemBQQSPF.LOSE_DRAW; // 负平
			else
				return ItemBQQSPF.LOSE_LOSE; // 负负
		}
	}

	// cyy-c 2011-04-12 增加单场的截至时间
	/**
	 * todo 复式普通过关模式提前截止时间，单位分钟 public static int
	 * COMPOUND_AHEAD_END_NORMAL_PASS_MODE = 20;
	 * 
	 * 复式多选过关模式提前截止时间，单位分钟 public static int
	 * COMPOUND_AHEAD_END_MULTIPLE_PASS_MODE = 20;//cyy-c 2011-4-12 赛前20分
	 * 
	 * 单式提前截止时间，单位分钟 public static int SINGLE_AHEAD_END = 20;//cyy-c 2011-4-12
	 * 赛前20分
	 */
	@Transient
	public Date getEndSaleTime(SalesMode salesMode) {
		///提前截至时间
		if(null==salesMode)return null;
		if(null==this.getEndSaleTime())return null;
		Integer aheadTime = null;
		switch (salesMode) {
		case COMPOUND:
			for (int m : DczcConstant.AHEAD_END_ARR) {
				if (aheadTime == null || aheadTime > m)
					aheadTime = m;
			}
			break;
		case SINGLE:
			aheadTime = DczcConstant.SINGLE_AHEAD_END;
			break;
		default:
			return null;
		}
		
		
		Calendar c = Calendar.getInstance();
		c.setTime(this.getEndSaleTime());
		c.add(Calendar.MINUTE, -aheadTime);
		return c.getTime();
	}
	@Transient 
	public Date getEndSaleTime() {
		if(null==this.getMatchTime())return null;
		Date matchTime = null;
		Date starDate = DateUtil.strToDate(DateUtil.dateToStr(this.getMatchTime(),"yyyy-MM-dd")+" 06:00");
		Date endDate = DateUtil.strToDate(DateUtil.dateToStr(this.getMatchTime(),"yyyy-MM-dd")+" 08:00");
		if(this.getMatchTime().after(starDate)&&this.getMatchTime().before(endDate)){
			matchTime = starDate;
		}else{
			matchTime = this.getMatchTime();
		}
		return matchTime;
	}
	@Transient
	public String getMatchTimeStr() {
		return matchTimeStr;
	}

	public void setMatchTimeStr(String matchTimeStr) {
		this.matchTimeStr = matchTimeStr;
	}
}
