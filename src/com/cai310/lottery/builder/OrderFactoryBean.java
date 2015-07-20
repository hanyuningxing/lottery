package com.cai310.lottery.builder;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.FactoryBean;

/**
 * Description: hibernate Order FactoryBean<br>
 * Copyright: Copyright (c) 2011<br>
 * Company: 肇庆优盛科技
 * @author zhuhui 2011-03-16 编写
 * @version 1.0
 */
public class OrderFactoryBean implements FactoryBean {
	private String propertyName;
	private boolean ascending;
	
	@Override
	public Object getObject() throws Exception {
		return (this.ascending)?Order.asc(propertyName):Order.desc(propertyName);
	}
	@Override
	public Class getObjectType() {
		return Order.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}
}
