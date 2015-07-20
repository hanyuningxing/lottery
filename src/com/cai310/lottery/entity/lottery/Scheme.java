package com.cai310.lottery.entity.lottery;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.Constant;
import com.cai310.lottery.common.AgentAnalyseState;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.common.PlatformInfo;
import com.cai310.lottery.common.SaleAnalyseState;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.SecretType;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.SubscriptionLicenseType;
import com.cai310.lottery.common.TicketSchemeState;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.prizeutils.template.VariableString;
import com.cai310.lottery.support.PrizeItem;
import com.cai310.lottery.support.WinItem;
import com.cai310.lottery.utils.BigDecimalUtil;
import com.cai310.utils.DateUtil;
import com.cai310.utils.HtmlTagFilter;
import com.cai310.utils.JsonUtil;

/**
 * <b>彩票方案基类.</b>
 * <p>
 * <strong>注：其中某些字段设置了[updatable = false], 执行更新操作时这些字段不会更新到数据库.</strong>
 * </p>
 */
@MappedSuperclass
public abstract class Scheme extends IdEntity implements CreateMarkable, UpdateMarkable {
	private static final long serialVersionUID = -1965967474872427824L;

	/** 方案描述的最大长度,一个汉字占两个字符 */
	public static final int DESCRIPTION_MAX_LENGTH = 500;

	/* ---------------------- 属性 ----------------------- */

	/** 期编号 */
	private Long periodId;

	/** 期号 */
	private String periodNumber;

	/** 方案发起人的用户编号 */
	private Long sponsorId;

	/** 方案发起人的用户名 */
	private String sponsorName;

	/** 方案描述 */
	private String description;

	/** 方案内容是否已上传 */
	private boolean uploaded;

	/** 方案内容上传时间 */
	private Date uploadTime;

	/** 方案注数（单倍注数） */
	private Integer units;

	/** 倍数 */
	private Integer multiple;

	/** 方案金额 */
	protected Integer schemeCost;

	/** 方案进度 */
	private Float progressRate;

	/** 已认购金额 */
	private BigDecimal subscribedCost;

	/** 保底金额 */
	private BigDecimal baodiCost;

	/** 方案内容 */
	private String content;

	/**
	 * 方案投注的方式
	 * 
	 * @see com.cai310.lottery.common.SalesMode
	 */
	private SalesMode mode;

	/**
	 * 方案分享类型
	 * 
	 * @see com.cai310.lottery.common.ShareType
	 */
	private ShareType shareType;

	/**
	 * 方案保密类型
	 * 
	 * @see com.cai310.lottery.common.SecretType
	 */
	private SecretType secretType;

	/**
	 * 方案状态
	 * 
	 * @see com.cai310.lottery.common.SchemeState
	 */
	private SchemeState state;

	/**
	 * 认购许可类型
	 * 
	 * @see com.cai310.lottery.common.SubscriptionLicenseType
	 */
	private SubscriptionLicenseType subscriptionLicenseType;

	/** 认购密码 */
	private String subscriptionPassword;

	/** 最小认购金额 */
	private BigDecimal minSubscriptionCost;

	/** 自动跟单是否已完成,如果没有跟单,设为true */
	private boolean autoFollowCompleted;

	/** 方案备注 */
	private String remark;

	/**
	 * 方案中奖更新状态
	 * 
	 * @see com.cai310.lottery.common.WinningUpdateStatus
	 */
	private WinningUpdateStatus winningUpdateStatus;

	/** 是否中奖 */
	private boolean won;

	/** 中奖详情 */
	private String wonDetail;

	/** 奖金是否已派发 */
	private boolean prizeSended;

	/** 奖金详情 */
	private String prizeDetail;

	/** 税前奖金 */
	private BigDecimal prize;

	/** 税后奖金 */
	private BigDecimal prizeAfterTax;

	/** 版本号,用于实现乐观锁 */
	private Integer version;

	/** 方案发起人的佣金率，值应小于1 */
	private Float commissionRate;

	/** 方案是否已发送去打印 */
	private boolean sendToPrint;

	/** 创建时间 */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;

	/** 是否保留(当方案被保留，执行保底、完成交易、赠送积分、更新中奖等等操作时，该方案将不会被执行) */
	private boolean reserved;

	/** 交易ID */
	private Long transactionId;

	/** 完成交易时间 --方案发起的是时候为结束出票时间*/
	private Date commitTime;

	/** 派奖时间 */
	private Date prizeSendTime;

	/** 方案出票状态 */
	private SchemePrintState schemePrintState;

	private List<Subscription> subscriptions = new ArrayList<Subscription>();

	private Byte orderPriority = Constant.ORDER_PRIORITY_DEFAULT;
	
	/**是否完成同步出票*/
	private boolean scheme_synchroned;
	
	/** 接票ID(订单号)*/
	private String orderId;
	/** 方案来源*/
	private PlatformInfo platform;
	/**
	 * 接票非数据字段
	 */
    private TicketSchemeState ticketSchemeState;
    
    /**
	 * 销售统计状态
	 */
    private SaleAnalyseState saleAnalyseState = SaleAnalyseState.NONE;
    
    /**
   	 * 佣金统计状态
   	 */
    private AgentAnalyseState agentAnalyseState = AgentAnalyseState.NONE;
    
    /**用户中奖排行统计，保存当前统计日期(20131125)*/
    private Integer rankFlag;
    
    /**用户战绩统计*/
    private boolean gradeFlag;

	/* ---------------------- getter and setter method ----------------------- */

