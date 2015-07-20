package com.cai310.lottery.service.lottery.tc22to5.impl;

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
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5MissDataInfo;
import com.cai310.lottery.entity.lottery.tc22to5.Tc22to5PeriodData;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.MissDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.service.lottery.impl.MissDataControllerServiceImpl;
import com.cai310.utils.WriteHTMLUtil;
@Service("tc22to5MissDataControllerServiceImpl")
public class Tc22to5MissDataControllerServiceImpl extends MissDataControllerServiceImpl<Tc22to5MissDataInfo, Tc22to5PeriodData> {

	@Autowired
	private Tc22to5MissDataEntityManagerImpl missDataEntityManager;
	@Autowired
	private Tc22to5PeriodDataEntityManagerImpl periodDataEntityManagerImpl;

	@Override
	public List<MissDataFilter> createFilters() {
		List<MissDataFilter> list = new ArrayList<MissDataFilter>();
		// 红球数字遗漏项
		MissDataFilter filter = null;
		for (int i = 1; i <= 22; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					for (String s : arr) {
						if (Integer.parseInt(s) == num) {
							return true;
						}
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
		

		// 连号
		for (int i = 1; i <= 33; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					boolean curHit = false;// 本号命中
					boolean byHit = false;// 邻号命中
					for (String s : arr) {
						if (Integer.parseInt(s) == num) {
							curHit = true;
							if (byHit) {
								return true;
							}
						} else if (Integer.parseInt(s) == num - 1 || Integer.parseInt(s) == num + 1) {
							byHit = true;
							if (curHit) {
								return true;
							}
						}
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "lh" + num;
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
		sb.append(arr[4]);
		return sb.toString();
	}

	@Override
	public void createMissFiles() {
		JSONObject resultRoot = new JSONObject();
		JSONObject redRoot = new JSONObject();
		List<Tc22to5MissDataInfo> list = missDataEntityManager.getLastMissDatas(200);
		Collections.sort(list, new Comparator() {      
    		public int compare(Object o1, Object o2) {       
    			return Integer.valueOf(((Tc22to5MissDataInfo) o2).getPeriodNumber()).intValue()-Integer.valueOf(((Tc22to5MissDataInfo) o1).getPeriodNumber()).intValue();
            }});    
		
		
		Collections.reverse(list);
		JSONObject redmiss = new JSONObject();
		JSONObject bluemiss = new JSONObject();
		// 创建红球遗漏文件
		for (Tc22to5MissDataInfo info : list) {
			JSONObject miss = new JSONObject();
			Map<String, Integer> map = info.getMissDataMap();// .getMissDataContent().toMap();
			for (int i = 1; i <= 33; i++) {
				String field = "num" + i;
				miss.put(i, map.get(field));
			}
			redRoot.put(info.getPeriodNumber(), miss);
			resultRoot.put(info.getPeriodNumber(), formatResult(info.getResult()));
			redmiss=miss;
		}
		createMaxMissFiles(redmiss,bluemiss);
		
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "tc22to5data.js", "dataTc22to5="+resultRoot.toString()+";function getdata(){return dataTc22to5;}", "UTF-8");
		
	}

	@Override
	public Lottery getLottery() {
		return Lottery.TC22TO5;
	}

	@Override
	public MissDataEntityManager<Tc22to5MissDataInfo> getMissDataManager() {
		return missDataEntityManager;
	}

	@Override
	public PeriodDataEntityManager<Tc22to5PeriodData> getPeriodDataEntityManager() {
		return periodDataEntityManagerImpl;
	}

	@Override
	public List<AnalyseFilter> getAnalyseFilters(NumberAnalyse ns) {
		List<AnalyseFilter> list = new ArrayList<AnalyseFilter>();
		// 红球冷热分析项
		list.add(new AnalyseFilter("num", ns, 1, 22) {

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
	
	private void createMaxMissFiles(JSONObject redmiss,JSONObject bluemiss){
		JSONObject  root = new JSONObject();
    	Map<String, Integer> missDataMap = MapSortUtil.sortByIntegerValueDesc(redmiss);
		root.put("redb", getMaxMissObj(missDataMap));
	    missDataMap = MapSortUtil.sortByIntegerValueAsc(redmiss);
		root.put("reds", getMaxMissObj(missDataMap));
		
    	missDataMap = MapSortUtil.sortByIntegerValueDesc(bluemiss);
		root.put("blueb", getMaxMissObj(missDataMap));
	    missDataMap = MapSortUtil.sortByIntegerValueAsc(bluemiss);
		root.put("blues", getMaxMissObj(missDataMap));
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/","tc22to5_max_miss.js", "tc22to5_max_miss="+root+";", "UTF-8");
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
		Tc22to5PeriodData periodData= getPeriodDataEntityManager().getNewestResultPeriodData();
		if(null!=periodData&&StringUtils.isNotBlank(periodData.getResult())){
			Period period =periodEntityManger.getPeriod(periodData.getPeriodId());
			contents.put("period", period);
			contents.put("periodData", periodData);
			createRightFile(contents, "rightContent.ftl", "rightContent.html",period);
		}
	}
}
