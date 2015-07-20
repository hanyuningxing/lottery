package com.cai310.lottery.web.controller.admin.lottery.lczc;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.zc.LczcScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.zc.impl.LczcSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.LczcSchemeServiceImpl;
import com.cai310.lottery.web.controller.admin.lottery.SchemeController;

@Action(value = "scheme")
public class LczcSchemeController extends SchemeController<LczcScheme> {
	private static final long serialVersionUID = 5985842237187757494L;

	@Autowired
	private LczcSchemeServiceImpl schemeService;

	@Autowired
	private LczcSchemeEntityManagerImpl schemeEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.LCZC;
	}

	@Override
	protected SchemeEntityManager<LczcScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected SchemeService<LczcScheme, ?> getSchemeService() {
		return schemeService;
	}

}
