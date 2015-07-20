package com.cai310.lottery.service.lottery.seven.impl;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.PeriodDataDao;
import com.cai310.lottery.dao.lottery.seven.SevenPeriodDataDao;
import com.cai310.lottery.entity.lottery.seven.SevenPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sevenPeriodDataEntityManagerImpl")
@Transactional
public class SevenPeriodDataEntityManagerImpl extends PeriodDataEntityManagerImpl<SevenPeriodData> {

	@Autowired
	private SevenPeriodDataDao periodDataDao;

	@Override
	@Transactional(readOnly = true)
	protected PeriodDataDao<SevenPeriodData> getPeriodDataDao() {
		return periodDataDao;
	}

	public SevenPeriodData getEntityInstance() {
		return new SevenPeriodData();
	}
	@Override
	public Lottery getLottery() {
		return Lottery.SEVEN;
	}

}
