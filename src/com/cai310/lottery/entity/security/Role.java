package com.cai310.lottery.entity.security;

import java.util.ArrayList;
import java.util.List;

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

import com.cai310.entity.IdEntity;
import com.cai310.utils.ReflectionUtils;

/**
 * 角色.
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SS_ROLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends IdEntity {
	private static final long serialVersionUID = -2405707558288763199L;

	private String name;
	private List<Authority> authorityList = new ArrayList<Authority>();

	@Column(nullable = false, unique = true, length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany
	@JoinTable(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SS_ROLE_AUTHORITY", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID") })
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Authority> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List<Authority> authorityList) {
		this.authorityList = authorityList;
	}

	@Transient
	public String getAuthNames() {
		return ReflectionUtils.convertElementPropertyToString(authorityList, "displayName", ", ");
	}

	@Transient
	@SuppressWarnings("unchecked")
	public List<Long> getAuthIds() {
		return ReflectionUtils.convertElementPropertyToList(authorityList, "id");
	}
}
