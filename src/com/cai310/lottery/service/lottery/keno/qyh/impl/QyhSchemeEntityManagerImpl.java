package com.cai310.lottery.service.lottery.keno.qyh.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.qyh.QyhSchemeDao;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhScheme;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * 方案相关实体管理实现类.
 * 
 */
@Service("qyhSchemeEntityManagerImpl")
@Transactional
public class QyhSchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<QyhScheme> {

	@Autowired
	private QyhSchemeDao schemeDao;

	@Override
	protected SchemeDao<QyhScheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.QYH;
	}
}
