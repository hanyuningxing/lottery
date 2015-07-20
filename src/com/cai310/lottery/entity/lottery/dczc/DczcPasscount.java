package com.cai310.lottery.entity.lottery.dczc;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.type.EnumType;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.WinRecordType;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.entity.lottery.SchemePasscount;
import com.cai310.lottery.support.dczc.PassMode;
import com.cai310.lottery.support.dczc.PassType;
import com.cai310.lottery.support.dczc.PlayType;

/**
 * 北单足彩方案过关信息类.
 * 
 */
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "DCZC_PASSCOUNT")
@Entity
public class DczcPasscount extends SchemePasscount {

	private static final long serialVersionUID = 1L;

	/**
	 * 玩法类型
	 * 
	 * @see com.cai310.lottery.support.dczc.PlayType
	 */
	private PlayType playType;

	/** 选择的场次 */
	private int betCount;

	/** 全中的注数 */
	private int wonCount;

	/** 命中场次 */
	private Integer passcount;

	/** 比赛状态 */
	private WinningUpdateStatus finsh;
	/**
	 * 过关模式
	 * 
	 * @see com.cai310.lottery.support.dczc.PassMode
	 * */
	private PassMode passMode;

	/**
	 * @return {@link #playType}
	 */
	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.support.dczc.PlayType"),
			@Parameter(name = EnumType.TYPE, value = PlayType.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	@Transient
	public Lottery getLotteryType() {
		return Lottery.DCZC;
	}

	/**
	 * @return 方案号
	 */
	@Transient
	public String getSchemeNumber() {
		return this.getLotteryType().getSchemeNumber(this.getSchemeId());
	}

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.common.WinningUpdateStatus"),
			@Parameter(name = EnumType.TYPE, value = PassMode.SQL_TYPE) })
	@Column(nullable = false)
	public WinningUpdateStatus getFinsh() {
		return finsh;
	}

	public void setFinsh(WinningUpdateStatus finsh) {
		this.finsh = finsh;
	}

	/**
	 * @return {@link #passMode}
	 */

	@Type(type = "org.hibernate.type.EnumType", parameters = {
			@Parameter(name = EnumType.ENUM, value = "com.cai310.lottery.support.dczc.PassMode"),
			@Parameter(name = EnumType.TYPE, value = PassMode.SQL_TYPE) })
	@Column(nullable = false, updatable = false)
	public PassMode getPassMode() {
		return passMode;
	}

	/**
	 * @param passMode
	 *            the {@link #passMode} to set
	 */
	public void setPassMode(PassMode passMode) {
		this.passMode = passMode;
	}

	/** 过关方式 */
	private int passType;

	/**
	 * @return {@link #passType}
	 */
	public int getPassType() {
		return passType;
	}

	/**
	 * @param passType
	 *            the {@link #passType} to set
	 */
	public void setPassType(int passType) {
		this.passType = passType;
	}

	@Transient
	public List<PassType> getPassTypes() {
		return PassType.getPassTypes(passType);
	}

	public int getBetCount() {
		return betCount;
	}

	public void setBetCount(int betCount) {
		this.betCount = betCount;
	}

	public int getWonCount() {
		return wonCount;
	}

	public void setWonCount(int wonCount) {
		this.wonCount = wonCount;
	}

	/**
	 * @return the passcount
	 */
	@Column(name = "passcount")
	public Integer getPasscount() {
		return passcount;
	}

	/**
	 * @param passcount
	 *            the passcount to set
	 */
	public void setPasscount(Integer passcount) {
		this.passcount = passcount;
	}

}
