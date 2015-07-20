package com.cai310.lottery.service.lottery.jclq.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeTempDao;
import com.cai310.lottery.dao.lottery.jclq.JclqSchemeTempDao;
import com.cai310.lottery.entity.lottery.jclq.JclqSchemeTemp;
import com.cai310.lottery.service.lottery.impl.SchemeTempEntityManagerImpl;


@Service("jclqSchemeTempEntityManagerImpl")
@Transactional
public class JclqSchemeTempEntityManagerImpl extends SchemeTempEntityManagerImpl<JclqSchemeTemp> {

	@Autowired
	private JclqSchemeTempDao schemeTempDao;
	
	@Override
	protected SchemeTempDao<JclqSchemeTemp> getSchemeTempDao() {
		return schemeTempDao;
	}

	@Override
	public Lottery gerLottery() {
		// TODO Auto-generated method stub
		return Lottery.JCLQ;
	}

}
