package com.cai310.lottery.common.miss;

import java.util.HashMap;
import java.util.Map;

import com.cai310.lottery.entity.lottery.NumberAnalyse;

public abstract class AnalyseFilter {

	Map<String, Integer> countAll = new HashMap<String, Integer>();
	Map<String, Integer> count30 = new HashMap<String, Integer>();
	Map<String, Integer> count50 = new HashMap<String, Integer>();
	Map<String, Integer> count100 = new HashMap<String, Integer>();

	private String prefix;

	public AnalyseFilter(String prefix, NumberAnalyse ns, int beginNum, int endNum) {
		this.prefix = prefix;
		if (ns != null) {
			countAll=ns.getMissContentAllMap();
			count30=ns.getMissContent30Map();
			count50=ns.getMissContent50Map();
			count100=ns.getMissContent100Map();
		} else {
			for (int i = beginNum; i <= endNum; i++) {
				countAll.put(prefix + i, 0);
				count30.put(prefix + i, 0);
				count50.put(prefix + i, 0);
				count100.put(prefix + i, 0);
			}
		}

	}

	/**
	 * 从结果中取出符合分析项的号码
	 * 
	 * @param results
	 * @return
	 */
	protected abstract String[] getNeedResult(String results);

	public void checkResult(String results) {
		String[] resultArr = getNeedResult(results);
		for (String str : resultArr) {
			String num = Integer.parseInt(str) + "";
			countAll.put(prefix + num, countAll.get(prefix + num)+1);
			count30.put(prefix + num, count30.get(prefix + num)+1);
			count50.put(prefix + num, count50.get(prefix + num)+1);
			count100.put(prefix + num, count100.get(prefix + num)+1);
		}
	}

	/**
	 * @return the countAll
	 */
	public Map<String, Integer> getCountAll() {
		return countAll;
	}

	/**
	 * @return the count30
	 */
	public Map<String, Integer> getCount30() {
		return count30;
	}

	/**
	 * @return the count50
	 */
	public Map<String, Integer> getCount50() {
		return count50;
	}

	/**
	 * @return the count100
	 */
	public Map<String, Integer> getCount100() {
		return count100;
	}

	public static void main(String[] args) {
		// AnalyseFilter f=new AnalyseFilter("red", 1, 33){
		//
		// @Override
		// public void checkResult(String result) {
		//				
		//				
		// }
		//			
		// };
	}
}
