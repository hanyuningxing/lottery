package com.cai310.lottery.service.lottery.tc22to5.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.PeriodDataDao;
import com.cai310.lottery.dao.lottery.tc22to5.Tc22to5PeriodDataDao;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5PeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;

@Service("tc22to5PeriodDataEntityManager")
@Transactional
public class Tc22to5PeriodDataEntityManagerImpl extends PeriodDataEntityManagerImpl<Tc22to5PeriodData> {

	@Autowired
	private Tc22to5PeriodDataDao periodDataDao;

	@Override
	@Transactional(readOnly = true)
	protected PeriodDataDao<Tc22to5PeriodData> getPeriodDataDao() {
		return periodDataDao;
	}

	public Tc22to5PeriodData getEntityInstance() {
		return new Tc22to5PeriodData();
	}
	@Override
	public Lottery getLottery() {
		return Lottery.TC22TO5;
	}
}
