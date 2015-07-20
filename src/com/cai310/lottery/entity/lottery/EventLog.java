package com.cai310.lottery.entity.lottery;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cai310.entity.IdEntity;
import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.Lottery;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "EVENT_LOG")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EventLog extends IdEntity {

	private static final long serialVersionUID = 1931316973683133023L;

	/** 彩种类型 */
	private Lottery lotteryType;

	private Long periodId;
	private String periodNumber;
	private Byte logType;
	private Date startTime;
	private Date doneTime;
	private Boolean normalFinish;
	private String msg;
	private String operator;
	private String ip;
	/** 版本号,用于实现乐观锁 */
	private Integer version;
	
	public EventLog() {

	}

	/**
	 * @param lotteryType 平台号
	 * @param periodId 销售期编号
	 * @param periodNumber 期号
	 * @param cmdType 操作类型
	 * @param operator 操作员
	 */
	public EventLog(Lottery lotteryType, Long periodId, String periodNumber, Byte logType, String operator, String ip) {
		super();
		this.lotteryType = lotteryType;
		this.periodId = periodId;
		this.periodNumber = periodNumber;
		this.logType = logType;
		this.operator = operator;
		this.ip = ip;
	}

	@Column(name = "lotteryType", updatable = false)
	/**
	 * @return the lotteryType
	 */
	public Lottery getLotteryType() {
		return lotteryType;
	}

	/**
	 * @param lotteryType the lotteryType to set
	 */
	public void setLotteryType(Lottery lotteryType) {
		this.lotteryType = lotteryType;
	}

	@Column(name = "periodId", updatable = false)
	public Long getPeriodId() {
		return this.periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}

	@Column(name = "periodNumber", length = 15, updatable = false)
	public String getPeriodNumber() {
		return this.periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}

	@Column(name = "logType", nullable = false, updatable = false)
	public Byte getLogType() {
		return this.logType;
	}

	public void setLogType(Byte cmdType) {
		this.logType = cmdType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "startTime", nullable = false, length = 23, updatable = false)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "doneTime", length = 23)
	public Date getDoneTime() {
		return this.doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}

	@Column(name = "normalFinish")
	public Boolean getNormalFinish() {
		return this.normalFinish;
	}

	public void setNormalFinish(Boolean normalFinish) {
		this.normalFinish = normalFinish;
	}

	@Lob
	@Column(name = "msg")
	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Column(name = "operator", length = 20, updatable = false)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the ip
	 */
	@Column(name = "ip", length = 20, updatable = false)
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	// =================================================

	@Transient
	public EventLogType getEventLogType() {
		return EventLogType.valueOf(this.getLogType());
	}
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

}