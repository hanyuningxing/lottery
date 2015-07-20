package com.cai310.lottery.service.lottery.seven.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.cai310.lottery.entity.lottery.seven.SevenMissDataInfo;
import com.cai310.lottery.entity.lottery.seven.SevenPeriodData;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.MissDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.service.lottery.impl.MissDataControllerServiceImpl;
import com.cai310.utils.WriteHTMLUtil;
@Service("sevenMissDataControllerServiceImpl")
public class SevenMissDataControllerServiceImpl extends MissDataControllerServiceImpl<SevenMissDataInfo, SevenPeriodData> {

	@Autowired
	private SevenMissDataEntityManagerImpl missDataEntityManager;
	@Autowired
	private SevenPeriodDataEntityManagerImpl periodDataEntityManagerImpl;

	@Override
	public List<MissDataFilter> createFilters() {
		List<MissDataFilter> list = new ArrayList<MissDataFilter>();
		// 红球数字遗漏项
		MissDataFilter filter = null;
		for (int i = 1; i <= 30; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					arr = (String[]) ArrayUtils.subarray(arr, 0, 6);
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
		// 蓝球数字遗漏项
		for (int i = 1; i <= 30; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int blue = Integer.parseInt(arr[7]);
					if (blue == num) {
						return true;
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
		// 蓝球单双遗漏项
		filter = new MissDataFilter() {

			@Override
			public boolean accect(String results) {
				String[] arr = results.split(",");
				int blue = Integer.parseInt(arr[6]);
				if (blue % 2 == 0) {
					return true;
				}
				return false;
			}

			@Override
			public String getFieldName() {
				return "blueS";
			}

		};
		list.add(filter);
		// 单
		filter = new MissDataFilter() {

			@Override
			public boolean accect(String results) {
				String[] arr = results.split(",");
				int blue = Integer.parseInt(arr[6]);
				if (blue % 2 != 0) {
					return true;
				}
				return false;
			}

			@Override
			public String getFieldName() {
				return "blueD";
			}

		};
		list.add(filter);

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
		sb.append(arr[6]).append("-");
		sb.append(arr[7]);
		return sb.toString();
	}

	@Override
	public void createMissFiles() {
		JSONObject resultRoot = new JSONObject();
		JSONObject redRoot = new JSONObject();
		JSONObject blueRoot = new JSONObject();
		List<SevenMissDataInfo> list = missDataEntityManager.getLastMissDatas(200);
		Collections.sort(list, new Comparator() {      
    		public int compare(Object o1, Object o2) {       
    			return Integer.valueOf(((SevenMissDataInfo) o2).getPeriodNumber()).intValue()-Integer.valueOf(((SevenMissDataInfo) o1).getPeriodNumber()).intValue();
            }});    
		
		
		Collections.reverse(list);
		JSONObject redmiss = new JSONObject();
		JSONObject bluemiss = new JSONObject();
		// 创建红球遗漏文件
		for (SevenMissDataInfo info : list) {
			JSONObject miss = new JSONObject();
			Map<String, Integer> map = info.getMissDataMap();// .getMissDataContent().toMap();
			for (int i = 1; i <= 30; i++) {
				String field = "red" + i;
				miss.put(i, map.get(field));
			}
			redRoot.put(info.getPeriodNumber(), miss);
			resultRoot.put(info.getPeriodNumber(), formatResult(info.getResult()));
			redmiss=miss;
		}
		for (SevenMissDataInfo info : list) {
			JSONObject miss = new JSONObject();
			Map<String, Integer> map = info.getMissDataMap();// .getMissDataContent().toMap();
			for (int i = 1; i <= 30; i++) {
				String field = "blue" + i;
				miss.put(i, map.get(field));
			}
			blueRoot.put(info.getPeriodNumber(), miss);
			bluemiss=miss;
		}
		createMaxMissFiles(redmiss,bluemiss);
		
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "sevendata.js", "dataSeven="+resultRoot.toString()+";function getdata(){return dataShuangseqiu;}", "UTF-8");
		
	}

	@Override
	public Lottery getLottery() {
		return Lottery.SEVEN;
	}

	@Override
	public MissDataEntityManager<SevenMissDataInfo> getMissDataManager() {
		return missDataEntityManager;
	}

	@Override
	public PeriodDataEntityManager<SevenPeriodData> getPeriodDataEntityManager() {
		return periodDataEntityManagerImpl;
	}

	@Override
	public List<AnalyseFilter> getAnalyseFilters(NumberAnalyse ns) {
		List<AnalyseFilter> list = new ArrayList<AnalyseFilter>();
		// 红球冷热分析项
		list.add(new AnalyseFilter("red", ns, 1, 30) {

			@Override
			protected String[] getNeedResult(String results) {
				String[] arr = results.split(",");
				return (String[]) ArrayUtils.subarray(arr, 0, 6);
			}

		});
		// 蓝球冷热分析项
		list.add(new AnalyseFilter("blue", ns, 1, 30) {

			@Override
			protected String[] getNeedResult(String results) {
				String[] arr = results.split(",");
				return (String[]) ArrayUtils.subarray(arr, 6, 7);
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
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/","seven_max_miss.js", "seven_max_miss="+root+";", "UTF-8");
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
		SevenPeriodData periodData= getPeriodDataEntityManager().getNewestResultPeriodData();
		if(null!=periodData&&StringUtils.isNotBlank(periodData.getResult())){
			Period period =periodEntityManger.getPeriod(periodData.getPeriodId());
			contents.put("period", period);
			contents.put("periodData", periodData);
			createRightFile(contents, "rightContent.ftl", "rightContent.html",period);
		}
	}
}
