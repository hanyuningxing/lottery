package com.cai310.lottery.web.controller.admin.security;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.PasswordEncoder;

import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.security.Role;
import com.cai310.lottery.web.controller.admin.AdminBaseController;
import com.cai310.orm.Page;
import com.cai310.orm.PropertyFilter;
import com.cai310.orm.hibernate.HibernateWebUtils;
import com.cai310.utils.Struts2Utils;

// 定义返回 success 时重定向到 user Action
public class UserController extends AdminBaseController {
	private static final long serialVersionUID = 6455114611563106136L;
	@Autowired
	PasswordEncoder passwordEncoder;
	private Long id;
	private AdminUser entity;
	private Page<AdminUser> pagination = new Page<AdminUser>(40);
	private List<Long> checkedRoleIds; // 页面中钩选的角色id列表
	private List<Role> allRoleList;

	// 进入首页
	public String index() {
		allRoleList = securityEntityManager.getAllRole();
		if (null == allRoleList || allRoleList.isEmpty()) {
			addActionError("请先设置用户角色");
		}
		List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(Struts2Utils.getRequest());
		// 设置默认排序方式
		if (!pagination.isOrderBySetted()) {
			pagination.setOrderBy("id");
			pagination.setOrder(Page.ASC);
		}
		pagination = securityEntityManager.searchUser(pagination, filters);
		return "index";
	}

	public String edit() throws Exception {
		if (null == entity && null == entity.getId()) {
			addActionError("用户实体为空");
		}
		entity = securityEntityManager.getUser(entity.getId());
		return index();
	}

	public String save() throws Exception {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			addActionError("权限不足！");
			return edit();
		}
		if (null == entity && null == entity.getId()) {
			this.addActionMessage("用户实体为空");
			return edit();
		}
		if (StringUtils.isBlank(entity.getLoginName())) {
			this.addActionMessage("用户名字为空,请填写");
			return edit();
		}
		if (null == checkedRoleIds || checkedRoleIds.isEmpty()) {
			this.addActionMessage("请配置改用户角色");
			return edit();
		}
		if (Long.valueOf("-1").equals(entity.getId())) {
			// 新增
			entity.setId(null);
			entity.setPassword(passwordEncoder.encodePassword("123456", null));
			// 根据页面上的checkbox 整合Role的Authorities Set.
			HibernateWebUtils.mergeByCheckedIds(entity.getRoleList(), checkedRoleIds, Role.class);
			// 保存用户并放入成功信息.
			securityEntityManager.saveUser(entity, adminUser);
			this.addActionMessage("新增用户成功");
			return edit();
		} else {
			// 修改
			AdminUser adminUserTemp = securityEntityManager.getUser(entity.getId());
			if (null == adminUserTemp || null == adminUserTemp.getId()) {
				this.addActionMessage("用户实体为空");
				return edit();
			}
			if (!securityEntityManager.isRoleNameUnique(entity.getLoginName(), adminUserTemp.getLoginName())) {
				this.addActionMessage("用户名字重复");
				return edit();
			}
			adminUserTemp.setLoginName(entity.getLoginName());
			adminUserTemp.setName(entity.getName());
			adminUserTemp.setEnabled(entity.isEnabled());
			HibernateWebUtils.mergeByCheckedIds(adminUserTemp.getRoleList(), checkedRoleIds, Role.class);
			securityEntityManager.saveUser(adminUserTemp, adminUser);
			this.addActionMessage("修改用户成功");
			return edit();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AdminUser getEntity() {
		return entity;
	}

	public void setEntity(AdminUser entity) {
		this.entity = entity;
	}

	public Page<AdminUser> getPagination() {
		return pagination;
	}

	public void setPagination(Page<AdminUser> pagination) {
		this.pagination = pagination;
	}

	public List<Long> getCheckedRoleIds() {
		return checkedRoleIds;
	}

	public void setCheckedRoleIds(List<Long> checkedRoleIds) {
		this.checkedRoleIds = checkedRoleIds;
	}

	public List<Role> getAllRoleList() {
		return allRoleList;
	}

	public void setAllRoleList(List<Role> allRoleList) {
		this.allRoleList = allRoleList;
	}

}
