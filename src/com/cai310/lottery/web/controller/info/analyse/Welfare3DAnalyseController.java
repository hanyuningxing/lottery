package com.cai310.lottery.web.controller.info.analyse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Welfare3dConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.service.lottery.MissDataControllerService;
import com.cai310.lottery.service.lottery.welfare3d.impl.Welfare3dMissDataControllerServiceImpl;

@Namespace("/" + Welfare3dConstant.KEY)
@Action(value = "analyse")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/analyse/"+Welfare3dConstant.KEY+"/index.ftl"),
	   @Result(name = "hezhi", location = "/WEB-INF/content/analyse/"+Welfare3dConstant.KEY+"/hezhi.ftl"),
	   @Result(name = "zhixuan", location = "/WEB-INF/content/analyse/"+Welfare3dConstant.KEY+"/zhixuan.ftl"),
	   @Result(name = "g6_miss", location = "/WEB-INF/content/analyse/"+Welfare3dConstant.KEY+"/g6_miss.ftl"),
	   @Result(name = "g3_miss", location = "/WEB-INF/content/analyse/"+Welfare3dConstant.KEY+"/g3_miss.ftl"),
	   @Result(name = "kuadu", location = "/WEB-INF/content/analyse/"+Welfare3dConstant.KEY+"/kuadu.ftl")
	})
public class Welfare3DAnalyseController extends AnalyseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;
	@Autowired
	private Welfare3dMissDataControllerServiceImpl welfare3dMissDataControllerServiceImpl;
	@Override
	protected MissDataControllerService getSchemeService() {
		return welfare3dMissDataControllerServiceImpl;
	}
	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE3D;
	}
	public String zhixuan(){
		loadForecastAndSkillsNewsList();
	     return "zhixuan";
	}
	public String kuadu(){
		loadForecastAndSkillsNewsList();
	     return "kuadu";
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
