package com.cai310.lottery.service.lottery.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.miss.AnalyseFilter;
import com.cai310.lottery.common.miss.MissDataFilter;
import com.cai310.lottery.common.miss.MissDataUpdate;
import com.cai310.lottery.entity.lottery.GroupNumMiss;
import com.cai310.lottery.entity.lottery.MissDataInfo;
import com.cai310.lottery.entity.lottery.NumberAnalyse;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodData;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.MissDataControllerService;
import com.cai310.lottery.service.lottery.MissDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.utils.ReflectionUtils;
import com.cai310.utils.TemplateGenerator;

public abstract class MissDataControllerServiceImpl<T extends MissDataInfo, P extends PeriodData> implements
		MissDataControllerService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	public PeriodEntityManager periodEntityManger;

	public abstract PeriodDataEntityManager<P> getPeriodDataEntityManager();

	public abstract MissDataEntityManager<T> getMissDataManager();

	public abstract Lottery getLottery();

	/**
	 * 创建遗漏数据的物理文件
	 */
	public abstract void createMissFiles();

	/**
	 * 创建各个遗漏项的命中规则
	 * 
	 * @return
	 */
	public abstract List<MissDataFilter> createFilters();

	private Class<P> periodDataClazz;

	private Class<T> missDataInfoClazz;

	@SuppressWarnings("unchecked")
	public MissDataControllerServiceImpl() {
		this.missDataInfoClazz = ReflectionUtils.getSuperClassGenricType(getClass(), 0);
		this.periodDataClazz = ReflectionUtils.getSuperClassGenricType(getClass(), 1);
	}

	/**
	 * 更新遗漏数据
	 */
	@Override
	@Transactional
	public void updateMissData() {
		T missData = getMissDataManager().getLastMissData();///找出最后生成的遗漏普通数字彩是取最近10期的第10期
		///找出这一期后还需要更新的
		DetachedCriteria criteria = DetachedCriteria.forClass(periodDataClazz);
		if (missData != null) {
			criteria.add(Restrictions.gt("periodId", missData.getPeriodId()));
		} else {
			try {
				missData = missDataInfoClazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			logger.info("还未有遗漏数据!");
		}
		criteria.add(Restrictions.isNotNull("result"));
		criteria.addOrder(Order.asc("id"));

		List<PeriodData> list = getPeriodDataEntityManager().findByDetachedCriteria(criteria);
		if (list != null && !list.isEmpty()) {
			logger.info("开始进行遗漏数据更新!");
			List<MissDataFilter> filter = createFilters();
			try {
				MissDataUpdate missUpdate = new MissDataUpdate(list, missData, filter);
				List missList = missUpdate.update();
				getMissDataManager().saveBatchMissData(missList);
				updateNumberAnalyse();
				updateGroupNumMiss();
				createMissFiles();
				makeShuNewResult();
			} catch (DataException e) {
				e.printStackTrace();
			}
		} else {
			logger.info("未查询到新的开奖数据!");
		}
	}

	/**
	 * 统计号码出现次数
	 */
	public void updateNumberAnalyse() {
		logger.info("开始更新冷热号码");
		NumberAnalyse ns = getMissDataManager().getNumberAnalyseByLottery(getLottery());

		DetachedCriteria criteria = DetachedCriteria.forClass(periodDataClazz);
		criteria.add(Restrictions.isNotNull("result"));
		if (ns != null) {
			criteria.add(Restrictions.gt("periodId", ns.getLastPeriodId()));
		}
		criteria.addOrder(Order.desc("periodId"));
		criteria.addOrder(Order.desc("id"));
		List<PeriodData> list = getPeriodDataEntityManager().findByDetachedCriteria(criteria);
		if (list == null || list.isEmpty()) {
			logger.info("无开奖数据");
			return;
		}
		Map<String, Integer> countAll = new HashMap<String, Integer>();
		Map<String, Integer> count30 = new HashMap<String, Integer>();
		Map<String, Integer> count50 = new HashMap<String, Integer>();
		Map<String, Integer> count100 = new HashMap<String, Integer>();
		List<AnalyseFilter> analyseFilters = getAnalyseFilters(ns);
		Long lastPeriodId = null;
		for (PeriodData data : list) {
			lastPeriodId = data.getPeriodId();
			for (AnalyseFilter filter : analyseFilters) {
				filter.checkResult(data.getResult());
			}
		}
		for (AnalyseFilter filter : analyseFilters) {
			countAll.putAll(filter.getCountAll());
			count30.putAll(filter.getCount30());
			count100.putAll(filter.getCount100());
			count50.putAll(filter.getCount50());
		}
		Period period = periodEntityManger.getPeriod(lastPeriodId);
		if (ns == null) {
			ns = new NumberAnalyse();
			ns.setLottery(getLottery());
		}
		ns.setLastPeriodId(lastPeriodId);
		ns.setLastPeriodNumber(period.getPeriodNumber());
		ns.setContent(JSONObject.fromObject(countAll).toString());
		ns.setContentFor30(JSONObject.fromObject(count30).toString());
		ns.setContentFor50(JSONObject.fromObject(count50).toString());
		ns.setContentFor100(JSONObject.fromObject(count100).toString());
		getMissDataManager().saveNumberAnalyse(ns);
	}

	/**
	 * 统计组合号码的出现次数 用于3D排三,以后的11选5重载此方法写吧
	 */
	public void updateGroupNumMiss() {
		logger.info("开始更新组合号码遗漏");
		GroupNumMiss ns = getMissDataManager().getGroupNumMiss(getLottery());

		DetachedCriteria criteria = DetachedCriteria.forClass(periodDataClazz);
		criteria.add(Restrictions.isNotNull("result"));
		if (ns != null) {
			criteria.add(Restrictions.gt("periodId", ns.getLastPeriodId()));
		}
		criteria.addOrder(Order.desc("periodId"));
		criteria.addOrder(Order.desc("id"));
		List<PeriodData> list = getPeriodDataEntityManager().findByDetachedCriteria(criteria);
		if (list == null || list.isEmpty()) {
			logger.info("无开奖数据");
			return;
		}
		Map<String, Integer> countG3 = new HashMap<String, Integer>();
		Map<String, Integer> countG6 = new HashMap<String, Integer>();
		Map<String, Integer> countMaxG3 = new HashMap<String, Integer>();
		Map<String, Integer> countMaxG6 = new HashMap<String, Integer>();
		if (ns != null) {
			countG3 = ns.getG3MissDataMap();
			countG6 = ns.getG6MissDataMap();
			countMaxG3 = ns.getG3MaxMissDataMap();
			countMaxG6 = ns.getG6MaxMissDataMap();
		} else {// 第一次统计,组合G3,G6所有号码
			for (int x = 0; x <= 9; x++) {
				// 组6
				for (int y = 0; y <= 9; y++) {
					for (int z = 0; z <= 9; z++) {
						if(x!=y&&y!=z&&x!=z)
						countG6.put(x + "" + y + z, 0);
						countMaxG6.put(x + "" + y + z, 0);
					}
				}
				// 组三
				for (int y = 0; y <= 9; y++) {
					for (int z = 0; z <= 9; z++) {
						if(x==y||y==z||x==z)
						countG3.put(x + "" + y + z, 0);
						countMaxG3.put(x + "" + y + z, 0);
					}
				}
			}

		}
		Long lastPeriodId = null;
		for (PeriodData data : list) {
			lastPeriodId = data.getPeriodId();
			String[] arr = data.getResult().split(",");
			Arrays.sort(arr);
			String code = arr[0] + arr[1] + arr[2];
			if (countG6.get(code) != null) {
				countG6.put(code, countG6.get(code) + 1);
				if (countG6.get(code) > countMaxG6.get(code)) {
					countMaxG6.put(code, countG6.get(code));
				}
			} else if (countG3.get(code) != null) {
				countG3.put(code, countG3.get(code) + 1);
				if (countG3.get(code) > countMaxG3.get(code)) {
					countMaxG3.put(code, countG3.get(code));
				}
			}
		}

		Period period = periodEntityManger.getPeriod(lastPeriodId);
		if (ns == null) {
			ns = new GroupNumMiss();
			ns.setLottery(getLottery());
		}
		ns.setLastPeriodId(lastPeriodId);
		ns.setLastPeriodNumber(period.getPeriodNumber());
		ns.setG3MaxMissContent(JSONObject.fromObject(countMaxG3).toString());
		ns.setG6MaxMissContent(JSONObject.fromObject(countMaxG6).toString());
		ns.setG3Content(JSONObject.fromObject(countG3).toString());
		ns.setG6Content(JSONObject.fromObject(countG6).toString());
		ns.setG3MaxMissContent(JSONObject.fromObject(countMaxG3).toString());
		ns.setG6MaxMissContent(JSONObject.fromObject(countMaxG6).toString());
		getMissDataManager().saveGroupNumMiss(ns);
	}

	/**
	 * 获取遗漏项检查过滤器
	 * 
	 * @param ns
	 * @return
	 */
	public abstract List<AnalyseFilter> getAnalyseFilters(NumberAnalyse ns);
	
	public void makeShuNewResult() throws DataException{
		//子类实现,生成右边栏
	}
	
	/**
	 * 生成静态文件工具类
	 * @param contents
	 * @param sourcefile
	 * @param destinationFileName
	 * @throws DataException
	 */
	public void createRightFile(Map<String,Object> contents,String sourcefile,String destinationFileName,Period period)throws DataException{
		try {
			TemplateGenerator tg=new TemplateGenerator(Constant.ROOTPATH+"/WEB-INF/content/"+period.getLotteryType().getKey());
			tg.create(sourcefile, contents, destinationFileName, Constant.ROOTPATH+"/WEB-INF/content/"+period.getLotteryType().getKey());

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("生成静态文件失败");
		}
	}
}
