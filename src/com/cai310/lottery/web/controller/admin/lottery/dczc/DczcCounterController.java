package com.cai310.lottery.web.controller.admin.lottery.dczc;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.DczcConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.web.controller.admin.lottery.CounterController;

@Action(DczcConstant.KEY)
public class DczcCounterController extends CounterController<DczcScheme> {
	private static final long serialVersionUID = -1082875672717113862L;

	protected PlayType playType;

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.DCZC;
	}

	public DetachedCriteria createCountCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(schemeClass);
		if (StringUtils.isNotBlank(periodNumber)) {
			criteria.add(Restrictions.eq("periodNumber", periodNumber.trim()));
			criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
		}
		if (null != playType) {
			criteria.add(Restrictions.eq("playType", playType));
		}
		criteria.addOrder(Order.asc("commitTime"));
		return criteria;
	}

}
