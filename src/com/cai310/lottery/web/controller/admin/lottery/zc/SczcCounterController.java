package com.cai310.lottery.web.controller.admin.lottery.zc;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.SczcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.zc.SczcScheme;
import com.cai310.lottery.web.controller.admin.lottery.CounterController;

@Action(SczcConstant.KEY)
public class SczcCounterController extends CounterController<SczcScheme> {
	private static final long serialVersionUID = -8753229471039885127L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SCZC;
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
