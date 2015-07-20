package com.cai310.lottery.web.controller.info.analyse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.Welfare36to7Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7MissDataInfo;
import com.cai310.lottery.service.lottery.MissDataControllerService;
import com.cai310.lottery.service.lottery.welfare36to7.impl.Welfare36to7MissDataControllerServiceImpl;
import com.cai310.lottery.utils.analyse.welfare36to7.BaseDataBuilder;
import com.cai310.utils.Struts2Utils;

@Namespace("/" + Welfare36to7Constant.KEY)
@Action(value = "analyse")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/analyse/"+Welfare36to7Constant.KEY+"/index.jsp"),
	   @Result(name = "szzst", location = "/WEB-INF/content/analyse/"+Welfare36to7Constant.KEY+"/szzst.jsp"),
	   @Result(name = "sxzst", location = "/WEB-INF/content/analyse/"+Welfare36to7Constant.KEY+"/sxzst.jsp"),
	   @Result(name = "jjfwzst", location = "/WEB-INF/content/analyse/"+Welfare36to7Constant.KEY+"/jjfwzst.jsp"),
	   @Result(name = "wszst", location = "/WEB-INF/content/analyse/"+Welfare36to7Constant.KEY+"/wszst.jsp"),
	   @Result(name = "wxzst", location = "/WEB-INF/content/analyse/"+Welfare36to7Constant.KEY+"/wxzst.jsp"),
	   @Result(name = "hmyl", location = "/WEB-INF/content/analyse/"+Welfare36to7Constant.KEY+"/hmyl.ftl"),   
	   @Result(name = "sxyl", location = "/WEB-INF/content/analyse/"+Welfare36to7Constant.KEY+"/sxyl.ftl"),   
	   @Result(name = "wsyl", location = "/WEB-INF/content/analyse/"+Welfare36to7Constant.KEY+"/wsyl.ftl"),   
	   @Result(name = "hsyl", location = "/WEB-INF/content/analyse/"+Welfare36to7Constant.KEY+"/hsyl.ftl"),   
	   @Result(name = "wxyl", location = "/WEB-INF/content/analyse/"+Welfare36to7Constant.KEY+"/wxyl.ftl"),
	   @Result(name = "xtyl", location = "/WEB-INF/content/analyse/"+Welfare36to7Constant.KEY+"/xtyl.ftl")   

 
})
public class Welfare36to7AnalyseController extends AnalyseController{

	private static final long serialVersionUID = 5369740394694989511L;

	@Autowired
	private Welfare36to7MissDataControllerServiceImpl missDataControllerServiceImpl;

	@Override
	protected MissDataControllerService getSchemeService() {
		return missDataControllerServiceImpl;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.WELFARE36To7;
	}
	private String type = "index";	
	private Integer count;
	private String start="";
	private String end="";
	private Integer sort = 0;
	
	public String w36to7zs(){
		if(null==count)count = 30;
		List<Welfare36to7MissDataInfo> datas = (List<Welfare36to7MissDataInfo>)missDataControllerServiceImpl.getLastMissDatas(count,start,end);
		if(sort==0||sort==1)
			Collections.sort(datas, new Comparator() {      
	    		public int compare(Object o1, Object o2) {       
	    			Integer pn1 = new Integer(((Welfare36to7MissDataInfo)o1).getPeriodNumber());
	    			Integer pn2 = new Integer(((Welfare36to7MissDataInfo)o2).getPeriodNumber());
	    			return pn1-pn2;
	    		}
			});  
		else
			Collections.sort(datas, new Comparator() {      
	    		public int compare(Object o1, Object o2) {       
	    			Integer pn1 = new Integer(((Welfare36to7MissDataInfo)o1).getPeriodNumber());
	    			Integer pn2 = new Integer(((Welfare36to7MissDataInfo)o2).getPeriodNumber());
	    			return pn2-pn1;
	    		}
			});  
		Struts2Utils.getRequest().setAttribute("datas",datas);
		loadForecastAndSkillsNewsList();
		return type;
	}
	
	public String w36to7yl(){
		if(null==count)count = 100;
		List<Welfare36to7MissDataInfo> missDatas = (List<Welfare36to7MissDataInfo>)missDataControllerServiceImpl.getMissDatas(count);
		Collections.sort(missDatas, new Comparator() {      
    		public int compare(Object o1, Object o2) {       
    			Integer pn1 = new Integer(((Welfare36to7MissDataInfo)o1).getPeriodNumber());
    			Integer pn2 = new Integer(((Welfare36to7MissDataInfo)o2).getPeriodNumber());
    			return pn1-pn2;
    		}
		});  	
		List result = getResultList(missDatas);

		BaseDataBuilder builder = new BaseDataBuilder();
		Vector<ArrayList> datas = null;
		if("hmyl".equals(type)){
			HashMap map = builder.execute(result);
			datas = (Vector<ArrayList>) map.get("w1");
		}else if("sxyl".equals(type)){
			HashMap map = builder.sxyl(result);
			datas = (Vector<ArrayList>) map.get("sx");
		}else if("wsyl".equals(type)){
			datas = builder.wsyl(result);
		}else if("hsyl".equals(type)){
			datas = builder.hsyl(result);
		}else if("wxyl".equals(type)){
			datas = builder.wxyl(result);
		}else if("xtyl".endsWith(type)){
			datas = builder.dxyl(result);

		}
		Struts2Utils.setAttribute("datas", datas);
		loadForecastAndSkillsNewsList();

		return type;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	private ArrayList getResultList(List missDatas){
		ArrayList resultList = new ArrayList();
		for(int i=0;i<missDatas.size();i++){
			Welfare36to7MissDataInfo missData = (Welfare36to7MissDataInfo) missDatas.get(i);
			StringBuffer buffer = new StringBuffer();
			String[] result = missData.getResult().split(",");	
			if(result[6].length()<2)
				resultList.add("0"+result[6]);
			else
				resultList.add(result[6]);
		}
		return resultList;
	}
	private ArrayList convertVector2List(Vector<ArrayList> datas){
		ArrayList list = new ArrayList();
		for(int i=0;i<datas.size();i++){
			ArrayList data = datas.get(i);
			list.add(data.toArray());
		}
		return list;
	}
	
}
