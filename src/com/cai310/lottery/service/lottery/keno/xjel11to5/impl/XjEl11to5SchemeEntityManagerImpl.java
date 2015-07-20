package com.cai310.lottery.service.lottery.keno.xjel11to5.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.xjel11to5.XjEl11to5SchemeDao;
import com.cai310.lottery.entity.lottery.keno.xjel11to5.XjEl11to5Scheme;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * 方案相关实体管理实现类.
 * 
 */
@Service("xjel11to5SchemeEntityManagerImpl")
@Transactional
public class XjEl11to5SchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<XjEl11to5Scheme> {

	@Autowired
	private XjEl11to5SchemeDao schemeDao;

	@Override
	protected SchemeDao<XjEl11to5Scheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.XJEL11TO5;
	}
}
