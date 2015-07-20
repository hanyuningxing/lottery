package com.cai310.lottery.dto.lottery.jczq;

import java.util.List;

import com.cai310.lottery.dto.lottery.SchemeDTO;
import com.cai310.lottery.support.jczq.PassMode;
import com.cai310.lottery.support.jczq.PassType;
import com.cai310.lottery.support.jczq.PlayType;
import com.cai310.lottery.support.jczq.PlayTypeWeb;
import com.cai310.lottery.support.jczq.SchemeType;

/**
 * 竞彩足球方案发起的数据传输对象
 */
public class JczqSchemeDTO extends SchemeDTO {
	private static final long serialVersionUID = 1702077599053626560L;

	/** 玩法类型 */
	private PlayType playType;
	
	/** 追号方案详情ID **/
	private Long jczqChasePlanDetailId;
	
	/** 页面玩法类型*/
	private PlayTypeWeb playTypeWeb;

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

	public PlayTypeWeb getPlayTypeWeb() {
		return playTypeWeb;
	}

	public void setPlayTypeWeb(PlayTypeWeb playTypeWeb) {
		this.playTypeWeb = playTypeWeb;
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

	public Long getJczqChasePlanDetailId() {
		return jczqChasePlanDetailId;
	}

	public void setJczqChasePlanDetailId(Long jczqChasePlanDetailId) {
		this.jczqChasePlanDetailId = jczqChasePlanDetailId;
	}
	
	public boolean isChasePlanScheme() {
		if(this.jczqChasePlanDetailId == null)
			return false;
		else 
			return true;
	}

}
