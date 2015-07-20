package com.cai310.lottery.service.lottery.zc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.zc.SfzcScheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.PrintServiceImpl;

@Service("sfzcPrintServiceImpl")
@Transactional
public class SfzcPrintServiceImpl extends PrintServiceImpl<SfzcScheme> {

	@Autowired
	private SfzcSchemeEntityManagerImpl schemeEntityManager;

	@Override
	protected SchemeEntityManager<SfzcScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	public Lottery getLotteryType() {
		return Lottery.SFZC;
	}

	@Override
	protected PrintInterface doSendToPrint(PrintInterface pi, SfzcScheme scheme) {
		pi = super.doSendToPrint(pi, scheme);
		pi.setBetType((byte) scheme.getPlayType().ordinal());// 玩法
		return pi;
	}

}
