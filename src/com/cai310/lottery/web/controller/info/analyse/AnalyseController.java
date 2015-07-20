package com.cai310.lottery.web.controller.info.analyse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.InfoState;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.lottery.MissDataControllerService;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;

public abstract class AnalyseController extends BaseController {
	protected abstract MissDataControllerService getSchemeService();
	@Autowired
	protected QueryService queryService;
	private ArrayList<NewsInfoData> hotNews = new ArrayList<NewsInfoData>();
	
	public String index(){
		loadForecastAndSkillsNewsList();
	     return "index";
	}
	public String hezhi(){
		loadForecastAndSkillsNewsList();
	     return "hezhi";
	}
	public abstract Lottery getLotteryType() ;
	
	protected void loadForecastAndSkillsNewsList(){
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsInfoData.class);
		criteria.add(Restrictions.eq("lotteryType", this.getLotteryType()));
		criteria.add(Restrictions.eq("state", InfoState.NORMAL));
		criteria.addOrder(Order.desc("id"));
		hotNews = (ArrayList<NewsInfoData>) queryService.findByDetachedCriteria(criteria,0,10,true);
	}
	
	public ArrayList<NewsInfoData> getHotNews() {
		return hotNews;
	}
	public void setHotNews(ArrayList<NewsInfoData> hotNews) {
		this.hotNews = hotNews;
	}
	public String getRandomStr(){
		return System.currentTimeMillis()+"";
	}
	
	public String getTime(int type){
		Calendar c=Calendar.getInstance();
		if(type!=0){
			c.add(Calendar.DAY_OF_MONTH, type);
		}
		DateFormat df=new SimpleDateFormat("yyyyMMdd");
		return df.format(c.getTime());
	}
}
