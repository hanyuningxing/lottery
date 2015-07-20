package com.cai310.lottery.builder;

import java.util.List;

import org.hibernate.criterion.Order;

import com.cai310.lottery.common.InfoSubType;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;

/**
 * Description: 新闻信息——查询器表单参数<br>
 * Copyright: Copyright (c) 2011<br>
 * Company: 肇庆优盛科技
 * @author  zhuhui 2011-03-16 编写
 * @version 1.0
 */
public class DefaultInfoBuilderQueryForm {
	private int needNum;
	private List<Order> orders;
	
	public int getNeedNum() {
		return needNum;
	}
	public void setNeedNum(int needNum) {
		this.needNum = needNum;
	}

	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
