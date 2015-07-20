package com.cai310.lottery.service.lottery.keno.gdel11to5.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.gdel11to5.GdEl11to5IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.gdel11to5.GdEl11to5MissDataDao;
import com.cai310.lottery.entity.lottery.keno.gdel11to5.GdEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.gdel11to5.GdEl11to5MissDataInfo;
import com.cai310.lottery.service.lottery.keno.impl.MissDataEntityManagerImpl;

@Service("gdel11to5MissDataEntityManagerImpl")
@Transactional
public class GdEl11to5MissDataEntityManagerImpl extends MissDataEntityManagerImpl<GdEl11to5MissDataInfo, GdEl11to5IssueData> {

	@Autowired
	private GdEl11to5IssueDataDao issueDataDao;
	@Autowired
	private GdEl11to5MissDataDao missDataDao;
	@Override
	public MissDataDao<GdEl11to5MissDataInfo> getMissDataDao() {
		return missDataDao;
	}
	@Override
	public IssueDataDao<GdEl11to5IssueData> getIssueDataDao() {
		return issueDataDao;
	}

}
