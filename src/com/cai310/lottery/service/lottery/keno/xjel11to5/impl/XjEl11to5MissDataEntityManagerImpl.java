package com.cai310.lottery.service.lottery.keno.xjel11to5.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.xjel11to5.XjEl11to5IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.xjel11to5.XjEl11to5MissDataDao;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5MissDataInfo;
import com.cai310.lottery.service.lottery.keno.impl.MissDataEntityManagerImpl;

@Service("xjel11to5MissDataEntityManagerImpl")
@Transactional
public class XjEl11to5MissDataEntityManagerImpl extends MissDataEntityManagerImpl<XjEl11to5MissDataInfo, XjEl11to5IssueData> {

	@Autowired
	private XjEl11to5IssueDataDao issueDataDao;
	@Autowired
	private XjEl11to5MissDataDao missDataDao;
	@Override
	public MissDataDao<XjEl11to5MissDataInfo> getMissDataDao() {
		return missDataDao;
	}
	@Override
	public IssueDataDao<XjEl11to5IssueData> getIssueDataDao() {
		return issueDataDao;
	}

}
