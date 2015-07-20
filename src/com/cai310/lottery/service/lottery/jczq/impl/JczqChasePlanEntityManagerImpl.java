package com.cai310.lottery.service.lottery.jczq.impl;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cai310.lottery.dao.lottery.jczq.JczqChasePlanDao;
import com.cai310.lottery.entity.lottery.jczq.JczqChasePlan;
import com.cai310.lottery.service.lottery.jczq.JczqChasePlanEntityManager;

@Service("JczqChasePlayEntityManagerImpl")
@Transactional
public class JczqChasePlanEntityManagerImpl implements JczqChasePlanEntityManager{
	@Autowired
	private JczqChasePlanDao chasePlanDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public JczqChasePlan getChasePlan(Long id) {
		return chasePlanDao.get(id);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public JczqChasePlan saveChasePlan(JczqChasePlan entity) {		
		return chasePlanDao.save(entity);
	}

	@Override
	public JczqChasePlan save(JczqChasePlan plan) {
		return chasePlanDao.save(plan);		
	}
}
