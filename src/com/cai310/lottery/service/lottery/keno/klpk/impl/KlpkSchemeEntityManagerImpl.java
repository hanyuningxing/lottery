package com.cai310.lottery.service.lottery.keno.klpk.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.klpk.KlpkSchemeDao;
import com.cai310.lottery.entity.lottery.keno.klpk.KlpkScheme;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * 方案相关实体管理实现类.
 * 
 */
@Service("klpkSchemeEntityManagerImpl")
@Transactional
public class KlpkSchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<KlpkScheme> {

	@Autowired
	private KlpkSchemeDao schemeDao;

	@Override
	protected SchemeDao<KlpkScheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.KLPK;
	}
}
