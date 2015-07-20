package com.cai310.lottery.service.lottery.tc22to5.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5Scheme;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.impl.PrintServiceImpl;

@Service("tc22to5PrintServiceImpl")
@Transactional
public class Tc22to5PrintServiceImpl extends PrintServiceImpl<Tc22to5Scheme> {

	@Autowired
	private Tc22to5SchemeEntityManagerImpl schemeEntityManager;

	@Override
	protected SchemeEntityManager<Tc22to5Scheme> getSchemeEntityManager() {
		return schemeEntityManager;
	}

	public Lottery getLotteryType() {
		return Lottery.TC22TO5;
	}

}
