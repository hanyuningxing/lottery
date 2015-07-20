package com.cai310.lottery.entity.lottery.welfare36to7;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.SchemePasscount;
import com.cai310.lottery.support.welfare36to7.Welfare36to7PlayType;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "W36TO7_PASSCOUNT")
@Entity
public class Welfare36to7Passcount extends SchemePasscount{ 
	
	private static final long serialVersionUID = -1455809927726557074L;

	private Welfare36to7WinUnit winUnit = new Welfare36to7WinUnit();

	private Welfare36to7PlayType playType;
	
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
	@Override
	@Transient
	public String getSchemeNumber() {
		return Lottery.WELFARE36To7.getSchemeNumber(super.schemeId);
	}

	@Column(nullable = false)
	public Welfare36to7PlayType getPlayType() {
		return playType;
	}
	/**
	 * @param betType the betType to set
	 */
	public void setPlayType(Welfare36to7PlayType playType) {
		this.playType = playType;
	}
	
	/**
	 * @return 是否中奖
	 */
	@Transient
	public boolean isWon() {
		return this.winUnit.isWon();
	}
	

}
