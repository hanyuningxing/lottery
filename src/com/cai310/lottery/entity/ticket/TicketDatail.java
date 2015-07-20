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

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "TICKETDATAIL")
@Entity
public class TicketDatail extends IdEntityWithTimeFlag {

	private static final long serialVersionUID = 2560091269922347708L;
	/**  */
	private Long ticketId;
	/** 方案内容 */
	private String content;
	/** 出票的奖金值 */
	private String printAwards;
	@Column
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	@Lob
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Lob
	@Column(name = "printAwards")
	public String getPrintAwards() {
		return printAwards;
	}
	public void setPrintAwards(String printAwards) {
		this.printAwards = printAwards;
	}

	
}
