package com.cai310.lottery.web.controller.info.passcount;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.SsqConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.ssq.SsqPasscount;
import com.cai310.lottery.entity.lottery.ssq.SsqPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.ssq.impl.SsqPeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.info.PasscountController;
import com.cai310.orm.XDetachedCriteria;

@Namespace("/" + SsqConstant.KEY)
@Action("passcount")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/info/passcount/index.ftl")
	})
public class SsqcountController extends PasscountController<SsqPasscount,SsqPeriodData> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SsqPeriodDataEntityManagerImpl ssqPeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<SsqPeriodData> getPeriodDataEntityManagerImpl() {
		return ssqPeriodDataEntityManagerImpl;
	}
	



	@Override
	public Lottery getLotteryType() {
		return Lottery.SSQ;
	}
	
	public  XDetachedCriteria addDetachedCriteria(XDetachedCriteria criteria){
		criteria.addOrder(Order.asc("state"));
		criteria.addOrder(Order.desc("winUnit.firstWinUnits"));
		criteria.addOrder(Order.desc("winUnit.secondWinUnits"));
		criteria.addOrder(Order.desc("winUnit.thirdWinUnits"));
		criteria.addOrder(Order.desc("winUnit.fourthWinUnits"));
		criteria.addOrder(Order.desc("winUnit.fifthWinUnits"));
		criteria.addOrder(Order.desc("winUnit.sixthWinUnits"));
		criteria.addOrder(Order.asc("units"));
		return criteria;
	}

	
}
