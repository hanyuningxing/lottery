package com.cai310.lottery.web.controller.info.results;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Tc22to5Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5PeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.tc22to5.impl.Tc22to5PeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.info.ResultController;

@Namespace("/" + Tc22to5Constant.KEY)
@Action("result")
public class Tc22to5ResultController extends ResultController<Tc22to5PeriodData> {
	
	private static final long serialVersionUID = 2697223247020927616L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.TC22TO5;
	}
	@Autowired
	private Tc22to5PeriodDataEntityManagerImpl tc22to5PeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<Tc22to5PeriodData> getPeriodDataEntityManagerImpl() {
		return tc22to5PeriodDataEntityManagerImpl;
	}
}
