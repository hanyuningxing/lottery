package com.cai310.lottery.web.controller.lottery.welfare36to7;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import com.cai310.lottery.Welfare36to7Constant;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7Scheme;
import com.cai310.lottery.web.controller.lottery.ChasePlanController;

@Namespace("/" + Welfare36to7Constant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends ChasePlanController<Welfare36to7Scheme> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;

}
