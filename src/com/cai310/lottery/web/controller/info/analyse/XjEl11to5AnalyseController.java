package com.cai310.lottery.web.controller.info.analyse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.cai310.lottery.XjEl11to5Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.YilouBean;
import com.cai310.lottery.entity.lottery.RandomNumMiss;
import com.cai310.lottery.service.lottery.MissDataControllerService;
import com.cai310.lottery.service.lottery.keno.xjel11to5.impl.XjEl11to5MissDataControllerServiceImpl;
import com.cai310.lottery.service.lottery.keno.xjel11to5.impl.XjEl11to5MissDataEntityManagerImpl;
import com.cai310.utils.Struts2Utils;

@Namespace("/" + XjEl11to5Constant.KEY)
@Action(value = "analyse")
@Results( {
	   @Result(name = "index", location = "/WEB-INF/content/analyse/"+XjEl11to5Constant.KEY+"/index.ftl"),
	   @Result(name = "hezhi", location = "/WEB-INF/content/analyse/"+XjEl11to5Constant.KEY+"/hezhi.ftl"),
	   @Result(name = "zhzs", location = "/WEB-INF/content/analyse/"+XjEl11to5Constant.KEY+"/zhzs.ftl"),
	   @Result(name = "qezs", location = "/WEB-INF/content/analyse/"+XjEl11to5Constant.KEY+"/qezs.ftl"),
	   @Result(name = "qszs", location = "/WEB-INF/content/analyse/"+XjEl11to5Constant.KEY+"/qszs.ftl"),
	   @Result(name = "lhzs", location = "/WEB-INF/content/analyse/"+XjEl11to5Constant.KEY+"/lhzs.ftl"),
	   @Result(name = "jgh", location = "/WEB-INF/content/analyse/"+XjEl11to5Constant.KEY+"/jgh.ftl"),
	   @Result(name = "chpl", location = "/WEB-INF/content/analyse/"+XjEl11to5Constant.KEY+"/chpl.ftl"),
	   @Result(name = "yilou", location = "/WEB-INF/content/analyse/"+XjEl11to5Constant.KEY+"/yilou.ftl"),
	   @Result(name = "qextzs", location = "/WEB-INF/content/analyse/"+XjEl11to5Constant.KEY+"/qextzs.ftl"),
		@Result(name = "qhzs", location = "/WEB-INF/content/analyse/"+ XjEl11to5Constant.KEY + "/qhzs.ftl"),
	   @Result(name = "qshz", location = "/WEB-INF/content/analyse/"+XjEl11to5Constant.KEY+"/qshz.ftl")
	})
public class XjEl11to5AnalyseController extends AnalyseController{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3026041191747739992L;
	
	@Autowired
	private XjEl11to5MissDataControllerServiceImpl el11to5MissDataControllerServiceImpl;

	@Autowired
	private XjEl11to5MissDataEntityManagerImpl missDataEntityManager;
	
	private int jo;
	
	private int dx;
	
	private int zh;
	
	private String type;
	
	private String count;
	
	private String order = "key";
	
