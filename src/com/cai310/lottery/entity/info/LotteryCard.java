package com.cai310.lottery.entity.info;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * 彩金卡
 * 
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "LOTTERYCARD")
public class LotteryCard implements Serializable {
	private static final long serialVersionUID = -1949293792466420941L;
	public static final String STATUS_USED = "1";// 已使用
	public static final String STATUS_UNUSED = "0";// 未使用

	private String lotteryNo;

	/** 预付款金额 */
	private BigDecimal presentAmount;

	private String batchno;
	/**/
	private String status;

	private String sourceId;

	@Id
	public String getLotteryNo() {
		return lotteryNo;
	}

	public void setLotteryNo(String lotteryNo) {
		this.lotteryNo = lotteryNo;
	}

	
	@Column
	public BigDecimal getPresentAmount() {
		return presentAmount;
	}

	public void setPresentAmount(BigDecimal presentAmount) {
		this.presentAmount = presentAmount;
	}

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
}
