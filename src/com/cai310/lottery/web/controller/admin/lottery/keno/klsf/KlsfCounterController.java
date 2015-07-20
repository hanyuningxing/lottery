package com.cai310.lottery.web.controller.admin.lottery.keno.klsf;

import org.apache.struts2.convention.annotation.Action;
import org.hibernate.criterion.DetachedCriteria;

import com.cai310.lottery.KlsfConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfScheme;
import com.cai310.lottery.web.controller.admin.lottery.KenoCounterController;

@Action(KlsfConstant.KEY)
public class KlsfCounterController extends KenoCounterController<KlsfScheme> {
	private static final long serialVersionUID = 1671475196458516195L;

	@Override
	public Lottery getLotteryType() {
		return Lottery.KLSF;
	}

	@Override
	public DetachedCriteria createCountCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

}
