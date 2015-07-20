package com.cai310.lottery.service.lottery.sevenstar.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.sevenstar.SevenstarPasscountDao;
import com.cai310.lottery.dao.lottery.sevenstar.SevenstarSchemeDao;
import com.cai310.lottery.dao.lottery.sevenstar.SevenstarSchemeWonInfoDao;
import com.cai310.lottery.entity.lottery.sevenstar.SevenstarPasscount;
import com.cai310.lottery.entity.lottery.sevenstar.SevenstarScheme;
import com.cai310.lottery.entity.lottery.sevenstar.SevenstarSchemeWonInfo;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * sevenstar方案相关实体管理实现类.
 * 
 */
@Service("sevenstarSchemeEntityManagerImpl")
@Transactional
public class SevenstarSchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<SevenstarScheme> {

	@Autowired
	private SevenstarSchemeDao schemeDao;

	@Autowired
	private SevenstarSchemeWonInfoDao SevenstarSchemeWonInfoDao;
	
	@Autowired
	private SevenstarPasscountDao passcountSevenstarDao;

	@Override
	protected SchemeDao<SevenstarScheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.SEVENSTAR;
	}

	public SevenstarSchemeWonInfo saveSevenstarSchemeWonInfo(SevenstarSchemeWonInfo entity) {
		return SevenstarSchemeWonInfoDao.save(entity);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public SevenstarSchemeWonInfo getSchemeBet(long id) {
		return SevenstarSchemeWonInfoDao.get(id);
	}

	public SevenstarPasscount saveSevenstarPasscount(SevenstarPasscount entity) {
		return passcountSevenstarDao.save(entity);
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public SevenstarPasscount getPasscountSevenstarDao(long id) {
		return passcountSevenstarDao.get(id);
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void deleteSevenstarPasscount(SevenstarPasscount entity ) {
		passcountSevenstarDao.delete(entity);
	}
}
