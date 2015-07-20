package com.cai310.lottery.dao.info;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.cai310.lottery.entity.info.Mobile;
import com.cai310.lottery.entity.user.User;
import com.cai310.orm.hibernate.HibernateDao;


/**
 * 手机DAO
 * 
 */
@Repository
public class MobileDAO extends HibernateDao<Mobile, Long> {
	/**
	 * 保存新增或修改的对象.
	 */
	@Override
	public Mobile save(final Mobile entity) {
		Assert.notNull(entity, "entity不能为空");
		if(null!=entity.getId()){
			synchronized (""+entity.getId()) {
				getSession().saveOrUpdate(entity);
			}
		}else{
			getSession().saveOrUpdate(entity);
		}
		return entity;
	}	
}
