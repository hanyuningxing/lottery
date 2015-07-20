package com.cai310.lottery.web.controller.info.results;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Welfare3dConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare3d.impl.Welfare3dPeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.info.ResultController;

@Namespace("/" + Welfare3dConstant.KEY)
@Action("result")
public class Welfare3dResultController extends ResultController<Welfare3dPeriodData> {
	private static final long serialVersionUID = -6819351883351688149L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE3D;
	}
	@Autowired
	private Welfare3dPeriodDataEntityManagerImpl welfare3dPeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<Welfare3dPeriodData> getPeriodDataEntityManagerImpl() {
		return welfare3dPeriodDataEntityManagerImpl;
	}

}
