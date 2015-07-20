package com.cai310.lottery.web.controller.lottery.keno.xjel11to5;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.XjEl11to5Constant;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5Scheme;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.lottery.keno.KenoChasePlanController;
import com.cai310.utils.ReflectionUtils;

@Namespace("/" + XjEl11to5Constant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends KenoChasePlanController<XjEl11to5IssueData, XjEl11to5Scheme> {

	private static final long serialVersionUID = -7083231968490589364L;
	@Resource(name = "xjel11to5KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<XjEl11to5IssueData, XjEl11to5Scheme> kenoService) {
		this.kenoService = kenoService;
	}
	
	public XjEl11to5IssueData getPeriod() {
		return this.period;
	}
	@SuppressWarnings("unchecked")
	public ChaseDetailController() {
		this.schemeClass = ReflectionUtils.getSuperClassGenricType(XjEl11to5Scheme.class, 0);
	}
}
