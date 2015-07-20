package com.cai310.lottery.web.controller.admin.lottery.welfare36to7;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.Welfare36to7Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7Scheme;
import com.cai310.lottery.support.welfare36to7.Welfare36to7PlayType;
import com.cai310.lottery.web.controller.admin.lottery.CounterController;

@Action(Welfare36to7Constant.KEY)
public class Welfare36to7CounterController extends CounterController<Welfare36to7Scheme> {
	private static final long serialVersionUID = -5873377678285024443L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE36To7;
	}
	
	protected Welfare36to7PlayType playType;
	
	public DetachedCriteria createCountCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(schemeClass);
		if(StringUtils.isNotBlank(periodNumber)){
			criteria.add(Restrictions.eq("periodNumber", periodNumber.trim()));
			criteria.add(Restrictions.in("state",SchemeState.finalStatuss));
		}
		if (null != playType) {
			criteria.add(Restrictions.eq("playType", playType));
		}
		criteria.addOrder(Order.asc("commitTime"));
		
		return criteria;
	}

	public Welfare36to7PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(Welfare36to7PlayType playType) {
		this.playType = playType;
	}
}
