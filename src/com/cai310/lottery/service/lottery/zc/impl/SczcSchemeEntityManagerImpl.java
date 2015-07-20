package com.cai310.lottery.service.lottery.zc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.zc.SczcSchemeDao;
import com.cai310.lottery.dao.lottery.zc.SczcSchemeWonInfoDao;
import com.cai310.lottery.entity.lottery.zc.LczcPasscount;
import com.cai310.lottery.entity.lottery.zc.SczcScheme;
import com.cai310.lottery.entity.lottery.zc.SczcPasscount;
import com.cai310.lottery.service.lottery.impl.SchemeEntityManagerImpl;

/**
 * 四场足彩方案相关实体管理实现类.
 * 
 */
@Service("sczcSchemeEntityManagerImpl")
@Transactional
public class SczcSchemeEntityManagerImpl extends SchemeEntityManagerImpl<SczcScheme> {

	@Autowired
	private SczcSchemeDao schemeDao;
	
	@Autowired
	private SczcSchemeWonInfoDao sczcSchemeWonInfoDao;
	
	public SczcPasscount saveSczcSchemeWonInfo(SczcPasscount entity) {
		return sczcSchemeWonInfoDao.save(entity);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public SczcPasscount getSczcSchemeWonInfo(long id) {
		return sczcSchemeWonInfoDao.get(id);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void deleteSczcSchemeWonInfo(SczcPasscount entity ) {
		sczcSchemeWonInfoDao.delete(entity);
	}
	
	@Override
	protected SchemeDao<SczcScheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.SCZC;
	}
}
