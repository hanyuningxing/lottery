package com.cai310.lottery.web.controller.lottery.keno.el11to5;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.El11to5Constant;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5Scheme;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.lottery.keno.KenoChasePlanController;
import com.cai310.utils.ReflectionUtils;

@Namespace("/" + El11to5Constant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends KenoChasePlanController<El11to5IssueData, El11to5Scheme> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;
	public El11to5IssueData getPeriod() {
		return this.period;
	}
	@Resource(name = "el11to5KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<El11to5IssueData, El11to5Scheme> kenoService) {
		this.kenoService = kenoService;
	}
	

	@SuppressWarnings("unchecked")
	public ChaseDetailController() {
		this.schemeClass = ReflectionUtils.getSuperClassGenricType(El11to5Scheme.class, 0);
	}
}
