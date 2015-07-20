package com.cai310.lottery.service.lottery.keno.el11to5.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.keno.IssueState;
import com.cai310.lottery.common.miss.AnalyseFilter;
import com.cai310.lottery.common.miss.MapSortUtil;
import com.cai310.lottery.common.miss.MissDataFilter;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.el11to5.El11to5IssueDataDao;
import com.cai310.lottery.entity.lottery.NumberAnalyse;
import com.cai310.lottery.entity.lottery.RandomNumMiss;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5IssueData;
import com.cai310.lottery.entity.lottery.keno.el11to5.El11to5MissDataInfo;
import com.cai310.lottery.service.lottery.keno.impl.MissDataControllerServiceImpl;
import com.cai310.lottery.service.lottery.keno.impl.MissDataEntityManagerImpl;
import com.cai310.utils.NumUtils;
import com.cai310.utils.WriteHTMLUtil;

@Service("el11to5MissDataControllerServiceImpl")
public class El11to5MissDataControllerServiceImpl extends
		MissDataControllerServiceImpl<El11to5MissDataInfo, El11to5IssueData> {
	@Autowired
	private El11to5IssueDataDao issueDataDao;
	@Autowired
	private El11to5MissDataEntityManagerImpl missDataEntityManager;
 
	@Override
	public List<MissDataFilter> createFilters() {
		List<MissDataFilter> list = new ArrayList<MissDataFilter>();
		// 遗漏项
		MissDataFilter filter = null;
		for (int i = 1; i <= 11; i++) {
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
		// 奇数个数
		for (int i = 0; i <= 5; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int count = 0;
					for (String s : arr) {
						if (Integer.parseInt(s) % 2 != 0) {
							count++;
						}
					}
					return count == num;
				}

				@Override
				public String getFieldName() {
					return "jnums" + num;
				}

			};
			list.add(filter);
		}
		for (int i = 0; i <= 5; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int count = 0;
					for (String s : arr) {
						if (Integer.parseInt(s) % 2 == 0) {
							count++;
						}
					}
					return count == num;
				}

				@Override
				public String getFieldName() {
					return "onums" + num;
				}

			};
			list.add(filter);
		}
		// 小数个数
		for (int i = 0; i <= 5; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int count = 0;
					for (String s : arr) {
						if (Integer.parseInt(s) <= 5) {
							count++;
						}
					}
					return count == num;
				}

				@Override
				public String getFieldName() {
					return "xnums" + num;
				}

			};
			list.add(filter);
		}
		for (int i = 0; i <= 5; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int count = 0;
					for (String s : arr) {
						if (Integer.parseInt(s) > 5) {
							count++;
						}
					}
					return count == num;
				}

				@Override
				public String getFieldName() {
					return "dnums" + num;
				}

			};
			list.add(filter);
		}
		// 质数个数
		for (int i = 0; i <= 5; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int count = 0;
					for (String s : arr) {
						int r = Integer.parseInt(s);
						if (NumUtils.isPrimNum(r)) {
							count++;
						}
					}
					return count == num;
				}

				@Override
				public String getFieldName() {
					return "znums" + num;
				}

			};
			list.add(filter);
		}
		// 质数个数
		for (int i = 0; i <= 5; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int count = 0;
					for (String s : arr) {
						int r = Integer.parseInt(s);
						if (!NumUtils.isPrimNum(r)) {
							count++;
						}
					}
					return count == num;
				}

				@Override
				public String getFieldName() {
					return "znums" + num;
				}

			};
			list.add(filter);
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
						if (Integer.parseInt(arr[j]) > 11) {
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
		for (int i = 1; i <= 11; i++) {
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

		// 第一位
		for (int i = 1; i <= 11; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					if (Integer.parseInt(arr[0]) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "num_one" + num;
				}

			};
			list.add(filter);
		}
		// 第二位
		for (int i = 1; i <= 11; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					if (Integer.parseInt(arr[1]) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "num_two" + num;
				}

			};
			list.add(filter);
		}
		// 第三位
		for (int i = 1; i <= 11; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					if (Integer.parseInt(arr[2]) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "num_three" + num;
				}

			};
			list.add(filter);
		}
		// 前二组选
		for (int i = 1; i <= 11; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					if (Integer.parseInt(arr[0]) == num || Integer.parseInt(arr[1]) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "num_g_two" + num;
				}

			};
			list.add(filter);
		}
		// 前三组选
		for (int i = 1; i <= 11; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					if (Integer.parseInt(arr[0]) == num || Integer.parseInt(arr[1]) == num
							|| Integer.parseInt(arr[2]) == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "num_g_three" + num;
				}

			};
			list.add(filter);
		}
		return list;
	}

	private String formatResult(String result) {
		String[] arr = result.split(",");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].length() < 2) {
				arr[i] = "0" + arr[i];
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
		JSONObject secondObj = new JSONObject();
		List<El11to5MissDataInfo> list = missDataEntityManager.getLastMissDatas(500);
		Collections.reverse(list);
		JSONObject nummiss = new JSONObject();
		JSONObject num1miss = new JSONObject();
		JSONObject num2miss = new JSONObject();
		JSONObject num3miss = new JSONObject();
		JSONObject numG2miss = new JSONObject();
		JSONObject numG3miss = new JSONObject();

		// 创建遗漏文件
		for (El11to5MissDataInfo info : list) {
			JSONObject miss = new JSONObject();
			Map<String, Integer> map = info.getMissDataMap();// .getMissDataContent().toMap();
			for (int i = 1; i <= 11; i++) {
				String field = "num" + i;
				miss.put(i, map.get(field));
				num1miss.put(i, "num_one" + i);
				num2miss.put(i, "num_two" + i);
				num3miss.put(i, "num_three" + i);
				numG2miss.put(i, "num_g_two" + i);
				numG3miss.put(i, "num_g_three" + i);
			}
			nummiss = miss;
			secondObj.put("num", miss);

			miss = new JSONObject();
			for (int i = 0; i <= 5; i++) {
				String field = "jnums" + i;
				miss.put(i, map.get(field));
			}
			secondObj.put("jnums", miss);

			miss = new JSONObject();
			for (int i = 0; i <= 5; i++) {
				String field = "onums" + i;
				miss.put(i, map.get(field));
			}
			secondObj.put("onums", miss);

			miss = new JSONObject();
			for (int i = 0; i <= 5; i++) {
				String field = "xnums" + i;
				miss.put(i, map.get(field));
			}
			secondObj.put("xnums", miss);

			miss = new JSONObject();
			for (int i = 0; i <= 5; i++) {
				String field = "dnums" + i;
				miss.put(i, map.get(field));
			}
			secondObj.put("dnums", miss);

			miss = new JSONObject();
			for (int i = 0; i <= 5; i++) {
				String field = "znums" + i;
				miss.put(i, map.get(field));
			}
			secondObj.put("znums", miss);

			miss = new JSONObject();
			for (int i = 0; i <= 5; i++) {
				String field = "hnums" + i;
				miss.put(i, map.get(field));
			}
			secondObj.put("hnums", miss);

			numRoot.put(info.getPeriodNumber(), secondObj);
			resultRoot.put(info.getPeriodNumber(), formatResult(info.getResult()));
		}
		createMaxMissFiles(nummiss, num1miss, num2miss, num3miss, numG2miss, numG3miss);

		WriteHTMLUtil.writeHtm("/js/analyse/" + getLottery().getKey() + "/", "el11to5data.js", "dataEl11to5="
				+ resultRoot.toString() + ";function getdata(){return dataEl11to5;}", "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/" + getLottery().getKey() + "/", "el11to5_num.js", "el11to5Num="
				+ numRoot.toString(), "UTF-8");

		List<RandomNumMiss> nsList = getMissDataManager().getRandomNumMissByLotter(getLottery());
		if (nsList != null && !nsList.isEmpty()) {
			for (RandomNumMiss ns : nsList) {
				Map<String, HashMap<String, Long>> missDataMap = ns.getMissDataMap();

				WriteHTMLUtil.writeHtm("/js/analyse/" + getLottery().getKey() + "/", ns.getKeyWord() + ".js", ns
						.getContent(), "UTF-8");
			}
		}
	}

	private void createMaxMissFiles(JSONObject nummiss, JSONObject num1miss, JSONObject num2miss, JSONObject num3miss,
			JSONObject numG2miss, JSONObject numG3miss) {
		JSONObject root = new JSONObject();
		root.put("num", nummiss);
		root.put("num1", num1miss);
		root.put("num2", num2miss);
		root.put("num3", num3miss);
		root.put("numG2", numG2miss);
		root.put("numG3", numG3miss);

		WriteHTMLUtil.writeHtm("/js/analyse/" + getLottery().getKey() + "/", "el11to5_max_miss.js", "el11to5_max_miss="
				+ root + ";", "UTF-8");
	}

	@Override
	public Lottery getLottery() {
		return Lottery.EL11TO5;
	}

	@Override
	public MissDataEntityManagerImpl<El11to5MissDataInfo, El11to5IssueData> getMissDataManager() {
		return missDataEntityManager;
	}

	@Override
	public List<AnalyseFilter> getAnalyseFilters(NumberAnalyse ns) {
		List<AnalyseFilter> list = new ArrayList<AnalyseFilter>();
		// 红球冷热分析项
		list.add(new AnalyseFilter("num", ns, 1, 23) {

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
		updateGroupNumMiss(0);
		updateGroupNumMiss(100);
		updateGroupNumMiss(200);
		updateGroupNumMiss(500);
		updateGroupNumMiss(1000);

	}

	public void updateGroupNumMiss(int periodCount) {
		String[] contentKeys = { "QEZX", "QSZX", "QEDX", "QSDX", "RAND2", "RAND3", "RAND4", "RAND5", "RAND6", "RAND7",
				"RAND8" };
		List<RandomNumMiss> nsList = getMissDataManager().getRandomNumMissByLotter(getLottery());
		RandomNumMiss ns = null;
		if (!CollectionUtils.isEmpty(nsList)) {
			ns = nsList.get(0);
		}
		List<El11to5IssueData> list = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(El11to5IssueData.class);
//		criteria.add(Restrictions.isNotNull("results"));
		criteria.add(Restrictions.in("state", IssueState.ISSUE_RESULT_STATE));
		if (periodCount == 0) {
			if (ns != null) {
				criteria.add(Restrictions.gt("id", ns.getLastPeriodId()));
			}
			criteria.addOrder(Order.asc("id"));
			list = getIssueDataDao().findByDetachedCriteria(criteria);
		} else {
			criteria.addOrder(Order.desc("id"));
			list = getIssueDataDao().findByDetachedCriteria(criteria, 0, periodCount);
			if (list != null) {
				Collections.reverse(list);
			}
		}
		if (list == null || list.isEmpty()) {
			logger.info("无开奖数据");
			return;
		}
		Map<String, RandomNumMiss> nsMap = new HashMap<String, RandomNumMiss>();
		if (nsList != null && !nsList.isEmpty()) {
			for (RandomNumMiss miss : nsList) {
				nsMap.put(miss.getKeyWord(), miss);
			}
		}
		Map<String, HashMap<String, Long>> countMiss = null;
//		KenoPeriod kenoPeriod = null;
		for (String contentKey : contentKeys) {
			countMiss = null;
			String lastPeriodNumber=null;
			String beginPeriodNumber=null;
			Long lastPeriodId = null;
			String beginPeriod = null;
			String endPreiod = null;
			String maxBeginPeriod = null;
			String maxEndPreiod = null;
			Integer totalPeriod = 0;
			ns = nsMap.get(contentKey);
			if (ns != null && periodCount == 0) {
				countMiss = ns.getMissDataMap();
				totalPeriod = ns.getTotalPeriod();
				beginPeriodNumber=ns.getBeginPeriodNumber();
			} else {
				countMiss = initContentMiss(contentKey);
			}
			if(periodCount==1000){
				System.out.println(periodCount);
			}
			for (El11to5IssueData data : list) {
				lastPeriodId = data.getId();
				lastPeriodNumber=data.getPeriodNumber();
				if(beginPeriodNumber==null){
					beginPeriodNumber = data.getPeriodNumber();
				}
				String result=data.getResults();
				if(StringUtils.isBlank(result)){//某些期次存在没有开奖结果的情况
					result="0,0,0,0,0";
					continue;
				}
				String[] arr = result.split("[^\\d]+");
				Integer[] arrInt = NumUtils.toIntegerArr(arr);
				// updateRandomMiss(arrInt, countMiss, contentKey);
				totalPeriod++;
				for (Map.Entry<String, HashMap<String, Long>> entry : countMiss.entrySet()) {
					String key = entry.getKey();
					HashMap<String, Long> map = entry.getValue();
					map.put("tc", totalPeriod+0l);
					if(map.get("ibp")==null){
						map.put("ibp", Long.valueOf(beginPeriodNumber));
					}
					map.put("iep", Long.valueOf(lastPeriodNumber));
					
					if (map.get("mb") == null) {
						map.put("mb", Long.valueOf(data.getPeriodNumber()));
						map.put("bp", Long.valueOf(map.get("mb") + ""));
					}
					if (map.get("me") == null) {
						map.put("me", Long.valueOf(data.getPeriodNumber()));
						map.put("ep", Long.valueOf(map.get("me") + ""));
					}
					if (isContentHit(arrInt, key, contentKey)) {
						map.put("s", Long.valueOf(map.get("s") + "") + 1l);
						map.put("ls", Long.valueOf(map.get("cs") + ""));
						map.put("cs", 0l);
						map.put("lh", 1l);
//						map.put("bp", Long.valueOf(map.get("ep") + ""));
					} else {
						if(map.get("lh")!=null&&Long.valueOf(map.get("lh")+"")>0){//上一期命中
							map.put("bp", Long.valueOf(data.getPeriodNumber()));
							map.put("lh",0l);
						}
						map.put("ep", Long.valueOf(data.getPeriodNumber()));
						map.put("cs", Long.valueOf((map.get("cs") + "")) + 1l);// 本次遗漏
						if (Long.valueOf(map.get("cs") + "") > Long.valueOf(map.get("mx") + "")) {
							map.put("mx", Long.valueOf(map.get("cs") + ""));// 最大遗漏
							map.put("mb", Long.valueOf(map.get("bp") + ""));
							map.put("me", Long.valueOf(map.get("ep") + ""));

						}
					}
					// contentMiss.put(key, map);
				}
			}
			if (ns == null) {
				ns = new RandomNumMiss();
				ns.setLottery(getLottery());
				ns.setKeyWord(contentKey);
				ns.setBeginPeriodNumber(beginPeriodNumber);
			}
			if (periodCount == 0) {
				ns.setTotalPeriod(totalPeriod);
				ns.setLastPeriodId(lastPeriodId);
				ns.setLastPeriodNumber(lastPeriodNumber);
				ns.setContent(JSONObject.fromObject(countMiss).toString());
			}else if(periodCount==100){
				ns.setContent100(JSONObject.fromObject(countMiss).toString());
			}else if(periodCount==200){
				ns.setContent200(JSONObject.fromObject(countMiss).toString());
			}else if(periodCount==500){
				ns.setContent500(JSONObject.fromObject(countMiss).toString());
			}else if(periodCount==1000){
				ns.setContent1000(JSONObject.fromObject(countMiss).toString());
			}
			getMissDataManager().saveRandomNumMiss(ns);
		}

	}

	private void createMaxMissFiles(JSONObject miss) {
		JSONObject root = new JSONObject();
		Map<String, Integer> missDataMap = MapSortUtil.sortByIntegerValueDesc(miss);
		root.put("numb", getMaxMissObj(missDataMap));
		missDataMap = MapSortUtil.sortByIntegerValueAsc(miss);
		root.put("nums", getMaxMissObj(missDataMap));
		WriteHTMLUtil.writeHtm("/js/analyse/" + getLottery().getKey() + "/", "el11to5_max_miss.js", "el11to5_max_miss="
				+ root + ";", "UTF-8");
	}

	public JSONObject getMaxMissObj(Map<String, Integer> missDataMap) {
		Set<String> keySet = missDataMap.keySet();
		JSONObject root = new JSONObject();
		int i = 0;
		for (String key : keySet) {
			i++;
			JSONObject miss = new JSONObject();
			miss.put("name", key);
			miss.put("value", missDataMap.get(key));
			root.put(i, miss);
			if (i > 2) {
				break;
			}

		}
		return root;
	}

	@Override
	public IssueDataDao<El11to5IssueData> getIssueDataDao() {
		return issueDataDao;
	}

	private boolean isContentHit(Integer[] arr, String code, String key) {
		if ("QEZX".equals(key)) {
			Integer[] temp = (Integer[]) ArrayUtils.subarray(arr, 0, 2);
			Arrays.sort(temp);
			String result = fomatCode(temp[0], temp[1]);
			return result.equals(code);
		} else if ("QSZX".equals(key)) {
			Integer[] temp = (Integer[]) ArrayUtils.subarray(arr, 0, 3);
			Arrays.sort(temp);
			String result = fomatCode(temp[0], temp[1], temp[2]);
			return result.equals(code);
		} else if ("QEDX".equals(key)) {
			Integer[] temp = (Integer[]) ArrayUtils.subarray(arr, 0, 2);
			String result = fomatCode(temp[0], temp[1]);
			return result.equals(code);
		} else if ("QSDX".equals(key)) {
			Integer[] temp = (Integer[]) ArrayUtils.subarray(arr, 0, 3);
			String result = fomatCode(temp[0], temp[1], temp[2]);
			return result.equals(code);
		} else {
			String[] codes = code.split(" ");
			int hits = 0;
			int needHit = codes.length > 5 ? 5 : codes.length;
			for (String c : codes) {
				if (ArrayUtils.contains(arr, Integer.parseInt(c))) {
					hits++;
					if (hits >= needHit) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private void updateRandomMiss(Integer[] results, Map<String, HashMap<String, Integer>> contentMiss,
			String contentKey) {

		for (Map.Entry<String, HashMap<String, Integer>> entry : contentMiss.entrySet()) {
			String key = entry.getKey();
			HashMap<String, Integer> map = entry.getValue();
			if (isContentHit(results, key, contentKey)) {
				map.put("s", map.get("s") + 1);
				map.put("ls", map.get("cs"));
				map.put("cs", 0);
			} else {
				map.put("cs", map.get("cs") + 1);// 本次遗漏
				if (map.get("cs") > map.get("mx"))
					;
				map.put("mx", map.get("cs"));// 最大遗漏
			}
			// contentMiss.put(key, map);
		}
	}

	private String fomatCode(Integer... num) {
		DecimalFormat df = new DecimalFormat("00");
		StringBuilder sb = new StringBuilder();
		for (Integer n : num) {
			sb.append(df.format(n));
			sb.append(" ");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	private Map<String, HashMap<String, Long>> initContentMiss(String key) {
		Map<String, HashMap<String, Long>> countMiss = new HashMap<String, HashMap<String, Long>>();// 前二组选
//		RandomNumMiss ns = getMissDataManager().getRandomNumMiss(getLottery(), key);
//		if (ns != null&&periondCount==0) {
//			return ns.getMissDataMap();
//		}
		DecimalFormat df = new DecimalFormat("00");
		if ("QSZX".equals(key) || "RAND3".equals(key)) {// 前三组选
			for (int x = 1; x <= 11; x++) {
				for (int y = x + 1; y <= 11; y++) {
					for (int z = y + 1; z <= 11; z++) {
						HashMap<String, Long> map = new HashMap<String, Long>();
						map.put("s", 0l);// 出现次数
						map.put("ls", 0l);// 上次遗漏
						map.put("cs", 0l);// 本次遗漏
						map.put("mx", 0l);// 最大遗漏
						countMiss.put(fomatCode(x, y, z), map);
					}
				}
			}
		} else if ("QEZX".equals(key) || "RAND2".equals(key)) {// 前二组选
			for (int x = 1; x <= 11; x++) {
				for (int y = x + 1; y <= 11; y++) {
					HashMap<String, Long> map = new HashMap<String, Long>();
					map.put("s", 0l);// 出现次数
					map.put("ls", 0l);// 上次遗漏
					map.put("cs", 0l);// 本次遗漏
					map.put("mx", 0l);// 最大遗漏
					countMiss.put(fomatCode(x, y), map);
				}
			}
		} else if ("QSDX".equals(key)) {// 前三直选
			for (int x = 1; x <= 11; x++) {
				for (int y = 1; y <= 11; y++) {
					if (x == y) {
						continue;
					}
					for (int z = 1; z <= 11; z++) {
						if (z == y || z == x) {
							continue;
						}
						HashMap<String, Long> map = new HashMap<String, Long>();
						map.put("s", 0l);// 出现次数
						map.put("ls", 0l);// 上次遗漏
						map.put("cs", 0l);// 本次遗漏
						map.put("mx", 0l);// 最大遗漏
						countMiss.put(fomatCode(x, y, z), map);
					}
				}
			}
		} else if ("QEDX".equals(key)) {// 前二直选
			for (int x = 1; x <= 11; x++) {
				for (int y = 1; y <= 11; y++) {
					if (x == y) {
						continue;
					}
					HashMap<String, Long> map = new HashMap<String, Long>();
					map.put("s", 0l);// 出现次数
					map.put("ls", 0l);// 上次遗漏
					map.put("cs", 0l);// 本次遗漏
					map.put("mx", 0l);// 最大遗漏
					countMiss.put(fomatCode(x, y), map);
				}
			}
		} else if ("RAND4".equals(key)) {
			for (int x = 1; x <= 11; x++) {
				for (int y = x + 1; y <= 11; y++) {
					for (int z = y + 1; z <= 11; z++) {
						for (int k = z + 1; k <= 11; k++) {
							HashMap<String, Long> map = new HashMap<String, Long>();
							map.put("s", 0l);// 出现次数
							map.put("ls", 0l);// 上次遗漏
							map.put("cs", 0l);// 本次遗漏
							map.put("mx", 0l);// 最大遗漏
							countMiss.put(fomatCode(x, y, z, k), map);
						}
					}
				}
			}
		} else if ("RAND5".equals(key)) {
			for (int x = 1; x <= 11; x++) {
				for (int y = x + 1; y <= 11; y++) {
					for (int z = y + 1; z <= 11; z++) {
						for (int k = z + 1; k <= 11; k++) {
							for (int j = k + 1; j <= 11; j++) {
								HashMap<String, Long> map = new HashMap<String, Long>();
								map.put("s", 0l);// 出现次数
								map.put("ls", 0l);// 上次遗漏
								map.put("cs", 0l);// 本次遗漏
								map.put("mx", 0l);// 最大遗漏
								countMiss.put(fomatCode(x, y, z, k, j), map);
							}
						}
					}
				}
			}
		} else if ("RAND6".equals(key)) {
			for (int x = 1; x <= 11; x++) {
				for (int y = x + 1; y <= 11; y++) {
					for (int z = y + 1; z <= 11; z++) {
						for (int k = z + 1; k <= 11; k++) {
							for (int j = k + 1; j <= 11; j++) {
								for (int h = j + 1; h <= 11; h++) {
									HashMap<String, Long> map = new HashMap<String, Long>();
									map.put("s", 0l);// 出现次数
									map.put("ls", 0l);// 上次遗漏
									map.put("cs", 0l);// 本次遗漏
									map.put("mx", 0l);// 最大遗漏
									countMiss.put(fomatCode(x, y, z, k, j, h), map);
								}
							}
						}
					}
				}
			}
		} else if ("RAND7".equals(key)) {
			for (int x = 1; x <= 11; x++) {
				for (int y = x + 1; y <= 11; y++) {
					for (int z = y + 1; z <= 11; z++) {
						for (int k = z + 1; k <= 11; k++) {
							for (int j = k + 1; j <= 11; j++) {
								for (int h = j + 1; h <= 11; h++) {
									for (int g = h + 1; g <= 11; g++) {
										HashMap<String, Long> map = new HashMap<String, Long>();
										map.put("s", 0l);// 出现次数
										map.put("ls", 0l);// 上次遗漏
										map.put("cs", 0l);// 本次遗漏
										map.put("mx", 0l);// 最大遗漏
										countMiss.put(fomatCode(x, y, z, k, j, h, g), map);
									}
								}
							}
						}
					}
				}
			}
		} else if ("RAND8".equals(key)) {
			for (int x = 1; x <= 11; x++) {
				for (int y = x + 1; y <= 11; y++) {
					for (int z = y + 1; z <= 11; z++) {
						for (int k = z + 1; k <= 11; k++) {
							for (int j = k + 1; j <= 11; j++) {
								for (int h = j + 1; h <= 11; h++) {
									for (int g = h + 1; g <= 11; g++) {
										for (int f = g + 1; f <= 11; f++) {
											HashMap<String, Long> map = new HashMap<String, Long>();
											map.put("s", 0l);// 出现次数
											map.put("ls", 0l);// 上次遗漏
											map.put("cs", 0l);// 本次遗漏
											map.put("mx", 0l);// 最大遗漏
											countMiss.put(fomatCode(x, y, z, k, j, h, g, f), map);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return countMiss;
	}
}
