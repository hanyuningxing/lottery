package com.cai310.lottery.service.lottery.zc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeTempDao;
import com.cai310.lottery.dao.lottery.zc.SczcSchemeTempDao;
import com.cai310.lottery.entity.lottery.zc.SczcSchemeTemp;
import com.cai310.lottery.service.lottery.impl.SchemeTempEntityManagerImpl;


@Service("sczcSchemeTempEntityManagerImpl")
@Transactional
public class SczcSchemeTempEntityManagerImpl extends SchemeTempEntityManagerImpl<SczcSchemeTemp> {

	@Autowired
	private SczcSchemeTempDao schemeTempDao;
	
	@Override
	protected SchemeTempDao<SczcSchemeTemp> getSchemeTempDao() {
		return schemeTempDao;
	}

	@Override
	public Lottery gerLottery() {
		return Lottery.SCZC;
	}

}
