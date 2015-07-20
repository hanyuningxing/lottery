package com.cai310.lottery.web.controller.admin.lottery.seven;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.SevenConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.seven.SevenScheme;
import com.cai310.lottery.web.controller.admin.lottery.CounterController;

@Action(SevenConstant.KEY)
public class SevenCounterController extends CounterController<SevenScheme> {
	private static final long serialVersionUID = 7236090271861628211L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SEVEN;
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
