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

@Namespace("/" + PlConstant.KEY+"5")
@Action(value = "analyse")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/analyse/"+PlConstant.KEY+"5/index.ftl"),
	   @Result(name = "zhixuan", location = "/WEB-INF/content/analyse/"+PlConstant.KEY+"5/zhixuan.ftl"),
	   @Result(name = "hezhi", location = "/WEB-INF/content/analyse/"+PlConstant.KEY+"5/hezhi.ftl")
	})
public class Pl5AnalyseController extends AnalyseController{

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
	public String zhixuan(){
		loadForecastAndSkillsNewsList();
	     return "zhixuan";
	}
}
