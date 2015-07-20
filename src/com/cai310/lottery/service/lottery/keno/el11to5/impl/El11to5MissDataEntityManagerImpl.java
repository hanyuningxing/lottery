package com.cai310.lottery.service.lottery.keno.el11to5.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.el11to5.El11to5IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.el11to5.El11to5MissDataDao;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5MissDataInfo;
import com.cai310.lottery.service.lottery.keno.impl.MissDataEntityManagerImpl;

@Service("el11to5MissDataEntityManagerImpl")
@Transactional
public class El11to5MissDataEntityManagerImpl extends MissDataEntityManagerImpl<El11to5MissDataInfo, El11to5IssueData> {

	@Autowired
	private El11to5IssueDataDao issueDataDao;
	@Autowired
	private El11to5MissDataDao missDataDao;
	@Override
	public MissDataDao<El11to5MissDataInfo> getMissDataDao() {
		return missDataDao;
	}
	@Override
	public IssueDataDao<El11to5IssueData> getIssueDataDao() {
		return issueDataDao;
	}

}
