package com.cai310.lottery.entity.lottery.pl;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cai310.lottery.entity.lottery.SchemeWonInfo;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "PL_SCHEME_WON_INFO")
@Entity
public class PlSchemeWonInfo extends SchemeWonInfo {

	private static final long serialVersionUID = 4981165004505541073L;

	private PlWinUnit winUnit = new PlWinUnit();

	@Embedded
	public PlWinUnit getWinUnit() {
		return winUnit;
	}

	public void setWinUnit(PlWinUnit winUnit) {
		if (winUnit != null) {
			this.winUnit = winUnit;
		} else {
			this.winUnit = new PlWinUnit();
		}
	}

}
