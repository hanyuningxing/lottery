package com.cai310.lottery.service.lottery.keno.sdel11to5.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.sdel11to5.SdEl11to5IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.sdel11to5.SdEl11to5MissDataDao;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5MissDataInfo;
import com.cai310.lottery.service.lottery.keno.impl.MissDataEntityManagerImpl;

@Service("sdel11to5MissDataEntityManagerImpl")
@Transactional
public class SdEl11to5MissDataEntityManagerImpl extends MissDataEntityManagerImpl<SdEl11to5MissDataInfo, SdEl11to5IssueData> {

	@Autowired
	private SdEl11to5IssueDataDao issueDataDao;
	@Autowired
	private SdEl11to5MissDataDao missDataDao;
	@Override
	public MissDataDao<SdEl11to5MissDataInfo> getMissDataDao() {
		return missDataDao;
	}
	@Override
	public IssueDataDao<SdEl11to5IssueData> getIssueDataDao() {
		return issueDataDao;
	}

}
