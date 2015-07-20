package com.cai310.lottery.entity.lottery;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.entity.UpdateMarkable;
import com.cai310.lottery.common.AgentAnalyseState;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SaleAnalyseState;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.ShareType;

/**
 * <b>交易成功的彩票方案.（符合更新中奖的方案，在更新中奖结果后对应保存）</b>
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SUCCESS_SCHEME")
public class TradeSuccessScheme extends IdEntity implements UpdateMarkable {
	
	private static final long serialVersionUID = 2056660047574998533L;

	/** 期编号 */
	private Long periodId;

	/** 期号 */
	private String periodNumber;

	/** 方案发起人的用户编号 */
	private Long sponsorId;

	/** 方案发起人的用户名 */
	private String sponsorName;
	
	/** 彩种类型*/
	private Lottery lotteryType;
	
	/** 方案号Id*/
	private Long schemeId;

	/** 方案金额 */
	protected Integer schemeCost;

	/** 方案投注的方式*/
	private SalesMode mode;

	/**方案分享类型*/
	private ShareType shareType;

	/**方案状态*/
	private SchemeState state;

	/** 是否中奖 */
	private boolean won;

	/** 税前奖金 */
	private BigDecimal schemePrize;

	/** 税后奖金 */
	private BigDecimal schemePrizeAfterTax;

	/** 创建时间(是指方案号的创建时间) */
	private Date createTime;

	/** 最后更新时间 */
	private Date lastModifyTime;
	    
    /**
	 * 销售统计状态
	 */
    private SaleAnalyseState saleAnalyseState = SaleAnalyseState.NONE;
    
    /**
   	 * 佣金统计状态
   	 */
    private AgentAnalyseState agentAnalyseState = AgentAnalyseState.NONE;

	/* ---------------------- getter and setter method ----------------------- */

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

	@Column(name = "prize")
    public BigDecimal getSchemePrize() {
		return schemePrize;
	}

	public void setSchemePrize(BigDecimal schemePrize) {
		this.schemePrize = schemePrize;
	}

	@Column(name = "prizeAfterTax")
	public BigDecimal getSchemePrizeAfterTax() {
		return schemePrizeAfterTax;
	}

	public void setSchemePrizeAfterTax(BigDecimal schemePrizeAfterTax) {
		this.schemePrizeAfterTax = schemePrizeAfterTax;
	}

	@Column
	public AgentAnalyseState getAgentAnalyseState() {
		return agentAnalyseState;
	}

	public void setAgentAnalyseState(AgentAnalyseState agentAnalyseState) {
		this.agentAnalyseState = agentAnalyseState;
	}

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column(name = "lottery", nullable = false, updatable = false)	
	public Lottery getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}


}
