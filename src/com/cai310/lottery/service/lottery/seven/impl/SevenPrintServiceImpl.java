package com.cai310.lottery.service.lottery.seven.impl;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.seven.SevenScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.PrintServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sevenPrintServiceImpl")
@Transactional
public class SevenPrintServiceImpl extends PrintServiceImpl<SevenScheme> {

	@Autowired
	private SevenSchemeEntityManagerImpl schemeEntityManager;

	@Override
	protected SchemeEntityManager<SevenScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	public Lottery getLotteryType() {
		return Lottery.SEVEN;
	}

}
