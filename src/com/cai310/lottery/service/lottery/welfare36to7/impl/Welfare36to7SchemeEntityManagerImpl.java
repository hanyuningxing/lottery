package com.cai310.lottery.service.lottery.welfare36to7.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.welfare36to7.Welfare36to7PasscountDao;
import com.cai310.lottery.dao.lottery.welfare36to7.Welfare36to7SchemeDao;
import com.cai310.lottery.dao.lottery.welfare36to7.Welfare36to7SchemeWonInfoDao;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7Passcount;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7Scheme;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7SchemeWonInfo;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * welfare36to7方案相关实体管理实现类.
 * 
 */
@Service("welfare36to7SchemeEntityManagerImpl")
@Transactional
public class Welfare36to7SchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<Welfare36to7Scheme> {

	@Autowired
	private Welfare36to7SchemeDao schemeDao;

	@Autowired
	private Welfare36to7PasscountDao passcountWelfare36to7Dao;
	
	@Autowired
	private Welfare36to7SchemeWonInfoDao Welfare36to7SchemeWonInfoDao;

	@Override
	protected SchemeDao<Welfare36to7Scheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.WELFARE36To7;
	}

	public Welfare36to7SchemeWonInfo saveWelfare36to7SchemeWonInfo(Welfare36to7SchemeWonInfo entity) {
		return Welfare36to7SchemeWonInfoDao.save(entity);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Welfare36to7SchemeWonInfo getSchemeBet(long id) {
		return Welfare36to7SchemeWonInfoDao.get(id);
	}
 
	public Welfare36to7Passcount saveWelfare36to7Passcount(Welfare36to7Passcount entity) {
		return passcountWelfare36to7Dao.save(entity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Welfare36to7Passcount getWelfare36to7Passcount(long id) {
		return passcountWelfare36to7Dao.get(id);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void deleteWelfare36to7Passcount(Welfare36to7Passcount entity ) {
		passcountWelfare36to7Dao.delete(entity);
	}
}
