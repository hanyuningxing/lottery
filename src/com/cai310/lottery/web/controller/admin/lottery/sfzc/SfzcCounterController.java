package com.cai310.lottery.web.controller.admin.lottery.sfzc;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.SfzcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.zc.SfzcScheme;
import com.cai310.lottery.web.controller.admin.lottery.CounterController;

@Action(SfzcConstant.KEY)
public class SfzcCounterController extends CounterController<SfzcScheme> {
	private static final long serialVersionUID = -646465947804546106L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SFZC;
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
