package com.cai310.lottery.entity.lottery.zc;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cai310.lottery.common.Lottery;

/**
 * 六场半全场足彩方案过关信息类.
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "LCZC_PASSCOUNT")
@Entity
public class LczcPasscount extends ZcPasscount{

	private static final long serialVersionUID = -2425610312588917882L;

	@Transient
	public Lottery getLotteryType() {
		return Lottery.LCZC;
	}
	
	/**
	 * @return 方案号
	 */
	@Transient
	public String getSchemeNumber() {
		return this.getLotteryType().getSchemeNumber(this.getSchemeId());
	}

}
