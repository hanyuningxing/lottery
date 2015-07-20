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

import com.cai310.lottery.SsqConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.ssq.SsqPeriodData;
import com.cai310.lottery.service.lottery.MissDataControllerService;
import com.cai310.lottery.service.lottery.ssq.impl.SsqMissDataControllerServiceImpl;
import com.cai310.lottery.service.lottery.ssq.impl.SsqPeriodDataEntityManagerImpl;
import com.cai310.utils.Struts2Utils;

@Namespace("/" + SsqConstant.KEY)
@Action(value = "analyse")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/analyse/"+SsqConstant.KEY+"/index.ftl"),
	   @Result(name = "zhzs", location = "/WEB-INF/content/analyse/"+SsqConstant.KEY+"/zhzs.ftl"),
	   @Result(name = "chpl", location = "/WEB-INF/content/analyse/"+SsqConstant.KEY+"/chpl.ftl"),
	   @Result(name = "lqzs", location = "/WEB-INF/content/analyse/"+SsqConstant.KEY+"/lqzs.ftl"),
	   @Result(name = "lhzs", location = "/WEB-INF/content/analyse/"+SsqConstant.KEY+"/lhzs.ftl"),
	   @Result(name = "lsbd", location = "/WEB-INF/content/analyse/"+SsqConstant.KEY+"/lsbd.ftl"),
	   @Result(name = "chzs", location = "/WEB-INF/content/analyse/"+SsqConstant.KEY+"/chzs.ftl")
	})
public class SsqAnalyseController extends AnalyseController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181515539780697830L;
	@Resource
	private SsqMissDataControllerServiceImpl missDataControllerServiceImpl;
	@Resource
	private SsqPeriodDataEntityManagerImpl periodDataEntityManagerImpl;
	private String red1;
	private String red2;
	private String red3;
	private String red4;
	private String red5;
	private String red6;
	private String blue;
	private Integer count;
	private Map<String,Integer> keyMap = new HashMap<String,Integer>();
	@Override
	protected MissDataControllerService getSchemeService() {
		return missDataControllerServiceImpl;
	}
	@Override
	public Lottery getLotteryType() {
		return Lottery.SSQ;
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
	public String lqzs(){
		loadForecastAndSkillsNewsList();
	     return "lqzs";
	}
	public String lhzs(){
		loadForecastAndSkillsNewsList();
	     return "lhzs";
	}
	public String lsbd(){
		if(count==null||count<=0){
			count=50;
		}
		formatNum();
		DetachedCriteria c=DetachedCriteria.forClass(SsqPeriodData.class);
		c.add(Restrictions.isNotNull("result"));
		c.addOrder(Order.desc("id"));
		List<SsqPeriodData> list=periodDataEntityManagerImpl.findByDetachedCriteria(c,0,count);
		
		List<Long> periodIdList=new ArrayList<Long>();
		for(SsqPeriodData data:list){
			periodIdList.add(data.getPeriodId());
			String[] rs=data.getRsultArr();
			if(red1==null){
				red1=rs[0];
				red2=rs[1];
				red3=rs[2];
				red4=rs[3];
				red5=rs[4];
				red6=rs[5];
				blue=rs[6];
				data.setPrizePool(7l);
			}else{
				long hits=0;
				String[] rs1=(String[])ArrayUtils.subarray(rs, 0, 6);
				if(ArrayUtils.contains(rs1, red1)){
					hits++;
				}
				if(ArrayUtils.contains(rs1, red2)){
					hits++;
				}
				if(ArrayUtils.contains(rs1, red3)){
					hits++;
				}
				if(ArrayUtils.contains(rs1, red4)){
					hits++;
				}
				if(ArrayUtils.contains(rs1, red5)){
					hits++;
				}
				if(ArrayUtils.contains(rs1, red6)){
					hits++;
				}
				if(blue.equals(rs[6])){
					hits++;
				}
				data.setPrizePool(hits);
			}
		}
		Collections.sort(list, new Comparator<SsqPeriodData>(){
			@Override
			public int compare(SsqPeriodData o1, SsqPeriodData o2) {
				if(o1.getPrizePool() < o2.getPrizePool()){
					return 1;
				}
				return -1;
			}
			
		});
		c=DetachedCriteria.forClass(Period.class);
		c.add(Restrictions.in("id", periodIdList));
		c.addOrder(Order.desc("id"));
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
			if(red1!=null){
				DecimalFormat df=new DecimalFormat("00");
				red1=df.format(Integer.parseInt(red1));
				red2=df.format(Integer.parseInt(red2));
				red3=df.format(Integer.parseInt(red3));
				red4=df.format(Integer.parseInt(red4));
				red5=df.format(Integer.parseInt(red5));
				red6=df.format(Integer.parseInt(red6));
				blue=df.format(Integer.parseInt(blue));
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
		String[] nums={red1,red2,red3,red4,red5,red6};
		return ArrayUtils.contains(nums, red);
	}
	public String getRed1() {
		return red1;
	}
	public void setRed1(String red1) {
		this.red1 = red1;
	}
	public String getRed2() {
		return red2;
	}
	public void setRed2(String red2) {
		this.red2 = red2;
	}
	public String getRed3() {
		return red3;
	}
	public void setRed3(String red3) {
		this.red3 = red3;
	}
	public String getRed4() {
		return red4;
	}
	public void setRed4(String red4) {
		this.red4 = red4;
	}
	public String getRed5() {
		return red5;
	}
	public void setRed5(String red5) {
		this.red5 = red5;
	}
	public String getRed6() {
		return red6;
	}
	public void setRed6(String red6) {
		this.red6 = red6;
	}
	public String getBlue() {
		return blue;
	}
	public void setBlue(String blue) {
		this.blue = blue;
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
	
}
