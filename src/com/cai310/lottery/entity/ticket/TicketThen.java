package com.cai310.lottery.entity.ticket;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.TicketSchemeState;
import com.cai310.lottery.ticket.common.TicketSupporter;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "TICKET_THEN")
@Entity
public class TicketThen extends IdEntityWithTimeFlag {

	private static final long serialVersionUID = 2560091269922347708L;
	/** 官方截止时间 */
	private Date officialEndTime;
	
	/** 发起平台 */
	private Long platformInfoId;

	/** 发起人ID */
	private Long userId;

	/** 订单号 */
	private String orderId;

	/** 彩种 */
	private Lottery lotteryType;

	/** 期号 */
	private String periodNumber;
	
	/** 彩票编号 */
	private String schemeNumber;
	
	/** 注数（单倍注数） */
	private Integer units;

	/** 倍数 */
	private Integer multiple;

	/** 金额 */
	private Integer schemeCost;

	/** 奖金*/                          
	private BigDecimal totalPrizeAfterTax;
	
    /**  投注时间 父类IdEntityWithTimeFlag 的createTime */
	
	/** 出票时间 */
	private Date ticketTime;
	/** 结算时间 */
	private Date prizeTime;
	/** 撤销时间 */
	private Date cancelTime;
 
	/** 彩票状态 */
	private TicketSchemeState state;
	/** 委托方方案号 一个方案号对应一个或者多个订单号 */
	private String outOrderNumber;
	/** 出票商 */
	private TicketSupporter ticketSupporter;
	/**电子票号*/
	private String remoteTicketId;
	/** 加奖*/                          
	private BigDecimal addPrize;

	/** 是否出票成功 */
	private boolean ticketFinsh;

	/** 是否发送*/
	private boolean sended;

	/** 发送时间 */
	private Date sendTime;



	/** 出票方提供状态的更新时间*/
	private Date stateModifyTime;

	/** 是否已同步*/
	private boolean synchroned;

	/** 是否已同步出票中奖*/
	private boolean checked;

	private boolean won;
	
	/** 中奖金额（税前金额）*/                          
	private Double totalPrize;        
	
	/** 投注方式 */
	private SalesMode mode;

	/** 玩法类型 */
	private Byte playType;
	/** 中奖详情*/
	private String wonDetail;

	/** 远程同步*/
	private Boolean ticket_synchroned;

