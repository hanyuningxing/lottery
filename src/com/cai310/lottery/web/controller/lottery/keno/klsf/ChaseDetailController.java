package com.cai310.lottery.web.controller.lottery.keno.klsf;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.KlsfConstant;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5Scheme;
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfIssueData;
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfScheme;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.lottery.ChasePlanController;
import com.cai310.lottery.web.controller.lottery.keno.KenoChasePlanController;
import com.cai310.utils.ReflectionUtils;

@Namespace("/" + KlsfConstant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends  KenoChasePlanController<KlsfIssueData, KlsfScheme> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;
	
	@Resource(name = "klsfKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<KlsfIssueData, KlsfScheme> kenoService) {
		this.kenoService = kenoService;
	}	

	public KlsfIssueData getPeriod() {
		return this.period;
	}
	
	@SuppressWarnings("unchecked")
	public ChaseDetailController() {
		this.schemeClass = ReflectionUtils.getSuperClassGenricType(KlsfScheme.class, 0);
	}
}
