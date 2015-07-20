package com.cai310.lottery.service.lottery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.dao.lottery.ChasePlanDetailDao;
import com.cai310.lottery.entity.lottery.ChasePlanDetail;
import com.cai310.lottery.service.lottery.ChasePlanDetailEntityManager;

@Service("chasePlanDetailEntityManagerImpl")
@Transactional
public class ChasePlanDetailEntityManagerImpl implements ChasePlanDetailEntityManager {

	@Autowired
	protected ChasePlanDetailDao chasePlanDetailDao;

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ChasePlanDetail getChasePlanDetail(Long id) {
		return chasePlanDetailDao.get(id);
	}

	public ChasePlanDetail saveChasePlanDetailDetail(ChasePlanDetail entity) {
		return chasePlanDetailDao.save(entity);
	}

}
