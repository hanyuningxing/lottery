package com.cai310.lottery.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.PasswordEncoder;

import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserLogin;
import com.cai310.lottery.service.user.UserEntityManager;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.lottery.web.sso.SsoLoginHolder;

public class UserBaseController extends BaseController {
	private static final long serialVersionUID = 743929159624334061L;

	@Autowired
	protected UserEntityManager userManager;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Override
	public User getLoginUser() {
		return super.getLoginUser();
	}
	
	public UserLogin getUserLogin(){
		return super.getUserLogin();
	}
}
