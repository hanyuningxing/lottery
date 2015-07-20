package com.cai310.lottery.web.controller.info.results;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.LczcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.zc.LczcPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.LczcPeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.info.ResultController;

@Namespace("/" + LczcConstant.KEY)
@Action("result")
public class LczcResultController extends ResultController<LczcPeriodData> {
	private static final long serialVersionUID = -500339323687209762L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.LCZC;
	}
	@Autowired
	private LczcPeriodDataEntityManagerImpl lczcPeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<LczcPeriodData> getPeriodDataEntityManagerImpl() {
		return lczcPeriodDataEntityManagerImpl;
	}
}
