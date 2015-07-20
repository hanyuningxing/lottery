package com.cai310.lottery.web.controller.admin.lottery.sczc;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.zc.SczcScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.zc.impl.SczcSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SczcSchemeServiceImpl;
import com.cai310.lottery.web.controller.admin.lottery.SchemeController;

@Action(value = "scheme")
public class SczcSchemeController extends SchemeController<SczcScheme> {
	private static final long serialVersionUID = 5985842237187757494L;

	@Autowired
	private SczcSchemeServiceImpl schemeService;

	@Autowired
	private SczcSchemeEntityManagerImpl schemeEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.SCZC;
	}

	@Override
	protected SchemeEntityManager<SczcScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected SchemeService<SczcScheme, ?> getSchemeService() {
		return schemeService;
	}

}
