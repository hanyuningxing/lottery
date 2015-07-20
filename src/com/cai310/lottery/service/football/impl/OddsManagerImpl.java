package com.cai310.lottery.service.football.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.cache.CacheConstant;
import com.cai310.lottery.cache.CompanyLocalCache;
import com.cai310.lottery.cache.JczqLocalCache;
import com.cai310.lottery.dao.football.CompanyDao;
import com.cai310.lottery.dao.football.LetGoalDao;
import com.cai310.lottery.dao.football.LetGoalDetailDao;
import com.cai310.lottery.dao.football.StandardDao;
import com.cai310.lottery.dao.football.StandardDetailDao;
import com.cai310.lottery.dao.football.TotalScoreDao;
import com.cai310.lottery.dao.football.TotalScoreDetailDao;
import com.cai310.lottery.entity.football.Company;
import com.cai310.lottery.entity.football.LetGoal;
import com.cai310.lottery.entity.lottery.jczq.JczqMatch;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.football.OddsManager;
import com.cai310.lottery.service.lottery.jczq.JczqMatchEntityManager;
import com.cai310.orm.hibernate.CriteriaExecuteCallBack;
import com.google.common.collect.Maps;

@Service("OddsManagerImpl")
@Transactional
public class OddsManagerImpl implements OddsManager {
	@Autowired
	CompanyDao companyDao;
	@Autowired
	LetGoalDao letGoalDao;
	@Autowired
	LetGoalDetailDao letGoalDetailDao;
	@Autowired
	StandardDao standardDao;
	@Autowired
	StandardDetailDao standardDetailDao;
	@Autowired
	TotalScoreDao totalScoreDao;
	@Autowired
	TotalScoreDetailDao totalScoreDetailDao;
	@Autowired
	private JczqLocalCache localCache;
	@Autowired
	CompanyLocalCache companyLocalCache;
	@Autowired
	private JczqMatchEntityManager matchEntityManager;
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Map<Long, JczqMatch> getJczqMatchMap() {
		Map<Long, JczqMatch> map = localCache.getMatchMap();
		if(null==map||map.isEmpty()){
			map = Maps.newHashMap();
			List<JczqMatch> matchList = matchEntityManager.findMatchsOfUnEnd();
			for (JczqMatch jczqMatch : matchList) {
				map.put(jczqMatch.getMatchKeyInteger(), jczqMatch);
			}
		}
		return map;
	}
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Company> getAllCompanyList(){
		return (List<Company>) companyDao.execute(new CriteriaExecuteCallBack() {
			public Object execute(Criteria criteria) {
				criteria.setCacheable(true);
				criteria.setCacheRegion(CacheConstant.H_LETGOAL_QUERY_CACHE_REGION);
				return criteria.list();
			}
		});
	}
	@Override
	public Company saveCompany(Company company){
		return companyDao.save(company);
	}
	@Override
	public Company getCompany(Long id){
		return companyDao.get(id);
	}
	private Company getCompanyByWin310Id(Long win310Id){
		return companyDao.findUniqueBy("win310Id", win310Id);
	}
	@Override
	public Long getCompanyByWin310(Long win310Id) throws DataException{
		List<Company> companyList = companyLocalCache.getCompanyLocalCache();
		Company company = null;
		if(null==companyList||companyList.isEmpty()){
			company = getCompanyByWin310Id(win310Id);
		}else{
			for (Company companyTemp : companyList) {
				if(win310Id.equals(companyTemp.getWin310Id())){
					company = companyTemp;
				}
			}
		}
		if(null!=company){
			return company.getId();
		}else{
			throw new DataException("找不到彩客对应的");
		}
	}
}
