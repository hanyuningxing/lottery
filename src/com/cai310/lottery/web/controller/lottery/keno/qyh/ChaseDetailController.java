package com.cai310.lottery.web.controller.lottery.keno.qyh;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.QyhConstant;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5Scheme;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhIssueData;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhScheme;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.lottery.ChasePlanController;
import com.cai310.lottery.web.controller.lottery.keno.KenoChasePlanController;
import com.cai310.utils.ReflectionUtils;

@Namespace("/" + QyhConstant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends  KenoChasePlanController<QyhIssueData, QyhScheme> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;
	
	@Resource(name = "qyhKenoServiceImpl")
	@Override
	public void setKenoService(KenoService<QyhIssueData, QyhScheme> kenoService) {
		this.kenoService = kenoService;
	}	

	public QyhIssueData getPeriod() {
		return this.period;
	}
	
	@SuppressWarnings("unchecked")
	public ChaseDetailController() {
		this.schemeClass = ReflectionUtils.getSuperClassGenricType(QyhScheme.class, 0);
	}
}
