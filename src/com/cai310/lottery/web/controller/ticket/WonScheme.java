package com.cai310.lottery.web.controller.ticket;

import java.math.BigDecimal;
import java.util.Date;

public class WonScheme {
	
	private Long id;
	
	/** 方案发起人的用户编号 */
	private Long sponsorId;
	
	/** 税后奖金 */
	private BigDecimal prizeAfterTax;
	
	/** 接票ID(订单号)*/
	private String orderId;
	
	/** 创建时间 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public BigDecimal getPrizeAfterTax() {
		return prizeAfterTax;
	}

	public void setPrizeAfterTax(BigDecimal prizeAfterTax) {
		this.prizeAfterTax = prizeAfterTax;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
