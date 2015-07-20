package com.cai310.lottery.web.controller.info.passcount;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Tc22to5Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5Passcount;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5PeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.tc22to5.impl.Tc22to5PeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.info.PasscountController;
import com.cai310.orm.XDetachedCriteria;

@Namespace("/" + Tc22to5Constant.KEY)
@Action("passcount")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/info/passcount/index.ftl")
	})
public class Tc22to5countController extends PasscountController<Tc22to5Passcount,Tc22to5PeriodData> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private Tc22to5PeriodDataEntityManagerImpl tc22to5PeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<Tc22to5PeriodData> getPeriodDataEntityManagerImpl() {
		return tc22to5PeriodDataEntityManagerImpl;
	}
	

	@Override
	public Lottery getLotteryType() {
		return Lottery.TC22TO5;
	}
	
	public  XDetachedCriteria addDetachedCriteria(XDetachedCriteria criteria){
		criteria.addOrder(Order.asc("state"));
		criteria.addOrder(Order.desc("winUnit.firstWinUnits"));
		criteria.addOrder(Order.desc("winUnit.secondWinUnits"));
		criteria.addOrder(Order.desc("winUnit.thirdWinUnits"));
		criteria.addOrder(Order.asc("units"));
		return criteria;
	}

	
}
