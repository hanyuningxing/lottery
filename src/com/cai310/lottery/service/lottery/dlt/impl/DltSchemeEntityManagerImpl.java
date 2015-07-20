package com.cai310.lottery.service.lottery.dlt.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.dlt.DltPasscountDao;
import com.cai310.lottery.dao.lottery.dlt.DltSchemeDao;
import com.cai310.lottery.dao.lottery.dlt.DltSchemeWonInfoDao;
import com.cai310.lottery.entity.lottery.dlt.DltPasscount;
import com.cai310.lottery.entity.lottery.dlt.DltScheme;
import com.cai310.lottery.entity.lottery.dlt.DltSchemeWonInfo;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5Passcount;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * 大乐透方案相关实体管理实现类.
 * 
 */
@Service("dltSchemeEntityManagerImpl")
@Transactional
public class DltSchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<DltScheme> {

	@Autowired
	private DltSchemeDao schemeDao;

	@Autowired
	private DltSchemeWonInfoDao dltSchemeBetDao;
	
	@Autowired
	private DltPasscountDao passcountDltDao;

	@Override
	protected SchemeDao<DltScheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.DLT;
	}

	public DltSchemeWonInfo saveDltSchemeWonInfo(DltSchemeWonInfo entity) {
		return dltSchemeBetDao.save(entity);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DltSchemeWonInfo getDltSchemeWonInfo(long id) {
		return dltSchemeBetDao.get(id);
	}
	
	public DltPasscount saveDltPasscount(DltPasscount entity) {
		return passcountDltDao.save(entity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DltPasscount getDltPasscount(long id) {
		return passcountDltDao.get(id);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void deleteDltPasscount(DltPasscount entity ) {
		passcountDltDao.delete(entity);
	}
}
