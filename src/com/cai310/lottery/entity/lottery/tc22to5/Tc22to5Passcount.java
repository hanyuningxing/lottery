package com.cai310.lottery.entity.lottery.tc22to5;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.SchemePasscount;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "TC22TO5_PASSCOUNT")
@Entity
public class Tc22to5Passcount extends SchemePasscount  {
	private static final long serialVersionUID = -1455809927726557074L;
	private Tc22to5WinUnit winUnit = new Tc22to5WinUnit();

	@Embedded
	public Tc22to5WinUnit getWinUnit() {
		return winUnit;
	}

	public void setWinUnit(Tc22to5WinUnit winUnit) {
		if (winUnit != null) {
			this.winUnit = winUnit;
		} else {
			this.winUnit = new Tc22to5WinUnit();
		}
	}

	@Override
	@Transient
	public String getSchemeNumber() {
		return Lottery.TC22TO5.getSchemeNumber(super.schemeId);
	}

	/**
	 * @return 是否中奖
	 */
	@Transient
	public boolean isWon() {
		return this.winUnit.isWon();
	}
}
