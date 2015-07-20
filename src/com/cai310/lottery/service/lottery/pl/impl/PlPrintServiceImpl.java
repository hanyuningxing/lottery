package com.cai310.lottery.service.lottery.pl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.pl.PlScheme;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.PrintServiceImpl;

@Service("plPrintServiceImpl")
@Transactional
public class PlPrintServiceImpl extends PrintServiceImpl<PlScheme> {

	@Autowired
	private PlSchemeEntityManagerImpl schemeEntityManager;

	@Override
	protected SchemeEntityManager<PlScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	public Lottery getLotteryType() {
		return Lottery.PL;
	}

	@Override
	public void sendToPrint(PlScheme scheme) {
		if (scheme.getLotteryType() != getLotteryType())
			throw new ServiceException("打印服务实例与方案所属彩种不一致.");

		PrintInterface pi = new PrintInterface();
		pi.setLotteryType(scheme.getLotteryType());
		pi.setPeriodNumber(scheme.getPeriodNumber());
		pi.setMode(scheme.getMode());
		pi.setSponsorId(scheme.getSponsorId());
		pi.setSchemeNumber(scheme.getSchemeNumber());
		pi.setUnits(scheme.getUnits());
		pi.setMultiple(scheme.getMultiple());
		pi.setSchemeCost(scheme.getSchemeCost());
		pi.setBetType(Byte.valueOf(String.valueOf(scheme.getPlayType().ordinal())));
		pi = doSendToPrint(pi, scheme);
		pi = printEntityManager.savePrintInterface(pi);
	}
}
