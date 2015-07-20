package com.cai310.lottery.service.lottery.sevenstar.impl;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.sevenstar.SevenstarScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.PrintServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sevenstarPrintServiceImpl")
@Transactional
public class SevenstarPrintServiceImpl extends PrintServiceImpl<SevenstarScheme> {

	@Autowired
	private SevenstarSchemeEntityManagerImpl schemeEntityManager;

	@Override
	protected SchemeEntityManager<SevenstarScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	public Lottery getLotteryType() {
		return Lottery.SEVENSTAR;
	}

}
