package com.cai310.lottery.entity.lottery.seven;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.SchemePasscount;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SEVEN_PASSCOUNT")
@Entity
public class SevenPasscount extends SchemePasscount {

	private static final long serialVersionUID = -3637162368761468405L;

	private SevenWinUnit winUnit = new SevenWinUnit();

	@Embedded
	public SevenWinUnit getWinUnit() {
		return winUnit;
	}

	public void setWinUnit(SevenWinUnit winUnit) {
		if (winUnit != null) {
			this.winUnit = winUnit;
		} else {
			this.winUnit = new SevenWinUnit();
		}
	}
	
	@Override
	@Transient
	public String getSchemeNumber() {
		return Lottery.SEVEN.getSchemeNumber(super.schemeId);
	}
	
	/**
	 * @return 是否中奖
	 */
	@Transient
	public boolean isWon() {
		return this.winUnit.isWon();
	}

	
}
