package com.cai310.lottery.web.controller.info.passcount;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.PlConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.pl.PlPasscount;
import com.cai310.lottery.entity.lottery.pl.PlPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.pl.impl.PlPeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.info.PasscountController;
import com.cai310.orm.XDetachedCriteria;

@Namespace("/" + PlConstant.KEY)
@Action("passcount")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/info/passcount/index.ftl")
	})
public class PlcountController extends PasscountController<PlPasscount,PlPeriodData> {
	private static final long serialVersionUID = -3004288085663395253L;
	
	@Autowired
	private PlPeriodDataEntityManagerImpl plPeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<PlPeriodData> getPeriodDataEntityManagerImpl() {
		return plPeriodDataEntityManagerImpl;
	}
	
	public String index() throws WebDataException {
	 
		return super.index();
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.PL;
	}
	

	
	
	public  XDetachedCriteria addDetachedCriteria(XDetachedCriteria criteria){
		if(null!=super.queryForm.getPlPlayType()) {
			criteria.add(Restrictions.eq("m.playType", super.queryForm.getPlPlayType()));
		}
		criteria.addOrder(Order.asc("state"));
		criteria.addOrder(Order.desc("winUnit.p5WinUnits"));
		criteria.addOrder(Order.desc("winUnit.p3WinUnits"));
		criteria.addOrder(Order.desc("winUnit.p3G3WinUnits"));
		criteria.addOrder(Order.desc("winUnit.p3G6WinUnits"));
		criteria.addOrder(Order.asc("units"));
		return criteria;
	}
}
