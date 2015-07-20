package com.cai310.lottery.service.lottery.welfare36to7.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.miss.AnalyseFilter;
import com.cai310.lottery.common.miss.MapSortUtil;
import com.cai310.lottery.common.miss.MissDataFilter;
import com.cai310.lottery.entity.lottery.NumberAnalyse;
import com.cai310.lottery.entity.lottery.Period;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7MissDataInfo;
import com.cai310.lottery.entity.lottery.welfare36to7.Welfare36to7PeriodData;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.MissDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.service.lottery.impl.MissDataControllerServiceImpl;
import com.cai310.utils.WriteHTMLUtil;
@Service("welfare36to7MissDataControllerServiceImpl")
public class Welfare36to7MissDataControllerServiceImpl extends MissDataControllerServiceImpl<Welfare36to7MissDataInfo, Welfare36to7PeriodData> {

	@Autowired
	private Welfare36to7MissDataEntityManagerImpl missDataEntityManager;
	@Autowired
	private Welfare36to7PeriodDataEntityManagerImpl periodDataEntityManagerImpl;

	@Override
	public List<MissDataFilter> createFilters() {
		List<MissDataFilter> list = new ArrayList<MissDataFilter>();
		// 特别号码遗漏项
		for (int i = 1; i <= 36; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					if (Integer.parseInt(arr[6]) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "num" + num;
				}

			};
			list.add(filter);
		}
		
		return list;
	}
	
	private String formatResult(String result){
		String[] arr=result.split(",");
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<arr.length;i++){
			if(arr[i].length()<2){
				arr[i]="0"+arr[i];
			}
		}
		sb.append(arr[0]).append(" ");
		sb.append(arr[1]).append(" ");
		sb.append(arr[2]).append(" ");
		sb.append(arr[3]).append(" ");
		sb.append(arr[4]).append(" ");
		sb.append(arr[5]).append(" ");
		sb.append(arr[6]);
		return sb.toString();
	}

	@Override
	public void createMissFiles() {
		JSONObject resultRoot = new JSONObject();
		JSONObject redRoot = new JSONObject();
		List<Welfare36to7MissDataInfo> list = missDataEntityManager.getLastMissDatas(200);
		Collections.sort(list, new Comparator() {      
    		public int compare(Object o1, Object o2) {       
    			return Integer.valueOf(((Welfare36to7MissDataInfo) o2).getPeriodNumber()).intValue()-Integer.valueOf(((Welfare36to7MissDataInfo) o1).getPeriodNumber()).intValue();
            }});    
		
		
		Collections.reverse(list);
		JSONObject redmiss = new JSONObject();
		// 创建特别号码遗漏文件
		for (Welfare36to7MissDataInfo info : list) {
			JSONObject miss = new JSONObject();
			Map<String, Integer> map = info.getMissDataMap();// .getMissDataContent().toMap();
			for (int i = 1; i <= 36; i++) {
				String field = "num" + i;
				miss.put(i, null==map?0:map.get(field));
			}
			redRoot.put(info.getPeriodNumber(), miss);
			resultRoot.put(info.getPeriodNumber(), formatResult(info.getResult()));
			redmiss=miss;
		}
		createMaxMissFiles(redmiss);
		
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "welfare36to7data.js", "dataWelfare36to7="+resultRoot.toString()+";function getdata(){return dataWelfare36to7;}", "UTF-8");
		
	}

	@Override
	public Lottery getLottery() {
		return Lottery.WELFARE36To7;
	}

	@Override
	public MissDataEntityManager<Welfare36to7MissDataInfo> getMissDataManager() {
		return missDataEntityManager;
	}

	@Override
	public PeriodDataEntityManager<Welfare36to7PeriodData> getPeriodDataEntityManager() {
		return periodDataEntityManagerImpl;
	}

	@Override
	public List<AnalyseFilter> getAnalyseFilters(NumberAnalyse ns) {
		List<AnalyseFilter> list = new ArrayList<AnalyseFilter>();
		// 特别号码冷热分析项
		list.add(new AnalyseFilter("num", ns, 1, 36) {

			@Override
			protected String[] getNeedResult(String results) {
				String[] arr = results.split(",");
				return arr;
			}

		});
		return list;
	}

	@Override
	public void updateGroupNumMiss() {
	}
	
	private void createMaxMissFiles(JSONObject redmiss){
		JSONObject  root = new JSONObject();
    	Map<String, Integer> missDataMap = MapSortUtil.sortByIntegerValueDesc(redmiss);
		root.put("redb", getMaxMissObj(missDataMap));
	    missDataMap = MapSortUtil.sortByIntegerValueAsc(redmiss);
		root.put("reds", getMaxMissObj(missDataMap));
		
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/","welfare36to7_max_miss.js", "welfare36to7_max_miss="+root+";", "UTF-8");
	}
	
	public JSONObject getMaxMissObj(Map<String, Integer> missDataMap){
		Set<String> keySet = missDataMap.keySet();
		JSONObject root =  new JSONObject();
		int i =0;
    	for (String key : keySet) {
    		i++;
    		JSONObject miss =  new JSONObject();
    		miss.put("name", key);
			miss.put("value", missDataMap.get(key));
			root.put(i, miss);
			if(i>2){
				break;
			}
			
		}
    	return root;
	}
	
	public void makeShuNewResult() throws DataException{
		Map<String,Object> contents=new HashMap<String,Object>();
		contents.put("base", Constant.BASEPATH);
		Welfare36to7PeriodData periodData= getPeriodDataEntityManager().getNewestResultPeriodData();
		if(null!=periodData&&StringUtils.isNotBlank(periodData.getResult())){
			Period period =periodEntityManger.getPeriod(periodData.getPeriodId());
			contents.put("period", period);
			contents.put("periodData", periodData);
			createRightFile(contents, "rightContent.ftl", "rightContent.html",period);
		}
	}
	
	public List getMissDatas(int count){
		return missDataEntityManager.getLastMissDatas(count);
	}
	
	public List getLastMissDatas(int count,String start,String end){
		return missDataEntityManager.getLastMissDatas(count,start,end);
	}
}
