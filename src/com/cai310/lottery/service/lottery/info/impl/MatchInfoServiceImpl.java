package com.cai310.lottery.service.lottery.info.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.info.MatchInfoDao;
import com.cai310.lottery.entity.info.MatchInfo;
import com.cai310.lottery.entity.info.OddsData;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.service.lottery.MatchInfoService;

@Service
@Transactional
class MatchInfoServiceImpl implements MatchInfoService{

	@Autowired
	private MatchInfoDao matchInfoDao;
	
	public void saveMatchInfo(List<MatchInfo> list) {
		
		for(MatchInfo info:list){
			MatchInfo o=matchInfoDao.findMatchInfo(info.getLottery(),info.getPeriodId(),info.getLineId());
			if(o!=null){
				o.setOddsData(info.getOddsData());
				matchInfoDao.save(o);
			}else{
				matchInfoDao.save(info);
			}
		}
		
	}
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void fillOddsData(List<DczcMatch> matchList) {
		DetachedCriteria criteria =DetachedCriteria.forClass(MatchInfo.class);
		criteria.add(Restrictions.eq("periodNumber", matchList.get(0).getPeriodNumber()));
		criteria.add(Restrictions.eq("lottery", Lottery.DCZC));
		List<MatchInfo> matchInfoList=matchInfoDao.findByDetachedCriteria(criteria);
		if (matchInfoList != null && !matchInfoList.isEmpty()) {
			Map<Integer,OddsData> map=new HashMap<Integer,OddsData>();
			for (MatchInfo info : matchInfoList) {
				map.put(info.getLineId().intValue(), info.getOddsData());
			}
			for(DczcMatch match:matchList){
				match.setOddsData(map.get(match.getLineId().intValue()));
			}
		}
	}
	
}