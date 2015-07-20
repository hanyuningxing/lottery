package com.cai310.lottery.web.controller.info.results;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.PlConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.pl.PlPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.pl.impl.PlPeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.info.ResultController;

@Namespace("/" + PlConstant.KEY)
@Action("result")
public class PlResultController extends ResultController<PlPeriodData> {
	private static final long serialVersionUID = 8808622196768020913L;
	private Integer playType;///1是排五。0是排三
	@Override
	public Lottery getLotteryType() {
		return Lottery.PL;
	}
	/**
	 * @return the playType
	 */
	public Integer getPlayType() {
		return playType;
	}

	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(Integer playType) {
		this.playType = playType;
	}
	@Autowired
	private PlPeriodDataEntityManagerImpl plPeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<PlPeriodData> getPeriodDataEntityManagerImpl() {
		return plPeriodDataEntityManagerImpl;
	}
}
