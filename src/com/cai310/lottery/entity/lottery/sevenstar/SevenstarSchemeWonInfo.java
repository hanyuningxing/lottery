package com.cai310.lottery.entity.lottery.sevenstar;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cai310.lottery.entity.lottery.SchemeWonInfo;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SEVENSTAR_SCHEME_WON_INFO")
@Entity
public class SevenstarSchemeWonInfo extends SchemeWonInfo {

	private static final long serialVersionUID = 4981165004505541073L;

	private SevenstarWinUnit winUnit = new SevenstarWinUnit();

	@Embedded
	public SevenstarWinUnit getWinUnit() {
		return winUnit;
	}

	public void setWinUnit(SevenstarWinUnit winUnit) {
		if (winUnit != null) {
			this.winUnit = winUnit;
		} else {
			this.winUnit = new SevenstarWinUnit();
		}
	}

}
