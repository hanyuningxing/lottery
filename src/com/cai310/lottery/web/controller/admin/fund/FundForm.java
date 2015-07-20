package com.cai310.lottery.web.controller.admin.fund;

import java.math.BigDecimal;

import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.FundMode;

public class FundForm {

	/** 此次操作涉及的金额 */
	private BigDecimal money;
	/**
	 * 表示资金是进帐还是出帐
	 * 
	 * @see com.cai310.lottery.common.FundMode
	 */
	private FundMode mode;

	/**
	 * 资金明细类型
	 * 
	 * @see com.cai310.lottery.common.FundDetailType
	 */
	private FundDetailType type;

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public FundMode getMode() {
		return mode;
	}

	public void setMode(FundMode mode) {
		this.mode = mode;
	}

	public FundDetailType getType() {
		return type;
	}

	public void setType(FundDetailType type) {
		this.type = type;
	}

	

}
