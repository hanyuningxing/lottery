package com.cai310.lottery.service.lottery.zc.impl;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.zc.SfzcSchemeDao;
import com.cai310.lottery.dao.lottery.zc.SfzcSchemeWonInfoDao;
import com.cai310.lottery.entity.lottery.zc.SczcPasscount;
import com.cai310.lottery.entity.lottery.zc.SfzcScheme;
import com.cai310.lottery.entity.lottery.zc.SfzcPasscount;
import com.cai310.lottery.service.lottery.impl.SchemeEntityManagerImpl;
import com.cai310.lottery.support.zc.PlayType;
import com.cai310.orm.XDetachedCriteria;

/**
 * 胜负足彩方案相关实体管理实现类.
 * 
 */
@Service("sfzcSchemeEntityManagerImpl")
@Transactional
public class SfzcSchemeEntityManagerImpl extends SchemeEntityManagerImpl<SfzcScheme> {

	@Autowired
	private SfzcSchemeDao schemeDao;
	
	@Autowired
	private SfzcSchemeWonInfoDao sfzcSchemeWonInfoDao;

	@Override
	protected SchemeDao<SfzcScheme> getSchemeDao() {
		return schemeDao;
	}
	
	public SfzcPasscount saveSfzcSchemeWonInfo(SfzcPasscount entity) {
		return sfzcSchemeWonInfoDao.save(entity);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public SfzcPasscount getSfzcSchemeWonInfo(long id) {
		return sfzcSchemeWonInfoDao.get(id);
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void deleteSfzcSchemeWonInfo(SfzcPasscount entity ) {
		sfzcSchemeWonInfoDao.delete(entity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.SFZC;
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<SfzcScheme> getTopScheme(Integer star,Integer count,PlayType playType) {
		XDetachedCriteria criteria = new XDetachedCriteria(SfzcScheme.class, "m");
		criteria.add(Restrictions.eq("m.state",SchemeState.UNFULL));
		criteria.add(Restrictions.eq("m.playType",playType));
		criteria.addOrder(Order.desc("m.orderPriority"));
		criteria.addOrder(Order.desc("m.schemeCost"));
		criteria.addOrder(Order.desc("m.progressRate"));
		criteria.addOrder(Order.desc("m.id"));
		return getSchemeDao().findByDetachedCriteria(criteria, star, count);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<SfzcScheme> getHotScheme(Integer star,Integer count,PlayType playType) {
		XDetachedCriteria criteria = new XDetachedCriteria(SfzcScheme.class, "m");
		criteria.add(Restrictions.eq("m.state",SchemeState.UNFULL));
		criteria.add(Restrictions.eq("m.orderPriority", Constant.ORDER_PRIORITY_TOP));
		criteria.add(Restrictions.eq("m.playType",playType));
		criteria.addOrder(Order.desc("m.schemeCost"));
		criteria.addOrder(Order.desc("m.progressRate"));
		criteria.addOrder(Order.desc("m.id"));
		return getSchemeDao().findByDetachedCriteria(criteria, star, count);
	}
}
