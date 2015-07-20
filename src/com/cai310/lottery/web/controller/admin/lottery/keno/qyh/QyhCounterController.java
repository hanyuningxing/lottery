package com.cai310.lottery.web.controller.admin.lottery.keno.qyh;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cai310.lottery.QyhConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhScheme;
import com.cai310.lottery.support.qyh.QyhPlayType;
import com.cai310.lottery.web.controller.admin.lottery.KenoCounterController;

@Action(QyhConstant.KEY)
public class QyhCounterController extends KenoCounterController<QyhScheme> {
	private static final long serialVersionUID = 7236090271861628211L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.QYH;
	}

	private QyhPlayType playType;

	public QyhPlayType getPlayType() {
		return playType;
	}

	public void setPlayType(QyhPlayType playType) {
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
