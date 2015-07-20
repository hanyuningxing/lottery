package com.cai310.lottery.service.lottery.keno.ssc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.ssc.SscSchemeDao;
import com.cai310.lottery.entity.lottery.keno.ssc.SscScheme;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * 方案相关实体管理实现类.
 * 
 */
@Service("sscSchemeEntityManagerImpl")
@Transactional
public class SscSchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<SscScheme> {

	@Autowired
	private SscSchemeDao schemeDao;

	@Override
	protected SchemeDao<SscScheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.SSC;
	}
}
