package com.cai310.lottery.utils.local;

public class CpTicket {
	private Integer Code;
	private Long TicketId;
	public Integer getCode() {
		return Code;
	}
	public void setCode(Integer code) {
		Code = code;
	}
	public Long getTicketId() {
		return TicketId;
	}
	public void setTicketId(Long ticketId) {
		TicketId = ticketId;
	}
	public Boolean getIsSuccess() {
		return Code.equals(0)||Code.equals(101);
	}
}
