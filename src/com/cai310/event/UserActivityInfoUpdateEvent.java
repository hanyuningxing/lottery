package com.cai310.event;

import org.springframework.context.ApplicationEvent;

import com.cai310.lottery.common.ActivityType;

/**
 * Description: 开奖结果更新事件<br>
 * 发布开奖事件，用于更新开奖结果HTML；
 * Copyright: Copyright (c) 2011<br>
 * Company: 肇庆优盛科技
 * @author  zhuhui 2011-03-16 编写
 * @version 1.0
 */
public class UserActivityInfoUpdateEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	private ActivityType userActivityType;


	public UserActivityInfoUpdateEvent(Object userActivityType) {
		super(userActivityType);
		this.userActivityType=(ActivityType) userActivityType;
	}

	public ActivityType getUserActivityType() {
		return userActivityType;
	}

	public void setUserActivityType(ActivityType userActivityType) {
		this.userActivityType = userActivityType;
	}

}
