package com.cai310.lottery.service.lottery.sevenstar.impl;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.PeriodDataDao;
import com.cai310.lottery.dao.lottery.sevenstar.SevenstarPeriodDataDao;
import com.cai310.lottery.entity.lottery.sevenstar.SevenstarPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sevenstarPeriodDataEntityManagerImpl")
@Transactional
public class SevenstarPeriodDataEntityManagerImpl extends PeriodDataEntityManagerImpl<SevenstarPeriodData> {

	@Autowired
	private SevenstarPeriodDataDao periodDataDao;

	@Override
	@Transactional(readOnly = true)
	protected PeriodDataDao<SevenstarPeriodData> getPeriodDataDao() {
		return periodDataDao;
	}

	public SevenstarPeriodData getEntityInstance() {
		return new SevenstarPeriodData();
	}
	@Override
	public Lottery getLottery() {
		return Lottery.SEVENSTAR;
	}
}
