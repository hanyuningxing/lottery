package com.cai310.lottery.web.controller.admin.lottery.keno.klpk;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.KlpkConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkScheme;
import com.cai310.lottery.support.klpk.KlpkPlayType;
import com.cai310.lottery.web.controller.admin.lottery.KenoCounterController;

@Action(KlpkConstant.KEY)
public class KlpkCounterController extends KenoCounterController<KlpkScheme> {
	private static final long serialVersionUID = 7236090271861628211L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.KLPK;
	}
	private KlpkPlayType playType;

	
	public KlpkPlayType getPlayType() {
		return playType;
	}


	public void setPlayType(KlpkPlayType playType) {
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
