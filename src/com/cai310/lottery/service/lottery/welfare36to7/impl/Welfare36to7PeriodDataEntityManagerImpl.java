package com.cai310.lottery.service.lottery.welfare36to7.impl;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.PeriodDataDao;
import com.cai310.lottery.dao.lottery.welfare36to7.Welfare36to7PeriodDataDao;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7PeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("welfare36to7PeriodDataEntityManagerImpl")
@Transactional
public class Welfare36to7PeriodDataEntityManagerImpl extends PeriodDataEntityManagerImpl<Welfare36to7PeriodData> {

	@Autowired
	private Welfare36to7PeriodDataDao periodDataDao;

	@Override
	@Transactional(readOnly = true)
	protected PeriodDataDao<Welfare36to7PeriodData> getPeriodDataDao() {
		return periodDataDao;
	}

	public Welfare36to7PeriodData getEntityInstance() {
		return getWelfare36to7PeriodData();
	}

	/**
	 * @return {@link #createForm}
	 */
	public Welfare36to7PeriodData getWelfare36to7PeriodData() {
		return new Welfare36to7PeriodData();
	}
	@Override
	public Lottery getLottery() {
		return Lottery.WELFARE36To7;
	}

}
