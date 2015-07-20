package com.cai310.lottery.service.lottery.keno.qyh.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.SchemeDao;
import com.cai310.lottery.entity.lottery.ChasePlan;
import com.cai310.lottery.entity.lottery.PrintInterface;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhIssueData;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhScheme;
import com.cai310.lottery.service.lottery.keno.impl.KenoServiceImpl;
import com.cai310.lottery.support.qyh.QyhPlayType;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeCreateForm;
import com.cai310.lottery.web.controller.lottery.keno.KenoSchemeDTO;

@Service("qyhKenoServiceImpl")
@Transactional
public class QyhKenoServiceImpl extends KenoServiceImpl<QyhIssueData, QyhScheme> {

	@Resource(name = "qyhIssueDataDao")
	public void setIssueDataDao(IssueDataDao<QyhIssueData> issueDataDao) {
		this.issueDataDao = issueDataDao;
	}

	@Resource(name = "qyhSchemeDao")
	public void setSchemeDao(SchemeDao<QyhScheme> schemeDao) {
		this.schemeDao = schemeDao;
	}

	@Override
	public Lottery getLottery() {
		return Lottery.QYH;
	}
	
	@Override
	public QyhScheme newInstance(KenoSchemeDTO schemeDTO){
		QyhScheme scheme=super.newInstance(schemeDTO);
		scheme.setBetType(schemeDTO.getQyhPlayType());
		return scheme;
	}
	
	@Override
	public QyhScheme newInstance(ChasePlan chasePlan){
		QyhScheme scheme=super.newInstance(chasePlan);
		scheme.setBetType(QyhPlayType.valueOf(chasePlan.getPlayType()));
		return scheme;
	}
	
	@Override
	protected void pushToPrintInterface(QyhScheme scheme, QyhIssueData issueData) {
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
		chasePlan.setPlayType(schemeDTO.getQyhPlayType().toString());
		return chasePlan;
	}
}
