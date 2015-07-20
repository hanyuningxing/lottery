package com.cai310.lottery.web.controller.info.results;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.SevenConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.seven.SevenPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.seven.impl.SevenPeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.info.ResultController;

@Namespace("/" + SevenConstant.KEY)
@Action("result")
public class SevenResultController extends ResultController<SevenPeriodData> {
	private static final long serialVersionUID = 7966177671954967060L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SEVEN;
	}
	@Autowired
	private SevenPeriodDataEntityManagerImpl sevenPeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<SevenPeriodData> getPeriodDataEntityManagerImpl() {
		return sevenPeriodDataEntityManagerImpl;
	}
}
