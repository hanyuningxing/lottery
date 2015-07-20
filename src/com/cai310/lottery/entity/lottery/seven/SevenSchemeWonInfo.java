package com.cai310.lottery.entity.lottery.seven;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cai310.lottery.entity.lottery.SchemeWonInfo;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SEVEN_SCHEME_WON_INFO")
@Entity
public class SevenSchemeWonInfo extends SchemeWonInfo {

	private static final long serialVersionUID = 4981165004505541073L;

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

}
