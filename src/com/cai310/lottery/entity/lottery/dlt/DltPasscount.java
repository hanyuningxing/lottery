package com.cai310.lottery.entity.lottery.dlt;

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
import com.cai310.lottery.support.dlt.DltPlayType;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "DLT_PASSCOUNT")
@Entity
public class DltPasscount extends SchemePasscount {

	private static final long serialVersionUID = 4981165004505541073L;

	private DltWinUnit winUnit = new DltWinUnit();

	private DltPlayType playType;

	@Transient
	public String getSchemeNumber() {
		return Lottery.DLT.getSchemeNumber(super.schemeId);
	}

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

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.support.dlt.DltPlayType"),
			@Parameter(name = EnumType.TYPE, value = Welfare3dPlayType.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public DltPlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType
	 *            the playType to set
	 */
	public void setPlayType(DltPlayType playType) {
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
