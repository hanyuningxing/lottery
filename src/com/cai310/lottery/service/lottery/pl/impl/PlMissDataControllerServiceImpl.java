package com.cai310.lottery.service.lottery.pl.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.miss.AnalyseFilter;
import com.cai310.lottery.common.miss.MapSortUtil;
import com.cai310.lottery.common.miss.MissDataFilter;
import com.cai310.lottery.entity.lottery.GroupNumMiss;
import com.cai310.lottery.entity.lottery.NumberAnalyse;
import com.cai310.lottery.entity.lottery.pl.PlMissDataInfo;
import com.cai310.lottery.entity.lottery.pl.PlPeriodData;
import com.cai310.lottery.exception.DataException;
import com.cai310.lottery.service.lottery.MissDataEntityManager;
import com.cai310.lottery.service.lottery.PeriodDataEntityManager;
import com.cai310.lottery.service.lottery.impl.MissDataControllerServiceImpl;
import com.cai310.utils.TemplateGenerator;
import com.cai310.utils.WriteHTMLUtil;
@Service("plMissDataControllerServiceImpl")
public class PlMissDataControllerServiceImpl extends MissDataControllerServiceImpl<PlMissDataInfo, PlPeriodData> {

	@Autowired
	private PlMissDataEntityManagerImpl missDataEntityManager;
	@Autowired
	private PlPeriodDataEntityManagerImpl periodDataEntityManagerImpl;

