package com.cai310.lottery.service.lottery.zc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.zc.SczcScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.PrintServiceImpl;

@Service("sczcPrintServiceImpl")
@Transactional
public class SczcPrintServiceImpl extends PrintServiceImpl<SczcScheme> {

	@Autowired
	private SczcSchemeEntityManagerImpl schemeEntityManager;

	@Override
	protected SchemeEntityManager<SczcScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	public Lottery getLotteryType() {
		return Lottery.SCZC;
	}

}