	private int desc=1;
	@Override
	protected MissDataControllerService getSchemeService() {
		return el11to5MissDataControllerServiceImpl;
	}
	@Override
	public Lottery getLotteryType() {
		return Lottery.XJEL11TO5;
	}
	public String zhzs(){
		loadForecastAndSkillsNewsList();
	     return "kuadu";
	}
	public String qezs(){
		loadForecastAndSkillsNewsList();
	     return "qezs";
	}
	public String qextzs(){
		loadForecastAndSkillsNewsList();
	     return "qextzs";
	}
	public String qszs(){
		loadForecastAndSkillsNewsList();
	     return "qszs";
	}
	public String qhzs() {
		loadForecastAndSkillsNewsList();
		return "qhzs";
	}
	public String qshz(){
		loadForecastAndSkillsNewsList();
	     return "qshz";
	}
	public String lhzs(){
		loadForecastAndSkillsNewsList();
	     return "lhzs";
	}
	public String jgh(){
		loadForecastAndSkillsNewsList();
	     return "jgh";
	}
	public String chpl(){
		loadForecastAndSkillsNewsList();
	     return "chpl";
	}
	public String g3Miss(){
		loadForecastAndSkillsNewsList();
	     return "g3_miss";
	}
	public String yilou(){
		RandomNumMiss ns=missDataEntityManager.getRandomNumMiss(getLotteryType(), type);
		Map<String, HashMap<String, Long>> map=null;
		String bp = null;
		String ep = null;
		if(StringUtils.isBlank(count)){
			map=ns.getMissDataMap();
			bp=ns.getBeginPeriodNumber();
		}else if("100".equals(count)){
			map=ns.getMissData100Map();
		}else if("200".equals(count)){
			map=ns.getMissData200Map();
		}else if("500".equals(count)){
			map=ns.getMissData500Map();
		}else if("1000".equals(count)){
			map=ns.getMissData1000Map();
		}else{
			map=ns.getMissDataMap();
			bp=ns.getBeginPeriodNumber();
		}
		double round=map.size();
		if("RAND2".equals(type)){
			round=5.5;
		}else if("RAND3".equals(type)){
			round=16.5;
		}else if("RAND4".equals(type)){
			round=66;
		} else if ("RAND6".equals(type)) {
			round = 77;
		} else if ("RAND7".equals(type)) {
			round = 22;
		} else if ("RAND8".equals(type)) {
			round = 8.25;
		}
		int tc=0;
		List<YilouBean> beanList=new ArrayList<YilouBean>();
		for(Entry<String, HashMap<String, Long>> entry:map.entrySet()){
			HashMap<String, Long> value=entry.getValue();
			YilouBean bean=new YilouBean();
			bean.setKey(entry.getKey());
			if (bp == null) {
				bp = value.get("ibp")+"";
			}
			if (ep == null) {
				ep = value.get("iep")+"";
			}
			tc=Long.valueOf(value.get("tc")+"").intValue();
			bean.setLastYilou(Long.valueOf(value.get("ls")+"").intValue());
			bean.setCurYilou(Long.valueOf(value.get("cs")+"").intValue());
			bean.setShow(Long.valueOf(value.get("s")+"").intValue());
			bean.setWillShow(tc/round);
			bean.setShowPercent(bean.getShow()/(double)tc);
			double avg=(tc-bean.getShow())/(bean.getShow()+1d);
			if(avg<0.006){
				avg=bean.getCurYilou();
			}
			bean.setAvgYilou(avg);
			bean.setMaxYilou(Long.valueOf(value.get("mx")+"").intValue());
			bean.setWillHappen(bean.getCurYilou()/bean.getAvgYilou());
			bean.setHuibu((bean.getLastYilou()-bean.getCurYilou())/round);
			bean.setPeriodAbout(value.get("mb")+"-"+value.get("me"));
			beanList.add(bean);
		}
		
		Collections.sort(beanList,new Comparator<YilouBean>(){
			@Override
			public int compare(YilouBean o1, YilouBean o2) {
				int compare=0;
				if("key".equals(order)){
					compare=o1.getKey().compareTo(o2.getKey());
				}else if("mx".equals(order)){
					compare=o1.getMaxYilou()<o2.getMaxYilou()?1:-1;
				}else if("ls".equals(order)){
					compare=o1.getLastYilou()<o2.getLastYilou()?1:-1;
				}else if("cs".equals(order)){
					compare=o1.getCurYilou()<o2.getCurYilou()?1:-1;
				}else if("avg".equals(order)){
					compare=o1.getAvgYilou()<o2.getAvgYilou()?1:-1;
				}else if("wo".equals(order)){
					compare=o1.getWillHappen()<o2.getWillHappen()?1:-1;
				}else if("sper".equals(order)){
					compare=o1.getShowPercent()<o2.getShowPercent()?1:-1;
				}else if("s".equals(order)){
					compare=o1.getShow()<o2.getShow()?1:-1;
				} else if ("huibu".equals(order)) {
					compare=o1.getHuibu() < o2.getHuibu() ? 1 : -1;
				} else{
					compare=o1.getKey().compareTo(o2.getKey());
				}
				if(desc<0){
					return compare*-1;
				}else{
					return compare;
				}
			}
			
		});
		Struts2Utils.setAttribute("beanList", beanList);
		Struts2Utils.setAttribute("totalPeriod", ns.getTotalPeriod());
		if (StringUtils.isBlank(count)) {
			Struts2Utils.setAttribute("bp", ns.getBeginPeriodNumber());
			Struts2Utils.setAttribute("ep", ns.getLastPeriodNumber());
		}else{
			Struts2Utils.setAttribute("bp", bp);
			Struts2Utils.setAttribute("ep", ep);
		}
		
		loadForecastAndSkillsNewsList();

		return "yilou";
	}
	
	public String calAvgMiss(Integer size,Integer out,Integer cs){
		double avg=(size-out)/(out+1d);
		DecimalFormat df=new DecimalFormat("#0.00");
		if(avg<0.006){
			avg=cs;
		}
		return df.format(avg);
	}
	public String calWillOut(Integer size,Integer out,Integer cs){
		double avg=(size-out)/(out+1d);
		if(avg<=0.005){
			avg=cs;
		}
		if(avg<=0){
			return "-";
		}
		double willout=cs/avg;
		DecimalFormat df=new DecimalFormat("#0.00");
		return df.format(willout);
	}
	
	public String divFormat(Integer size,double out){
		if(out==0){
			return "-";
		}
		double avg=size/out;
		DecimalFormat df=new DecimalFormat("#0.00");
		return df.format(avg);
	}
	public String divPerCentFormat(Integer size,double out){
		if(out==0){
			return "-";
		}
		double avg=size/out;
		DecimalFormat df=new DecimalFormat("#0.00%");
		return df.format(avg);
	}
	
	public String qezx(){
		loadForecastAndSkillsNewsList();
		return "qezx";
	}
	public int getJo() {
		return jo;
	}
	public void setJo(int jo) {
		this.jo = jo;
	}
	public int getDx() {
		return dx;
	}
	public void setDx(int dx) {
		this.dx = dx;
	}
	public int getZh() {
		return zh;
	}
	public void setZh(int zh) {
		this.zh = zh;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	} 
	public int getDesc() {
		return desc;
	}
	public void setDesc(int desc) {
		this.desc = desc;
	}
}
