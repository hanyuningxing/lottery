package com.cai310.lottery.service.lottery.keno.ssc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.miss.AnalyseFilter;
import com.cai310.lottery.common.miss.MapSortUtil;
import com.cai310.lottery.common.miss.MissDataFilter;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.ssc.SscIssueDataDao;
import com.cai310.lottery.entity.lottery.NumberAnalyse;
import com.cai310.lottery.entity.lottery.keno.ssc.SscIssueData;
import com.cai310.lottery.entity.lottery.keno.ssc.SscMissDataInfo;
import com.cai310.lottery.service.lottery.keno.impl.MissDataControllerServiceImpl;
import com.cai310.lottery.service.lottery.keno.impl.MissDataEntityManagerImpl;
import com.cai310.utils.WriteHTMLUtil;
@Service("sscMissDataControllerServiceImpl")
public class SscMissDataControllerServiceImpl extends MissDataControllerServiceImpl<SscMissDataInfo, SscIssueData> {
	@Autowired
	private SscIssueDataDao issueDataDao;
	@Autowired
	private SscMissDataEntityManagerImpl missDataEntityManager;
	@Override
	public List<MissDataFilter> createFilters() {
		List<MissDataFilter> list = new ArrayList<MissDataFilter>();
		// 遗漏项
		MissDataFilter filter = null;
		for (int i = 0; i <= 9; i++) {
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
		for (int t = 0; t < 5; t++) {
			for (int i = 0; i <= 9; i++) {
				final int num = i;
				final int miss_index = t;
				filter = new MissDataFilter() {
	
					@Override
					public boolean accect(String results) {
						String[] arr = results.split(",");
						if (Integer.parseInt(arr[miss_index]) == num) {
								return true;
						}
						return false;
					}
	
					@Override
					public String getFieldName() {
						return "miss_"+(miss_index)+"_" + num;
					}
	
				};
				list.add(filter);
			}
		}
		// 大小比
		for (int i = 0; i <= 5; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int bigC = 0;
					int smallC = 0;
					for (int j = 0; j < 5; j++) {
						if (Integer.parseInt(arr[j]) > 4) {
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
					return "dxb" + num + (5 - num);
				}

			};
			list.add(filter);
		}

		// 连号
		for (int i = 1; i <= 9; i++) {
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
		sb.append(arr[4]).append(" ");
		return sb.toString();
	}

	@Override
	public void createMissFiles() {
		JSONObject resultRoot = new JSONObject();
		JSONObject numRoot = new JSONObject();
		JSONObject weizhi1_miss_root = new JSONObject();
		JSONObject weizhi2_miss_root = new JSONObject();
		JSONObject weizhi3_miss_root = new JSONObject();
		JSONObject weizhi4_miss_root = new JSONObject();
		JSONObject weizhi5_miss_root = new JSONObject();
		List<SscMissDataInfo> list = missDataEntityManager.getLastMissDatas(200);
		Collections.reverse(list);
		JSONObject num_1_miss = new JSONObject();
		JSONObject num_2_miss = new JSONObject();
		JSONObject num_3_miss = new JSONObject();
		JSONObject num_4_miss = new JSONObject();
		JSONObject num_5_miss = new JSONObject();
		// 创建遗漏文件
		for (SscMissDataInfo info : list) {
			JSONObject miss = new JSONObject();
			Map<String, Integer> map = info.getMissDataMap();// .getMissDataContent().toMap();
			for (int i = 0; i <= 9; i++) {
				String field = "num" + i;
				miss.put(i, map.get(field));
			}
			for (int t = 0; t < 5; t++) {
				JSONObject weizhi_miss = new JSONObject();
				for (int i = 0; i <= 9; i++) {
					String field = "miss_"+t+"_" + i;
					weizhi_miss.put(i, map.get(field));
				}
				if(t==0){
					weizhi1_miss_root.put(info.getPeriodNumber(), weizhi_miss);
					num_1_miss = weizhi_miss;
				}else if(t==1){
					weizhi2_miss_root.put(info.getPeriodNumber(), weizhi_miss);
					num_2_miss = weizhi_miss;
				}else if(t==2){
					weizhi3_miss_root.put(info.getPeriodNumber(), weizhi_miss);
					num_3_miss = weizhi_miss;
				}else if(t==3){
					weizhi4_miss_root.put(info.getPeriodNumber(), weizhi_miss);
					num_4_miss = weizhi_miss;
				}else if(t==4){
					weizhi5_miss_root.put(info.getPeriodNumber(), weizhi_miss);
					num_5_miss = weizhi_miss;
				}
			}
			numRoot.put(info.getPeriodNumber(), miss);
			resultRoot.put(info.getPeriodNumber(), formatResult(info.getResult()));
		}
		createMaxMissFiles(num_1_miss,num_2_miss,num_3_miss,num_4_miss,num_5_miss);
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "miss_1.js", "miss_data="+weizhi1_miss_root.toString(), "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "miss_2.js", "miss_data="+weizhi2_miss_root.toString(), "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "miss_3.js", "miss_data="+weizhi3_miss_root.toString(), "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "miss_4.js", "miss_data="+weizhi4_miss_root.toString(), "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "miss_5.js", "miss_data="+weizhi5_miss_root.toString(), "UTF-8");
		
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "sscdata.js", "dataSsc="+resultRoot.toString()+";function getdata(){return dataSsc;}", "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/", "ssc_num.js", "sscNum="+numRoot.toString(), "UTF-8");
		
	}

