package com.cai310.lottery.web.controller.lottery.sevenstar;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import com.cai310.lottery.SevenstarConstant;
import com.cai310.lottery.entity.lottery.sevenstar.SevenstarScheme;
import com.cai310.lottery.web.controller.lottery.ChasePlanController;

@Namespace("/" + SevenstarConstant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends ChasePlanController<SevenstarScheme> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;

}
