package com.cai310.lottery.web.controller.lottery.dczc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cai310.lottery.DczcConstant;
import com.cai310.lottery.FetchConstant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.dczc.DczcMatch;
import com.cai310.lottery.service.lottery.PeriodEntityManager;
import com.cai310.lottery.service.lottery.dczc.DczcMatchEntityManager;
import com.cai310.lottery.support.Item;
import com.cai310.lottery.support.dczc.DczcMatchItem;
import com.cai310.lottery.support.dczc.ItemBF;
import com.cai310.lottery.support.dczc.PlayType;
import com.cai310.lottery.utils.FootBallFilterBean;
import com.cai310.lottery.web.controller.GlobalResults;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.lottery.web.controller.lottery.LotteryBaseController;
import com.cai310.utils.Struts2Utils;
@Namespace("/" + DczcConstant.KEY)
@Action(value = "filter")
@Results( {
	   @Result(name = "spf-index", location = "/WEB-INF/content/dczcgl/spf-filter.ftl"),
	   @Result(name = "filter-detail", location = "/WEB-INF/content/dczcgl/filter-detail.ftl")
})
public class DczcFilterController extends LotteryBaseController {
	
	private static final String[] SPF = new String[]{"3 -1 -1","1 -1 -1","3 1 -1","0 -1 -1","3 0 -1","1 0 -1","3 1 0"};
	private DczcSchemeCreateForm createForm;
	private List<String> spss = new ArrayList<String>();
	private List<DczcMatch> matchs = new ArrayList<DczcMatch>();
	private List matchFilterDatas =  new ArrayList();
	private String periodNumber;
	private DczcFilterForm filterForm;
	private Period period;
	@Autowired
	protected PeriodEntityManager periodEntityManager;
	
	public Period getPeriod() {
		return period;
	}
	public void setPeriod(Period period) {
		this.period = period;
	}

	private PlayType playType;

	public Lottery getLotteryType(){
		return Lottery.DCZC;
	}
 
	@Autowired
	private DczcMatchEntityManager matchEntityManager;

	@Autowired
	@Qualifier("dczcMatchCache")
	private Cache matchCache;
	
