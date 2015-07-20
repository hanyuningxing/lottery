package com.cai310.event;

import org.springframework.context.ApplicationEvent;

import com.cai310.lottery.entity.user.UserNewestLog;

/**
 * 用户最新动态日志事件
 * @author jack
 *
 */
public class UserNewestLogEvent extends ApplicationEvent{

	private static final long serialVersionUID = 4715706276294091090L;
	
	private UserNewestLog userNewestLog;

	public UserNewestLogEvent(UserNewestLog userNewestLog) {
		super(userNewestLog);
		this.userNewestLog = userNewestLog;
	}

	public UserNewestLog getUserNewestLog() {
		return userNewestLog;
	}

	public void setUserNewestLog(UserNewestLog userNewestLog) {
		this.userNewestLog = userNewestLog;
	}
	
}
