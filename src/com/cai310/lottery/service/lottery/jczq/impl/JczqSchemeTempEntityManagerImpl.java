package com.cai310.lottery.service.lottery.jczq.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeTempDao;
import com.cai310.lottery.dao.lottery.jczq.JczqSchemeTempDao;
import com.cai310.lottery.entity.lottery.jczq.JczqSchemeTemp;
import com.cai310.lottery.service.lottery.impl.SchemeTempEntityManagerImpl;


@Service("jczqSchemeTempEntityManagerImpl")
@Transactional
public class JczqSchemeTempEntityManagerImpl extends SchemeTempEntityManagerImpl<JczqSchemeTemp> {

	@Autowired
	private JczqSchemeTempDao schemeTempDao;
	
	@Override
	protected SchemeTempDao<JczqSchemeTemp> getSchemeTempDao() {
		return schemeTempDao;
	}

	@Override
	public Lottery gerLottery() {
		// TODO Auto-generated method stub
		return Lottery.JCZQ;
	}

}
