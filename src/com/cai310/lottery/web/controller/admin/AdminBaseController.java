package com.cai310.lottery.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.SecurityUserHolder;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.service.lottery.EventLogManager;
import com.cai310.lottery.service.security.SecurityEntityManager;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.lottery.web.controller.GlobalResults;

public class AdminBaseController extends BaseController {
	private static final long serialVersionUID = -1826319292241649534L;

	@Autowired
	protected SecurityEntityManager securityEntityManager;

	@Autowired
	protected EventLogManager eventLogManager;

	public AdminUser adminUser;

	public AdminUser getAdminUser() {
		Object o = SecurityUserHolder.getCurrentUser();
		if (o instanceof AdminUser) {
			return (AdminUser) o;
		} else {
			return null;
		}
	}
	@Override
	protected String error() {
		return forward(false, GlobalResults.ADMIN_FWD_ERROR);
	}
}
