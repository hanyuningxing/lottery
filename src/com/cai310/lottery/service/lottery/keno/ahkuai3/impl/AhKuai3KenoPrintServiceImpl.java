package com.cai310.lottery.service.lottery.keno.ahkuai3.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PrintInterfaceState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.dao.lottery.keno.ahkuai3.AhKuai3IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.ahkuai3.AhKuai3SchemeDao;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3IssueData;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3Scheme;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.PrintService;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeCreateForm;

@Service("ahkuai3KenoPrintServiceImpl")
@Transactional
public class AhKuai3KenoPrintServiceImpl implements PrintService<AhKuai3Scheme>{

	@Autowired
	private AhKuai3SchemeDao schemeDao;
	
	@Autowired
	protected PrintEntityManager printEntityManager;
	
	@Autowired
	private AhKuai3IssueDataDao issueDataDao;
	
	@Override
	public Lottery getLotteryType() {
		return Lottery.AHKUAI3;
	}

	@Override
	public void sendToPrint(AhKuai3Scheme scheme) {
		if (scheme.getLotteryType() != getLotteryType())
			throw new ServiceException("打印服务实例与方案所属彩种不一致.");
		PrintInterface printInterface = new PrintInterface();

		AhKuai3IssueData period = issueDataDao.get(scheme.getPeriodId());
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
		
//		scheme.setState(SchemeState.SUCCESS);
	}

}
