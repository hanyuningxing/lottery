package com.cai310.lottery.web.controller.admin.lottery.welfare3d;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.welfare3d.impl.Welfare3dSchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare3d.impl.Welfare3dSchemeServiceImpl;
import com.cai310.lottery.web.controller.admin.lottery.SchemeController;

@Action(value = "scheme")
public class Welfare3dSchemeController extends SchemeController<Welfare3dScheme> {
	private static final long serialVersionUID = 5985842237187757494L;

	@Autowired
	private Welfare3dSchemeServiceImpl schemeService;

	@Autowired
	private Welfare3dSchemeEntityManagerImpl schemeEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE3D;
	}

	@Override
	protected SchemeEntityManager<Welfare3dScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected SchemeService<Welfare3dScheme, ?> getSchemeService() {
		return schemeService;
	}

}
