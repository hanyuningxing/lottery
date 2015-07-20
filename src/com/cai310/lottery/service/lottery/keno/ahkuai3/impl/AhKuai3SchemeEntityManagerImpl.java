package com.cai310.lottery.service.lottery.keno.ahkuai3.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.keno.ahkuai3.AhKuai3SchemeDao;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3Scheme;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;

/**
 * 方案相关实体管理实现类.
 * 
 */
@SuppressWarnings("unchecked")
@Service("ahkuai3SchemeEntityManagerImpl")
@Transactional
public class AhKuai3SchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<AhKuai3Scheme> {
	
	@Autowired
	private AhKuai3SchemeDao schemeDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.AHKUAI3;
	}

	@Override
	protected SchemeDao<AhKuai3Scheme> getSchemeDao() {
		
		return schemeDao;
	}

}
