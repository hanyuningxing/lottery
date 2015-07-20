package com.cai310.lottery.service.lottery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.PrintInterfaceState;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.PrintService;
import com.cai310.lottery.service.lottery.SchemeEntityManager;

/**
 * 打印服务实现类
 * 
 */
@Transactional
public abstract class PrintServiceImpl<T extends Scheme> implements PrintService<T> {

	@Autowired
	protected PrintEntityManager printEntityManager;

	@Autowired
	protected PeriodEntityManager periodEntityManager;

	protected abstract SchemeEntityManager<T> getSchemeEntityManager();

	public void sendToPrint(T scheme) {
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

		pi = doSendToPrint(pi, scheme);
		pi = printEntityManager.savePrintInterface(pi);
	}

	protected  PrintInterface doSendToPrint(PrintInterface pi, T scheme) {
		pi.setContent(scheme.toPrintString());
		pi.setPrintState(PrintInterfaceState.UNDISASSEMBLE);
		Period period = periodEntityManager.getPeriod(scheme.getPeriodId());
		pi.setOfficialEndTime(period.getEndedTime());

		return pi;
	}
}
