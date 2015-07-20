package com.cai310.lottery.service.lottery.welfare3d.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.welfare3d.Welfare3dPasscountDao;
import com.cai310.lottery.dao.lottery.welfare3d.Welfare3dSchemeDao;
import com.cai310.lottery.dao.lottery.welfare3d.Welfare3dSchemeWonInfoDao;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dPasscount;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dScheme;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dSchemeWonInfo;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * 3D方案相关实体管理实现类.
 * 
 */
@Service("welfare3dSchemeEntityManagerImpl")
@Transactional
public class Welfare3dSchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<Welfare3dScheme> {

	@Autowired
	private Welfare3dSchemeDao schemeDao;

	@Autowired
	private Welfare3dSchemeWonInfoDao welfare3dSchemeWonInfoDao;

	@Autowired
	private Welfare3dPasscountDao passcountWelfare3dDao;
	
	
	@Override
	protected SchemeDao<Welfare3dScheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.WELFARE3D;
	}

	public Welfare3dSchemeWonInfo saveWelfare3dSchemeWonInfo(Welfare3dSchemeWonInfo entity) {
		return welfare3dSchemeWonInfoDao.save(entity);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Welfare3dSchemeWonInfo getSchemeBet(long id) {
		return welfare3dSchemeWonInfoDao.get(id);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public  Welfare3dPasscount saveWelfare3dPasscount(Welfare3dPasscount entity) {
		return passcountWelfare3dDao.save(entity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Welfare3dPasscount getWelfare3dPasscount(long id) {
		return passcountWelfare3dDao.get(id);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void deleteWelfare3dPasscount(Welfare3dPasscount entity ) {
		passcountWelfare3dDao.delete(entity);
	}
}
