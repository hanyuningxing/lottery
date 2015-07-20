package com.cai310.lottery.service.lottery.keno.klsf.impl;

import javax.annotation.Resource;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.SchemeDao;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfIssueData;
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfScheme;
import com.cai310.lottery.service.lottery.keno.impl.KenoServiceImpl;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeCreateForm;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("klsfKenoServiceImpl")
@Transactional

public class KlsfKenoServiceImpl extends KenoServiceImpl<KlsfIssueData, KlsfScheme> {

//	@Resource(name = "klsfChasePlanDao")
//	public void setChasePlanDao(ChasePlanDao<KlsfChasePlan> chasePlanDao) {
//		this.chasePlanDao = chasePlanDao;
//	}

	@Resource(name = "klsfIssueDataDao")
	public void setIssueDataDao(IssueDataDao<KlsfIssueData> issueDataDao) {
		this.issueDataDao = issueDataDao;
	}

	@Resource(name = "klsfSchemeDao")
	public void setSchemeDao(SchemeDao<KlsfScheme> schemeDao) {
		this.schemeDao = schemeDao;
	}

	@Override
	public Lottery getLottery() {
		return Lottery.KLSF;
	}
	
	@Override
	public KlsfScheme newInstance(KenoSchemeDTO schemeDTO){
		KlsfScheme scheme=super.newInstance(schemeDTO);
		scheme.setBetType(schemeDTO.getKlsfPlayType());
		return scheme;
	}
	
	@Override
	protected void pushToPrintInterface(KlsfScheme scheme, KlsfIssueData issueData) {
		    PrintInterface printInterface = new PrintInterface();
			printInterface.setBetType(scheme.getBetType().getBetTypeValue());
			printInterface.setContent(scheme.getContent());
			printInterface.setCreateTime(scheme.getCreateTime());
			printInterface.setLotteryType(scheme.getLotteryType());
			printInterface.setMode(scheme.getMode());
			printInterface.setMultiple(scheme.getMultiple());
			printInterface.setOfficialEndTime(issueData.getEndedTime());
			printInterface.setPeriodNumber(scheme.getPeriodNumber());
			printInterface.setSchemeCost(scheme.getUnits() * scheme.getMultiple() * KenoSchemeCreateForm.unitsMoney);
			printInterface.setSchemeNumber(scheme.getSchemeNumber());
			printInterface.setSponsorId(scheme.getSponsorId());
			printInterface.setUnits(scheme.getUnits());
			printInterfaceDao.save(printInterface);

	}

	@Override
	protected ChasePlan setChasePlanPlayType(ChasePlan chasePlan,
			KenoSchemeDTO schemeDTO) {
		// TODO Auto-generated method stub
		return null;
	}
}
