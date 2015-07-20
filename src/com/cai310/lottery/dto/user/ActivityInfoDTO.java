package com.cai310.lottery.dto.user;

import java.math.BigDecimal;

import com.cai310.lottery.common.ActivityType;
import com.cai310.lottery.utils.BigDecimalUtil;

public class ActivityInfoDTO {
	
	private String userid;
	
	private String registname;
	
	private BigDecimal amount;
	
	private ActivityType userActivityType;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRegistname() {
		return registname;
	}

	public void setRegistname(String registname) {
		this.registname = registname;
	}

	public ActivityType getUserActivityType() {
		return userActivityType;
	}

	public void setUserActivityType(ActivityType userActivityType) {
		this.userActivityType = userActivityType;
	}

	public BigDecimal getAmount() {
		Double amountDouble = amount.doubleValue()/BigDecimalUtil.MULTIPLE;
		return new BigDecimal(amountDouble);
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
