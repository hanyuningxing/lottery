package com.cai310.lottery.service.lottery.zc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.PeriodDataDao;
import com.cai310.lottery.dao.lottery.zc.SfzcPeriodDataDao;
import com.cai310.lottery.entity.lottery.zc.SfzcPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;

@Service("sfzcPeriodDataEntityManagerImpl")
@Transactional
public class SfzcPeriodDataEntityManagerImpl extends PeriodDataEntityManagerImpl<SfzcPeriodData> {

	@Autowired
	private SfzcPeriodDataDao periodDataDao;

	@Override
	@Transactional(readOnly = true)
	protected PeriodDataDao<SfzcPeriodData> getPeriodDataDao() {
		return periodDataDao;
	}

	public SfzcPeriodData getEntityInstance() {
		return new SfzcPeriodData();
	}
	@Override
	public Lottery getLottery() {
		return Lottery.SFZC;
	}

}
