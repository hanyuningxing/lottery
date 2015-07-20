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

import com.cai310.lottery.QyhConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.keno.qyh.QyhMissDataInfo;
import com.cai310.lottery.entity.lottery.keno.ssc.SscMissDataInfo;
import com.cai310.lottery.service.lottery.MissDataControllerService;
import com.cai310.lottery.service.lottery.keno.qyh.impl.QyhMissDataControllerServiceImpl;
import com.cai310.lottery.utils.analyse.qyh.BaseDataBuilder;
import com.cai310.utils.Struts2Utils;

@Namespace("/" + QyhConstant.KEY)
@Action(value = "analyse")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/analyse/"+QyhConstant.KEY+"/index.ftl"),
	   @Result(name = "weizhi", location = "/WEB-INF/content/analyse/"+QyhConstant.KEY+"/weizhi.ftl"),
	   @Result(name = "yilou", location = "/WEB-INF/content/analyse/"+QyhConstant.KEY+"/yilou.ftl"),
	   @Result(name = "lianhao", location = "/WEB-INF/content/analyse/"+QyhConstant.KEY+"/lianhao.ftl"),
	   @Result(name = "chonghao", location = "/WEB-INF/content/analyse/"+QyhConstant.KEY+"/chonghao.ftl"),
	   @Result(name = "chzs", location = "/WEB-INF/content/analyse/"+QyhConstant.KEY+"/chzs.ftl"),
	   @Result(name = "weixuan", location = "/WEB-INF/content/analyse/"+QyhConstant.KEY+"/weixuan.ftl"),
	   @Result(name = "jiou", location = "/WEB-INF/content/analyse/"+QyhConstant.KEY+"/jiou.ftl"),
	   @Result(name = "renxuan", location = "/WEB-INF/content/analyse/"+QyhConstant.KEY+"/renxuan.ftl"),
	   @Result(name = "hezhi", location = "/WEB-INF/content/analyse/"+QyhConstant.KEY+"/hezhi.ftl"),
	   @Result(name = "lhyl", location = "/WEB-INF/content/analyse/"+QyhConstant.KEY+"/lhyl.ftl"),
	   @Result(name = "twh", location = "/WEB-INF/content/analyse/"+QyhConstant.KEY+"/twh.ftl")

})
public class QyhAnalyseController extends AnalyseController{

