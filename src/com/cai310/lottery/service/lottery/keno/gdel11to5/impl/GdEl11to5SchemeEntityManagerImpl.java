package com.cai310.lottery.service.lottery.keno.gdel11to5.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.gdel11to5.GdEl11to5SchemeDao;
import com.cai310.lottery.entity.lottery.keno.gdel11to5.GdEl11to5Scheme;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * 方案相关实体管理实现类.
 * 
 */
@Service("gdel11to5SchemeEntityManagerImpl")
@Transactional
public class GdEl11to5SchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<GdEl11to5Scheme> {

	@Autowired
	private GdEl11to5SchemeDao schemeDao;

	@Override
	protected SchemeDao<GdEl11to5Scheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.GDEL11TO5;
	}
}
