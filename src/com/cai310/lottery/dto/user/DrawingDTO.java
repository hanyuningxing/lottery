package com.cai310.lottery.dto.user;

import java.math.BigDecimal;
import java.util.Date;

import com.cai310.lottery.common.ActivityType;
import com.cai310.lottery.common.DrawingOrderState;
import com.cai310.lottery.utils.BigDecimalUtil;

public class DrawingDTO {
	private DrawingOrderState state;
	/** 创建时间 */
	private String createTime;
	
	private Boolean enable;
	
	private BigDecimal money;

	public DrawingOrderState getState() {
		return state;
	}

	public void setState(DrawingOrderState state) {
		this.state = state;
	}

	
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	
}
