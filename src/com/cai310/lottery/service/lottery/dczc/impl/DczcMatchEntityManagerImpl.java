package com.cai310.lottery.service.lottery.dczc.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.GameColorHolder;
import com.cai310.lottery.JczqConstant;
import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.dao.lottery.dczc.DczcMatchDao;
import com.cai310.lottery.dao.lottery.dczc.DczcSpInfoDao;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.entity.lottery.dczc.DczcSpInfo;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.service.lottery.dczc.DczcMatchEntityManager;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;

@Service
@Transactional
public class DczcMatchEntityManagerImpl implements DczcMatchEntityManager {

	@Autowired
	protected DczcMatchDao matchDao;

	@Autowired
	protected DczcSpInfoDao dczcSpInfoDao;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<DczcMatch> findMatchs(final Long periodId) {
		return (List<DczcMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_DCZC_MATCH_QUERY_CACHE_REGION);
				criteria.add(Restrictions.eq("periodId", periodId));
				criteria.addOrder(Order.asc("lineId"));
				return criteria.list();
			}
		});
	}
	

	public List<DczcMatch> findMatchs(final Integer matchDate) {
		return (List<DczcMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_DCZC_MATCH_QUERY_CACHE_REGION);
				criteria.add(Restrictions.eq("matchDate", matchDate));
				criteria.addOrder(Order.asc("lineId"));
				return criteria.list();
			}
		});
	}
	
	
	public List<DczcMatch> findMatchsOfUnEnd() {
		final Date startTime = new DateTime().plusMinutes(JczqConstant.MATCH_AHEAD_TIME).toDate();
		return (List<DczcMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_DCZC_MATCH_QUERY_CACHE_REGION);
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
	public List<DczcMatch> findMatchs(final String periodNumber) {
		return (List<DczcMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_DCZC_MATCH_QUERY_CACHE_REGION);
				criteria.add(Restrictions.eq("periodNumber", periodNumber));
				criteria.addOrder(Order.asc("lineId"));
				return criteria.list();
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public DczcMatch getMatch(Long id) {
		return matchDao.get(id);
	}

	public DczcMatch saveMatch(DczcMatch match) {
		if(StringUtils.isBlank(match.getGameColor())){
			match.setGameColor(GameColorHolder.getColor(match.getGameName()));
		}
		return matchDao.save(match);
	}

	public void saveMatchs(List<DczcMatch> matchs) {
		int lineId = 0;
		for (DczcMatch match : matchs) {
			match.setLineId(lineId);
			match = saveMatch(match);
			lineId++;
		}
		List<DczcMatch> ms = findMatchs(matchs.get(0).getPeriodId());
		if (matchs.size() < ms.size()) {
			for (int i = matchs.size(); i < ms.size(); i++) {
				matchDao.delete(ms.get(i));
			}
		}

	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<Integer, DczcMatch> findMatchMap(Long periodId) {
		List<DczcMatch> matchs = findMatchs(periodId);
		Map<Integer, DczcMatch> matchMap = new HashMap<Integer, DczcMatch>();
		for (DczcMatch m : matchs) {
			matchMap.put(m.getLineId(), m);
		}
		return matchMap;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Integer> findEndedMatchLineIds(final Long periodId, final Date leMatchTime) {
		List<DczcMatch> matchs = findMatchs(periodId);
		List<Integer> lineIds = new ArrayList<Integer>();
		for (DczcMatch match : matchs) {
			if (match.isCancel() || match.isEnded() || match.getMatchTime().before(leMatchTime))
				lineIds.add(match.getLineId());
		}
		return lineIds;
	}

	public void updateSpInfo(DczcSpInfo dczcSpInfo) {
		if (dczcSpInfo == null) {
			return;
		}
		DczcSpInfo oldO = this.dczcSpInfoDao.getDczcSpInfo(dczcSpInfo.getPlayType(), dczcSpInfo.getPeriodNumber(),
				dczcSpInfo.getLineId());
		if (oldO != null) {
			oldO.setSpContent(dczcSpInfo.getSpContent());
			dczcSpInfoDao.save(oldO);
		} else {
			dczcSpInfoDao.save(dczcSpInfo);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<DczcSpInfo> getDczcSpInfo(final PlayType playType, final String periodNumer) {
		return (List<DczcSpInfo>) dczcSpInfoDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.add(Restrictions.eq("playType", playType));
				criteria.add(Restrictions.eq("periodNumber", periodNumer));
				criteria.addOrder(Order.asc("lineId"));
				return criteria.list();
			}
		});
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<Integer, DczcMatch> findMatchMap(Long periodId, List<Integer> lineIdList) {
		if (lineIdList == null || lineIdList.isEmpty())
			return null;
		List<DczcMatch> matchs = findMatchs(periodId, lineIdList);
		if (matchs == null || matchs.isEmpty())
			return null;
		Map<Integer, DczcMatch> map = new HashMap<Integer, DczcMatch>();
		for (DczcMatch match : matchs) {
			map.put(match.getLineId(), match);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<DczcMatch> findMatchs(final Long periodId, final List<Integer> lineIdList) {
		if (lineIdList == null || lineIdList.isEmpty())
			return null;
		return (List<DczcMatch>) matchDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_DCZC_MATCH_QUERY_CACHE_REGION);
				criteria.add(Restrictions.eq("periodId", periodId));
				if (lineIdList.size() == 1) {
					criteria.add(Restrictions.eq("lineId", lineIdList.get(0)));
				} else if (lineIdList.size() <= 15) {
					Disjunction disjun = Restrictions.disjunction();
					for (Integer lineId : lineIdList) {
						disjun.add(Restrictions.eq("lineId", lineId));
					}
					criteria.add(disjun);
				} else {
					criteria.add(Restrictions.in("lineId", lineIdList));
				}
				criteria.addOrder(Order.asc("lineId"));
				return criteria.list();
			}
		});
	}
}