	/* ----------- getter and setter method ----------- */
	/**
	 * @return {@link #sponsorId}
	 */
	@Column(nullable = false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	

	/**
	 * @return {@link #officialEndTime}
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	public Date getOfficialEndTime() {
		return officialEndTime;
	}

	/**
	 * @param officialEndTime the {@link #officialEndTime} to set
	 */
	public void setOfficialEndTime(final Date officialEndTime) {
		this.officialEndTime = officialEndTime;
	}

	/**
	 * @return {@link #schemeNumber}
	 */
	@Column(nullable = false, length = 20)
	public String getSchemeNumber() {
		return schemeNumber;
	}

	/**
	 * @param schemeNumber the {@link #schemeNumber} to set
	 */
	public void setSchemeNumber(final String schemeNumber) {
		this.schemeNumber = schemeNumber;
	}

	
	/**
	 * @return {@link #lotteryType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lotteryType the {@link #lotteryType} to set
	 */
	public void setLotteryType(final Lottery lotteryType) {
		this.lotteryType = lotteryType;
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
	 * @param mode the {@link #mode} to set
	 */
	public void setMode(final SalesMode mode) {
		this.mode = mode;
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
	public void setPeriodNumber(final String periodNumber) {
		this.periodNumber = periodNumber;
	}

	/**
	 * @return {@link #betType}
	 */
	@Column
	public Byte getPlayType() {
		return playType;
	}

	/**
	 * @param betType the {@link #betType} to set
	 */
	public void setPlayType(final Byte playType) {
		this.playType = playType;
	}

	/**
	 * @return {@link #units}
	 */
	@Column(nullable = false, updatable = false)
	public Integer getUnits() {
		return units;
	}

	/**
	 * @param units the {@link #units} to set
	 */
	public void setUnits(final Integer units) {
		this.units = units;
	}

	/**
	 * @return {@link #multiple}
	 */
	@Column(nullable = false, updatable = false)
	public Integer getMultiple() {
		return multiple;
	}

	/**
	 * @param multiple the {@link #multiple} to set
	 */
	public void setMultiple(final Integer multiple) {
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
	 * @param schemeCost the {@link #schemeCost} to set
	 */
	public void setSchemeCost(final Integer schemeCost) {
		this.schemeCost = schemeCost;
	}



	/**
	 * @return the ticketFinsh
	 */
	@Column(name = "ticketFinsh")
	public boolean isTicketFinsh() {
		return ticketFinsh;
	}

	/**
	 * @param ticketFinsh the ticketFinsh to set
	 */
	public void setTicketFinsh(final boolean ticketFinsh) {
		this.ticketFinsh = ticketFinsh;
	}

	/**
	 * @return the sended
	 */
	@Column(name = "sended")
	public boolean isSended() {
		return sended;
	}

	/**
	 * @param sended the sended to set
	 */
	public void setSended(final boolean sended) {
		this.sended = sended;
	}

	/**
	 * @return the sendTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(final Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * @return the ticketSupporter
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.ticket.common.TicketSupporter"),
			@Parameter(name = EnumType.TYPE, value = SalesMode.SQL_TYPE) })
	@Column
	public TicketSupporter getTicketSupporter() {
		return ticketSupporter;
	}

	/**
	 * @param ticketSupporter the ticketSupporter to set
	 */
	public void setTicketSupporter(final TicketSupporter ticketSupporter) {
		this.ticketSupporter = ticketSupporter;
	}

	/**
	 * @return the remoteTicketId
	 */
	@Column
	public String getRemoteTicketId() {
		return remoteTicketId;
	}

	/**
	 * @param remoteTicketId the remoteTicketId to set
	 */
	public void setRemoteTicketId(final String remoteTicketId) {
		this.remoteTicketId = remoteTicketId;
	}

	

	/**
	 * @return the synchroned
	 */
	@Column
	public boolean isSynchroned() {
		return synchroned;
	}

	/**
	 * @param 出票方提供状态的更新时间
	 */
	public void setSynchroned(final boolean synchroned) {
		this.synchroned = synchroned;
	}

	/**
	 * 是否高频彩种
	 * @return
	 */
	@Transient
	public boolean isKeno() {
		return Lottery.isKeno(this.getLotteryType());
	}

	@Column
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(final boolean checked) {
		this.checked = checked;
	}

	@Lob
	@Column(length = 16777216) 
	public String getWonDetail() {
		return wonDetail;
	}

	public void setWonDetail(final String wonDetail) {
		this.wonDetail = wonDetail;
	}
	@Column
	public Double getTotalPrize() {
		return totalPrize;
	}

	public void setTotalPrize(final Double totalPrize) {
		this.totalPrize = totalPrize;
	}

	@Column
	public BigDecimal getTotalPrizeAfterTax() {
		return totalPrizeAfterTax;
	}

	public void setTotalPrizeAfterTax(BigDecimal totalPrizeAfterTax) {
		this.totalPrizeAfterTax = totalPrizeAfterTax;
	}

	@Column
	public boolean isWon() {
		return won;
	}

	public void setWon(final boolean won) {
		this.won = won;
	}

	@Column(columnDefinition = "bit(1) default 0")
	public Boolean isTicket_synchroned() {
		return ticket_synchroned;
	}

	public void setTicket_synchroned(final Boolean ticket_synchroned) {
		this.ticket_synchroned = ticket_synchroned;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getTicketTime() {
		return ticketTime;
	}

	public void setTicketTime(final Date ticketTime) {
		this.ticketTime = ticketTime;
	}
	@Column
	public Date getPrizeTime() {
		return prizeTime;
	}

	public void setPrizeTime(Date prizeTime) {
		this.prizeTime = prizeTime;
	}
	@Column
	public String getOutOrderNumber() {
		return outOrderNumber;
	}

	public void setOutOrderNumber(String outOrderNumber) {
		this.outOrderNumber = outOrderNumber;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getStateModifyTime() {
		return stateModifyTime;
	}

	public void setStateModifyTime(Date stateModifyTime) {
		this.stateModifyTime = stateModifyTime;
	}
	@Column
	public BigDecimal getAddPrize() {
		return addPrize;
	}

	public void setAddPrize(BigDecimal addPrize) {
		this.addPrize = addPrize;
	}
	@Column
	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}
	@Column
	public TicketSchemeState getState() {
		return state;
	}

	public void setState(TicketSchemeState state) {
		this.state = state;
	}
	@Column
	public Long getPlatformInfoId() {
		return platformInfoId;
	}

	public void setPlatformInfoId(Long platformInfoId) {
		this.platformInfoId = platformInfoId;
	}
	@Column
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
