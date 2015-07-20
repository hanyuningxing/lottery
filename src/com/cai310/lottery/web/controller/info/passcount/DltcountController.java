package com.cai310.lottery.web.controller.info.passcount;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.DltConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.dlt.DltPasscount;
import com.cai310.lottery.entity.lottery.dlt.DltPeriodData;
import com.cai310.lottery.service.lottery.dlt.impl.DltPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.info.PasscountController;
import com.cai310.orm.XDetachedCriteria;

@Namespace("/" + DltConstant.KEY)
@Action("passcount")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/info/passcount/index.ftl")
	})
public class DltcountController extends PasscountController<DltPasscount,DltPeriodData> {
	private static final long serialVersionUID = -3004288085663395253L;
	@Autowired
	private DltPeriodDataEntityManagerImpl dltPeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<DltPeriodData> getPeriodDataEntityManagerImpl() {
		return dltPeriodDataEntityManagerImpl;
	}
	
	public String index() throws WebDataException {
	 
		return super.index();
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.DLT;
	}
	
	
	public  XDetachedCriteria addDetachedCriteria(XDetachedCriteria criteria){
		criteria.addOrder(Order.asc("state"));
		criteria.addOrder(Order.desc("winUnit.firstWinUnits"));
		criteria.addOrder(Order.desc("winUnit.secondWinUnits"));
		criteria.addOrder(Order.desc("winUnit.thirdWinUnits"));
		criteria.addOrder(Order.desc("winUnit.fourthWinUnits"));
		criteria.addOrder(Order.desc("winUnit.fifthWinUnits"));
		criteria.addOrder(Order.desc("winUnit.sixthWinUnits"));
		criteria.addOrder(Order.desc("winUnit.seventhWinUnits"));
		criteria.addOrder(Order.desc("winUnit.eighthWinUnits"));
		criteria.addOrder(Order.desc("winUnit.select12to2WinUnits"));
		criteria.addOrder(Order.asc("units"));
		return criteria;
	}
}
