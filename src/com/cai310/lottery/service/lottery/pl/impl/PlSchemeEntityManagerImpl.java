package com.cai310.lottery.service.lottery.pl.impl;

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
import com.cai310.lottery.dao.lottery.pl.PlPasscountDao;
import com.cai310.lottery.dao.lottery.pl.PlSchemeDao;
import com.cai310.lottery.dao.lottery.pl.PlSchemeWonInfoDao;
import com.cai310.lottery.entity.lottery.pl.PlPasscount;
import com.cai310.lottery.entity.lottery.pl.PlScheme;
import com.cai310.lottery.entity.lottery.pl.PlSchemeWonInfo;
import com.cai310.lottery.entity.lottery.zc.SfzcScheme;
import com.cai310.lottery.service.lottery.impl.NumberSchemeEntityManagerImpl;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.orm.XDetachedCriteria;

/**
 * 方案相关实体管理实现类.
 * 
 */
@Service("plSchemeEntityManagerImpl")
@Transactional
public class PlSchemeEntityManagerImpl extends NumberSchemeEntityManagerImpl<PlScheme> {

	@Autowired
	private PlSchemeDao schemeDao;

	@Autowired
	private PlPasscountDao passcountPlDao;

	@Autowired
	private PlSchemeWonInfoDao plSchemeWonInfoDao;

	@Override
	protected SchemeDao<PlScheme> getSchemeDao() {
		return schemeDao;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Lottery getLottery() {
		return Lottery.PL;
	}

	public PlSchemeWonInfo savePlSchemeWonInfo(PlSchemeWonInfo entity) {
		return plSchemeWonInfoDao.save(entity);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public PlSchemeWonInfo getSchemeBet(long id) {
		return plSchemeWonInfoDao.get(id);
	}

	public PlPasscount savePlPasscount(PlPasscount entity) {
		return passcountPlDao.save(entity);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public PlPasscount getPlPasscount(long id) {
		return passcountPlDao.get(id);
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void deletePlPasscount(PlPasscount entity) {
		passcountPlDao.delete(entity);
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<SfzcScheme> getTopScheme(Integer star, Integer count, PlPlayType playType) {
		XDetachedCriteria criteria = new XDetachedCriteria(SfzcScheme.class, "m");
		criteria.add(Restrictions.eq("m.state", SchemeState.UNFULL));
		if (null != playType) {
			criteria.add(Restrictions.eq("m.playType", playType));
		} else {
			criteria.add(Restrictions.not(Restrictions.eq("m.playType", PlPlayType.P5Direct)));
		}
		criteria.add(Restrictions.eq("m.playType", playType));
		criteria.addOrder(Order.desc("m.orderPriority"));
		criteria.addOrder(Order.desc("m.schemeCost"));
		criteria.addOrder(Order.desc("m.progressRate"));
		criteria.addOrder(Order.desc("m.id"));
		return getSchemeDao().findByDetachedCriteria(criteria, star, count);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<SfzcScheme> getHotScheme(Integer star, Integer count, PlPlayType playType) {
		XDetachedCriteria criteria = new XDetachedCriteria(SfzcScheme.class, "m");
		criteria.add(Restrictions.eq("m.state", SchemeState.UNFULL));
		if (null != playType) {
			criteria.add(Restrictions.eq("m.playType", playType));
		} else {
			criteria.add(Restrictions.not(Restrictions.eq("m.playType", PlPlayType.P5Direct)));
		}
		criteria.add(Restrictions.eq("m.orderPriority", Constant.ORDER_PRIORITY_TOP));
		criteria.addOrder(Order.desc("m.schemeCost"));
		criteria.addOrder(Order.desc("m.progressRate"));
		criteria.addOrder(Order.desc("m.id"));
		return getSchemeDao().findByDetachedCriteria(criteria, star, count);
	}
}
