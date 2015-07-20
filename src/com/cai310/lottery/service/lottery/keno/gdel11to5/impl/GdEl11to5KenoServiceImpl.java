package com.cai310.lottery.service.lottery.keno.gdel11to5.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.SchemeDao;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.keno.gdel11to5.GdEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.gdel11to5.GdEl11to5Scheme;
import com.cai310.lottery.service.lottery.keno.impl.KenoServiceImpl;
import com.cai310.lottery.support.gdel11to5.GdEl11to5PlayType;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeCreateForm;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;

@Service("gdel11to5KenoServiceImpl")
@Transactional
public class GdEl11to5KenoServiceImpl extends KenoServiceImpl<GdEl11to5IssueData, GdEl11to5Scheme> {

	@Resource(name = "gdel11to5IssueDataDao")
	public void setIssueDataDao(IssueDataDao<GdEl11to5IssueData> issueDataDao) {
		this.issueDataDao = issueDataDao;
	}

	@Resource(name = "gdel11to5SchemeDao")
	public void setSchemeDao(SchemeDao<GdEl11to5Scheme> schemeDao) {
		this.schemeDao = schemeDao;
	}

	@Override
	public Lottery getLottery() {
		return Lottery.GDEL11TO5;
	}
	
	@Override
	public GdEl11to5Scheme newInstance(KenoSchemeDTO schemeDTO){
		GdEl11to5Scheme scheme=super.newInstance(schemeDTO);
		scheme.setBetType(schemeDTO.getGdel11to5PlayType());
		return scheme;
	}
	@Override
	public GdEl11to5Scheme newInstance(ChasePlan chasePlan){
		GdEl11to5Scheme scheme=super.newInstance(chasePlan);
		scheme.setBetType(GdEl11to5PlayType.valueOf(chasePlan.getPlayType()));
		return scheme;
	}
	
	@Override
	public ChasePlan setChasePlanPlayType(ChasePlan chasePlan, KenoSchemeDTO schemeDTO){
		chasePlan.setPlayType(schemeDTO.getGdel11to5PlayType().toString());
		return chasePlan;
	}
	
	@Override
	protected void pushToPrintInterface(GdEl11to5Scheme scheme, GdEl11to5IssueData issueData) {
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


}
