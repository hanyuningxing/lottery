package com.cai310.lottery.web.controller.admin.lottery.tc22to5;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5Scheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.tc22to5.impl.Tc22to5SchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.tc22to5.impl.Tc22to5SchemeServiceImpl;
import com.cai310.lottery.web.controller.admin.lottery.SchemeController;

@Action(value = "scheme")
public class Tc22to5SchemeController extends SchemeController<Tc22to5Scheme> {
	private static final long serialVersionUID = 5985842237187757494L;

	@Autowired
	private Tc22to5SchemeServiceImpl schemeService;

	@Autowired
	private Tc22to5SchemeEntityManagerImpl schemeEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.TC22TO5;
	}

	@Override
	protected SchemeEntityManager<Tc22to5Scheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected SchemeService<Tc22to5Scheme, ?> getSchemeService() {
		return schemeService;
	}

}
