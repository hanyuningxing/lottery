package com.cai310.lottery.web.controller.lottery.keno.sdel11to5;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.SdEl11to5Constant;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhScheme;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5Scheme;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.lottery.keno.KenoChasePlanController;
import com.cai310.utils.ReflectionUtils;

@Namespace("/" + SdEl11to5Constant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends KenoChasePlanController<SdEl11to5IssueData, SdEl11to5Scheme> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;

	
	@Resource(name = "sdel11to5KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<SdEl11to5IssueData, SdEl11to5Scheme> kenoService) {
		this.kenoService = kenoService;
	}
	
	public SdEl11to5IssueData getPeriod() {
		return this.period;
	}
	@SuppressWarnings("unchecked")
	public ChaseDetailController() {
		this.schemeClass = ReflectionUtils.getSuperClassGenricType(SdEl11to5Scheme.class, 0);
	}
}
