package com.cai310.lottery.builder.indexnews;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Constant;
import com.cai310.lottery.builder.AbstractInfoBuilder;
import com.cai310.lottery.builder.DefaultInfoBuilderCreateConfig;
import com.cai310.lottery.common.InfoState;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.QueryService;

/**
 * Description: 首页最新新闻信息静态化工具类<br>
 * Copyright: Copyright (c) 2011<br>
 * Company: 肇庆优盛科技
 * 
 * @author zhuhui 2011-03-16 编写
 * @version 1.0
 */
public class IndexNewsBuilder extends AbstractInfoBuilder {
	//
	@Autowired
	protected QueryService queryService;
	// 查询器
	private IndexNewsBuilderQueryForm indexNewsBuilderQueryForm;

	public IndexNewsBuilderQueryForm getIndexNewsBuilderQueryForm() {
		return indexNewsBuilderQueryForm;
	}

	public void setIndexNewsBuilderQueryForm(IndexNewsBuilderQueryForm indexNewsBuilderQueryForm) {
		this.indexNewsBuilderQueryForm = indexNewsBuilderQueryForm;
	}

	public DefaultInfoBuilderCreateConfig getIndexNewsCreateConfig() {
		return indexNewsCreateConfig;
	}

	public void setIndexNewsCreateConfig(DefaultInfoBuilderCreateConfig indexNewsCreateConfig) {
		this.indexNewsCreateConfig = indexNewsCreateConfig;
	}

	// 生成器
	private DefaultInfoBuilderCreateConfig indexNewsCreateConfig;

	public void createNewIndexFile() throws DataException {
		Map<String, Object> indexContents = new HashMap<String, Object>();
		indexContents.put("base", Constant.BASEPATH);
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsInfoData.class);
		criteria.add(Restrictions.eq("isNotice", NewsInfoData.NOTICE));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		if (null != indexNewsBuilderQueryForm.getLottery()) {
			criteria.add(Restrictions.eq("lotteryType", indexNewsBuilderQueryForm.getLottery()));
		}
		if ((null != indexNewsBuilderQueryForm.getInfoType())) {
			criteria.add(Restrictions.eq("type", indexNewsBuilderQueryForm.getInfoType()));
		}
		if ((null != indexNewsBuilderQueryForm.getInfoSubType())) {
			criteria.add(Restrictions.eq("infoSubType", indexNewsBuilderQueryForm.getInfoSubType()));
		}
		// 处理排序
		for (Order order : indexNewsBuilderQueryForm.getOrders()) {
			criteria.addOrder(order);
		}
		List<NewsInfoData> newsIndexNeedList = queryService.findByDetachedCriteria(criteria, 0,
				indexNewsBuilderQueryForm.getNeedNum());
		indexContents.put("newsIndexNeedList", newsIndexNeedList);
		String templatePath = indexNewsCreateConfig.getTemplatePath();
		String templateName = indexNewsCreateConfig.getTemplateName();
		String targetFilePath = indexNewsCreateConfig.getTargetFilePath();
		String targetFileName = indexNewsCreateConfig.getTargetFileName();
		super.createNewsFile(indexContents, templatePath, templateName, targetFilePath, targetFileName);

	}

}
