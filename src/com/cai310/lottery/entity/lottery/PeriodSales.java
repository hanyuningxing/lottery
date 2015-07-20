package com.cai310.lottery.entity.lottery;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesState;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.exception.DataException;

/**
 * 期销售配置.
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "PERIOD_SALES")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheConstant.H_PERIOD_SALES_CACHE_REGION)
public class PeriodSales implements Serializable, CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = 8638545846045169115L;

	/*** 复合主键 */
	protected PeriodSalesId id;

	/*** 销售状态 */
	protected SalesState state;

	/**
	 * 彩种类型
	 * 
	 * @see com.cai310.lottery.common.Lottery
	 */
	private Lottery lotteryType;

	/*** 开售时间 */
	protected Date startTime;

	/*** 合买发起截止时间 */
	protected Date shareEndInitTime;

	/*** 代购发起截止时间 */
	protected Date selfEndInitTime;

	/*** 加入截止时间 */
	protected Date endJoinTime;

	/*** 版本号 */
	protected Integer version;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;

	/* ---------------------- getter and setter method ---------------------- */

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "periodId", column = @Column(name = "periodId", nullable = false)),
			@AttributeOverride(name = "salesMode", column = @Column(name = "salesMode", nullable = false)) })
	public PeriodSalesId getId() {
		return this.id;
	}

	public void setId(PeriodSalesId id) {
		this.id = id;
	}

	@Version
	@Column(name = "version", nullable = false)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SalesState"),
			@Parameter(name = EnumType.TYPE, value = SalesState.SQL_TYPE) })
	@Column(name = "state", nullable = false)
	public SalesState getState() {
		return this.state;
	}

	public void setState(SalesState state) {
		this.state = state;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startTime", length = 23)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "shareEndInitTime", nullable = false, length = 23)
	public Date getShareEndInitTime() {
		return this.shareEndInitTime;
	}

	public void setShareEndInitTime(Date shareEndInitTime) {
		this.shareEndInitTime = shareEndInitTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "selfEndInitTime", nullable = false, length = 23)
	public Date getSelfEndInitTime() {
		return this.selfEndInitTime;
	}

	public void setSelfEndInitTime(Date selfEndInitTime) {
		this.selfEndInitTime = selfEndInitTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "endJoinTime", length = 23, nullable = false)
	public Date getEndJoinTime() {
		return this.endJoinTime;
	}

	public void setEndJoinTime(Date endJoinTime) {
		this.endJoinTime = endJoinTime;
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
	@Column(nullable = false)
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

	/* ---------------------- logic method ---------------------- */
	/**
	 * 检查是否允许发起方案
	 * 
	 * @param shareType 合买或代购
	 * @throws DataException
	 */
	public void checkIsCanInit(ShareType shareType) throws DataException {
		if (!isBeginSaled())
			throw new DataException("未开始销售,请稍候再试. ");
		else if (isSaleEnded())
			throw new DataException("已截止销售.");
		else if (!isOnSale())
			throw new DataException("该销售期不在销售状态. ");

		Date saleEndTime = getSaleEndTime(shareType);
		if (saleEndTime.getTime() < System.currentTimeMillis())
			throw new DataException("已过发起截止时间.");
	}

	/**
	 * 检查是否允许认购或保底方案
	 * 
	 * @throws DataException
	 */
	public void checkIsCanSubscriptionOrBaodi() throws DataException {
		if (!isBeginSaled())
			throw new DataException("未开始销售,请稍候再试. ");
		else if (isSaleEnded())
			throw new DataException("已截止销售.");
		else if (!isOnSale())
			throw new DataException("该销售期不在销售状态. ");

		if (this.endJoinTime.getTime() < System.currentTimeMillis())
			throw new DataException("已过认购截止时间.");
	}

	/**
	 * 检查是否撤销方案
	 * 
	 * @throws DataException
	 */
	public void checkIsCanCancelScheme() throws DataException {
		if (isSaleEnded())
			throw new DataException("已截止销售.");
		else if (!isOnSale())
			throw new DataException("该销售期不在销售状态. ");

		if (this.endJoinTime.getTime() < System.currentTimeMillis())
			throw new DataException("该方案已截止，不能撤销.");
	}

	@Transient
	public boolean isBeginSaled() {
		return this.state.ordinal() >= SalesState.NOT_BEGUN.ordinal();
	}

	@Transient
	public boolean isSaleEnded() {
		return this.state.ordinal() >= SalesState.SALE_ENDED.ordinal();
	}

	public Date getSaleEndTime(ShareType shareType) {
		switch (shareType) {
		case TOGETHER:
			return this.shareEndInitTime;
		case SELF:
			return this.selfEndInitTime;
		}
		return null;
	}

	// ***************************** 附加的对象方法 *****************************
	// public Date getEndJoinTime(boolean printed) {
	// return (printed && this.isCanLazyJoin()) ? this.getPrintedEndJoinTime() :
	// this.getEndJoinTime();
	// }
	//
	/**
	 * @return 是否在售
	 */
	@Transient
	public boolean isOnSale() {
		return this.state == SalesState.ON_SALE;
	}

	/**
	 * 是否已完成销售流程（开售、截止、保底、完成交易）
	 * 
	 * @return 是否已完成销售流程
	 */
	@Transient
	public boolean isSaleFinished() {
		return this.state.ordinal() >= SalesState.PAYMENT_COMMITTED.ordinal();
	}

	//
	// @Transient
	// public Date getEndUploadTime(boolean share) {
	// Date endTime = this.getEndUploadTime();
	// if (endTime != null)
	// return endTime;
	//
	// return share ? this.getShareEndInitTime() : this.getSelfEndInitTime();
	// }
	//
	// @Transient
	// public boolean isPoly() {
	// return LotteryPlatform.isPoly(this.id.getLotteryType());
	// }
	//
	// /**
	// * 是否延迟加入截止时间
	// */
	// @Transient
	// public boolean isCanLazyJoin() {
	// return this.getPrintedEndJoinTime() != null &&
	// this.getPrintedEndJoinTime().after(this.getEndJoinTime());
	// }
	//
	/**
	 * @return 能否开售
	 */
	public boolean canBeginSale() {
		return this.state == SalesState.NOT_BEGUN;
	}

	//
	// /**
	// * @return 能否截止未出票销售
	// */
	// public boolean canUnPrintedEndSale() {
	// return this == SalesState.ON_SALE;
	// }
	//
	/**
	 * @return 能否截止全部销售
	 */
	public boolean canEndSale() {
		return this.state == SalesState.ON_SALE;
	}

	/**
	 * @return 能否执行保底
	 */
	public boolean canRunBaodi() {
		return this.state == SalesState.SALE_ENDED;
	}

	/**
	 * @return 能否执行完成交易
	 */
	public boolean canComminPayment() {
		return this.state.getState() >= SalesState.UN_PRINTED_ENDED.getState()
				&& this.state.getState() < SalesState.PAYMENT_COMMITTED.getState();
	}

	/**
	 * @return 能否结束完成交易
	 */
	public boolean canEndComminPayment() {
		return this.state.getState() >= SalesState.SALE_ENDED.getState();
	}
	//
	// @Transient
	// public SalesState getSaleStatusType() {
	// return SalesState.valueOfStatus(this);
	// }
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
