package com.cai310.lottery.entity.lottery;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.exception.DataException;

@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SALE_ANALYSE")
public class SaleAnalyse extends IdEntity implements CreateMarkable, UpdateMarkable {

	private static final long serialVersionUID = -6380516365102466395L;
	private Long userId;
	private String userName;
	private Long periodId;
	private String periodNumber;
	/** 彩种类型 */
	private Lottery lottery;

	private Integer playType;

	// 方案总数
	private Integer schemeCount;
	// 方案总金额
	private Integer schemeCost;
	// 方案总奖金
	private BigDecimal schemeWonPrize;
	private Integer schemeSuccessCount;
	private Integer schemeSuccessCost;
	private BigDecimal schemeSuccessWonPrize;
	private Integer schemeCancelCount;
	private Integer schemeCancelCost;
	private BigDecimal schemeCancelWonPrize;
	// 加入总数
	private Integer subscriptionCount;
	// 加入总金额
	private BigDecimal subscriptionCost;
	// 加入总奖金
	private BigDecimal subscriptionWonPrize;
	private Integer subscriptionSuccessCount;
	private BigDecimal subscriptionSuccessCost;
	private BigDecimal subscriptionSuccessWonPrize;
	private Integer subscriptionCancelCount;
	private BigDecimal subscriptionCancelCost;
	private BigDecimal subscriptionCancelWonPrize;

	private Integer yearNum;
	private Integer quarterNum;
	private Integer monthNum;
	private Integer weekNum;
	/** 期截止时间 */
	protected Date endedTime;

	private Date createTime;

	private Date lastModifyTime;
	/** 版本号,用于实现乐观锁 */
	private Integer version;

	/** 是否赠送娱乐币 */
	private boolean sendCurrency;
	
	private String schemeIds;
	/**
	 * @return the userId
	 */
	@Column
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	@Column(length = 50)
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the periodNumber
	 */
	@Column(length = 50)
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
	 * @return the periodId
	 */
	@Column
	public Long getPeriodId() {
		return periodId;
	}

