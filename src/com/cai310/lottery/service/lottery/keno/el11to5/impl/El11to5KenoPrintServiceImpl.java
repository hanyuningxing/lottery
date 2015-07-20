package com.cai310.lottery.service.lottery.keno.el11to5.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PrintInterfaceState;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.dao.lottery.keno.el11to5.El11to5IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.el11to5.El11to5SchemeDao;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5Scheme;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.PrintService;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeCreateForm;

@Service("el11to5KenoPrintServiceImpl")
@Transactional
public class El11to5KenoPrintServiceImpl implements PrintService<El11to5Scheme> {

	@Autowired
	private El11to5SchemeDao schemeDao;
	
	@Autowired
	protected PrintEntityManager printEntityManager;
	@Autowired
	private El11to5IssueDataDao issueDataDao;

	public Lottery getLotteryType() {
		return Lottery.EL11TO5;
	}

	@Override
	public void sendToPrint(El11to5Scheme scheme) {
		if (scheme.getLotteryType() != getLotteryType())
			throw new ServiceException("打印服务实例与方案所属彩种不一致.");

//		PrintInterface pi = new PrintInterface();
//		pi.setLotteryType(scheme.getLotteryType());
//		pi.setPeriodNumber(scheme.getPeriodNumber());
//		pi.setMode(scheme.getMode());
//		pi.setSponsorId(scheme.getSponsorId());
//		pi.setSchemeNumber(scheme.getSchemeNumber());
//		pi.setUnits(scheme.getUnits());
//		pi.setMultiple(scheme.getMultiple());
//		pi.setSchemeCost(scheme.getSchemeCost());
//		pi.setBetType(Byte.valueOf(String.valueOf(scheme.getPlayType().ordinal())));
//		pi = doSendToPrint(pi, scheme);
//		pi = printEntityManager.savePrintInterface(pi);
		
			PrintInterface printInterface = new PrintInterface();
	
			El11to5IssueData period = issueDataDao.get(scheme.getPeriodId());
			printInterface.setOfficialEndTime(period.getEndedTime());
			
			printInterface.setBetType(scheme.getBetType().getBetTypeValue());
			printInterface.setContent(scheme.getContent());
			printInterface.setCreateTime(scheme.getCreateTime());
			printInterface.setLotteryType(scheme.getLotteryType());
			printInterface.setMode(scheme.getMode());
			printInterface.setMultiple(scheme.getMultiple());
			printInterface.setOfficialEndTime(period.getEndedTime());
			printInterface.setPeriodNumber(scheme.getPeriodNumber());
			printInterface.setSchemeCost(scheme.getUnits()*scheme.getMultiple()*KenoSchemeCreateForm.unitsMoney);
			printInterface.setSchemeNumber(scheme.getSchemeNumber());
			printInterface.setSponsorId(scheme.getSponsorId());
			printInterface.setUnits(scheme.getUnits());
			printInterface.setPrintState(PrintInterfaceState.UNDISASSEMBLE);
			printEntityManager.savePrintInterface(printInterface);
			
			scheme.setState(SchemeState.FULL);
	}
	
}
