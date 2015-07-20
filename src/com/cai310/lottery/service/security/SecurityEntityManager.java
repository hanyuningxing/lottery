package com.cai310.lottery.service.security;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.dao.lottery.EventLogDao;
import com.cai310.lottery.dao.security.AdminUserDao;
import com.cai310.lottery.dao.security.AuthorityDao;
import com.cai310.lottery.dao.security.ResourceDao;
import com.cai310.lottery.dao.security.RoleDao;
import com.cai310.lottery.entity.lottery.EventLog;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.security.Authority;
import com.cai310.lottery.entity.security.Resource;
import com.cai310.lottery.entity.security.Role;
import com.cai310.lottery.service.ServiceException;
import com.cai310.orm.Page;
import com.cai310.orm.PropertyFilter;
import com.cai310.security.springsecurity.SpringSecurityUtils;
import com.cai310.utils.Struts2Utils;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 */
@Service
@Transactional
public class SecurityEntityManager {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected AdminUserDao userDao;
	@Autowired
	protected RoleDao roleDao;
	@Autowired
	protected AuthorityDao authorityDao;
	@Autowired
	protected ResourceDao resourceDao;
	@Autowired
	private EventLogDao eventLogDao;

	// -- User Manager --//
	@Transactional(readOnly = true)
	public AdminUser getUser(Long id) {
		return userDao.get(id);
	}

	public AdminUser saveUser(AdminUser entity, AdminUser operator) {
		entity = userDao.save(entity);
		// 记录操作日志
		EventLog eventLog = new EventLog();
		eventLog.setStartTime(new Date() );
		eventLog.setDoneTime(new Date());
		eventLog.setLogType(EventLogType.Security.getLogType());
		eventLog.setMsg("保存或更改了用户ID为【"+ entity.getId()+ "】实体对象为"+entity.toString());
		eventLog.setNormalFinish(true);
		eventLog.setOperator(operator.getName());
		eventLog.setLotteryType(null);
		eventLog.setIp(Struts2Utils.getRemoteAddr());
		eventLogDao.save(eventLog);
		return entity;

	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteUser(Long id, AdminUser operator) {
		if (id == 1) {
			logger.warn("操作员{}尝试删除超级管理员用户", SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		// 记录操作日志
		EventLog eventLog = new EventLog();
		eventLog.setStartTime(new Date() );
		eventLog.setDoneTime(new Date());
		eventLog.setLogType(EventLogType.Security.getLogType());
		eventLog.setMsg("删除了用户ID为【"+ id+ "】");
		eventLog.setNormalFinish(true);
		eventLog.setOperator(operator.getName());
		eventLog.setLotteryType(null);
		eventLog.setIp(Struts2Utils.getRemoteAddr());
		eventLogDao.save(eventLog);
		userDao.delete(id);
	}

	/**
	 * 使用属性过滤条件查询用户.
	 */
	@Transactional(readOnly = true)
	public Page<AdminUser> searchUser(final Page<AdminUser> page, final List<PropertyFilter> filters) {
		return userDao.findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public AdminUser findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}

	/**
	 * 检查用户名是否唯一.
	 * 
	 * @return loginName在数据库中唯一或等于oldLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String newLoginName, String oldLoginName) {
		return userDao.isPropertyUnique("loginName", newLoginName, oldLoginName);
	}

	/**
	 * 检查角色名是否唯一.
	 * 
	 * @return newRoleName在数据库中唯一或等于oldRoleName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isRoleNameUnique(String newRoleName, String oldRoleName) {
		return roleDao.isPropertyUnique("name", newRoleName, oldRoleName);
	}

	// -- Role Manager --//
	@Transactional(readOnly = true)
	public Role getRole(Long id) {
		return roleDao.get(id);
	}

	@Transactional(readOnly = true)
	public List<Role> getAllRole() {
		return roleDao.getAll("id", true);
	}

	public Role saveRole(Role entity, AdminUser operator) {
		entity = roleDao.save(entity);
		// 记录操作日志
		EventLog eventLog = new EventLog();
		eventLog.setStartTime(new Date() );
		eventLog.setDoneTime(new Date());
		eventLog.setLogType(EventLogType.Security.getLogType());
		eventLog.setMsg("保存或更改了角色ID为【"
				+ entity.getId() + "】"+",实体为"+entity.toString());
		eventLog.setNormalFinish(true);
		eventLog.setOperator(operator.getName());
		eventLog.setLotteryType(null);
		eventLog.setIp(Struts2Utils.getRemoteAddr());
		eventLog.setIp(Struts2Utils.getRemoteAddr());
		eventLogDao.save(eventLog);
		return entity;
	}

	public void deleteRole(Long id, AdminUser operator) {
		roleDao.delete(id);
		// 记录操作日志
		EventLog eventLog = new EventLog();
		eventLog.setStartTime(new Date() );
		eventLog.setDoneTime(new Date());
		eventLog.setLogType(EventLogType.Security.getLogType());
		eventLog.setMsg("删除角色ID为【" + id + "】");
		eventLog.setNormalFinish(true);
		eventLog.setOperator(operator.getName());
		eventLog.setIp(Struts2Utils.getRemoteAddr());
		eventLog.setLotteryType(null);
		eventLogDao.save(eventLog);
	}

	// -- Resource Manager --//
	/**
	 * 查找URL类型的资源并初始化可访问该资源的授权.
	 */
	@Transactional(readOnly = true)
	public List<Resource> getUrlResourceWithAuthorities() {
		return resourceDao.getUrlResourceWithAuthorities();
	}

	// -- Authority Manager --//
	@Transactional(readOnly = true)
	public List<Authority> getAllAuthority() {
		return authorityDao.getAll();
	}

	/**
	 * 使用属性过滤条件查询保护资源.
	 */
	@Transactional(readOnly = true)
	public Page<Resource> searchResource(final Page<Resource> page, final List<PropertyFilter> filters) {
		return resourceDao.findPage(page, filters);
	}

	// -- Role Manager --//
	@Transactional(readOnly = true)
	public Resource getResource(Long id) {
		return resourceDao.get(id);
	}

	public Resource saveResource(Resource entity, AdminUser operator) {
		entity = resourceDao.save(entity);
		// 记录操作日志
		EventLog eventLog = new EventLog();
		eventLog.setStartTime(new Date() );
		eventLog.setDoneTime(new Date());
		eventLog.setLogType(EventLogType.Security.getLogType());
		eventLog.setMsg("保存或更改了资源ID为【"
				+ entity.getId() + "】"+"实体为："+entity.toString());
		eventLog.setNormalFinish(true);
		eventLog.setOperator(operator.getName());
		eventLog.setLotteryType(null);
		eventLog.setIp(Struts2Utils.getRemoteAddr());
		eventLogDao.save(eventLog);
		return entity;
	}

	/**
	 * 检查角色名是否唯一.
	 * 
	 * @return newResourceName在数据库中唯一或等于oldResourceName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isResourceNameUnique(String newResourceName, String oldResourceName) {
		return resourceDao.isPropertyUnique("value", newResourceName, oldResourceName);
	}
	@Transactional(readOnly = true)
	public EventLog getEventLog(Long id) {
		return eventLogDao.get(id);
	}

}
