package com.cai310.lottery.dto.lottery.dczc;

import java.util.List;

import com.cai310.lottery.dto.lottery.SchemeDTO;
import com.cai310.lottery.support.dczc.PassMode;
import com.cai310.lottery.support.dczc.PassType;
import com.cai310.lottery.support.dczc.PlayType;

/**
 * 单场足彩方案发起的数据传输对象
 */
public class DczcSchemeDTO extends SchemeDTO {
	private static final long serialVersionUID = 1702077599053626560L;

	/** 玩法类型 */
	private PlayType playType;

	/** 过关方式 */
	private List<PassType> passTypes;

	/** 过关模式 */
	private PassMode passMode;

	/**
	 * @return {@link #playType}
	 */
	public PlayType getPlayType() {
		return playType;
	}

	/**
	 * @param playType the {@link #playType} to set
	 */
	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	/**
	 * @return {@link #passTypes}
	 */
	public List<PassType> getPassTypes() {
		return passTypes;
	}

	/**
	 * @param passTypes the {@link #passTypes} to set
	 */
	public void setPassTypes(List<PassType> passTypes) {
		this.passTypes = passTypes;
	}

	/**
	 * @return {@link #passMode}
	 */
	public PassMode getPassMode() {
		return passMode;
	}

	/**
	 * @param passMode the {@link #passMode} to set
	 */
	public void setPassMode(PassMode passMode) {
		this.passMode = passMode;
	}

}
