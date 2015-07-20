package com.cai310.lottery.common.miss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapSortUtil {
	public static Map sortByIntegerValueDesc(Map map) {
		List list = new ArrayList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return (Integer) ((Map.Entry) o2).getValue()
						- (Integer) ((Map.Entry) o1).getValue();
			} 
		});
		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public static Map sortByIntegerValueAsc(Map map) {
		List list = new ArrayList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return (Integer) ((Map.Entry) o1).getValue()
						- (Integer) ((Map.Entry) o2).getValue();
			}
		});
		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	/**
	 * 根据value升序排序
	 * @param map
	 * @return
	 */
	public static Map.Entry[] sortByValueAsc(Map map) {
		Set set = map.entrySet();
		Map.Entry<String,Double>[] entries = (Map.Entry<String,Double>[]) set.toArray(new Map.Entry[set.size()]);
		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Double key1 = Double.valueOf(((Map.Entry)arg0).getValue()
						.toString());
				Double key2 = Double.valueOf(((Map.Entry)arg1).getValue()
						.toString());
				return key1.compareTo(key2);
			}
		});
		return entries;
	}
	/**
	 * 根据value降序序排序
	 * @param map
	 * @return
	 */
	public static Map.Entry[] sortByValueDesc(Map map) {
		Set set = map.entrySet();
		Map.Entry<String,Double>[] entries = (Map.Entry<String,Double>[]) set.toArray(new Map.Entry[set.size()]);
		Arrays.sort(entries, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				Double key1 = Double.valueOf(((Map.Entry)arg0).getValue()
						.toString());
				Double key2 = Double.valueOf(((Map.Entry)arg1).getValue()
						.toString());
				return key2.compareTo(key1);
			}
		});
		return entries;
	}
	
	
	public static void main(String[] args){
		
	}
}
