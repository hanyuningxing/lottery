package com.cai310.lottery.web.controller.lottery.dlt;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.DltConstant;
import com.cai310.lottery.entity.lottery.dlt.DltScheme;
import com.cai310.lottery.web.controller.lottery.ChasePlanController;

@Namespace("/" + DltConstant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends ChasePlanController<DltScheme> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;

}
