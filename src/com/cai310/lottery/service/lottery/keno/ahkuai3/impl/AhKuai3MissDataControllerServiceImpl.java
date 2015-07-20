package com.cai310.lottery.service.lottery.keno.ahkuai3.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cai310.lottery.Constant;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.keno.IssueState;
import com.cai310.lottery.common.miss.AnalyseFilter;
import com.cai310.lottery.common.miss.MapSortUtil;
import com.cai310.lottery.common.miss.MissDataFilter;
import com.cai310.lottery.dao.lottery.keno.IssueDataDao;
import com.cai310.lottery.dao.lottery.keno.ahkuai3.AhKuai3IssueDataDao;
import com.cai310.lottery.entity.lottery.NumberAnalyse;
import com.cai310.lottery.entity.lottery.RandomNumMiss;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3IssueData;
import com.cai310.lottery.entity.lottery.keno.ahkuai3.AhKuai3MissDataInfo;
import com.cai310.lottery.service.lottery.keno.impl.MissDataControllerServiceImpl;
import com.cai310.lottery.service.lottery.keno.impl.MissDataEntityManagerImpl;
import com.cai310.utils.NumUtils;
import com.cai310.utils.WriteHTMLUtil;

@Service("ahkuai3MissDataControllerServiceImpl")
public class AhKuai3MissDataControllerServiceImpl extends
		MissDataControllerServiceImpl<AhKuai3MissDataInfo, AhKuai3IssueData> {

	@Autowired
	private AhKuai3IssueDataDao issueDataDao;
	@Autowired
	private AhKuai3MissDataEntityManagerImpl missDataEntityManager;

	@Override
	public List<MissDataFilter> createFilters() {
		List<MissDataFilter> list = new ArrayList<MissDataFilter>();
		// 遗漏项
		MissDataFilter filter = null;
		for (int i = 1; i <= 6; i++) {
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
		// 和值
		for (int i = 3; i <= 18; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public String getFieldName() {
					return "hz" + num;
				}

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int count = 0;
					for (String s : arr) {
						count += Integer.parseInt(s);
					}
					if (count == num) {
						return true;
					}
					return false;
				}
			};
			list.add(filter);
		}
		// 和尾分布
		for (int i = 0; i < 10; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public String getFieldName() {
					return "hzw" + num;
				}

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int count = 0;
					for (String s : arr) {
						count += Integer.parseInt(s);
					}
					int count_ = count % 10;
					if (count_ == num) {
						return true;
					}
					return false;
				}
			};
			list.add(filter);
		}

		// 奇数个数
		for (int i = 0; i <= 3; i++) {
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
		// 偶数个数
		for (int i = 0; i <= 3; i++) {
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
		for (int i = 0; i <= 3; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int count = 0;
					for (String s : arr) {
						if (Integer.parseInt(s) <= 3) {
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
		// 大数个数
		for (int i = 0; i <= 3; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int count = 0;
					for (String s : arr) {
						if (Integer.parseInt(s) > 3) {
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
		for (int i = 0; i <= 3; i++) {
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
		// 合数个数
		for (int i = 0; i <= 2; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int count = 0;
					for (String s : arr) {
						int r = Integer.parseInt(s);
						if (r == 4 || r == 6) {
							count++;
						}
					}
					return count == num;
				}

				@Override
				public String getFieldName() {
					return "hnums" + num;
				}

			};
			list.add(filter);
		}
		// 奇偶比
		for (int i = 0; i <= 3; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int jnum = 0;
					int onum = 0;
					for (int j = 0; j < arr.length; j++) {
						int r = Integer.parseInt(arr[j]);
						if (r % 2 != 0) {
							jnum++;
						} else {
							onum++;
						}
					}
					if (jnum == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "job" + num;
				}

			};
			list.add(filter);
		}
		// 质合比
		for (int i = 0; i <= 3; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int znum = 0;
					int hnum = 0;
					for (int j = 0; j < arr.length; j++) {
						int r = Integer.parseInt(arr[j]);
						if (NumUtils.isPrimNum(r)) {
							znum++;
						} else {
							hnum++;
						}
					}
					if (znum == num) {
						return true;
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "zhb" + num;
				}

			};
			list.add(filter);
		}
		// 大小比
		for (int i = 0; i <= 3; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int bigC = 0;
					int smallC = 0;
					for (int j = 0; j < 3; j++) {
						if (Integer.parseInt(arr[j]) > 3) {
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
					return "dxb" + num;
				}

			};
			list.add(filter);
		}

		// 连号
		for (int i = 1; i <= 6; i++) {
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
						} else if (Integer.parseInt(s) == num - 1
								|| Integer.parseInt(s) == num + 1) {
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
		// 最大跨度
		for (int i = 0; i <= 5; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results
							.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
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
					return "allK" + num;
				}

			};
			list.add(filter);
		}

		// 除3余数
		for (int i = 0; i <= 2; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public boolean accect(String results) {
					String[] arr = results
							.split(Constant.RESULT_SEPARATOR_FOR_NUMBER);
					for (String str : arr) {
						int s = Integer.valueOf(str);
						if (s % 3 == num) {
							return true;
						}
					}
					return false;
				}

				@Override
				public String getFieldName() {
					return "yu" + num;
				}

			};
			list.add(filter);
		}

		// 均值
		for (int i = 1; i <= 6; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public String getFieldName() {
					return "jun" + num;
				}

				@Override
				public boolean accect(String results) {
					String[] arr = results.split(",");
					int sum = 0;
					for (String s : arr) {
						sum += Integer.parseInt(s);
					}
					if (sum / 3 == num) {
						return true;
					}
					return false;
				}
			};
			list.add(filter);
		}
		for (int i = 0; i < 3; i++) {
			final int num = i;
			filter = new MissDataFilter() {

				@Override
				public String getFieldName() {
					return "xt" + num;
				}

				@Override
				public boolean accect(String results) {
					int count = 2;
					String[] arr = results.split(",");
					if (Integer.parseInt(arr[0]) == Integer.parseInt(arr[1])
							&& Integer.parseInt(arr[1]) == Integer
									.parseInt(arr[2])) {
						count = 0;
					} else if (Integer.parseInt(arr[0]) != Integer
							.parseInt(arr[1])
							&& Integer.parseInt(arr[1]) != Integer
									.parseInt(arr[2])) {
						count = 1;
					} else {
						count = 2;
					}
					if (count == num) {
						return true;
					}
					return false;
				}
			};
			list.add(filter);
		}
		return list;
	}

	@Override
	public void createMissFiles() {
		JSONObject resultRoot = new JSONObject();
		JSONObject numRoot = new JSONObject();
		JSONObject secondObj = new JSONObject();
		JSONObject heRoot = new JSONObject();
		JSONObject heObj = new JSONObject();
		List<AhKuai3MissDataInfo> list = missDataEntityManager
				.getLastMissDatas(500);
		Collections.reverse(list);
		JSONObject nummiss = new JSONObject();
		JSONObject hz_nummiss = new JSONObject();

		HashMap<Integer, Integer> tx_static = new HashMap<Integer, Integer>();// 三同号通选
		HashMap<Integer, Integer> lx_static = new HashMap<Integer, Integer>();// 三连号通选
		tx_static.put(1, 0);
		lx_static.put(1, 0);
		HashMap<Integer, Integer> dx_static = new HashMap<Integer, Integer>();// 三同号单选
		HashMap<Integer, Integer> fx_static = new HashMap<Integer, Integer>();// 二同号复选
		HashMap<Integer, Integer> dx_static_2 = new HashMap<Integer, Integer>();// 二同号单选
		ArrayList<Integer> dx_list = new ArrayList<Integer>();
		ArrayList<Integer> fx_list = new ArrayList<Integer>();
		dx_list.add(111);
		dx_list.add(222);
		dx_list.add(333);
		dx_list.add(444);
		dx_list.add(555);
		dx_list.add(666);
		fx_list.add(11);
		fx_list.add(22);
		fx_list.add(33);
		fx_list.add(44);
		fx_list.add(55);
		fx_list.add(66);
		for (int i = 0; i < dx_list.size(); i++) {
			dx_static.put(dx_list.get(i), 0);
		}
		for (int i = 0; i < fx_list.size(); i++) {
			fx_static.put(fx_list.get(i), 0);
		}
		for (int i = 1; i < 7; i++) {
			dx_static_2.put(i, 0);
		}
		ArrayList<String> dxList = new ArrayList<String>();
		ArrayList<String> fxList = new ArrayList<String>();
		ArrayList<String> dxList_2 = new ArrayList<String>();
		// 创建遗漏文件
		for (AhKuai3MissDataInfo info : list) {
			String[] temp = info.getResult().split(",");
			String sum = temp[0] + temp[1] + temp[2];
			dxList.add(sum);
			if (temp[0].equals(temp[1]) || temp[1].equals(temp[2])) {
				if (temp[0].equals(temp[1])) {
					fxList.add(temp[0] + temp[1]);
				} else {
					fxList.add(temp[1] + temp[2]);
				}
			} else {
				fxList.add("12");
			}
			if (temp[0].equals(temp[1]) && temp[1].equals(temp[2])) {
				tx_static.put(1, 0);
			} else {
				tx_static.put(1, tx_static.get(1) + 1);
			}
			Integer num1 = Integer.valueOf(temp[0]);
			Integer num2 = Integer.valueOf(temp[1]);
			Integer num3 = Integer.valueOf(temp[2]);
			if (num3 == num2 + 1 && num2 == num1 + 1) {
				lx_static.put(1, 0);
			} else {
				lx_static.put(1, lx_static.get(1) + 1);
			}
			if (num1 != num2 && num2 != num3) {
				dxList_2.add("0");
			} else if (num1 == num2 && num2 == num3) {
				dxList_2.add("0");
			} else {
				if (num1 == num2) {
					dxList_2.add(temp[2]);
				} else {
					dxList_2.add(temp[0]);
				}
			}
			JSONObject miss = new JSONObject();
			Map<String, Integer> map = info.getMissDataMap();// .getMissDataContent().toMap();
			// 出号
			miss = new JSONObject();
			for (int i = 1; i <= 6; i++) {
				String field = "num" + i;
				miss.put(i, map.get(field));
			}
			nummiss = miss;
			secondObj.put("num", miss);

			// 大小比
			miss = new JSONObject();
			for (int i = 0; i < 4; i++) {
				String field = "dxb" + i;
				miss.put(i, map.get(field));
			}
			secondObj.put("dxb", miss);
			// 奇偶比
			miss = new JSONObject();
			for (int i = 0; i < 4; i++) {
				String field = "job" + i;
				miss.put(i, map.get(field));
			}
			secondObj.put("job", miss);
			// 质合比
			miss = new JSONObject();
			for (int i = 0; i < 4; i++) {
				String field = "zhb" + i;
				miss.put(i, map.get(field));
			}
			secondObj.put("zhb", miss);
			// 形态
			miss = new JSONObject();
			for (int i = 0; i < 3; i++) {
				String field = "xt" + i;
				miss.put(i, map.get(field));
			}
			secondObj.put("xt", miss);

			// 和值遗漏
			miss = new JSONObject();
			for (int i = 3; i <= 18; i++) {
				String field = "hz" + i;
				miss.put(i, map.get(field));
			}
			hz_nummiss = miss;
			heObj.put("hz", miss);

			miss = new JSONObject();
			for (int i = 0; i < 10; i++) {
				String field = "hzw" + i;
				miss.put(i, map.get(field));
			}
			heObj.put("hzw", miss);

			miss = new JSONObject();
			for (int i = 0; i < 3; i++) {
				String field = "yu" + i;
				miss.put(i, map.get(field));
			}
			heObj.put("yu", miss);

			heRoot.put(info.getPeriodNumber(), heObj);
			numRoot.put(info.getPeriodNumber(), secondObj);
			resultRoot.put(info.getPeriodNumber(), info.getResult());
		}

		for (String str_zh : dxList) {
			Set<Integer> keySet = dx_static.keySet();
			for (Integer key : keySet) {
				if (key.equals(Integer.valueOf(str_zh))) {
					dx_static.put(key, 0);
				} else {
					dx_static.put(key, dx_static.get(key) + 1);
				}
			}
		}
		for (String str_zh : dxList_2) {
			Set<Integer> keySet = dx_static_2.keySet();
			for (Integer key : keySet) {
				if (key.equals(Integer.valueOf(str_zh))) {
					dx_static_2.put(key, 0);
				} else {
					dx_static_2.put(key, dx_static_2.get(key) + 1);
				}
			}
		}
		for (String str_zh : fxList) {
			Set<Integer> keySet = fx_static.keySet();
			for (Integer key : keySet) {
				if (key.equals(Integer.valueOf(str_zh))) {
					fx_static.put(key, 0);
				} else  {
					fx_static.put(key, fx_static.get(key) + 1);
				}
			}
		}
		JSONObject tx_miss = new JSONObject();
		tx_miss.put(1, tx_static.get(1));
		JSONObject lx_miss = new JSONObject();
		lx_miss.put(1, lx_static.get(1));
		JSONObject dx_miss = new JSONObject();
		for (int i = 1; i < 7; i++) {
			int value = i * 111;
			dx_miss.put(i, dx_static.get(value));
		}
		JSONObject fx_miss = new JSONObject();
		for (int i = 1; i < 7; i++) {
			int value = i * 11;
			fx_miss.put(i, fx_static.get(value));
		}
		JSONObject dx2_miss = new JSONObject();
		for (int i = 1; i < 7; i++) {
			dx2_miss.put(i, dx_static_2.get(i));
		}
		WriteHTMLUtil.writeHtm("/js/analyse/" + getLottery().getKey() + "/",
				"ahkuai3data.js", "dataAhKuai3=" + resultRoot.toString()
						+ ";function getdata(){return dataAhKuai3;}", "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/" + getLottery().getKey() + "/",
				"ahkuai3_num.js", "ahkuai3Num=" + numRoot.toString(), "UTF-8");
		WriteHTMLUtil.writeHtm("/js/analyse/" + getLottery().getKey() + "/",
				"ahkuai3_he.js", "ahkuai3_he=" + heRoot.toString() + ";",
				"UTF-8");

		if (list.size() != 0 && list != null) {
			// 获取78期开奖结果进行冷热号码统计
			List<AhKuai3MissDataInfo> list_static = new ArrayList<AhKuai3MissDataInfo>();
			if (list.size() <= 80) {
				list_static = list;
			} else {
				list_static = list.subList(list.size() - 81, list.size());
			}
			List<String> list_result = new ArrayList<String>();
			HashMap<Integer, Integer> map_static = new HashMap<Integer, Integer>();

			for (int i = 1; i < 7; i++) {
				map_static.put(i, 0);
			}
			for (AhKuai3MissDataInfo info_result : list_static) {
				list_result.add(info_result.getResult());
			}
			for (String str : list_result) {
				String[] strA = str.split(",");
				for (String str1 : strA) {
					map_static.put(Integer.valueOf(str1),
							map_static.get(Integer.valueOf(str1)) + 1);
				}
			}
			JSONObject numStatic = new JSONObject();
			for (int i = 1; i < 7; i++) {
				numStatic.put(i, map_static.get(i));
			}
			numStatic = numStatic(numStatic);
			// 创建遗漏和冷热号码
			createMaxMissFiles(nummiss, numStatic, hz_nummiss, tx_miss,
					lx_miss, dx_miss, fx_miss, dx2_miss);
		}

	}

	private void createMaxMissFiles(JSONObject nummiss, JSONObject numStatic,
			JSONObject hz_nummiss, JSONObject tx_miss, JSONObject lx_miss,
			JSONObject dx_miss, JSONObject fx_miss, JSONObject dx2_miss) {
		JSONObject root = new JSONObject();
		root.put("num", nummiss);
		root.put("numStatic", numStatic);
		root.put("dx2_miss", dx2_miss);
		root.put("fx_miss", fx_miss);
		root.put("dx_miss", dx_miss);
		root.put("lx_miss", lx_miss);
		root.put("tx_miss", tx_miss);
		root.put("hz_nummiss", hz_nummiss);
		WriteHTMLUtil.writeHtm("/js/analyse/" + getLottery().getKey() + "/",
				"ahkuai3_max_miss.js", "ahkuai3_max_miss=" + root + ";",
				"UTF-8");
	}

	@Override
	public IssueDataDao<AhKuai3IssueData> getIssueDataDao() {
		return issueDataDao;
	}

	@Override
	public MissDataEntityManagerImpl<AhKuai3MissDataInfo, AhKuai3IssueData> getMissDataManager() {
		return missDataEntityManager;
	}

	@Override
	public Lottery getLottery() {
		return Lottery.AHKUAI3;
	}

	public List getMissDatas(int count) {
		return missDataEntityManager.getLastMissDatas(count);
	}

	public List getLastMissDatas(int count, String start, String end) {
		return missDataEntityManager.getLastMissDatas(count, start, end);
	}

	@Override
	public List<AnalyseFilter> getAnalyseFilters(NumberAnalyse ns) {
		List<AnalyseFilter> list = new ArrayList<AnalyseFilter>();
		// 红球冷热分析项
		list.add(new AnalyseFilter("num", ns, 1, 6) {

			@Override
			protected String[] getNeedResult(String results) {
				String[] arr = results.split(",");
				return arr;
			}

		});
		return list;
	}

	/**
	 * 统计冷热号码
	 * 
	 * @param numStatic
	 * @return
	 */
	private JSONObject numStatic(JSONObject numStatic) {
		JSONObject root = new JSONObject();
		Map<String, Integer> missDataMap = MapSortUtil
				.sortByIntegerValueDesc(numStatic);
		root.put("numb", getMaxMissObj(missDataMap));
		missDataMap = MapSortUtil.sortByIntegerValueAsc(numStatic);
		root.put("nums", getMaxMissObj(missDataMap));
		return root;
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
	public void updateGroupNumMiss() {

	}

}
