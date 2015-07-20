package com.cai310.lottery.web.controller.admin.lottery.keno.ahkuai3;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.AhKuai3Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3Scheme;
import com.cai310.lottery.support.ahkuai3.AhKuai3PlayType;
import com.cai310.lottery.web.controller.admin.lottery.KenoCounterController;

@Action(AhKuai3Constant.KEY)
public class AhKuai3CounterController extends KenoCounterController<AhKuai3Scheme>{


	private static final long serialVersionUID = 3982425900866550626L;
	private AhKuai3PlayType playType;
	
	public AhKuai3PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(AhKuai3PlayType playType) {
		this.playType = playType;
	}

	@Override
	public DetachedCriteria createCountCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(schemeClass);
		criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
		if (null != playType) {
			criteria.add(Restrictions.eq("betType", playType));
		}
		if (StringUtils.isNotBlank(periodNumber)) {
			criteria.add(Restrictions.eq("periodNumber", periodNumber.trim()));
		}
		criteria.add(Restrictions.ge("createTime", super.getBeginTime()));
		criteria.add(Restrictions.le("createTime", super.getEndTime()));
		criteria.addOrder(Order.asc("createTime"));
		return criteria;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.AHKUAI3;
	}
	
}
