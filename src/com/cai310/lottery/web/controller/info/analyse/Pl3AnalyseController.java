package com.cai310.lottery.web.controller.info.analyse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.PlConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.service.lottery.MissDataControllerService;
import com.cai310.lottery.service.lottery.pl.impl.PlMissDataControllerServiceImpl;

@Namespace("/" + PlConstant.KEY+"3")
@Action(value = "analyse")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/analyse/"+PlConstant.KEY+"3/index.ftl"),
	   @Result(name = "hezhi", location = "/WEB-INF/content/analyse/"+PlConstant.KEY+"3/hezhi.ftl"),
	   @Result(name = "zhixuan", location = "/WEB-INF/content/analyse/"+PlConstant.KEY+"3/zhixuan.ftl"),
	   @Result(name = "g6_miss", location = "/WEB-INF/content/analyse/"+PlConstant.KEY+"3/g6_miss.ftl"),
	   @Result(name = "g3_miss", location = "/WEB-INF/content/analyse/"+PlConstant.KEY+"3/g3_miss.ftl"),
	   @Result(name = "kuadu", location = "/WEB-INF/content/analyse/"+PlConstant.KEY+"3/kuadu.ftl")
	})
public class Pl3AnalyseController extends AnalyseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;
	@Autowired
	private PlMissDataControllerServiceImpl plMissDataControllerServiceImpl;
	@Override
	protected MissDataControllerService getSchemeService() {
		return plMissDataControllerServiceImpl;
	}
	@Override
	public Lottery getLotteryType() {
		return Lottery.PL;
	}
	public String kuadu(){
		loadForecastAndSkillsNewsList();
	     return "kuadu";
	}
	public String zhixuan(){
		loadForecastAndSkillsNewsList();
	     return "zhixuan";
	}
	public String g6Miss(){
		loadForecastAndSkillsNewsList();
	     return "g6_miss";
	}
	public String g3Miss(){
		loadForecastAndSkillsNewsList();
	     return "g3_miss";
	}
}
