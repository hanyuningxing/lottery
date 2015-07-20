package com.cai310.lottery.service.lottery.keno.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.lottery.GroupNumMisseDao;
import com.cai310.lottery.dao.lottery.MissDataDao;
import com.cai310.lottery.dao.lottery.NumberAnalyseDao;
import com.cai310.lottery.dao.lottery.RandomNumMisseDao;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.entity.lottery.GroupNumMiss;
import com.cai310.lottery.entity.lottery.MissDataInfo;
import com.cai310.lottery.entity.lottery.NumberAnalyse;
import com.cai310.lottery.entity.lottery.RandomNumMiss;
import com.cai310.lottery.entity.lottery.keno.KenoPeriod;
import com.cai310.lottery.service.lottery.MissDataEntityManager;
import com.cai310.utils.ReflectionUtils;

@Transactional
public abstract class MissDataEntityManagerImpl<T extends MissDataInfo, P extends KenoPeriod> implements MissDataEntityManager<T> {
	
	public abstract MissDataDao<T> getMissDataDao();
	
	public abstract IssueDataDao<P> getIssueDataDao();
	
	@Autowired
	private NumberAnalyseDao numberAnalyseDao;
	@Autowired
	private GroupNumMisseDao groupNumMissDao;
	@Autowired
	private RandomNumMisseDao randomNumMissDao;
	
	private Class<T> missDataClazz;
	
	@SuppressWarnings("unchecked")
	public MissDataEntityManagerImpl(){
		this.missDataClazz= ReflectionUtils.getSuperClassGenricType(getClass(), 0);
	}

	@Override
	public T getMissDataByPeriodId(Long periodId) {
		return getMissDataDao().getByPeriodId(periodId);
	}

	@Override
	public T getMissData(Long id) {
		return getMissDataDao().get(id);
	}

	@Override
	public void saveBatchMissData(List<T> dataList) {
		for(T t:dataList){
			T data=getMissDataDao().getByPeriodId(t.getPeriodId());
			KenoPeriod kenoPeriod=getIssueDataDao().get(t.getPeriodId());
			t.setPeriodNumber(kenoPeriod.getPeriodNumber());
			if(data==null){
				getMissDataDao().save(t);
			}else{
				data.setContent(t.getContent());
				data.setResult(t.getResult());
				data.setPeriodNumber(t.getPeriodNumber());
				data.setPeriodNumber(t.getPeriodNumber());
				getMissDataDao().save(data);
			}
		}
		
	}

	@Override
	public T getLastMissData() {
		DetachedCriteria criteria=DetachedCriteria.forClass(missDataClazz);
		criteria.addOrder(Order.desc("periodNumber"));
		criteria.addOrder(Order.desc("id"));
		
		List<T> list=getMissDataDao().findByDetachedCriteria(criteria,0,1);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<T> getLastMissDatas(int count) {
		DetachedCriteria criteria=DetachedCriteria.forClass(missDataClazz);
		criteria.addOrder(Order.desc("periodId"));
		criteria.addOrder(Order.desc("id"));
		if(count>0){
			return getMissDataDao().findByDetachedCriteria(criteria,0,count);
		}
		return getMissDataDao().findByDetachedCriteria(criteria);
	}
	
	@Override
	public GroupNumMiss getGroupNumMiss(Long id) {
		return groupNumMissDao.get(id);
	}

	@Override
	public GroupNumMiss getGroupNumMiss(Lottery lottery) {
		return groupNumMissDao.findUniqueBy("lottery", lottery);
	}

	@Override
	public GroupNumMiss saveGroupNumMiss(GroupNumMiss data) {
		return groupNumMissDao.save(data);
	}

	@Override
	public NumberAnalyse getNumberAnalyseById(Long id) {
		return numberAnalyseDao.get(id);
	}

	@Override
	public NumberAnalyse getNumberAnalyseByLottery(Lottery lottery) {
		return numberAnalyseDao.findUniqueBy("lottery", lottery);
	}

	@Override
	public NumberAnalyse saveNumberAnalyse(NumberAnalyse data) {
		return numberAnalyseDao.save(data);
	}
	


	public RandomNumMiss saveRandomNumMiss(RandomNumMiss data){
		return randomNumMissDao.save(data);
	}

	public List<RandomNumMiss> getRandomNumMissByLotter(Lottery lottery){
		return randomNumMissDao.findBy("lottery", lottery);
	}

	public RandomNumMiss getRandomNumMiss(Long id){
		return randomNumMissDao.get(id);
	}

	public RandomNumMiss getRandomNumMiss(Lottery lottery,String key){
		DetachedCriteria c=DetachedCriteria.forClass(RandomNumMiss.class);
		c.add(Restrictions.eq("lottery", lottery));
		c.add(Restrictions.eq("keyWord", key));
		List<RandomNumMiss> list=randomNumMissDao.findByDetachedCriteria(c);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	public List getLastMissDatas(int count,String start,String end){
		DetachedCriteria criteria=DetachedCriteria.forClass(missDataClazz);
		if(StringUtils.isNotEmpty(start)||StringUtils.isNotEmpty(start))
			criteria.add(Restrictions.ge("periodNumber", start));
		if(StringUtils.isNotEmpty(end)||StringUtils.isNotBlank(end))
			criteria.add(Restrictions.le("periodNumber", end));
		criteria.addOrder(Order.desc("periodId"));
		criteria.addOrder(Order.desc("id"));
		if(count>0){
			return getMissDataDao().findByDetachedCriteria(criteria,0,count);
		}
		return getMissDataDao().findByDetachedCriteria(criteria);
	}
}
