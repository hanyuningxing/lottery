package com.cai310.lottery.service.lottery.keno.sdel11to5.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.sdel11to5.SdEl11to5SchemeDao;
import com.cai310.lottery.entity.lottery.keno.sdel11to5.SdEl11to5Scheme;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * 方案相关实体管理实现类.
 * 
 */
@Service("sdel11to5SchemeEntityManagerImpl")
@Transactional
public class SdEl11to5SchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<SdEl11to5Scheme> {

	@Autowired
	private SdEl11to5SchemeDao schemeDao;

	@Override
	protected SchemeDao<SdEl11to5Scheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.SDEL11TO5;
	}
}
