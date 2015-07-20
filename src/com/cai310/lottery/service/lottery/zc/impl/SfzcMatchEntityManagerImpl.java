package com.cai310.lottery.service.lottery.zc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.zc.SfzcMatchDao;
import com.cai310.lottery.dao.lottery.zc.ZcMatchDao;
import com.cai310.lottery.entity.lottery.zc.SfzcMatch;

@Service("sfzcMatchEntityManagerImpl")
@Transactional
public class SfzcMatchEntityManagerImpl extends ZcMatchEntityManagerImpl<SfzcMatch> {

	@Autowired
	protected SfzcMatchDao matchDao;

	@Override
	protected ZcMatchDao<SfzcMatch> getZcMatchDao() {
		return this.matchDao;
	}

	@Override
	protected SfzcMatch[] getZcMatchInitArray() {
		return new SfzcMatch[0];
	}
}