package com.cai310.lottery.dao.security;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.security.Role;
import com.cai310.orm.hibernate.HibernateDao;

/**
 * 角色对象的泛型DAO.
 * 
 */
@Repository
public class RoleDao extends HibernateDao<Role, Long> {

	private static final String QUERY_USER_BY_ROLEID = "select u from User u left join u.roleList r where r.id=?";

	/**
	 * 重载函数,因为Role中没有建立与User的关联,因此需要以较低效率的方式进行删除User与Role的多对多中间表.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void delete(Long id) {
		Role role = get(id);
		// 查询出拥有该角色的用户,并删除该用户的角色.
		List<AdminUser> users = createQuery(QUERY_USER_BY_ROLEID, role.getId()).list();
		for (AdminUser u : users) {
			u.getRoleList().remove(role);
		}
		super.delete(role);
	}
}
