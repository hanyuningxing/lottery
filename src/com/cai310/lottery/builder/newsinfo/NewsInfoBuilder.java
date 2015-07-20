package com.cai310.lottery.builder.newsinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Constant;
import com.cai310.lottery.builder.AbstractInfoBuilder;
import com.cai310.lottery.builder.DefaultInfoBuilderCreateConfig;
import com.cai310.lottery.builder.DefaultInfoBuilderQueryForm;
import com.cai310.lottery.common.InfoState;
import com.cai310.lottery.common.InfoSubType;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.info.NewsInfoDataEntityManager;

/**
 * Description: 新闻信息静态化工具类<br>
 * Copyright: Copyright (c) 2011<br>
 * Company: 肇庆优盛科技
 * 
 * @author zhuhui 2011-03-16 编写
 * @version 1.0
 */
public class NewsInfoBuilder extends AbstractInfoBuilder {
	//
	@Autowired
	protected QueryService queryService;
	/** 新闻 **/
	@Resource
	private NewsInfoDataEntityManager newsInfoDataEntityManager;

	// 首页栏目查询器
	private DefaultInfoBuilderQueryForm indexPartQueryForm;
	// 首页技巧新闻查询器
	private DefaultInfoBuilderQueryForm indexSkillsQueryForm;
	// 北京单场——单场足彩首页新闻查询器
	private DefaultInfoBuilderQueryForm dczcNewsInfoQueryForm;
	// 竞彩足球——单场足彩首页新闻查询器
	private DefaultInfoBuilderQueryForm jcNewsInfoQueryForm;


	public DefaultInfoBuilderQueryForm getJcNewsInfoQueryForm() {
		return jcNewsInfoQueryForm;
	}

	public void setJcNewsInfoQueryForm(DefaultInfoBuilderQueryForm jcNewsInfoQueryForm) {
		this.jcNewsInfoQueryForm = jcNewsInfoQueryForm;
	}

	public DefaultInfoBuilderCreateConfig getJcNewsinfoCreateConfig() {
		return jcNewsinfoCreateConfig;
	}

	public void setJcNewsinfoCreateConfig(DefaultInfoBuilderCreateConfig jcNewsinfoCreateConfig) {
		this.jcNewsinfoCreateConfig = jcNewsinfoCreateConfig;
	}

	// 首页栏目配置
	private DefaultInfoBuilderCreateConfig indexPartCreateConfig;
	// 首页投注技巧配置
	private DefaultInfoBuilderCreateConfig indexSkillsCreateConfig;
	// 北京单场——单场足彩首页新闻配置
	private DefaultInfoBuilderCreateConfig dczcNewsinfoCreateConfig;
	// 竞彩足球——单场足彩首页新闻配置
	private DefaultInfoBuilderCreateConfig jcNewsinfoCreateConfig;
	// 竞彩篮球——单场足彩首页新闻配置
	

	/**
	 * 
	 * 创建首页栏目HTML
	 *
	 */
	public void createIndexNewsFile(Lottery lottery) throws DataException {
		Map<String, Object> indexContents = new HashMap<String, Object>();
		indexContents.put("base", Constant.BASEPATH);
		indexContents.put("lottery", lottery);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsInfoData.class);
		if (null != lottery) {
			criteria.add(Restrictions.eq("lotteryType", lottery));
		}
		//预测
		criteria.add(Restrictions.eq("type", InfoType.FORECAST));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.add(Restrictions.eq("isNotice", NewsInfoData.LotteryNOTICE));
		// 处理排序
		for (Order order : indexPartQueryForm.getOrders()) {
			criteria.addOrder(order);
		}
		
		//zhuhui mofity by 2011-08-31 业务：首页彩种栏目 显示10条：4条预测+6条其他 首页栏目公告
		List<NewsInfoData> dataList = new ArrayList<NewsInfoData>(10);
		
