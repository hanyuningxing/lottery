package com.cai310.lottery.web.controller.info.analyse;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.InfoState;
import com.cai310.lottery.common.InfoSubType;
import com.cai310.lottery.common.InfoType;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.orm.XDetachedCriteria;
@Namespace("/info")
@Action(value = "analyseindex")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/analyse/index.ftl")  
	})
public  class AnalyseindexController extends BaseController {
	@Autowired
	protected QueryService queryService;
	private List<NewsInfoData> allNewsList;
	public List<NewsInfoData> getAllNewsList() {
		return allNewsList;
	}

	public void setAllNewsList(List<NewsInfoData> allNewsList) {
		this.allNewsList = allNewsList;
	}

	public String index(){
		//获取最新分析
		 allNewsList = this.findNewsInfoDataList(null, InfoType.FORECAST, null, 0, 6);
	     return "index";
	}
	
	private List<NewsInfoData> findNewsInfoDataList(Lottery lottery,InfoType infoType ,InfoSubType subType,int firstResult, int maxResults){
		XDetachedCriteria criteria = new XDetachedCriteria(NewsInfoData.class);
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		if(null!=lottery){
			criteria.add(Restrictions.eq("lotteryType",lottery));
		}
		if(null!=infoType){
			criteria.add(Restrictions.eq("type", infoType));
		}
		if(null!=subType){
			criteria.add(Restrictions.eq("subType", subType));
		}
		criteria.addOrder(Order.desc("id"));
	    return queryService.findByDetachedCriteria(criteria,firstResult, maxResults);
	}
}
