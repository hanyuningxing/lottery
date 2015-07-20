package com.cai310.lottery.web.controller.admin.lottery.jclq;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.web.controller.admin.lottery.CounterController;
import com.cai310.utils.DateUtil;

@Action(JclqConstant.KEY)
public class JclqCounterController extends CounterController<JclqScheme> {
	private static final long serialVersionUID = -1082875672717113862L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.JCZQ;
	}
	 
	@Override
	public DetachedCriteria createCountCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(schemeClass);
		if(StringUtils.isNotBlank(periodNumber)){
			criteria.add(Restrictions.eq("periodNumber", periodNumber.trim()));
			criteria.add(Restrictions.in("state",SchemeState.finalStatuss));
			//竞彩只统计一个月方案 zhuhui motify 2011-09-02
			criteria.add(Restrictions.gt("createTime", DateUtil.getdecDateOfDate(new Date(), 30)));
		}
		criteria.addOrder(Order.asc("commitTime"));
		
		return criteria;
	}

}
