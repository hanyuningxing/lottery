package com.cai310.lottery.service.lottery.zc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.zc.LczcSchemeDao;
import com.cai310.lottery.dao.lottery.zc.LczcSchemeWonInfoDao;
import com.cai310.lottery.entity.lottery.zc.LczcPasscount;
import com.cai310.lottery.entity.lottery.zc.LczcScheme;
import com.cai310.lottery.service.lottery.impl.SchemeEntityManagerImpl;

/**
 * 六场半全场足彩方案相关实体管理实现类.
 * 
 */
@Service("lczcSchemeEntityManagerImpl")
@Transactional
public class LczcSchemeEntityManagerImpl extends SchemeEntityManagerImpl<LczcScheme> {

	@Autowired
	private LczcSchemeDao schemeDao;

	@Autowired
	private LczcSchemeWonInfoDao lczcSchemeWonInfoDao;
	
	public LczcPasscount saveLczcSchemeWonInfo(LczcPasscount entity) {
		return lczcSchemeWonInfoDao.save(entity);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public LczcPasscount getLczcSchemeWonInfo(long id) {
		return lczcSchemeWonInfoDao.get(id);
	}
	
	@Override
	protected SchemeDao<LczcScheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void deleteLczcSchemeWonInfo(LczcPasscount entity ) {
		lczcSchemeWonInfoDao.delete(entity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.LCZC;
	}
}
