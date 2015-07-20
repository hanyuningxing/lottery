package com.cai310.lottery.web.controller.info.results;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.DltConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.dlt.DltPeriodData;
import com.cai310.lottery.service.lottery.dlt.impl.DltPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.info.ResultController;

@Namespace("/" + DltConstant.KEY)
@Action("result")
public class DltResultController extends ResultController<DltPeriodData> {
	private static final long serialVersionUID = -4017746524382243L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.DLT;
	}
	@Autowired
	private DltPeriodDataEntityManagerImpl dltPeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<DltPeriodData> getPeriodDataEntityManagerImpl() {
		return dltPeriodDataEntityManagerImpl;
	}
}
