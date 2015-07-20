package com.cai310.lottery.service.lottery.ssq.impl;

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
import com.cai310.lottery.entity.lottery.ssq.SsqMissDataInfo;
import com.cai310.lottery.entity.lottery.ssq.SsqPeriodData;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.MissDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.service.lottery.impl.MissDataControllerServiceImpl;
import com.cai310.utils.WriteHTMLUtil;
@Service("ssqMissDataControllerServiceImpl")
public class SsqMissDataControllerServiceImpl extends MissDataControllerServiceImpl<SsqMissDataInfo, SsqPeriodData> {

	@Autowired
	private SsqMissDataEntityManagerImpl missDataEntityManager;
	@Autowired
	private SsqPeriodDataEntityManagerImpl periodDataEntityManagerImpl;

	@Override
	public List<MissDataFilter> createFilters() {
		List<MissDataFilter> list = new ArrayList<MissDataFilter>();
		// 红球数字遗漏项
		MissDataFilter filter = null;
		for (int i = 1; i <= 33; i++) {
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
		for (int i = 1; i <= 16; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int blue = Integer.parseInt(arr[6]);
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

		// 红球大小比
		for (int i = 0; i <= 6; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int bigC = 0;
					int smallC = 0;
					for (int j = 0; j < 6; j++) {
						if (Integer.parseInt(arr[j]) > 16) {
							bigC++;
						} else {
							smallC++;
						}
					}
					if (bigC == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "dxb" + num + (6 - num);
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
					arr = (String[]) ArrayUtils.subarray(arr, 0, 6);
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
		sb.append(arr[4]).append(" ");
		sb.append(arr[5]).append("-");
		sb.append(arr[6]);
		return sb.toString();
	}

	@Override
	public void createMissFiles() {
		JSONObject resultRoot = new JSONObject();
		JSONObject redRoot = new JSONObject();
		JSONObject blueRoot = new JSONObject();
		List<SsqMissDataInfo> list = missDataEntityManager.getLastMissDatas(200);
		Collections.reverse(list);
		JSONObject redmiss = new JSONObject();
		JSONObject bluemiss = new JSONObject();
		// 创建红球遗漏文件
		for (SsqMissDataInfo info : list) {
			JSONObject miss = new JSONObject();
			Map<String, Integer> map = info.getMissDataMap();// .getMissDataContent().toMap();
			for (int i = 1; i <= 33; i++) {
				String field = "red" + i;
				miss.put(i, map.get(field));
			}
			redRoot.put(info.getPeriodNumber(), miss);
			resultRoot.put(info.getPeriodNumber(), formatResult(info.getResult()));
			redmiss=miss;
		}
		for (SsqMissDataInfo info : list) {
			JSONObject miss = new JSONObject();
			Map<String, Integer> map = info.getMissDataMap();// .getMissDataContent().toMap();
			for (int i = 1; i <= 16; i++) {
				String field = "blue" + i;
				miss.put(i, map.get(field));
			}
			blueRoot.put(info.getPeriodNumber(), miss);
			bluemiss=miss;
		}
		createMaxMissFiles(redmiss,bluemiss);
		
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "ssqdata.js", "dataShuangseqiu="+resultRoot.toString()+";function getdata(){return dataShuangseqiu;}", "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "Shuangseqiu_red.js", "Shuangseqiured="+redRoot.toString(), "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "Shuangseqiu_blue.js", "Shuangseqiublue="+blueRoot.toString(), "UTF-8");
		
	}

	@Override
	public Lottery getLottery() {
		return Lottery.SSQ;
	}

	@Override
	public MissDataEntityManager<SsqMissDataInfo> getMissDataManager() {
		return missDataEntityManager;
	}

	@Override
	public PeriodDataEntityManager<SsqPeriodData> getPeriodDataEntityManager() {
		return periodDataEntityManagerImpl;
	}

	@Override
	public List<AnalyseFilter> getAnalyseFilters(NumberAnalyse ns) {
		List<AnalyseFilter> list = new ArrayList<AnalyseFilter>();
		// 红球冷热分析项
		list.add(new AnalyseFilter("red", ns, 1, 33) {

			@Override
			protected String[] getNeedResult(String results) {
				String[] arr = results.split(",");
				return (String[]) ArrayUtils.subarray(arr, 0, 6);
			}

		});
		// 蓝球冷热分析项
		list.add(new AnalyseFilter("blue", ns, 1, 16) {

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
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/","ssq_max_miss.js", "ssq_max_miss="+root+";", "UTF-8");
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
		SsqPeriodData periodData= getPeriodDataEntityManager().getNewestResultPeriodData();
		if(null!=periodData&&StringUtils.isNotBlank(periodData.getResult())){
			Period period =periodEntityManger.getPeriod(periodData.getPeriodId());
			contents.put("period", period);
			contents.put("periodData", periodData);
			createRightFile(contents, "rightContent.ftl", "rightContent.html",period);
		}
	}
}
