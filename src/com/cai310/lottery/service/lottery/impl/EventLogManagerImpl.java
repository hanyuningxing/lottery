package com.cai310.lottery.service.lottery.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.EventLogDao;
import com.cai310.lottery.entity.lottery.EventLog;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.service.lottery.EventLogManager;
import com.cai310.utils.Struts2Utils;

@Service
@Transactional
public class EventLogManagerImpl implements EventLogManager {

	@Autowired
	private EventLogDao eventLogDao;

	public EventLog saveEventLog(EventLog eventLog) {
		return eventLogDao.save(eventLog);
	}
	
	public void saveSimpleEventLog(Period period,Lottery lottery,AdminUser adminUser,EventLogType eventLogType,String msg) {
		EventLog eventLog = new EventLog();
		eventLog.setOperator(adminUser==null?"":adminUser.getName());
		this.saveEventLog(eventLog, period, lottery, eventLogType, msg);
	}
	
	public void saveSimpleEventLog(Period period,Lottery lottery,String operatorName,EventLogType eventLogType,String msg) {
		EventLog eventLog = new EventLog();
		eventLog.setOperator(operatorName);
		this.saveEventLog(eventLog, period, lottery, eventLogType, msg);
	}
	
	public void saveSimpleEventLog(EventLog eventLog) {
		eventLog.setStartTime(new Date());
		eventLog.setNormalFinish(true);
		eventLogDao.save(eventLog);
	}

	private void saveEventLog(EventLog eventLog,Period period,Lottery lottery,EventLogType eventLogType,String msg) {
		eventLog.setStartTime(new Date());
		eventLog.setDoneTime(new Date());
		eventLog.setLogType(eventLogType.getLogType());
		eventLog.setMsg(msg);
		eventLog.setNormalFinish(true);
		eventLog.setLotteryType(lottery);
		if(null!=period){
			eventLog.setPeriodId(period.getId());
			eventLog.setPeriodNumber(period.getPeriodNumber());
		}
		eventLog.setIp(Struts2Utils.getRemoteAddr());
	    eventLogDao.save(eventLog);
	}
	
	
	public void updateEventLog(Map<String, Object> params, long eventLogId) {
		eventLogDao.update(params, eventLogId);

	}

}
