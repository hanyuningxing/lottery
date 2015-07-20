package com.cai310.lottery.service.lottery.keno.ssc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.ssc.SscIssueDataDao;
import com.cai310.lottery.dao.lottery.qyh.QyhMissDataDao;
import com.cai310.lottery.dao.lottery.ssc.SscMissDataDao;
import com.cai310.lottery.entity.lottery.keno.ssc.SscIssueData;
import com.cai310.lottery.entity.lottery.keno.ssc.SscMissDataInfo;
import com.cai310.lottery.service.lottery.keno.impl.MissDataEntityManagerImpl;

@Service("sscMissDataEntityManagerImpl")
@Transactional
public class SscMissDataEntityManagerImpl extends MissDataEntityManagerImpl<SscMissDataInfo, SscIssueData> {

	@Autowired
	private SscIssueDataDao issueDataDao;
	@Autowired
	private SscMissDataDao missDataDao;
	@Override
	public MissDataDao<SscMissDataInfo> getMissDataDao() {
		return missDataDao;
	}
	@Override
	public IssueDataDao<SscIssueData> getIssueDataDao() {
		return issueDataDao;
	}
	

}
