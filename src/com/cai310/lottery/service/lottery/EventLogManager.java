package com.cai310.lottery.service.lottery;

import java.util.Map;

import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.EventLog;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.user.User;

public interface EventLogManager {

	/**
	 * 持久化对象
	 * 
	 * @param eventLog
	 * @return
	 */
	public EventLog saveEventLog(EventLog eventLog);

	public void updateEventLog(Map<String, Object> params, long eventLogId);
	
	public void saveSimpleEventLog(Period period,Lottery lottery,AdminUser adminUser,EventLogType eventLogType,String msg);
	public void saveSimpleEventLog(Period period,Lottery lottery,String operatorName,EventLogType eventLogType,String msg);
	public void saveSimpleEventLog(EventLog eventLog);

}
