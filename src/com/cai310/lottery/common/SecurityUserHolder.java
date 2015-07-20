package com.cai310.lottery.common;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;

import com.cai310.lottery.entity.security.AdminUser;

public class SecurityUserHolder {
	/**
	 * @return 返回当前登录的后台用户.
	 */
	public static AdminUser getCurrentUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			Authentication auth = context.getAuthentication();
			if (auth != null) {
				Object o = auth.getPrincipal();
				if (o instanceof AdminUser) {
					return (AdminUser) o;
				} else {
					return null;
				}
			}
		}
		return null;
	}
}
