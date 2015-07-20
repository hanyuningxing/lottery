package com.cai310.lottery.service.lottery.keno.qyh.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.qyh.QyhIssueDataDao;
import com.cai310.lottery.dao.lottery.qyh.QyhMissDataDao;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhIssueData;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhMissDataInfo;
import com.cai310.lottery.service.lottery.keno.impl.MissDataEntityManagerImpl;

@Service("qyhMissDataEntityManagerImpl")
@Transactional
public class QyhMissDataEntityManagerImpl extends MissDataEntityManagerImpl<QyhMissDataInfo, QyhIssueData> {

	@Autowired
	private QyhIssueDataDao issueDataDao;
	@Autowired
	private QyhMissDataDao missDataDao;
	@Override
	public MissDataDao<QyhMissDataInfo> getMissDataDao() {
		return missDataDao;
	}
	@Override
	public IssueDataDao<QyhIssueData> getIssueDataDao() {
		return issueDataDao;
	}

}
