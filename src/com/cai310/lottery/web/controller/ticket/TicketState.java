package com.cai310.lottery.web.controller.ticket;

import java.math.BigDecimal;
import java.util.Date;

import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.TicketSchemeState;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.entity.lottery.Scheme;

public class TicketState {
	/** 税后奖金 */
	private BigDecimal prizeAfterTax;
	
	/** 接票ID(订单号)*/
	private String orderId;
	
	/** 彩票状态 */
	private TicketSchemeState printState;
	
	/** 是否中奖 */
	private boolean won;

	/** 中奖详情 */
	private String wonDetail;

	/** 奖金是否已派发 */
	private boolean prizeSended;

	/** 奖金详情 */
	private String prizeDetail;
	/**
	 * 方案中奖更新状态
	 * 
	 * @see com.cai310.lottery.common.WinningUpdateStatus
	 */
	private WinningUpdateStatus winningUpdateStatus;

	public TicketState(){
		
	}
	public TicketState(Scheme scheme){
		if(SchemeState.REFUNDMENT.equals(scheme.getState())||SchemeState.CANCEL.equals(scheme.getState())||SchemeState.UNFULL.equals(scheme.getState())){
			this.printState = TicketSchemeState.FAILD;
		}else if(scheme.getState().equals(SchemeState.FULL)){
			if(scheme.getSchemePrintState().equals(SchemePrintState.SUCCESS)){
				this.printState = TicketSchemeState.SUCCESS;
			}else if(scheme.getSchemePrintState().equals(SchemePrintState.PRINT)||scheme.getSchemePrintState().equals(SchemePrintState.UNPRINT)){
				this.printState = TicketSchemeState.FULL;
			}else if(scheme.getSchemePrintState().equals(SchemePrintState.FAILED)){
				this.printState = TicketSchemeState.FAILD;
			}
		}else if(scheme.getState().equals(SchemeState.SUCCESS)){
			this.printState = TicketSchemeState.SUCCESS;
		}
		this.winningUpdateStatus = scheme.getWinningUpdateStatus();
		this.orderId = scheme.getOrderId();
		this.won = scheme.isWon();
		if(this.won){
			this.wonDetail = scheme.getWonDetail();
			this.prizeAfterTax = scheme.getPrizeAfterTax();
			this.prizeDetail =scheme.getPrizeDetail();
		}
		this.prizeSended = scheme.isPrizeSended();
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

	

	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}

	public String getWonDetail() {
		return wonDetail;
	}

	public void setWonDetail(String wonDetail) {
		this.wonDetail = wonDetail;
	}

	public boolean isPrizeSended() {
		return prizeSended;
	}

	public void setPrizeSended(boolean prizeSended) {
		this.prizeSended = prizeSended;
	}

	public String getPrizeDetail() {
		return prizeDetail;
	}

	public void setPrizeDetail(String prizeDetail) {
		this.prizeDetail = prizeDetail;
	}

	public WinningUpdateStatus getWinningUpdateStatus() {
		return winningUpdateStatus;
	}

	public void setWinningUpdateStatus(WinningUpdateStatus winningUpdateStatus) {
		this.winningUpdateStatus = winningUpdateStatus;
	}
	public TicketSchemeState getPrintState() {
		return printState;
	}
	public void setPrintState(TicketSchemeState printState) {
		this.printState = printState;
	}
}
