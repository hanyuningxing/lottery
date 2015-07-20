package com.cai310.lottery.web.controller.info.results;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.SczcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.zc.SczcPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SczcPeriodDataEntityManagerImpl;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.lottery.web.controller.info.ResultController;

@Namespace("/" + SczcConstant.KEY)
@Action("result")
public class SczcResultController extends ResultController<SczcPeriodData> {
	private static final long serialVersionUID = -2084447571315683057L;
	private PlayType playType;
	@Override
	public Lottery getLotteryType() {
		return Lottery.SCZC;
	}
	/**
	 * @return the playType
	 */
	public PlayType getPlayType() {
		return playType;
	}
	/**
	 * @param playType the playType to set
	 */
	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}
	@Autowired
	private SczcPeriodDataEntityManagerImpl sczcPeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<SczcPeriodData> getPeriodDataEntityManagerImpl() {
		return sczcPeriodDataEntityManagerImpl;
	}
}
