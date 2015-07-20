package com.cai310.lottery.service.lottery.keno.ahkuai3.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.SchemeDao;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3IssueData;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3Scheme;
import com.cai310.lottery.service.lottery.keno.KenoPlayer;
import com.cai310.lottery.service.lottery.keno.impl.KenoServiceImpl;
import com.cai310.lottery.support.ahkuai3.AhKuai3PlayType;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeCreateForm;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;

@Service("ahkuai3KenoServiceImpl")
@Transactional
public class AhKuai3KenoServiceImpl extends KenoServiceImpl<AhKuai3IssueData, AhKuai3Scheme> {
	@Resource(name = "ahkuai3KenoPlayer")
	@Override
	public void setKenoPlayer(KenoPlayer<AhKuai3IssueData, AhKuai3Scheme> kenoPlayer) {
		this.kenoPlayer = kenoPlayer;
	}
	@Override
	public Lottery getLottery() {
		return Lottery.AHKUAI3;
	}
	
	@Resource(name = "ahkuai3IssueDataDao")
	public void setIssueDataDao(IssueDataDao<AhKuai3IssueData> issueDataDao) {
		this.issueDataDao = issueDataDao;
	}

	@Resource(name = "ahkuai3SchemeDao")
	public void setSchemeDao(SchemeDao<AhKuai3Scheme> schemeDao) {
		this.schemeDao = schemeDao;
	}
	
	@Override
	public AhKuai3Scheme newInstance(KenoSchemeDTO schemeDTO){
		AhKuai3Scheme scheme=super.newInstance(schemeDTO);
		scheme.setBetType(schemeDTO.getAhkuai3PlayType());
		return scheme;
	}
	
	@Override
	public AhKuai3Scheme newInstance(ChasePlan chasePlan){
		AhKuai3Scheme scheme=super.newInstance(chasePlan);
		scheme.setBetType(AhKuai3PlayType.valueOf(chasePlan.getPlayType()));
		return scheme;
	}
	/**
	 * 把方案写到打印接口
	 */
	@Override
	protected void pushToPrintInterface(AhKuai3Scheme scheme, AhKuai3IssueData issueData) {
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
	/**
	 * 将玩法添加到追号计划实体中
	 */
	@Override
	protected ChasePlan setChasePlanPlayType(ChasePlan chasePlan, KenoSchemeDTO schemeDTO) {
		chasePlan.setPlayType(schemeDTO.getAhkuai3PlayType().toString());
		return chasePlan;
	}

}
