package com.cai310.lottery.web.controller.lottery.welfare3d;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.Welfare3dConstant;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dScheme;
import com.cai310.lottery.web.controller.lottery.ChasePlanController;

@Namespace("/" + Welfare3dConstant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends ChasePlanController<Welfare3dScheme> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;

}
