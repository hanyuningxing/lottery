package com.cai310.lottery.service.lottery.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.AfterFinishFlag;
import com.cai310.lottery.common.AfterSaleFlag;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.common.PeriodState;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SalesState;
import com.cai310.lottery.dao.lottery.PeriodDao;
import com.cai310.lottery.dao.lottery.PeriodSalesDao;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.PeriodSalesId;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.support.NumberSaleBean;
import com.cai310.orm.Page;
import com.cai310.orm.PropertyFilter;
import com.cai310.orm.hibernate.ExecuteCallBack;
import com.cai310.utils.DateUtil;

@Service("periodEntityManager")
@Transactional
public class PeriodEntityManagerImpl implements PeriodEntityManager {

	@Autowired
	protected PeriodDao periodDao;

	@Autowired
	protected PeriodSalesDao periodSalesDao;

	public void doBeginSales(PeriodSalesId salesId) {
		PeriodSales periodSale = periodSalesDao.get(salesId);
		if (!periodSale.canBeginSale()) {
			throw new ServiceException("当前不能执行开始销售！");
		}

		periodSale.setState(SalesState.ON_SALE);
		periodSale.setStartTime(new Date());
		periodSale = periodSalesDao.save(periodSale);

		Period period = periodDao.get(periodSale.getId().getPeriodId());
		if (!period.isStarted()) {// 只要单复式中有一个开始了,则默认整期开始销售了
			period.setCurrent(true);
			period.setState(PeriodState.Started);
			period.setStartTime(new Date());
			period = periodDao.save(period);
			////数字彩增加最新旗号JS写入系统
			if(period.getLotteryType().getCategory().equals(LotteryCategory.NUMBER)){
				PeriodSales compoundPeriodSale = periodSalesDao.get(new PeriodSalesId(period.getId(), SalesMode.COMPOUND));
				if(compoundPeriodSale.isOnSale()){
					NumberSaleBean numberSaleBean = new NumberSaleBean();
					numberSaleBean.setId(period.getId());
					numberSaleBean.setLotteryType(period.getLotteryType());
					numberSaleBean.setPeriodNumber(period.getPeriodNumber());
					numberSaleBean.setSelfEndInitTime(DateUtil.dateToStr(compoundPeriodSale.getSelfEndInitTime(),"yyyy-MM-dd HH:mm:ss"));
					String dir = "/html/js/data/" + period.getLotteryType().getKey() + "/";
					String fileName = "sale_period.js";
					Map<String, Object> contents = new HashMap<String, Object>();
					contents.put("numberSaleBean", numberSaleBean);
					String value = JSONObject.fromObject(contents).toString();
					com.cai310.utils.WriteHTMLUtil.writeHtm(dir, fileName, value, "UTF-8");
				}
			}
		}
	}

	public void doEndSales(PeriodSalesId periodSaleId) {
		PeriodSales periodSale = periodSalesDao.get(periodSaleId);
		if (!periodSale.canEndSale()) {
			throw new ServiceException("当前不能执行结束销售！");
		}

		// 标记销售截止
		periodSale.setState(SalesState.SALE_ENDED);
		periodSale = periodSalesDao.save(periodSale);

		// 加载另一个子销售对象
		Period period = periodDao.get(periodSale.getId().getPeriodId());
		SalesMode otherSaleMode;
		if (SalesMode.SINGLE == periodSale.getId().getSalesMode()) {
			otherSaleMode = SalesMode.COMPOUND;
		} else if (SalesMode.COMPOUND == periodSale.getId().getSalesMode()) {
			otherSaleMode = SalesMode.SINGLE;
		} else {
			throw new ServiceException("彩种号不正确！");
		}
		PeriodSales otherPeriodSale = periodSalesDao.get(new PeriodSalesId(period.getId(), otherSaleMode));

		// 当另一个子销售对象也结束了,才算本期真正结束
		if (otherPeriodSale.getState().getState() >= SalesState.SALE_ENDED.getState()) {
			period.setState(PeriodState.SaleEnded);
			period = periodDao.save(period);
		}
		
		
	}

