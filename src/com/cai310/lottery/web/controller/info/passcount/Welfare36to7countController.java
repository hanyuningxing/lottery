package com.cai310.lottery.web.controller.info.passcount;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Welfare36to7Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7Passcount;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7PeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare36to7.impl.Welfare36to7PeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.info.PasscountController;
import com.cai310.orm.XDetachedCriteria;

@Namespace("/" + Welfare36to7Constant.KEY)
@Action("passcount")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/info/passcount/index.ftl")
	})
public class Welfare36to7countController extends PasscountController<Welfare36to7Passcount,Welfare36to7PeriodData> {
	private static final long serialVersionUID = 1L;

 
	@Autowired
	private Welfare36to7PeriodDataEntityManagerImpl welfare36to7PeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<Welfare36to7PeriodData> getPeriodDataEntityManagerImpl() {
		return welfare36to7PeriodDataEntityManagerImpl;
	}
	

	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE36To7;
	}
	
	public  XDetachedCriteria addDetachedCriteria(XDetachedCriteria criteria){
		if(null!=super.queryForm.getWelfare36to7playType()) {
			criteria.add(Restrictions.eq("m.playType", super.queryForm.getWelfare36to7playType()));
		}
		criteria.addOrder(Order.asc("state"));
		criteria.addOrder(Order.desc("winUnit.firstWinUnits"));
		criteria.addOrder(Order.desc("winUnit.secondWinUnits"));
		criteria.addOrder(Order.desc("winUnit.thirdWinUnits"));
		criteria.addOrder(Order.asc("units"));
		return criteria;
	}

	
}