	@Override
	public List<MissDataFilter> createFilters() {
		List<MissDataFilter> list = new ArrayList<MissDataFilter>();
		// 万位数字遗漏项
		for (int i = 0; i <= 9; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					if (Integer.parseInt(arr[0]) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "wan" + num;
				}

			};
			list.add(filter);
		}
		// 千位
		for (int i = 0; i <= 9; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					if (Integer.parseInt(arr[1]) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "qian" + num;
				}

			};
			list.add(filter);
		}
		// 百位
		for (int i = 0; i <= 9; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					if (Integer.parseInt(arr[2]) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "bai" + num;
				}

			};
			list.add(filter);
		}
		// 十位
		for (int i = 0; i <= 9; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					if (Integer.parseInt(arr[3]) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "shi" + num;
				}

			};
			list.add(filter);
		}
		// 个位
		for (int i = 0; i <= 9; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					if (Integer.parseInt(arr[4]) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "ge" + num;
				}

			};
			list.add(filter);
		}
		// 排列3出号遗漏
		for (int i = 0; i <= 9; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					if (Integer.parseInt(arr[0]) == num||Integer.parseInt(arr[1]) == num||Integer.parseInt(arr[2]) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "ch_3" + num;
				}

			};
			list.add(filter);
		}
		// 排列5出号遗漏
		for (int i = 0; i <= 9; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					if (Integer.parseInt(arr[0]) == num||Integer.parseInt(arr[1]) == num||Integer.parseInt(arr[2]) == num||Integer.parseInt(arr[3]) == num||Integer.parseInt(arr[4]) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "ch_5" + num;
				}

			};
			list.add(filter);
		}
		///大=1小=2 奇=3 偶=4 质=5 合=6
		final Map<String,Integer[]> ruleMap = new HashMap<String,Integer[]>();
		ruleMap.put("bbb", new Integer[]{1,1,1});
		ruleMap.put("bbs", new Integer[]{1,1,2});
		ruleMap.put("bsb", new Integer[]{1,2,1});
		ruleMap.put("sbb", new Integer[]{2,1,1});
		ruleMap.put("sss", new Integer[]{2,2,2});
		ruleMap.put("ssb", new Integer[]{2,2,1});
		ruleMap.put("sbs", new Integer[]{2,1,2});
		ruleMap.put("bss", new Integer[]{1,2,2});
		
		
		ruleMap.put("jjj", new Integer[]{3,3,3});
		ruleMap.put("jjo", new Integer[]{3,3,4});
		ruleMap.put("joj", new Integer[]{3,4,3});
		ruleMap.put("ojj", new Integer[]{4,3,3});
		ruleMap.put("ooo", new Integer[]{4,4,4});
		ruleMap.put("ooj", new Integer[]{4,4,3});
		ruleMap.put("ojo", new Integer[]{4,3,4});
		ruleMap.put("joo", new Integer[]{3,4,4});
		
		ruleMap.put("zzz", new Integer[]{5,5,5});
		ruleMap.put("zzh", new Integer[]{5,5,6});
		ruleMap.put("zhz", new Integer[]{5,6,5});
		ruleMap.put("hzz", new Integer[]{6,5,5});
		ruleMap.put("hhh", new Integer[]{6,6,6});
		ruleMap.put("hhz", new Integer[]{6,6,5});
		ruleMap.put("hzh", new Integer[]{6,5,6});
		ruleMap.put("zhh", new Integer[]{5,6,6});
		Set<String> ruleKey=ruleMap.keySet();
		for (String key : ruleKey) {
			final String name = key;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					return accectBsJoZh(new Integer[]{Integer.parseInt(arr[0]),Integer.parseInt(arr[1]),Integer.parseInt(arr[2])},ruleMap.get(name));
				}

				@Override
				public String getFieldName() {
					return name;
				}

			};
			list.add(filter);
		}
		// 排列3和值
		for (int i = 0; i <= 27; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					int count = 0;
					for (String str : arr) {
						count += Integer.parseInt(str);
					}
					if (count == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "he_3" + num;
				}

			};
			list.add(filter);
		}
		// pl5和值
		for (int i = 0; i <= 45; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					int count = 0;
					for (String str : arr) {
						count += Integer.parseInt(str);
					}
					if (count == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "he_5" + num;
				}

			};
			list.add(filter);
		}
		//p3 最大跨度
		for (int i = 0; i <= 9; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					arr = (String[]) ArrayUtils.subarray(arr, 0, 3);
					int max = 0;
					int min = 0;
					for (String str : arr) {
						int t = Integer.parseInt(str);
						if (t > max) {
							max = t;
						} else if (t < min) {
							min = t;
						}
					}
					if (max - min == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "allK_3" + num;
				}

			};
			list.add(filter);
		}
		// p5最大跨度
		for (int i = 0; i <= 9; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					int max = 0;
					int min = 0;
					for (String str : arr) {
						int t = Integer.parseInt(str);
						if (t > max) {
							max = t;
						} else if (t < min) {
							min = t;
						}
					}
					if (max - min == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "allK_5" + num;
				}

			};
			list.add(filter);
		}
		// 百十跨度pl3
		for (int i = 0; i <= 9; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					if (Math.abs(Integer.parseInt(arr[0]) - Integer.parseInt(arr[1])) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "bsK" + num;
				}

			};
			list.add(filter);
		}
		// 十个跨度pl3
		for (int i = 0; i <= 9; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					if (Math.abs(Integer.parseInt(arr[1]) - Integer.parseInt(arr[2])) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "sgK" + num;
				}

			};
			list.add(filter);
		}
		// 百个跨度pl3
		for (int i = 0; i <= 9; i++) {
			final int num = i;
			MissDataFilter filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					if (Math.abs(Integer.parseInt(arr[0]) - Integer.parseInt(arr[2])) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "bgK" + num;
				}

			};
			list.add(filter);
		}

		return list;
	}
	public Boolean accectBsJoZh(Integer[] resultArr , Integer[] ruleArr){
		    Integer bigSmallPos = 5;
		    Boolean returnValue = Boolean.TRUE;
		    for (int i = 0; i < 3; i++) {
				if(1==ruleArr[i]){
					//大
					if(resultArr[i]<bigSmallPos){
						returnValue =Boolean.FALSE;
					}
				}else if(2==ruleArr[i]){
					//小
					if(resultArr[i]>=bigSmallPos){
						returnValue =Boolean.FALSE;
					}
				}else if(3==ruleArr[i]){
					//奇
					if(resultArr[i]%2==0){
						returnValue =Boolean.FALSE;
					}
				}else if(4==ruleArr[i]){
					//偶
					if(resultArr[i]%2==1){
						returnValue =Boolean.FALSE;
					}
				}else if(5==ruleArr[i]){
					//质
					if(1!=resultArr[i]||2!=resultArr[i]||3!=resultArr[i]||5!=resultArr[i]||7!=resultArr[i]){
						returnValue =Boolean.FALSE;
					}
				}else if(6==ruleArr[i]){
					//合
					if(1==resultArr[i]||2==resultArr[i]||3==resultArr[i]||5==resultArr[i]||7==resultArr[i]){
						returnValue =Boolean.FALSE;
					}
				}
		    }
		    return returnValue;
	}

	@Override
	public void createMissFiles() {
		List<PlMissDataInfo> list = missDataEntityManager.getLastMissDatas(200);
		// 创建基本号码遗漏文件
		Collections.reverse(list);
		JSONObject dataRoot = new JSONObject();
		JSONObject dataRoot_p5 = new JSONObject();
		JSONObject numRoot = new JSONObject();
		JSONObject numRoot_p5 = new JSONObject();
		JSONObject chRoot = new JSONObject();
		JSONObject chRoot_p5 = new JSONObject();
		JSONObject heRoot = new JSONObject();
		JSONObject heRoot_p5 = new JSONObject();
		JSONObject kdRoot = new JSONObject();
		JSONObject dxjozhRoot = new JSONObject();
		JSONObject weizhi_p5 = new JSONObject();
		for (PlMissDataInfo info : list) {
			JSONObject weizhi = new JSONObject();
		    weizhi_p5 = new JSONObject();
			JSONObject miss = new JSONObject();
			Map<String, Integer> map = info.getMissDataMap();// .getMissDataContent().toMap();
			dataRoot_p5.put(info.getPeriodNumber(),info.getResult());
			String[] resultArr = info.getResult().split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
			dataRoot.put(info.getPeriodNumber(), resultArr[0]+Constant.RESULT_SEPARATOR_FOR_NUMBER+resultArr[1]+Constant.RESULT_SEPARATOR_FOR_NUMBER+resultArr[2]);
			// 百pl3
			for (int i = 0; i <= 9; i++) {
				String field = "wan" + i;
				miss.put(i, map.get(field));
			}
			weizhi_p5.put("w", miss);
			weizhi.put("b", miss);
			// 十pl3
			miss = new JSONObject();
			for (int i = 0; i <= 9; i++) {
				String field = "qian" + i;
				miss.put(i, map.get(field));
			}
			weizhi_p5.put("q", miss);
			weizhi.put("s", miss);
			// 个pl3
			miss = new JSONObject();
			for (int i = 0; i <= 9; i++) {
				String field = "bai" + i;
				miss.put(i, map.get(field));
			}
			weizhi_p5.put("b", miss);
			weizhi.put("g", miss);
			
			miss = new JSONObject();
			for (int i = 0; i <= 9; i++) {
				String field = "shi" + i;
				miss.put(i, map.get(field));
			}
			weizhi_p5.put("s", miss);
			
			miss = new JSONObject();
			for (int i = 0; i <= 9; i++) {
				String field = "ge" + i;
				miss.put(i, map.get(field));
			}
			weizhi_p5.put("g", miss);
			numRoot_p5.put(info.getPeriodNumber(), weizhi_p5);
			numRoot.put(info.getPeriodNumber(), weizhi);
			
			
			// 出号pl3
			miss = new JSONObject();
			for (int i = 0; i <= 9; i++) {
				String field = "ch_3" + i;
				miss.put(i, map.get(field));
			}
			chRoot.put(info.getPeriodNumber(), miss);
			
			
			// 出号pl5
			miss = new JSONObject();
			for (int i = 0; i <= 9; i++) {
				String field = "ch_5" + i;
				miss.put(i, map.get(field));
			}
			chRoot_p5.put(info.getPeriodNumber(), miss);


			// 和值遗漏pl3
			miss = new JSONObject();
			for (int i = 0; i <= 27; i++) {
				String field = "he_3" + i;
				miss.put(i, map.get(field));
			}
			heRoot.put(info.getPeriodNumber(), miss);
			
			// 和值遗漏pl3
			miss = new JSONObject();
			for (int i = 0; i <= 45; i++) {
				String field = "he_5" + i;
				miss.put(i, map.get(field));
			}
			heRoot_p5.put(info.getPeriodNumber(), miss);

			// 最大跨度遗漏pl3
			weizhi = new JSONObject();
			miss = new JSONObject();
			for (int i = 0; i <= 9; i++) {
				String field = "allK_3" + i;
				miss.put(i, map.get(field));
			}
			weizhi.put("b", miss);
			// 最大跨度遗漏pl5
			weizhi = new JSONObject();
			miss = new JSONObject();
			for (int i = 0; i <= 9; i++) {
				String field = "allK_5" + i;
				miss.put(i, map.get(field));
			}
			weizhi.put("allK_5", miss);
			
			miss = new JSONObject();
			for (int i = 0; i <= 9; i++) {
				String field = "bsK" + i;
				miss.put(i, map.get(field));
			}
			weizhi.put("bs", miss);
			miss = new JSONObject();
			for (int i = 0; i <= 9; i++) {
				String field = "sgK" + i;
				miss.put(i, map.get(field));
			}
			weizhi.put("sg", miss);
			miss = new JSONObject();
			for (int i = 0; i <= 9; i++) {
				String field = "bgK" + i;
				miss.put(i, map.get(field));
			}
			weizhi.put("bg", miss);
			kdRoot.put(info.getPeriodNumber(), weizhi);
			
			
			miss = new JSONObject();
			weizhi = new JSONObject();
			///大=1小=2 奇=3 偶=4 质=5 合=6
		    Map<String,Integer[]> ruleMap = new HashMap<String,Integer[]>();
			ruleMap.put("bbb", new Integer[]{1,1,1});
			ruleMap.put("bbs", new Integer[]{1,1,2});
			ruleMap.put("bsb", new Integer[]{1,2,1});
			ruleMap.put("sbb", new Integer[]{2,1,1});
			ruleMap.put("sss", new Integer[]{2,2,2});
			ruleMap.put("ssb", new Integer[]{2,2,1});
			ruleMap.put("sbs", new Integer[]{2,1,2});
			ruleMap.put("bss", new Integer[]{1,2,2});
			Set<String> ruleKey=ruleMap.keySet();
			for (String key : ruleKey) {
				miss.put(key, map.get(key));
			}
			weizhi.put("bs", miss);
			
			miss = new JSONObject();
			ruleMap = new HashMap<String,Integer[]>();
			ruleMap.put("jjj", new Integer[]{3,3,3});
			ruleMap.put("jjo", new Integer[]{3,3,4});
			ruleMap.put("joj", new Integer[]{3,4,3});
			ruleMap.put("ojj", new Integer[]{4,3,3});
			ruleMap.put("ooo", new Integer[]{4,4,4});
			ruleMap.put("ooj", new Integer[]{4,4,3});
			ruleMap.put("ojo", new Integer[]{4,3,4});
			ruleMap.put("joo", new Integer[]{3,4,4});
			ruleKey=ruleMap.keySet();
			for (String key : ruleKey) {
				miss.put(key, map.get(key));
			}
			weizhi.put("jo", miss);
			
			miss = new JSONObject();
			ruleMap = new HashMap<String,Integer[]>();
			ruleMap.put("zzz", new Integer[]{5,5,5});
			ruleMap.put("zzh", new Integer[]{5,5,6});
			ruleMap.put("zhz", new Integer[]{5,6,5});
			ruleMap.put("hzz", new Integer[]{6,5,5});
			ruleMap.put("hhh", new Integer[]{6,6,6});
			ruleMap.put("hhz", new Integer[]{6,6,5});
			ruleMap.put("hzh", new Integer[]{6,5,6});
			ruleMap.put("zhh", new Integer[]{5,6,6});
			ruleKey=ruleMap.keySet();
			for (String key : ruleKey) {
				miss.put(key, map.get(key));
			}
			weizhi.put("zh", miss);
			dxjozhRoot.put(info.getPeriodNumber(), weizhi);
		}

		createMaxMissFiles(weizhi_p5);
		//pl5
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "pl5_ball_data.js", "pl5_ball_data="+dataRoot_p5.toString()+";function getdata(){return pl5_ball_data;}", "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "pl5_miss_ch.js", "pl5_miss_ch="+chRoot_p5.toString()+";", "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "pl5_miss_zx.js", "pl5_miss_zx="+numRoot_p5.toString()+";", "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "pl5_he.js", "pl5_he="+heRoot_p5.toString()+";", "UTF-8");
		///生成右边栏静态页面
		//pl3
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "pl3_ball_data.js", "pl3_ball_data="+dataRoot.toString()+";function getdata(){return pl3_ball_data;}", "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "pl3_miss_ch.js", "pl3_miss_ch="+chRoot.toString()+";", "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "pl3_miss_zx.js", "pl3_miss_zx="+numRoot.toString()+";", "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "pl3_he.js", "pl3_he="+heRoot.toString()+";", "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "pl3_kd.js", "pl3_kd="+kdRoot.toString()+";function getdataballkd(){return pl3_kd;}", "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "pl3_dxjozh.js", "pl3_dxjozh="+dxjozhRoot.toString()+";", "UTF-8");
		GroupNumMiss ns = getMissDataManager().getGroupNumMiss(getLottery());
		if (ns != null) {
			createG3G6MissFiles(ns.getG3MissDataMap(),"pl3_g3_miss");
			createG3G6MissFiles(ns.getG6MissDataMap(),"pl3_g6_miss");
		}
		
	}
	public void createMaxMissFiles(JSONObject numRoot_p5){
    	if(null==numRoot_p5||numRoot_p5.isEmpty()){
    		return;
    	}
    	JSONObject  root = new JSONObject();
    	JSONObject miss = (JSONObject) numRoot_p5.get("w");
    	Map<String, Integer> missDataMap = MapSortUtil.sortByIntegerValueDesc(miss);
		root.put("n1b", getMaxMissObj(missDataMap));
	    missDataMap = MapSortUtil.sortByIntegerValueAsc(miss);
		root.put("n1s", getMaxMissObj(missDataMap));
		
		miss = (JSONObject) numRoot_p5.get("q");
    	missDataMap = MapSortUtil.sortByIntegerValueDesc(miss);
		root.put("n2b", getMaxMissObj(missDataMap));
	    missDataMap = MapSortUtil.sortByIntegerValueAsc(miss);
		root.put("n2s", getMaxMissObj(missDataMap));
		
		
		miss = (JSONObject) numRoot_p5.get("b");
    	missDataMap = MapSortUtil.sortByIntegerValueDesc(miss);
		root.put("n3b", getMaxMissObj(missDataMap));
	    missDataMap = MapSortUtil.sortByIntegerValueAsc(miss);
		root.put("n3s", getMaxMissObj(missDataMap));
		
		
		miss = (JSONObject) numRoot_p5.get("s");
    	missDataMap = MapSortUtil.sortByIntegerValueDesc(miss);
		root.put("n4b", getMaxMissObj(missDataMap));
	    missDataMap = MapSortUtil.sortByIntegerValueAsc(miss);
		root.put("n4s", getMaxMissObj(missDataMap));
		
		
		miss = (JSONObject) numRoot_p5.get("g");
    	missDataMap = MapSortUtil.sortByIntegerValueDesc(miss);
		root.put("n5b", getMaxMissObj(missDataMap));
	    missDataMap = MapSortUtil.sortByIntegerValueAsc(miss);
		root.put("n5s", getMaxMissObj(missDataMap));
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/","pl_max_miss.js", "pl_max_miss="+root+";", "UTF-8");
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
    public void createG3G6MissFiles(Map<String, Integer> missDataMap,String name){
    	if(null==missDataMap||missDataMap.isEmpty()){
    		return;
    	}
    	JSONObject miss = new JSONObject();
		//排序 
    	missDataMap=MapSortUtil.sortByIntegerValueDesc(missDataMap);
		Set<String> keySet = missDataMap.keySet();
		for (String key : keySet) {
			miss.put(key, missDataMap.get(key));
		}
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", name+".js", name+"="+miss.toString()+";", "UTF-8");
    }
	@Override
	public Lottery getLottery() {
		return Lottery.PL;
	}

	@Override
	public MissDataEntityManager<PlMissDataInfo> getMissDataManager() {
		return missDataEntityManager;
	}

	@Override
	public PeriodDataEntityManager<PlPeriodData> getPeriodDataEntityManager() {
		return periodDataEntityManagerImpl;
	}

	@Override
	public List<AnalyseFilter> getAnalyseFilters(NumberAnalyse ns) {
		List<AnalyseFilter> list = new ArrayList<AnalyseFilter>();
		// 百位冷热分析项
		list.add(new AnalyseFilter("bai", ns, 0, 9) {

			@Override
			protected String[] getNeedResult(String results) {
				String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
				return (String[]) ArrayUtils.subarray(arr, 0, 1);
			}

		});
		// 十位冷热分析项
		list.add(new AnalyseFilter("shi", ns, 0, 9) {

			@Override
			protected String[] getNeedResult(String results) {
				String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
				return (String[]) ArrayUtils.subarray(arr, 1, 2);
			}

		});
		// 个位冷热分析项
		list.add(new AnalyseFilter("ge", ns, 0, 9) {

			@Override
			protected String[] getNeedResult(String results) {
				String[] arr = results.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
				return (String[]) ArrayUtils.subarray(arr, 2, 3);
			}

		});
		return list;
	}
	/**
	 * 生成静态文件工具类
	 * @param contents
	 * @param sourcefile
	 * @param destinationFileName
	 * @throws DataException
	 */
	public void createRightFile(Map<String,Object> contents,String sourcefile,String destinationFileName,Lottery lottery)throws DataException{
		try {
			TemplateGenerator tg=new TemplateGenerator(Constant.ROOTPATH+"/WEB-INF/content/"+lottery.getKey());
			tg.create(sourcefile, contents, destinationFileName, Constant.ROOTPATH+"/WEB-INF/content/"+lottery.getKey());

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException("生成静态文件失败");
		}
	}

}
