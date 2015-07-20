package com.cai310.lottery.entity.lottery.zc;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cai310.lottery.common.Lottery;

/**
 * 胜负足彩方案过关信息类.
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SCZC_PASSCOUNT")
@Entity
public class SczcPasscount extends ZcPasscount {

	private static final long serialVersionUID = 2512704994591879380L;

	@Transient
	public Lottery getLotteryType() {
		return Lottery.SCZC;
	}

	/**
	 * @return 方案号
	 */
	@Transient
	public String getSchemeNumber() {
		return this.getLotteryType().getSchemeNumber(this.getSchemeId());
	}
}
