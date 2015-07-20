package com.cai310.lottery.web.controller.lottery.dczc;

import java.util.List;

import com.cai310.lottery.support.dczc.DczcExtraMatchItem;
import com.cai310.lottery.support.dczc.PassType;

public class PrizeForecastForm {
	private List<DczcExtraMatchItem> matchItemList;
	private List<PassType> passTypes;
	private Integer danMinHit;
	private Integer danMaxHit;
	private Integer multiple;

	public List<DczcExtraMatchItem> getMatchItemList() {
		return matchItemList;
	}

	public void setMatchItemList(List<DczcExtraMatchItem> matchItemList) {
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
