package com.cai310.lottery.entity.lottery.tc22to5;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cai310.lottery.entity.lottery.SchemeWonInfo;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "TC22TO5_SCHEME_WON_INFO")
@Entity
public class Tc22to5SchemeWonInfo extends SchemeWonInfo {
 
	private static final long serialVersionUID = 3484429817436044832L;
	
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

}
