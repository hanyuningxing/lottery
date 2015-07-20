package com.cai310.lottery.web.controller.lottery.seven;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.SevenConstant;
import com.cai310.lottery.entity.lottery.seven.SevenScheme;
import com.cai310.lottery.web.controller.lottery.ChasePlanController;

@Namespace("/" + SevenConstant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends ChasePlanController<SevenScheme> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;

}
