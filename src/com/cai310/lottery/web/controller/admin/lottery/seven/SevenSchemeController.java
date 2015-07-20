package com.cai310.lottery.web.controller.admin.lottery.seven;

import org.apache.struts2.convention.annotation.Action;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.seven.SevenScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.seven.impl.SevenSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.seven.impl.SevenSchemeServiceImpl;
import com.cai310.lottery.web.controller.admin.lottery.SchemeController;
import org.springframework.beans.factory.annotation.Autowired;

@Action(value = "scheme")
public class SevenSchemeController extends SchemeController<SevenScheme> {
	private static final long serialVersionUID = 5985842237187757494L;

	@Autowired
	private SevenSchemeServiceImpl schemeService;

	@Autowired
	private SevenSchemeEntityManagerImpl schemeEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SEVEN;
	}

	@Override
	protected SchemeEntityManager<SevenScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected SchemeService<SevenScheme, ?> getSchemeService() {
		return schemeService;
	}

}
