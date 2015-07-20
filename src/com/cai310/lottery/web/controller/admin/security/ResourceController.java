package com.cai310.lottery.web.controller.admin.security;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.security.Authority;
import com.cai310.lottery.entity.security.Resource;
import com.cai310.lottery.web.controller.admin.AdminBaseController;
import com.cai310.orm.Page;
import com.cai310.orm.PropertyFilter;
import com.cai310.orm.hibernate.HibernateWebUtils;
import com.cai310.utils.Struts2Utils;

public class ResourceController extends AdminBaseController {
	private static final long serialVersionUID = 6455114611563106136L;
	private Long id;
	private Resource entity;
	private Page<Resource> pagination = new Page<Resource>(20);
	private List<Long> checkedAuthorityIds; // 页面中钩选的角色id列表
	private List<Authority> allAuthorityList;
	public String URL_TYPE;
	public String MENU_TYPE;

	// 进入首页
	public String index() {
		this.setMENU_TYPE(Resource.MENU_TYPE);
		this.setURL_TYPE(Resource.URL_TYPE);
		allAuthorityList = securityEntityManager.getAllAuthority();
		if (null == allAuthorityList || allAuthorityList.isEmpty()) {
			addActionError("请联系程序员设置角色权限");
		}
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(Struts2Utils.getRequest());
		// 设置默认排序方式
		if (!pagination.isOrderBySetted()) {
			pagination.setOrderBy("id");
			pagination.setOrder(Page.ASC);
		}
		pagination = securityEntityManager.searchResource(pagination, filters);
		return "index";
	}

	public String edit() throws Exception {
		this.setMENU_TYPE(Resource.MENU_TYPE);
		this.setURL_TYPE(Resource.URL_TYPE);
		if (null == entity && null == entity.getId()) {
			addActionError("用户实体为空");
		}
		entity = securityEntityManager.getResource(entity.getId());
		return index();
	}

	public String save() throws Exception {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			addActionError("权限不足！");
			return edit();
		}
		if (null == entity) {
			this.addActionMessage("资源实体为空");
			return edit();
		}
		if (StringUtils.isBlank(entity.getValue())) {
			this.addActionMessage("资源值为空,请填写");
			return edit();
		}
		if (null == entity.getPosition()) {
			this.addActionMessage("资源排位为空,请填写");
			return edit();
		}
		if (null == checkedAuthorityIds || checkedAuthorityIds.isEmpty()) {
			this.addActionMessage("请配置权限");
			return edit();
		}
		if (Long.valueOf("-1").equals(entity.getId())) {
			// 新增
			entity.setId(null);
			// 根据页面上的checkbox 整合Role的Authorities Set.
			HibernateWebUtils.mergeByCheckedIds(entity.getAuthorityList(), checkedAuthorityIds, Authority.class);
			// 保存用户并放入成功信息.
			securityEntityManager.saveResource(entity, adminUser);
			this.addActionMessage("新增资源成功");
			return edit();
		} else {
			// 修改
			Resource resource = securityEntityManager.getResource(entity.getId());
			if (null == resource) {
				this.addActionMessage("资源实体不存在");
				return edit();
			}
			if (!securityEntityManager.isResourceNameUnique(entity.getValue(), resource.getValue())) {
				this.addActionMessage("资源内容与现有资源重复，请直接赋予权限");
				return edit();
			}
			resource.setPosition(entity.getPosition());
			resource.setResourceType(entity.getResourceType());
			resource.setValue(entity.getValue());
			// 根据页面上的checkbox 整合Role的Authorities Set.
			HibernateWebUtils.mergeByCheckedIds(entity.getAuthorityList(), checkedAuthorityIds, Authority.class);
			// 保存用户并放入成功信息.
			securityEntityManager.saveResource(entity, adminUser);
			this.addActionMessage("修改资源成功");
			return edit();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Resource getEntity() {
		return entity;
	}

	public void setEntity(Resource entity) {
		this.entity = entity;
	}

	public Page<Resource> getPagination() {
		return pagination;
	}

	public void setPagination(Page<Resource> pagination) {
		this.pagination = pagination;
	}

	public List<Authority> getAllAuthorityList() {
		return allAuthorityList;
	}

	public void setAllAuthorityList(List<Authority> allAuthorityList) {
		this.allAuthorityList = allAuthorityList;
	}

	public List<Long> getCheckedAuthorityIds() {
		return checkedAuthorityIds;
	}

	public void setCheckedAuthorityIds(List<Long> checkedAuthorityIds) {
		this.checkedAuthorityIds = checkedAuthorityIds;
	}

	public String getURL_TYPE() {
		return URL_TYPE;
	}

	public void setURL_TYPE(String url_type) {
		URL_TYPE = url_type;
	}

	public String getMENU_TYPE() {
		return MENU_TYPE;
	}

	public void setMENU_TYPE(String menu_type) {
		MENU_TYPE = menu_type;
	}

}
