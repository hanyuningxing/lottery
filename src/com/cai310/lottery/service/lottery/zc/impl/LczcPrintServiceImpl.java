package com.cai310.lottery.service.lottery.zc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.zc.LczcScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.PrintServiceImpl;

@Service("lczcPrintServiceImpl")
@Transactional
public class LczcPrintServiceImpl extends PrintServiceImpl<LczcScheme> {

	@Autowired
	private LczcSchemeEntityManagerImpl schemeEntityManager;

	@Override
	protected SchemeEntityManager<LczcScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	public Lottery getLotteryType() {
		return Lottery.LCZC;
	}

}