		List<NewsInfoData> forecastList = queryService.findByDetachedCriteria(criteria, 0,4);
		
		
		DetachedCriteria criteria2 = DetachedCriteria.forClass(NewsInfoData.class);
		if (null != lottery) {
			criteria2.add(Restrictions.eq("lotteryType", lottery));
		}
		//非预测
		criteria2.add(Restrictions.ne("type", InfoType.FORECAST));
		criteria2.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria2.add(Restrictions.eq("isNotice", NewsInfoData.LotteryNOTICE));
		// 处理排序
		for (Order order : indexPartQueryForm.getOrders()) {
			criteria2.addOrder(order);
		}
		
		List<NewsInfoData> notForecastList = queryService.findByDetachedCriteria(criteria2, 0,6);
		
		dataList.addAll(forecastList);
		dataList.addAll(notForecastList);
		
		indexContents.put("newsIndexNeedList", dataList);
		// 模版路径
		String templatePath = indexPartCreateConfig.getTemplatePath();
		// 首页信息
		String indexTemplateName = indexPartCreateConfig.getTemplateName();
		String indexTargetFilePath = indexPartCreateConfig.getTargetFilePath();
		String indexTargetFileName = ((lottery == null) ? "" : lottery) + ".html".toLowerCase();
		super.createNewsFile(indexContents, templatePath, indexTemplateName, indexTargetFilePath, indexTargetFileName);
	}

	/**
	 * 
	 * 创建首页 彩票学院-新手成长之路 HTML
	 *
	 */
	public void createIndexSkillsFile() throws DataException {
		Map<String, Object> indexContents = new HashMap<String, Object>();
		indexContents.put("base", Constant.BASEPATH);
		List<NewsInfoData> DLTList = newsInfoDataEntityManager.getNewsInfoDataBy(InfoType.SKILLS, Lottery.DLT,
				indexSkillsQueryForm.getNeedNum(), true);
		List<NewsInfoData> SSQList =  newsInfoDataEntityManager.getNewsInfoDataBy(InfoType.SKILLS, Lottery.SSQ,
				indexSkillsQueryForm.getNeedNum(), true);;
		List<NewsInfoData> WELFARE3DList =  newsInfoDataEntityManager.getNewsInfoDataBy(InfoType.SKILLS, Lottery.WELFARE3D,
				indexSkillsQueryForm.getNeedNum(), true);;
		List<NewsInfoData> QYHList =newsInfoDataEntityManager.getNewsInfoDataBy(InfoType.SKILLS, Lottery.QYH,
				indexSkillsQueryForm.getNeedNum(), true);;

		List<Lottery> lottery = new ArrayList<Lottery>();
		lottery.add(Lottery.SSQ);
		lottery.add(Lottery.DLT);
		lottery.add(Lottery.WELFARE3D);
		lottery.add(Lottery.QYH);

		indexContents.put("dltList", DLTList);
		indexContents.put("ssqList", SSQList);
		indexContents.put("welfare3dList", WELFARE3DList);
		indexContents.put("qyhList", QYHList);

		indexContents.put("lotteryList", lottery);

		// 模版路径
		String templatePath = indexSkillsCreateConfig.getTemplatePath();
		// 首页信息
		String templateName = indexSkillsCreateConfig.getTemplateName();
		String targetFilePath = indexSkillsCreateConfig.getTargetFilePath();
		String targetFileName = indexSkillsCreateConfig.getTargetFileName();
		super.createNewsFile(indexContents, templatePath, templateName, targetFilePath, targetFileName);
	}

	
	
	/**
	 * 
	 * 创建单场足彩 首页面HTML
	 *
	 */
	public void createDCZCFile(InfoType infoType,InfoSubType infoSubType) throws DataException {
		//创建最新咨询
		if(infoType.equals(InfoType.INFO)){
			createDCZCFileByInfoType(InfoType.INFO, infoSubType);
		}
		//创建最新预测
		if(infoType.equals(InfoType.FORECAST)){
			createDCZCFileByInfoType(InfoType.FORECAST, infoSubType);
		}
		
	}
	
	/**
	 * 
	 * 创建竞彩足球首页面HTML
	 *
	 */
	public void createJCFile(Lottery lottery ,InfoType infoType,InfoSubType infoSubType) throws DataException {
		//创建最新咨询
		if(infoType.equals(InfoType.INFO)){
			createJCFileByInfoType(lottery,InfoType.INFO, infoSubType);
		}
		//创建最新预测
		if(infoType.equals(InfoType.FORECAST)){
			createJCFileByInfoType(lottery,InfoType.FORECAST, infoSubType);
		}
		
	}
	
	
	
    //获取最新单场足彩新闻信息
	@SuppressWarnings("unused")
	private void createDCZCFileByInfoType(InfoType infoType,InfoSubType infoSubType) throws DataException  {
		Map<String, Object> indexContents = new HashMap<String, Object>();
		indexContents.put("base", Constant.BASEPATH);
		indexContents.put("lottery", Lottery.DCZC);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsInfoData.class);
		criteria.add(Restrictions.eq("lotteryType", Lottery.DCZC));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		if(null!=infoType) {
			criteria.add(Restrictions.eq("type",infoType));
		}
		if(null!=infoSubType){
			criteria.add(Restrictions.eq("subType", infoSubType));
		}
		// 处理排序
		for (Order order : dczcNewsInfoQueryForm.getOrders()) {
			criteria.addOrder(order);
		}		
		List<NewsInfoData> newsInfoDataList = queryService.findByDetachedCriteria(criteria, 0, dczcNewsInfoQueryForm.getNeedNum());
		indexContents.put("newsInfoDataList", newsInfoDataList); 
		
		// 模版路径
		String templatePath = dczcNewsinfoCreateConfig.getTemplatePath();
		// 首页信息
		String indexTemplateName = dczcNewsinfoCreateConfig.getTemplateName();
		String indexTargetFilePath = dczcNewsinfoCreateConfig.getTargetFilePath();
		//生成 HTML名称为 InfoType—InfoSubType
		String indexTargetFileName = (infoType == null ? "" : infoType) + (infoSubType == null ? "" : "-"+infoSubType)+".html".toLowerCase();
		super.createNewsFile(indexContents, templatePath, indexTemplateName, indexTargetFilePath, indexTargetFileName);

	}
	
	  //获取最新竞彩足球新闻信息
	@SuppressWarnings("unused")
	private void createJCFileByInfoType(Lottery lottery,InfoType infoType,InfoSubType infoSubType) throws DataException  {
		Map<String, Object> indexContents = new HashMap<String, Object>();
		indexContents.put("base", Constant.BASEPATH);
		indexContents.put("lottery", lottery);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsInfoData.class);
		criteria.add(Restrictions.eq("lotteryType", lottery));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		if(null!=infoType) {
			criteria.add(Restrictions.eq("type",infoType));
		}
		if(null!=infoSubType){
			criteria.add(Restrictions.eq("subType", infoSubType));
		}
		// 处理排序
		for (Order order : jcNewsInfoQueryForm.getOrders()) {
			criteria.addOrder(order);
		}		
		List<NewsInfoData> newsInfoDataList = queryService.findByDetachedCriteria(criteria, 0, jcNewsInfoQueryForm.getNeedNum());

		indexContents.put("newsInfoDataList", newsInfoDataList); 
		// 模版路径
		String templatePath = jcNewsinfoCreateConfig.getTemplatePath();
		// 首页信息
		String indexTemplateName = jcNewsinfoCreateConfig.getTemplateName();
		String indexTargetFilePath = jcNewsinfoCreateConfig.getTargetFilePath();
		//生成 HTML名称为 lottery-InfoType—InfoSubType  
		String indexTargetFileName =  (lottery == null ? "" : lottery.getKey()+"_") +(infoType == null ? "" : infoType) + (infoSubType == null ? "" : "-"+infoSubType)+".html".toLowerCase();
		super.createNewsFile(indexContents, templatePath, indexTemplateName, indexTargetFilePath, indexTargetFileName);

	}
	
	
	/**
	 * 
	 * 创建专题活动页面HTML
	 *
	 */
	public void createIndexTopicsFile(InfoType infoType,InfoSubType infoSubType) throws DataException {
		Map<String, Object> indexContents = new HashMap<String, Object>();
		indexContents.put("base", Constant.BASEPATH);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsInfoData.class);
		criteria.add(Restrictions.eq("type", infoType));
		criteria.add(Restrictions.eq("subType", infoSubType));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("createTime"));

		List<NewsInfoData> newsInfoDataList = queryService.findByDetachedCriteria(criteria, 0, 10);
		indexContents.put("newsInfoDataList", newsInfoDataList); 
		
		// 模版路径
		String templatePath = "/WEB-INF/content/info/tags";
		// 首页信息
		String indexTemplateName = "index-topic.ftl";
		String indexTargetFilePath = "/html/info/news/topic";
		//生成 HTML名称为 InfoType—InfoSubType
		String indexTargetFileName = (infoType == null ? "" : infoType) + (infoSubType == null ? "" : "-"+infoSubType)+".html".toLowerCase();
		super.createNewsFile(indexContents, templatePath, indexTemplateName, indexTargetFilePath, indexTargetFileName);
		
	}
	
	public DefaultInfoBuilderQueryForm getIndexSkillsQueryForm() {
		return indexSkillsQueryForm;
	}

	public void setIndexSkillsQueryForm(DefaultInfoBuilderQueryForm indexSkillsQueryForm) {
		this.indexSkillsQueryForm = indexSkillsQueryForm;
	}

	public DefaultInfoBuilderCreateConfig getIndexSkillsCreateConfig() {
		return indexSkillsCreateConfig;
	}

	public void setIndexSkillsCreateConfig(DefaultInfoBuilderCreateConfig indexSkillsCreateConfig) {
		this.indexSkillsCreateConfig = indexSkillsCreateConfig;
	}
	

	public DefaultInfoBuilderQueryForm getDczcNewsInfoQueryForm() {
		return dczcNewsInfoQueryForm;
	}

	public void setDczcNewsInfoQueryForm(DefaultInfoBuilderQueryForm dczcNewsInfoQueryForm) {
		this.dczcNewsInfoQueryForm = dczcNewsInfoQueryForm;
	}

	public DefaultInfoBuilderCreateConfig getDczcNewsinfoCreateConfig() {
		return dczcNewsinfoCreateConfig;
	}

	public void setDczcNewsinfoCreateConfig(DefaultInfoBuilderCreateConfig dczcNewsinfoCreateConfig) {
		this.dczcNewsinfoCreateConfig = dczcNewsinfoCreateConfig;
	}

	public DefaultInfoBuilderQueryForm getIndexPartQueryForm() {
		return indexPartQueryForm;
	}

	public void setIndexPartQueryForm(DefaultInfoBuilderQueryForm indexPartQueryForm) {
		this.indexPartQueryForm = indexPartQueryForm;
	}

	public DefaultInfoBuilderCreateConfig getIndexPartCreateConfig() {
		return indexPartCreateConfig;
	}

	public void setIndexPartCreateConfig(DefaultInfoBuilderCreateConfig indexPartCreateConfig) {
		this.indexPartCreateConfig = indexPartCreateConfig;
	}

	
	/**
	 * 
	 * 创建标签页 HTML
	 *
	 */
	public void createIndexTagsFile(List datas,Lottery lottery) throws DataException {
		Map<String, Object> indexContents = new HashMap<String, Object>();
		indexContents.put("base", Constant.BASEPATH);

		indexContents.put("tagsList", datas);
		// 模版路径
		String templatePath = "/WEB-INF/content/info/tags";
		// 首页信息
		String templateName = "index-tags.ftl";
		String targetFilePath = "/html/info/tags";
		String targetFileName = lottery.getKey()+"-tags.html";
		super.createNewsFile(indexContents, templatePath, templateName, targetFilePath, targetFileName);
	}
	
	public void createActivityWinnerFile(List datas,Lottery lottery) throws DataException {
		Map<String, Object> indexContents = new HashMap<String, Object>();
		indexContents.put("base", Constant.BASEPATH);

		indexContents.put("activityWinnerList", datas);
		// 模版路径
		String templatePath = "/WEB-INF/content/info/tags";
		// 首页信息
		String templateName = "list-of-winner.ftl";
		String targetFilePath = "/html/info/tags";
		String targetFileName = lottery.getKey()+"-winner.html";
		super.createNewsFile(indexContents, templatePath, templateName, targetFilePath, targetFileName);
	}
	
}
