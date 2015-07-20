package com.cai310.lottery.web.controller.lottery.keno.gdel11to5;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.GdEl11to5Constant;
import com.cai310.lottery.entity.lottery.keno.gdel11to5.GdEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.gdel11to5.GdEl11to5Scheme;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.lottery.keno.KenoChasePlanController;
import com.cai310.utils.ReflectionUtils;

@Namespace("/" + GdEl11to5Constant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends KenoChasePlanController<GdEl11to5IssueData, GdEl11to5Scheme> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;

	
	@Resource(name = "gdel11to5KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<GdEl11to5IssueData, GdEl11to5Scheme> kenoService) {
		this.kenoService = kenoService;
	}
	
	public GdEl11to5IssueData getPeriod() {
		return this.period;
	}
	@SuppressWarnings("unchecked")
	public ChaseDetailController() {
		this.schemeClass = ReflectionUtils.getSuperClassGenricType(GdEl11to5Scheme.class, 0);
	}
}
