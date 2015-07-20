package com.cai310.lottery.service.user.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.user.UserNewestLogDao;
import com.cai310.lottery.entity.user.UserNewestLog;
import com.cai310.lottery.service.user.UserNewestLogManager;

@Service("userNewestLogManger")
public class UserNewestLogManagerImpl implements UserNewestLogManager {
	@Resource
	private UserNewestLogDao userNewestLogDao;

	@Transactional
	@Override
	public UserNewestLog saveLog(UserNewestLog log) {
		return userNewestLogDao.save(log);
	}

	@Transactional
	@Override
	public void removeLog(Long id) {
		userNewestLogDao.delete(id);
	}

}
