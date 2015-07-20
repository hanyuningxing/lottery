package com.cai310.lottery.web.controller.lottery.tc22to5;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.Tc22to5Constant;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5Scheme;
import com.cai310.lottery.web.controller.lottery.ChasePlanController;

@Namespace("/" + Tc22to5Constant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends ChasePlanController<Tc22to5Scheme> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5055460262815703546L;



}
