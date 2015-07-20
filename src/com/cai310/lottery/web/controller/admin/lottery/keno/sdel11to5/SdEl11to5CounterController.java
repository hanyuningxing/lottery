package com.cai310.lottery.web.controller.admin.lottery.keno.sdel11to5;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.SdEl11to5Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5Scheme;
import com.cai310.lottery.support.sdel11to5.SdEl11to5PlayType;
import com.cai310.lottery.web.controller.admin.lottery.KenoCounterController;

@Action(SdEl11to5Constant.KEY)
public class SdEl11to5CounterController extends KenoCounterController<SdEl11to5Scheme> {
	private static final long serialVersionUID = 7236090271861628211L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SDEL11TO5;
	}
	private SdEl11to5PlayType playType;

	
	public SdEl11to5PlayType getPlayType() {
		return playType;
	}


	public void setPlayType(SdEl11to5PlayType playType) {
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
