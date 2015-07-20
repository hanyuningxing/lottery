package com.cai310.lottery.service.info.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.common.EventLogType;
import com.cai310.lottery.common.InfoState;
import com.cai310.lottery.common.InfoSubType;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.dao.info.MobileNewsDataDao;
import com.cai310.lottery.dao.info.NewsInfoDataDao;
import com.cai310.lottery.dao.info.RuleDao;
import com.cai310.lottery.dao.info.TagsInfoDataDao;
import com.cai310.lottery.dao.lottery.EventLogDao;
import com.cai310.lottery.entity.info.MobileNewsData;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.entity.info.Rule;
import com.cai310.lottery.entity.info.TagsInfoData;
import com.cai310.lottery.entity.lottery.EventLog;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.info.NewsInfoDataEntityManager;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.Struts2Utils;

@Service("newsInfoDataEntityManager")
@Transactional
public class NewsInfoDataEntityManagerImpl implements NewsInfoDataEntityManager {
	@Autowired
	protected NewsInfoDataDao newsInfoDataDao;
	@Autowired
	protected QueryService queryService;
	@Autowired
	private EventLogDao eventLogDao;
	@Autowired
	private MobileNewsDataDao mobileNewsDataDao;
	
	@Autowired
	private TagsInfoDataDao tagsInfoDataDao;
	
	@Autowired
	private RuleDao ruleDao;
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public NewsInfoData getNewsInfoData(Long id) {
		return newsInfoDataDao.get(id);
	}

	public NewsInfoData saveNewsInfoData(NewsInfoData entity,AdminUser operator) {
		// 记录操作日志
		EventLog eventLog = new EventLog();
		eventLog.setStartTime(new Date() );
		eventLog.setDoneTime(new Date());
		eventLog.setLogType(EventLogType.Info_Admin.getLogType());
		eventLog.setMsg("保存或更改了文章【"
				+ entity.getId() + "】"+"实体为："+entity.toString());
		eventLog.setNormalFinish(true);
		eventLog.setOperator(operator.getName());
		eventLog.setLotteryType(null);
		eventLog.setIp(Struts2Utils.getRemoteAddr());
		eventLogDao.save(eventLog);
		return newsInfoDataDao.save(entity);
	}
	
	public NewsInfoData saveNewsInfoData(NewsInfoData entity) {
		return newsInfoDataDao.save(entity);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<NewsInfoData> getNewsInfoDataBy(InfoType type,Lottery lotteryType,Integer needNum,Boolean isIndex) {
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsInfoData.class);
		if(null!=type){
			criteria.add(Restrictions.eq("type", type));
		}
		if(null!=lotteryType){
			criteria.add(Restrictions.eq("lotteryType", lotteryType));
		}
		if(isIndex){
			criteria.addOrder(Order.asc("level"));
		}
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("id"));
		return queryService.findByDetachedCriteria(criteria, 0, needNum, true);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<NewsInfoData> getNewsInfoDataBy(InfoType type,Lottery lotteryType,InfoSubType subType,Integer needNum,Boolean isIndex) {
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsInfoData.class);
		if(null!=type){
			criteria.add(Restrictions.eq("type", type));
		}
		if(null!=lotteryType){
			criteria.add(Restrictions.eq("lotteryType", lotteryType));
		}
		if(null!=subType){
			criteria.add(Restrictions.eq("subType", subType));
		}
		if(isIndex){
			criteria.add(Restrictions.eq("isNotice", Integer.valueOf(1)));
			criteria.addOrder(Order.asc("level"));
		}
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("id"));
		return queryService.findByDetachedCriteria(criteria, 0, needNum, true);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<NewsInfoData> getNewsInfoDataByArr(InfoType type,Lottery[] lotteryTypeArr,Integer needNum,Boolean isIndex) {
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsInfoData.class);
		if(null!=type){
			criteria.add(Restrictions.eq("type", type));
		}
		if(null!=lotteryTypeArr){
			criteria.add(Restrictions.in("lotteryType", lotteryTypeArr));
		}
		if(isIndex){
			criteria.addOrder(Order.asc("level"));
		}
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("id"));
		return queryService.findByDetachedCriteria(criteria, 0, needNum, true);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Pagination getNewsInfoDataBy(InfoType type,Lottery lotteryType,Pagination page) {
		XDetachedCriteria criteria = new XDetachedCriteria(NewsInfoData.class);
		criteria.setCacheable(true);
		if(null!=type){
			criteria.add(Restrictions.eq("type", type));
		}
		if(null!=lotteryType){
			criteria.add(Restrictions.eq("lotteryType", lotteryType));
		}
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("id"));
		return queryService.findByCriteriaAndPagination(criteria, page);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public MobileNewsData getMobileNewsData(long id) {
		return mobileNewsDataDao.get(id);
	}

	@Override
	public MobileNewsData saveMobileNewsData(MobileNewsData entity,AdminUser operator) {
		// 记录操作日志
		EventLog eventLog = new EventLog();
		eventLog.setStartTime(new Date() );
		eventLog.setDoneTime(new Date());
		eventLog.setLogType(EventLogType.Info_Admin.getLogType());
		eventLog.setMsg("保存或更改了手机公告【"
				+ entity.getId() + "】"+"实体为："+entity.toString());
		eventLog.setNormalFinish(true);
		eventLog.setOperator(operator.getName());
		eventLog.setLotteryType(null);
		eventLog.setIp(Struts2Utils.getRemoteAddr());
		eventLogDao.save(eventLog);
		return mobileNewsDataDao.save(entity);
	}

	@Override
	public void delMobileNewsData(long id) {
		mobileNewsDataDao.delete(id);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Rule getRule(long id) {
		return ruleDao.get(id);
	}

	@Override
	public Rule saveRule(Rule entity, AdminUser operator) {
		return ruleDao.save(entity);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public TagsInfoData getTagsInfoData(long id) {
		return tagsInfoDataDao.get(id);
	}

	@Override
	public TagsInfoData saveTagsInfoData(TagsInfoData entity, AdminUser operator) {
		return tagsInfoDataDao.save(entity);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void delTagsInfoData(long id) {
		tagsInfoDataDao.delete(id);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<TagsInfoData> loadTagsInfoDatas() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TagsInfoData.class);
		criteria.addOrder(Order.desc("id"));
		return queryService.findByDetachedCriteria(criteria, true);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<TagsInfoData> loadTagsInfoDatas(int level, Lottery lotterType) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TagsInfoData.class);
		if(null!=lotterType){
			criteria.add(Restrictions.eq("lotteryType", lotterType));
		}
		if(level>=0){
			criteria.add(Restrictions.eq("level", level));
		}
		criteria.addOrder(Order.desc("id"));
		return queryService.findByDetachedCriteria(criteria, 0, 30, true);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<NewsInfoData> loadTopics(InfoType type, InfoSubType[] subTypes,
			Integer needNum) {
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsInfoData.class);
		criteria.add(Restrictions.eq("type", type));
		criteria.add(Restrictions.in("subType", subTypes));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("createTime"));
		return queryService.findByDetachedCriteria(criteria, 0,-1,true);
	}
}
