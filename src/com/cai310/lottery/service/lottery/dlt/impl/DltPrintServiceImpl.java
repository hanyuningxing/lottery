package com.cai310.lottery.service.lottery.dlt.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.dlt.DltScheme;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.PrintServiceImpl;

@Service("dltPrintServiceImpl")
@Transactional
public class DltPrintServiceImpl extends PrintServiceImpl<DltScheme> {

	@Autowired
	private DltSchemeEntityManagerImpl schemeEntityManager;

	@Override
	protected SchemeEntityManager<DltScheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	public Lottery getLotteryType() {
		return Lottery.DLT;
	}

	@Override
	public void sendToPrint(DltScheme scheme) {
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
