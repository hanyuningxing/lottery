package com.cai310.lottery.entity.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;

import com.cai310.entity.IdEntity;
import com.cai310.utils.ReflectionUtils;

/**
 * 后台用户.
 * 
 * 使用JPA annotation定义ORM关系. 使用Hibernate annotation定义JPA 1.0未覆盖的部分.
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SS_USER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdminUser extends IdEntity implements UserDetails {
	private static final long serialVersionUID = -4027726973191718607L;

	private String loginName;
	private String password;
	private String name;
	private boolean enabled;
	private List<Role> roleList = new ArrayList<Role>();// 有序的关联对象集合

	@Column(nullable = false, unique = true, length = 20)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(nullable = false, length = 50)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@ManyToMany
	@JoinTable(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SS_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	@Transient
	public String getRoleNames() {
		return ReflectionUtils.convertElementPropertyToString(roleList, "name", ", ");
	}

	/**
	 * 用户拥有的角色id字符串, 多个角色id用','分隔.
	 */
	@Transient
	@SuppressWarnings("unchecked")
	public List<Long> getRoleIds() {
		return ReflectionUtils.convertElementPropertyToList(roleList, "id");
	}

	@Transient
	public GrantedAuthority[] getAuthorities() {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		for (Role role : this.getRoleList()) {
			for (Authority authority : role.getAuthorityList()) {
				authSet.add(new GrantedAuthorityImpl(authority.getName()));
			}
		}
		return authSet.toArray(new GrantedAuthority[authSet.size()]);
	}

	@Transient
	public String getUsername() {
		return this.loginName;
	}

	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

}