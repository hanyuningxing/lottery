package com.cai310.lottery.support.agent;

import java.util.List;

import com.cai310.lottery.support.jclq.JclqMatchItem;

public class UserRebateLimitList {
	/** 选择的场次内容 */
	private List<UserRebateLimit> items;

	public List<UserRebateLimit> getItems() {
		return items;
	}

	public void setItems(List<UserRebateLimit> items) {
		this.items = items;
	}
}
