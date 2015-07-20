package com.cai310.lottery.service.lottery.dlt.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
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
import com.cai310.lottery.entity.lottery.dlt.DltMissDataInfo;
import com.cai310.lottery.entity.lottery.dlt.DltPeriodData;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.MissDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.service.lottery.impl.MissDataControllerServiceImpl;
import com.cai310.utils.WriteHTMLUtil;
@Service("dltMissDataControllerServiceImpl")
public class DltMissDataControllerServiceImpl extends MissDataControllerServiceImpl<DltMissDataInfo, DltPeriodData> {

	@Autowired
	private DltMissDataEntityManagerImpl missDataEntityManager;
	@Autowired
	private DltPeriodDataEntityManagerImpl periodDataEntityManagerImpl;

	@Override
	public List<MissDataFilter> createFilters() {
		List<MissDataFilter> list = new ArrayList<MissDataFilter>();
		// 前区数字遗漏项
		for (int i = 1; i <= 35; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					arr = (String[]) ArrayUtils.subarray(arr, 0, 5);
					for (String s : arr) {
						if (Integer.parseInt(s) == num) {
							return true;
						}
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "red" + num;
				}

			};
			list.add(filter);
		}
		// 后区数字遗漏项
		for (int i = 1; i <= 12; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					arr = (String[]) ArrayUtils.subarray(arr, 5, 7);
					for (String s : arr) {
						if (Integer.parseInt(s) == num) {
							return true;
						}
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "blue" + num;
				}

			};
			list.add(filter);
		}
		
		for(int i=0;i<5;i++){
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					arr = (String[]) ArrayUtils.subarray(arr, 0, 5);
					int curNum=Integer.parseInt(arr[num]);
					for (String s : arr) {
						if (Math.abs(Integer.parseInt(s) - curNum)==1) {
							return true;
						}
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "lian" + num;
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
		sb.append(arr[4]).append("-");
		sb.append(arr[5]).append(" ");
		sb.append(arr[6]);
		return sb.toString();
	}

	@Override
	public void createMissFiles() {
		JSONObject resultRoot = new JSONObject();
		JSONObject redRoot = new JSONObject();
		JSONObject blueRoot = new JSONObject();
//		JSONObject lianRoot = new JSONObject();//位置连号遗漏
		List<DltMissDataInfo> list = missDataEntityManager.getLastMissDatas(200);
		Collections.reverse(list);
		JSONObject redmiss = new JSONObject();
		JSONObject bluemiss = new JSONObject();
		// 创建红球遗漏文件
		for (DltMissDataInfo info : list) {
			JSONObject miss = new JSONObject();
			Map<String, Integer> map = info.getMissDataMap();// .getMissDataContent().toMap();
			for (int i = 1; i <= 35; i++) {
				String field = "red" + i;
				miss.put(i, map.get(field));
			}
			redRoot.put(info.getPeriodNumber(), miss);
			resultRoot.put(info.getPeriodNumber(), formatResult(info.getResult()));
			redmiss=miss;
		}
		for (DltMissDataInfo info : list) {
			JSONObject miss = new JSONObject();
			Map<String, Integer> map = info.getMissDataMap();// .getMissDataContent().toMap();
			for (int i = 1; i <= 12; i++) {
				String field = "blue" + i;
				miss.put(i, map.get(field));
			}
			blueRoot.put(info.getPeriodNumber(), miss);
			bluemiss=miss;
		}
		createMaxMissFiles(redmiss,bluemiss);
		
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "dltdata.js", "Dltdata="+resultRoot.toString()+";function getdata(){return Dltdata;}", "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "dlt_red.js", "Dltred="+redRoot.toString(), "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "dlt_blue.js", "Dltblue="+blueRoot.toString(), "UTF-8");
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
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/","dlt_max_miss.js", "dlt_max_miss="+root+";", "UTF-8");
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

	@Override
	public Lottery getLottery() {
		return Lottery.DLT;
	}

	@Override
	public MissDataEntityManager<DltMissDataInfo> getMissDataManager() {
		return missDataEntityManager;
	}

	@Override
	public PeriodDataEntityManager<DltPeriodData> getPeriodDataEntityManager() {
		return periodDataEntityManagerImpl;
	}

	@Override
	public List<AnalyseFilter> getAnalyseFilters(NumberAnalyse ns) {
		List<AnalyseFilter> list = new ArrayList<AnalyseFilter>();
		// 前区冷热分析项
		list.add(new AnalyseFilter("red", ns, 1, 35) {

			@Override
			protected String[] getNeedResult(String results) {
				String[] arr = results.split(",");
				return (String[]) ArrayUtils.subarray(arr, 0, 5);
			}

		});
		// 后区冷热分析项
		list.add(new AnalyseFilter("blue", ns, 1, 12) {

			@Override
			protected String[] getNeedResult(String results) {
				String[] arr = results.split(",");
				return (String[]) ArrayUtils.subarray(arr, 5, 7);
			}

		});
		return list;
	}

	@Override
	public void updateGroupNumMiss() {
	}
	
	public void makeShuNewResult() throws DataException{
		Map<String,Object> contents=new HashMap<String,Object>();
		contents.put("base", Constant.BASEPATH);
		DltPeriodData periodData= getPeriodDataEntityManager().getNewestResultPeriodData();
		if(null!=periodData&&StringUtils.isNotBlank(periodData.getResult())){
			Period period =periodEntityManger.getPeriod(periodData.getPeriodId());
			contents.put("period", period);
			contents.put("periodData", periodData);
			createRightFile(contents, "rightContent.ftl", "rightContent.html",period);
		}
	}
}
