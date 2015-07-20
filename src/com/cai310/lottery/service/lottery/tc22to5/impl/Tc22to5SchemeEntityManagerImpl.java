package com.cai310.lottery.service.lottery.tc22to5.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.tc22to5.Tc22to5PasscountDao;
import com.cai310.lottery.dao.lottery.tc22to5.Tc22to5SchemeDao;
import com.cai310.lottery.dao.lottery.tc22to5.Tc22to5SchemeWonInfoDao;
import com.cai310.lottery.entity.lottery.ssq.SsqPasscount;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5Passcount;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5Scheme;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5SchemeWonInfo;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * 体彩22选5方案相关实体管理实现类.
 * 
 */
@Service("tc22to5SchemeEntityManagerImpl")
@Transactional
public class Tc22to5SchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<Tc22to5Scheme> {

	@Autowired
	private Tc22to5SchemeDao schemeDao;
	@Autowired
	private Tc22to5PasscountDao passcountTc22to5Dao;
	@Autowired
	private Tc22to5SchemeWonInfoDao tc22to5SchemeBetDao;

	@Override
	protected SchemeDao<Tc22to5Scheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.TC22TO5;
	}

	public Tc22to5SchemeWonInfo saveTc22to5SchemeWonInfo(Tc22to5SchemeWonInfo entity) {
		return tc22to5SchemeBetDao.save(entity);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Tc22to5SchemeWonInfo getTc22to5SchemeWonInfo(long id) {
		return tc22to5SchemeBetDao.get(id);
	}
	public Tc22to5Passcount saveTc22to5Passcount(Tc22to5Passcount entity) {
		return passcountTc22to5Dao.save(entity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Tc22to5Passcount getTc22to5Passcount(long id) {
		return passcountTc22to5Dao.get(id);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void deleteTc22to5Passcount(Tc22to5Passcount entity ) {
		passcountTc22to5Dao.delete(entity);
	}
}
