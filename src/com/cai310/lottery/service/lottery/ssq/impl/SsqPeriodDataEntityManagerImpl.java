package com.cai310.lottery.service.lottery.ssq.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.PeriodDataDao;
import com.cai310.lottery.dao.lottery.ssq.SsqPeriodDataDao;
import com.cai310.lottery.entity.lottery.ssq.SsqPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;

@Service("ssqPeriodDataEntityManager")
@Transactional
public class SsqPeriodDataEntityManagerImpl extends PeriodDataEntityManagerImpl<SsqPeriodData> {

	@Autowired
	private SsqPeriodDataDao periodDataDao;

	@Override
	@Transactional(readOnly = true)
	protected PeriodDataDao<SsqPeriodData> getPeriodDataDao() {
		return periodDataDao;
	}

	public SsqPeriodData getEntityInstance() {
		return new SsqPeriodData();
	}
	@Override
	public Lottery getLottery() {
		return Lottery.SSQ;
	}
}