	@Override
	public Lottery getLottery() {
		return Lottery.SSC;
	}

	@Override
	public MissDataEntityManagerImpl<SscMissDataInfo,SscIssueData> getMissDataManager() {
		return missDataEntityManager;
	}
	@Override
	public List<AnalyseFilter> getAnalyseFilters(NumberAnalyse ns) {
		List<AnalyseFilter> list = new ArrayList<AnalyseFilter>();
		// 红球冷热分析项
		list.add(new AnalyseFilter("num", ns, 0, 9) {

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
	
	private void createMaxMissFiles(JSONObject miss1,JSONObject miss2,JSONObject miss3,JSONObject miss4,JSONObject miss5){
		JSONObject  root = new JSONObject();
    	Map<String, Integer> missDataMap = MapSortUtil.sortByIntegerValueDesc(miss1);
		root.put("num_1_b", getMaxMissObj(missDataMap));
	    missDataMap = MapSortUtil.sortByIntegerValueAsc(miss1);
		root.put("num_1_s", getMaxMissObj(missDataMap));
		
        missDataMap = MapSortUtil.sortByIntegerValueDesc(miss2);
		root.put("num_2_b", getMaxMissObj(missDataMap));
	    missDataMap = MapSortUtil.sortByIntegerValueAsc(miss2);
		root.put("num_2_s", getMaxMissObj(missDataMap));
		
        missDataMap = MapSortUtil.sortByIntegerValueDesc(miss3);
		root.put("num_3_b", getMaxMissObj(missDataMap));
	    missDataMap = MapSortUtil.sortByIntegerValueAsc(miss3);
		root.put("num_3_s", getMaxMissObj(missDataMap));
		
        missDataMap = MapSortUtil.sortByIntegerValueDesc(miss4);
		root.put("num_4_b", getMaxMissObj(missDataMap));
	    missDataMap = MapSortUtil.sortByIntegerValueAsc(miss4);
		root.put("num_4_s", getMaxMissObj(missDataMap));
		
        missDataMap = MapSortUtil.sortByIntegerValueDesc(miss5);
		root.put("num_5_b", getMaxMissObj(missDataMap));
	    missDataMap = MapSortUtil.sortByIntegerValueAsc(miss5);
		root.put("num_5_s", getMaxMissObj(missDataMap));
		
		WriteHTMLUtil.writeHtm("/js/analyse/"+getLottery().getKey()+"/","ssc_max_miss.js", "ssc_max_miss="+root+";", "UTF-8");
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
	public IssueDataDao<SscIssueData> getIssueDataDao() {
		return issueDataDao;
	}
	
	public List getMissDatas(int count){
		return missDataEntityManager.getLastMissDatas(count);
	}
	
	public List getLastMissDatas(int count,String start,String end){
		return missDataEntityManager.getLastMissDatas(count,start,end);
	}
}
