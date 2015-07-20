package com.cai310.lottery.dto.lottery;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cai310.lottery.common.ChaseDetailState;

public class ChasePlanDetailDTO implements Serializable {
	private static final long serialVersionUID = -1025920991877401793L;
	
	private Long curPeriodNum;
		
	private Integer multiple;
				
	private Integer cost;
	
	private BigDecimal kenoCost;
	
	private ChaseDetailState state;
	 
	public ChaseDetailState getState() {
		return state;
	}
	public void setState(ChaseDetailState state) {
		this.state = state;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	public Long getCurPeriodNum() {
		return curPeriodNum;
	}
	public void setCurPeriodNum(Long curPeriodNum) {
		this.curPeriodNum = curPeriodNum;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public BigDecimal getKenoCost() {
		return kenoCost;
	}
	public void setKenoCost(BigDecimal kenoCost) {
		this.kenoCost = kenoCost;
	}

}