	/**
	 * @param periodId the periodId to set
	 */
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	/**
	 * @return the lottery
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = false, updatable = true)
	public Lottery getLottery() {
		return lottery;
	}

	/**
	 * @param lottery the lottery to set
	 */
	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}

	/**
	 * @return the playType
	 */
	@Column
	public Integer getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(Integer playType) {
		this.playType = playType;
	}

	/**
	 * @return the schemeCount
	 */
	@Column
	public Integer getSchemeCount() {
		return schemeCount;
	}

	/**
	 * @param schemeCount the schemeCount to set
	 */
	public void setSchemeCount(Integer schemeCount) {
		this.schemeCount = schemeCount;
	}

	/**
	 * @return the schemeCost
	 */
	
	@Column
	public Integer getSchemeCost() {
		return schemeCost;
	}

	/**
	 * @param schemeCost the schemeCost to set
	 */
	public void setSchemeCost(Integer schemeCost) {
		this.schemeCost = schemeCost;
	}

	/**
	 * @return the schemeWonPrize
	 */
	
	@Column
	public BigDecimal getSchemeWonPrize() {
		return schemeWonPrize;
	}

	/**
	 * @param schemeWonPrize the schemeWonPrize to set
	 */
	public void setSchemeWonPrize(BigDecimal schemeWonPrize) {
		this.schemeWonPrize = schemeWonPrize;
	}

	/**
	 * @return the schemeSuccessCount
	 */
	@Column
	public Integer getSchemeSuccessCount() {
		return schemeSuccessCount;
	}

	/**
	 * @param schemeSuccessCount the schemeSuccessCount to set
	 */
	public void setSchemeSuccessCount(Integer schemeSuccessCount) {
		this.schemeSuccessCount = schemeSuccessCount;
	}

	/**
	 * @return the schemeSuccessCost
	 */
	
	@Column
	public Integer getSchemeSuccessCost() {
		return schemeSuccessCost;
	}

	/**
	 * @param schemeSuccessCost the schemeSuccessCost to set
	 */
	public void setSchemeSuccessCost(Integer schemeSuccessCost) {
		this.schemeSuccessCost = schemeSuccessCost;
	}

	/**
	 * @return the schemeSuccessWonPrize
	 */
	
	@Column
	public BigDecimal getSchemeSuccessWonPrize() {
		return schemeSuccessWonPrize;
	}

	/**
	 * @param schemeSuccessWonPrize the schemeSuccessWonPrize to set
	 */
	public void setSchemeSuccessWonPrize(BigDecimal schemeSuccessWonPrize) {
		this.schemeSuccessWonPrize = schemeSuccessWonPrize;
	}

	/**
	 * @return the schemeCancelCount
	 */
	@Column
	public Integer getSchemeCancelCount() {
		return schemeCancelCount;
	}

	/**
	 * @param schemeCancelCount the schemeCancelCount to set
	 */
	public void setSchemeCancelCount(Integer schemeCancelCount) {
		this.schemeCancelCount = schemeCancelCount;
	}

	/**
	 * @return the schemeCancelCost
	 */
	
	@Column
	public Integer getSchemeCancelCost() {
		return schemeCancelCost;
	}

	/**
	 * @param schemeCancelCost the schemeCancelCost to set
	 */
	public void setSchemeCancelCost(Integer schemeCancelCost) {
		this.schemeCancelCost = schemeCancelCost;
	}

	/**
	 * @return the schemeCancelWonPrize
	 */
	
	@Column
	public BigDecimal getSchemeCancelWonPrize() {
		return schemeCancelWonPrize;
	}

	/**
	 * @param schemeCancelWonPrize the schemeCancelWonPrize to set
	 */
	public void setSchemeCancelWonPrize(BigDecimal schemeCancelWonPrize) {
		this.schemeCancelWonPrize = schemeCancelWonPrize;
	}

	/**
	 * @return the subscriptionCount
	 */
	@Column
	public Integer getSubscriptionCount() {
		return subscriptionCount;
	}

	/**
	 * @param subscriptionCount the subscriptionCount to set
	 */
	public void setSubscriptionCount(Integer subscriptionCount) {
		this.subscriptionCount = subscriptionCount;
	}

	/**
	 * @return the subscriptionCost
	 */
	
	@Column
	public BigDecimal getSubscriptionCost() {
		return subscriptionCost;
	}

	/**
	 * @param subscriptionCost the subscriptionCost to set
	 */
	public void setSubscriptionCost(BigDecimal subscriptionCost) {
		this.subscriptionCost = subscriptionCost;
	}

	/**
	 * @return the subscriptionWonPrize
	 */
	
	@Column
	public BigDecimal getSubscriptionWonPrize() {
		return subscriptionWonPrize;
	}

	/**
	 * @param subscriptionWonPrize the subscriptionWonPrize to set
	 */
	public void setSubscriptionWonPrize(BigDecimal subscriptionWonPrize) {
		this.subscriptionWonPrize = subscriptionWonPrize;
	}

	/**
	 * @return the subscriptionSuccessCount
	 */
	@Column
	public Integer getSubscriptionSuccessCount() {
		return subscriptionSuccessCount;
	}

	/**
	 * @param subscriptionSuccessCount the subscriptionSuccessCount to set
	 */
	public void setSubscriptionSuccessCount(Integer subscriptionSuccessCount) {
		this.subscriptionSuccessCount = subscriptionSuccessCount;
	}

	/**
	 * @return the subscriptionSuccessCost
	 */
	
	@Column
	public BigDecimal getSubscriptionSuccessCost() {
		return subscriptionSuccessCost;
	}

	/**
	 * @param subscriptionSuccessCost the subscriptionSuccessCost to set
	 */
	public void setSubscriptionSuccessCost(BigDecimal subscriptionSuccessCost) {
		this.subscriptionSuccessCost = subscriptionSuccessCost;
	}

	/**
	 * @return the subscriptionSuccessWonPrize
	 */
	
	@Column
	public BigDecimal getSubscriptionSuccessWonPrize() {
		return subscriptionSuccessWonPrize;
	}

	/**
	 * @param subscriptionSuccessWonPrize the subscriptionSuccessWonPrize to set
	 */
	public void setSubscriptionSuccessWonPrize(BigDecimal subscriptionSuccessWonPrize) {
		this.subscriptionSuccessWonPrize = subscriptionSuccessWonPrize;
	}

	/**
	 * @return the subscriptionCancelCount
	 */
	@Column
	public Integer getSubscriptionCancelCount() {
		return subscriptionCancelCount;
	}

	/**
	 * @param subscriptionCancelCount the subscriptionCancelCount to set
	 */
	public void setSubscriptionCancelCount(Integer subscriptionCancelCount) {
		this.subscriptionCancelCount = subscriptionCancelCount;
	}

	/**
	 * @return the subscriptionCancelCost
	 */
	
	@Column
	public BigDecimal getSubscriptionCancelCost() {
		return subscriptionCancelCost;
	}

	/**
	 * @param subscriptionCancelCost the subscriptionCancelCost to set
	 */
	public void setSubscriptionCancelCost(BigDecimal subscriptionCancelCost) {
		this.subscriptionCancelCost = subscriptionCancelCost;
	}

	/**
	 * @return the subscriptionCancelWonPrize
	 */
	
	@Column
	public BigDecimal getSubscriptionCancelWonPrize() {
		return subscriptionCancelWonPrize;
	}

	/**
	 * @param subscriptionCancelWonPrize the subscriptionCancelWonPrize to set
	 */
	public void setSubscriptionCancelWonPrize(BigDecimal subscriptionCancelWonPrize) {
		this.subscriptionCancelWonPrize = subscriptionCancelWonPrize;
	}

	/**
	 * @return the yearNum
	 */
	@Column
	public Integer getYearNum() {
		return yearNum;
	}

	/**
	 * @param yearNum the yearNum to set
	 */
	public void setYearNum(Integer yearNum) {
		this.yearNum = yearNum;
	}

	/**
	 * @return the quarterNum
	 */
	@Column
	public Integer getQuarterNum() {
		return quarterNum;
	}

	/**
	 * @param quarterNum the quarterNum to set
	 */
	public void setQuarterNum(Integer quarterNum) {
		this.quarterNum = quarterNum;
	}

	/**
	 * @return the monthNum
	 */
	@Column
	public Integer getMonthNum() {
		return monthNum;
	}

	/**
	 * @param monthNum the monthNum to set
	 */
	public void setMonthNum(Integer monthNum) {
		this.monthNum = monthNum;
	}

	/**
	 * @return the weekNum
	 */
	@Column
	public Integer getWeekNum() {
		return weekNum;
	}

	/**
	 * @param weekNum the weekNum to set
	 */
	public void setWeekNum(Integer weekNum) {
		this.weekNum = weekNum;
	}

	/**
	 * @return the endedTime
	 */
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
	 * @return the version
	 */
	@Version
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
	 * @return the sendCurrency
	 */
	public boolean isSendCurrency() {
		return sendCurrency;
	}

	/**
	 * @param sendCurrency the sendCurrency to set
	 */
	public void setSendCurrency(boolean sendCurrency) {
		this.sendCurrency = sendCurrency;
	}

	@Column
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;

	}

	public void addSchemeCount(Integer num) throws DataException {
		if (num == null || num <= 0)
			throw new DataException("增加不能为空或小于等于零.");

		this.schemeCount = (this.schemeCount == null) ? num : this.schemeCount + num;
	}

	public void addSchemeCost(Integer num) throws DataException {
		if (num == null || num <= 0)
			throw new DataException("增加不能为空或小于等于零.");

		this.schemeCost = (this.schemeCost == null) ? num : this.schemeCost + num;
	}

	public void addSchemeWonPrize(BigDecimal num) throws DataException {
		if (num == null || num.doubleValue() <= 0)
			return;
		this.schemeWonPrize = (this.schemeWonPrize == null) ? num : this.schemeWonPrize.add(num);
	}

	public void addSchemeSuccessCount(Integer num) throws DataException {
		if (num == null || num <= 0)
			throw new DataException("增加不能为空或小于等于零.");

		this.schemeSuccessCount = (this.schemeSuccessCount == null) ? num : this.schemeSuccessCount + num;
	}

	public void addSchemeSuccessCost(Integer num) throws DataException {
		if (num == null || num <= 0)
			throw new DataException("增加不能为空或小于等于零.");

		this.schemeSuccessCost = (this.schemeSuccessCost == null) ? num : this.schemeSuccessCost + num;
	}

	public void addSchemeSuccessWonPrize(BigDecimal num) throws DataException {
		if (num == null || num.doubleValue() <= 0)
			return;
		this.schemeSuccessWonPrize = (this.schemeSuccessWonPrize == null) ? num : this.schemeSuccessWonPrize.add(num);
	}

	public void addSchemeCancelCount(Integer num) throws DataException {
		if (num == null || num <= 0)
			throw new DataException("增加不能为空或小于等于零.");

		this.schemeCancelCount = (this.schemeCancelCount == null) ? num : this.schemeCancelCount + num;
	}

	public void addSchemeCancelCost(Integer num) throws DataException {
		if (num == null || num <= 0)
			throw new DataException("增加不能为空或小于等于零.");

		this.schemeCancelCost = (this.schemeCancelCost == null) ? num : this.schemeCancelCost + num;
	}

	public void addSchemeCancelWonPrize(BigDecimal num) throws DataException {
		if (num == null || num.doubleValue() <= 0)
			return;
		this.schemeCancelWonPrize = (this.schemeCancelWonPrize == null) ? num : this.schemeCancelWonPrize.add(num);
	}

	// ////////////////////加入
	public void addSubscriptionCount(Integer num) throws DataException {
		if (num == null || num <= 0)
			throw new DataException("增加不能为空或小于等于零.");

		this.subscriptionCount = (this.subscriptionCount == null) ? num : this.subscriptionCount + num;
	}

	public void addSubscriptionCost(BigDecimal num) throws DataException {
		if (num == null || num.doubleValue() <= 0)
			throw new DataException("增加不能为空或小于等于零.");

		this.subscriptionCost = (this.subscriptionCost == null) ? num : this.subscriptionCost.add(num);
	}

	public void addSubscriptionWonPrize(BigDecimal num) throws DataException {
		if (num == null || num.doubleValue() <= 0)
			return;
		this.subscriptionWonPrize = (this.subscriptionWonPrize == null) ? num : this.subscriptionWonPrize.add(num);
	}

	public void addSubscriptionSuccessCount(Integer num) throws DataException {
		if (num == null || num <= 0)
			throw new DataException("增加不能为空或小于等于零.");

		this.subscriptionSuccessCount = (this.subscriptionSuccessCount == null) ? num : this.subscriptionSuccessCount
				+ num;
	}

	public void addSubscriptionSuccessCost(BigDecimal num) throws DataException {
		if (num == null || num.doubleValue() <= 0)
			throw new DataException("增加不能为空或小于等于零.");

		this.subscriptionSuccessCost = (this.subscriptionSuccessCost == null) ? num : this.subscriptionSuccessCost
				.add(num);
	}

	public void addSubscriptionSuccessWonPrize(BigDecimal num) throws DataException {
		if (num == null || num.doubleValue() <= 0)
			return;
		this.subscriptionSuccessWonPrize = (this.subscriptionSuccessWonPrize == null) ? num
				: this.subscriptionSuccessWonPrize.add(num);
	}

	public void addSubscriptionCancelCount(Integer num) throws DataException {
		if (num == null || num <= 0)
			throw new DataException("增加不能为空或小于等于零.");

		this.subscriptionCancelCount = (this.subscriptionCancelCount == null) ? num : this.subscriptionCancelCount
				+ num;
	}

	public void addSubscriptionCancelCost(BigDecimal num) throws DataException {
		if (num == null || num.doubleValue() <= 0)
			throw new DataException("增加不能为空或小于等于零.");

		this.subscriptionCancelCost = (this.subscriptionCancelCost == null) ? num : this.subscriptionCancelCost
				.add(num);
	}

	public void addSubscriptionCancelWonPrize(BigDecimal num) throws DataException {
		if (num == null || num.doubleValue() <= 0)
			return;
		this.subscriptionCancelWonPrize = (this.subscriptionCancelWonPrize == null) ? num
				: this.subscriptionCancelWonPrize.add(num);
	}
	@Lob
	@Column
	public String getSchemeIds() {
		return schemeIds;
	}

	public void setSchemeIds(String schemeIds) {
		this.schemeIds = schemeIds;
	}

}
