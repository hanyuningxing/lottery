package com.cai310.lottery.web.controller.admin.lottery.welfare36to7;

import org.apache.struts2.convention.annotation.Action;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7Scheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.welfare36to7.impl.Welfare36to7SchemeEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare36to7.impl.Welfare36to7SchemeServiceImpl;
import com.cai310.lottery.web.controller.admin.lottery.SchemeController;
import org.springframework.beans.factory.annotation.Autowired;

@Action(value = "scheme")
public class Welfare36to7SchemeController extends SchemeController<Welfare36to7Scheme> {
	private static final long serialVersionUID = 5985842237187757494L;

	@Autowired
	private Welfare36to7SchemeServiceImpl schemeService;

	@Autowired
	private Welfare36to7SchemeEntityManagerImpl schemeEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE36To7;
	}

	@Override
	protected SchemeEntityManager<Welfare36to7Scheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected SchemeService<Welfare36to7Scheme, ?> getSchemeService() {
		return schemeService;
	}

}
