package com.cai310.lottery.service.lottery.jclq.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.JclqConstant;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.dao.lottery.jclq.JclqMatchDao;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.entity.lottery.jclq.JclqMatch;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.jclq.JclqMatchEntityManager;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;
import com.cai310.utils.DateUtil;

@Service("jclqMatchEntityManagerImpl")
@Transactional
public class JclqMatchEntityManagerImpl implements JclqMatchEntityManager {

	
	@Autowired
	protected JclqMatchDao matchDao;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public JclqMatch getMatch(Long id) {
		return matchDao.get(id);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<JclqMatch> findMatchsOfUnEnd() {
		final Date startTime = new DateTime().plusMinutes(JclqConstant.MATCH_AHEAD_TIME).toDate();
		return (List<JclqMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_JCLQ_MATCH_QUERY_CACHE_REGION);
				criteria.add(Restrictions.gt("matchTime", startTime));
				criteria.add(Restrictions.eq("ended", false));
				criteria.add(Restrictions.eq("cancel", false));
				criteria.addOrder(Order.asc("matchDate"));
				criteria.addOrder(Order.asc("lineId"));
				return criteria.list();
			}
		});
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<JclqMatch> findMatchsOfTicketUnEnd() {
		final Date startTime = new DateTime().plusMinutes(JclqConstant.TICKET_AHEAD_TIME).toDate();
		return (List<JclqMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_JCLQ_MATCH_CACHE_REGION);
				criteria.add(Restrictions.gt("matchTime", startTime));
				criteria.add(Restrictions.eq("ended", false));
				criteria.add(Restrictions.eq("cancel", false));
				criteria.addOrder(Order.asc("matchDate"));
				criteria.addOrder(Order.asc("lineId"));
				return criteria.list();
			}
		});
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<String> findEndedMatchLineIds(final Long periodId, final Date leMatchTime) throws DataException {
		List<JclqMatch> matchs = findMatchs(periodId);
		List<String> matchIds = new ArrayList<String>();
		for (JclqMatch match : matchs) {
			if (match.isCancel() || match.isEnded() || match.getWebOfficialEndTime().before(leMatchTime))
				matchIds.add(match.getMatchKey());
		}
		return matchIds;
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<JclqMatch> findMatchs(final Long periodId) {
		return (List<JclqMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_JCLQ_MATCH_QUERY_CACHE_REGION);
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.add(Restrictions.gt("matchTime", DateUtil.getdecDateOfMinute(new Date(),5*24*60)));//前5天以来的对阵
				criteria.addOrder(Order.asc("matchDate"));
				criteria.addOrder(Order.asc("lineId"));
				return criteria.list();
			}
		});
	}
	@Override
	public void saveMatchs(List<JclqMatch> matchs) {
		for (JclqMatch m : matchs) {
			matchDao.save(m);
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Integer getFirstMatchDate() {
		return (Integer) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_JCLQ_MATCH_QUERY_CACHE_REGION);
				criteria.setProjection(Projections.property("matchDate"));
				criteria.addOrder(Order.asc("matchDate"));
				criteria.setMaxResults(1);
				return criteria.uniqueResult();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<JclqMatch> findMatchs(final Integer matchDate) {
		return (List<JclqMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_JCLQ_MATCH_QUERY_CACHE_REGION);
				criteria.add(Restrictions.eq("matchDate", matchDate));
				criteria.addOrder(Order.asc("lineId"));
				return criteria.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JclqMatch> findMatchs(final List<String> matchKeyList) {
		return (List<JclqMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_JCLQ_MATCH_QUERY_CACHE_REGION);
				criteria.add(Restrictions.in("matchKey", matchKeyList));
				criteria.addOrder(Order.asc("matchKey"));
				return criteria.list();
			}
		});
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public JclqMatch findLastMatchsOfEnd() {
		return (JclqMatch) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_JCZQ_MATCH_QUERY_CACHE_REGION);
				criteria.add(Restrictions.eq("ended", true));
				criteria.addOrder(Order.desc("matchDate"));
				criteria.addOrder(Order.desc("lineId"));
				criteria.setFetchSize(1);
				List list =  criteria.list();
				if(null!=list&&!list.isEmpty()){
					return list.get(0);
				}
				return null;
			}
		});
	}
}
