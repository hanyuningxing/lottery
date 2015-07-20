package com.cai310.lottery.entity.lottery.dlt;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cai310.lottery.entity.lottery.SchemeWonInfo;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "DLT_SCHEME_WON_INFO")
@Entity
public class DltSchemeWonInfo extends SchemeWonInfo {

	private static final long serialVersionUID = 4981165004505541073L;

	private DltWinUnit winUnit = new DltWinUnit();

	@Embedded
	public DltWinUnit getWinUnit() {
		return winUnit;
	}

	public void setWinUnit(DltWinUnit winUnit) {
		if (winUnit != null) {
			this.winUnit = winUnit;
		} else {
			this.winUnit = new DltWinUnit();
		}
	}

}
