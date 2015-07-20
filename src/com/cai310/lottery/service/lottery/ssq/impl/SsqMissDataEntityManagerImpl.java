package com.cai310.lottery.service.lottery.ssq.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.ssq.SsqMissDataDao;
import com.cai310.lottery.entity.lottery.ssq.SsqMissDataInfo;
import com.cai310.lottery.service.lottery.impl.MissDataEntityManagerImpl;

@Service("ssqMissDataEntityManagerImpl")
@Transactional
public class SsqMissDataEntityManagerImpl extends MissDataEntityManagerImpl<SsqMissDataInfo> {

	@Autowired
	private SsqMissDataDao missDataDao;
	@Override
	public MissDataDao<SsqMissDataInfo> getMissDataDao() {
		return missDataDao;
	}

}
