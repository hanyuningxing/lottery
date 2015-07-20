package com.cai310.lottery.service.info.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SalesMode;
import com.cai310.lottery.dao.info.AdImagesDao;
import com.cai310.lottery.entity.info.AdImages;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.PeriodData;
import com.cai310.lottery.entity.lottery.PeriodSales;
import com.cai310.lottery.entity.lottery.Subscription;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.entity.lottery.dlt.DltPeriodData;
import com.cai310.lottery.entity.lottery.pl.PlPeriodData;
import com.cai310.lottery.entity.lottery.seven.SevenPeriodData;
import com.cai310.lottery.entity.lottery.sevenstar.SevenstarPeriodData;
import com.cai310.lottery.entity.lottery.ssq.SsqPeriodData;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5PeriodData;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7PeriodData;
import com.cai310.lottery.entity.lottery.welfare3d.Welfare3dPeriodData;
import com.cai310.lottery.entity.lottery.zc.LczcPeriodData;
import com.cai310.lottery.entity.lottery.zc.SczcPeriodData;
import com.cai310.lottery.entity.lottery.zc.SfzcPeriodData;
import com.cai310.lottery.entity.lottery.zc.ZcMatch;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.info.IndexInfoDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcMatchEntityManager;
import com.cai310.lottery.service.lottery.dlt.impl.DltPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.pl.impl.PlPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.seven.impl.SevenPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.sevenstar.impl.SevenstarPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.ssq.impl.SsqPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.tc22to5.impl.Tc22to5PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare36to7.impl.Welfare36to7PeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.welfare3d.impl.Welfare3dPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.LczcMatchEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.LczcPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SczcMatchEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SczcPeriodDataEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SfzcMatchEntityManagerImpl;
import com.cai310.lottery.service.lottery.zc.impl.SfzcPeriodDataEntityManagerImpl;
import com.cai310.utils.TemplateGenerator;
import com.google.common.collect.Maps;

@Service("indexInfoDataEntityManager")
@Transactional
public class IndexInfoDataEntityManagerImpl implements IndexInfoDataEntityManager {
	@Autowired
	protected DczcMatchEntityManager dczcMatchEntityManager;

	@Autowired
	protected LczcMatchEntityManagerImpl lczcMatchEntityManagerImpl;

	@Autowired
	protected SczcMatchEntityManagerImpl sczcMatchEntityManagerImpl;

	@Autowired
	protected SfzcMatchEntityManagerImpl sfzcMatchEntityManagerImpl;

	// /dlt
	@Resource
	private DltPeriodDataEntityManagerImpl dltPeriodDataEntityManagerImpl;

	// /双色球
	@Resource
	private SsqPeriodDataEntityManagerImpl ssqPeriodDataEntityManagerImpl;
	// /排列
	@Resource
	private PlPeriodDataEntityManagerImpl plPeriodDataEntityManagerImpl;
	// /3D
	@Resource
	private Welfare3dPeriodDataEntityManagerImpl welfare3dPeriodDataEntityManagerImpl;
	// /Seven
	@Resource
	private SevenPeriodDataEntityManagerImpl sevenPeriodDataEntityManagerImpl;
	
	// /225
	@Resource
	private Tc22to5PeriodDataEntityManagerImpl tc22to5PeriodDataEntityManagerImpl;
	
	// /Sevenstar
	@Resource
	private SevenstarPeriodDataEntityManagerImpl sevenstarPeriodDataEntityManagerImpl;	

	// 好彩1
	@Resource
	private Welfare36to7PeriodDataEntityManagerImpl welfare36to7PeriodDataEntityManagerImpl;
	// /四场
	@Resource
	private SczcPeriodDataEntityManagerImpl sczcPeriodDataEntityManagerImpl;

	// /六场
	@Resource
	private LczcPeriodDataEntityManagerImpl lczcPeriodDataEntityManagerImpl;

	// /14,9场
	@Resource
	private SfzcPeriodDataEntityManagerImpl sfzcPeriodDataEntityManagerImpl;

	@Autowired
	protected PeriodEntityManager periodManager;

	@Autowired
	protected QueryService queryService;

	@Autowired
	protected AdImagesDao adImagesDao;
	
