package com.cai310.lottery.service.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.security.Authority;
import com.cai310.lottery.entity.security.Role;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 */
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SecurityEntityManager securityEntityManager;

	/**
	 * 获取用户Details信息的回调函数.
	 */
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {

		AdminUser adminUser = securityEntityManager.findUserByLoginName(userName);
		if (adminUser == null)
			throw new UsernameNotFoundException("用户" + userName + " 不存在");
		return adminUser;
	}

	/**
	 * 获得用户所有角色的权限集合.
	 */
	private GrantedAuthority[] obtainGrantedAuthorities(AdminUser adminUser) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		for (Role role : adminUser.getRoleList()) {
			for (Authority authority : role.getAuthorityList()) {
				authSet.add(new GrantedAuthorityImpl(authority.getName()));
			}
		}
		return authSet.toArray(new GrantedAuthority[authSet.size()]);
	}
}
