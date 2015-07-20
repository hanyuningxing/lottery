package com.cai310.lottery.web.controller.info.results;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.SfzcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.zc.SfzcPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SfzcPeriodDataEntityManagerImpl;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.lottery.web.controller.info.ResultController;

@Namespace("/" + SfzcConstant.KEY)
@Action("result")
public class SfzcResultController extends ResultController<SfzcPeriodData> {
	private static final long serialVersionUID = 3065749835620298892L;

	private PlayType playType;
	
	@Override
	public Lottery getLotteryType() {
		return Lottery.SFZC;
	}

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}
	@Autowired
	private SfzcPeriodDataEntityManagerImpl sfzcPeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<SfzcPeriodData> getPeriodDataEntityManagerImpl() {
		return sfzcPeriodDataEntityManagerImpl;
	}
}
