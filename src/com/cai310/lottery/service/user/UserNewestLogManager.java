package com.cai310.lottery.service.user;

import com.cai310.lottery.entity.user.UserNewestLog;

public interface UserNewestLogManager{
	
	UserNewestLog saveLog(UserNewestLog log);
	
	void removeLog(Long id);

}
