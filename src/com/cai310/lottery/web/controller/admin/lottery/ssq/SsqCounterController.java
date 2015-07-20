package com.cai310.lottery.web.controller.admin.lottery.ssq;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.SsqConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.ssq.SsqScheme;
import com.cai310.lottery.web.controller.admin.lottery.CounterController;

@Action(SsqConstant.KEY)
public class SsqCounterController extends CounterController<SsqScheme> {
	private static final long serialVersionUID = -5462300535442825971L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SSQ;
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
