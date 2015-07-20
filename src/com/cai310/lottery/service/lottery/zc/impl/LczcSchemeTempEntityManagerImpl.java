package com.cai310.lottery.service.lottery.zc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeTempDao;
import com.cai310.lottery.dao.lottery.zc.LczcSchemeTempDao;
import com.cai310.lottery.entity.lottery.zc.LczcSchemeTemp;
import com.cai310.lottery.service.lottery.impl.SchemeTempEntityManagerImpl;


@Service("lczcSchemeTempEntityManagerImpl")
@Transactional
public class LczcSchemeTempEntityManagerImpl extends SchemeTempEntityManagerImpl<LczcSchemeTemp> {

	@Autowired
	private LczcSchemeTempDao schemeTempDao;
	
	@Override
	protected SchemeTempDao<LczcSchemeTemp> getSchemeTempDao() {
		return schemeTempDao;
	}

	@Override
	public Lottery gerLottery() {
		return Lottery.LCZC;
	}

}
