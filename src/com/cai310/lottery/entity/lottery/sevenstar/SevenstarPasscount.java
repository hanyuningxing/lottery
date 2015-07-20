package com.cai310.lottery.entity.lottery.sevenstar;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.WinRecordType;
import com.cai310.lottery.entity.lottery.SchemePasscount;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SEVENSTAR_PASSCOUNT")
@Entity
public class SevenstarPasscount extends SchemePasscount {

	private static final long serialVersionUID = -3637162368761468405L;

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
	
	@Override
	@Transient
	public String getSchemeNumber() {
		return Lottery.SEVENSTAR.getSchemeNumber(super.schemeId);
	}
	
	
	/**
	 * @return 是否中奖
	 */
	@Transient
	public boolean isWon() {
		return this.winUnit.isWon();
	}
	
	
}
