package com.cai310.lottery.web.controller.admin.lottery.ssq;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.ssq.SsqScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.ssq.impl.SsqSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.ssq.impl.SsqSchemeServiceImpl;
import com.cai310.lottery.web.controller.admin.lottery.SchemeController;

@Action(value = "scheme")
public class SsqSchemeController extends SchemeController<SsqScheme> {
	private static final long serialVersionUID = 5985842237187757494L;

	@Autowired
	private SsqSchemeServiceImpl schemeService;

	@Autowired
	private SsqSchemeEntityManagerImpl schemeEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SSQ;
	}

	@Override
	protected SchemeEntityManager<SsqScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected SchemeService<SsqScheme, ?> getSchemeService() {
		return schemeService;
	}

}
