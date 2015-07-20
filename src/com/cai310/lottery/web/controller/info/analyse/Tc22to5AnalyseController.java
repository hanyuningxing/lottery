package com.cai310.lottery.web.controller.info.analyse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.SevenConstant;
import com.cai310.lottery.SsqConstant;
import com.cai310.lottery.Tc22to5Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5MissDataInfo;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5PeriodData;
import com.cai310.lottery.service.lottery.MissDataControllerService;
import com.cai310.lottery.service.lottery.tc22to5.impl.Tc22to5MissDataControllerServiceImpl;
import com.cai310.lottery.service.lottery.tc22to5.impl.Tc22to5PeriodDataEntityManagerImpl;
import com.cai310.utils.Struts2Utils;

@Namespace("/" + Tc22to5Constant.KEY)
@Action(value = "analyse")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/analyse/"+Tc22to5Constant.KEY+"/index.ftl"),
	   @Result(name = "zhzs", location = "/WEB-INF/content/analyse/"+Tc22to5Constant.KEY+"/zhzs.ftl"),
	   @Result(name = "chpl", location = "/WEB-INF/content/analyse/"+Tc22to5Constant.KEY+"/chpl.ftl"),
	   @Result(name = "dwzs", location = "/WEB-INF/content/analyse/"+Tc22to5Constant.KEY+"/dwzs.ftl"),
	   @Result(name = "lhzs", location = "/WEB-INF/content/analyse/"+Tc22to5Constant.KEY+"/lhzs.ftl"),
	   @Result(name = "chzs", location = "/WEB-INF/content/analyse/"+Tc22to5Constant.KEY+"/chzs.ftl"),
	   @Result(name = "lsbd", location = "/WEB-INF/content/analyse/"+Tc22to5Constant.KEY+"/lsbd.ftl"),
	   @Result(name = "fhzs", location = "/WEB-INF/content/analyse/"+Tc22to5Constant.KEY+"/fhzs.ftl"),
	   @Result(name = "hzzs", location = "/WEB-INF/content/analyse/"+Tc22to5Constant.KEY+"/hzzs.ftl")
	})
public class Tc22to5AnalyseController extends AnalyseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4416745067975690903L;
	@Autowired
	private Tc22to5MissDataControllerServiceImpl missDataControllerServiceImpl;
	@Resource
	private Tc22to5PeriodDataEntityManagerImpl periodDataEntityManagerImpl;
	private String num1;
	private String num2;
	private String num3;
	private String num4;
	private String num5;
	private Map<String,Integer> keyMap = new HashMap<String,Integer>();
	
	private Integer count;
	@Override
	protected MissDataControllerService getSchemeService() {
		return missDataControllerServiceImpl;
	}
	@Override
	public Lottery getLotteryType() {
		return Lottery.TC22TO5;
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
	public String chpl(){
		loadForecastAndSkillsNewsList();
	     return "chpl";
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
	public String lsbd(){
		if(count==null||count<=0){
			count=50;
		}
		formatNum();
		DetachedCriteria c=DetachedCriteria.forClass(Tc22to5PeriodData.class);
		c.add(Restrictions.isNotNull("result"));
		c.addOrder(Order.desc("id"));
		List<Tc22to5PeriodData> list=periodDataEntityManagerImpl.findByDetachedCriteria(c,0,count);
		
		List<Long> periodIdList=new ArrayList<Long>();
		for(Tc22to5PeriodData data:list){
			periodIdList.add(data.getPeriodId());
			if(num1==null){
				String[] rs=data.getRsultArr();
				num1=rs[0];
				num2=rs[1];
				num3=rs[2];
				num4=rs[3];
				num5=rs[4];
			}
		}
		c=DetachedCriteria.forClass(Period.class);
		c.add(Restrictions.in("id", periodIdList));
		c.addOrder(Order.desc("periodNumber"));
		List<Period> periodList=periodDataEntityManagerImpl.findByDetachedCriteria(c);
		Map<Long,Period> periodMap=new HashMap<Long,Period>();
		for(Period data:periodList){
			periodMap.put(data.getId(), data);
		}
		Struts2Utils.setAttribute("periodMap", periodMap);
		Struts2Utils.setAttribute("list", list);
		
		loadForecastAndSkillsNewsList();

	     return "lsbd";
	}
	private void formatNum(){
		try {
			if(num1!=null){
				DecimalFormat df=new DecimalFormat("00");
				num1=df.format(Integer.parseInt(num1));
				num2=df.format(Integer.parseInt(num2));
				num3=df.format(Integer.parseInt(num3));
				num4=df.format(Integer.parseInt(num4));
				num5=df.format(Integer.parseInt(num5));
			}
		} catch (NumberFormatException e) {
		}
	}
	public void updateKey(String key){
		Integer value=keyMap.get(key);
		if(value==null){
			value=0;
		}
		value++;
		keyMap.put(key, value);
	}
	public boolean isContain(String red){
		String[] nums={num1,num2,num3,num4,num5};
		return ArrayUtils.contains(nums, red);
	}
	public String getNum1() {
		return num1;
	}
	public void setNum1(String num1) {
		this.num1 = num1;
	}
	public String getNum2() {
		return num2;
	}
	public void setNum2(String num2) {
		this.num2 = num2;
	}
	public String getNum3() {
		return num3;
	}
	public void setNum3(String num3) {
		this.num3 = num3;
	}
	public String getNum4() {
		return num4;
	}
	public void setNum4(String num4) {
		this.num4 = num4;
	}
	public String getNum5() {
		return num5;
	}
	public void setNum5(String num5) {
		this.num5 = num5;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Map<String, Integer> getKeyMap() {
		return keyMap;
	}
	public void setKeyMap(Map<String, Integer> keyMap) {
		this.keyMap = keyMap;
	}
	public String fhzs(){
	     return "fhzs";
	}
	
}
