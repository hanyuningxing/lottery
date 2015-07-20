package com.cai310.lottery.service.lottery.keno.sdel11to5.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PrintInterfaceState;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.dao.lottery.keno.sdel11to5.SdEl11to5IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.sdel11to5.SdEl11to5SchemeDao;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.keno.KenoScheme;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5Scheme;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.PrintService;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeCreateForm;

@Service("sdel11to5KenoPrintServiceImpl")
@Transactional
public class SdEl11to5KenoPrintServiceImpl implements PrintService<SdEl11to5Scheme> {

	@Autowired
	private SdEl11to5SchemeDao schemeDao;
	
	@Autowired
	protected PrintEntityManager printEntityManager;
	@Autowired
	private SdEl11to5IssueDataDao issueDataDao;

	public Lottery getLotteryType() {
		return Lottery.SDEL11TO5;
	}

	@Override
	public void sendToPrint(SdEl11to5Scheme scheme) {
		if (scheme.getLotteryType() != getLotteryType())
			throw new ServiceException("打印服务实例与方案所属彩种不一致.");
		
			PrintInterface printInterface = new PrintInterface();
	
			SdEl11to5IssueData period = issueDataDao.get(scheme.getPeriodId());
			printInterface.setOfficialEndTime(period.getEndedTime());
			
			printInterface.setBetType(scheme.getBetType().getBetTypeValue());
			printInterface.setContent(scheme.getContent());
			printInterface.setCreateTime(scheme.getCreateTime());
			printInterface.setLotteryType(scheme.getLotteryType());
			printInterface.setMode(scheme.getMode());
			printInterface.setMultiple(scheme.getMultiple());
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
