package com.cai310.lottery.entity.lottery.welfare3d;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cai310.lottery.entity.lottery.SchemeWonInfo;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "W3D_SCHEME_WON_INFO")
@Entity
public class Welfare3dSchemeWonInfo extends SchemeWonInfo {

	private static final long serialVersionUID = 4981165004505541073L;

	private Welfare3dWinUnit winUnit = new Welfare3dWinUnit();

	@Embedded
	public Welfare3dWinUnit getWinUnit() {
		return winUnit;
	}

	public void setWinUnit(Welfare3dWinUnit winUnit) {
		if (winUnit != null) {
			this.winUnit = winUnit;
		} else {
			this.winUnit = new Welfare3dWinUnit();
		}
	}

}
