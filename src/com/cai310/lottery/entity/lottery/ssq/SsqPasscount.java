package com.cai310.lottery.entity.lottery.ssq;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.SchemePasscount;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SSQ_PASSCOUNT")
@Entity
public class SsqPasscount extends SchemePasscount   {

	private static final long serialVersionUID = 4981165004505541073L;

	private SsqWinUnit winUnit = new SsqWinUnit();
	
	@Override
	@Transient
	public String getSchemeNumber() {
		return Lottery.SSQ.getSchemeNumber(super.schemeId);
	}

	@Embedded
	public SsqWinUnit getWinUnit() {
		return winUnit;
	}

	public void setWinUnit(SsqWinUnit winUnit) {
		if (winUnit != null) {
			this.winUnit = winUnit;
		} else {
			this.winUnit = new SsqWinUnit();
		}
	}

	
	/**
	 * @return 是否中奖
	 */
	@Transient
	public boolean isWon() {
		return this.winUnit.isWon();
	}
	

}
