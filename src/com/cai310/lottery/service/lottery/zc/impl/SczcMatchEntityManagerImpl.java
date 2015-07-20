package com.cai310.lottery.service.lottery.zc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.zc.SczcMatchDao;
import com.cai310.lottery.dao.lottery.zc.ZcMatchDao;
import com.cai310.lottery.entity.lottery.zc.SczcMatch;

@Service("sczcMatchEntityManagerImpl")
@Transactional
public class SczcMatchEntityManagerImpl extends ZcMatchEntityManagerImpl<SczcMatch> {

	@Autowired
	protected SczcMatchDao matchDao;

	@Override
	protected ZcMatchDao<SczcMatch> getZcMatchDao() {
		return this.matchDao;
	}

	@Override
	protected SczcMatch[] getZcMatchInitArray() {
		return new SczcMatch[0];
	}

}
