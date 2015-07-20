package com.cai310.lottery.service.lottery.tc22to5.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.tc22to5.Tc22to5MissDataDao;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5MissDataInfo;
import com.cai310.lottery.service.lottery.impl.MissDataEntityManagerImpl;

@Service("tc22to5MissDataEntityManagerImpl")
@Transactional
public class Tc22to5MissDataEntityManagerImpl extends MissDataEntityManagerImpl<Tc22to5MissDataInfo> {

	@Autowired
	private Tc22to5MissDataDao missDataDao;
	@Override
	public MissDataDao<Tc22to5MissDataInfo> getMissDataDao() {
		return missDataDao;
	}

}
