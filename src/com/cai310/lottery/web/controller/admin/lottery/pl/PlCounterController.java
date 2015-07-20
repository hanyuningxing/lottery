package com.cai310.lottery.web.controller.admin.lottery.pl;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.PlConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.pl.PlScheme;
import com.cai310.lottery.web.controller.admin.lottery.CounterController;

@Action(PlConstant.KEY)
public class PlCounterController extends CounterController<PlScheme> {
	private static final long serialVersionUID = 954505427116074543L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.PL;
	}

	public DetachedCriteria createCountCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(schemeClass);
		if(StringUtils.isNotBlank(periodNumber)){
			criteria.add(Restrictions.eq("periodNumber", periodNumber.trim()));
			criteria.add(Restrictions.in("state",SchemeState.finalStatuss));
		}
		criteria.addOrder(Order.asc("commitTime"));
		
		return criteria;
	}
}
