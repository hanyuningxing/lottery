package com.cai310.lottery.web.controller.admin.lottery.keno.gdel11to5;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.GdEl11to5Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.keno.gdel11to5.GdEl11to5Scheme;
import com.cai310.lottery.support.gdel11to5.GdEl11to5PlayType;
import com.cai310.lottery.web.controller.admin.lottery.KenoCounterController;

@Action(GdEl11to5Constant.KEY)
public class GdEl11to5CounterController extends KenoCounterController<GdEl11to5Scheme> {
	private static final long serialVersionUID = 7236090271861628211L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.GDEL11TO5;
	}
	private GdEl11to5PlayType playType;

	
	public GdEl11to5PlayType getPlayType() {
		return playType;
	}


	public void setPlayType(GdEl11to5PlayType playType) {
		this.playType = playType;
	}


	public DetachedCriteria createCountCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(schemeClass);
		criteria.add(Restrictions.in("state",SchemeState.finalStatuss));
		if(null!=playType) {
			criteria.add(Restrictions.eq("betType", playType));
		}
		if(StringUtils.isNotBlank(periodNumber)){
			criteria.add(Restrictions.eq("periodNumber", periodNumber.trim()));
		}
		criteria.add(Restrictions.ge("createTime", super.getBeginTime()));
		criteria.add(Restrictions.le("createTime", super.getEndTime()));
		criteria.addOrder(Order.asc("createTime"));
		return criteria;
	}
}
