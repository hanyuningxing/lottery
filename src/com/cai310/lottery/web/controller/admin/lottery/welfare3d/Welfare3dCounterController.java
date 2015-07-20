package com.cai310.lottery.web.controller.admin.lottery.welfare3d;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.Welfare3dConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dScheme;
import com.cai310.lottery.web.controller.admin.lottery.CounterController;

@Action(Welfare3dConstant.KEY)
public class Welfare3dCounterController extends CounterController<Welfare3dScheme> {
	private static final long serialVersionUID = 7335729722450488788L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE3D;
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
