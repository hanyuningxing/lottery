package com.cai310.lottery.web.controller.lottery.pl;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.PlConstant;
import com.cai310.lottery.entity.lottery.pl.PlScheme;
import com.cai310.lottery.web.controller.lottery.ChasePlanController;

@Namespace("/" + PlConstant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends ChasePlanController<PlScheme> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;

}
