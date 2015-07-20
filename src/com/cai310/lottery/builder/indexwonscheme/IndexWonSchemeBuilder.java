package com.cai310.lottery.builder.indexwonscheme;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Constant;
import com.cai310.lottery.FetchConstant;
import com.cai310.lottery.builder.AbstractInfoBuilder;
import com.cai310.lottery.builder.DefaultInfoBuilderCreateConfig;
import com.cai310.lottery.builder.DefaultInfoBuilderQueryForm;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.SchemeState;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.lottery.dczc.DczcScheme;
import com.cai310.lottery.entity.lottery.jclq.JclqScheme;
import com.cai310.lottery.entity.lottery.jczq.JczqScheme;
import com.cai310.lottery.entity.lottery.keno.KenoSchemeInfo;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.QueryService;

/**
 * Description: 获奖信息静态化工具类<br>
 * Copyright: Copyright (c) 2011<br>
 * Company: 肇庆优盛科技
 * 
 * @author zhuhui 2011-03-16 编写
 * @version 1.0
 */
public class IndexWonSchemeBuilder extends AbstractInfoBuilder {
	//
	@Autowired
	protected QueryService queryService;
	// 查询器
	private DefaultInfoBuilderQueryForm indexWonSchemeBuilderQueryForm;

	// 生成器
	private DefaultInfoBuilderCreateConfig indexWonSchemeBuilderCreateConfig;
	
	// 北京单场——单场足彩首页最新中奖
	private DefaultInfoBuilderCreateConfig dczcNewWonSchemeCreateConfig;
	
	//竞彩首页最新中奖
	private DefaultInfoBuilderCreateConfig jcNewWonSchemeCreateConfig;



	/**
	 * 
	 * 创建最新获奖方案 首页面HTML
	 *
	 */
	public void createNewIndexFile() throws DataException {
		Map<String, Object> contents = new HashMap<String, Object>();
		contents.put("base", Constant.BASEPATH);
		DetachedCriteria criteria = DetachedCriteria.forClass(com.cai310.lottery.entity.lottery.Scheme.class);
		criteria.add(Restrictions.eq("won", true));
		criteria.add(Restrictions.eq("state", SchemeState.SUCCESS));

		// 处理排序
		for (Order order : indexWonSchemeBuilderQueryForm.getOrders()) {
			criteria.addOrder(order);
		}
		List<Scheme> wonSchemeList = queryService.findByDetachedCriteria(criteria, 0,indexWonSchemeBuilderQueryForm.getNeedNum());
		contents.put("wonSchemeList", wonSchemeList);
		
		String templatePath = indexWonSchemeBuilderCreateConfig.getTemplatePath();
		String templateName = indexWonSchemeBuilderCreateConfig.getTemplateName();
		String targetFilePath = indexWonSchemeBuilderCreateConfig.getTargetFilePath();
		String targetFileName = indexWonSchemeBuilderCreateConfig.getTargetFileName();
		super.createNewsFile(contents, templatePath, templateName, targetFilePath, targetFileName);
	}
	
	/**
	 * 
	 * 创建单场足彩  最新获奖方案 首页面HTML
	 *
	 */
	public void createNewWonDczcSchemeFile() throws DataException {
		Map<String, Object> contents = new HashMap<String, Object>();
		contents.put("base", Constant.BASEPATH);
		DetachedCriteria criteria = DetachedCriteria.forClass(DczcScheme.class);
		criteria.add(Restrictions.eq("won", true));
		criteria.add(Restrictions.eq("state", SchemeState.SUCCESS));

		// 处理排序
		for (Order order : indexWonSchemeBuilderQueryForm.getOrders()) {
			criteria.addOrder(order);
		}
		List<DczcScheme> wonSchemeList = queryService.findByDetachedCriteria(criteria, 0, 20);
		contents.put("wonSchemeList", wonSchemeList);
		String templatePath = dczcNewWonSchemeCreateConfig.getTemplatePath();
		String templateName = dczcNewWonSchemeCreateConfig.getTemplateName();
		String targetFilePath = dczcNewWonSchemeCreateConfig.getTargetFilePath();
		String targetFileName = dczcNewWonSchemeCreateConfig.getTargetFileName();
		super.createNewsFile(contents, templatePath, templateName, targetFilePath, targetFileName);
	}
	
	/**
	 * 
	 * 创建竞彩足彩  最新获奖方案 首页面HTML
	 *
	 */
	public void createNewWonJczqSchemeFile() throws DataException {
		Map<String, Object> contents = new HashMap<String, Object>();
		contents.put("base", Constant.BASEPATH);
		DetachedCriteria criteria = DetachedCriteria.forClass(JczqScheme.class);
		criteria.add(Restrictions.eq("won", true));
		criteria.add(Restrictions.eq("state", SchemeState.SUCCESS));

		// 处理排序
		for (Order order : indexWonSchemeBuilderQueryForm.getOrders()) {
			criteria.addOrder(order);
		}
		List<JczqScheme> wonSchemeList = queryService.findByDetachedCriteria(criteria, 0, 20);
		contents.put("wonSchemeList", wonSchemeList);
		String templatePath = jcNewWonSchemeCreateConfig.getTemplatePath();
		String templateName = jcNewWonSchemeCreateConfig.getTemplateName();
		String targetFilePath = jcNewWonSchemeCreateConfig.getTargetFilePath();
		String targetFileName = Lottery.JCZQ.getKey()+"_"+jcNewWonSchemeCreateConfig.getTargetFileName();
		super.createNewsFile(contents, templatePath, templateName, targetFilePath, targetFileName);
	}
	
