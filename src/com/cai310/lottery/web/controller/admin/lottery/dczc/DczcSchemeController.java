package com.cai310.lottery.web.controller.admin.lottery.dczc;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.SchemeService;
import com.cai310.lottery.service.lottery.dczc.DczcSchemeEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcSchemeService;
import com.cai310.lottery.web.controller.admin.lottery.SchemeController;

@Action(value = "scheme")
public class DczcSchemeController extends SchemeController<DczcScheme> {
	private static final long serialVersionUID = 5985842237187757494L;

	@Autowired
	private DczcSchemeService schemeService;

	@Autowired
	private DczcSchemeEntityManager schemeEntityManager;

	@Override
	public Lottery getLotteryType() {
		return Lottery.DCZC;
	}

	@Override
	protected SchemeEntityManager<DczcScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	@Override
	protected SchemeService<DczcScheme, ?> getSchemeService() {
		return schemeService;
	}

	@Override
	protected String getSchemeContentText() {
		if (scheme == null)
			return super.getSchemeContentText();

		return scheme.getContentText();
	}
	
}
