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

import com.cai310.lottery.DltConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.dlt.DltPeriodData;
import com.cai310.lottery.service.lottery.MissDataControllerService;
import com.cai310.lottery.service.lottery.dlt.impl.DltMissDataControllerServiceImpl;
import com.cai310.lottery.service.lottery.dlt.impl.DltPeriodDataEntityManagerImpl;
import com.cai310.utils.Struts2Utils;

@Namespace("/" + DltConstant.KEY)
@Action(value = "analyse")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/analyse/"+DltConstant.KEY+"/index.ftl"),
	   @Result(name = "zhzs", location = "/WEB-INF/content/analyse/"+DltConstant.KEY+"/zhzs.ftl"),
	   @Result(name = "chpl", location = "/WEB-INF/content/analyse/"+DltConstant.KEY+"/chpl.ftl"),
	   @Result(name = "lqzs", location = "/WEB-INF/content/analyse/"+DltConstant.KEY+"/lqzs.ftl"),
	   @Result(name = "lhzs", location = "/WEB-INF/content/analyse/"+DltConstant.KEY+"/lhzs.ftl"),
	   @Result(name = "lsbd", location = "/WEB-INF/content/analyse/"+DltConstant.KEY+"/lsbd.ftl"),
	   @Result(name = "chzs", location = "/WEB-INF/content/analyse/"+DltConstant.KEY+"/chzs.ftl")
	})
public class DltAnalyseController extends AnalyseController{

	private static final long serialVersionUID = -6314279078587995931L;
	
	@Autowired
	private DltMissDataControllerServiceImpl missDataControllerServiceImpl;
	
	@Resource
	private DltPeriodDataEntityManagerImpl periodDataEntityManagerImpl;
	private String red1;
	private String red2;
	private String red3;
	private String red4;
	private String red5;
	private String blue1;
	private String blue2;
	private Integer count;
	private Map<String,Integer> keyMap = new HashMap<String,Integer>();
	@Override
	protected MissDataControllerService getSchemeService() {
		return missDataControllerServiceImpl;
	}
	
	@Override
	public Lottery getLotteryType() {
		return Lottery.DLT;
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
		DetachedCriteria c=DetachedCriteria.forClass(DltPeriodData.class);
		c.add(Restrictions.isNotNull("result"));
		c.addOrder(Order.desc("id"));
		List<DltPeriodData> list=periodDataEntityManagerImpl.findByDetachedCriteria(c,0,count);
		
		List<Long> periodIdList=new ArrayList<Long>();
		for(DltPeriodData data:list){
			String[] rs=data.getRsultArr();
			periodIdList.add(data.getPeriodId());
			if(red1==null){
				red1=rs[0];
				red2=rs[1];
				red3=rs[2];
				red4=rs[3];
				red5=rs[4];
				blue1=rs[5];
				blue2=rs[6];
			}else{
				long hits=0;
				String[] rs1=(String[])ArrayUtils.subarray(rs, 0, 5);
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
				rs1=(String[])ArrayUtils.subarray(rs, 5, 7);
				if(ArrayUtils.contains(rs1, blue1)){
					hits++;
				}
				if(ArrayUtils.contains(rs1, blue2)){
					hits++;
				}
				data.setPrizePool(hits);
			}
		}
		Collections.sort(list, new Comparator<DltPeriodData>(){
			@Override
			public int compare(DltPeriodData o1, DltPeriodData o2) {
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
				blue1=df.format(Integer.parseInt(blue1));
				blue2=df.format(Integer.parseInt(blue2));
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
		String[] nums={red1,red2,red3,red4,red5};
		return ArrayUtils.contains(nums, red);
	}
	public boolean isBlueContain(String blue){
		String[] nums={blue1,blue2};
		return ArrayUtils.contains(nums, blue);
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

	public String getBlue1() {
		return blue1;
	}

	public void setBlue1(String blue1) {
		this.blue1 = blue1;
	}

	public String getBlue2() {
		return blue2;
	}

	public void setBlue2(String blue2) {
		this.blue2 = blue2;
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
