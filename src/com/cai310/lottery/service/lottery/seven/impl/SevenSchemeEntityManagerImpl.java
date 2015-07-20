package com.cai310.lottery.service.lottery.seven.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.seven.SevenPasscountDao;
import com.cai310.lottery.dao.lottery.seven.SevenSchemeDao;
import com.cai310.lottery.dao.lottery.seven.SevenSchemeWonInfoDao;
import com.cai310.lottery.entity.lottery.seven.SevenPasscount;
import com.cai310.lottery.entity.lottery.seven.SevenScheme;
import com.cai310.lottery.entity.lottery.seven.SevenSchemeWonInfo;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * seven方案相关实体管理实现类.
 * 
 */
@Service("sevenSchemeEntityManagerImpl")
@Transactional
public class SevenSchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<SevenScheme> {

	@Autowired
	private SevenSchemeDao schemeDao;

	@Autowired
	private SevenPasscountDao passcountSevenDao;
	
	@Autowired
	private SevenSchemeWonInfoDao sevenSchemeWonInfoDao;

	@Override
	protected SchemeDao<SevenScheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.SEVEN;
	}

	public SevenSchemeWonInfo saveSevenSchemeWonInfo(SevenSchemeWonInfo entity) {
		return sevenSchemeWonInfoDao.save(entity);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public SevenSchemeWonInfo getSchemeBet(long id) {
		return sevenSchemeWonInfoDao.get(id);
	}
	
 
	public SevenPasscount saveSevenPasscount(SevenPasscount entity) {
		return passcountSevenDao.save(entity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public SevenPasscount getSevenPasscount(long id) {
		return passcountSevenDao.get(id);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void deleteSevenPasscount(SevenPasscount entity ) {
		passcountSevenDao.delete(entity);
	}
}
