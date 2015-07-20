package com.cai310.lottery.service.lottery.zc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.zc.LczcMatchDao;
import com.cai310.lottery.dao.lottery.zc.ZcMatchDao;
import com.cai310.lottery.entity.lottery.zc.LczcMatch;

@Service("lczcMatchEntityManagerImpl")
@Transactional
public class LczcMatchEntityManagerImpl extends ZcMatchEntityManagerImpl<LczcMatch> {

	@Autowired
	protected LczcMatchDao matchDao;

	@Override
	protected ZcMatchDao<LczcMatch> getZcMatchDao() {
		return this.matchDao;
	}

	@Override
	protected LczcMatch[] getZcMatchInitArray() {
		return new LczcMatch[0];
	}

}
