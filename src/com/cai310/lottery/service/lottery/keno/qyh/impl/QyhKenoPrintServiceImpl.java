package com.cai310.lottery.service.lottery.keno.qyh.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PrintInterfaceState;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.dao.lottery.keno.qyh.QyhIssueDataDao;
import com.cai310.lottery.dao.lottery.keno.qyh.QyhSchemeDao;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhIssueData;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhScheme;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.PrintEntityManager;
import com.cai310.lottery.service.lottery.PrintService;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeCreateForm;

@Service("qyhKenoPrintServiceImpl")
@Transactional
public class QyhKenoPrintServiceImpl implements PrintService<QyhScheme> {

	@Autowired
	private QyhSchemeDao schemeDao;
	
	@Autowired
	protected PrintEntityManager printEntityManager;
	@Autowired
	private QyhIssueDataDao issueDataDao;

	public Lottery getLotteryType() {
		return Lottery.QYH;
	}

	@Override
	public void sendToPrint(QyhScheme scheme) {
		if (scheme.getLotteryType() != getLotteryType())
			throw new ServiceException("打印服务实例与方案所属彩种不一致.");
		
			PrintInterface printInterface = new PrintInterface();
	
			QyhIssueData period = issueDataDao.get(scheme.getPeriodId());
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
