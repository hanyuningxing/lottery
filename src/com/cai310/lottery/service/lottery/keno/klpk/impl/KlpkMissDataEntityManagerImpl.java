package com.cai310.lottery.service.lottery.keno.klpk.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.klpk.KlpkIssueDataDao;
import com.cai310.lottery.dao.lottery.keno.klpk.KlpkMissDataDao;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkIssueData;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkMissDataInfo;
import com.cai310.lottery.service.lottery.keno.impl.MissDataEntityManagerImpl;

@Service("klpkMissDataEntityManagerImpl")
@Transactional
public class KlpkMissDataEntityManagerImpl extends MissDataEntityManagerImpl<KlpkMissDataInfo, KlpkIssueData> {

	@Autowired
	private KlpkIssueDataDao issueDataDao;
	@Autowired
	private KlpkMissDataDao missDataDao;
	@Override
	public MissDataDao<KlpkMissDataInfo> getMissDataDao() {
		return missDataDao;
	}
	@Override
	public IssueDataDao<KlpkIssueData> getIssueDataDao() {
		return issueDataDao;
	}

}
