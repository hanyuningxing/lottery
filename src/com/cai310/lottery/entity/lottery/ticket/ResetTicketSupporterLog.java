package com.cai310.lottery.entity.lottery.ticket;

import java.util.Date;

import com.cai310.entity.IdEntity;
import com.cai310.lottery.ticket.common.TicketSupporter;

/**
 * 重置出票商操作日志
 * @author jack
 *
 */
public class ResetTicketSupporterLog extends IdEntity{

	private static final long serialVersionUID = 2184623327945756784L;
	
	/** 票ID*/
	private Long ticketId;
	/** 所属方案号 */
	private String schemeNumber;
	/** 返回的状态码*/
	private String stateCode;
	/** 返回的状态信息*/
	private String stateCodeMessage;
	/** 操作员*/
	private String operName;
	/** 原出票商*/
	private TicketSupporter ticketSupporter;
	/** 切换后的出票商*/
	private TicketSupporter resetTikcetSupporter;
	/** 发送的时间*/
	private Date sendTime;
	/** 状态修改时间*/
	private Date stateModifyTime;
	/** 备注*/
	private String remark;
	/** 创建时间*/
	private Date createTime;

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getSchemeNumber() {
		return schemeNumber;
	}

	public void setSchemeNumber(String schemeNumber) {
		this.schemeNumber = schemeNumber;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateCodeMessage() {
		return stateCodeMessage;
	}

	public void setStateCodeMessage(String stateCodeMessage) {
		this.stateCodeMessage = stateCodeMessage;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public TicketSupporter getTicketSupporter() {
		return ticketSupporter;
	}

	public void setTicketSupporter(TicketSupporter ticketSupporter) {
		this.ticketSupporter = ticketSupporter;
	}

	public TicketSupporter getResetTikcetSupporter() {
		return resetTikcetSupporter;
	}

	public void setResetTikcetSupporter(TicketSupporter resetTikcetSupporter) {
		this.resetTikcetSupporter = resetTikcetSupporter;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getStateModifyTime() {
		return stateModifyTime;
	}

	public void setStateModifyTime(Date stateModifyTime) {
		this.stateModifyTime = stateModifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
