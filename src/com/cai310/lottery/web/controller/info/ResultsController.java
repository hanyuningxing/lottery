package com.cai310.lottery.web.controller.info;

import java.util.ArrayList;
import java.util.Date;
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
import com.cai310.lottery.common.LotteryCategory;
import com.cai310.lottery.common.LotteryUtil;
import com.cai310.lottery.entity.info.NewsInfoData;
import com.cai310.lottery.service.QueryService;
import com.cai310.lottery.service.ServiceException;
import com.cai310.lottery.web.controller.BaseController;
import com.cai310.orm.Pagination;
import com.cai310.orm.XDetachedCriteria;
import com.cai310.utils.DateUtil;
import com.cai310.utils.FileUtils;
@Namespace("/")
@Action("results")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/info/results/index.ftl")
	}) 
public class ResultsController extends BaseController {
	private static final long serialVersionUID = -3004288085663395253L;
	private Boolean scExists=Boolean.FALSE;
	private Boolean zcExists=Boolean.FALSE;
	private Boolean el11to5Exists=Boolean.FALSE;
	private Boolean sdEl11to5Exists=Boolean.FALSE;
	private Boolean gdEl11to5Exists=Boolean.FALSE;
	private Boolean ahKuai3Exists=Boolean.FALSE;
	private Boolean quh5Exists=Boolean.FALSE;
	private Boolean sscExists=Boolean.FALSE;
	private Boolean xjEl11to5Exists=Boolean.FALSE;
	private List<NewsInfoData> allNewsList;
	@Autowired
	private QueryService queryService;
	
	public Boolean getAhKuai3Exists() {
		return ahKuai3Exists;
	}
	public void setAhKuai3Exists(Boolean ahKuai3Exists) {
		this.ahKuai3Exists = ahKuai3Exists;
	}
	private List<Lottery> lotteryList=new ArrayList<Lottery>();;
	public String index() {
		try {
			for (Lottery lottery : LotteryUtil.getWebLotteryList()) {
				if(!lottery.getCategory().equals(LotteryCategory.FREQUENT)&&!lottery.equals(Lottery.DCZC)){
					lotteryList.add(lottery);
				}
			} 
			scExists=FileUtils.checkFileExists("/html/info/results/sc-result.html");
			zcExists=FileUtils.checkFileExists("/html/info/results/zc-result.html");
			el11to5Exists=FileUtils.checkFileExists("/html/info/results/el11to5-result.html");
			sdEl11to5Exists=FileUtils.checkFileExists("/html/info/results/sdel11to5-result.html");
			gdEl11to5Exists=FileUtils.checkFileExists("/html/info/results/gdel11to5-result.html");
			ahKuai3Exists=FileUtils.checkFileExists("/html/info/results/ahkuai3-result.html");
			quh5Exists=FileUtils.checkFileExists("/html/info/results/qyh-result.html");
			sscExists = FileUtils.checkFileExists("/html/info/results/ssc-result.html");
			xjEl11to5Exists=FileUtils.checkFileExists("/html/info/results/xjel11to5-result.html");
			 allNewsList = this.findNewsInfoDataList(null, InfoType.FORECAST, null, 0, 10);

			return "index";
		} catch (ServiceException e) {
			addActionMessage(e.getMessage());
		} catch (Exception e) {
			addActionMessage(e.getMessage());
			logger.warn(e.getMessage(), e);
		}

		return error();
	}
	public String getDateFormat(){
		return DateUtil.getLocalDate("yyyy-MM-dd")+" "+DateUtil.getWeekStr(new Date());
	}
	public Boolean getScExists() {
		return scExists;
	}
	public void setScExists(Boolean scExists) {
		this.scExists = scExists;
	}
	public Boolean getZcExists() {
		return zcExists;
	}
	public void setZcExists(Boolean zcExists) {
		this.zcExists = zcExists;
	}
	public Boolean getEl11to5Exists() {
		return el11to5Exists;
	}
	public void setEl11to5Exists(Boolean el11to5Exists) {
		this.el11to5Exists = el11to5Exists;
	}
	public List<Lottery> getLotteryList() {
		return lotteryList;
	}
	public void setLotteryList(List<Lottery> lotteryList) {
		this.lotteryList = lotteryList;
	}
	public Boolean getSdEl11to5Exists() {
		return sdEl11to5Exists;
	}
	public void setSdEl11to5Exists(Boolean sdEl11to5Exists) {
		this.sdEl11to5Exists = sdEl11to5Exists;
	}
	public Boolean getQuh5Exists() {
		return quh5Exists;
	}
	public void setQuh5Exists(Boolean quh5Exists) {
		this.quh5Exists = quh5Exists;
	}
	public Boolean getSscExists() {
		return sscExists;
	}
	public void setSscExists(Boolean sscExists) {
		this.sscExists = sscExists;
	}
	
	public Boolean getGdEl11to5Exists() {
		return gdEl11to5Exists;
	}
	public void setGdEl11to5Exists(Boolean gdEl11to5Exists) {
		this.gdEl11to5Exists = gdEl11to5Exists;
	}
	
	public List<NewsInfoData> getAllNewsList() {
		return allNewsList;
	}
	public void setAllNewsList(List<NewsInfoData> allNewsList) {
		this.allNewsList = allNewsList;
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
	public Boolean getXjEl11to5Exists() {
		return xjEl11to5Exists;
	}
	public void setXjEl11to5Exists(Boolean xjEl11to5Exists) {
		this.xjEl11to5Exists = xjEl11to5Exists;
	}
}
