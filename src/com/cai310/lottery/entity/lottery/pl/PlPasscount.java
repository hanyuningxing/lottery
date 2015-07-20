package com.cai310.lottery.entity.lottery.pl;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.WinRecordType;
import com.cai310.lottery.entity.lottery.SchemePasscount;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "PL_PASSCOUNT")
@Entity
public class PlPasscount extends SchemePasscount   {

	private static final long serialVersionUID = 3846436377262373717L;

	private PlWinUnit winUnit = new PlWinUnit();

	/** 投注方式 */
	private PlPlayType playType;

	@Override
	@Transient
	public String getSchemeNumber() {
		return Lottery.PL.getSchemeNumber(super.schemeId);
	}

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

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.support.pl.PlPlayType"),
			@Parameter(name = EnumType.TYPE, value = Welfare3dPlayType.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public PlPlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType
	 *            the playType to set
	 */
	public void setPlayType(PlPlayType playType) {
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
