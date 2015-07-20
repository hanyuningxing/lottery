package com.cai310.lottery.service.lottery.keno.klsf.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.klsf.KlsfSchemeDao;
import com.cai310.lottery.entity.lottery.keno.klsf.KlsfScheme;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * 方案相关实体管理实现类.
 * 
 */
@Service("klsfSchemeEntityManagerImpl")
@Transactional
public class KlsfSchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<KlsfScheme> {

	@Autowired
	private KlsfSchemeDao schemeDao;

	@Override
	protected SchemeDao<KlsfScheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.KLSF;
	}
}
