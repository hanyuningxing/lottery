package com.cai310.lottery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.ProcedureDao;
import com.cai310.lottery.service.GenerateIdService;

@Service
@Transactional
public class GenerateIdServiceImpl implements GenerateIdService {

	@Autowired
	private ProcedureDao procedureDao;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String generateId(final String key) {
		return procedureDao.generateId(key);
	}
}
