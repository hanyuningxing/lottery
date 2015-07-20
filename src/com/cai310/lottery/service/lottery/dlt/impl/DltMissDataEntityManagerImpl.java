package com.cai310.lottery.service.lottery.dlt.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.dlt.DltMissDataDao;
import com.cai310.lottery.entity.lottery.dlt.DltMissDataInfo;
import com.cai310.lottery.service.lottery.impl.MissDataEntityManagerImpl;

@Service("dltMissDataEntityManagerImpl")
@Transactional
public class DltMissDataEntityManagerImpl extends MissDataEntityManagerImpl<DltMissDataInfo> {

	@Autowired
	private DltMissDataDao missDataDao;
	@Override
	public MissDataDao<DltMissDataInfo> getMissDataDao() {
		return missDataDao;
	}

}
