package com.cai310.lottery.dto.lottery.jclq;

import java.util.List;

import com.cai310.lottery.dto.lottery.SchemeDTO;
import com.cai310.lottery.support.jclq.PassMode;
import com.cai310.lottery.support.jclq.PassType;
import com.cai310.lottery.support.jclq.PlayType;
import com.cai310.lottery.support.jclq.SchemeType;

/**
 * 竞彩篮球方案发起的数据传输对象
 */
public class JclqSchemeDTO extends SchemeDTO {
	private static final long serialVersionUID = 1702077599053626560L;

	/** 玩法类型 */
	private PlayType playType;

	/** 过关方式 */
	private List<PassType> passTypes;

	/** 方案类型 */
	private SchemeType schemeType;

	/** 过关模式 */
	private PassMode passMode;
	
	/** 是否为优化方案*/
	private boolean isOptimize;
	
	private String ticketContent;

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
	 * @return the schemeType
	 */
	public SchemeType getSchemeType() {
		return schemeType;
	}

	/**
	 * @param schemeType the schemeType to set
	 */
	public void setSchemeType(SchemeType schemeType) {
		this.schemeType = schemeType;
	}

	/**
	 * @return the passMode
	 */
	public PassMode getPassMode() {
		return passMode;
	}

	/**
	 * @param passMode the passMode to set
	 */
	public void setPassMode(PassMode passMode) {
		this.passMode = passMode;
	}

	public String getTicketContent() {
		return ticketContent;
	}

	public void setTicketContent(String ticketContent) {
		this.ticketContent = ticketContent;
	}
	
	/**
	 * 是否为优化方案
	 * @return
	 */
	public boolean isOptimize() {
		return isOptimize;
	}

	public void setOptimize(boolean isOptimize) {
		this.isOptimize = isOptimize;
	}

}
