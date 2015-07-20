package com.cai310.lottery.web.controller.admin.lottery.pl;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.pl.PlScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.pl.impl.PlSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.pl.impl.PlSchemeServiceImpl;
import com.cai310.lottery.web.controller.admin.lottery.SchemeController;

@Action(value = "scheme")
public class PlSchemeController extends SchemeController<PlScheme> {
	private static final long serialVersionUID = 5985842237187757494L;

	@Autowired
	private PlSchemeServiceImpl schemeService;

	@Autowired
	private PlSchemeEntityManagerImpl schemeEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.PL;
	}

	@Override
	protected SchemeEntityManager<PlScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected SchemeService<PlScheme, ?> getSchemeService() {
		return schemeService;
	}

}
