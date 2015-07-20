package com.cai310.lottery.web.controller.admin.lottery.sfzc;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.zc.SfzcScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.zc.impl.SfzcSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SfzcSchemeServiceImpl;
import com.cai310.lottery.web.controller.admin.lottery.SchemeController;

@Action(value = "scheme")
public class SfzcSchemeController extends SchemeController<SfzcScheme> {
	private static final long serialVersionUID = 5985842237187757494L;

	@Autowired
	private SfzcSchemeServiceImpl schemeService;

	@Autowired
	private SfzcSchemeEntityManagerImpl schemeEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SFZC;
	}

	@Override
	protected SchemeEntityManager<SfzcScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected SchemeService<SfzcScheme, ?> getSchemeService() {
		return schemeService;
	}

}
