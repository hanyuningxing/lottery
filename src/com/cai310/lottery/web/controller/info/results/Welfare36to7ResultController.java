package com.cai310.lottery.web.controller.info.results;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Welfare36to7Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7PeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare36to7.impl.Welfare36to7PeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.info.ResultController;

@Namespace("/" + Welfare36to7Constant.KEY)
@Action("result")
public class Welfare36to7ResultController extends ResultController<Welfare36to7PeriodData> {
	private static final long serialVersionUID = -6819351883351688149L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE36To7;
	}
	@Autowired
	private Welfare36to7PeriodDataEntityManagerImpl welfare36to7PeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<Welfare36to7PeriodData> getPeriodDataEntityManagerImpl() {
		return welfare36to7PeriodDataEntityManagerImpl;
	}

}