	public void endComminPayment(PeriodSalesId periodSalesId) {
		PeriodSales issueSale = periodSalesDao.get(periodSalesId);
		if (!issueSale.canEndComminPayment()) {
			throw new ServiceException("当前不能结束【完成交易】。");
		}
		issueSale.setState(SalesState.PAYMENT_COMMITTED);
		issueSale = periodSalesDao.save(issueSale);

	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean exists(Lottery lotteryType, String periodNumber) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Period.class);
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("lotteryType", lotteryType));
		criteria.add(Restrictions.eq("periodNumber", periodNumber));
		List<Integer> list = periodDao.findByDetachedCriteria(criteria,true);
		if (list != null && !list.isEmpty()) {
			return list.get(0) > 0;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Period> findOldPeriods(final Lottery lotteryType, final int size, final boolean cacheable) {
		return (List<Period>) periodDao.execute(new ExecuteCallBack() {
			public Object execute(Session session) {
				Criteria criteria = session.createCriteria(Period.class);

				if (cacheable) {
					criteria.setCacheable(true);
					criteria.setCacheRegion(CacheConstant.H_PERIOD_QUERY_CACHE_REGION);
				}

				criteria.add(Restrictions.eq("lotteryType", lotteryType));
				criteria.add(Restrictions.eq("current", false));
				criteria.add(Restrictions.gt("state", PeriodState.Inited));

				criteria.addOrder(Order.desc("periodNumber"));

				if (size > 0)
					criteria.setMaxResults(size);
				return criteria.list();
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Period> findCurrentPeriods(Lottery lotteryType) {
		return findCurrentPeriods(lotteryType, true);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Period> findCurrentPeriods(final Lottery lotteryType, final boolean cacheable) {
		return (List<Period>) periodDao.execute(new ExecuteCallBack() {
			public Object execute(Session session) {
				Criteria criteria = session.createCriteria(Period.class);
				if (cacheable) {
					criteria.setCacheable(true);
					criteria.setCacheRegion(CacheConstant.H_CURRENT_PERIOD_QUERY_CACHE_REGION);
				}
				criteria.add(Restrictions.eq("lotteryType", lotteryType));
				criteria.add(Restrictions.eq("current", true));
				criteria.addOrder(Order.asc("endedTime"));
				criteria.addOrder(Order.asc("id"));
				return criteria.list();
			}
		});
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	public List<Period> findCurrentPeriods(final boolean cacheable) {
		return (List<Period>) periodDao.execute(new ExecuteCallBack() {
			public Object execute(Session session) {
				Criteria criteria = session.createCriteria(Period.class);
				if (cacheable) {
					criteria.setCacheable(true);
					criteria.setCacheRegion(CacheConstant.H_CURRENT_PERIOD_QUERY_CACHE_REGION);
				}
				criteria.add(Restrictions.eq("current", true));
				criteria.addOrder(Order.asc("endedTime"));
				criteria.addOrder(Order.asc("id"));
				return criteria.list();
			}
		});
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<String> findOnSalePeriodNumber(final Lottery lotteryType) {
		return (List<String>) periodDao.execute(new ExecuteCallBack() {
			public Object execute(Session session) {
				Criteria criteria = session.createCriteria(Period.class);
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_PERIOD_QUERY_CACHE_REGION);
				criteria.setProjection(Projections.property("periodNumber"));
				criteria.add(Restrictions.eq("lotteryType", lotteryType));
				criteria.add(Restrictions.eq("current", true));
				return criteria.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Period> getDrawPeriodList(final Lottery lotteryType, final int count) {
		return (List<Period>) periodDao.execute(new ExecuteCallBack() {
			public Object execute(Session session) {
				Criteria criteria = session.createCriteria(Period.class);
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_PERIOD_QUERY_CACHE_REGION);
				criteria.add(Restrictions.eq("drawed", true));
				criteria.add(Restrictions.eq("state", PeriodState.Finished));
				criteria.add(Restrictions.eq("lotteryType", lotteryType));
				criteria.addOrder(Order.desc("id"));
				criteria.setMaxResults(count);
				return criteria.list();
			}
		});
	}

	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Period getPeriod(Long id) {
		return periodDao.get(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public PeriodSales getPeriodSales(PeriodSalesId id) {
		return periodSalesDao.get(id);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Integer getPrevPeriodId(Lottery lotteryType) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Period.class, "m");
		criteria.setProjection(Projections.projectionList().add(Projections.property("id"), "periodId"));
		// scriteria.add(Property.forName("m.started").eq(true));
		criteria.add(Restrictions.eq("lotteryType", lotteryType));
		criteria.addOrder(Order.desc("m.id"));
		criteria.setResultTransformer(DetachedCriteria.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = periodDao.findByDetachedCriteria(criteria, 0, 1);
		if (list != null && !list.isEmpty()) {
			return (Integer) list.get(0).get("periodId");
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public void hideCurrentPeriod(Long periodId) {
		Period period = periodDao.get(periodId);
		if (!period.isCurrent()) {
			throw new ServiceException("销售期[#" + period.getId() + "]已经是非当前期，不能再设置为非当前期");
		}
		if (period.isOnSale()) {
			throw new ServiceException("销售期[#" + period.getId() + "]在售中，不能设为非当前期");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Period.class);
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("current", true));
		List<Integer> list = periodDao.findByDetachedCriteria(criteria);
		Integer count = null;
		if (list != null && !list.isEmpty()) {
			count = list.get(0);
		}
		if (count == null || count.intValue() <= 1) {
			throw new ServiceException("至少必须有一个当前期，销售期[#" + period.getId() + "]不能设为非当前期");
		}

		period.setCurrent(false);
		period = periodDao.save(period);
	}

	@SuppressWarnings("unchecked")
	public void showPeriodCurrent(Long periodId) {
		Period period = periodDao.get(periodId);
		if (period.isCurrent()) {
			throw new ServiceException("销售期[#" + period.getId() + "]已经是当前期，不能再设置为当前期");
		}
		period.setCurrent(true);
		period = periodDao.save(period);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Period loadPeriod(Lottery lotteryType, String periodNumber) {
		List<Period> list = periodDao.find(Restrictions.eq("lotteryType", lotteryType),
				Restrictions.eq("periodNumber", periodNumber));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public void removePeriod(Long periodId) {
		periodDao.delete(periodId);
	}

	public Period savePeriod(Period period) {
		return periodDao.save(period);
	}

	public PeriodSales savePeriodSales(PeriodSales periodSales) {
		return periodSalesDao.save(periodSales);
	}

	public void savePeriodSales(PeriodSales singlePeriodSales, PeriodSales polyPeriodSales) {
		periodSalesDao.save(singlePeriodSales);
		periodSalesDao.save(polyPeriodSales);

	}

	public Period updateAfterFinishFlags(Long periodId, AfterFinishFlag afterFinishFlag) {
		Period period = periodDao.get(periodId);
		period.addAfterFinishFlags(afterFinishFlag.getFlagValue());
		period = periodDao.save(period);
		return period;
	}

	public Period updateAfterSaleFlags(Long periodId, AfterSaleFlag afterSaleFlag) {
		Period period = periodDao.get(periodId);
		period.addAfterSaleFlags(afterSaleFlag.getFlagValue());
		period = periodDao.save(period);
		return period;
	}

	public Period updateAfterSaleFlags(Long periodId, AfterSaleFlag[] afterSaleFlags) {
		Period period = periodDao.get(periodId);
		int afterSaleFlag = 0;
		for (int i = 0; i < afterSaleFlags.length; i++) {
			afterSaleFlag |= afterSaleFlags[i].getFlagValue();
		}
		period.addAfterSaleFlags(afterSaleFlag);
		period = periodDao.save(period);
		return period;
	}

	public Period updatePeriod(Map<String, Object> updateMap, long periodId) {
		return periodDao.update(updateMap, periodId);
	}

	public PeriodSales updatePeriodSales(Map<String, Object> updateMap, PeriodSalesId periodSalesId) {
		return periodSalesDao.update(updateMap, periodSalesId);
	}

	public PeriodSales updatePeriodSalesState(PeriodSalesId periodSalesId, SalesState salesState) {
		PeriodSales issueSale = periodSalesDao.get(periodSalesId);
		issueSale.setState(salesState);
		issueSale = periodSalesDao.save(issueSale);
		return issueSale;
	}

	public Period createNewPeriod(Period period, PeriodSales... sales) {
		if (exists(period.getPeriodNumber().trim(), period.getLotteryType()))
			throw new ServiceException("已经存在期号为[" + period.getPeriodNumber().trim() + "]的销售期！");

		if (period.getPrevPreriodId() == null || period.getPrevPreriodId() <= 0) {
			period.setPrevPreriodId(getLastPeriodId(period.getLotteryType()));// 设置上一期ID
		}
		period.setPeriodNumber(period.getPeriodNumber().trim());
		period = periodDao.save(period);
		if (period.getPrevPreriodId() != null) {
			// 更新上一期的下一期ID字段
			Period prevPeriod = periodDao.get(period.getPrevPreriodId());
			prevPeriod.setNextPreriodId(period.getId());
			periodDao.save(prevPeriod);
		}
		for (PeriodSales sale : sales) {
			sale.getId().setPeriodId(period.getId());
			periodSalesDao.save(sale);
		}
		return period;
	}

	/**
	 * 验证销售期是否已经存在
	 * 
	 * @param periodNumber
	 * @param lotteryType
	 * @return
	 */
	@Transactional(readOnly = true)
	protected boolean exists(String periodNumber, Lottery lotteryType) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Period.class);
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("periodNumber", periodNumber));
		criteria.add(Restrictions.eq("lotteryType", lotteryType));
		List<Integer> list = periodDao.findByDetachedCriteria(criteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0) > 0;
		}
		return false;
	}

	/**
	 * 查找最后一期ID
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	protected Long getLastPeriodId(Lottery lotteryType) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Period.class);
		criteria.setProjection(Projections.property("id"));
		criteria.add(Restrictions.eq("lotteryType", lotteryType));
		criteria.addOrder(Order.desc("id"));
		List<Long> list = periodDao.findByDetachedCriteria(criteria, 0, 1);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public Page<Period> list(Page<Period> page, List<PropertyFilter> propertyFilterList) {
		return periodDao.findPage(page, propertyFilterList);
	}

	public List<PeriodSales> findPeriodSales(long periodId) {
		return periodSalesDao.findBy("id.periodId", periodId);
	}

	@Override
	public Period getNextPeriod(Lottery lotteryType, long periodId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Period.class);
		criteria.add(Restrictions.eq("lotteryType", lotteryType));
		criteria.add(Restrictions.gt("id", periodId));
		//TODO zhuhui motify 2011-06-30 导入历史数据导致新彩期的id 小于旧数据  criteria.addOrder(Order.asc("id"));
		criteria.addOrder(Order.asc("id"));
		List<Period> list = periodDao.findByDetachedCriteria(criteria, 0, 1);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
}