	public String spfIndex(){
		try {
			filterForm.setUnits(null==createForm.getUnits()?0:createForm.getUnits());
			filterForm.setSchemeCost(null==createForm.getSchemeCost()?0:createForm.getSchemeCost());
			if(null==filterForm||null==filterForm.getSps()){				
				throw new WebDataException("选择场次SP值不能为空");			
			}
			List<DczcMatchItem> items = createForm.getItems();
			List<DczcMatch> allMatchs = findMatchsOfCacheable(createForm.getPeriodId());
			
			if(allMatchs.isEmpty()){
				throw new WebDataException("当前期暂无数据");
			}
			
			for(DczcMatchItem item:items){
				matchs.add(allMatchs.get(item.getLineId()));
			}
			int[] ids = new int[items.size()]; 
			List<String> sps = null;
			for(int i=0;i<items.size();i++){
				
				DczcMatchItem item = items.get(i);
				DczcMatch match = matchs.get(i);
				ArrayList data = new ArrayList();
				data.add(item.getLineId());
				data.add(match.getHomeTeamName());
				data.add(match.getHandicap());
				data.add(match.getGuestTeamName());
				
				ids[i]=item.getLineId();
				String spfs = SPF[item.getValue()-1];
				
				String[] spf = spfs.split("\\s");
				for(String s:spf){
					data.add(s);
				}
				
				//sp值
				sps = filterForm.getSps();	
				String[] spData = sps.get(i).split(",");			
				data.add(spData[0]);
				data.add(spData[1]);
				data.add(spData[2]);
				
				matchFilterDatas.add(data);
			}
			Struts2Utils.setAttribute("ids", ids);
			Struts2Utils.setAttribute("size", items.size());
			period = periodEntityManager.getPeriod(createForm.getPeriodId());
			
			return "spf-index";
		} catch (WebDataException e) {
			e.printStackTrace();
		}
		return GlobalResults.FWD_ERROR;
	
	}
	/**
	 * 过滤数据
	 * @return
	 */
	public String filter(){
		HashMap result = new HashMap();
		List<String> target = null;
		try {
			if(null==filterForm||null==filterForm.getSpfs()||filterForm.getSpfs().size()<1){				
				result.put("success", false);
				result.put("msg", "过滤数据不能为空");
				Struts2Utils.renderJson(result);
				return null;
			} 
			List<String> sps = filterForm.getSp();
			if(null==sps){				
				result.put("success", false);
				result.put("msg", "SP值不能为空");
				Struts2Utils.renderJson(result);
				return null;
			} 
			List datas = (ArrayList) filterForm.getSpfs();

			ArrayList<String> conditions = (ArrayList) filterForm.getCondition();	
			ArrayList<String> rcCondtions = (ArrayList)filterForm.getRcCondtion();
			ArrayList<String> jhCondtions = (ArrayList)filterForm.getJhCondtion();

			if(conditions.isEmpty()||rcCondtions.isEmpty()){	
				result.put("success", false);
				result.put("msg", "过滤条件不能为空");
				Struts2Utils.renderJson(result);
				return null;
			} 
			FootBallFilterBean filter = new FootBallFilterBean(datas);
			ArrayList<Integer> stepCount = new ArrayList<Integer>();//记录每步处理的剩余注数
			if(null==filter){
				throw new WebDataException("创建对象失败");
			}
			if(null!=filter.getSource()){
				String[] firstLine = filter.getSource().get(0);			
				filter.collectionAllRow(firstLine, "");//数组全排
				if(filter.getTarget().size()>=FetchConstant.MAX_FILTER){//限制过滤条目过大
					result.put("success", false);
					result.put("msg", "处理数据大于100万条,请调整后再过滤!");
					Struts2Utils.renderJson(result);
					return null;
				}
				for(int step = 0;step<rcCondtions.size();step++){//循环遍历每步
					String rcnd = rcCondtions.get(step);
					String[] rcs = rcnd.split(",");
					//+hashObjArray[i].ltCond+","+hashObjArray[i].gtCond+","+hashObjArray[i].step+","+hashObjArray[i].totalFaultCount;
					ArrayList<String> newConditon = new ArrayList<String>();
					if(null!=conditions&&conditions.size()>=1){//取出同一步的过滤条件
						for(String condition:conditions){
							String[] cond = condition.split(",");
							if(rcs[2].equals(cond[3])){//判断是否当前步骤
								newConditon.add(condition);
							}
						}
					}
					
					if(null!=newConditon&&newConditon.size()>=1){//循环条件过滤数据		
						HashMap extCondMap = arrangeSps(sps,datas);
						extCondMap.put("spfDatas", datas);
						extCondMap.put("sps", sps);
						if(new Integer(rcs[3])>0){//容错处理
							Integer ltValue = new Integer(rcs[0]);
							Integer gtValue = new Integer(rcs[1]);
							target = faultTolerantFilter(filter,newConditon,ltValue,gtValue,extCondMap);							
							filter.setTarget(target);
						}else{
							normalFilter(filter,newConditon,extCondMap);
							target = filter.getAfterFilterSource();//获取过滤后数据
						}				
					}
					stepCount.add(target.size());
				}				
								
				result.put("success", true);
				result.put("datas", target);
				result.put("count", stepCount);
				result.put("finallyCount", target.size());
				result.put("totalCost", target.size()*2);
				result.put("msg", "处理完毕.过滤前:"+filterForm.getUnits()+"注,处理后:"+target.size()+"注.");
				Struts2Utils.renderJson(result);
				return null;
			}
		}catch (WebDataException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "过滤处理失败");
			Struts2Utils.renderJson(result);
		}			
		return null;
	}
	
	
	private void normalFilter(FootBallFilterBean filter,ArrayList<String> conditions,HashMap extCondMap){
		for(String condition:conditions){
			String[] cond = condition.split(",");
			extCondMap.put("jhCondition", condition);
			filter.filter(filter.getTarget(),cond,extCondMap);
		}
	}
	
	private List faultTolerantFilter(FootBallFilterBean filter,ArrayList<String> conditions,int ltValue,int gtValue,HashMap extCondMap){
		List<String> target = null;
		List<String> datas = filter.getTarget();
		List<String> newDatas = new ArrayList<String>();
		List<String> originalDatas = (List<String>) extCondMap.get("spfDatas");
		
		for(String data:datas){
			int count = 0;//容错计数器
			int size = 0;
			for(String condition:conditions){//循环所有过滤条件			
				String[] cond = condition.split(",");
				String key = cond[4];
				String[] items = this.arrangeParams(originalDatas, key);
				if(((key.equals("3")||key.equals("1")||key.equals("0"))&&!filter.filterGs(data,cond))||
						(key.equals("hz")&&!filter.filterHz(data, cond))||(key.equals("dd")&&!filter.filterDd(data, cond))||
						(key.equals("lh")&&!filter.filterLh(data, cond))||(key.equals("ls3")&&!filter.filterLs(data, cond,"3"))||
						(key.equals("ls1")&&!filter.filterLs(data, cond,"1"))||(key.equals("ls0")&&!filter.filterLs(data, cond,"0"))||
						(key.equals("bls3")&&!filter.filterBls(data, cond,"3"))||(key.equals("bls1")&&!filter.filterBls(data, cond,"1"))||
						(key.equals("bls0")&&!filter.filterBls(data, cond,"0"))||((key.equals("sxmz")||key.equals("cxmz")||key.equals("mxmz"))&&!filter.filterMz(data, cond, items))||
						((key.equals("zsh")&&!filter.filterZsh(data, cond, extCondMap))||(key.equals("zsj")&&!filter.filterZsj(data, cond, extCondMap))||(key.equals("jjfw")&&!filter.filterJjfw(data, cond, extCondMap)))||
						((key.equals("dyzs")&&!filter.filterSpmz(data,cond,extCondMap,"dyzs"))||(key.equals("dezs")&&!filter.filterSpmz(data,cond,extCondMap,"dezs"))||(key.equals("dszs")&&!filter.filterSpmz(data,cond,extCondMap,"dszs")))
				){//如果不符合条件，则判断是否容错
					if(condition.indexOf("true")>0){//是容错条件
						count++;
						size++;
					}else{
						break;
					}
				}else if(key.indexOf("ccmz")!=-1||key.indexOf("lmgl")!=-1||key.indexOf("djgl")!=-1){//集合过滤
					String[] jhConds = condition.split("\\|");
					boolean flag = false;
					for(int i=1;i<jhConds.length;i++){
						String[] tempsTemp = jhConds[i].split(":");
						String[] items1 = tempsTemp[0].split(",");
						String[] conds1 = tempsTemp[1].split("-");
						if(!filter.filterCcMz(data,conds1,items1)){
							if(!flag&&condition.indexOf("true")>0){//是容错条件
								count++;
								size++;
								flag = true;
							}else{
								break;
							}
						}
					}
					flag = false;
				}else{
					size++;
				}
			}
			if(count>=ltValue&&count<=gtValue&&size==conditions.size()){
				newDatas.add(data);
			}
		}
		return newDatas;	
	}
	
	private String[] arrangeParams(List<String> datas,String key){
		String[] items = new String[datas.size()];
		for(int i=0;i<items.length;i++){
			String data = (String) datas.get(i);
			String[] spells = data.split(",");
			if(key.equals("sxmz")){
				items[i] = spells[0];
			}else if(key.equals("cxmz")){
				if(data.length()>=2){
					items[i] = spells[1];
				}else{
					items[i] = "-1";
				}
			}else if(key.equals("mxmz")){
				if(data.length()>=3){
					items[i] = spells[2];
				}else{
					items[i] = "-1";
				}
			}
		}
		return items;
	}
	
	private HashMap arrangeSps(List<String> sps,List datas){
		HashMap map = new HashMap();
		ArrayList<Double> everyMatchSp = null;
		ArrayList<Double> allMatchSp = new ArrayList<Double>();
		double[] minSps = new double[sps.size()]; 
		double[] maxSps = new double[sps.size()]; 
		double[] midSps = new double[sps.size()];
		
		for(int i=0;i<datas.size();i++){
			everyMatchSp = new ArrayList<Double>();
			String[] tempValues = sps.get(i).split(",");
			for(String temp:tempValues){
				everyMatchSp.add(new Double(temp));
				allMatchSp.add(new Double(temp));
			}	
			Collections.sort(everyMatchSp);
			minSps[i] = everyMatchSp.get(0);
			midSps[i] = everyMatchSp.get(1);
			maxSps[i] = everyMatchSp.get(everyMatchSp.size()-1);
		}
		Collections.sort(allMatchSp);
		map.put("minSps", minSps);
		map.put("midSps", midSps);
		map.put("maxSps", maxSps);
		map.put("allMatchSp",allMatchSp);
		map.put("originaSps",sps);
		return map;
	}
	
	public DczcSchemeCreateForm getCreateForm() {
		return createForm;
	}

	public void setCreateForm(DczcSchemeCreateForm createForm) {
		this.createForm = createForm;
	}

	public List<String> getSpss() {
		return spss;
	}

	public void setSpss(List<String> spss) {
		this.spss = spss;
	}

	public String getPeriodNumber() {
		return periodNumber;
	}

	public void setPeriodNumber(String periodNumber) {
		this.periodNumber = periodNumber;
	}
	
	@SuppressWarnings("unchecked")
	protected List<DczcMatch> findMatchsOfCacheable(Long periodId) {
		Element el = matchCache.get(periodId);
		if (el == null) {
			List<DczcMatch> matchs = matchEntityManager.findMatchs(periodId);
			if (matchs != null)
				matchCache.put(new Element(periodId, matchs));
			return matchs;
		} else {
			return (List<DczcMatch>) el.getValue();
		}
	}

	public List<DczcMatch> getMatchs() {
		return matchs;
	}

	public void setMatchs(List<DczcMatch> matchs) {
		this.matchs = matchs;
	}

	public List getMatchFilterDatas() {
		return matchFilterDatas;
	}

	public void setMatchFilterDatas(List matchFilterDatas) {
		this.matchFilterDatas = matchFilterDatas;
	}

	public DczcFilterForm getFilterForm() {
		return filterForm;
	}

	public void setFilterForm(DczcFilterForm filterForm) {
		this.filterForm = filterForm;
	}

	public PlayType getPlayType() {
		return playType;
	}

	public void setPlayType(PlayType playType) {
		this.playType = playType;
	}
	
	public String filterDetailShow(){
		ArrayList<ArrayList<String>> matchDatas = new ArrayList();
		for(int i=0;i<matchFilterDatas.size();i++){
			String[] datas = (String[])matchFilterDatas.get(i);
			ArrayList<String> everyMatch = new ArrayList<String>();
			for(String temp:datas){
				everyMatch.add(temp);
			}
			matchDatas.add(everyMatch);
		}
		
		if (playType == null)
			playType = PlayType.SPF;

		Item[] itemArr = playType.getAllItems();
		if (playType == PlayType.BF) {
			Map<String, Item[]> itemMap = new LinkedHashMap<String, Item[]>();
			itemMap.put("胜", ItemBF.WINS);
			itemMap.put("平", ItemBF.DRAWS);
			itemMap.put("负", ItemBF.LOSES);
			Struts2Utils.setAttribute("itemMap", itemMap);
			Struts2Utils.setAttribute("itemColspan", ItemBF.WINS.length);
		}
		Struts2Utils.setAttribute("itemArr", itemArr);
		
		period = periodEntityManager.getPeriod(createForm.getPeriodId());
		Struts2Utils.setAttribute("matchDatas", matchDatas);
		return "filter-detail";
	}
	
	
	public String viewDetails(){
		return "content-detail";
	}
}
