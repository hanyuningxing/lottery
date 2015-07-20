package com.cai310.lottery.entity.lottery.welfare3d;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.SchemePasscount;
import com.cai310.lottery.support.welfare3d.Welfare3dPlayType;

@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "W3D_PASSCOUNT")
@Entity
public class Welfare3dPasscount extends SchemePasscount  {

	private static final long serialVersionUID = -1874907474064617287L;

	private Welfare3dWinUnit winUnit = new Welfare3dWinUnit();

	/** 投注方式 */
	private Welfare3dPlayType playType;
	
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

	@Override
	@Transient
	public String getSchemeNumber() {
		return Lottery.WELFARE3D.getSchemeNumber(super.schemeId);
	}

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.support.welfare3d.Welfare3dPlayType"),
			@Parameter(name = EnumType.TYPE, value = Welfare3dPlayType.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public Welfare3dPlayType getPlayType() {
		return playType;
	}

	public void setPlayType(Welfare3dPlayType playType) {
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
