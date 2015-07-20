package com.cai310.lottery.web.controller.info.analyse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.SevenConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.service.lottery.MissDataControllerService;
import com.cai310.lottery.service.lottery.seven.impl.SevenMissDataControllerServiceImpl;

@Namespace("/" + SevenConstant.KEY)
@Action(value = "analyse")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/analyse/"+SevenConstant.KEY+"/index.ftl"),
	   @Result(name = "zhzs", location = "/WEB-INF/content/analyse/"+SevenConstant.KEY+"/zhzs.ftl"),
	   @Result(name = "tmzs", location = "/WEB-INF/content/analyse/"+SevenConstant.KEY+"/tmzs.ftl"),
	   @Result(name = "dwzs", location = "/WEB-INF/content/analyse/"+SevenConstant.KEY+"/dwzs.ftl"),
	   @Result(name = "lhzs", location = "/WEB-INF/content/analyse/"+SevenConstant.KEY+"/lhzs.ftl"),
	   @Result(name = "chzs", location = "/WEB-INF/content/analyse/"+SevenConstant.KEY+"/chzs.ftl"),
	   @Result(name = "fhzs", location = "/WEB-INF/content/analyse/"+SevenConstant.KEY+"/fhzs.ftl"),
	   @Result(name = "hzzs", location = "/WEB-INF/content/analyse/"+SevenConstant.KEY+"/hzzs.ftl")
	})
public class SevenAnalyseController extends AnalyseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4416745067975690903L;
	@Autowired
	private SevenMissDataControllerServiceImpl missDataControllerServiceImpl;
	@Override
	protected MissDataControllerService getSchemeService() {
		return missDataControllerServiceImpl;
	}
	@Override
	public Lottery getLotteryType() {
		return Lottery.SEVEN;
	}
	public String index(){
		loadForecastAndSkillsNewsList();
		return "chzs";
	}
	public String zhzs(){
		loadForecastAndSkillsNewsList();
	     return "zhzs";
	}
	public String chzs(){
		loadForecastAndSkillsNewsList();
	     return "chzs";
	}
	public String tmzs(){
		loadForecastAndSkillsNewsList();
	     return "tmzs";
	}
	public String dwzs(){
		loadForecastAndSkillsNewsList();
	     return "dwzs";
	}
	public String lhzs(){
		loadForecastAndSkillsNewsList();
	     return "lhzs";
	}
	public String hzzs(){
		loadForecastAndSkillsNewsList();
	     return "hzzs";
	}
	public String fhzs(){
		loadForecastAndSkillsNewsList();
	     return "fhzs";
	}
}