	/**
	 * @return {@link #orderPriority}
	 */
	@Column(nullable = false)
	public Byte getOrderPriority() {
		return orderPriority;
	}

	/**
	 * @param orderPriority the {@link #orderPriority} to set
	 */
	public void setOrderPriority(Byte orderPriority) {
		this.orderPriority = orderPriority;
	}

	/**
	 * @return {@link #subscriptions}
	 */

	@OneToMany
	@JoinColumn(name = "schemeId")
	@Fetch(FetchMode.SUBSELECT)
	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	/**
	 * @param subscriptions the {@link #subscriptions} to set
	 */
	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	/**
	 * @return {@link #periodId}
	 */
	@Column(nullable = false, updatable = false)
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
	 * @return {@link #periodNumber}
	 */
	@Column(nullable = false, length = 20, updatable = false)
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
	 * @return {@link #sponsorId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getSponsorId() {
		return sponsorId;
	}

	/**
	 * @param sponsorId the sponsorId to set
	 */
	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	/**
	 * @return {@link #sponsorName}
	 */
	@Column(nullable = false, length = 20, updatable = false)
	public String getSponsorName() {
		return sponsorName;
	}

	/**
	 * @param sponsorName the sponsorName to set
	 */
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	/**
	 * @return {@link #description}
	 */
	@Column(length = DESCRIPTION_MAX_LENGTH, updatable = false)
	public String getDescription() {
		return HtmlTagFilter.Html2Text(description);
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return {@link #uploaded}
	 */
	@Column(nullable = false)
	public boolean isUploaded() {
		return uploaded;
	}

	/**
	 * @param uploaded the uploaded to set
	 */
	public void setUploaded(boolean uploaded) {
		this.uploaded = uploaded;
	}

	/**
	 * @return {@link #uploadTime}
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getUploadTime() {
		return uploadTime;
	}

	/**
	 * @param uploadTime the uploadTime to set
	 */
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * @return {@link #units}
	 */
	@Column
	public Integer getUnits() {
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(Integer units) {
		this.units = units;
	}

	/**
	 * @return {@link #multiple}
	 */
	@Column
	public Integer getMultiple() {
		return multiple;
	}

	/**
	 * @param multiple the multiple to set
	 */
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	/**
	 * @return {@link #schemeCost}
	 */
	
	@Column(nullable = false, updatable = false)
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
	 * @return {@link #progressRate}
	 */
	@Column
	public Float getProgressRate() {
		return progressRate;
	}

	/**
	 * @param progressRate the progressRate to set
	 */
	public void setProgressRate(Float progressRate) {
		this.progressRate = progressRate;
	}

	/**
	 * @return {@link #subscribedCost}
	 */
	
	@Column
	public BigDecimal getSubscribedCost() {
		return subscribedCost;
	}

	/**
	 * @param subscribedCost the subscribedCost to set
	 */
	public void setSubscribedCost(BigDecimal subscribedCost) {
		this.subscribedCost = subscribedCost;
	}

	/**
	 * @return {@link #baodiCost}
	 */
	
	@Column
	public BigDecimal getBaodiCost() {
		return baodiCost;
	}

	/**
	 * @param baodiCost the baodiCost to set
	 */
	public void setBaodiCost(BigDecimal baodiCost) {
		this.baodiCost = baodiCost;
	}

	/**
	 * @return {@link #shareType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.ShareType"),
			@Parameter(name = EnumType.TYPE, value = ShareType.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public ShareType getShareType() {
		return shareType;
	}

	/**
	 * @param shareType the shareType to set
	 */
	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}

	/**
	 * @return {@link #secretType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SecretType"),
			@Parameter(name = EnumType.TYPE, value = SecretType.SQL_TYPE) })
	@Column(nullable = false)
	public SecretType getSecretType() {
		return secretType;
	}

	/**
	 * @param secretType the secretType to set
	 */
	public void setSecretType(SecretType secretType) {
		this.secretType = secretType;
	}

	/**
	 * @return {@link #state}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SchemeState"),
			@Parameter(name = EnumType.TYPE, value = SchemeState.SQL_TYPE) })
	@Column(nullable = false)
	public SchemeState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(SchemeState state) {
		this.state = state;
	}

	/**
	 * @return {@link #subscriptionLicenseType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SubscriptionLicenseType"),
			@Parameter(name = EnumType.TYPE, value = SubscriptionLicenseType.SQL_TYPE) })
	@Column(nullable = false)
	public SubscriptionLicenseType getSubscriptionLicenseType() {
		return subscriptionLicenseType;
	}

	/**
	 * @param subscriptionLicenseType the subscriptionLicenseType to set
	 */
	public void setSubscriptionLicenseType(SubscriptionLicenseType subscriptionLicenseType) {
		this.subscriptionLicenseType = subscriptionLicenseType;
	}

	/**
	 * @return {@link #subscriptionPassword}
	 */
	@Column(length = 10)
	public String getSubscriptionPassword() {
		return subscriptionPassword;
	}

	/**
	 * @param subscriptionPassword the subscriptionPassword to set
	 */
	public void setSubscriptionPassword(String subscriptionPassword) {
		this.subscriptionPassword = subscriptionPassword;
	}

	/**
	 * @return {@link #minSubscriptionCost}
	 */
	
	@Column
	public BigDecimal getMinSubscriptionCost() {
		return minSubscriptionCost;
	}

	/**
	 * @param minSubscriptionCost the minSubscriptionCost to set
	 */
	public void setMinSubscriptionCost(BigDecimal minSubscriptionCost) {
		this.minSubscriptionCost = minSubscriptionCost;
	}

	/**
	 * @return {@link #autoFollowCompleted}
	 */
	@Column(nullable = false)
	public boolean isAutoFollowCompleted() {
		return autoFollowCompleted;
	}

	/**
	 * @param autoFollowCompleted the autoFollowCompleted to set
	 */
	public void setAutoFollowCompleted(boolean autoFollowCompleted) {
		this.autoFollowCompleted = autoFollowCompleted;
	}

	/**
	 * @return {@link #remark}
	 */
	@Column(length = 500)
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
	 * @return {@link #content}
	 */
	@Lob
	@Column(name = "content")
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return {@link #winningUpdateStatus}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.WinningUpdateStatus"),
			@Parameter(name = EnumType.TYPE, value = WinningUpdateStatus.SQL_TYPE) })
	@Column(nullable = false)
	public WinningUpdateStatus getWinningUpdateStatus() {
		return winningUpdateStatus;
	}

	/**
	 * @param winningUpdateStatus the winningUpdateStatus to set
	 */
	public void setWinningUpdateStatus(WinningUpdateStatus winningUpdateStatus) {
		this.winningUpdateStatus = winningUpdateStatus;
	}

	/**
	 * @return {@link #won}
	 */
	@Column(nullable = false)
	public boolean isWon() {
		return won;
	}

	/**
	 * @param won the won to set
	 */
	public void setWon(boolean won) {
		this.won = won;
	}

	/**
	 * @return {@link #prizeSended}
	 */
	@Column(nullable = false)
	public boolean isPrizeSended() {
		return prizeSended;
	}

	/**
	 * @param prizeSended the cut to set
	 */
	public void setPrizeSended(boolean prizeSended) {
		this.prizeSended = prizeSended;
	}

	/**
	 * @return {@link #version}
	 */
	@Version
	@Column(name = "version", nullable = false)
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
	 * @return {@link #commissionRate}
	 */
	@Column
	public Float getCommissionRate() {
		return commissionRate;
	}

	/**
	 * @param commissionRate the commissionRate to set
	 */
	public void setCommissionRate(Float commissionRate) {
		this.commissionRate = commissionRate;
	}
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.PlatformInfo"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column(updatable = false)
	public PlatformInfo getPlatform() {
		return platform;
	}

	public void setPlatform(PlatformInfo platform) {
		this.platform = platform;
	}

	/**
	 * @return {@link #mode}
	 */

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SalesMode"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column(name = "sales_mode", nullable = false, updatable = false)
	public SalesMode getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(SalesMode mode) {
		this.mode = mode;
	}
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SaleAnalyseState"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column
	public SaleAnalyseState getSaleAnalyseState() {
		return saleAnalyseState;
	}

	public void setSaleAnalyseState(SaleAnalyseState saleAnalyseState) {
		this.saleAnalyseState = saleAnalyseState;
	}
	/**
	 * @return {@link #sendToPrint}
	 */
	@Column(nullable = false)
	public boolean isSendToPrint() {
		return sendToPrint;
	}

	/**
	 * @param sendToPrint the sendToPrint to set
	 */
	public void setSendToPrint(boolean sendToPrint) {
		this.sendToPrint = sendToPrint;
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
	 * @return {@link #reserved}
	 */
	@Column(nullable = false)
	public boolean isReserved() {
		return reserved;
	}

	/**
	 * @param reserved the {@link #reserved} to set
	 */
	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	/**
	 * @return {@link #wonDetail}
	 */
	@Lob
	@Column(insertable = false)
	public String getWonDetail() {
		return wonDetail;
	}

	/**
	 * @param wonDetail the {@link #wonDetail} to set
	 */
	public void setWonDetail(String wonDetail) {
		this.wonDetail = wonDetail;
	}

	/**
	 * @return {@link #prizeDetail}
	 */
	@Lob
	@Column(insertable = false)
	public String getPrizeDetail() {
		return prizeDetail;
	}

	/**
	 * @param prizeDetail the {@link #prizeDetail} to set
	 */
	public void setPrizeDetail(String prizeDetail) {
		this.prizeDetail = prizeDetail;
	}

	/**
	 * @return {@link #prize}
	 */
	
	@Column(insertable = false)
	public BigDecimal getPrize() {
		return prize;
	}

	/**
	 * @param prize the {@link #prize} to set
	 */
	public void setPrize(BigDecimal prize) {
		this.prize = prize;
	}

	/**
	 * @return {@link #prizeAfterTax}
	 */
	
	@Column(insertable = false)
	public BigDecimal getPrizeAfterTax() {
		return prizeAfterTax;
	}

	/**
	 * @param prizeAfterTax the {@link #prizeAfterTax} to set
	 */
	public void setPrizeAfterTax(BigDecimal prizeAfterTax) {
		this.prizeAfterTax = prizeAfterTax;
	}

	/**
	 * @return {@link #transactionId}
	 */
	@Column(nullable = false, updatable = false)
	public Long getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the {@link #transactionId} to set
	 */
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return #{@link commitTime}
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getCommitTime() {
		return commitTime;
	}

	/**
	 * @param commitTime the commitTime to set
	 */
	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	/**
	 * @return #{@link prizeSendTime}
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getPrizeSendTime() {
		return prizeSendTime;
	}

	/**
	 * @param prizeSendTime the prizeSendTime to set
	 */
	public void setPrizeSendTime(Date prizeSendTime) {
		this.prizeSendTime = prizeSendTime;
	}
	@Column
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/* ---------------------- 模型方法 ----------------------- */

	/**
	 * @return 剩余未认购金额
	 */
	@Transient
	public BigDecimal getRemainingCost() {
		Integer schemeCost = this.getSchemeCost();
		if (schemeCost == null)
			return null;
		BigDecimal remainingCost = BigDecimalUtil.valueOf(schemeCost);
		if (getSubscribedCost() != null)
			remainingCost = remainingCost.subtract(getSubscribedCost());
		return remainingCost;
	}

	/**
	 * @return 方案号
	 */
	@Transient
	public String getSchemeNumber() {
		return this.getLotteryType().getSchemeNumber(this.getId());
	}

	/**
	 * 彩种类型
	 * 
	 * @return {@link com.cai310.lottery.common.Lottery}
	 */
	@Transient
	public abstract Lottery getLotteryType();

	/**
	 * 刷新方案进度
	 */
	protected void refreshProcessRate() {
		float rate;
		if (getState() == SchemeState.FULL)
			rate = 100f;
		else
			rate = ((getSubscribedCost() == null) ? 0.0f : getSubscribedCost().floatValue() * 100) / getSchemeCost();

		setProgressRate(rate);
	}

	/**
	 * 增加已认购金额
	 * 
	 * @param cost 增加的金额
	 */
	protected void addSubscribedCost(BigDecimal cost) {
		setSubscribedCost((getSubscribedCost() == null) ? cost : getSubscribedCost().add(cost));
		if (getSubscribedCost().doubleValue() == getSchemeCost())
			setState(SchemeState.FULL);

		this.refreshProcessRate();
	}

	/**
	 * 增加认购金额
	 * 
	 * @param cost 认购金额
	 * @throws DataException
	 */
	public void subscription(BigDecimal cost) throws DataException {
		if (cost == null || cost.doubleValue() <= 0)
			throw new DataException("认购金额不能为空或小于0.");

		if (!isCanSubscribe())
			throw new DataException("方案已满员.");

		BigDecimal remainingCost = this.getRemainingCost();
		if (cost.doubleValue() > remainingCost.doubleValue())
			throw new DataException("认购金额大于方案剩余金额.");

		if (getMinSubscriptionCost() != null && cost.doubleValue() < getMinSubscriptionCost().doubleValue()) {
			if (remainingCost.doubleValue() < getMinSubscriptionCost().doubleValue()) {
				if (cost.doubleValue() != remainingCost.doubleValue())
					throw new DataException("方案剩余金额小于方案设置的最小认购金额时,必须一次性认购剩余所有金额.");
			} else {
				throw new DataException("认购金额小于方案设置的最小认购金额.");
			}
		}

		addSubscribedCost(cost);
	}

	/**
	 * 增加保底金额
	 * 
	 * @param cost 保底金额
	 * @throws DataException
	 */
	public void baodi(BigDecimal cost) throws DataException {
		if (cost == null || cost.doubleValue() <= 0)
			throw new DataException("认购金额不能为空或小于0.");

		if (!isCanBaodi())
			throw new DataException("方案当前不允许保底.");

		BigDecimal remainingCost = this.getRemainingCost();
		BigDecimal maxCanBaodiCost = (getBaodiCost() == null) ? remainingCost : remainingCost.subtract(getBaodiCost());

		if (cost.doubleValue() > maxCanBaodiCost.doubleValue())
			throw new DataException("方案当前最多只能保底[" + Constant.MONEY_FORMAT.format(maxCanBaodiCost) + "]元.");

		if (getMinSubscriptionCost() != null && cost.doubleValue() < getMinSubscriptionCost().doubleValue()) {
			if (maxCanBaodiCost.doubleValue() < getMinSubscriptionCost().doubleValue()) {
				if (cost.doubleValue() != remainingCost.doubleValue())
					throw new DataException("方案剩余可保底金额[" + Constant.MONEY_FORMAT.format(maxCanBaodiCost)
							+ "]元小于方案设置的最小认购金额时,必须一次性认购方案剩余的可保底金额.");
			} else {
				throw new DataException("保底金额小于方案设置的最小认购金额.");
			}
		}

		this.setBaodiCost((getBaodiCost() == null) ? cost : getBaodiCost().add(cost));
	}

	/**
	 * 保底转认购
	 * 
	 * @param transferCost 保底转认购的金额
	 * @return 认购金额
	 * @throws DataException
	 */
	public BigDecimal baodiTransferSubscription(BigDecimal transferCost) throws DataException {
		if (transferCost == null || transferCost.doubleValue() <= 0)
			throw new DataException("保底转认购的金额不能为空、小于或等于0.");
		if (!isCanSubscribe())
			throw new DataException("方案当前不允许认购.");
		if (!isHasBaodi())
			throw new DataException("方案当前没有保底可以转认购.");
		if (transferCost.compareTo(getBaodiCost()) > 0)
			throw new DataException("保底转认购的金额大于方案当前总保底金额.");

		BigDecimal cost;
		BigDecimal remainingCost = getRemainingCost();
		if (transferCost.compareTo(remainingCost) > 0)
			cost = remainingCost;
		else
			cost = transferCost;

		this.addSubscribedCost(cost);
		this.setBaodiCost(getBaodiCost().subtract(cost));

		return cost;
	}

	/**
	 * 判断是否允许认购
	 * 
	 * @return 是否允许认购
	 */
	@Transient
	public boolean isCanSubscribe() {
		return getRemainingCost().doubleValue() > 0 && getState() == SchemeState.UNFULL;
	}

	/**
	 * 判断是否允许保底
	 * 
	 * @return 是否允许保底
	 */
	@Transient
	public boolean isCanBaodi() {
		BigDecimal remainingCost = getRemainingCost();
		if (remainingCost.doubleValue() > 0 && getState() == SchemeState.UNFULL) {
			if (getBaodiCost() != null) {
				return getBaodiCost().doubleValue() < remainingCost.doubleValue();
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return 是否有保底
	 */
	@Transient
	public boolean isHasBaodi() {
		return this.getBaodiCost() != null && this.getBaodiCost().doubleValue() > 0;
	}

	@Transient
	public boolean isCanCancel() {
		return isCanCancel(false);
	}

	/**
	 * 
	 * @param isAdminRequest 是否管理员操作
	 * @return 能否撤销方案
	 */
	@Transient
	public boolean isCanCancel(boolean isAdminRequest) {
		if (isAdminRequest)
			return !isSendToPrint() && (getState() == SchemeState.UNFULL || getState() == SchemeState.FULL);
		else
			return getTotalProgressRate() < Constant.MIN_PROGRESS_RATE && !isSendToPrint()
					&& (getState() == SchemeState.UNFULL || (!isUploaded() && getState() == SchemeState.FULL));
	}

	@Transient
	public float getTotalProgressRate() {
		BigDecimal baodiCost = isHasBaodi() && getBaodiCost() != null ? getBaodiCost() : BigDecimal.ZERO;
		BigDecimal subscribedCost = getSubscribedCost() != null ? getSubscribedCost() : BigDecimal.ZERO;
		BigDecimal totalCost = baodiCost.add(subscribedCost);
		return (totalCost.floatValue() * 100) / getSchemeCost();
	}

	/**
	 * 撤销方案
	 * 
	 * @param isAdminRequest 是否管理员操作
	 * @throws DataException
	 */
	public void cancel(boolean isAdminRequest) throws DataException {
		if (!isCanCancel(isAdminRequest))
			throw new DataException("方案当前不允许撤单.");

		setState(SchemeState.CANCEL);
	}

	/**
	 * 强制撤销方案
	 * 
	 * @throws DataException
	 */
	public void forceCancel() throws DataException {
		if (getState() == SchemeState.SUCCESS)
			throw new DataException("成功方案不能撤单只能退款.");
		if (getState() != SchemeState.UNFULL && getState() != SchemeState.FULL)
			throw new DataException("方案当前不允许撤单.");

		setState(SchemeState.CANCEL);
	}

	/**
	 * @return 能否撤销认购
	 */
	@Transient
	public boolean isCanCancelSubscription() {
		return getTotalProgressRate() < Constant.MIN_PROGRESS_RATE && getState() == SchemeState.UNFULL
				&& !isSendToPrint() && getSubscribedCost() != null && getSubscribedCost().doubleValue() > 0;
	}

	/**
	 * 撤销认购
	 * 
	 * @param cost 撤销的金额
	 * @throws DataException
	 */
	public void cancelSubscription(BigDecimal cost) throws DataException {
		if (!isCanCancelSubscription())
			throw new DataException("方案当前不能撤销认购.");
		if (cost == null || cost.doubleValue() <= 0)
			throw new DataException("撤销的金额不能为空、小于或者等于0.");
		else if (cost.compareTo(getSubscribedCost()) > 0)
			throw new DataException("撤销的金额大于方案已认购的金额.");

		setSubscribedCost(getSubscribedCost().subtract(cost));
		this.refreshProcessRate();
	}

	/**
	 * @return 是否能撤销保底
	 */
	@Transient
	public boolean isCanCancelBaodi() {
		if (isHasBaodi()) {
			return getState() == SchemeState.UNFULL || getState() == SchemeState.FULL;
		}
		return false;
	}

	/**
	 * 撤销保底
	 * 
	 * @param cost 撤销的金额
	 * @param checkSpare 是否检查多余保底，撤销的保底金额不能大于多余的保底金额
	 * @throws DataException
	 */
	public void cancelBaodi(BigDecimal cost, boolean checkSpare) throws DataException {
		if (!isCanCancelBaodi())
			throw new DataException("方案当前不能撤销保底.");
		if (cost == null || cost.doubleValue() <= 0)
			throw new DataException("撤销的金额不能为空、小于或者等于0.");
		else if (cost.compareTo(getBaodiCost()) > 0)
			throw new DataException("撤销的金额大于方案保底总金额.");

		if (checkSpare) {
			BigDecimal spareBaodiCost = getSpareBaodiCost();
			if (spareBaodiCost == null || cost.compareTo(spareBaodiCost) > 0)
				throw new DataException("撤销的保底金额大于多余的保底金额.");
		}

		setBaodiCost(getBaodiCost().subtract(cost));
	}

	/**
	 * @return 多余的保底金额
	 */
	@Transient
	public BigDecimal getSpareBaodiCost() {
		if (isHasBaodi()) {
			BigDecimal remainingCost = getRemainingCost();
			if (getBaodiCost().compareTo(remainingCost) > 0)
				return getBaodiCost().subtract(remainingCost);
		}
		return null;
	}

	/**
	 * @return 能否退款
	 */
	public boolean canRefundment() {
		return getState() == SchemeState.SUCCESS;
	}

	/**
	 * 方案退款
	 */
	public void refundment() throws DataException {
		if (!canRefundment())
			throw new DataException("方案当前不能退款.");

		setState(SchemeState.REFUNDMENT);
		setPrizeSended(false);
		setPrizeDetail(null);
	}

	/**
	 * 上传方案内容
	 * 
	 * @param content 上传的方案内容
	 * @throws DataException
	 */
	public void uploadContent(String content) throws DataException {
		if (isUploaded())
			throw new DataException("方案内容已上传,不能再上传方案.");
		if (StringUtils.isBlank(content))
			throw new DataException("上传的方案内容为空.");

		setContent(content);
		setUploaded(true);
		setUploadTime(new Date());
	}

	/**
	 * @return 是否可以执行完成交易
	 */
	public boolean canCommitTransaction() {
		return sendToPrint && uploaded && !isHasBaodi() && state == SchemeState.FULL;
	}

	/**
	 * 完成交易
	 */
	public void commitTransaction() {
		setState(SchemeState.SUCCESS);// 标识方案状态为成功
		setCommitTime(new Date());// 设置交易结束时间
	}

	/**
	 * @return 是否可以执行取消交易
	 */
	public boolean canCancelTransaction() {
		return !sendToPrint && (state == SchemeState.UNFULL || (!uploaded && state == SchemeState.FULL));
	}

	/**
	 * 能否执行保底（使用或者撤销保底）
	 * 
	 * @return 能否执行保底
	 */
	public boolean canRunBaodi() {
		if (this.getState() == SchemeState.FULL) {
			return this.isHasBaodi();
		} else if (this.getState() == SchemeState.UNFULL) {
			return sendToPrint || this.isHasBaodi();
		}
		return false;
	}

	/**
	 * @return 能否使用保底
	 */
	public boolean canUseBaodi() {
		return this.isHasBaodi() && this.isCanSubscribe();
	}

	/**
	 * @return 是否可以发送去打印
	 */
	public boolean canSendToPrint() {
		if (this.isSendToPrint() || !this.isUploaded()
				|| (this.getState() != SchemeState.UNFULL && this.getState() != SchemeState.FULL))
			return false;

		BigDecimal totalCost = BigDecimal.ZERO;
		if (this.getSubscribedCost() != null)
			totalCost = totalCost.add(this.getSubscribedCost());
		if (this.getBaodiCost() != null)
			totalCost = totalCost.add(this.getBaodiCost());
		if (Constant.SITE_BAODI_RATE > 0 && Constant.SITE_BAODI_RATE < 1)
			totalCost = totalCost.add(BigDecimalUtil.valueOf(this.getSchemeCost() * Constant.SITE_BAODI_RATE));

		return totalCost.doubleValue() >= this.getSchemeCost();
	}

	/**
	 * @return 保底进度
	 */
	@Transient
	public float getBaodiProgressRate() {
		if (!this.isHasBaodi())
			return 0f;
		else
			return (getBaodiCost().floatValue() * 100) / getSchemeCost();
	}

	/**
	 * 返回符合打印格式的字符串，默认使用content中的内容，可在子类重载定义独特的方式
	 * 
	 * @return 符合打印格式的字符串
	 */
	public String toPrintString() {
		return this.getContent();
	}

	/**
	 * @return 已更新中奖
	 */
	@Transient
	public boolean isUpdateWon() {
		return this.getWinningUpdateStatus() == WinningUpdateStatus.WINNING_UPDATED || isUpdatePrize();
	}

	/**
	 * @return 已更新奖金
	 */
	@Transient
	public boolean isUpdatePrize() {
		return this.getWinningUpdateStatus() == WinningUpdateStatus.PRICE_UPDATED;
	}

	public void doUpdateResult(List<WinItem> list) throws DataException {
		if (list == null || list.isEmpty())
			throw new DataException("中奖信息为空！");

		VariableString varWonLineText = new VariableString(Constant.WON_LINE_TMPLATE, null);
		StringBuffer sb = new StringBuffer();
		for (WinItem item : list) {
			varWonLineText.setVar("PRIZENAME", item.getWinName());
			varWonLineText.setVar("WINUNITS", item.getWinUnit());
			sb.append(varWonLineText.toString());
		}

		doUpdateResult(sb.toString());
	}

	public void doUpdateResult(String wonDetail) throws DataException {
		if (this.isUpdateWon())
			throw new DataException("已经更新中奖,不能再更新(如果未派奖,可重置为未开奖状态后,再重新更新).");
		else if (this.isUpdatePrize())
			throw new DataException("已经更新奖金,不能再更新中奖(如果未派奖,可重置为未开奖状态后,再重新更新).");
		else if (this.isPrizeSended())
			throw new DataException("已经派奖,不能再更新中奖.");

		this.setWinningUpdateStatus(WinningUpdateStatus.WINNING_UPDATED);
		this.setWon(true);
		this.setWonDetail(wonDetail);
	}

	public void doUpdatePrize(List<PrizeItem> list) throws DataException {
		if (list == null || list.isEmpty()) {
			throw new DataException("中奖信息为空！");
		}

		BigDecimal totalPrize = BigDecimal.ZERO;// 总奖金
		BigDecimal totalPrizeAfterTax = BigDecimal.ZERO;// 税后总奖金

		VariableString varPrizeLineText = new VariableString(Constant.PRIZE_LINE_TMPLATE, null);

		StringBuilder sb = new StringBuilder();

		BigDecimal prizeAfterTax;
		for (PrizeItem item : list) {
			varPrizeLineText.setVar("PRIZENAME", item.getWinItem().getWinName());
			varPrizeLineText.setVar("WINUNITS", item.getWinItem().getWinUnit());
			varPrizeLineText.setVar("UNIT_PRIZE", Constant.numFmt.format(item.getUnitPrize()));
			prizeAfterTax = item.getUnitPrizeAfterTax();
			varPrizeLineText.setVar("UNIT_PRIZE_TAXED", Constant.numFmt.format(prizeAfterTax));
			sb.append(varPrizeLineText.toString());

			totalPrize = totalPrize.add(BigDecimalUtil.multiply(item.getUnitPrize(),
					BigDecimalUtil.valueOf(item.getWinItem().getWinUnit())));
			totalPrizeAfterTax = totalPrizeAfterTax.add(BigDecimalUtil.multiply(prizeAfterTax,
					BigDecimalUtil.valueOf(item.getWinItem().getWinUnit())));
		}

		doUpdatePrize(totalPrize, totalPrizeAfterTax, sb.toString());
	}

	public void doUpdatePrize(BigDecimal totalPrize, BigDecimal totalPrizeAfterTax, String prizeDetail)
			throws DataException {
		if (!this.isWon())
			throw new DataException("方案未中奖,不能更新奖金.");
		else if (this.isUpdatePrize())
			throw new DataException("已经更新过奖金,不能再更新(如果未派奖,可重置为未开奖状态后,再重新更新).");
		else if (this.isPrizeSended())
			throw new DataException("已经派奖,不能再更新奖金.");

		StringBuilder sb = new StringBuilder();
		sb.append(prizeDetail);

		VariableString varFooterText = new VariableString(Constant.PRIZE_FOOTER_TMPLATE, null);
		varFooterText.setVar("TOTAL_PRIZE", Constant.numFmt.format(totalPrize));
		varFooterText.setVar("AFTER_TAX_PRIZE", Constant.numFmt.format(totalPrizeAfterTax));
		sb.append(varFooterText.toString());

		if (this.getShareType() == ShareType.TOGETHER) {
			BigDecimal totalReturn;
			boolean profit = BigDecimalUtil.valueOf(this.getSchemeCost()).compareTo(totalPrizeAfterTax) < 0;// 是否盈利
			BigDecimal organigerDeductRate;// 方案发起人佣金率
			if (profit && this.getCommissionRate() != null && this.getCommissionRate() > 0) {
				organigerDeductRate = BigDecimalUtil.valueOf(this.getCommissionRate()).setScale(2,
						BigDecimal.ROUND_HALF_UP);
			} else {
				organigerDeductRate = BigDecimal.ZERO;
			}

			BigDecimal organigerDeduct = BigDecimalUtil.multiply(totalPrizeAfterTax, organigerDeductRate);// 方案发起人佣金提成

			totalReturn = totalPrizeAfterTax.subtract(organigerDeduct);// 扣除所有提成后的可分配金额

			VariableString varCutText = new VariableString(Constant.PRIZE_CUT_TEMPLATE, null);
			varCutText.setVar("AFTER_TAX_PRIZE", Constant.numFmt.format(totalPrizeAfterTax));
			varCutText.setVar("ORGANIGER_DEDUCT_RATE", new DecimalFormat("0%").format(organigerDeductRate));
			varCutText.setVar("ORGANIGER_DEDUCT", Constant.numFmt.format(organigerDeduct));
			varCutText.setVar("TOTAL_RETURN", Constant.numFmt.format(totalReturn));
			sb.append(varCutText.toString());
		}

		this.setWinningUpdateStatus(WinningUpdateStatus.PRICE_UPDATED);
		this.setPrize(totalPrize);
		this.setPrizeAfterTax(totalPrizeAfterTax);
		this.setPrizeDetail(sb.toString());
	}

	/**
	 * @return 用户的认购金额
	 */
	@Transient
	public BigDecimal getUserBetCost() {
		if (null == this.getSubscriptions() || this.getSubscriptions().isEmpty())
			return null;
		List<Subscription> l = this.getSubscriptions();
		BigDecimal total = new BigDecimal(0);
		for (Subscription subscription : l) {
			total = total.add(subscription.getCost());
		}
		return total;
	}

	/**
	 * 是否追号方案，默认false 数字彩在子类重载实现判断
	 * 
	 * @return 是否追号方案
	 */
	@Transient
	public boolean isChasePlanScheme() {
		return false;
	}

	@Transient
	public boolean isUnFullState() {
		return this.getState() == SchemeState.UNFULL;
	}

	@Transient
	public boolean isSuccessWon() {
		return won && getState() == SchemeState.SUCCESS;
	}

	@Transient
	public boolean isUnSuccessWon() {
		return won && (getState() == SchemeState.CANCEL || getState() == SchemeState.REFUNDMENT);
	}

	@Transient
	public String getWonDetailHtml(boolean canView) {
		if (canView && StringUtils.isNotBlank(this.getWonDetail())) {
			String html = this.getWonDetail().replaceAll("/(\\r\\n|;)/ig", "<br/>");
			if (getState() == SchemeState.CANCEL || getState() == SchemeState.REFUNDMENT)
				html += "<br /><span class=\"redboldchar\">方案不成功，奖金流失</span>";
		}
		return null;
	}
	@Transient
	public JSONArray getCompoundContentStr() {
		if (StringUtils.isBlank(this.getContent()))
			return null;
		if(this.mode.equals(SalesMode.COMPOUND)){
			if(LotteryCategory.ZC.equals(this.getLotteryType().getCategory())||LotteryCategory.JC.equals(this.getLotteryType().getCategory())||LotteryCategory.DCZC.equals(this.getLotteryType().getCategory())){
				return null;
			}else{
				return JsonUtil.getJSONArray4Json(this.getContent());
			}
		}
		return null;
	}
	@Transient
	public String getPrizeDetailHtml(boolean canView) {
		if (StringUtils.isBlank(this.getPrizeDetail())) {
			return "";
		}
		String html = this.getPrizeDetail().replace("\r\n", "<br/>");
		if (!canView) {
			String[] arr = html.split("合计");
			if (arr.length == 2)
				html = "总奖金" + arr[1];
		}
		if (getState() == SchemeState.CANCEL || getState() == SchemeState.REFUNDMENT)
			html += "<br /><span class=\"redboldchar\">方案不成功，奖金流失</span>";
		return html;
	}

	@Transient
	public String getWonStatusHtml() {
		if (isUpdateWon()) {
			if (isSuccessWon()) {
				String html = "<font color=\"red\">中奖</font><br/>";
				if (isUpdatePrize()) {
					html += "<font color=\"red\">已更新奖金</font><br/>";
					if (isPrizeSended()) {
						html += "<span style=\"color:red\">已派奖</span>";
					} else {
						html += "<span style=\"color:gray\">未派奖</span>";
					}
				} else {
					html += "<span style=\"color:gray\">未更新奖金</span>";
				}
				return html;
			} else if (isUnSuccessWon()) {
				return "<font color=\"black\">流产</font>";
			} else {
				return "<span style=\"color:gray\">未中奖</span>";
			}
		} else {
			return "<font color=\"gray\">未开奖</font>";
		}
	}

	@Transient
	public String getOrderPriorityText() {
		if (this.getOrderPriority() != null)
			return Constant.getOrderPriorityText(this.getOrderPriority());
		else
			return "未知类型";
	}

	/**
	 * @return 是否合买方案
	 */
	@Transient
	public boolean isTogether() {
		return getShareType() == ShareType.TOGETHER;
	}

	/**
	 * 重置为未开奖状态
	 * 
	 * @throws DataException
	 */
	public void resetUnUpdateWon() throws DataException {
		if (!this.isUpdateWon())
			throw new DataException("方案未开奖,无需重置为未开奖.");
		this.clearWonMsg();
		this.setWinningUpdateStatus(WinningUpdateStatus.NONE);
	}

	/**
	 * 清空中奖信息
	 * 
	 * @throws DataException
	 */
	public void clearWonMsg() throws DataException {
		if (this.isPrizeSended())
			throw new DataException("方案已经派奖.");
		this.setWon(false);
		this.setWonDetail(null);
		this.setPrize(null);
		this.setPrizeAfterTax(null);
		this.setPrizeDetail(null);
	}

	@Transient
	public boolean isCompoundMode() {
		return getMode() == SalesMode.COMPOUND;
	}
	@Transient
	public String getCompoundContentDetail(String result) {
		return null;
	}
	/**
	 * 后台方案管理
	 * 
	 * @throws DataException
	 */
	@Transient
	public abstract String getContentText();

	@Transient
	//用于自动跟单
	public Byte getLotteryPlayType() {
		return null;
	}
	@Transient
	//用于接口playTypeOrdinal字段
	public Byte getPlayTypeOrdinal() {
		return 0;
	}
	@Transient
	public boolean isKeepTop() {
		return this.getOrderPriority() != null && this.getOrderPriority().equals(Constant.ORDER_PRIORITY_TOP);
	}

	@Transient
	public boolean isKeepBottom() {
		return this.getOrderPriority() != null && this.getOrderPriority().equals(Constant.ORDER_PRIORITY_BOTTOM);
	}

	/**
	 * @return the schemePrintState
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.SchemePrintState"),
			@Parameter(name = EnumType.TYPE, value = SchemeState.SQL_TYPE) })
	@Column(nullable = false)
	public SchemePrintState getSchemePrintState() {
		return schemePrintState;
	}

	/**
	 * @param schemePrintState the schemePrintState to set
	 */
	public void setSchemePrintState(SchemePrintState schemePrintState) {
		this.schemePrintState = schemePrintState;
	}
	@Transient
	public TicketSchemeState getTicketSchemeState() {
		return ticketSchemeState;
	}

	public void setTicketSchemeState(TicketSchemeState ticketSchemeState) {
		this.ticketSchemeState = ticketSchemeState;
	}
	
	@Transient
	public String getCreateTimeStr(){
		return DateUtil.dateToStr(this.createTime,"yyyy-MM-dd HH:mm:ss");
	}
	@Transient
	public String getPrizeSendTimeStr(){
		return DateUtil.dateToStr(this.prizeSendTime,"yyyy-MM-dd HH:mm:ss");
	}
    @Column
	public AgentAnalyseState getAgentAnalyseState() {
		return agentAnalyseState;
	}

	public void setAgentAnalyseState(AgentAnalyseState agentAnalyseState) {
		this.agentAnalyseState = agentAnalyseState;
	}
	@Column
	public Integer getRankFlag() {
		return rankFlag;
	}

	public void setRankFlag(Integer rankFlag) {
		this.rankFlag = rankFlag;
	}

	@Column(nullable = false,columnDefinition="bit(1) default 0")
	public boolean isGradeFlag() {
		return gradeFlag;
	}

	public void setGradeFlag(boolean gradeFlag) {
		this.gradeFlag = gradeFlag;
	}
	
	@Column(columnDefinition ="bit(1) default 0")
	public Boolean isScheme_synchroned() {
		return scheme_synchroned;
	}

	public void setScheme_synchroned(Boolean scheme_synchroned) {
		this.scheme_synchroned = scheme_synchroned;
	}
}
