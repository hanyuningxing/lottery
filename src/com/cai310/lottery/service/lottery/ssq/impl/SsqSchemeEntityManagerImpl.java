package com.cai310.lottery.service.lottery.ssq.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.ssq.SsqPasscountDao;
import com.cai310.lottery.dao.lottery.ssq.SsqSchemeDao;
import com.cai310.lottery.dao.lottery.ssq.SsqSchemeWonInfoDao;
import com.cai310.lottery.entity.lottery.ssq.SsqPasscount;
import com.cai310.lottery.entity.lottery.ssq.SsqScheme;
import com.cai310.lottery.entity.lottery.ssq.SsqSchemeWonInfo;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * 双色球方案相关实体管理实现类.
 * 
 */
@Service("ssqSchemeEntityManagerImpl")
@Transactional
public class SsqSchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<SsqScheme> {

	@Autowired
	private SsqSchemeDao schemeDao;
	@Autowired
	private SsqPasscountDao passcountSsqDao;
	@Autowired
	private SsqSchemeWonInfoDao ssqSchemeBetDao;

	@Override
	protected SchemeDao<SsqScheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.SSQ;
	}

	public SsqSchemeWonInfo saveSsqSchemeWonInfo(SsqSchemeWonInfo entity) {
		return ssqSchemeBetDao.save(entity);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public SsqSchemeWonInfo getSsqSchemeWonInfo(long id) {
		return ssqSchemeBetDao.get(id);
	}
	public SsqPasscount saveSsqPasscount(SsqPasscount entity) {
		return passcountSsqDao.save(entity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public SsqPasscount getSsqPasscount(long id) {
		return passcountSsqDao.get(id);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void deleteSsqPasscount(SsqPasscount ssqPasscount ) {
		  passcountSsqDao.delete(ssqPasscount);
	}
	
}
