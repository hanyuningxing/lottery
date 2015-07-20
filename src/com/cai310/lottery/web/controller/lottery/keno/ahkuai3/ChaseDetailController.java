package com.cai310.lottery.web.controller.lottery.keno.ahkuai3;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.cai310.lottery.AhKuai3Constant;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3IssueData;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3Scheme;
import com.cai310.lottery.service.lottery.keno.KenoService;
import com.cai310.lottery.web.controller.lottery.keno.KenoChasePlanController;
import com.cai310.utils.ReflectionUtils;

@Namespace("/" + AhKuai3Constant.KEY)
@Action(value = "chase")
public class ChaseDetailController extends KenoChasePlanController<AhKuai3IssueData, AhKuai3Scheme>{

	private static final long serialVersionUID = 3523467490741371023L;
	
	@Resource(name = "ahkuai3KenoServiceImpl")
	@Override
	public void setKenoService(KenoService<AhKuai3IssueData, AhKuai3Scheme> kenoService) {
		this.kenoService=kenoService;
	}
	public AhKuai3IssueData getPeriod() {
		return this.period;
	}
	@SuppressWarnings("unchecked")
	public ChaseDetailController() {
		this.schemeClass = ReflectionUtils.getSuperClassGenricType(AhKuai3Scheme.class, 0);
	}
}
