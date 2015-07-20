package com.cai310.lottery.web.controller.lottery.keno.klpk;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.KlpkConstant;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkIssueData;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkScheme;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.lottery.keno.KenoChasePlanController;
import com.cai310.utils.ReflectionUtils;
@Namespace("/" + KlpkConstant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends KenoChasePlanController<KlpkIssueData, KlpkScheme>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -492704338152764311L;

	@Resource(name = "klpkKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<KlpkIssueData, KlpkScheme> kenoService) {
		this.kenoService = kenoService;
		
	}
	public KlpkIssueData getPeriod() {
		return this.period;
	}
	

	@SuppressWarnings("unchecked")
	public ChaseDetailController() {
		this.schemeClass = ReflectionUtils.getSuperClassGenricType(KlpkScheme.class, 0);
	}
}
