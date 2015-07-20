package com.cai310.lottery.service.lottery.welfare3d.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.welfare3d.Welfare3dMissDataDao;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dMissDataInfo;
import com.cai310.lottery.service.lottery.impl.MissDataEntityManagerImpl;

@Service("welfare3dMissDataEntityManagerImpl")
@Transactional
public class Welfare3dMissDataEntityManagerImpl extends MissDataEntityManagerImpl<Welfare3dMissDataInfo> {

	@Autowired
	private Welfare3dMissDataDao missDataDao;
	@Override
	public MissDataDao<Welfare3dMissDataInfo> getMissDataDao() {
		return missDataDao;
	}

}
