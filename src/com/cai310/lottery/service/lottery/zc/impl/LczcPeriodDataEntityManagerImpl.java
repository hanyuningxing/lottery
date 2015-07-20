package com.cai310.lottery.service.lottery.zc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.PeriodDataDao;
import com.cai310.lottery.dao.lottery.zc.LczcPeriodDataDao;
import com.cai310.lottery.entity.lottery.zc.LczcPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;

@Service("lczcPeriodDataEntityManagerImpl")
@Transactional
public class LczcPeriodDataEntityManagerImpl extends PeriodDataEntityManagerImpl<LczcPeriodData> {

	@Autowired
	private LczcPeriodDataDao periodDataDao;

	@Override
	@Transactional(readOnly = true)
	protected PeriodDataDao<LczcPeriodData> getPeriodDataDao() {
		return periodDataDao;
	}

	public LczcPeriodData getEntityInstance() {
		return new LczcPeriodData();
	}
	@Override
	public Lottery getLottery() {
		return Lottery.LCZC;
	}
}
