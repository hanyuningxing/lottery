package com.cai310.lottery.service.lottery.keno.ssc;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.SchemeDao;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.keno.ssc.SscIssueData;
import com.cai310.lottery.entity.lottery.keno.ssc.SscScheme;
import com.cai310.lottery.service.lottery.keno.impl.KenoServiceImpl;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeCreateForm;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;

@Service("sscKenoServiceImpl")
@Transactional
public class SscKenoServiceImpl extends KenoServiceImpl<SscIssueData,SscScheme> {

	@Resource(name = "sscIssueDataDao")
	public void setIssueDataDao(IssueDataDao<SscIssueData> issueDataDao) {
		this.issueDataDao = issueDataDao;
	}

	@Resource(name = "sscSchemeDao")
	public void setSchemeDao(SchemeDao<SscScheme> schemeDao) {
		this.schemeDao = schemeDao;
	}

	@Override
	public Lottery getLottery() {
		return Lottery.SSC;
	}
	
	@Override
	public SscScheme newInstance(KenoSchemeDTO schemeDTO){
		SscScheme scheme=super.newInstance(schemeDTO);
		scheme.setBetType(schemeDTO.getSscPlayType());
		return scheme;
	}
	
	@Override
	protected void pushToPrintInterface(SscScheme scheme, SscIssueData issueData) {
				PrintInterface printInterface = new PrintInterface();
				printInterface.setBetType(scheme.getBetType().getBetTypeValue());
				printInterface.setContent(scheme.getContent());
				printInterface.setCreateTime(scheme.getCreateTime());
				printInterface.setLotteryType(scheme.getLotteryType());
				printInterface.setMode(scheme.getMode());
				printInterface.setMultiple(scheme.getMultiple());
				printInterface.setOfficialEndTime(issueData.getEndedTime());
				printInterface.setPeriodNumber(scheme.getPeriodNumber());
				printInterface.setSchemeCost(scheme.getUnits()*scheme.getMultiple()*KenoSchemeCreateForm.unitsMoney);
				printInterface.setSchemeNumber(scheme.getSchemeNumber());
				printInterface.setSponsorId(scheme.getSponsorId());
				printInterface.setUnits(scheme.getUnits());
				printInterfaceDao.save(printInterface);

	}

	@Override
	protected ChasePlan setChasePlanPlayType(ChasePlan chasePlan, KenoSchemeDTO schemeDTO) {
		chasePlan.setPlayType(schemeDTO.getSscPlayType().toString());
		return chasePlan;
	}

}
