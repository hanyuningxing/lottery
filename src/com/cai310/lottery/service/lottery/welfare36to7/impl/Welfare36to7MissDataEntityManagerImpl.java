package com.cai310.lottery.service.lottery.welfare36to7.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.welfare36to7.Welfare36to7MissDataDao;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7MissDataInfo;
import com.cai310.lottery.service.lottery.impl.MissDataEntityManagerImpl;

@Service("welfare36to7MissDataEntityManagerImpl")
@Transactional
public class Welfare36to7MissDataEntityManagerImpl extends MissDataEntityManagerImpl<Welfare36to7MissDataInfo> {

	@Autowired
	private Welfare36to7MissDataDao missDataDao;
	@Override
	public MissDataDao<Welfare36to7MissDataInfo> getMissDataDao() {
		return missDataDao;
	}
	
}
