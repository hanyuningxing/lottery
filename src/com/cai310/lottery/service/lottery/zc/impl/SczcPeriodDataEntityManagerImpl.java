package com.cai310.lottery.service.lottery.zc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.PeriodDataDao;
import com.cai310.lottery.dao.lottery.zc.SczcPeriodDataDao;
import com.cai310.lottery.entity.lottery.zc.SczcPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;

@Service("sczcPeriodDataEntityManagerImpl")
@Transactional
public class SczcPeriodDataEntityManagerImpl extends PeriodDataEntityManagerImpl<SczcPeriodData> {

	@Autowired
	private SczcPeriodDataDao periodDataDao;

	@Override
	@Transactional(readOnly = true)
	protected PeriodDataDao<SczcPeriodData> getPeriodDataDao() {
		return periodDataDao;
	}

	public SczcPeriodData getEntityInstance() {
		return new SczcPeriodData();
	}
	@Override
	public Lottery getLottery() {
		return Lottery.SCZC;
	}
}
