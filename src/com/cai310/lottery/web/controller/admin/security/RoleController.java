package com.cai310.lottery.web.controller.admin.security;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.security.Authority;
import com.cai310.lottery.entity.security.Role;
import com.cai310.lottery.web.controller.admin.AdminBaseController;
import com.cai310.orm.hibernate.HibernateWebUtils;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 角色管理.
 * 
 */
public class RoleController extends AdminBaseController implements ModelDriven<Object> {

	private static final long serialVersionUID = 1L;

	// -- 页面属性 --//
	private Long id;
	private Role entity;
	private List<Role> allRoleList;// 角色列表
	private List<Authority> allAuthorityList;
	private List<Long> checkedAuthIds;// 页面中钩选的权限id列表

	// 进入首页
	public String index() {
		allAuthorityList = securityEntityManager.getAllAuthority();
		if (null == allAuthorityList || allAuthorityList.isEmpty()) {
			addActionError("请先设置角色权限");
		}
		allRoleList = securityEntityManager.getAllRole();
		return "index";
	}

	public String edit() throws Exception {
		if (null == entity && null == entity.getId()) {
			addActionError("角色实体为空");
		}
		entity = securityEntityManager.getRole(entity.getId());
		return index();
	}

	public String save() throws Exception {
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			addActionError("权限不足！");
			return edit();
		}
		if (null == entity && null == entity.getId()) {
			this.addActionMessage("角色实体为空");
			return edit();
		}
		if (StringUtils.isBlank(entity.getName())) {
			this.addActionMessage("角色名字为空,请填写");
			return edit();
		}
		if (null == checkedAuthIds || checkedAuthIds.isEmpty()) {
			this.addActionMessage("请配置改角色权限");
			return edit();
		}
		if (Long.valueOf("-1").equals(entity.getId())) {
			// 新增
			entity.setId(null);
			// 根据页面上的checkbox 整合Role的Authorities Set.
			HibernateWebUtils.mergeByCheckedIds(entity.getAuthorityList(), checkedAuthIds, Authority.class);
			// 保存用户并放入成功信息.
			securityEntityManager.saveRole(entity, adminUser);
			this.addActionMessage("新增角色成功");
			return edit();
		} else {
			// 修改
			Role role = securityEntityManager.getRole(entity.getId());
			if (null == role) {
				this.addActionMessage("角色实体不存在.");
				return edit();
			}
			if (!securityEntityManager.isRoleNameUnique(entity.getName(), role.getName())) {
				this.addActionMessage("角色名字重复");
				return edit();
			}
			role.setName(entity.getName());
			HibernateWebUtils.mergeByCheckedIds(role.getAuthorityList(), checkedAuthIds, Authority.class);
			securityEntityManager.saveRole(role, adminUser);
			this.addActionMessage("修改角色成功");
			return edit();
		}
	}

	// public String delete() throws Exception {
	// securityEntityManager.deleteRole(id);
	// addActionMessage("删除角色成功");
	// return SUCCESS;
	// }
	//	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getEntity() {
		return entity;
	}

	public void setEntity(Role entity) {
		this.entity = entity;
	}

	public List<Role> getAllRoleList() {
		return allRoleList;
	}

	public void setAllRoleList(List<Role> allRoleList) {
		this.allRoleList = allRoleList;
	}

	public List<Authority> getAllAuthorityList() {
		return allAuthorityList;
	}

	public void setAllAuthorityList(List<Authority> allAuthorityList) {
		this.allAuthorityList = allAuthorityList;
	}

	public List<Long> getCheckedAuthIds() {
		return checkedAuthIds;
	}

	public void setCheckedAuthIds(List<Long> checkedAuthIds) {
		this.checkedAuthIds = checkedAuthIds;
	}

	public Object getModel() {
		return entity;
	}

}