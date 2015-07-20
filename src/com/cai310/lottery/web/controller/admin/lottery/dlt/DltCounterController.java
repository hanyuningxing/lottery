package com.cai310.lottery.web.controller.admin.lottery.dlt;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.DltConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.dlt.DltScheme;
import com.cai310.lottery.web.controller.admin.lottery.CounterController;

@Action(DltConstant.KEY)
public class DltCounterController extends CounterController<DltScheme> {
	private static final long serialVersionUID = 4471883651792293436L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.DLT;
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
