package com.cai310.lottery.web.controller.admin.lottery.sevenstar;

import org.apache.struts2.convention.annotation.Action;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.sevenstar.SevenstarScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.sevenstar.impl.SevenstarSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.sevenstar.impl.SevenstarSchemeServiceImpl;
import com.cai310.lottery.web.controller.admin.lottery.SchemeController;
import org.springframework.beans.factory.annotation.Autowired;

@Action(value = "scheme")
public class SevenstarSchemeController extends SchemeController<SevenstarScheme> {
	private static final long serialVersionUID = 5985842237187757494L;

	@Autowired
	private SevenstarSchemeServiceImpl schemeService;

	@Autowired
	private SevenstarSchemeEntityManagerImpl schemeEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SEVENSTAR;
	}

	@Override
	protected SchemeEntityManager<SevenstarScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected SchemeService<SevenstarScheme, ?> getSchemeService() {
		return schemeService;
	}

}
