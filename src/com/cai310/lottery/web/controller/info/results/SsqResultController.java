package com.cai310.lottery.web.controller.info.results;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.SsqConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.ssq.SsqPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.ssq.impl.SsqPeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.info.ResultController;

@Namespace("/" + SsqConstant.KEY)
@Action("result")
public class SsqResultController extends ResultController<SsqPeriodData> {
	private static final long serialVersionUID = 7966177671954967060L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SSQ;
	}
	@Autowired
	private SsqPeriodDataEntityManagerImpl ssqPeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<SsqPeriodData> getPeriodDataEntityManagerImpl() {
		return ssqPeriodDataEntityManagerImpl;
	}
}
