package com.cai310.lottery.service.lottery.welfare36to7.impl;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7Scheme;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.PrintServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("welfare36to7PrintServiceImpl")
@Transactional
public class Welfare36to7PrintServiceImpl extends PrintServiceImpl<Welfare36to7Scheme> {

	@Autowired
	private Welfare36to7SchemeEntityManagerImpl schemeEntityManager;

	@Override
	protected SchemeEntityManager<Welfare36to7Scheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	public Lottery getLotteryType() {
		return Lottery.WELFARE36To7;
	}

	@Override
	public void sendToPrint(Welfare36to7Scheme scheme) {
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
