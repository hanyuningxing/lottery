package com.cai310.lottery.web.controller.lottery.jczq;

import java.util.List;

import com.cai310.lottery.support.jczq.JczqExtraMatchItem;
import com.cai310.lottery.support.jczq.PassType;

public class PrizeForecastForm {
	private List<JczqExtraMatchItem> matchItemList;
	private List<PassType> passTypes;
	private Integer danMinHit;
	private Integer danMaxHit;
	private Integer multiple;

	public List<JczqExtraMatchItem> getMatchItemList() {
		return matchItemList;
	}

	public void setMatchItemList(List<JczqExtraMatchItem> matchItemList) {
		this.matchItemList = matchItemList;
	}

	public List<PassType> getPassTypes() {
		return passTypes;
	}

	public void setPassTypes(List<PassType> passTypes) {
		this.passTypes = passTypes;
	}

	public Integer getDanMinHit() {
		return danMinHit;
	}

	public void setDanMinHit(Integer danMinHit) {
		this.danMinHit = danMinHit;
	}

	public Integer getDanMaxHit() {
		return danMaxHit;
	}

	public void setDanMaxHit(Integer danMaxHit) {
		this.danMaxHit = danMaxHit;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
}
