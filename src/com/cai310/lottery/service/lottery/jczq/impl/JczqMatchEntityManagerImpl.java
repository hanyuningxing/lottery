package com.cai310.lottery.service.lottery.jczq.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.dao.lottery.jczq.JczqMatchDao;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.jczq.JczqMatchEntityManager;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;
import com.cai310.utils.DateUtil;

@Service("jczqMatchEntityManagerImpl")
@Transactional
public class JczqMatchEntityManagerImpl implements JczqMatchEntityManager {

	
	@Autowired
	protected JczqMatchDao matchDao;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public JczqMatch getMatch(Long id) {
		return matchDao.get(id);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<JczqMatch> findMatchs(final Long periodId) {
		return (List<JczqMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_JCZQ_MATCH_QUERY_CACHE_REGION);
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.add(Restrictions.gt("matchTime", DateUtil.getdecDateOfMinute(new Date(),5*24*60)));//前5天以来的对阵
				criteria.addOrder(Order.asc("matchDate"));
				criteria.addOrder(Order.asc("lineId"));
				return criteria.list();
			}
		});
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<String> findEndedMatchLineIds(final Long periodId, final Date leMatchTime) throws DataException {
		List<JczqMatch> matchs = findMatchs(periodId);
		List<String> matchIds = new ArrayList<String>();
		for (JczqMatch match : matchs) {
			if (match.isCancel() || match.isEnded() || match.getWebOfficialEndTime().before(leMatchTime))
				matchIds.add(match.getMatchKey());
		}
		return matchIds;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<JczqMatch> findMatchsOfTicketUnEnd() {
		final Date startTime = new DateTime().plusMinutes(JczqConstant.TICKET_AHEAD_TIME).toDate();
		return (List<JczqMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_JCZQ_MATCH_CACHE_REGION);
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
	public List<JczqMatch> findMatchsOfUnEnd() {
		final Date startTime = new DateTime().plusMinutes(JczqConstant.MATCH_AHEAD_TIME).toDate();
		return (List<JczqMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_JCZQ_MATCH_QUERY_CACHE_REGION);
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
	public JczqMatch findLastMatchsOfEnd() {
		return (JczqMatch) matchDao.execute(new CriteriaExecuteCallBack() {
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveMatchs(List<JczqMatch> matchs) {
		for (JczqMatch m : matchs) {
			matchDao.save(m);
		}
		//最多保留14场的足彩对阵，之前的去除其标识
		List<JczqMatch> zcMatchs= (List<JczqMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("zcMatch", Boolean.TRUE));
				criteria.addOrder(Order.desc("matchDate"));
				criteria.addOrder(Order.desc("lineId"));
				List<JczqMatch> list =  criteria.list();
				return list;
			}
		});
		int zcMatchCount = zcMatchs.size();
		JczqMatch match = null;
		if(zcMatchs!=null && zcMatchCount>14){
			for(int i=14;i<zcMatchCount;i++){
				match = zcMatchs.get(i);
				match.setZcMatch(Boolean.FALSE);
				matchDao.save(match);
			}
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Integer getFirstMatchDate() {
		return (Integer) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_JCZQ_MATCH_QUERY_CACHE_REGION);
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
	public List<JczqMatch> findMatchs(final Integer matchDate) {
		return (List<JczqMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_JCZQ_MATCH_QUERY_CACHE_REGION);
				criteria.add(Restrictions.eq("matchDate", matchDate));
				criteria.addOrder(Order.asc("lineId"));
				return criteria.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<JczqMatch> findMatchs(final List<String> matchKeyList) {
		return (List<JczqMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_JCZQ_MATCH_QUERY_CACHE_REGION);
				criteria.add(Restrictions.in("matchKey", matchKeyList));
				criteria.addOrder(Order.asc("matchKey"));
				return criteria.list();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<JczqMatch> findMatchsOfZc(){
		return (List<JczqMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.add(Restrictions.eq("zcMatch", Boolean.TRUE));
				criteria.addOrder(Order.asc("matchKey"));
				return criteria.list();
			}
		});
	}

}
