package com.cai310.lottery.service.lottery.welfare3d.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.PeriodDataDao;
import com.cai310.lottery.dao.lottery.welfare3d.Welfare3dPeriodDataDao;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dPeriodData;
import com.cai310.lottery.service.lottery.impl.PeriodDataEntityManagerImpl;

@Service("welfare3dPeriodDataEntityManagerImpl")
@Transactional
public class Welfare3dPeriodDataEntityManagerImpl extends PeriodDataEntityManagerImpl<Welfare3dPeriodData> {

	@Autowired
	private Welfare3dPeriodDataDao periodDataDao;

	@Override
	@Transactional(readOnly = true)
	protected PeriodDataDao<Welfare3dPeriodData> getPeriodDataDao() {
		return periodDataDao;
	}

	public Welfare3dPeriodData getEntityInstance() {
		return getWelfare3dPeriodData();
	}

	/**
	 * @return {@link #createForm}
	 */
	public Welfare3dPeriodData getWelfare3dPeriodData() {
		return new Welfare3dPeriodData();
	}
	@Override
	public Lottery getLottery() {
		return Lottery.WELFARE3D;
	}
}