	/**
	 * 
	 */
	private Integer wz=1;
	private Integer count = 100;
	private String endPeriod;
	private String startPeriod;
	private static final long serialVersionUID = 2181515539780697830L;
	@Autowired
	private QyhMissDataControllerServiceImpl missDataControllerServiceImpl;
	@Override
	protected MissDataControllerService getSchemeService() {
		return missDataControllerServiceImpl;
	}
	@Override
	public Lottery getLotteryType() {
		return Lottery.QYH;
	}
	public String index(){
		loadForecastAndSkillsNewsList();
		return "chzs";
	}
	public String weizhi(){
		loadForecastAndSkillsNewsList();
	     return "weizhi";
	}
	public String yilou(){
		loadForecastAndSkillsNewsList();
	     return "yilou";
	}
	public String lianhao(){
		loadForecastAndSkillsNewsList();
	     return "lianhao";
	}
	public String chonghao(){
		loadForecastAndSkillsNewsList();
	     return "chonghao";
	}
	public String lhzs(){
		loadForecastAndSkillsNewsList();
	     return "lhzs";
	}
	public Integer getWz() {
		return wz;
	}
	public void setWz(Integer wz) {
		this.wz = wz;
	}
	//围选二 三
	public String weixuan(){
		List missDatas = missDataControllerServiceImpl.getMissDatas(count);
		BaseDataBuilder builder = new BaseDataBuilder();
		
		ArrayList resultList = getResultList(missDatas);
		if(wz==1){
			HashMap map = builder.weixuan2(resultList);
			Vector<ArrayList> datas = (Vector<ArrayList>) map.get("w2");
			ArrayList w2Datas = convertVector2List(datas);
			Struts2Utils.setAttribute("datas", w2Datas);
		}else{
			HashMap map = builder.weixuan3(resultList);
			Vector<ArrayList> datas = (Vector<ArrayList>) map.get("w3");
			ArrayList w3Datas = convertVector2List(datas);
			Struts2Utils.setAttribute("datas", w3Datas);
		}	
		loadForecastAndSkillsNewsList();
		return "weixuan";
	}
	//奇偶统计
	public String oddEvenSummary(){
		List missDatas = missDataControllerServiceImpl.getMissDatas(count);
		BaseDataBuilder builder = new BaseDataBuilder();
		
		ArrayList resultList = getResultList(missDatas);
		HashMap map = builder.oddEvenSummary(resultList,wz);
		if(wz==3){
			Struts2Utils.setAttribute("minOddCount2", (List) map.get("minOddCount2"));
			Struts2Utils.setAttribute("maxOddCount2", (List) map.get("maxOddCount2"));
			Struts2Utils.setAttribute("minEvenCount2", (List) map.get("minEvenCount2"));
			Struts2Utils.setAttribute("maxEvenCount2", (List) map.get("maxEvenCount2"));
		}else if(wz==4){
			Struts2Utils.setAttribute("minOddCount3", (List) map.get("minOddCount3"));
			Struts2Utils.setAttribute("maxOddCount3", (List) map.get("maxOddCount3"));
			Struts2Utils.setAttribute("minEvenCount3", (List) map.get("minEvenCount3"));
			Struts2Utils.setAttribute("maxEvenCount3", (List) map.get("maxEvenCount3"));
		}else if(wz==5){
			Struts2Utils.setAttribute("minRandom3Odd", (List) map.get("minRandom3Odd"));
			Struts2Utils.setAttribute("maxRandom3Odd", (List) map.get("maxRandom3Odd"));
			Struts2Utils.setAttribute("minRandom3Even", (List) map.get("minRandom3Even"));
			Struts2Utils.setAttribute("maxRandom3Even", (List) map.get("maxRandom3Even"));
		}
		loadForecastAndSkillsNewsList();
		return "jiou";
	}
	//任选
	public String renxuan(){
		List missDatas = missDataControllerServiceImpl.getMissDatas(count);
		BaseDataBuilder builder = new BaseDataBuilder();
		if(wz==6){
			ArrayList resultList = getResultList(missDatas);
			Vector<ArrayList> datas = builder.executeRand3Balls(resultList);
			Struts2Utils.setAttribute("datas", convertVector2List(datas));
		}
		loadForecastAndSkillsNewsList();

		return "renxuan";
	} 
	//和值
	public String hezhi(){
		List missDatas = missDataControllerServiceImpl.getMissDatas(count);
		BaseDataBuilder builder = new BaseDataBuilder();
		
		ArrayList resultList = getResultList(missDatas);
		Vector<ArrayList> datas = builder.hezhi(resultList);
		Struts2Utils.setAttribute("datas", convertVector2List(datas));
		loadForecastAndSkillsNewsList();
		return "hezhi";
	}
	//连号
	public String lianhaoMiss(){
		List missDatas = missDataControllerServiceImpl.getMissDatas(count);
		BaseDataBuilder builder = new BaseDataBuilder();
		
		ArrayList resultList = getResultList(missDatas);
		ArrayList datas = builder.lianhao(resultList);
		Struts2Utils.setAttribute("datas", datas);
		loadForecastAndSkillsNewsList();
		return "lhyl";
	}
	//同尾号
	public String commonTailNum(){
		List missDatas = missDataControllerServiceImpl.getMissDatas(count);
		BaseDataBuilder builder = new BaseDataBuilder();
		
		ArrayList resultList = getResultList(missDatas);
		ArrayList datas = builder.tongweihao(resultList);
		Struts2Utils.setAttribute("datas", datas);
		loadForecastAndSkillsNewsList();
		return "twh";
	}
	
	private ArrayList convertVector2List(Vector<ArrayList> datas){
		ArrayList list = new ArrayList();
		for(int i=0;i<datas.size();i++){
			ArrayList data = datas.get(i);
			list.add(data.toArray());
		}
		return list;
	}
	
	private ArrayList getResultList(List missDatas){
		Collections.sort(missDatas, new Comparator() {      
    		public int compare(Object o1, Object o2) {       
    			Long pn1 = new Long(((QyhMissDataInfo)o1).getPeriodNumber().replace("-", ""));
    			Long pn2 = new Long(((QyhMissDataInfo)o2).getPeriodNumber().replace("-", ""));
    			return pn1.intValue()-pn2.intValue();
    		}
		});
		ArrayList resultList = new ArrayList();
		for(int i=0;i<missDatas.size();i++){
			QyhMissDataInfo missData = (QyhMissDataInfo) missDatas.get(i);
			StringBuffer buffer = new StringBuffer();
			String[] result = missData.getResult().split(",");
			for(int j=0;j<result.length;j++){
				if(result[j].length()==1){
					buffer.append("0").append(result[j]).append(",");
				}else{
					buffer.append(result[j]).append(",");
				}
			}
			
			resultList.add(buffer.substring(0, buffer.length()-1));
		}
		return resultList;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getEndPeriod() {
		return endPeriod;
	}
	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
	}
	public String getStartPeriod() {
		return startPeriod;
	}
	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}
	
}
