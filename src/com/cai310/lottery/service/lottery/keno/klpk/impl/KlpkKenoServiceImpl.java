package com.cai310.lottery.service.lottery.keno.klpk.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.SchemeDao;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkIssueData;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkScheme;
import com.cai310.lottery.service.lottery.keno.impl.KenoServiceImpl;
import com.cai310.lottery.support.klpk.KlpkPlayType;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeCreateForm;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;

@Service("klpkKenoServiceImpl")
@Transactional
public class KlpkKenoServiceImpl extends KenoServiceImpl<KlpkIssueData, KlpkScheme> {

	@Resource(name = "klpkIssueDataDao")
	public void setIssueDataDao(IssueDataDao<KlpkIssueData> issueDataDao) {
		this.issueDataDao = issueDataDao;
	}

	@Resource(name = "klpkSchemeDao")
	public void setSchemeDao(SchemeDao<KlpkScheme> schemeDao) {
		this.schemeDao = schemeDao;
	}

	@Override
	public Lottery getLottery() {
		return Lottery.KLPK;
	}
	
	@Override
	public KlpkScheme newInstance(KenoSchemeDTO schemeDTO){
		KlpkScheme scheme=super.newInstance(schemeDTO);
		scheme.setBetType(schemeDTO.getKlpkPlayType());
		return scheme;
	}
	@Override
	public Byte getPlayType(KenoSchemeDTO schemeDTO){
		 return Byte.valueOf(""+schemeDTO.getKlpkPlayType().ordinal());
	}
	@Override
	public KlpkScheme newInstance(ChasePlan chasePlan){
		KlpkScheme scheme=super.newInstance(chasePlan);
		scheme.setBetType(KlpkPlayType.valueOf(chasePlan.getPlayType()));
		return scheme;
	}
	
	@Override
	public ChasePlan setChasePlanPlayType(ChasePlan chasePlan, KenoSchemeDTO schemeDTO){
		chasePlan.setPlayType(schemeDTO.getSdel11to5PlayType().toString());
		return chasePlan;
	}
	
	@Override
	protected void pushToPrintInterface(KlpkScheme scheme, KlpkIssueData issueData) {
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
