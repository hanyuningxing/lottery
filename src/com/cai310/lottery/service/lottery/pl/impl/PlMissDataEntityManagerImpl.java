package com.cai310.lottery.service.lottery.pl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.pl.PlMissDataDao;
import com.cai310.lottery.entity.lottery.pl.PlMissDataInfo;
import com.cai310.lottery.service.lottery.impl.MissDataEntityManagerImpl;

@Service("plMissDataEntityManagerImpl")
@Transactional
public class PlMissDataEntityManagerImpl extends MissDataEntityManagerImpl<PlMissDataInfo> {

	@Autowired
	private PlMissDataDao missDataDao;
	@Override
	public MissDataDao<PlMissDataInfo> getMissDataDao() {
		return missDataDao;
	}

}
