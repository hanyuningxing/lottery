package com.cai310.lottery.entity.lottery;

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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.Constant;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.AfterSaleFlag;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PeriodState;
import com.cai310.lottery.exception.DataException;
import com.cai310.utils.DateUtil;

/**
 * 销售期实体基类
 * 
 */

@Entity
@Table(name = Constant.LOTTERY_TABLE_PREFIX + "PERIOD")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_PERIOD_CACHE_REGION)
public class Period extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = -6413216139697348459L;

	/** 彩种类型 */
	private Lottery lotteryType;

	/** 期号 */
	protected String periodNumber;

	/**
	 * 期结束后操作标志
	 * 
	 * @see com.cai310.lottery.common.AfterSaleFlag
	 */
	protected Integer afterSaleFlags = 0;

	/**
	 * 完全结束后操作标志
	 * 
	 * @see com.cai310.lottery.common.AfterFinishFlag
	 */
	protected Integer afterFinishFlags = 0;

	/**
	 * 期状态
	 * 
	 * @see com.cai310.lottery.common.PeriodState
	 * 
	 */
	protected PeriodState state;

	/** 开奖标志 */
	protected boolean drawed;

	/** 当前期标志 */
	protected boolean current;

	/** 过关统计标志 */
	protected boolean passcount;

	/** 上一期ID */
	protected Long prevPreriodId;

	/** 下一期ID */
	protected Long nextPreriodId;

	/** 期截止时间 */
	protected Date endedTime;

	/** 开奖时间 */
	protected Date prizeTime;

	/** 期开始时间 */
	protected Date startTime;

	/** 备注 */
	protected String remark;

	/** 版本号 */
	protected Integer version;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;

	/** 期号前台显示 ，非数据库字段 */
	protected Integer periodNumberDisplay;

	/* ---------------------- getter and setter method ---------------------- */

	/**
	 * @return the periodNumber
	 */
	@Column(length = 10, nullable = false)
	public String getPeriodNumber() {
		return periodNumber;
	}

	/**
	 * @param periodNumber the periodNumber to set
	 */
	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	/**
	 * @return the afterSalFlags
	 *         {@link com.cai310.lottery.common.AfterFinishFlag}
	 */
	@Column
	public Integer getAfterSaleFlags() {
		return afterSaleFlags;
	}

	/**
	 * @param afterSalFlags the afterSalFlags to set
	 */
	public void setAfterSaleFlags(Integer afterSaleFlags) {
		this.afterSaleFlags = afterSaleFlags;
	}

	/**
	 * @return the afterFinishFlags
	 */
	@Column
	public Integer getAfterFinishFlags() {
		return afterFinishFlags;
	}

	/**
	 * @param afterFinishFlags the afterFinishFlags to set
	 */
	public void setAfterFinishFlags(Integer afterFinishFlags) {
		this.afterFinishFlags = afterFinishFlags;
	}

	/**
	 * @return the state
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.PeriodState"),
			@Parameter(name = EnumType.TYPE, value = PeriodState.SQL_TYPE) })
	@Column(nullable = false)
	public PeriodState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(PeriodState state) {
		this.state = state;
	}

	/**
	 * @return the drawed
	 */
	@Column(nullable = false)
	public boolean isDrawed() {
		return drawed;
	}

	/**
	 * @param drawed the drawed to set
	 */
	public void setDrawed(boolean drawed) {
		this.drawed = drawed;
	}

	/**
	 * @return the current
	 */
	@Column(name = "current_period", nullable = false)
	public boolean isCurrent() {
		return current;
	}

	/**
	 * @param current the current to set
	 */
	public void setCurrent(boolean current) {
		this.current = current;
	}

	/**
	 * @return the prevPreriodId
	 */
	@Column
	public Long getPrevPreriodId() {
		return prevPreriodId;
	}

	/**
	 * @param prevPreriodId the prevPreriodId to set
	 */
	public void setPrevPreriodId(Long prevPreriodId) {
		this.prevPreriodId = prevPreriodId;
	}

	/**
	 * @return the nextPreriodId
	 */
	@Column
	public Long getNextPreriodId() {
		return nextPreriodId;
	}

	/**
	 * @param nextPreriodId the nextPreriodId to set
	 */
	public void setNextPreriodId(Long nextPreriodId) {
		this.nextPreriodId = nextPreriodId;
	}

	/**
	 * @return the endedTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getEndedTime() {
		return endedTime;
	}

	/**
	 * @param endedTime the endedTime to set
	 */
	public void setEndedTime(Date endedTime) {
		this.endedTime = endedTime;
	}

	/**
	 * @return the prizeTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getPrizeTime() {
		return prizeTime;
	}

	/**
	 * @param prizeTime the prizeTime to set
	 */
	public void setPrizeTime(Date prizeTime) {
		this.prizeTime = prizeTime;
	}

	/**
	 * @return the startTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the remark
	 */
	@Column(length = 200)
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the version
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
	 * 彩种类型
	 * 
	 * @return 彩种类型
	 * @see com.cai310.lottery.common.Lottery
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = false, updatable = true)
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * 设置彩种类型
	 * 
	 * @param lotteryType 彩种类型
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	/* ------------------------- logic method ------------------------- */
	/**
	 * 检查是否能发起方案
	 * 
	 * @throws DataException
	 */
	public void checkIsCanInit() throws DataException {
		if (isPaused())
			throw new DataException("销售暂停中,请稍候再试.");
		else if (isSaleEnded() || isFinished())
			throw new DataException("已截止销售.");
		else if (!isOnSale())
			throw new DataException("该销售期不在销售状态.");
	}

	/**
	 * 检查是否能发起方案
	 * 
	 * @throws DataException
	 */
	public void checkIsCanSubscriptionOrBaodi() throws DataException {
		if (isPaused())
			throw new DataException("销售暂停中,请稍候再试.");
		else if (isSaleEnded() || isFinished())
			throw new DataException("已截止销售.");
		else if (!isOnSale())
			throw new DataException("该销售期不在销售状态.");
	}

	/**
	 * 判断是否在售
	 * 
	 * @return 是否在售
	 */
	@Transient
	public boolean isOnSale() {
		return state == PeriodState.Started;
	}

	@Transient
	public boolean isPaused() {
		return this.state == PeriodState.Paused;
	}

	@Transient
	public boolean isSaleEnded() {
		return this.state.ordinal() >= PeriodState.SaleEnded.ordinal();
	}

	@Transient
	public boolean isFinished() {
		return this.state.ordinal() >= PeriodState.Finished.ordinal();
	}

	@Transient
	public boolean isStarted() {
		return this.state.ordinal() >= PeriodState.Started.ordinal();
	}

	@Transient
	public boolean isResultUpdate() {
		return checkAfterSaleFlag(AfterSaleFlag.RESULT_UPDATED.getFlagValue());
	}

	@Transient
	public boolean isPrizeUpdate() {
		return checkAfterSaleFlag(AfterSaleFlag.PRIZE_UPDATED.getFlagValue());
	}

	public boolean checkAfterSaleFlag(int afterSaleFlag) {
		int flags = (this.getAfterSaleFlags() != null) ? this.getAfterSaleFlags() : 0;
		return (flags & afterSaleFlag) > 0;
	}

	@Transient
	public boolean isPrizeDelived() {
		return (this.getAfterSaleFlags() & AfterSaleFlag.PRIZE_DELIVED.getFlagValue()) != 0;
	}

	public boolean checkAfterFinishFlags(int afterFinishFlags) {
		int flags = (this.afterFinishFlags != null) ? this.afterFinishFlags : 0;
		return (flags & afterFinishFlags) > 0;
	}

	public void addAfterFinishFlags(int afterFinishFlags) {
		int flags = (this.afterFinishFlags != null) ? this.afterFinishFlags : 0;
		this.afterFinishFlags = flags | afterFinishFlags;
	}

	public void addAfterSaleFlags(int afterSaleFlags) {
		int flags = (this.afterSaleFlags != null) ? this.afterSaleFlags : 0;
		this.afterSaleFlags = flags | afterSaleFlags;
	}

	/**
	 * @return the periodNumberDisplay
	 */
	@Transient
	public Integer getPeriodNumberDisplay() {
		return periodNumberDisplay;
	}
	@Transient
	/**
	 * 
	 */
	public Date getEndPrizeTime() {
		if(null==this.prizeTime){
			return null;
		}
		return DateUtil.calDate(this.prizeTime, 0, 2, 0);
	}
	/**
	 * @param periodNumberDisplay the periodNumberDisplay to set
	 */
	public void setPeriodNumberDisplay(Integer periodNumberDisplay) {
		this.periodNumberDisplay = periodNumberDisplay;
	}

	/**
	 * @return the passcount
	 */
	@Column(name = "passcount")
	public boolean isPasscount() {
		return passcount;
	}

	/**
	 * @param passcount the passcount to set
	 */
	public void setPasscount(boolean passcount) {
		this.passcount = passcount;
	}
}
