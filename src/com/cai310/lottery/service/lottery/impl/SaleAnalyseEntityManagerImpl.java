package com.cai310.lottery.service.lottery.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.FundMode;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.PeriodState;
import com.cai310.lottery.common.SaleAnalyseState;
import com.cai310.lottery.dao.lottery.SaleAnalyseDao;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.SaleAnalyse;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.user.FundDetail;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SaleAnalyseEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.orm.hibernate.ExecuteCallBack;
import com.google.common.collect.Maps;

/**
 * 
 */
@Service("saleAnalyseEntityManagerImpl")
@Transactional
public class SaleAnalyseEntityManagerImpl implements SaleAnalyseEntityManager {

	@SuppressWarnings("rawtypes")
	private Map<Lottery, SchemeEntityManager> schemeEntityManagerMap = Maps.newHashMap();
	@SuppressWarnings("rawtypes")
	private SchemeEntityManager getSchemeEntityManager(Lottery lotteryType) {
		return schemeEntityManagerMap.get(lotteryType);
	}
	@SuppressWarnings("rawtypes")
	@Autowired
	public void setSchemeEntityManagerList(List<SchemeEntityManager> schemeEntityManagerList) {
		for (SchemeEntityManager manager : schemeEntityManagerList) {
			schemeEntityManagerMap.put(manager.getLottery(), manager);
		}
	}
	@Autowired
	protected SaleAnalyseDao saleAnalyseDao;

	public void updateSaleAnalyse(List<SaleAnalyse> updateSaleAnalyse,Set<Long> updateSchemeSet,Lottery lottery){
		if(null==updateSchemeSet)throw new ServiceException("更新统计方案号为空.");
		if(null==updateSaleAnalyse)throw new ServiceException("更新统计为空.");
		Scheme scheme;
		for (Long schemeId : updateSchemeSet) {
			scheme = this.getSchemeEntityManager(lottery).getScheme(Long.valueOf(schemeId));
			scheme.setSaleAnalyseState(SaleAnalyseState.UPDATED);
			this.getSchemeEntityManager(lottery).saveScheme(scheme);
		}
		for (SaleAnalyse saleAnalyse : updateSaleAnalyse) {
			 saleAnalyseDao.save(saleAnalyse);
		}
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<SaleAnalyse> getTop10SaleAnalyse() {
		DetachedCriteria criteria = DetachedCriteria.forClass(SaleAnalyse.class, "m");
		ProjectionList prop = Projections.projectionList();
		prop.add(Projections.groupProperty("m.userId"), "userId");
		prop.add(Projections.groupProperty("m.userName"), "userName");
		prop.add(Projections.sum("m.subscriptionSuccessWonPrize"), "subscriptionSuccessWonPrize");
		criteria.setProjection(prop);
		criteria.addOrder(Order.desc("subscriptionSuccessWonPrize"));
		criteria.setResultTransformer(Transformers.aliasToBean(SaleAnalyse.class));
		return saleAnalyseDao.findByDetachedCriteria(criteria,0,10);
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<SaleAnalyse> getTop10SaleAnalyse(Lottery lottery,Integer monthNum) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SaleAnalyse.class, "m");
		criteria.add(Restrictions.ge("m.monthNum", monthNum));
		criteria.add(Restrictions.le("m.lottery", lottery));
		ProjectionList prop = Projections.projectionList();
		prop.add(Projections.groupProperty("m.userId"), "userId");
		prop.add(Projections.groupProperty("m.userName"), "userName");
		prop.add(Projections.sum("m.schemeSuccessWonPrize"), "schemeSuccessWonPrize");
		prop.add(Projections.property("m.userId"), "userId");
		prop.add(Projections.property("m.userName"), "userName");
		criteria.setProjection(prop);
		criteria.setResultTransformer(Transformers.aliasToBean(SaleAnalyse.class));
		return saleAnalyseDao.findByDetachedCriteria(criteria);
	}
	@Override
	public SaleAnalyse getJcSaleAnalyse(Long userId,Lottery lottery,Integer playType,Integer week) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SaleAnalyse.class);
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("lottery",lottery));
		criteria.add(Restrictions.eq("playType", playType));
		criteria.add(Restrictions.eq("weekNum", week));
		List<SaleAnalyse> list=saleAnalyseDao.findByDetachedCriteria(criteria);
		if(null!=list&&!list.isEmpty()){
			///没有该记录
			return list.get(0);
		}
		return null;
	}
	@Override
	public SaleAnalyse saveJcSaleAnalyse(SaleAnalyse saleAnalyse) {
		return saleAnalyseDao.save(saleAnalyse);
	}
}
