package com.cai310.lottery.web.controller.info.results;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.SevenstarConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.sevenstar.SevenstarPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.sevenstar.impl.SevenstarPeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.info.ResultController;

@Namespace("/" + SevenstarConstant.KEY)
@Action("result")
public class SevenstarResultController extends ResultController<SevenstarPeriodData> {
	private static final long serialVersionUID = 7966177671954967060L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SEVENSTAR;
	}
	@Autowired
	private SevenstarPeriodDataEntityManagerImpl sevenstarPeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<SevenstarPeriodData> getPeriodDataEntityManagerImpl() {
		return sevenstarPeriodDataEntityManagerImpl;
	}
}
