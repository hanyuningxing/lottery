package com.cai310.lottery.service.lottery.pl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.PeriodDataDao;
import com.cai310.lottery.dao.lottery.pl.PlPeriodDataDao;
import com.cai310.lottery.entity.lottery.pl.PlPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;

@Service("plPeriodDataEntityManagerImpl")
@Transactional
public class PlPeriodDataEntityManagerImpl extends PeriodDataEntityManagerImpl<PlPeriodData> {

	@Autowired
	private PlPeriodDataDao periodDataDao;

	@Override
	@Transactional(readOnly = true)
	protected PeriodDataDao<PlPeriodData> getPeriodDataDao() {
		return periodDataDao;
	}

	public PlPeriodData getEntityInstance() {
		return getPlPeriodData();
	}

	/**
	 * @return {@link #createForm}
	 */
	public PlPeriodData getPlPeriodData() {
		return new PlPeriodData();
	}
	@Override
	public Lottery getLottery() {
		return Lottery.PL;
	}
}
