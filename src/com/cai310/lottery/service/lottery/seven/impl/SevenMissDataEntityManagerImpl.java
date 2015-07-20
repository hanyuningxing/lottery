package com.cai310.lottery.service.lottery.seven.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.seven.SevenMissDataDao;
import com.cai310.lottery.entity.lottery.seven.SevenMissDataInfo;
import com.cai310.lottery.service.lottery.impl.MissDataEntityManagerImpl;

@Service("sevenMissDataEntityManagerImpl")
@Transactional
public class SevenMissDataEntityManagerImpl extends MissDataEntityManagerImpl<SevenMissDataInfo> {

	@Autowired
	private SevenMissDataDao missDataDao;
	@Override
	public MissDataDao<SevenMissDataInfo> getMissDataDao() {
		return missDataDao;
	}

}
