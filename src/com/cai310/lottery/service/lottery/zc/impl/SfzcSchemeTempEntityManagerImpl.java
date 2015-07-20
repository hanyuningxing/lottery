package com.cai310.lottery.service.lottery.zc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeTempDao;
import com.cai310.lottery.dao.lottery.zc.SfzcSchemeTempDao;
import com.cai310.lottery.entity.lottery.zc.SfzcSchemeTemp;
import com.cai310.lottery.service.lottery.impl.SchemeTempEntityManagerImpl;


@Service("sfzcSchemeTempEntityManagerImpl")
@Transactional
public class SfzcSchemeTempEntityManagerImpl extends SchemeTempEntityManagerImpl<SfzcSchemeTemp> {

	@Autowired
	private SfzcSchemeTempDao schemeTempDao;
	
	@Override
	protected SchemeTempDao<SfzcSchemeTemp> getSchemeTempDao() {
		return schemeTempDao;
	}

	@Override
	public Lottery gerLottery() {
		return Lottery.SFZC;
	}

}