	@SuppressWarnings("rawtypes")
	private Map<Lottery, SchemeEntityManager> schemeEntityManagerMap = Maps.newHashMap();
	@SuppressWarnings("rawtypes")
	private SchemeEntityManager getSchemeEntityManager(Lottery lotteryType) {
		return schemeEntityManagerMap.get(lotteryType);
	}
	@SuppressWarnings("rawtypes")
	@Autowired
	public void setSchemeEntityManagerList(List<SchemeEntityManager> schemeEntityManagerList) {
		for (SchemeEntityManager manager : schemeEntityManagerList) {
			schemeEntityManagerMap.put(manager.getLottery(), manager);
		}
	}
	/**
	 * 上传广告图
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void uploadAdPic(AdImages adImages) throws DataException {
		DetachedCriteria criteria = DetachedCriteria.forClass(AdImages.class);
		criteria.add(Restrictions.eq("pos", adImages.getPos()));
		List<AdImages> adImagesList = queryService.findByDetachedCriteria(criteria, 0, 1);
		if (adImagesList == null || adImagesList.isEmpty()) {
			adImagesDao.save(adImages);
		} else {
			AdImages adImagesTemp = adImagesList.get(0);
			adImagesTemp.setName(adImages.getName());
			adImagesTemp.setLink(adImages.getLink());
			adImagesTemp.setWord(adImages.getWord());
			adImagesDao.save(adImagesTemp);
		}
	}
	
	/**
	 * 删除广告图
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void deleteAdPic(Long id) throws DataException {
		AdImages adImages = adImagesDao.get(id);
		if(null==adImages) throw new DataException("找不到广告图");
		adImagesDao.delete(adImages);
	}

	/**
	 * 生成足彩对阵
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void makeZcMatch(Period period) throws DataException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Period.class);
		criteria.add(Restrictions.eq("current", true));
		criteria.add(Restrictions.eq("lotteryType", period.getLotteryType()));
		criteria.addOrder(Order.desc("id"));
		List<Period> periodList = queryService.findByDetachedCriteria(criteria, 0, 1);
		if (periodList == null || periodList.isEmpty()) {
			return;
		}
		period = periodList.get(0);
		ZcMatch[] matchArr = null;
		List<DczcMatch> dczcMatchList = null;
		switch (period.getLotteryType()) {
		case DCZC:
			dczcMatchList = dczcMatchEntityManager.findMatchs(period.getId());
			break;
		case LCZC:
			matchArr = lczcMatchEntityManagerImpl.findMatchs(period.getId());
			break;
		case SFZC:
			matchArr = sfzcMatchEntityManagerImpl.findMatchs(period.getId());
			break;
		case SCZC:
			matchArr = sczcMatchEntityManagerImpl.findMatchs(period.getId());
			break;
		}
		Date singleEndTime = null, compoundEndTime = null;
		List<PeriodSales> salesList = periodManager.findPeriodSales(period.getId());
		for (PeriodSales sale : salesList) {
			if (sale.getId().getSalesMode() == SalesMode.SINGLE) {
				singleEndTime = sale.getShareEndInitTime();
			} else {
				compoundEndTime = sale.getShareEndInitTime();
			}
		}

		Map<String, Object> contents = new HashMap<String, Object>();
		contents.put("dczcMatchList", dczcMatchList);
		contents.put("matchArr", matchArr);
		contents.put("singleEndTime", singleEndTime);
		contents.put("compoundEndTime", compoundEndTime);
		contents.put("period", period);
		contents.put("base", Constant.BASEPATH);
		createFile(contents, "match.ftl", period.getLotteryType().getKey() + ".html");
	}
	public void makeShuTopWon(Lottery lottery) throws DataException {
		List<Subscription> list = this.getSchemeEntityManager(lottery).getTopWon(lottery);
		Map<String, Object> contents = new HashMap<String, Object>();
		contents.put("topwon", list);
    	String dir = "/js/analyse/" + lottery.getKey() + "/";
		String fileName = "topwon.js";
		JsonConfig jsonConfig = new JsonConfig();  
		jsonConfig.setExcludes(new String[] {"id", "canCancel","lotteryType","platform", "prepaymentId","lastModifyTime","createTime","version", "bonusSended","way","state", "cost","schemeId"});  
		String value =JSONObject.fromObject(contents,jsonConfig).toString();
		com.cai310.utils.WriteHTMLUtil.writeHtm(dir, fileName, value, "UTF-8");
	}
	public void makeShuNewResult() throws DataException {
		Map<String, Object> contents = new HashMap<String, Object>();
		contents.put("base", Constant.BASEPATH);
		// /双色球
		SsqPeriodData ssqPeriodData = ssqPeriodDataEntityManagerImpl.getNewestResultPeriodData();
		Period period;
		if (null != ssqPeriodData && StringUtils.isNotBlank(ssqPeriodData.getResult())) {
			period = periodManager.getPeriod(ssqPeriodData.getPeriodId());
			if (null != period) {
				contents.put("ssqPeriod", period);
				contents.put("ssqPeriodData", ssqPeriodData);
				makeNewPeriodData(period,ssqPeriodData);
			}
		}
		// ///大乐透
		DltPeriodData dltPeriodData = dltPeriodDataEntityManagerImpl.getNewestResultPeriodData();
		if (null != dltPeriodData && StringUtils.isNotBlank(dltPeriodData.getResult())) {
			period = periodManager.getPeriod(dltPeriodData.getPeriodId());
			if (null != period) {
				contents.put("dltPeriod", period);
				contents.put("dltPeriodData", dltPeriodData);
				makeNewPeriodData(period,dltPeriodData);
			}
		}
		// /排列
		PlPeriodData plPeriodData = plPeriodDataEntityManagerImpl.getNewestResultPeriodData();
		if (null != plPeriodData && StringUtils.isNotBlank(plPeriodData.getResult())) {
			period = periodManager.getPeriod(plPeriodData.getPeriodId());
			if (null != period) {
				contents.put("plPeriod", period);
				contents.put("plPeriodData", plPeriodData);
				makeNewPeriodData(period,plPeriodData);
			}
		}
		// 3d
		Welfare3dPeriodData welfare3dPeriodData = welfare3dPeriodDataEntityManagerImpl.getNewestResultPeriodData();
		if (null != welfare3dPeriodData && StringUtils.isNotBlank(welfare3dPeriodData.getResult())) {
			period = periodManager.getPeriod(welfare3dPeriodData.getPeriodId());
			if (null != period) {
				contents.put("welfare3dPeriod", period);
				contents.put("welfare3dPeriodData", welfare3dPeriodData);
				makeNewPeriodData(period,welfare3dPeriodData);
			}
		}
		//7乐彩
		SevenPeriodData sevenPeriodData = sevenPeriodDataEntityManagerImpl.getNewestResultPeriodData();
		if (null != sevenPeriodData && StringUtils.isNotBlank(sevenPeriodData.getResult())) {
			period = periodManager.getPeriod(sevenPeriodData.getPeriodId());
			if (null != period) {
				contents.put("sevenPeriod", period);
				contents.put("sevenPeriodData", sevenPeriodData);
				makeNewPeriodData(period,sevenPeriodData);
			}
		}
		//22to5
		Tc22to5PeriodData tc22to5PeriodData = tc22to5PeriodDataEntityManagerImpl.getNewestResultPeriodData();
		if (null != tc22to5PeriodData && StringUtils.isNotBlank(tc22to5PeriodData.getResult())) {
			period = periodManager.getPeriod(tc22to5PeriodData.getPeriodId());
			if (null != period) {
				contents.put("tc22to5Period", period);
				contents.put("tc22to5PeriodData", tc22to5PeriodData);
				makeNewPeriodData(period,tc22to5PeriodData);
			}
		}
		//Sevenstar
		SevenstarPeriodData sevenstarPeriodData = sevenstarPeriodDataEntityManagerImpl.getNewestResultPeriodData();
				if (null != sevenstarPeriodData && StringUtils.isNotBlank(sevenstarPeriodData.getResult())) {
					period = periodManager.getPeriod(sevenstarPeriodData.getPeriodId());
					if (null != period) {
						contents.put("sevenstarPeriod", period);
						contents.put("sevenstarPeriodData", sevenstarPeriodData);
						makeNewPeriodData(period,sevenstarPeriodData);
					}
				}
		
		//好彩1
		Welfare36to7PeriodData welfare36to7PeriodData = welfare36to7PeriodDataEntityManagerImpl.getNewestResultPeriodData();
		if (null != welfare36to7PeriodData && StringUtils.isNotBlank(welfare36to7PeriodData.getResult())) {
			period = periodManager.getPeriod(welfare36to7PeriodData.getPeriodId());
			if (null != period) {
				contents.put("welfare36to7Period", period);
				contents.put("welfare36to7PeriodData", welfare36to7PeriodData);
				makeNewPeriodData(period,welfare36to7PeriodData);
			}
		}
		
		createFile(contents, "new-result-s.ftl", "sc-result.html");
		// /生成开奖首页
		createResultFile(contents, "new-result-s.ftl", "sc-result.html");
	}
    public void makeNewPeriodData(Period period,PeriodData periodData){
    	Map<String, Object> contents = new HashMap<String, Object>();
		contents.put("base", Constant.BASEPATH);
		contents.put("period", period);
		contents.put("periodData", periodData);
    	String dir = "/html/js/data/" + period.getLotteryType().getKey() + "/";
		String fileName = "period_data.js";
		JsonConfig jsonConfig = new JsonConfig();  
		jsonConfig.setExcludes(new String[] { "winName"});  
		String value =JSONObject.fromObject(contents,jsonConfig).toString();
		com.cai310.utils.WriteHTMLUtil.writeHtm(dir, fileName, value, "UTF-8");
    }
	public void makeZcNewResult() throws DataException {
		Map<String, Object> contents = new HashMap<String, Object>();
		contents.put("base", Constant.BASEPATH);
		SfzcPeriodData sfzcPeriodData = sfzcPeriodDataEntityManagerImpl.getNewestResultPeriodData();
		Period period;
		if (null != sfzcPeriodData && StringUtils.isNotBlank(sfzcPeriodData.getResult())) {
			period = periodManager.getPeriod(sfzcPeriodData.getPeriodId());
			contents.put("sfzcPeriod", period);
			contents.put("sfzcPeriodData", sfzcPeriodData);
			makeNewPeriodData(period,sfzcPeriodData);
		}
		LczcPeriodData lczcPeriodData = lczcPeriodDataEntityManagerImpl.getNewestResultPeriodData();
		if (null != lczcPeriodData && StringUtils.isNotBlank(lczcPeriodData.getResult())) {
			period = periodManager.getPeriod(lczcPeriodData.getPeriodId());
			contents.put("lczcPeriod", period);
			contents.put("lczcPeriodData", lczcPeriodData);
			makeNewPeriodData(period,lczcPeriodData);
		}
		SczcPeriodData sczcPeriodData = sczcPeriodDataEntityManagerImpl.getNewestResultPeriodData();
		if (null != sczcPeriodData && StringUtils.isNotBlank(sczcPeriodData.getResult())) {
			period = periodManager.getPeriod(sczcPeriodData.getPeriodId());
			contents.put("sczcPeriod", period);
			contents.put("sczcPeriodData", sczcPeriodData);
			makeNewPeriodData(period,sczcPeriodData);
		}
		createFile(contents, "new-result.ftl", "zc-result.html");
		createResultFile(contents, "new-result.ftl", "zc-result.html");
	}

	/**
	 * 生成静态文件工具类
	 * 
	 * @param contents
	 * @param sourcefile
	 * @param destinationFileName
	 * @throws DataException
	 */
	public void createFile(Map<String, Object> contents, String sourcefile, String destinationFileName)
			throws DataException {
		try {
			TemplateGenerator tg = new TemplateGenerator(Constant.ROOTPATH + "/WEB-INF/content/admin/info");
			tg.create(sourcefile, contents, destinationFileName, Constant.ROOTPATH + "/html/info");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("生成静态文件失败");
		}
	}

	public void createResultFile(Map<String, Object> contents, String sourcefile, String destinationFileName)
			throws DataException {
		try {
			TemplateGenerator tg = new TemplateGenerator(Constant.ROOTPATH + "/WEB-INF/content/info/results");
			tg.create(sourcefile, contents, destinationFileName, Constant.ROOTPATH + "/html/info/results");

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("生成静态文件失败");
		}
	}

	/**
	 * 生成静态文件工具类
	 * 
	 * @param contents
	 * @param sourcefile
	 * @param destinationFileName
	 * @throws DataException
	 */
	public void createRightFile(Map<String, Object> contents, String sourcefile, String destinationFileName,
			Period period) throws DataException {
		try {
			TemplateGenerator tg = new TemplateGenerator(Constant.ROOTPATH + "/WEB-INF/content/"
					+ period.getLotteryType().getKey());
			tg.create(sourcefile, contents, destinationFileName, Constant.ROOTPATH + "/WEB-INF/content/"
					+ period.getLotteryType().getKey());

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("生成静态文件失败");
		}
	}
}
