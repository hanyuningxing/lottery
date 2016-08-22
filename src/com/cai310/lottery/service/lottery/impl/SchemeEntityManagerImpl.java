package com.cai310.lottery.service.lottery.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.Constant;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.common.BaodiState;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.common.SaleAnalyseState;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.common.SchemePrintState;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.common.ShareType;
import com.cai310.lottery.common.SubscriptionState;
import com.cai310.lottery.common.SubscriptionWay;
import com.cai310.lottery.common.WinningUpdateStatus;
import com.cai310.lottery.dao.lottery.BaodiDao;
import com.cai310.lottery.dao.lottery.SchemeDao;
import com.cai310.lottery.dao.lottery.SubscriptionDao;
import com.cai310.lottery.dao.ticket.TicketThenDao;
import com.cai310.lottery.dto.lottery.SchemeQueryDTO;
import com.cai310.lottery.entity.lottery.Baodi;
import com.cai310.lottery.entity.lottery.MyScheme;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.Subscription;
import com.cai310.lottery.entity.ticket.TicketThen;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.support.pl.PlPlayType;
import com.cai310.lottery.ticket.common.TicketConstant;
import com.cai310.lottery.utils.DozerMapperSingleton;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;
import com.cai310.orm.hibernate.ExecuteCallBack;
import com.cai310.utils.DateUtil;
import com.cai310.utils.ReflectionUtils;
import com.google.common.collect.Lists;

/**
 * 方案相关实体的管理的实现基类.
 * 
 */
@SuppressWarnings("unchecked")
@Transactional
public abstract class SchemeEntityManagerImpl<T extends Scheme> implements SchemeEntityManager<T> {
	protected transient Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @see com.cai310.lottery.dao.lottery.BaodiDao
	 */
	@Autowired
	protected BaodiDao baodiDao;

	protected abstract SchemeDao<T> getSchemeDao();

	/**
	 * @see com.cai310.lottery.dao.lottery.SubscriptionDao
	 */
	@Autowired
	protected SubscriptionDao subscriptionDao;

	protected final Class<T> schemeClass;

