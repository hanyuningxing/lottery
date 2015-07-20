package com.cai310.lottery.service.lottery.keno.xjel11to5.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.SchemeDao;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5Scheme;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.impl.KenoServiceImpl;
import com.cai310.lottery.support.xjel11to5.XjEl11to5PlayType;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeCreateForm;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;

@Service("xjel11to5KenoServiceImpl")
@Transactional
public class XjEl11to5KenoServiceImpl extends KenoServiceImpl<XjEl11to5IssueData, XjEl11to5Scheme> {

	@Resource(name = "xjel11to5KenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<XjEl11to5IssueData, XjEl11to5Scheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}
	@Resource(name = "xjel11to5IssueDataDao")
	public void setIssueDataDao(IssueDataDao<XjEl11to5IssueData> issueDataDao) {
		this.issueDataDao = issueDataDao;
	}

	@Resource(name = "xjel11to5SchemeDao")
	public void setSchemeDao(SchemeDao<XjEl11to5Scheme> schemeDao) {
		this.schemeDao = schemeDao;
	}

	@Override
	public Lottery getLottery() {
		return Lottery.XJEL11TO5;
	}
	
	@Override
	public XjEl11to5Scheme newInstance(KenoSchemeDTO schemeDTO){
		XjEl11to5Scheme scheme=super.newInstance(schemeDTO);
		scheme.setBetType(schemeDTO.getXjEl11to5PlayType());
		return scheme;
	}
	@Override
	public XjEl11to5Scheme newInstance(ChasePlan chasePlan){
		XjEl11to5Scheme scheme=super.newInstance(chasePlan);
		scheme.setBetType(XjEl11to5PlayType.valueOf(chasePlan.getPlayType()));
		return scheme;
	}
	
	@Override
	public ChasePlan setChasePlanPlayType(ChasePlan chasePlan, KenoSchemeDTO schemeDTO){
		chasePlan.setPlayType(schemeDTO.getXjEl11to5PlayType().toString());
		return chasePlan;
	}
	
	@Override
	protected void pushToPrintInterface(XjEl11to5Scheme scheme, XjEl11to5IssueData issueData) {
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
