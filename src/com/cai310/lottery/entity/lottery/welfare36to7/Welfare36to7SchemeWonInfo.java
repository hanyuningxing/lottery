package com.cai310.lottery.entity.lottery.welfare36to7;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cai310.lottery.entity.lottery.SchemeWonInfo;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "W36TO7_SCHEME_WON_INFO")
@Entity
public class Welfare36to7SchemeWonInfo extends SchemeWonInfo {

	private static final long serialVersionUID = 4981165004505541073L;

	private Welfare36to7WinUnit winUnit = new Welfare36to7WinUnit();

	@Embedded
	public Welfare36to7WinUnit getWinUnit() {
		return winUnit;
	}

	public void setWinUnit(Welfare36to7WinUnit winUnit) {
		if (winUnit != null) {
			this.winUnit = winUnit;
		} else {
			this.winUnit = new Welfare36to7WinUnit();
		}
	}

}
