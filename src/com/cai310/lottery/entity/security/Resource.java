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
 * 受保护的资源.
 */
@Entity
@Table(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SS_RESOURCE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Resource extends IdEntity {
	private static final long serialVersionUID = 6727451789898565208L;

	// -- resourceType常量 --//
	public static final String URL_TYPE = "url";
	public static final String MENU_TYPE = "menu";

	private String resourceType;
	private String value;
	private Double position;
	private List<Authority> authorityList = new ArrayList<Authority>();

	/**
	 * 资源类型.
	 */
	@Column(nullable = false, length = 20)
	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * 资源标识.
	 */
	@Column(nullable = false, unique = true, length = 100)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 资源在SpringSecurity中的校验顺序字段.
	 */
	@Column
	public Double getPosition() {
		return position;
	}

	public void setPosition(Double position) {
		this.position = position;
	}

	/**
	 * 可访问该资源的授权集合.
	 */
	@ManyToMany
	@JoinTable(name = com.cai310.lottery.Constant.LOTTERY_TABLE_PREFIX + "SS_RESOURCE_AUTHORITY", joinColumns = { @JoinColumn(name = "RESOURCE_ID") }, inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID") })
	@Fetch(FetchMode.SUBSELECT)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	public List<Authority> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List<Authority> authorityList) {
		this.authorityList = authorityList;
	}

	/**
	 * 可访问该资源的授权名称字符串, 多个授权用','分隔.
	 */
	@Transient
	public String getAuthNames() {
		return ReflectionUtils.convertElementPropertyToString(authorityList, "name", ",");
	}

	/**
	 * 可访问该资源的授权名称字符串, 多个授权用','分隔.
	 */
	@Transient
	public String getAuthDNames() {
		return ReflectionUtils.convertElementPropertyToString(authorityList, "displayName", ",");
	}

	/**
	 * 可访问该资源的授权id
	 */
	@Transient
	@SuppressWarnings("unchecked")
	public List<Long> getAuthIds() {
		return ReflectionUtils.convertElementPropertyToList(authorityList, "id");
	}
}
