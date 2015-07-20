package com.cai310.lottery.service.lottery.keno.ssl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.ssl.SslSchemeDao;
import com.cai310.lottery.entity.lottery.keno.ssl.SslScheme;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * 方案相关实体管理实现类.
 * 
 */
@Service("sslSchemeEntityManagerImpl")
@Transactional
public class SslSchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<SslScheme> {

	@Autowired
	private SslSchemeDao schemeDao;

	@Override
	protected SchemeDao<SslScheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.SSL;
	}
}
