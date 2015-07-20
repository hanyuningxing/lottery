package com.cai310.lottery.dao.lottery.keno;

import java.lang.reflect.ParameterizedType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cai310.entity.IdEntity;
import com.cai310.orm.hibernate.HibernateDao;

public abstract class KenoGenericDao<T extends IdEntity> extends HibernateDao<T, Long> {
	protected Log logger = LogFactory.getLog(getClass());

	protected Class<T> entityClass;

	public KenoGenericDao() {

	}

	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			logger.debug("T class = " + entityClass.getName());
		}
		return entityClass;
	}
}
