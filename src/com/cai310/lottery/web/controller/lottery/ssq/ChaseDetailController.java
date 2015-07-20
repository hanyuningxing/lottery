package com.cai310.lottery.web.controller.lottery.ssq;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.SsqConstant;
import com.cai310.lottery.entity.lottery.ssq.SsqScheme;
import com.cai310.lottery.web.controller.lottery.ChasePlanController;

@Namespace("/" + SsqConstant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends ChasePlanController<SsqScheme> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;

}
