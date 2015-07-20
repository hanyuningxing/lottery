package com.cai310.lottery.web.controller.lottery.keno.ssc;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.SscConstant;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5Scheme;
import com.cai310.lottery.entity.lottery.keno.ssc.SscIssueData;
import com.cai310.lottery.entity.lottery.keno.ssc.SscScheme;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.lottery.keno.KenoChasePlanController;
import com.cai310.utils.ReflectionUtils;

@Namespace("/" + SscConstant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends KenoChasePlanController<SscIssueData, SscScheme> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;

	
	@Resource(name = "sscKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<SscIssueData, SscScheme> kenoService) {
		this.kenoService = kenoService;
	}
	
	public SscIssueData getPeriod() {
		return this.period;
	}

	@SuppressWarnings("unchecked")
	public ChaseDetailController() {
		this.schemeClass = ReflectionUtils.getSuperClassGenricType(SscScheme.class, 0);
	}
}