	public void createNewWonJclqSchemeFile() throws DataException {
		Map<String, Object> contents = new HashMap<String, Object>();
		contents.put("base", Constant.BASEPATH);
		DetachedCriteria criteria = DetachedCriteria.forClass(JclqScheme.class);
		criteria.add(Restrictions.eq("won", true));
		criteria.add(Restrictions.eq("state", SchemeState.SUCCESS));

		// 处理排序
		for (Order order : indexWonSchemeBuilderQueryForm.getOrders()) {
			criteria.addOrder(order);
		}
		List<JczqScheme> wonSchemeList = queryService.findByDetachedCriteria(criteria, 0, 20);
		contents.put("wonSchemeList", wonSchemeList);
		String templatePath = jcNewWonSchemeCreateConfig.getTemplatePath();
		String templateName = jcNewWonSchemeCreateConfig.getTemplateName();
		String targetFilePath = jcNewWonSchemeCreateConfig.getTargetFilePath();
		String targetFileName = Lottery.JCLQ.getKey()+"_"+jcNewWonSchemeCreateConfig.getTargetFileName();
		super.createNewsFile(contents, templatePath, templateName, targetFilePath, targetFileName);
	}
	
	/**
	 * 
	 * 创建时时彩  最新获奖方案 首页面HTML
	 *
	 */
	public void createNewWonSscSchemeFile(List<KenoSchemeInfo> wonSchemeList) throws DataException {
			Map<String, Object> contents = new HashMap<String, Object>();;
			contents.put("base", Constant.BASEPATH);
			contents.put("wonSchemeList", wonSchemeList);
			String templatePath = "/WEB-INF/content/ssc";
			String templateName = "newwon-scheme.ftl";
			String targetFilePath = "/html/info/yhhd";
			String targetFileName = "sscWonScheme.html";
			super.createNewsFile(contents, templatePath, templateName, targetFilePath, targetFileName);
	}

	public DefaultInfoBuilderQueryForm getIndexWonSchemeBuilderQueryForm() {
		return indexWonSchemeBuilderQueryForm;
	}

	public void setIndexWonSchemeBuilderQueryForm(DefaultInfoBuilderQueryForm indexWonSchemeBuilderQueryForm) {
		this.indexWonSchemeBuilderQueryForm = indexWonSchemeBuilderQueryForm;
	}

	public DefaultInfoBuilderCreateConfig getIndexWonSchemeBuilderCreateConfig() {
		return indexWonSchemeBuilderCreateConfig;
	}

	public void setIndexWonSchemeBuilderCreateConfig(DefaultInfoBuilderCreateConfig indexWonSchemeBuilderCreateConfig) {
		this.indexWonSchemeBuilderCreateConfig = indexWonSchemeBuilderCreateConfig;
	}

	public DefaultInfoBuilderCreateConfig getDczcNewWonSchemeCreateConfig() {
		return dczcNewWonSchemeCreateConfig;
	}

	public void setDczcNewWonSchemeCreateConfig(DefaultInfoBuilderCreateConfig dczcNewWonSchemeCreateConfig) {
		this.dczcNewWonSchemeCreateConfig = dczcNewWonSchemeCreateConfig;
	}
	
 
	/**
	 * 生成XML文件
	 * 为车业务提供最新中奖信息
	 * @param data
	 * @throws IOException
	 * @throws JDOMException
	 */
	private void buildNewsXml(Map content){
		//创建根节点 root;
       Element root = new Element("root");
     
       //根节点添加到文档中；
       Document Doc = new Document(root);
       List wonSchemeList = (List)content.get("wonSchemeList");
        
       for(int i=0;i<wonSchemeList.size();i++){
    	   Element item = new Element("item");
    	   Scheme scheme = (Scheme)wonSchemeList.get(i);
    	   item.setAttribute("lotteryType", scheme.getLotteryType().getLotteryName());
    	   item.setAttribute("content","恭喜"+ scheme.getSponsorName()+" 喜中   奖金"+scheme.getPrize()+"元");
    	   root.addContent(item);
       }
	   XMLOutputter XMLOut = new XMLOutputter();
	   
	    try {
			XMLOut.output(Doc, new FileOutputStream(new File(Constant.ROOTPATH+FetchConstant.LAST_RESULT+"won.xml")));
			Log.info("【最新中奖信息生成XML文件成功】");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Log.info("【最新中奖信息生成XML文件失败】");
		} catch (IOException e) {
			e.printStackTrace();
			Log.info("【最新中奖信息生成XML文件失败】");
		}       
	}
	
	


	public DefaultInfoBuilderCreateConfig getJcNewWonSchemeCreateConfig() {
		return jcNewWonSchemeCreateConfig;
	}

	public void setJcNewWonSchemeCreateConfig(DefaultInfoBuilderCreateConfig jcNewWonSchemeCreateConfig) {
		this.jcNewWonSchemeCreateConfig = jcNewWonSchemeCreateConfig;
	}

}
