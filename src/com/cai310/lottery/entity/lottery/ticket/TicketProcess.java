package com.cai310.lottery.entity.lottery.ticket;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.entity.CreateMarkable;
import com.cai310.entity.IdEntity;
import com.cai310.lottery.common.Lottery;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "TICKET_PROCESS")
@Entity
public class TicketProcess extends IdEntity implements CreateMarkable{

	private static final long serialVersionUID = 2560091269922347708L;
	
	/** 创建时间 */
	private Date createTime;	
	
	/** 发起人ID */
	private Long userId;
	
	/** orderId */
	private Long orderId;

	/** 彩种 */
	private Lottery lotteryType;

	/** 处理 */
	private String process;
	
	/** 错误内容 */
	private String errorMsg;
	
	/** 错误内容 */
	private String exceptionMsg;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return {@link #lotteryType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.Lottery"),
			@Parameter(name = EnumType.TYPE, value = Lottery.SQL_TYPE) })
	@Column(updatable = false)
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lotteryType the {@link #lotteryType} to set
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}
	@Column(updatable = false)
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	@Column
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	@Column
	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}
	@Lob
	@Column
	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
    
    
	
}
