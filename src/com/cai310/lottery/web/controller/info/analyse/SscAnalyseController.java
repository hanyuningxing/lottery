package com.cai310.lottery.web.controller.info.analyse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.ssc.SscMissDataInfo;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7MissDataInfo;
import com.cai310.lottery.service.lottery.MissDataControllerService;
import com.cai310.lottery.service.lottery.keno.ssc.SscMissDataControllerServiceImpl;
import com.cai310.lottery.utils.analyse.ssc.BaseDataBuilder;
import com.cai310.utils.Struts2Utils;
import com.cai310.lottery.SscConstant;

@Namespace("/" + SscConstant.KEY)
@Action(value = "analyse")
@Results( {
	   @Result(name = "generalAward", location = "/WEB-INF/content/analyse/"+SscConstant.KEY+"/generalAward.ftl"),
	   @Result(name = "lyel", location = "/WEB-INF/content/analyse/"+SscConstant.KEY+"/lyel.ftl"),
	   @Result(name = "samSung", location = "/WEB-INF/content/analyse/"+SscConstant.KEY+"/samSung.ftl"),
	   @Result(name = "sxlyel", location = "/WEB-INF/content/analyse/"+SscConstant.KEY+"/sxlyel.ftl"),
	   @Result(name = "dxds", location = "/WEB-INF/content/analyse/"+SscConstant.KEY+"/dxds.ftl"),
	   @Result(name = "wxzs", location = "/WEB-INF/content/analyse/"+SscConstant.KEY+"/wxzs.jsp"),
	   @Result(name = "exhz", location = "/WEB-INF/content/analyse/"+SscConstant.KEY+"/exhz.jsp"),
	   @Result(name = "sxhz", location = "/WEB-INF/content/analyse/"+SscConstant.KEY+"/sxhz.jsp"),
	   @Result(name = "sxjo", location = "/WEB-INF/content/analyse/"+SscConstant.KEY+"/sxjo.jsp"),
	   @Result(name = "wnzs", location = "/WEB-INF/content/analyse/"+SscConstant.KEY+"/wnzs.jsp"),
	   @Result(name = "zhixuan", location = "/WEB-INF/content/analyse/"+SscConstant.KEY+"/zhixuan.ftl"),
	   @Result(name = "hezhi", location = "/WEB-INF/content/analyse/"+SscConstant.KEY+"/hezhi.ftl")
	   
})
public class SscAnalyseController extends AnalyseController{

	private static final long serialVersionUID = 5369740394694989511L;

	@Autowired
	private SscMissDataControllerServiceImpl missDataControllerServiceImpl;

	@Override
	protected MissDataControllerService getSchemeService() {
		return missDataControllerServiceImpl;
	}

	@Override
	public Lottery getLotteryType() {
		return Lottery.SSC;
	}
	
	private String type="";
	
	private Integer count;
	private String start;
	private String end;
	
	/**
	 * 二星综合
	 * @return
	 */
	public String generalAward(){
		loadForecastAndSkillsNewsList();
		return "generalAward";
	}
	/**
	 * 二星012路
	 * @return
	 */
	public String lyel(){
		loadForecastAndSkillsNewsList();
		return "lyel";
	}
	/**
	 * 三星综合
	 * @return
	 */
	public String samSung(){
		loadForecastAndSkillsNewsList();
		return "samSung";
	}

	/**
	 * 三星012路
	 * @return
	 */
	public String sxlyel(){
		loadForecastAndSkillsNewsList();
		return "sxlyel";
	}
	/**
	 * 大小单双
	 * @return
	 */
	public String dxds(){
		loadForecastAndSkillsNewsList();
		return "dxds";
	}
	
	public String ssczs(){
		if(null==count)count = 50;
		List datas = missDataControllerServiceImpl.getLastMissDatas(count,start,end);
		Struts2Utils.getRequest().setAttribute("datas", datas);
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
	//一二三星直选
	public String zhixuan(){
		if(null==count)count=100;
		List missDatas = missDataControllerServiceImpl.getMissDatas(count);
		Collections.sort(missDatas, new Comparator() {      
    		public int compare(Object o1, Object o2) {       
    			Long pn1 = new Long(((SscMissDataInfo)o1).getPeriodNumber());
    			Long pn2 = new Long(((SscMissDataInfo)o2).getPeriodNumber());  			
    			return pn1.intValue()-pn2.intValue();
    		}
		});
		
		BaseDataBuilder builder = new BaseDataBuilder();
		
		ArrayList resultList = getResultList(missDatas);
		if("zx1".equals(type)){//一星直选
			HashMap map = builder.execute(resultList);
			Vector<ArrayList> datas = (Vector<ArrayList>) map.get("w1");
			ArrayList w1Datas = convertVector2List(datas);
			Struts2Utils.setAttribute("datas", w1Datas);
		}else if("zx2".equals(type)){
			HashMap map = builder.exzx(resultList);
			Vector<ArrayList> datas = (Vector<ArrayList>) map.get("w2");
			ArrayList w2Datas = convertVector2List(datas);
			Struts2Utils.setAttribute("datas", w2Datas);
		}else if("zx3".equals(type)){	
			HashMap map = builder.sxzx(resultList);
			Vector<ArrayList> datas = (Vector<ArrayList>) map.get("w3");
			ArrayList w3Datas = convertVector2List(datas);
			Struts2Utils.setAttribute("datas", w3Datas);
		}else if("gx2".equals(type)){
			HashMap map = builder.exgx(resultList);
			Vector<ArrayList> datas = (Vector<ArrayList>) map.get("z2");
			ArrayList z2Datas = convertVector2List(datas);
			Struts2Utils.setAttribute("datas", z2Datas);
		}
		loadForecastAndSkillsNewsList();
		return "zhixuan";
	}
	
	//二三星和值
	public String hezhi(){
		if(null==count)count=100;
		List missDatas = missDataControllerServiceImpl.getMissDatas(count);
		Collections.sort(missDatas, new Comparator() {      
    		public int compare(Object o1, Object o2) {       
    			Long pn1 = new Long(((SscMissDataInfo)o1).getPeriodNumber());
    			Long pn2 = new Long(((SscMissDataInfo)o2).getPeriodNumber());
    			return pn1.intValue()-pn2.intValue();
    		}
		});
		
		BaseDataBuilder builder = new BaseDataBuilder();
		
		ArrayList resultList = getResultList(missDatas);
		if("exhzyl".equals(type)){
			Vector<ArrayList> datas = builder.exHezhi(resultList);
			ArrayList hzDatas = convertVector2List(datas);
			Struts2Utils.setAttribute("datas", hzDatas);
		}else if("sxhzyl".equals(type)){
			Vector<ArrayList> datas = builder.sxHezhi(resultList);
			ArrayList w2Datas = convertVector2List(datas);
			Struts2Utils.setAttribute("datas", w2Datas);
		}
		loadForecastAndSkillsNewsList();
		return "hezhi";
	}
	
	private ArrayList getResultList(List missDatas){
		ArrayList resultList = new ArrayList();
		for(int i=0;i<missDatas.size();i++){
			SscMissDataInfo missData = (SscMissDataInfo) missDatas.get(i);
			StringBuffer buffer = new StringBuffer();
			String[] result = missData.getResult().split(",");
			for(int j=0;j<result.length;j++){
				if(result[j].length()>1){
					buffer.append(new Integer(result[j])).append(",");
				}else{
					buffer.append(result[j]).append(",");
				}
			}
			
			resultList.add(buffer.substring(0, buffer.length()-1));
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