	public SchemeEntityManagerImpl() {
		this.schemeClass = ReflectionUtils.getSuperClassGenricType(this.getClass());
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<T> countMyScheme(final Long userId, final SchemeQueryDTO dto, final Pagination pagination) {
		if (userId == null)
			throw new ServiceException("用户ID不能为空.");
		return (List<T>) getSchemeDao().execute(new ExecuteCallBack() {
			public Object execute(Session session) {
				Criteria criteria = session.createCriteria(schemeClass, "m");
				criteria.add(Restrictions.eq("m.sponsorId", userId));
				if (dto != null) {
					if (dto.getState() != null)
						criteria.add(Restrictions.eq("m.state", dto.getState()));
					if(null!=dto.getRestrictions()&&!dto.getRestrictions().isEmpty()){
						for (Criterion criterion : dto.getRestrictions()) {
							criteria.add(criterion);
						}
					}

					if (dto.getStartTime() != null)
						criteria.add(Restrictions.ge("m.createTime", dto.getStartTime()));
					
					if (dto.getEndTime()!= null)
						criteria.add(Restrictions.le("m.createTime", dto.getEndTime()));
					
					
					if (dto.getStartTime_prize() != null)
						criteria.add(Restrictions.ge("m.prizeSendTime", dto.getStartTime_prize()));
					
					if (dto.getEndTime_prize()!= null)
						criteria.add(Restrictions.le("m.prizeSendTime", dto.getEndTime_prize()));

					if (StringUtils.isNotBlank(dto.getPeriodNumber()))
						criteria.add(Restrictions.eq("m.periodNumber", dto.getPeriodNumber()));
					
					if (dto.isWon()) {
						criteria.add(Restrictions.eq("m.state", SchemeState.SUCCESS));
						criteria.add(Restrictions.eq("m.won", dto.isWon()));
					}
					if (dto.getPlayType() != null) {
						switch (getLottery()) {
						case PL:
							if (String.valueOf(0).equals(dto.getPlayType())) {
								criteria.add(Restrictions.ne("m.playType", PlPlayType.P5Direct));
							} else if (String.valueOf(1).equals(dto.getPlayType())) {
								criteria.add(Restrictions.eq("m.playType", PlPlayType.P5Direct));
							}
							break;
						case SFZC:
							com.cai310.lottery.support.zc.PlayType zcPlayType = com.cai310.lottery.support.zc.PlayType
									.valueOfName(dto.getPlayType());
							criteria.add(Restrictions.eq("m.playType", zcPlayType));
							break;
						case DCZC:
							com.cai310.lottery.support.dczc.PlayType dczcPlayType = com.cai310.lottery.support.dczc.PlayType
									.valueOfName(dto.getPlayType());
							criteria.add(Restrictions.eq("m.playType", dczcPlayType));
							break;
						}
					}

				}
				ProjectionList prop = Projections.projectionList();
				prop.add(Projections.groupProperty("m.state"), "state");
				prop.add(Projections.groupProperty("m.schemePrintState"), "schemePrintState");
				prop.add(Projections.count("m.version"), "version");
				prop.add(Projections.sum("m.units"), "units");
				prop.add(Projections.sum("m.schemeCost"), "schemeCost");
				prop.add(Projections.sum("m.prize"), "prize");
				prop.add(Projections.sum("m.prizeAfterTax"), "prizeAfterTax");
				prop.add(Projections.property("m.state"), "state");
				prop.add(Projections.property("m.schemePrintState"), "schemePrintState");
				criteria.setProjection(prop);
				criteria.setResultTransformer(Transformers.aliasToBean(schemeClass));
				return criteria.list();
			    
			}
		});
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public T getScheme(Long id) {
		return getSchemeDao().get(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<T> getTopScheme(Integer star, Integer count) {
		XDetachedCriteria criteria = new XDetachedCriteria(schemeClass, "m");
		criteria.add(Restrictions.eq("m.state", SchemeState.UNFULL));
		criteria.addOrder(Order.desc("m.orderPriority"));
		criteria.addOrder(Order.desc("m.schemeCost"));
		criteria.addOrder(Order.desc("m.progressRate"));
		criteria.addOrder(Order.desc("m.id"));
		return getSchemeDao().findByDetachedCriteria(criteria, star, count);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<T> getHotScheme(Integer star, Integer count) {
		XDetachedCriteria criteria = new XDetachedCriteria(schemeClass, "m");
		criteria.add(Restrictions.eq("m.state", SchemeState.UNFULL));
		criteria.add(Restrictions.eq("m.orderPriority", Constant.ORDER_PRIORITY_TOP));
		criteria.addOrder(Order.desc("m.schemeCost"));
		criteria.addOrder(Order.desc("m.progressRate"));
		criteria.addOrder(Order.desc("m.id"));
		return getSchemeDao().findByDetachedCriteria(criteria, star, count);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public T getSchemeBy(String schemeNumber) {
		Long schemeId = getLottery().getSchemeId(schemeNumber);
		if (schemeId == null)
			throw new ServiceException("方案号不正确.");
		return getScheme(schemeId);
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public T getTicketSchemeBy(final String orderId,final Long userId){
		return (T) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("orderId", orderId));
				criteria.add(Restrictions.eq("sponsorId", userId));
				return (T) criteria.uniqueResult();
			}
		});
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<T> getTicketListSchemeBy(final List<String> orderIds,final Long userId){
		return (List<T>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.in("orderId", orderIds));
				criteria.add(Restrictions.eq("sponsorId", userId));
				return criteria.list();
			}
		});
	}
	public T saveScheme(T scheme) {
		return getSchemeDao().save(scheme);
	}

	public Baodi saveBaodi(Baodi baodi) {
		return baodiDao.save(baodi);
	}

	public Subscription saveSubscription(Subscription subscription) {
		return subscriptionDao.save(subscription);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Subscription getSubscription(Long id) {
		return subscriptionDao.get(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Baodi getBaodi(Long id) {
		return baodiDao.get(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findSchemeIdOfCanDelivePrize(final long periodId) {
		return (List<Long>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.add(Restrictions.eq("winningUpdateStatus", WinningUpdateStatus.PRICE_UPDATED));// 已更新奖金
				criteria.add(Restrictions.eq("won", true));// 方案中奖
				criteria.add(Restrictions.eq("prizeSended", false));// 未派奖
				criteria.add(Restrictions.eq("state", SchemeState.SUCCESS));
				return criteria.list();
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findSchemeIdOfCanDeliveWinRecord(final long periodId) {
		return (List<Long>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.add(Restrictions.eq("winningUpdateStatus", WinningUpdateStatus.PRICE_UPDATED));// 已更新奖金
				criteria.add(Restrictions.eq("won", true));// 方案中奖
				criteria.add(Restrictions.eq("prizeSended", true));// 已派奖
				criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
				return criteria.list();
			}
		});
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findSchemeIdOfCanUpdatePrize(final long periodId) {
		return (List<Long>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.add(Restrictions.eq("winningUpdateStatus", WinningUpdateStatus.WINNING_UPDATED));
				criteria.add(Restrictions.eq("won", true));// 中奖方案才需要更新奖金
				criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
				return criteria.list();
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findSchemeIdOfCanUpdateWon(final long periodId) {
		return (List<Long>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.add(Restrictions.eq("uploaded", true));
				criteria.add(Restrictions.eq("winningUpdateStatus", WinningUpdateStatus.NONE));// 未更新中奖
				criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
				return criteria.list();
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findSchemeIdOfCanPasscount(final long periodId) {
		return (List<Long>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.add(Restrictions.eq("uploaded", true));
				criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
				return criteria.list();
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findSchemeIdOfHasBaodiAndUnReserved(final long periodId, final SalesMode salesMode) {
		return (List<Long>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("periodId", periodId));
				if (salesMode != null) {
					criteria.add(Restrictions.eq("mode", salesMode));
				}
				criteria.add(Restrictions.eq("reserved", false));// 没被保留
				criteria.add(Restrictions.or(
						Restrictions.gt("baodiCost", BigDecimal.ZERO),
						Restrictions.and(Restrictions.eq("sendToPrint", true),
								Restrictions.neProperty("subscribedCost", "schemeCost"))));
				criteria.add(Restrictions.or(Restrictions.eq("state", SchemeState.UNFULL),
						Restrictions.eq("state", SchemeState.FULL))); // 加入或满员状态
				return criteria.list();
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findSchemeIdOfUnCommitAndUnReserved(final long periodId, final SalesMode salesMode) {
		return (List<Long>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("periodId", periodId));

				if (salesMode != null)
					criteria.add(Restrictions.eq("mode", salesMode));

				criteria.add(Restrictions.eq("reserved", false));// 没被保留
				criteria.add(Restrictions.or(Restrictions.eq("state", SchemeState.UNFULL),
						Restrictions.eq("state", SchemeState.FULL)));

				return criteria.list();
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> statUnCommit(final long periodId, final SalesMode salesMode) {
		return (List<Map<String, Object>>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {

				criteria.setProjection(Projections.projectionList().add(Projections.rowCount(), "count")
						.add(Projections.groupProperty("reserved"), "reserved"));
				criteria.add(Restrictions.eq("periodId", periodId));
				if (salesMode != null) {
					criteria.add(Restrictions.eq("mode", salesMode));
				}
				criteria.add(Restrictions.or(Restrictions.eq("state", SchemeState.UNFULL),
						Restrictions.eq("state", SchemeState.FULL)));
				criteria.setResultTransformer(DetachedCriteria.ALIAS_TO_ENTITY_MAP);
				return criteria.list();
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Integer statUnDelivePrize(final long periodId) {
		return (Integer) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.rowCount());
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.add(Restrictions.eq("winningUpdateStatus", WinningUpdateStatus.PRICE_UPDATED));// 已更新奖金
				criteria.add(Restrictions.eq("won", true));// 方案中奖
				criteria.add(Restrictions.eq("prizeSended", false));// 未派奖
				criteria.add(Restrictions.eq("state", SchemeState.SUCCESS));
				List<Integer> list = criteria.list();
				if (list != null && !list.isEmpty())
					return list.get(0);

				return null;
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Map<String, Object>> statUnRunBaodi(final long periodId, final SalesMode salesMode) {
		return (List<Map<String, Object>>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.projectionList().add(Projections.rowCount(), "count")
						.add(Projections.groupProperty("reserved"), "reserved"));
				criteria.add(Restrictions.eq("periodId", periodId));
				if (salesMode != null) {
					criteria.add(Restrictions.eq("mode", salesMode));
				}
				criteria.add(Restrictions.gt("baodiCost", BigDecimal.ZERO));
				// criteria.add(Restrictions.or(Restrictions.gt("baodiCost",
				// BigDecimal.ZERO), Restrictions.and(Restrictions.eq(
				// "printed", true), Restrictions.gtProperty("schemeCost",
				// "subscribedCost"))));//没有剩余份数，改为金额比较
				criteria.add(Restrictions.or(Restrictions.eq("state", SchemeState.UNFULL),
						Restrictions.eq("state", SchemeState.FULL)));
				criteria.setResultTransformer(DetachedCriteria.ALIAS_TO_ENTITY_MAP);
				return criteria.list();
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Integer statUnUpdatePrize(final long periodId) {
		return (Integer) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.rowCount());
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.add(Restrictions.eq("winningUpdateStatus", WinningUpdateStatus.WINNING_UPDATED));// 已更新中奖
				criteria.add(Restrictions.eq("won", true));// 中奖方案才需要更新奖金
				criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
				List<Integer> list = criteria.list();
				if (list != null && !list.isEmpty())
					return list.get(0);

				return null;
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Integer statUnUpdateWon(final long periodId) {
		return (Integer) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.rowCount());
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.add(Restrictions.eq("uploaded", true));
				criteria.add(Restrictions.eq("winningUpdateStatus", WinningUpdateStatus.NONE));// 已更新中奖
				criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
				List<Integer> list = criteria.list();
				if (list != null && !list.isEmpty())
					return list.get(0);

				return null;
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean hasOthersSubscription(Long schemeId) {
		final T scheme = getScheme(schemeId);
		return (Boolean) subscriptionDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.rowCount());
				criteria.add(Restrictions.eq("schemeId", scheme.getId()));
				criteria.add(Restrictions.eq("lotteryType", scheme.getLotteryType()));
				criteria.add(Restrictions.eq("state", SubscriptionState.NORMAL));
				criteria.add(Restrictions.ne("userId", scheme.getSponsorId()));
				List<Integer> list = criteria.list();
				if (list != null && !list.isEmpty()) {
					Integer result = list.get(0);
					return result != null && result > 0;
				}
				return false;
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean hasOthersBaodi(Long schemeId) {
		final T scheme = getScheme(schemeId);
		return (Boolean) baodiDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.rowCount());
				criteria.add(Restrictions.eq("schemeId", scheme.getId()));
				criteria.add(Restrictions.eq("lotteryType", scheme.getLotteryType()));
				criteria.add(Restrictions.eq("state", BaodiState.NORMAL));
				criteria.add(Restrictions.ne("userId", scheme.getSponsorId()));
				List<Integer> list = criteria.list();
				if (list != null && !list.isEmpty()) {
					Integer result = list.get(0);
					return result != null && result > 0;
				}
				return false;
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Baodi> findNormalBaodi(final Long schemeId) {
		final T scheme = getScheme(schemeId);
		return (List<Baodi>) baodiDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("schemeId", schemeId));
				criteria.add(Restrictions.eq("lotteryType", scheme.getLotteryType()));
				criteria.add(Restrictions.eq("state", BaodiState.NORMAL));
				return criteria.list();
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Pagination findMyScheme(final Long userId, final SchemeQueryDTO dto, final Pagination pagination) {
		if (userId == null)
			throw new ServiceException("用户ID不能为空.");
		return (Pagination) getSchemeDao().execute(new ExecuteCallBack() {
			public Object execute(Session session) {
				Criteria criteria = session.createCriteria(schemeClass, "m");
				criteria.createAlias("subscriptions", "s");
				criteria.add(Restrictions.eq("s.lotteryType", getLottery()));
				criteria.add(Restrictions.eq("s.userId", userId));
				//criteria.add(Restrictions.eq("s.state", SubscriptionState.NORMAL));
				
				
				
				if (dto != null) {
					if (dto.getState() != null)
						criteria.add(Restrictions.eq("m.state", dto.getState()));
					if(dto.getShareType() != null) {
						criteria.add(Restrictions.eq("m.shareType", dto.getShareType()));
					}
					
					if (dto.getStartTime() != null)
						criteria.add(Restrictions.ge("m.createTime", dto.getStartTime()));
					
					if (dto.getEndTime() != null)
						criteria.add(Restrictions.le("m.createTime", dto.getEndTime()));
						
					if (StringUtils.isNotBlank(dto.getPeriodNumber()))
						criteria.add(Restrictions.eq("m.periodNumber", dto.getPeriodNumber()));
					
					if (dto.isWon()) {
						criteria.add(Restrictions.eq("m.state", SchemeState.SUCCESS));
						criteria.add(Restrictions.eq("m.won", dto.isWon()));
					}
					if(null!=dto.getRestrictions()&&!dto.getRestrictions().isEmpty()){
						for (Criterion criterion : dto.getRestrictions()) {
							criteria.add(criterion);
						}
					}
					if (dto.getPlayType() != null) {
						switch (getLottery()) {
						case PL:
							if (String.valueOf(0).equals(dto.getPlayType())) {
								criteria.add(Restrictions.ne("m.playType", PlPlayType.P5Direct));
							} else if (String.valueOf(1).equals(dto.getPlayType())) {
								criteria.add(Restrictions.eq("m.playType", PlPlayType.P5Direct));
							}
							break;
						case SFZC:
							com.cai310.lottery.support.zc.PlayType zcPlayType = com.cai310.lottery.support.zc.PlayType
									.valueOfName(dto.getPlayType());
							criteria.add(Restrictions.eq("m.playType", zcPlayType));
							break;
						case DCZC:
							com.cai310.lottery.support.dczc.PlayType dczcPlayType = com.cai310.lottery.support.dczc.PlayType
									.valueOfName(dto.getPlayType());
							criteria.add(Restrictions.eq("m.playType", dczcPlayType));
							break;
						case JCZQ:
							com.cai310.lottery.support.jczq.PlayType jczqPlayType = com.cai310.lottery.support.jczq.PlayType
							.valueOfName(dto.getPlayType());
							criteria.add(Restrictions.eq("m.playType", jczqPlayType));
							break;
						}
					}

				}

				ProjectionList count = Projections.projectionList();
				count.add(Projections.countDistinct("m.id"));
				criteria.setProjection(count);

				Integer rowCount = (Integer) criteria.uniqueResult();
				if (rowCount != null && rowCount > 0) {
					pagination.setTotalCount(rowCount);

					ProjectionList prop = Projections.projectionList();
					prop.add(Projections.groupProperty("s.schemeId"), "schemeId");
					prop.add(Projections.sum("s.cost"), "myCost");
					if(LotteryCategory.FREQUENT.equals(getLottery().getCategory())){
						prop.add(Projections.sum("m.prize"), "myBonus");
						///高频彩我的奖金=方案奖金
					}else{
						prop.add(Projections.sum("s.bonus"), "myBonus");
					}
					prop.add(Projections.min("s.lotteryType"), "lotteryType");
					prop.add(Projections.min("m.won"), "won");
					prop.add(Projections.min("m.periodNumber"), "periodNumber");
					prop.add(Projections.min("m.sponsorId"), "sponsorId");
					prop.add(Projections.min("m.sponsorName"), "sponsorName");
					prop.add(Projections.min("m.schemeCost"), "schemeCost");
					prop.add(Projections.min("m.state"), "schemeState");
					prop.add(Projections.min("m.sendToPrint"), "sendToPrint");
					prop.add(Projections.min("m.prize"), "prize");
					prop.add(Projections.min("m.prizeAfterTax"), "prizeAfterTax");
					prop.add(Projections.min("m.createTime"), "createTime");
					prop.add(Projections.min("m.schemePrintState"), "schemePrintState");
					prop.add(Projections.min("m.shareType"), "shareType");
					///手机加
					prop.add(Projections.min("m.units"), "units");
					prop.add(Projections.min("m.multiple"), "multiple");
					prop.add(Projections.min("m.winningUpdateStatus"), "winningUpdateStatus");
					switch (getLottery()) {
						case PL:
							prop.add(Projections.min("m.playType"), "plPlayType");
							break;
						case SFZC:
							prop.add(Projections.min("m.playType"), "zcPlayType");
							break;
					}
//					if(getLottery().getCategory().equals(LotteryCategory.FREQUENT)){
//						prop.add(Projections.min("m.zoomType"), "zoomType");
//					}
					criteria.setProjection(prop);

					criteria.setResultTransformer(new AliasToBeanResultTransformer(MyScheme.class));

					criteria.addOrder(Order.desc("s.schemeId"));

					if (pagination.getFirst() >= 0) {
						criteria.setFirstResult(pagination.getFirst());
					}
					if (pagination.getPageSize() > 0) {
						criteria.setMaxResults(pagination.getPageSize());
					}

					pagination.setResult(criteria.list());
				} else {
					pagination.setResult(null);
					pagination.setTotalCount(0);
				}

				return pagination;
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Subscription> findSubscriptionsOfScheme(final long schemeId, final SubscriptionState state) {
		final T scheme = getScheme(schemeId);
		DetachedCriteria criteria = DetachedCriteria.forClass(Subscription.class);
		criteria.add(Restrictions.eq("schemeId", schemeId));
		criteria.add(Restrictions.eq("lotteryType", scheme.getLotteryType()));
		if (state != null) {
			criteria.add(Restrictions.eq("state", state));
		}
		return subscriptionDao.findByDetachedCriteria(criteria);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public T cloneNewScheme(T oldScheme) {
		getSchemeDao().getSession().evict(oldScheme);
		oldScheme.setSubscriptions(null);
		T newScheme = DozerMapperSingleton.map(oldScheme, this.schemeClass);
		newScheme.setId(null);
		oldScheme = getSchemeDao().get(oldScheme.getId());
		return newScheme;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Subscription cloneNewSubscription(Subscription oldSubscription) {
		subscriptionDao.getSession().evict(oldSubscription);
		Subscription newSubscrt = DozerMapperSingleton.map(oldSubscription, Subscription.class);
		newSubscrt.setId(null);
		return newSubscrt;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public BigDecimal countSubscribedCost(Long schemeId, final Long userId) {
		final T scheme = getScheme(schemeId);
		return (BigDecimal) subscriptionDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.sum("cost"));
				criteria.add(Restrictions.eq("schemeId", scheme.getId()));
				criteria.add(Restrictions.eq("userId", userId));
				criteria.add(Restrictions.eq("state", SubscriptionState.NORMAL));
				criteria.add(Restrictions.eq("lotteryType", scheme.getLotteryType()));
				return criteria.uniqueResult();
			}
		});
	}

	public void changeOrderPriority(Long schemeId, byte orderPriority) {
		T scheme = getScheme(schemeId);
		scheme.setOrderPriority(orderPriority);
		scheme = saveScheme(scheme);
	}

	public void changeReserved(Long schemeId, boolean reserved) {
		T scheme = getScheme(schemeId);
		scheme.setReserved(reserved);
		scheme = saveScheme(scheme);
	}

	public void resetUnUpdateWon(Long schemeId) {
		T scheme = getScheme(schemeId);
		try {
			scheme.resetUnUpdateWon();
		} catch (DataException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		scheme = saveScheme(scheme);
	}
	

	public void resetPriceUpdated(Long schemeId) {
		T scheme = getScheme(schemeId);
		scheme.setWinningUpdateStatus(WinningUpdateStatus.PRICE_UPDATED);
		scheme = saveScheme(scheme);
	}
	

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Long> findSchemeIdOfSaleAnalyse(final long periodId) {
		return (List<Long>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(Projections.property("id"));
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.add(Restrictions.in("state", SchemeState.finalStatuss));
				criteria.add(Restrictions.or(Restrictions.isNull("saleAnalyseState"), Restrictions.eq("saleAnalyseState", SaleAnalyseState.NONE)));
				return criteria.list();
			}
		});
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<T> findSchemeByState(final SchemePrintState schemePrintState,final SchemeState schemeState,final boolean sendToPrint){
		return (List<T>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("schemePrintState", schemePrintState));
				criteria.add(Restrictions.eq("state", schemeState));
				criteria.add(Restrictions.eq("sendToPrint", sendToPrint));
				criteria.add(Restrictions.gt("createTime", DateUtil.getdecDateOfMinute(new Date(),TicketConstant.QUERY_MIN)));
				return criteria.list();
			}
		});
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<T> countSchemeByState(final SchemePrintState schemePrintState,final SchemeState schemeState,final boolean sendToPrint){
		return (List<T>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				ProjectionList prop = Projections.projectionList();
				prop.add(Projections.count("id"), "units");
				prop.add(Projections.groupProperty("commitTime"), "commitTime");
				criteria.add(Restrictions.eq("schemePrintState", schemePrintState));
				criteria.add(Restrictions.eq("state", schemeState));
				criteria.add(Restrictions.eq("sendToPrint", sendToPrint));
				criteria.add(Restrictions.gt("createTime", DateUtil.getdecDateOfMinute(new Date(),TicketConstant.QUERY_MIN)));
				criteria.setProjection(prop);
				criteria.setResultTransformer(Transformers.aliasToBean(schemeClass));
				return criteria.list();
			}
		});
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<T> countSchemeByCriterion(final List<Criterion> restrictions,final ProjectionList prop){
		return (List<T>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setProjection(prop);
				for (Criterion criterion : restrictions) {
					criteria.add(criterion);
				}
				criteria.setResultTransformer(Transformers.aliasToBean(schemeClass));
				return criteria.list();
			}
		});
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<T> findSchemeByCriterion(final List<Criterion> restrictions){
		return (List<T>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				for (Criterion criterion : restrictions) {
					criteria.add(criterion);
				}
				return criteria.list();
			}
		});
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Subscription> getTop10SaleAnalyse() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Subscription.class, "m");
		ProjectionList prop = Projections.projectionList();
		prop.add(Projections.groupProperty("m.userId"), "userId");
		prop.add(Projections.groupProperty("m.userName"), "userName");
		prop.add(Projections.sum("m.bonus"), "bonus");
		criteria.setProjection(prop);
		criteria.addOrder(Order.desc("bonus"));
		criteria.setResultTransformer(Transformers.aliasToBean(Subscription.class));
		return subscriptionDao.findByDetachedCriteria(criteria,0,10);
	}
	//累计中奖
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Subscription> getBonusAccumulator() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Subscription.class, "m");
		ProjectionList prop = Projections.projectionList();
		prop.add(Projections.sum("m.bonus"), "bonus");
		criteria.setProjection(prop);
		criteria.setResultTransformer(Transformers.aliasToBean(Subscription.class));
		return subscriptionDao.findByDetachedCriteria(criteria,0,1);
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Subscription> getTopWon(Lottery lottery) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Subscription.class, "m");
		ProjectionList prop = Projections.projectionList();
		prop.add(Projections.groupProperty("m.userId"), "userId");
		prop.add(Projections.groupProperty("m.userName"), "userName");
		prop.add(Projections.sum("m.bonus"), "bonus");
		criteria.setProjection(prop);
		criteria.add(Restrictions.eq("lotteryType", lottery));
		criteria.addOrder(Order.desc("bonus"));
		criteria.setResultTransformer(Transformers.aliasToBean(Subscription.class));
		return subscriptionDao.findByDetachedCriteria(criteria,0,5);
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Subscription> getNewestWon() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Subscription.class, "m");
		criteria.add(Restrictions.eq("bonusSended", true));
		criteria.add(Restrictions.eq("state", SubscriptionState.NORMAL));
		criteria.addOrder(Order.desc("lastModifyTime"));
		return subscriptionDao.findByDetachedCriteria(criteria,0,6);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Subscription> getNewestWonOfLottery(Lottery lottery) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Subscription.class, "m");
		criteria.add(Restrictions.eq("bonusSended", true));
		criteria.add(Restrictions.eq("lotteryType", lottery));
		criteria.add(Restrictions.eq("state", SubscriptionState.NORMAL));
		criteria.addOrder(Order.desc("lastModifyTime"));
		return subscriptionDao.findByDetachedCriteria(criteria,0,6);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean isRepeatOrder(final String orderId,final Long userId){
		return (Boolean) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("orderId", orderId));
				criteria.add(Restrictions.eq("sponsorId", userId));
				if(null!=criteria.uniqueResult()){
					return true;
				}else{
					return false;
				}
			}
		});
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public T findSchemeByOrderId(final String orderId){
		return (T) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("orderId", orderId));
				List l= criteria.list();
				if(null!=l&&!l.isEmpty()){
					return l.get(0);
				}
				return null;
			}
		});
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<T> findSchemeByOrderId(final List<String> orderId){
		return (List<T>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.in("orderId", orderId));
				return criteria.list();
			}
		});
	}
	@Autowired
	protected TicketThenDao ticketThenDao;
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public TicketThen findTicketThenByOrderId(final String orderId){
		
		return (TicketThen) ticketThenDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("orderId", orderId));
				return criteria.uniqueResult();
			}
		});
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<T> findWonSchemeBySponsorId(final Long sponsorId, final Date startDate,
			final Date endDate){
		return (List<T>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("sponsorId", sponsorId));
				criteria.add(Restrictions.eq("won", true));
				criteria.add(Restrictions.ge("createTime", startDate));
				criteria.add(Restrictions.le("createTime", endDate));
				return criteria.list();
			}
		});
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List findSubsriptionByCriterion(final List<Criterion> restrictions,final List<Order> orders,final Integer start,final Integer count){
		return (List) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_SCHEME_QUERY_CACHE_REGION);
				if(null!=restrictions){
					for (Criterion criterion : restrictions) {
						criteria.add(criterion);
					}
				}
				if(null!=orders){
					for (Order order : orders) {
						criteria.addOrder(order);
					}
				}
				if (null!=start&&start >= 0) {
					criteria.setFirstResult(start);
				}
				if (null!=count&&count >= 0) {
					criteria.setMaxResults(count);
				}
				return criteria.list();
			}
		});
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<T> findWonOfUnRank(final Integer currDateToInt, final Date startDate, final Date endDate,final int maxSize){
		return (List<T>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("won", true));
				criteria.add(Restrictions.ge("createTime", startDate));
				criteria.add(Restrictions.lt("createTime", endDate));
				criteria.add(Restrictions.gt("prize", new BigDecimal(0)));
				criteria.add(Restrictions.or(Restrictions.isNull("rankFlag"),Restrictions.ne("rankFlag", currDateToInt)));
				criteria.add(
						Restrictions.or(
							Restrictions.or(
								Restrictions.eq("state", SchemeState.SUCCESS),
								Restrictions.eq("state", SchemeState.CANCEL)
							), 
							Restrictions.eq("state", SchemeState.REFUNDMENT)
						)
				);
				criteria.setMaxResults(maxSize);
				return criteria.list();
			}
		});
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<T> findWinUpdateOfUnGrade(final int maxSize){
		return (List<T>) getSchemeDao().execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {				
				criteria.add(
						Restrictions.or(
							Restrictions.or(
								Restrictions.eq("state", SchemeState.SUCCESS),
								Restrictions.eq("state", SchemeState.CANCEL)
							), 
							Restrictions.eq("state", SchemeState.REFUNDMENT)
						)
				);
				criteria.add(Restrictions.or(
						Restrictions.eq("winningUpdateStatus", WinningUpdateStatus.WINNING_UPDATED),
						Restrictions.eq("winningUpdateStatus", WinningUpdateStatus.PRICE_UPDATED)
						)
			    );
				criteria.add(Restrictions.eq("gradeFlag",false));
				criteria.setMaxResults(maxSize);
				return criteria.list();
			}
		});
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Subscription> findSubscriptionsOfBaoDiScheme(final long schemeId,final long userId, final SubscriptionState state) {
		final T scheme = getScheme(schemeId);
		DetachedCriteria criteria = DetachedCriteria.forClass(Subscription.class);
		criteria.add(Restrictions.eq("schemeId", schemeId));
		criteria.add(Restrictions.eq("lotteryType", scheme.getLotteryType()));
		criteria.add(Restrictions.eq("way", SubscriptionWay.TRANSFORM_BAODI));
		criteria.add(Restrictions.eq("userId", userId));
		if (state != null) {
			criteria.add(Restrictions.eq("state", state));
		}
		return subscriptionDao.findByDetachedCriteria(criteria);
	}
}
