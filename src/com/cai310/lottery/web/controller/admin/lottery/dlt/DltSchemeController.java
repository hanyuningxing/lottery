package com.cai310.lottery.web.controller.admin.lottery.dlt;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.dlt.DltScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.dlt.impl.DltSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.dlt.impl.DltSchemeServiceImpl;
import com.cai310.lottery.web.controller.admin.lottery.SchemeController;

@Action(value = "scheme")
public class DltSchemeController extends SchemeController<DltScheme> {
	private static final long serialVersionUID = 5985842237187757494L;

	@Autowired
	private DltSchemeServiceImpl schemeService;

	@Autowired
	private DltSchemeEntityManagerImpl schemeEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.DLT;
	}

	@Override
	protected SchemeEntityManager<DltScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected SchemeService<DltScheme, ?> getSchemeService() {
		return schemeService;
	}

}
