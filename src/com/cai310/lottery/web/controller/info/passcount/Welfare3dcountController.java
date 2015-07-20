package com.cai310.lottery.web.controller.info.passcount;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Welfare3dConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dPasscount;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare3d.impl.Welfare3dPeriodDataEntityManagerImpl;
import com.cai310.lottery.web.controller.info.PasscountController;
import com.cai310.orm.XDetachedCriteria;

@Namespace("/" + Welfare3dConstant.KEY)
@Action("passcount")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/info/passcount/index.ftl")
	})
public class Welfare3dcountController extends PasscountController<Welfare3dPasscount,Welfare3dPeriodData> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private Welfare3dPeriodDataEntityManagerImpl welfare3dPeriodDataEntityManagerImpl;
	
	@Override
	public PeriodDataEntityManagerImpl<Welfare3dPeriodData> getPeriodDataEntityManagerImpl() {
		return welfare3dPeriodDataEntityManagerImpl;
	}
	



	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE3D;
	}
	
	public  XDetachedCriteria addDetachedCriteria(XDetachedCriteria criteria){
	
		if(null!=super.queryForm.getWelfare3dPlayType()) {
			criteria.add(Restrictions.eq("m.playType", super.queryForm.getWelfare3dPlayType()));
		}
		criteria.addOrder(Order.asc("state"));
		criteria.addOrder(Order.desc("winUnit.firstWinUnits"));
		criteria.addOrder(Order.desc("winUnit.secondWinUnits"));
		criteria.addOrder(Order.desc("winUnit.thirdWinUnits"));
		criteria.addOrder(Order.desc("winUnit.fourthWinUnits"));
		criteria.addOrder(Order.desc("winUnit.fifthWinUnits"));
		criteria.addOrder(Order.desc("winUnit.sixthWinUnits"));
		criteria.addOrder(Order.desc("winUnit.sevenWinUnits"));
		criteria.addOrder(Order.asc("units"));
		return criteria;
	}

	
}
