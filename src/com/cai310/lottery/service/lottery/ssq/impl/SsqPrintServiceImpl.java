package com.cai310.lottery.service.lottery.ssq.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.ssq.SsqScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.PrintServiceImpl;

@Service("ssqPrintServiceImpl")
@Transactional
public class SsqPrintServiceImpl extends PrintServiceImpl<SsqScheme> {

	@Autowired
	private SsqSchemeEntityManagerImpl schemeEntityManager;
	@Override
	protected SchemeEntityManager<SsqScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	public Lottery getLotteryType() {
		return Lottery.SSQ;
	}
}
